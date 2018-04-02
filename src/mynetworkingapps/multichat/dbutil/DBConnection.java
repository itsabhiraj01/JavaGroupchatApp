/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mynetworkingapps.multichat.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author ABHIRAJ
 */
public class DBConnection {
    
    private static Connection conn;
    
    static {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            
            conn = DriverManager.getConnection("jdbc:oracle:thin:@//Sachin-PC/orcl","chatdata","chatapp");
            JOptionPane.showMessageDialog(null,"Connected succesfull to the database","Success!",JOptionPane.INFORMATION_MESSAGE);
            
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
    
    public static Connection getConnection() {
        return conn;
    }
    
    public static void closeConnection() {
        try {
            if(conn != null) 
                conn.close();
            JOptionPane.showMessageDialog(null,"Disconnectd successfully from the database","Success!",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Error Disconnection from the database" + e,"Error!",JOptionPane.ERROR_MESSAGE);
        }
    }
}
