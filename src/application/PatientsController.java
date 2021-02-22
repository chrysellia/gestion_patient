package application;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.models.Database;
import application.models.Patient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PatientsController {
	
	private List<Patient> listPatients;
	private Patient editPatient = null;

	@FXML private TextField txtNom;
    @FXML private TextField txtPrenom;
    @FXML private TextField txtDateNaissance;
    @FXML private TextField txtAdresse;
    @FXML private TextField txtTelephone;
    @FXML private TextField txtMedecin;
    @FXML private TextField txtMaladie;

    @FXML private Button btnAdd;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;

    @FXML private TableView<Patient> tbView; 
    @FXML private TableColumn<Patient, Integer> colId;
    @FXML private TableColumn<Patient, String> colNom;
    @FXML private TableColumn<Patient, String> colPrenom;   
    @FXML private TableColumn<Patient, Date> colDateNaissance;
    @FXML private TableColumn<Patient, String> colAdresse;
    @FXML private TableColumn<Patient, String> colTelephone;
 
    private Action action;
    
    public ResultSet getAllPatient() {
    	System.out.println("Bonjour");
    	Database db = new Database();
    	db.connect();
    	ResultSet rs = db.execute("SELECT * FROM patients");
		return rs;
    }
    
    public void initialize() {
		action = new Action("ADD");
		
		btnAdd.setOnAction(addHandler);
		btnEdit.setOnAction(updateHandler);
		btnDelete.setOnAction(deleteHandler);
		
		initTable();
		
		tbView.getSelectionModel().selectedItemProperty().addListener((object, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		        fillEdit(newSelection);
		    }
		}); 
	}
    
    private void initTable() {
		colId.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("id"));
		colNom.setCellValueFactory(new PropertyValueFactory<Patient, String>("nom"));
		colPrenom.setCellValueFactory(new PropertyValueFactory<Patient, String>("prenom"));
		colAdresse.setCellValueFactory(new PropertyValueFactory<Patient, String>("adresse"));
		colTelephone.setCellValueFactory(new PropertyValueFactory<Patient, String>("telephone"));
		// colDateNaissance.setCellValueFactory(cellData -> cellData.getValue().getDateNaissance());
        
		listPatients = new ArrayList<Patient>();
		
		ResultSet rs = this.getAllPatient();
		
		try {
			while(rs.next()) {
				int id  = rs.getInt("id");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String adresse = rs.getString("adresse");
				String telephone = rs.getString("telephone");
				
				Patient patient = new Patient();
				patient.setId(id);
				patient.setNom(nom);
				patient.setPrenom(prenom);
				patient.setAdresse(adresse);
				patient.setTelephone(telephone);
				
				listPatients.add(patient);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tbView.getItems().setAll(listPatients);
	}
    
    public void insertPatient() {
		Database db = new Database();
		db.connect();
		String nomValue = txtNom.getText();
		String prenomValue = txtPrenom.getText();
		String telephoneValue = txtTelephone.getText();
		String adresseValue = txtAdresse.getText();
		String sql = "INSERT INTO patients (nom, prenom, adresse, telephone) VALUES ('" + nomValue + "', '" + prenomValue + "','" + adresseValue +"', '" + telephoneValue +"')";
		db.update(sql);
		
		this.initTable();
	}
    
    public void updatePatient(int id) {
		Database db = new Database();
		db.connect();
		
		String nomValue = txtNom.getText();
		String prenomValue = txtPrenom.getText();
		String telephoneValue = txtTelephone.getText();
		String adresseValue = txtAdresse.getText();
		
		String sql = "UPDATE patients SET nom='" + nomValue + "', prenom='" + prenomValue + "', adresse='" + adresseValue +"', telephone='" + telephoneValue +"' WHERE id = " + id + " LIMIT 1";
		db.update(sql);
		
		this.action.setType("ADD");
		
		this.initTable();
		this.refreshAction();
	}
    
    public void deletePatient(int id) {
		Database db = new Database();
		db.connect();
		
		String sql = "DELETE FROM patients WHERE id = " + id + " LIMIT 1";
		db.update(sql);
		
		this.action.setType("ADD");
		
		this.initTable();
		this.refreshAction();
	}
    
    private void fillEdit(Patient patient) {
		txtNom.setText(patient.getNom());
		txtPrenom.setText(patient.getPrenom());
		txtAdresse.setText(patient.getAdresse());
		txtTelephone.setText(patient.getTelephone());

		
		this.action.setType("EDIT");
		this.refreshAction();
		
		this.editPatient = patient;
	}
    
    private void refreshAction() {
		String action_type = this.action.getType();
		
		if (action_type == "ADD") {
			btnAdd.setDisable(false);
			btnEdit.setDisable(true);
			btnDelete.setDisable(true);
			
			txtNom.setText("");
			txtPrenom.setText("");
			txtAdresse.setText("");
			txtTelephone.setText("");
		} else if (action_type == "EDIT") {
			btnAdd.setDisable(true);
			btnEdit.setDisable(false);
			btnDelete.setDisable(false);
		}
	}
    
    EventHandler<ActionEvent> addHandler = new EventHandler<ActionEvent>() {
		@Override
    	public void handle(ActionEvent event) {
			insertPatient();
		}
	};
	
	EventHandler<ActionEvent> updateHandler = new EventHandler<ActionEvent>() {
		@Override
    	public void handle(ActionEvent event) {
			if (editPatient != null) {
				int id = editPatient.getId();
				updatePatient(id);
			}
		}
	};
	
	EventHandler<ActionEvent> deleteHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			if (editPatient != null) {
				int id = editPatient.getId();
				deletePatient(id);
			}
		}
	};
	
	class Action {
		private String type;
		
		public Action(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}

}