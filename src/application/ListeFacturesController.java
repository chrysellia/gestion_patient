package application;

import application.models.Consultation;
import application.models.Database;
import application.models.Facture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class ListeFacturesController {

	private ArrayList<Facture> listFactures = new ArrayList<Facture>();
	
	@FXML
	private TableView<Facture> tbListeFacture;

    @FXML
    private Button btnPrintFacture;

    @FXML
    private TableColumn<Facture, Integer> factureId;

    @FXML
    private TableColumn<Facture, String> colMedecin;

    @FXML
    private TableColumn<Facture, String> colPatient;

    @FXML
    private TableColumn<Facture, Date> colDateConsultation;

    @FXML
    private TableColumn<Facture, String> colMontantTotal;

    @FXML
    void handeActionButton(ActionEvent event) {

    }
    
    private ResultSet getAllFactures() {
    	Database db = new Database();
    	db.connect();
    	ResultSet rs = db.execute("SELECT * FROM factures");
		return rs;
    }
    
    private void initialize() {
    	initTable();
    }
    
    private void initTable() {
    	factureId.setCellValueFactory(new PropertyValueFactory<Facture, Integer>("id"));
    	colMedecin.setCellValueFactory(new PropertyValueFactory<Facture, String>("medecin"));
    	colPatient.setCellValueFactory(new PropertyValueFactory<Facture, String>("patient"));
    	colDateConsultation.setCellValueFactory(new PropertyValueFactory<Facture, Date>("dateConsultation"));
    	colMontantTotal.setCellValueFactory(new PropertyValueFactory<Facture, String>("medecin"));
    	
    	listFactures = new ArrayList<Facture>();
    	ResultSet rs = this.getAllFactures();
    	
    	try {
			while(rs.next()) {
				int factureId  = rs.getInt("factureId");
				String medecin = rs.getString("patient");
				String patient = rs.getString("patient");
				Date dateConsultation = rs.getDate("dateConsultation");
				String montantTotal = rs.getString("montantTotal");
				
				Facture facture = new Facture();
				facture.setFactureId(factureId);
				facture.setPatient(patient);
				facture.setMedecin(medecin);
				facture.setDateConsultation(dateConsultation);
				facture.setMontantTotal(montantTotal);
				
				listFactures.add(facture);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	tbListeFacture.getItems().setAll(listFactures);
    }
}