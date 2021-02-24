package application;

import java.awt.TextArea;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.MedicamentsController.Action;
import application.models.Database;
import application.models.Medecin;
import application.models.Medicament;
import application.models.NewConsultation;
import application.models.Patient;
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
	
	private ArrayList<Patient> listPatients = new ArrayList<Patient>();
	private ArrayList<Medecin> listMedecins = new ArrayList<Medecin>();
	
    @FXML
    private TableView<Patient> tbPatient;

    @FXML
    private TableColumn<Patient, String> colPatient;

    @FXML
    private TableView<Medecin> tbMedecin;

    @FXML
    private TableColumn<Medecin, String> colMedecin;

    @FXML
    private TextArea txtObservation;

    @FXML private Button btnEnregistrer;

    @FXML private TableView<NewConsultation> tbConsultation;
    @FXML private TableColumn<NewConsultation, Integer> colId;
    @FXML private TableColumn<NewConsultation, String> colPatientConsultation;
    @FXML private TableColumn<NewConsultation, String> colMedecinConsultation;
    @FXML private TableColumn<NewConsultation, String> colConsultation;
    @FXML private TableColumn<NewConsultation, String> colObservations; 

    @FXML
    void saveConsultation(ActionEvent event) {

    }
    
    public ResultSet getAllPatients() {
    	Database db = new Database();
    	db.connect();
    	ResultSet rs = db.execute("SELECT * from patients");
    	return rs;
    }
    
    public ResultSet getAllMedecins() {
    	Database db = new Database();
    	db.connect();
    	ResultSet rs1 = db.execute("SELECT * from medecins");
    	return rs1;
    }
    
    public void initialize() {
    	initTable();
    }
    
    public void initTable() {
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
}
