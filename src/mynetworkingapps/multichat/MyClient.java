/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mynetworkingapps.multichat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author ABHIRAJ
 */
public class MyClient {
    public static void main(String args[]) {
    //String hostName = "ABHIRAJ-PC";
    int port = 2222;
    try {
    InetAddress ia=InetAddress.getLocalHost();
    Socket sock=new Socket(ia,port);
    System.out.println("Created a socket");
    PrintWriter pw = new PrintWriter(sock.getOutputStream());
    pw.println("Hello Server");
    pw.close();
    sock.close();
    }
    catch(IOException e) { System.out.println("IOException occured"); }
}
}
