/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mynetworkingapps.multichat;

import java.net.InetAddress;
import java.net.UnknownHostException;
/**
 *
 * @author ABHIRAJ
 */
public class ShowMyIp {
    public static void main(String args[]) {
        try {
            InetAddress obj = InetAddress.getLocalHost();
            System.out.println("IP is : " + obj.getHostAddress());
            System.out.println("IP is : " + obj.getHostAddress());
        }
        catch(UnknownHostException ex) {
            System.out.println("Exception");
        }
    }  
}
