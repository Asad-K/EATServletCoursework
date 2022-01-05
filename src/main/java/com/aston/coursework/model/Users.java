package com.aston.coursework.model;

import java.sql.PreparedStatement;
import java.sql.Connection;

import java.sql.ResultSet;

import com.aston.coursework.util.DBUtil;


public class Users {

    public Users() {
    }

    // TODO: implement this method so that if the user does not exist, it returns -1. - Done
    // If the username and password are correct, it should return the 'clientID' value from the database. - Done
    public int isValid(String name, String pwd) {
        try {
            Connection connection = DBUtil.getConnection();
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement("SELECT clientid FROM clients WHERE username = ? AND password = ?");
                statement.setString(1, name);
                statement.setString(2, pwd);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
                connection.close();
            }
            return -1;
        } catch (Exception e) {
            System.out.println("Exception is ;" + e + ": message is " + e.getMessage());
            return -1;
        }
    }

    public int usernameExists(String name) {
        try {
            Connection connection = DBUtil.getConnection();
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement("SELECT clientid FROM clients WHERE username = ?");
                statement.setString(1, name);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
                connection.close();
            }
            return -1;
        } catch (Exception e) {
            System.out.println("Exception is ;" + e + ": message is " + e.getMessage());
            return -1;
        }
    }


    // TODO:  add a user with specified username and password
    // TODO: implement this method so that the specified username and password are inserted into the database.
    public boolean addUser(String name, String pwd) {

        // Check if user is already in database
        if (usernameExists(name) != -1) return false;
        // if username or password is empty
        if (name.isEmpty() || pwd.isEmpty()) return false;

        try {
            Connection connection = DBUtil.getConnection();
            if (connection != null) {
                PreparedStatement statement = connection
                        .prepareStatement("INSERT INTO clients (username, password) VALUES (?, ?)");
                statement.setString(1, name);
                statement.setString(2, pwd);
                statement.executeUpdate();
                connection.close();
            }
            return true;
        } catch (Exception e) {
            System.out.println("Exception is ;" + e + ": message is " + e.getMessage());
            return false;
        }
    }
}
