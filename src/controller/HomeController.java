package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Collections;
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.util.Duration;

import model.Patient;
import model.Admin;
import utils.FileManager;
import utils.InputValidator;
import dao.AdminDAO;
import dao.PatientDAO;


public class HomeController {

    @FXML
    private Label lblFirstname;
    @FXML
    private Label lblId;
    @FXML
    private Label lblLastname;
    @FXML
    private Label lblUsername;
    @FXML
    private Label lblNbrAccounts;
    @FXML
    private Label lblNbrDoctors;
    @FXML
    private Label lblNbrPatients;

    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtFirstname;
    @FXML
    private TextField txtLastname;
    @FXML
    private TextField txtSearch;
    @FXML
    private ComboBox<String> comboBloodType;
    @FXML
    private ComboBox<String> comboGender;
    
    @FXML
    private TableView<model.Patient> tablePatients;
    
    @FXML
    private TableColumn<model.Patient, String> coEmail;
    @FXML
    private TableColumn<model.Patient, String> coFirstname;
    @FXML
    private TableColumn<model.Patient, String> coGender;
    @FXML
    private TableColumn<model.Patient, String> coBloodType;
    @FXML
    private TableColumn<model.Patient, Integer> coId;
    @FXML
    private TableColumn<model.Patient, String> coLastname;
    
    @FXML
    private AnchorPane PatientsView;
    @FXML
    private AnchorPane dashboardView;
    @FXML
    private AnchorPane doctorsView;
    @FXML
    private AnchorPane accountsView;
    
    @FXML
    private ComboBox<String> comboSortBy;
    @FXML
    private Label lblErrors;

    @FXML
    private Button btnAccounts;
    @FXML
    private Button btnDashboard;
    @FXML
    private Button btnDoctors;
    @FXML
    private Button btnPatients;
    
    Stage stage;
    Admin admin;
    
    @FXML
    public void initialize() {
        comboGender.getItems().addAll("Male", "Female");
        comboBloodType.getItems().addAll("AB+", "AB-", "A+", "A-", "B+", "B-", "O+", "O-");
        comboSortBy.getItems().addAll("ID", "First name", "Last name", "Gender", "Blood Type");
        
        readPatients();
        
        lblNbrPatients.setText(Integer.toString(PatientDAO.countPatients));
        
        if (!LoginController.emailCurrAdmin.isEmpty()) {
            admin = AdminDAO.readByEmail(LoginController.emailCurrAdmin);
        }
        lblId.setText(Integer.toString(admin.getId()));
        lblFirstname.setText(admin.getFirstname());
        lblLastname.setText(admin.getLastname());
        lblUsername.setText(admin.getUsername());
    }
    
    @FXML
    public void closeButton(MouseEvent event) {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void logoutButton(MouseEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/LoginFXML.fxml")));
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void showDashboard(MouseEvent event) {
            dashboardView.setVisible(true);
            PatientsView.setVisible(false);
            doctorsView.setVisible(false);
            accountsView.setVisible(false);
    }
    
    @FXML
    public void showPatients(MouseEvent event) {
            dashboardView.setVisible(false);
            PatientsView.setVisible(true);
            doctorsView.setVisible(false);
            accountsView.setVisible(false);
    }
    
    @FXML
    public void showDoctors(MouseEvent event) {
        dashboardView.setVisible(false);
        PatientsView.setVisible(false);
        doctorsView.setVisible(true);
        accountsView.setVisible(false);
    }
    
    @FXML
    public void showAccounts(MouseEvent event) {
        dashboardView.setVisible(false);
        PatientsView.setVisible(false);
        doctorsView.setVisible(false);
        accountsView.setVisible(true);
    }
    
    
    @FXML
    public void btnSearch(MouseEvent event) {
        int id = Integer.parseInt(txtSearch.getText());
        ArrayList<Patient> patientList = new ArrayList<>(PatientDAO.read());
        ArrayList<Patient> searchedPatient = new ArrayList<>();
        for (Patient patient : patientList) {
            if (patient.getId() == id) {
                searchedPatient.add(patient);
            }
        }
        if (!searchedPatient.isEmpty()) {
            ObservableList<Patient> searchedPatientTab = FXCollections.observableArrayList(searchedPatient);
            coId.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("id"));
            coFirstname.setCellValueFactory(new PropertyValueFactory<Patient, String>("firstname"));
            coLastname.setCellValueFactory(new PropertyValueFactory<Patient, String>("lastname"));
            coEmail.setCellValueFactory(new PropertyValueFactory<Patient, String>("email"));
            coGender.setCellValueFactory(new PropertyValueFactory<Patient, String>("gender"));
            coBloodType.setCellValueFactory(new PropertyValueFactory<Patient, String>("bloodType"));
            tablePatients.setItems(searchedPatientTab);
            txtSearch.setText("");
        } else {
            setLblError(Color.TOMATO, "Operation failed, Please try again.");
            emptyLblAfterDelay();
        }
    }
    
    @FXML
    public void btnSort(MouseEvent event) {
        String sortBy = comboSortBy.getValue();
        if (!sortBy.isEmpty()) {
            ArrayList<Patient> patientList = new ArrayList<>(PatientDAO.read());
            if (sortBy.equals("ID")) {
                Collections.sort(patientList, Comparator.comparing(Patient::getId));
            } else if (sortBy.equals("First name")) {
                Collections.sort(patientList, Comparator.comparing(Patient::getFirstname));
            } else if (sortBy.equals("Last name")) {
                Collections.sort(patientList, Comparator.comparing(Patient::getLastname));
            } else if (sortBy.equals("Gender")) {
                Collections.sort(patientList, Comparator.comparing(Patient::getGender));
            } else if (sortBy.equals("Blood type")) {
                Collections.sort(patientList, Comparator.comparing(Patient::getBloodType));
            }
            ObservableList<Patient> sortedList = FXCollections.observableArrayList(patientList);
            coId.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("id"));
            coFirstname.setCellValueFactory(new PropertyValueFactory<Patient, String>("firstname"));
            coLastname.setCellValueFactory(new PropertyValueFactory<Patient, String>("lastname"));
            coEmail.setCellValueFactory(new PropertyValueFactory<Patient, String>("email"));
            coGender.setCellValueFactory(new PropertyValueFactory<Patient, String>("gender"));
            coBloodType.setCellValueFactory(new PropertyValueFactory<Patient, String>("bloodType"));
            tablePatients.setItems(sortedList);
            comboSortBy.setValue(null);
        } else {
            setLblError(Color.TOMATO, "Operation failed, Please try again.");
            emptyLblAfterDelay();
        }
    }
    
