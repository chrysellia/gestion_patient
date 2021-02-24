package application;

import javafx.scene.control.Button;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.models.Database;
import application.models.ListeConsultations;
import application.models.Patient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;

public class ListeConsultationsController {
	
	private ArrayList<ListeConsultations> listConsultations = new ArrayList<ListeConsultations>();
	@FXML
	private TableView<ListeConsultations> tbView4;
	@FXML
	private TableColumn<ListeConsultations, Integer> colId;
	@FXML
	private TableColumn<ListeConsultations, Integer> colPatient;
	@FXML
	private TableColumn<ListeConsultations, Integer> colMedecin;
	@FXML
	private TableColumn<ListeConsultations, Date> colDateConsultation;
	@FXML
	private TableColumn<ListeConsultations, String> colObservations;
	@FXML
    private Button addFacture;
	
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
	
	private ResultSet getAllConsultations() {
		Database db = new Database();
		db.connect();
		ResultSet rs = db.execute("SELECT * FROM consultations");
		return rs;
	}
	
	private void initialize() {
		initTable();
	}
	
	private void initTable() {
		colId.setCellValueFactory(new PropertyValueFactory<ListeConsultations, Integer>("id"));
		colPatient.setCellValueFactory(new PropertyValueFactory<ListeConsultations, Integer>("patient"));
		colMedecin.setCellValueFactory(new PropertyValueFactory<ListeConsultations, Integer>("medecin"));
		colDateConsultation.setCellValueFactory(new PropertyValueFactory<ListeConsultations, Date>("dateConsultation"));
		colObservations.setCellValueFactory(new PropertyValueFactory<ListeConsultations, String>("observations"));
	
		listConsultations = new ArrayList<ListeConsultation>();
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
}
