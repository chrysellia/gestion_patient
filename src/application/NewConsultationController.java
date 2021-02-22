package application;

import java.awt.TextArea;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.MedicamentsController.Action;
import application.models.Database;
import application.models.Medecin;
import application.models.NewConsultation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class NewConsultationController {
	
	private List<NewConsultation> listNewConsultation;
	private Action action;
	private NewConsultation editConsultation = null;
	
	private List<String> patientList;
	private List<String> medecinList;

    @FXML private TextField txtPatient;
    @FXML private TextField txtMedecin;
    @FXML private TextField txtConsultation;
    @FXML private TextArea txtObservation;

    @FXML private ChoiceBox<String> selectPatient;
    @FXML private ChoiceBox<String> selectMedecin;
    @FXML private Button btnEnregistrer;

    @FXML private TableView<NewConsultation> tbConsultation;
    @FXML private TableColumn<NewConsultation, Integer> colId;
    @FXML private TableColumn<NewConsultation, String> colPatient;
    @FXML private TableColumn<NewConsultation, String> colMedecin;
    @FXML private TableColumn<NewConsultation, String> colConsultation;
    @FXML private TableColumn<NewConsultation, String> colObservations; 


    public ResultSet getAllConsultations() {
    	Database db = new Database();
    	db.connect();
    	ResultSet rs = db.execute("SELECT * FROM consultations");
		return rs;
    }
    
    public ResultSet fillChoiceBox() {
    	Database db = new Database();
    	db.connect();
    	String sql_patient = "SELECT prenom FROM patients";
    	
    	return db.execute(sql_patient);
    }
    
    public ResultSet setChoiceBox() {
    	Database db = new Database();
    	db.connect();
    	String sql_medecin = "SELECT nom FROM medecins";
    	return db.execute(sql_medecin);
    }
    
    public void initialize() {
    	ResultSet res_patient = this.fillChoiceBox();
    	ResultSet res_medecin = this.setChoiceBox();
    	
    	try {
			while(res_patient.next()) {
				String prenom  = res_patient.getString("prenom");
				
				selectPatient.getItems().add(prenom);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	try {
			while(res_medecin.next()) {
				String nom = res_medecin.getString("nom");
				
				selectMedecin.getItems().add(nom);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		action = new Action("ADD");	
		btnEnregistrer.setOnAction(addHandler);
		initTable();
		tbConsultation.getSelectionModel().selectedItemProperty().addListener((object, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		        fillEdit(newSelection);
		    }
		}); 
	}
    
    public void insertConsultation() {
		Database db = new Database();
		db.connect();
		String patientValue = txtPatient.getText();
		String medecinValue = txtMedecin.getText();
		String observationValue = txtObservation.getText();
		String sql = "INSERT INTO consultations (patient, medecin, observations) VALUES ('" + patientValue + "','" + medecinValue +"', '" + observationValue +"')";
		db.update(sql);
		
		this.initTable();
	}

    private void initTable() {
		colId.setCellValueFactory(new PropertyValueFactory<NewConsultation, Integer>("id"));
		colPatient.setCellValueFactory(new PropertyValueFactory<NewConsultation, String>("patient"));
		colMedecin.setCellValueFactory(new PropertyValueFactory<NewConsultation, String>("medecin"));
		colObservations.setCellValueFactory(new PropertyValueFactory<NewConsultation, String>("observations"));
		
		listNewConsultation = new ArrayList<NewConsultation>();
		
		ResultSet rs = this.getAllConsultations();
		
		try {
			while(rs.next()) {
				int id  = rs.getInt("id");
				String patient = rs.getString("patient");
				String medecin = rs.getString("medecin");
				String observations = rs.getString("observations");
				
				NewConsultation consultation = new NewConsultation();
				consultation.setId(id);
				consultation.setPatient(patient);
				consultation.setMedecin(medecin);
				consultation.setObservations(observations);
				
				listNewConsultation.add(consultation);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tbConsultation.getItems().setAll(listNewConsultation);
	}
	    
    private void fillEdit(NewConsultation newConsultation) {
    	txtPatient.setText(newConsultation.getPatient());
    	txtMedecin.setText(newConsultation.getMedecin());
    	txtObservation.setText(newConsultation.getObservations());

		this.action.setType("EDIT");
		
		this.editConsultation = newConsultation;
	}
    
    
    EventHandler<ActionEvent> addHandler = new EventHandler<ActionEvent>() {
		@Override
    	public void handle(ActionEvent event) {
			insertConsultation();
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
