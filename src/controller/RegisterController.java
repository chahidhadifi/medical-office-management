/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Admin;

/**
 *
 * @author hp
 */
public class RegisterController {

    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblErrors;
    @FXML
    private TextField txtUsername;

    @FXML
    private void submitButton(MouseEvent event) {
        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        if (isValidInput(username, email, password)) {
            Admin admin = new Admin(username, email, password);
            Boolean registrationState = registerAdmin(admin);
            if (registrationState) {
                setLblError(Color.LIMEGREEN, "Registration successful!");
            } else {
                setLblError(Color.TOMATO, email);
            }
        } else {
            setLblError(Color.TOMATO, "Registration failed. Please try again.");
        }
    }
    
    private boolean isValidInput(String username, String email, String password) {
        return isValidUsername(username) && isValidEmail(email) && isValidPassword(password);
    }

    private boolean isValidUsername(String username) {
        return !username.isEmpty();
    }

    private boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

    @FXML
    private void loginButton(MouseEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/LoginFXML.fxml")));
        stage.setScene(scene);
        stage.show();
    }
    
    private void setLblError(Color color, String text) {
       lblErrors.setTextFill(color);
       lblErrors.setText(text);
       System.out.println(text);
    }
    
    private boolean registerAdmin(Admin admin){
        try {
            Connection con = utils.ConnectionDB.conDB();
            String query = "insert into admin(username, email, password) values (?, ?, ?)";
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
            ps.setString(1, admin.getUsername());
            ps.setString(2, admin.getEmail());
            ps.setString(3, admin.getPassword());
            int rowAffected = ps.executeUpdate();
            return rowAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
   }
    
}
