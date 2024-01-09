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
import javafx.scene.control.Button;
import javafx.stage.Stage;

import model.Admin;
import dao.AdminDAO;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import utils.InputValidator;

public class RegisterController {
    
    @FXML
    private TextField txtFirstname;
    @FXML
    private TextField txtLastname;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblErrors;
    
    Stage stage;

    @FXML
    private void submitButton(MouseEvent event) {
        String firstname = txtFirstname.getText();
        String lastname = txtLastname.getText();
        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        if (InputValidator.isValidInputRegistration(firstname, lastname, username, email, password)) {
            Admin admin = new Admin(firstname, lastname, email, password, username);
            Boolean registrationState = AdminDAO.create(admin);
            if (registrationState) {
                setLblError(Color.LIMEGREEN, "Registration successful!");
                emptyLblAfterDelay();
            } else {
                setLblError(Color.TOMATO, "Registration failed, Please try again.");
                emptyLblAfterDelay();
            }
        } else {
            setLblError(Color.TOMATO, "Registration failed. Please try again.");
            emptyLblAfterDelay();
        }
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
    
    @FXML
    public void closeButton(MouseEvent event) {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    private void setLblError(Color color, String text) {
       lblErrors.setTextFill(color);
       lblErrors.setText(text);
       System.out.println(text);
    }
    
    public void emptyLblAfterDelay() {
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> setLblError(Color.TRANSPARENT, ""));
        pause.play();
    }
    
}
