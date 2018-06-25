/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mynetworkingapps.multichat.gui;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import javax.swing.JOptionPane;
import mynetworkingapps.multichat.dao.ChatClientDAO;
import mynetworkingapps.multichat.dao.ChatLogDAO;
import mynetworkingapps.multichat.dbutil.DBConnection;
import mynetworkingapps.multichat.pojo.ChatClient;
import mynetworkingapps.multichat.pojo.ChatLog;

/**
 *
 * @author ABHIRAJ
 */
public class GroupChatServer extends javax.swing.JFrame {

    ServerSocket serverSock;
    ArrayList<String> userNames;
    ArrayList<PrintWriter> printWriter;
    final PrintWriter filePrintWriter;
    FileWriter fileWriter;
    SimpleDateFormat sdf;
    
    
    /**
     * Creates new form GroupChatServer
     */
    public GroupChatServer() {
        initComponents();
        printWriter = new ArrayList<>();
        userNames = new ArrayList<>();
        try {
            fileWriter = new FileWriter("d:\\chatlogs.txt",true);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        filePrintWriter = new PrintWriter(fileWriter,true);
        sdf = new SimpleDateFormat("HH:mm:ss,dd-MMM-yyyy");
    }
    
    public static void addClinetToDB(String user, String password) throws SQLException {
        ChatClient chatClient = new ChatClient();
        chatClient.setUsername(user);
        chatClient.setPassword(password);
        boolean result = ChatClientDAO.addClient(chatClient);
        if(result == false)
            System.out.print("User added to database\n");
        else
            System.out.println("Some error occured while adding user to database\n");
    }
    
    public static void resetPassword(String user, String password) throws SQLException {
        ChatClient chatClient = new ChatClient();
        chatClient.setUsername(user);
        chatClient.setPassword(password);
        boolean result = ChatClientDAO.resetPassword(chatClient);
        if(result == false)
            System.out.print("Password reset successfull.\n");
        else
            System.out.println("Some error occured while resetting password.\n");
    }
    
    public static void addMobile(String user, Long mobile) throws SQLException {
        boolean result = ChatClientDAO.addClientMobile(user, mobile);
        if(result == false)
            System.out.print("User added to database\n");
        else
            System.out.println("Some error occured while adding user to database\n");
    }
    
    public static String findMobile(String user) throws SQLException {
        long mobile = ChatClientDAO.findClientMobile(user);
        return Long.toString(mobile);
    }
    
    class WaitingForClient extends Thread {
        
        @Override
        public void run() {
            try {
                serverSock = new ServerSocket(2222);
                while(true) {
                    Socket obj = serverSock.accept();
                    ChatHandler chatHandler = new ChatHandler(obj);
                    chatHandler.start();
                }
            }
            catch(Exception e) 
            { 
                System.out.println(e); 
            }
        }
    }
    
  
    class ChatHandler extends Thread {
        Socket client;
        Scanner sc;
        PrintWriter pw;
        
        ChatHandler(Socket sock) {
            client = sock;
        }
        
        public void run() {
            try {
                String name;
                boolean login = false;
                sc = new Scanner(client.getInputStream());
                pw = new PrintWriter(client.getOutputStream());
                parent:
                while(true) {
                    login = true;
                    name = sc.nextLine();
                    System.out.println("String name recieved : " + name);
                    //User alredy exist
                    if(ChatClientDAO.findClient(name)) {
                        pw.println(false);
                        pw.flush();
                        System.out.println("boolean sent : " + false);
                        while(true) {
                            String password = sc.next();
                            System.out.println("String passowrd recieved : " + password);
                            boolean found = ChatClientDAO.authenticate(name,password);
                            System.out.print("ChatClientDAO.authenticate(name,password for name = " + name + " password = " + password);
                            System.out.println("found = " + found);
                            if(found) {
                                //login succesfull
                                pw.println(false);
                                if(userNames.contains(name)) {
                                    pw.println(false);
                                    pw.flush();
                                    break parent;
                                } else {
                                    pw.println(true);
                                    pw.flush();
                                    System.out.println("boolean sent for succesful login: " + false);
                                    userNames.add(name);
                                    jTextArea1.append("\nConnected with client : " + name);
                                    printWriter.add(pw);
                                    break parent;
                                }
                            } else {
                                //Password missmatch
                                pw.println(true);
                                pw.println(true);
                                pw.flush();
                                System.out.println("boolean sent for login failed: " + true);
                                boolean reset = sc.nextBoolean();
                                if(reset) {
                                    System.out.println("Resetting password");
                                    String mobile = findMobile(name);
                                    System.out.println("mobile is " + mobile);
                                    //mobile no. sent to client
                                    pw.println(mobile);
                                    pw.flush();
                                    
                                    reset = sc.nextBoolean();
                                    if(reset) {
                                        String newPassword = sc.next();
                                        resetPassword(name, newPassword);
                                    } else {
                                        break parent;
                                    }  
                                } 
                            }
                        }
                    }
                    //Create user
                    else {
                        pw.println(true);
                        pw.flush();
                        System.out.println("boolean sent for user creation: " + true);
                        String password = sc.next();
                        System.out.println("String password recieved user creation : " + password);
                        userNames.add(name);
                        jTextArea1.append("\nConnected with client : " + name);
                        addClinetToDB(name,password);
                        printWriter.add(pw);
                                                
                        String mobile = sc.next();
                        addMobile(name,Long.parseLong(mobile));
                        break;
                    }
                }
                if(login == true)
                while(true) {
                    String str = sc.nextLine();
                    if(str.equals("quit")) {
                        str = name + " has left the chat";
                        pw.println("quit");
                        pw.flush();
                        printWriter.remove(pw);
                        pw.close();
                        client.close();
                    }
                    synchronized(filePrintWriter) {
                        filePrintWriter.println(str + "(" + sdf.format(new Date()).toString() + ")");
                        String str1[] = str.split(":");
                        ChatLog chatLog=new ChatLog();
                        if(str1.length == 2) {
                            chatLog.setUser(str1[0].trim());
                            System.out.println("user : " + str1[0].trim());
                        } else {
                            chatLog.setUser("server");
                            System.out.println("user : server");
                        }
                        if(str1.length == 2) {
                            chatLog.setMessage(str1[1].trim());
                            System.out.println("msg = " + str1[1].trim());
                        } else {
                            chatLog.setMessage(str1[0].trim());
                            System.out.println("msg = " + str1[0].trim());
                        }
                        chatLog.setTime(sdf.format(new Date()).toString());
                        ChatLogDAO.logChat(chatLog);
                    }
                    for(PrintWriter temp : printWriter) {
                        //if(temp.equals(pw) == false)
                        if(temp == pw)
                            continue;
                        temp.println(str);
                        temp.flush();
                    }
                }
                
            }
            catch(Exception e) 
            { 
                System.out.println(e); 
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel1.setText("SERVER");

        jButton1.setText("START SERVER");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("STOP SERVER");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(185, 185, 185)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 9, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Connection conn = DBConnection.getConnection();
        if(conn != null) {
            jTextArea1.setText("Waiting for client at port 2222");
            WaitingForClient waitingForClient = new WaitingForClient();
            waitingForClient.start();
            jButton1.setEnabled(false);
            jButton2.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null,"Error in Connecting to database" ,"Error!",JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        try {
            serverSock.close();
            jTextArea1.append("\n Server Closed!");
            jButton1.setEnabled(true);
            jButton2.setEnabled(false);
        }
        catch(Exception e) { System.out.println(e); }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        filePrintWriter.close();
        JOptionPane.showMessageDialog(null,"Chats logged successfully");
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GroupChatServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GroupChatServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GroupChatServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GroupChatServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GroupChatServer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
