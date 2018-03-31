/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mynetworkingapps.multichat;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;
/**
 *
 * @author ABHIRAJ
 */
public class getTimeNist {
    public static void main(String args[]) {
    String host = "time-c.nist.gov";
    int port = 13;
    try {
    Socket s = new Socket(host,port);
    Scanner in = new Scanner(s.getInputStream());
    in.nextLine(); //first line is empty
    String date = in.nextLine();
    System.out.println("Time is : " + date);
    }
    catch(IOException e) { System.out.println("IOException occured"); }
}
}
