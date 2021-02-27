package application;

import java.sql.*;
import java.util.*;

import application.MedicamentsController.Action;

import application.models.Database;
import application.models.FactureContenu;
import application.models.Medecin;
import application.models.Medicament;
import application.models.Consultation;
import application.models.Patient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

public class NewConsultationController {
	
	private ArrayList<Patient> listPatients = new ArrayList<Patient>();
	private ArrayList<Medecin> listMedecins = new ArrayList<Medecin>();
	private ArrayList<Consultation> listConsultations = new ArrayList<Consultation>();
    
    private Patient selectedPatient;
	private Medecin selectedMedecin;
	
	@FXML private Label lblNomPatient;
	@FXML private Label lblNomMedecin;
	
    @FXML private TableView<Patient> tbPatient;

    @FXML private TableColumn<Patient, Integer> colIdPatient;
    @FXML private TableColumn<Patient, String> colPatient;

    @FXML private TableView<Medecin> tbMedecin;

    @FXML private TableColumn<Medecin, Integer> colIdMedecin;
    @FXML private TableColumn<Medecin, String> colMedecin;

    @FXML private TextArea txtObservation;
    @FXML private Button btnEnregistrer;

    @FXML private TableView<Consultation> tbConsultation;
    @FXML private TableColumn<Consultation, Integer> colIdConsultation;
    @FXML private TableColumn<Consultation, String> colPatientConsultation;
    @FXML private TableColumn<Consultation, String> colMedecinConsultation;
    @FXML private TableColumn<Consultation, String> colDateConsultation;
    @FXML private TableColumn<Consultation, String> colObservationConsultation; 
    
    public ResultSet getAllPatients() {
    	Database db = new Database();
    	db.connect();
    	ResultSet rs = db.execute("SELECT * from `patients` LEFT JOIN consultations ON consultations.patient_id = patients.id WHERE consultations.id IS NULL");
    	return rs;
    }
    
    public ResultSet getAllMedecins() {
    	Database db = new Database();
    	db.connect();
    	ResultSet rs1 = db.execute("SELECT * FROM `medecins` LEFT JOIN consultations ON consultations.medecin_id = medecins.id WHERE consultations.id IS NULL");
    	return rs1;
    }
    
    public ResultSet getAllConsultations() {
    	Database db = new Database();
    	db.connect();
    	ResultSet rs = db.execute("SELECT \r\n" + 
    			"	consultations.id,\r\n" + 
    			"   CONCAT(patients.nom, \" \", patients.prenom) AS patient,\r\n" + 
    			"   medecins.nom AS medecin,\r\n" + 
    			"   DATE_FORMAT(consultations.dateConsultation, \"%d/%m/%Y\") AS dateConsultation,\r\n" + 
    			"   consultations.observations\r\n" + 
    			"FROM `consultations`\r\n" + 
    			"INNER JOIN patients ON patients.id = consultations.patient_id\r\n" + 
    			"INNER JOIN medecins ON medecins.id = consultations.medecin_id");
    	
    	return rs;
    }
    
    public void initialize() {
    	initTable();
    	
    	tbPatient.getSelectionModel().selectedItemProperty().addListener((object, oldSelection, newSelection) -> {
			if (newSelection != null) {
		        selectPatient(newSelection);
		    }
		});
    	
    	tbMedecin.getSelectionModel().selectedItemProperty().addListener((object, oldSelection, newSelection) -> {
			if (newSelection != null) {
		        selectMedecin(newSelection);
		    }
		});
    	
    	btnEnregistrer.setOnAction(enregistrerHandler);
    	btnEnregistrer.setDisable(true);
    }
    
    EventHandler<ActionEvent> enregistrerHandler = new EventHandler<ActionEvent>() {
		@Override
    	public void handle(ActionEvent event) {
			saveConsultation();
		}
	};
    
    private void initTablePatient () {
    	colIdPatient.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("id"));
    	colPatient.setCellValueFactory(new PropertyValueFactory<Patient, String>("nom"));
    	listPatients = new ArrayList<Patient>();
    	ResultSet rs = this.getAllPatients();

