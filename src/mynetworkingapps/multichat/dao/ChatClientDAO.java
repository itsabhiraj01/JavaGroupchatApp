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

/**
 *
 * @author ABHIRAJ
 */
public class ChatClientDAO {
    
    public static boolean findClient(String username) throws SQLException {
        String qry = "SELECT * FROM chatclients where user_name=?";
        boolean found = false;
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(qry);
        ps.setString(1,username);
        ResultSet rs = ps.executeQuery();
        found = rs.next();
        return found;
    }
    
    
}
