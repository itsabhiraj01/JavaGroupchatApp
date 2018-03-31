/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mynetworkingapps.multichat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author ABHIRAJ
 */
public class myServer {
    public static void main(String args[]) {
    try {
        ServerSocket ServerSock = new ServerSocket(2222);
        Socket client1 =  ServerSock.accept();
        Scanner in = new Scanner(client1.getInputStream());
        String msg = in.next();
        System.out.println("client : " + msg);
        client1.close();
        ServerSock.close();
    }
    catch(IOException io) { System.out.println("Error");}
}
}
