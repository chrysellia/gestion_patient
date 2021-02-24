package application;

import javafx.scene.control.Button;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.models.Consultation;
import application.models.Database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;

public class ListeConsultationsController {
	
	private ArrayList<Consultation> listConsultations = new ArrayList<Consultation>();
	@FXML
	private TableView<Consultation> tbView4;
	@FXML
	private TableColumn<Consultation, Integer> colId;
	@FXML
	private TableColumn<Consultation, Integer> colPatient;
	@FXML
	private TableColumn<Consultation, Integer> colMedecin;
	@FXML
	private TableColumn<Consultation, Date> colDateConsultation;
	@FXML
	private TableColumn<Consultation, String> colObservations;
	@FXML
    private Button addFacture;
	
	private Consultation selectedConsultation;
	
	@FXML
    void handleButtonAction(javafx.event.ActionEvent mouseEvent) {
		if(mouseEvent.getSource() == addFacture ) {
			loadStage("/application/Facture.fxml");
		}
    }
	
	private void loadStage(String fxml) {
		try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private void loadStage(String fxml, Consultation consultation) {
		try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = new Stage();
            
            if (consultation.getId() > 0) {
            	
            }
            
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private ResultSet getAllConsultations() {
		Database db = new Database();
    	db.connect();
    	ResultSet rs = db.execute("SELECT \r\n" + 
    			"	consultations.id,\r\n" + 
    			"   CONCAT(patients.nom, \" \", patients.prenom) AS patient,\r\n" + 
    			"   medecins.nom AS medecin,\r\n" + 
    			"   consultations.dateConsultation,\r\n" + 
    			"   consultations.observations\r\n" + 
    			"FROM `consultations`\r\n" + 
    			"INNER JOIN patients ON patients.id = consultations.patient_id\r\n" + 
    			"INNER JOIN medecins ON medecins.id = consultations.medecin_id");
    	
    	return rs;
	}
	
	public void initialize() {
		initTable();
		addFacture.setDisable(true);
		
		tbView4.getSelectionModel().selectedItemProperty().addListener((object, oldSelection, newSelection) -> {
			if (newSelection != null) {
				fillEditConsultation(newSelection);
		    }
		});
	}
	
	private void initTable() {
		colId.setCellValueFactory(new PropertyValueFactory<Consultation, Integer>("id"));
		colPatient.setCellValueFactory(new PropertyValueFactory<Consultation, Integer>("patient"));
		colMedecin.setCellValueFactory(new PropertyValueFactory<Consultation, Integer>("medecin"));
		colDateConsultation.setCellValueFactory(new PropertyValueFactory<Consultation, Date>("dateConsultation"));
		colObservations.setCellValueFactory(new PropertyValueFactory<Consultation, String>("observations"));
	
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
    	
    	tbView4.getItems().setAll(listConsultations);
	}
	
	private void fillEditConsultation(Consultation consultation) {
		this.selectedConsultation = consultation;
		this.addFacture.setDisable(false);
		System.out.println(this.selectedConsultation.toString());
	}
}
