package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import model.Admin;
import utils.ConnectionDB;

public class AdminDAO {
    
    public static int countAdmins = 0;
    
    public AdminDAO() {
        
    }
    
    public static boolean create(Admin admin) {
        try {
            Connection con = utils.ConnectionDB.conDB();
            String query = "insert into admin(id, firstname, lastname, email, password, username) values (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
            ps.setInt(1, admin.getId());
            ps.setString(2, admin.getFirstname());
            ps.setString(3, admin.getLastname());
            ps.setString(4, admin.getEmail());
            ps.setString(5, admin.getPassword());
            ps.setString(6, admin.getUsername());
            int rowAffected = ps.executeUpdate();
            return rowAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static Admin readByEmail(String email) {
        Connection con = ConnectionDB.conDB();
        String query = "select * from admin";
        Statement st;
        ResultSet rs;
        Admin admin = null;
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
               admin = new Admin(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("email"), rs.getString("password"), rs.getString("username"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }
    
    public static boolean update(Admin admin) {
        try {
            Connection con = ConnectionDB.conDB();
            String query = "update admin set firstname=?, lastname=?, email=?, password=?, username=? where id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, admin.getFirstname());
            ps.setString(2, admin.getLastname());
            ps.setString(3, admin.getEmail());
            ps.setString(4, admin.getPassword());
            ps.setString(5, admin.getUsername());
            ps.setInt(6, admin.getId());
            int rowAffected = ps.executeUpdate();
            return rowAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public static boolean delete(int id) {
        try {
            Connection con = ConnectionDB.conDB();
            String query = "delete from admin where id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            int rowAffected = ps.executeUpdate();
            return rowAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public static ObservableList<Admin> read() {
        ObservableList<Admin> doctorList = FXCollections.observableArrayList();
        Connection con = ConnectionDB.conDB();
        String query = "select * from admin";
        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            Admin admin;
            while (rs.next()) {
               admin = new Admin(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("email"), rs.getString("password"), rs.getString("username"));
               doctorList.add(admin);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        countAdmins = doctorList.size();
        return doctorList;
    }
    
}
