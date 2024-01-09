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
import model.Doctor;

import utils.FileManager;
import utils.InputValidator;

import dao.AdminDAO;
import dao.PatientDAO;
import dao.DoctorDAO;


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
    private TextField txtEmailDoc;
    @FXML
    private TextField txtFirstnameDoc;
    @FXML
    private TextField txtLastnameDoc;
    @FXML
    private TextField txtSearchDoc;
    @FXML
    private ComboBox<String> comboSpeciality;
    @FXML
    private ComboBox<String> comboGenderDoc;
    
    @FXML
    private TableView<model.Patient> tablePatients;
    @FXML
    private TableView<model.Doctor> tableDoctors;
    
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
    private TableColumn<model.Doctor, String> coEmailDoc;
    @FXML
    private TableColumn<model.Doctor, String> coFirstnameDoc;
    @FXML
    private TableColumn<model.Doctor, String> coGenderDoc;
    @FXML
    private TableColumn<model.Doctor, String> coSpeciality;
    @FXML
    private TableColumn<model.Doctor, Integer> coIdDoc;
    @FXML
    private TableColumn<model.Doctor, String> coLastnameDoc;
    
    @FXML
    private AnchorPane PatientsView;
    @FXML
    private AnchorPane dashboardView;
    @FXML
    private AnchorPane doctorsView;
    
    @FXML
    private ComboBox<String> comboSortBy;
    @FXML
    private ComboBox<String> comboSortByDoc;
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
        comboSortByDoc.getItems().addAll("ID", "First name", "Last name", "Gender", "Speciality");
        comboSpeciality.getItems().addAll("Cardiologists", "Ophthalmologists", "Allergists", "Endocrinologists", "Anesthesiologist", "Geneticist");
        comboGenderDoc.getItems().addAll("Male", "Female");
        
        readPatients();
        readDoctors();
        
        lblNbrPatients.setText(Integer.toString(PatientDAO.countPatients));
        lblNbrDoctors.setText(Integer.toString(DoctorDAO.countDoctors));
        
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
            // accountsView.setVisible(false);
    }
    
    @FXML
    public void showPatients(MouseEvent event) {
            dashboardView.setVisible(false);
            PatientsView.setVisible(true);
            doctorsView.setVisible(false);
            //accountsView.setVisible(false);
    }
    
    @FXML
    public void showDoctors(MouseEvent event) {
        dashboardView.setVisible(false);
        PatientsView.setVisible(false);
        doctorsView.setVisible(true);
        //accountsView.setVisible(false);
    }
    
    @FXML
    public void showAccounts(MouseEvent event) {
        dashboardView.setVisible(false);
        PatientsView.setVisible(false);
        doctorsView.setVisible(false);
        //accountsView.setVisible(true);
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
    public void btnSearchDoc(MouseEvent event) {
        int id = Integer.parseInt(txtSearchDoc.getText());
        ArrayList<Doctor> doctorList = new ArrayList<>(DoctorDAO.read());
        ArrayList<Doctor> searchedDoctor = new ArrayList<>();
        for (Doctor doctor : doctorList) {
            if (doctor.getId() == id) {
                searchedDoctor.add(doctor);
            }
        }
        if (!searchedDoctor.isEmpty()) {
            ObservableList<Doctor> searchedDoctorTab = FXCollections.observableArrayList(searchedDoctor);
            coIdDoc.setCellValueFactory(new PropertyValueFactory<Doctor, Integer>("id"));
            coFirstnameDoc.setCellValueFactory(new PropertyValueFactory<Doctor, String>("firstname"));
            coLastnameDoc.setCellValueFactory(new PropertyValueFactory<Doctor, String>("lastname"));
            coEmailDoc.setCellValueFactory(new PropertyValueFactory<Doctor, String>("email"));
            coGenderDoc.setCellValueFactory(new PropertyValueFactory<Doctor, String>("gender"));
            coSpeciality.setCellValueFactory(new PropertyValueFactory<Doctor, String>("speciality"));
            tableDoctors.setItems(searchedDoctorTab);
            txtSearchDoc.setText("");
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
    public void btnSortDoc(MouseEvent event) {
        String sortBy = comboSortByDoc.getValue();
        if (!sortBy.isEmpty()) {
            ArrayList<Doctor> doctorList = new ArrayList<>(DoctorDAO.read());
            if (sortBy.equals("ID")) {
                Collections.sort(doctorList, Comparator.comparing(Doctor::getId));
            } else if (sortBy.equals("First name")) {
                Collections.sort(doctorList, Comparator.comparing(Doctor::getFirstname));
            } else if (sortBy.equals("Last name")) {
                Collections.sort(doctorList, Comparator.comparing(Doctor::getLastname));
            } else if (sortBy.equals("Gender")) {
                Collections.sort(doctorList, Comparator.comparing(Doctor::getGender));
            } else if (sortBy.equals("Speciality")) {
                Collections.sort(doctorList, Comparator.comparing(Doctor::getSpeciality));
            }
            ObservableList<Doctor> sortedList = FXCollections.observableArrayList(doctorList);
            coIdDoc.setCellValueFactory(new PropertyValueFactory<Doctor, Integer>("id"));
            coFirstnameDoc.setCellValueFactory(new PropertyValueFactory<Doctor, String>("firstname"));
            coLastnameDoc.setCellValueFactory(new PropertyValueFactory<Doctor, String>("lastname"));
            coEmailDoc.setCellValueFactory(new PropertyValueFactory<Doctor, String>("email"));
            coGenderDoc.setCellValueFactory(new PropertyValueFactory<Doctor, String>("gender"));
            coSpeciality.setCellValueFactory(new PropertyValueFactory<Doctor, String>("speciality"));
            tableDoctors.setItems(sortedList);
            comboSortByDoc.setValue(null);
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
                clearPatientFields();
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
    
    
    @FXML
    public void createDoctor(MouseEvent event) throws SQLException {
        String firstname = txtFirstnameDoc.getText();
        String lastname = txtLastnameDoc.getText();
        String email = txtEmailDoc.getText();
        String gender = comboGenderDoc.getValue();
        String speciality = comboSpeciality.getValue();
        if (InputValidator.isValidInput(firstname, lastname, email, gender, speciality)) {
            Doctor doctor = new Doctor(firstname, lastname, email, gender, speciality);
            Boolean operationState = dao.DoctorDAO.create(doctor);
            if (operationState) {
                clearDoctorFields();
                readDoctors();
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
   
    public void readDoctors() {
        ObservableList<Doctor> doctorList = DoctorDAO.read();
        coIdDoc.setCellValueFactory(new PropertyValueFactory<Doctor, Integer>("id"));
        coFirstnameDoc.setCellValueFactory(new PropertyValueFactory<Doctor, String>("firstname"));
        coLastnameDoc.setCellValueFactory(new PropertyValueFactory<Doctor, String>("lastname"));
        coEmailDoc.setCellValueFactory(new PropertyValueFactory<Doctor, String>("email"));
        coGenderDoc.setCellValueFactory(new PropertyValueFactory<Doctor, String>("gender"));
        coSpeciality.setCellValueFactory(new PropertyValueFactory<Doctor, String>("speciality"));
        tableDoctors.setItems(doctorList);
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
            clearPatientFields();
            readPatients();
        } else {
            setLblError(Color.TOMATO, "Operation failed, Please try again.");
            emptyLblAfterDelay();
        }
    }

    @FXML
    public void updateDoctor(MouseEvent event) {
        int indexOfSelectedRow = tableDoctors.getSelectionModel().getSelectedIndex();
        int id = Integer.parseInt(String.valueOf(tableDoctors.getItems().get(indexOfSelectedRow).getId()));
        String firstname = txtFirstnameDoc.getText();
        String lastname = txtLastnameDoc.getText();
        String email = txtEmailDoc.getText();
        String gender = comboGenderDoc.getValue();
        String speciality = comboSpeciality.getValue();
        Doctor doctor = new Doctor(id, firstname, lastname, email, gender, speciality);
        Boolean operationState = DoctorDAO.update(doctor);
        if (operationState) {
            clearDoctorFields();
            readDoctors();
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
    public void deleteDoctor(MouseEvent event) {
        int indexOfSelectedRow = tableDoctors.getSelectionModel().getSelectedIndex();
        int id = Integer.parseInt(String.valueOf(tableDoctors.getItems().get(indexOfSelectedRow).getId()));
        boolean operationState = DoctorDAO.delete(id);
        if (operationState) {
            readDoctors();
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

    @FXML
    public void printAllDoctors(MouseEvent event) {
        ArrayList<Doctor> listDoctor = new ArrayList<>(DoctorDAO.read());
        FileManager.saveAllDoctors(listDoctor);
    }
    
    public void setLblError(Color color, String text) {
       lblErrors.setTextFill(color);
       lblErrors.setText(text);
       System.out.println(text);
    }
    
    public void clearPatientFields() {
        txtFirstname.setText("");
        txtLastname.setText("");
        txtEmail.setText("");
        comboGender.setValue(null);
        comboBloodType.setValue(null);    
    }
    
    public void clearDoctorFields() {
        txtFirstnameDoc.setText("");
        txtLastnameDoc.setText("");
        txtEmailDoc.setText("");
        comboGender.setValue(null);
        comboSpeciality.setValue(null);    
    }
    
    public void emptyLblAfterDelay() {
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> setLblError(Color.TRANSPARENT, ""));
        pause.play();
    }

}
