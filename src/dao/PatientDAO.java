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

import model.Patient;
import utils.ConnectionDB;

public class PatientDAO {
    
    public static int countPatients = 0;
    
    public PatientDAO() {
        
    }
    
    public static boolean create(Patient patient) {
        try {
            Connection con = ConnectionDB.conDB();
            String query = "insert into patient(id, firstname, lastname, email, gender, bloodtype) values (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, patient.getId());
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
    
    public static boolean update(Patient patient) {
        try {
            Connection con = ConnectionDB.conDB();
            String query = "update patient set firstname=?, lastname=?, email=?, gender=?, bloodtype=? where id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, patient.getFirstname());
            ps.setString(2, patient.getLastname());
            ps.setString(3, patient.getEmail());
            ps.setString(4, patient.getGender());
            ps.setString(5, patient.getBloodType());
            ps.setInt(6, patient.getId());
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
            String query = "delete from patient where id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            int rowAffected = ps.executeUpdate();
            return rowAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public static ObservableList<Patient> read() {
        ObservableList<Patient> patientList = FXCollections.observableArrayList();
        Connection con = ConnectionDB.conDB();
        String query = "select * from patient";
        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            Patient patient;
            while (rs.next()) {
               patient = new Patient(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("email"), rs.getString("gender"), rs.getString("bloodtype"));
               patientList.add(patient);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        countPatients = patientList.size();
        return patientList;
    }
    
}
