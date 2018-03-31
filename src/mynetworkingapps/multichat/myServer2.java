/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mynetworkingapps.multichat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author ABHIRAJ
 */
public class myServer2 {
    public static void main(String args[]) {
    try {
        ServerSocket ServerSock = new ServerSocket(2222);
        Socket client1 =  ServerSock.accept();
        Scanner in = new Scanner(client1.getInputStream());
        
        Scanner kb = new Scanner(System.in);
        PrintWriter pw = new PrintWriter(client1.getOutputStream());
        String msg, Inputmsg;
        do {
        msg = in.nextLine();
        System.out.println("client : " + msg);
        System.out.println("Enter something : ");
        Inputmsg = kb.nextLine();
        pw.println(Inputmsg);
        pw.flush();
        }while(!msg.equals("quit"));
        client1.close();
        ServerSock.close();
    }
    catch(IOException io) { System.out.println(io);}
}
}
