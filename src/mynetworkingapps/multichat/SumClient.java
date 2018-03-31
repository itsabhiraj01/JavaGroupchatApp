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
class SumClient {
    public static void main(String args[]) {
    //String hostName = "ABHIRAJ-PC";
    int port = 2222;
    try {
    InetAddress ia=InetAddress.getLocalHost();
    Socket sock=new Socket(ia,port);
    System.out.println("Created a socket");
    PrintWriter pw = new PrintWriter(sock.getOutputStream());
    Scanner kb= new Scanner(sock.getInputStream());
    int num;
    System.out.println("Enter 0 twice to exit (0 , 0) ");
    Scanner in = new Scanner(System.in);
    do{
    System.out.println("Enter first no. ");
    num = in.nextInt();
    pw.println(num);
    pw.flush();
    System.out.println("Enter second no. ");
    num = in.nextInt();
    pw.println(num);
    pw.flush();
    num = kb.nextInt();
    System.out.println("Sum : "+ num);
    }while(num != 0);
    System.out.println("Exiting");
    pw.close();
    sock.close();
    }
    catch(IOException e) { System.out.println("IOException occured"); }
}
}