    	try {
			while(rs.next()) {
				int id  = rs.getInt("id");
				String prenom = rs.getString("prenom");

				Patient patient = new Patient();
				patient.setId(id);
				patient.setNom(prenom);
	
				
				listPatients.add(patient);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	tbPatient.getItems().setAll(listPatients);
    }
    
    private void initTableMedecin() {
    	colIdMedecin.setCellValueFactory(new PropertyValueFactory<Medecin, Integer>("id"));
    	colMedecin.setCellValueFactory(new PropertyValueFactory<Medecin, String>("nom"));
    	listMedecins = new ArrayList<Medecin>();
    	ResultSet rs1 = this.getAllMedecins();
    	
    	try {
			while(rs1.next()) {
				int id  = rs1.getInt("id");
				String nom = rs1.getString("nom");

				Medecin medecin = new Medecin();
				medecin.setId(id);
				medecin.setNom(nom);
	
				
				listMedecins.add(medecin);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	tbMedecin.getItems().setAll(listMedecins);
    }
    
    private void initTableConsultation() {
        colIdConsultation.setCellValueFactory(new PropertyValueFactory<Consultation, Integer>("id"));
        colPatientConsultation.setCellValueFactory(new PropertyValueFactory<Consultation, String>("patient"));
        colMedecinConsultation.setCellValueFactory(new PropertyValueFactory<Consultation, String>("medecin"));
        colDateConsultation.setCellValueFactory(new PropertyValueFactory<Consultation, String>("dateConsultation"));
        colObservationConsultation.setCellValueFactory(new PropertyValueFactory<Consultation, String>("observations"));
    	
        listConsultations = new ArrayList<Consultation>();
    	ResultSet rs = this.getAllConsultations();
    	
    	try {
			while(rs.next()) {
				int id  = rs.getInt("id");
				String patient = rs.getString("patient");
				String medecin = rs.getString("medecin");
				String dateConsultation = rs.getString("dateConsultation");
				String observations = rs.getString("observations");
				
				Consultation consultation = new Consultation();
				consultation.setId(id);
				consultation.setPatient(patient);
				consultation.setMedecin(medecin);
				consultation.setDateConsultation(dateConsultation);
				consultation.setObservations(observations);
				
				listConsultations.add(consultation);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	tbConsultation.getItems().setAll(listConsultations);
    }
    
    public void initTable() {
    	this.initTablePatient();
    	this.initTableMedecin();
    	this.initTableConsultation();
    }
    
    private void selectPatient(Patient patient) {
    	this.selectedPatient = patient;
    	lblNomPatient.setText("Patient : " + patient.getNom());
    	System.out.println(txtObservation.getText());
    	this.checkConsultation();
    }
    
    private void selectMedecin(Medecin medecin) {
    	this.selectedMedecin = medecin;
    	lblNomMedecin.setText("Médecin : " + medecin.getNom());
    	System.out.println(txtObservation.getText());
    	this.checkConsultation();
    }
    
    private void saveConsultation() {
		Database db = new Database();
		db.connect();
		String sql_consultation = "INSERT INTO consultations (medecin_id, patient_id, observations) VALUES ('" + this.selectedMedecin.getId() + "', '" + this.selectedPatient.getId() + "', '" + txtObservation.getText() + "')";
		
		System.out.println(sql_consultation);
		
		db.update(sql_consultation);
		
		this.initTable();
		
    	this.refreshAction();
	}
    
    private void checkConsultation() {
    	if (this.selectedPatient == null || this.selectedMedecin.getId() == null || (this.txtObservation == null || this.txtObservation.getText() == "")) {
    		this.btnEnregistrer.setDisable(true);
    	} else {
    		this.btnEnregistrer.setDisable(false);
    	}
    }
    
    private void refreshAction() {
		btnEnregistrer.setDisable(false);
		
		lblNomPatient.setText("patient: N/A");
		lblNomMedecin.setText("medecin: N/A");
		txtObservation.setText("");
	}

}