    @FXML
    public void createPatient(MouseEvent event) throws SQLException {
        String firstname = txtFirstname.getText();
        String lastname = txtLastname.getText();
        String email = txtEmail.getText();
        String gender = comboGender.getValue();
        String bloodType = comboBloodType.getValue();
        if (InputValidator.isValidInput(firstname, lastname, email, gender, bloodType)) {
            Patient patient = new Patient(firstname, lastname, email, gender, bloodType);
            Boolean operationState = dao.PatientDAO.create(patient);
            if (operationState) {
                clearFields();
                readPatients();
            }  else {
                setLblError(Color.TOMATO, "Operation failed, Please try again.");
                emptyLblAfterDelay();
            }
        } else {
            setLblError(Color.TOMATO, "Operation failed, Please try again.");
            emptyLblAfterDelay();
        }
    }

    public void readPatients() {
        ObservableList<Patient> patientList = PatientDAO.read();
        coId.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("id"));
        coFirstname.setCellValueFactory(new PropertyValueFactory<Patient, String>("firstname"));
        coLastname.setCellValueFactory(new PropertyValueFactory<Patient, String>("lastname"));
        coEmail.setCellValueFactory(new PropertyValueFactory<Patient, String>("email"));
        coGender.setCellValueFactory(new PropertyValueFactory<Patient, String>("gender"));
        coBloodType.setCellValueFactory(new PropertyValueFactory<Patient, String>("bloodType"));
        tablePatients.setItems(patientList);
    }
    
    @FXML
    public void updatePatient(MouseEvent event) {
        int indexOfSelectedRow = tablePatients.getSelectionModel().getSelectedIndex();
        int id = Integer.parseInt(String.valueOf(tablePatients.getItems().get(indexOfSelectedRow).getId()));
        String firstname = txtFirstname.getText();
        String lastname = txtLastname.getText();
        String email = txtEmail.getText();
        String gender = comboGender.getValue();
        String bloodType = comboBloodType.getValue();
        Patient patient = new Patient(id, firstname, lastname, email, gender, bloodType);
        Boolean operationState = PatientDAO.update(patient);
        if (operationState) {
            clearFields();
            readPatients();
        } else {
            setLblError(Color.TOMATO, "Operation failed, Please try again.");
            emptyLblAfterDelay();
        }
    }
    
    @FXML
    public void deletePatient(MouseEvent event) {
        int indexOfSelectedRow = tablePatients.getSelectionModel().getSelectedIndex();
        int id = Integer.parseInt(String.valueOf(tablePatients.getItems().get(indexOfSelectedRow).getId()));
        boolean operationState = PatientDAO.delete(id);
        if (operationState) {
            readPatients();
        } else {
            setLblError(Color.TOMATO, "Operation failed, Please try again.");
            emptyLblAfterDelay();
       }
    }
    
    @FXML
    public void printAllPatients(MouseEvent event) {
        ArrayList<Patient> listPatient = new ArrayList<>(PatientDAO.read());
        FileManager.saveAllPatients(listPatient);
    }
    
    public void setLblError(Color color, String text) {
       lblErrors.setTextFill(color);
       lblErrors.setText(text);
       System.out.println(text);
    }
    
    public void clearFields() {
        txtFirstname.setText("");
        txtLastname.setText("");
        txtEmail.setText("");
        comboGender.setValue(null);
        comboBloodType.setValue(null);    
    }
    
    public void emptyLblAfterDelay() {
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> setLblError(Color.TRANSPARENT, ""));
        pause.play();
    }

}
