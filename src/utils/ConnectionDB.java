package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionDB {
    Connection conn = null;
    public static Connection conDB() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/medical_office_management", "root", "");
            return con;
        } catch (SQLException ex) {
            System.err.println("ConnectionDB : "+ex.getMessage());
           return null;
        }
    }

}