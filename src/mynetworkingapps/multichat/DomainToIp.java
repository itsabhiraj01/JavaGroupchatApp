/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mynetworkingapps.multichat;
import java.net.UnknownHostException;
import java.net.InetAddress;
import java.util.Scanner;
/**
 *
 * @author ABHIRAJ
 */
public class DomainToIp {
    public static void main(String args[]) {
    Scanner in = new Scanner(System.in);
    System.out.println("Enter a domain name");
    String domain = in.nextLine();
        try {
            InetAddress obj = InetAddress.getByName(domain);
            System.out.println("IP address of " + domain + " is : " + obj.getHostAddress());
        }
        catch(UnknownHostException ex) {
            System.out.print("Exception found");
        }
    }
}
