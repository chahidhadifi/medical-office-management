package model;

import java.io.Serializable;

import utils.PersonalInformations;
import utils.IdGenerator;

public class Admin extends PersonalInformations implements Serializable {
    
    private String username;
    private String password;
    
    public Admin(String firstname, String lastname, String email, String password, String username) {
        super(IdGenerator.getAdminId(), firstname, lastname, email);
        this.username = username;
        this.password = password;
    }
    
    public Admin(int id, String firstname, String lastname, String email, String password, String username) {
        super(id, firstname, lastname, email);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" + "username=" + username + ", password=" + password + '}';
    }
    
}
