package application;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.ArrayList;
import java.util.List;

import application.PatientsController.Action;
import application.models.Database;
import application.models.Medecin;
import application.models.Patient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class MedecinsController {
	
	private List<Medecin> listMedecins;
	private Action action;
	private Medecin editMedecin = null;
	
	@FXML private TextField txtNom;
	@FXML private TextField txtTelephone;
	@FXML private TextField txtCategorie;
	
	@FXML private Button btnAdd;
	@FXML private Button btnEdit;
	@FXML private Button btnDelete;
	
	@FXML private TableView<Medecin> tblMedecin;
	@FXML private TableColumn<Medecin, Integer> colId;
	@FXML private TableColumn<Medecin, String> colNom;
	@FXML private TableColumn<Medecin, String> colTelephone;
	@FXML private TableColumn<Medecin, String> colCategorie;
	@FXML private TableColumn<Medecin, String> colTotalConsultation;
	
	public ResultSet getAllMedecin() {
    	Database db = new Database();
    	db.connect();
    	ResultSet rs = db.execute("SELECT "
    			+ "*, "
    			+ "(SELECT COUNT(consultations.id) FROM consultations WHERE consultations.medecin_id = medecins.id LIMIT 1) AS totalConsultation "
    			+ "FROM `medecins`");
		return rs;
    }
	
	public void initialize() {
		action = new Action("ADD");
		
		btnAdd.setOnAction(addHandler);
		btnEdit.setOnAction(updateHandler);
		btnDelete.setOnAction(deleteHandler);
		
		initTable();
		
		tblMedecin.getSelectionModel().selectedItemProperty().addListener((object, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		        fillEdit(newSelection);
		    }
		}); 
	}
	
	private void initTable() {
		colId.setCellValueFactory(new PropertyValueFactory<Medecin, Integer>("id"));
		colNom.setCellValueFactory(new PropertyValueFactory<Medecin, String>("nom"));
		colCategorie.setCellValueFactory(new PropertyValueFactory<Medecin, String>("categorie"));
		colTelephone.setCellValueFactory(new PropertyValueFactory<Medecin, String>("telephone"));
		colTelephone.setCellValueFactory(new PropertyValueFactory<Medecin, String>("telephone"));
		colTotalConsultation.setCellValueFactory(new PropertyValueFactory<Medecin, String>("totalConsultation"));
		
		listMedecins = new ArrayList<Medecin>();
		
		ResultSet rs = this.getAllMedecin();
		
		try {
			while(rs.next()) {
				int id  = rs.getInt("id");
				String nom = rs.getString("nom");
				String categorie = rs.getString("categorie");
				String telephone = rs.getString("telephone");
				String totalConsultation = rs.getString("totalConsultation");
				
				Medecin medecin = new Medecin();
				medecin.setId(id);
				medecin.setNom(nom);
				medecin.setCategorie(categorie);
				medecin.setTelephone(telephone);
				medecin.setTotalConsultation(totalConsultation);
				
				listMedecins.add(medecin);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tblMedecin.getItems().setAll(listMedecins);
	}
	
	 public void insertMedecin() {
			Database db = new Database();
			db.connect();
			String nomValue = txtNom.getText();
			String telephoneValue = txtTelephone.getText();
			String categorieValue = txtCategorie.getText();
			String sql = "INSERT INTO medecins (nom, categorie, telephone) VALUES ('" + nomValue + "','" + categorieValue +"', '" + telephoneValue +"')";
			db.update(sql);
			
			this.initTable();
			this.refreshAction();
		}
	    
	    public void updateMedecin(int id) {
			Database db = new Database();
			db.connect();
			
			String nomValue = txtNom.getText();
			String telephoneValue = txtTelephone.getText();
			String categorieValue = txtCategorie.getText();
			
			String sql = "UPDATE medecins SET nom='" + nomValue + "', categorie='" + categorieValue +"', telephone='" + telephoneValue +"' WHERE id = " + id + " LIMIT 1";
			db.update(sql);
			
			this.action.setType("ADD");
			
			this.initTable();
			this.refreshAction();
		}
	    
	    public void deleteMedecin(int id) {
			Database db = new Database();
			db.connect();
			
			String sql = "DELETE FROM medecins WHERE id = " + id + " LIMIT 1";
			db.update(sql);
			
			this.action.setType("ADD");
			
			this.initTable();
			this.refreshAction();
		}
	    
	    private void fillEdit(Medecin medecin) {
			txtNom.setText(medecin.getNom());
			txtCategorie.setText(medecin.getCategorie());
			txtTelephone.setText(medecin.getTelephone());
			
			this.action.setType("EDIT");
			this.refreshAction();
			
			this.editMedecin = medecin;
		}
	    
	    private void refreshAction() {
			String action_type = this.action.getType();
			
			if (action_type == "ADD") {
				btnAdd.setDisable(false);
				btnEdit.setDisable(true);
				btnDelete.setDisable(true);
				
				txtNom.setText("");
				txtCategorie.setText("");
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
				insertMedecin();
			}
		};
		
		EventHandler<ActionEvent> updateHandler = new EventHandler<ActionEvent>() {
			@Override
	    	public void handle(ActionEvent event) {
				if (editMedecin != null) {
					int id = editMedecin.getId();
					updateMedecin(id);
				}
			}
		};
		
		EventHandler<ActionEvent> deleteHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (editMedecin != null) {
					int id = editMedecin.getId();
					deleteMedecin(id);
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
