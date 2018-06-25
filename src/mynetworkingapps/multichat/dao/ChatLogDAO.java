/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mynetworkingapps.multichat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import mynetworkingapps.multichat.dbutil.DBConnection;
import mynetworkingapps.multichat.pojo.ChatLog;

/**
 *
 * @author ABHIRAJ
 */
public class ChatLogDAO {
    public static boolean logChat(ChatLog chatLog) throws SQLException {
        String qry = "INSERT INTO chatlogs values(?,?,?)";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(qry);
        ps.setString(1,chatLog.getUser());
        ps.setString(2,chatLog.getMessage());
        ps.setString(3,chatLog.getTime());
        boolean result = ps.execute();
        return result;
    }
}
