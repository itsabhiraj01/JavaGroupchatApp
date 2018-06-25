/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mynetworkingapps.multichat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mynetworkingapps.multichat.dbutil.DBConnection;
import mynetworkingapps.multichat.pojo.ChatClient;

/**
 *
 * @author ABHIRAJ
 */
public class ChatClientDAO {
    
    public static boolean findClient(String username) throws SQLException {
        String qry = "SELECT * FROM CHATCLIENTS where USER_NAME=?";
        boolean found = false;
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(qry);
        ps.setString(1,username);
        ResultSet rs = ps.executeQuery();
        found = rs.next();
        System.out.println("user found : " + found);
        return found;
    }
    
    public static boolean addClient(ChatClient chatClient) throws SQLException {
        String qry = "INSERT INTO chatclients(USER_NAME,PASSWORD) values(?,?)";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(qry);
        ps.setString(1,chatClient.getUsername());
        ps.setString(2,chatClient.getPassword());
        boolean result = ps.execute();
        return result;
    }
    
    public static boolean resetPassword(ChatClient chatClient) throws SQLException {
        String qry = "UPDATE chatclients set password=? WHERE USER_NAME=?";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(qry);
        ps.setString(1,chatClient.getPassword());
        ps.setString(2,chatClient.getUsername());
        boolean result = ps.execute();
        return result;
    }
    
    public static boolean addClientMobile(String username, Long mobile) throws SQLException {
        String qry = "UPDATE chatclients set mobile=? WHERE USER_NAME=?";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(qry);
        ps.setLong(1,mobile);
        ps.setString(2,username);
        boolean result = ps.execute();
        return result;
    }
    
    public static long findClientMobile(String username) throws SQLException {
        String qry = "SELECT mobile FROM CHATCLIENTS WHERE USER_NAME=?";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(qry);
        ps.setString(1,username);
        ResultSet rs = ps.executeQuery();
        String temp = "mobile";
        long mob=0;
        if(rs.next())
            mob = rs.getLong(temp);
        return mob;
    }
    
    public static boolean authenticate(String username,String password) throws SQLException {
        String qry = "SELECT * FROM CHATCLIENTS where USER_NAME=? AND PASSWORD=?";
        boolean found = false;
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(qry);
        ps.setString(1,username);
        ps.setString(2,password);
        ResultSet rs = ps.executeQuery();
        found = rs.next();
        System.out.println("user authenticated : " + found);
        return found;
    }
}
