package utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Patient;

public class FileManager {
    
    public FileManager() {
        
    }
    
    public static void saveAllPatients(List<Patient> listPatient) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:/Patients.txt"));
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
    
}
