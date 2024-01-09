package model;

import java.io.Serializable;

import utils.PersonalInformations;
import utils.IdGenerator;

public class Patient extends PersonalInformations implements Serializable {
    
    private String gender;
    private String bloodType;

    public Patient(String firstname, String lastname, String email, String gender, String bloodType) {
        super(IdGenerator.getPatientId(), firstname, lastname, email);
        this.gender = gender;
        this.bloodType = bloodType;
    }
    
    public Patient(int id, String firstname, String lastname, String email, String gender, String bloodType) {
        super(id, firstname, lastname, email);
        this.gender = gender;
        this.bloodType = bloodType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    @Override
    public String toString() {
        return "Patient{" + "gender=" + gender + ", bloodType=" + bloodType + '}';
    }

}
