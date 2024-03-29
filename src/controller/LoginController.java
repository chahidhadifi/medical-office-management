package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.ConnectionDB;

public class LoginController implements Initializable {

    @FXML
    private Label lblErrors;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnSignin;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Stage stage;
    
    static String emailCurrAdmin = "";
    
    @FXML
    public void closeButton(MouseEvent event) {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void loginButton(MouseEvent event) {

        if (event.getSource() == btnSignin) {
            if (logIn().equals("Success")) {
                try {
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/HomeFXML.fxml")));
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }

            }
        }
    }
    
    @FXML
    public void registerButton(MouseEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/RegisterFXML.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (con == null) {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Failed to connect to database");
            emptyLblAfterDelay();
        }
    }

    public LoginController() {
        con = ConnectionDB.conDB();
    }

    private String logIn() {
        String status = "Success";
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        if(email.isEmpty() || password.isEmpty()) {
            setLblError(Color.TOMATO, "Empty credentials");
            status = "Error";
            emptyLblAfterDelay();
        } else {
            String sql = "select email, password from admin where email = ? and password = ?";
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    setLblError(Color.TOMATO, "Enter correct email or password");
                    status = "Error";
                    emptyLblAfterDelay();
                }
                emailCurrAdmin = email;
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                status = "Exception";
            }
        }
        return status;
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