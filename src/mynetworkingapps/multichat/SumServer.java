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
public class SumServer {
    public static void main(String args[]) {
    try {
        ServerSocket ServerSock = new ServerSocket(2222);
        Socket client1 =  ServerSock.accept();
        Scanner in = new Scanner(client1.getInputStream());
        
        Scanner kb = new Scanner(System.in);
        PrintWriter pw = new PrintWriter(client1.getOutputStream());
        int sum;
        do {
        sum = in.nextInt();
        sum += in.nextInt();
        pw.println(sum);
        pw.flush();
        }while(sum != 0);
        client1.close();
        ServerSock.close();
    }
    catch(IOException io) { System.out.println(io);}
}
}
