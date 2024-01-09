package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Doctor;
import model.Patient;
import model.Admin;

public class FileManager {
    

    public static File filePatients;
    public static File fileAdmins;
    public static File fileDoctors;
    
    public FileManager() {
        
    }
    
    public static void saveAllPatients(List<Patient> listPatient) {
        try {
            filePatients = new File("D:/patients.txt");
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePatients));
            for (Patient patient : listPatient) {
                oos.writeObject(patient);
                oos.flush();
            }
            oos.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void saveAllDoctors(List<Doctor> listDoctor) {
        try {
            fileDoctors = new File("D:/doctors.txt");
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileDoctors));
            for (Doctor doctor : listDoctor) {
                oos.writeObject(doctor);
                oos.flush();
            }
            oos.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void saveAllAdmins(List<Admin> listAdmin) {
        try {
            fileAdmins = new File("D:/admins.txt");
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileAdmins));
            for (Admin admin : listAdmin) {
                oos.writeObject(admin);
                oos.flush();
            }
            oos.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
