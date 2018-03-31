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
import java.util.Scanner;

/**
 *
 * @author ABHIRAJ
 */
class MyClient2 {
    public static void main(String args[]) {
    //String hostName = "ABHIRAJ-PC";
    int port = 2222;
    try {
    InetAddress ia=InetAddress.getLocalHost();
    Socket sock=new Socket(ia,port);
    System.out.println("Created a socket");
    PrintWriter pw = new PrintWriter(sock.getOutputStream());
    Scanner kb= new Scanner(sock.getInputStream());
    String str, serverMsg;
    Scanner in = new Scanner(System.in);
    do{
    System.out.println("Enter something ");
    str = in.nextLine();
    pw.println(str);
    pw.flush();
    serverMsg = kb.nextLine();
    System.out.println("Server : "+ serverMsg);
    }while(!str.equals("quit"));
    System.out.println("Exiting");
    pw.close();
    sock.close();
    }
    catch(IOException e) { System.out.println("IOException occured"); }
}
}
