package model;

import java.io.Serializable;

import utils.IdGenerator;
import utils.PersonalInformations;

public class Doctor extends PersonalInformations implements Serializable {
    
    private String gender;
    private String speciality;

    public Doctor(String firstname, String lastname, String email, String gender, String speciality) {
        super(IdGenerator.getDoctorId(), firstname, lastname, email);
        this.gender = gender;
        this.speciality = speciality;
    }
    
    public Doctor(int id, String firstname, String lastname, String email, String gender, String speciality) {
        super(id, firstname, lastname, email);
        this.gender = gender;
        this.speciality = speciality;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    @Override
    public String toString() {
        return "Doctor{" + "gender=" + gender + ", speciality=" + speciality + '}';
    }
    
}
