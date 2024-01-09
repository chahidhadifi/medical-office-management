package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import model.Doctor;
import utils.ConnectionDB;

public class DoctorDAO {
    
    public static int countDoctors = 0;
    
    public DoctorDAO() {
        
    }
    
    public static boolean create(Doctor doctor) {
        try {
            Connection con = ConnectionDB.conDB();
            String query = "insert into doctor(id, firstname, lastname, email, gender, specialite) values (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, doctor.getId());
            ps.setString(2, patient.getFirstname());
            ps.setString(3, patient.getLastname());
            ps.setString(4, patient.getEmail());
            ps.setString(5, patient.getGender());
            ps.setString(6, patient.getBloodType());
            int rowAffected = ps.executeUpdate();
            return rowAffected > 0;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean update(Doctor doctor) {
        try {
            Connection con = ConnectionDB.conDB();
            String query = "update doctor set firstname=?, lastname=?, email=?, gender=?, bloodtype=? where id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, doctor.getFirstname());
            ps.setString(2, doctor.getLastname());
            ps.setString(3, doctor.getEmail());
            ps.setString(4, doctor.getGender());
            ps.setString(5, doctor.getSpecialite());
            ps.setInt(6, doctor.getId());
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
            String query = "delete from doctor where id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            int rowAffected = ps.executeUpdate();
            return rowAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public static ObservableList<Doctor> read() {
        ObservableList<Doctor> doctorList = FXCollections.observableArrayList();
        Connection con = ConnectionDB.conDB();
        String query = "select * from doctor";
        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            Doctor doctor;
            while (rs.next()) {
               doctor = new Doctor(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("email"), rs.getString("gender"), rs.getString("bloodtype"));
               doctorList.add(doctor);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        countDoctors = doctorList.size();
        return doctorList;
    }
    
}
