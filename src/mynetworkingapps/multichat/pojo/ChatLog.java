/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mynetworkingapps.multichat.pojo;

/**
 *
 * @author ABHIRAJ
 */
public class ChatLog {
    String user;
    String message;
    String time;
    
    public void setUser(String user) {
        this.user = user;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public void setTime(String time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
         return time;
    }
}
