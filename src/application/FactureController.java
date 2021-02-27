package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.sql.*;

import application.PatientsController.Action;
import application.holder.ConsultationHolder;
import application.models.Consultation;
import application.models.Database;
import application.models.Facture;
import application.models.FactureContenu;
import application.models.Medecin;
import application.models.Medicament;
import application.models.Patient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FactureController {

	private Action action;
	private ArrayList<Medicament> listMedicaments = new ArrayList<Medicament>();
	private Facture editFacture = null;
	
	@FXML private TableView<FactureContenu> tbFacture;
	
    @FXML private TableView<Medicament> tbMedicament;
    
    private Medicament selectedMedicament;
    private FactureContenu selectedItem;
    private ArrayList<FactureContenu> lignesFacture = new ArrayList<FactureContenu>();
    
    @FXML private TableColumn<FactureContenu, String> colDesignation;
    @FXML private TableColumn<FactureContenu, String> colQuantite;
    @FXML private TableColumn<FactureContenu, String> colPrixUnitaire;
    @FXML private TableColumn<FactureContenu, String> colTotal;
    @FXML private TableColumn<FactureContenu, Integer> colIdFacture;
    @FXML private TableColumn<FactureContenu, String> colPosologie;
    
    
    @FXML private Label textTotal;
    @FXML private Button btnValidate;

    @FXML private Button btnDelete;
    @FXML private Button btnAdd;
    @FXML private TextField fieldQuantite;
    @FXML private Label lblPatient;
    @FXML private TextArea txtPosologie;

    @FXML private TableColumn<Medicament, String> colDesignationMedicament;
    @FXML private TableColumn<Medicament, String> colPrixMedicament;
    
    
    public void initialize() {
		initTable();
		
		ConsultationHolder cHolder = ConsultationHolder.getInstance();
        Consultation currentConsultation = cHolder.getConsultation();
		
		lblPatient.setText("Nom du patient : " + currentConsultation.getPatient());
		//		 " / Médecin traitant : " + currentConsultation.getMedecin()
		
		btnAdd.setOnAction(addHandler);
		btnDelete.setDisable(true);
		btnValidate.setOnAction(saveHandler);
		btnDelete.setOnAction(deleteHandler);
		//btnDelete.setOnAction(deleteHandler);
		
		tbMedicament.getSelectionModel().selectedItemProperty().addListener((object, oldSelection, newSelection) -> {
			if (newSelection != null) {
		        fillEdit(newSelection);
		    }
		});
		
		tbFacture.getSelectionModel().selectedItemProperty().addListener((object, oldSelection, newSelection) -> {
			if (newSelection != null) {
				selectItem(newSelection);
		    }
		});
    }

    
    private ResultSet getAllMedicaments() {
    	Database db = new Database();
    	db.connect();
    	ResultSet rs = db.execute("SELECT * FROM medicaments");
    	return rs;
    }
    
    private void initTable() {
    	// Initialisation table facture contenu
    	colDesignation.setCellValueFactory(new PropertyValueFactory<FactureContenu, String>("designation"));
    	colQuantite.setCellValueFactory(new PropertyValueFactory<FactureContenu, String>("quantite"));
    	colPrixUnitaire.setCellValueFactory(new PropertyValueFactory<FactureContenu, String>("prixUnitaire"));
    	colTotal.setCellValueFactory(new PropertyValueFactory<FactureContenu, String>("sousTotal"));
    	colPosologie.setCellValueFactory(new PropertyValueFactory<FactureContenu, String>("posologie"));
    	
    	// Initialisation table medicament
    	colDesignationMedicament.setCellValueFactory(new PropertyValueFactory<Medicament, String>("designation"));
		colPrixMedicament.setCellValueFactory(new PropertyValueFactory<Medicament, String>("prix"));
        
		listMedicaments = new ArrayList<Medicament>();
		
		ResultSet rs = this.getAllMedicaments();
		
		try {
			while(rs.next()) {
				int id  = rs.getInt("id");
				String designation = rs.getString("designation");
				String prix = rs.getString("prix");

				
				Medicament medicament = new Medicament();
				medicament.setId(id);
				medicament.setDesignation(designation);
				medicament.setPrix(prix);
				
				listMedicaments.add(medicament);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tbMedicament.getItems().setAll(listMedicaments);
	}
        	
	private void fillEdit(Medicament medicament) {
		this.selectedMedicament = medicament;
	}
	
	private void selectItem(FactureContenu factureContenu) {
		this.selectedItem = factureContenu;
		this.btnDelete.setDisable(false);
	}
	
	private void addItem() {
		//Prendre les éléments du tableau médicament
		FactureContenu itemFacture = new FactureContenu();
		String designation = this.selectedMedicament.getDesignation();
		itemFacture.setDesignation(designation);
		int prix = Integer.parseInt(this.selectedMedicament.getPrix());
		int medicamentId = this.selectedMedicament.getId();
		itemFacture.setMedicamentId(medicamentId);
		int quantite = Integer.parseInt(fieldQuantite.getText());
		itemFacture.setQuantite("" + quantite);
		String posologie = txtPosologie.getText();
		itemFacture.setPosologie("" + posologie);
		
		//Ajouter des éléments dans le tableau facture
		itemFacture.setPrixUnitaire("" + prix);
		int sousTotal = prix * quantite;
		itemFacture.setSousTotal("" + sousTotal);

		try {
			this.lignesFacture.add(itemFacture);
		} catch (Exception er) {
			System.out.println("error : " + er.getMessage());
		}
		
		// System.out.println("test : " + this.lignesFacture.toString());
		
		
		tbFacture.getItems().setAll(lignesFacture);
		
		this.totalMedicament();
		
		this.refreshAction();
	}
	
	public int totalMedicament() {
		int total = 0;

		for (int i = 0; i < lignesFacture.size(); i++) {
			FactureContenu item = lignesFacture.get(i);
			System.out.println(item);
			int sous_total = Integer.parseInt(item.getSousTotal());
			total += sous_total;
		}
		
		textTotal.setText(total + " Ar");
		
		return total;
	}
	
	
	public void saveFacture() {
		// 1- Insertion de la facture qui va contenir le détail général
		int lastInsertId = 0;
		try {
			Database db = new Database();
			ConsultationHolder cHolder = ConsultationHolder.getInstance();
			Consultation currentConsultation = cHolder.getConsultation();
			String sql_facture = "INSERT INTO factures (consultation_id, montant_total) VALUES(' " + currentConsultation.getId()  + " ', '" + this.totalMedicament() + "')";
			db.connect();
			String columns[] = new String[] {"id"};
			
			Statement stmt = db.prepare(sql_facture, columns);
			if (stmt.executeUpdate(sql_facture, Statement.RETURN_GENERATED_KEYS) > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				
				if ( rs.next() ) {
					lastInsertId = rs.getInt(1);
	            }
				
				System.out.println("lastInsertId " + lastInsertId);
			}
			
			
		} catch (Exception er) {
			System.out.println("error : " + er.getMessage());
		}
		
		// 2- Insertion de chaque médicament prescrit avec la designation, le prix, la quantite, sous-total et le prix unitaire
		try {
			for (int i = 0; i < lignesFacture.size(); i++) {
				FactureContenu item = lignesFacture.get(i);
				
				Database db = new Database();
				String sql_contenu = "INSERT INTO facture_contenus (facture_id, designation, medicamentId, quantite, prixUnitaire, sousTotal, posologie) VALUES ('" + lastInsertId +"','" + item.getDesignation() +"', '" + item.getMedicamentId() +"', '" + item.getQuantite() +"','" + item.getSousTotal() + "', '" + item.getPrixUnitaire() + "', '" + item.getPosologie()+"')";
				db.connect();
				ResultSet res = db.update(sql_contenu);
				System.out.println(sql_contenu);
			}
			
		} catch(Exception er) {
			System.out.println("error :" + er.getMessage());
		}
		this.action.setType("ADD");
		this.refreshAction();
	}
	
	public void deleteFacture(int id) {
		Database db = new Database();
		db.connect();
		
		String sql = "DELETE FROM facture_contenus WHERE id = " + id + " LIMIT 1";
		db.update(sql);
		
		this.action.setType("ADD");
		
		this.initTable();
		this.refreshAction();
	}
	
	private void refreshAction() {
		String action_type = this.action.getType();
		
		if (action_type == "ADD") {
			btnAdd.setDisable(false);
			btnDelete.setDisable(true);
			
			colDesignation.setText("");
			colPrixUnitaire.setText("");
			colQuantite.setText("");
			colTotal.setText("");
			colPosologie.setText("");
		} else if (action_type == "EDIT") {
			btnAdd.setDisable(true);
			btnDelete.setDisable(false);
		}
	}
	
	private void deleteItem() {
		int index = this.lignesFacture.indexOf(this.selectedItem);
		this.lignesFacture.remove(index);
		
		this.tbFacture.getItems().setAll(this.lignesFacture);
		this.btnDelete.setDisable(true);
		this.totalMedicament();
	}
	
	EventHandler<ActionEvent> addHandler = new EventHandler<ActionEvent>() {
		@Override
    	public void handle(ActionEvent event) {
			try {
				addItem();
			} catch(Exception er) {
				System.out.println("" + er.getMessage());
			}
			
		}
	};
	
	EventHandler<ActionEvent> deleteHandler = new EventHandler<ActionEvent>() {
		@Override
    	public void handle(ActionEvent event) {
			deleteItem();
		}
	};
	
	EventHandler<ActionEvent> saveHandler = new EventHandler<ActionEvent>() {
		@Override
    	public void handle(ActionEvent event) {
			try {
				saveFacture();
			} catch(Exception er) {
				System.out.println("" + er.getMessage());
			}
			
		}
	};
}
