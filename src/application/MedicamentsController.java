package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.MedecinsController.Action;
import application.models.Database;
import application.models.Medicament;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class MedicamentsController {
	
	private List<Medicament> listMedicament;
	private Action action;
	private Medicament editMedicament = null;
	
	@FXML
	private TextField txtNom;
	@FXML
	private TextField txtDesignation;
	@FXML
	private TextField txtPrix;
	@FXML
	private Button btnAdd;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnDelete;
	@FXML
	private TableView<Medicament> tbView3;
	@FXML
	private TableColumn<Medicament, Integer> colId;
	@FXML
	private TableColumn<Medicament, String> colNom;
	@FXML
	private TableColumn<Medicament, String> colDesignation;
	@FXML
	private TableColumn<Medicament, Double> colPrix;
    
    public ResultSet getAllMedicaments() {
    	Database db = new Database();
    	db.connect();
    	ResultSet rs = db.execute("SELECT * FROM medicaments");
		return rs;
    }
	
	public void initialize() {
		action = new Action("ADD");
		
		btnAdd.setOnAction(addHandler);
		btnEdit.setOnAction(updateHandler);
		btnDelete.setOnAction(deleteHandler);
		
		initTable();
		
		tbView3.getSelectionModel().selectedItemProperty().addListener((object, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		        fillEdit(newSelection);
		    }
		}); 
	}
	
	private void initTable() {
		colId.setCellValueFactory(new PropertyValueFactory<Medicament, Integer>("id"));
		colNom.setCellValueFactory(new PropertyValueFactory<Medicament, String>("nom"));
		colDesignation.setCellValueFactory(new PropertyValueFactory<Medicament, String>("designation"));
		colPrix.setCellValueFactory(new PropertyValueFactory<Medicament, Double>("prix"));;
		
		listMedicament = new ArrayList<Medicament>();
		
		ResultSet rs = this.getAllMedicaments();
		
		try {
			while(rs.next()) {
				int id  = rs.getInt("id");
				String nom = rs.getString("nom");
				String designation = rs.getString("designation");
				double prix = rs.getDouble("prix");
				
				Medicament medicament = new Medicament();
				medicament.setId(id);
				medicament.setNom(nom);
				medicament.setDesignation(designation);
				medicament.setPrix(prix);

				
				listMedicament.add(medicament);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tbView3.getItems().setAll(listMedicament);
	}
	
	 public void insertMedicament() {
			Database db = new Database();
			db.connect();
			String nomValue = txtNom.getText();
			String designationValue = txtDesignation.getText();
			String prixValue = txtPrix.getText();
			String sql = "INSERT INTO medicaments (nom, designation,prix) VALUES ('" + nomValue + "', '" + designationValue + "','" + prixValue +"')";
			db.update(sql);
			
			this.initTable();
		}
	    
	    public void updateMedicament(int id) {
			Database db = new Database();
			db.connect();
			
			String nomValue = txtNom.getText();
			String designationValue = txtDesignation.getText();
			String prixValue = txtPrix.getText();
			
			String sql = "UPDATE medicaments SET nom='" + nomValue + "', designation='" + designationValue + "', prix='" + prixValue +"' WHERE id = " + id + " LIMIT 1";
			db.update(sql);
			
			this.action.setType("ADD");
			
			this.initTable();
			this.refreshAction();
		}
	    
	    public void deleteMedicament(int id) {
			Database db = new Database();
			db.connect();
			
			String sql = "DELETE FROM medicaments WHERE id = " + id + " LIMIT 1";
			db.update(sql);
			
			this.action.setType("ADD");
			
			this.initTable();
			this.refreshAction();
		}
	    
	    private void fillEdit(Medicament medicament) {
			txtNom.setText(medicament.getNom());
			txtDesignation.setText(medicament.getDesignation());
			txtPrix.setText(String.valueOf(medicament.getPrix()));

			this.action.setType("EDIT");
			this.refreshAction();
			
			this.editMedicament = medicament;
		}
	    
	    private void refreshAction() {
			String action_type = this.action.getType();
			
			if (action_type == "ADD") {
				btnAdd.setDisable(false);
				btnEdit.setDisable(true);
				btnDelete.setDisable(true);
				
				txtNom.setText("");
				txtDesignation.setText("");
				txtPrix.setText("");
			} else if (action_type == "EDIT") {
				btnAdd.setDisable(true);
				btnEdit.setDisable(false);
				btnDelete.setDisable(false);
			}
		}
	    
	    EventHandler<ActionEvent> addHandler = new EventHandler<ActionEvent>() {
			@Override
	    	public void handle(ActionEvent event) {
				insertMedicament();
			}
		};
		
		EventHandler<ActionEvent> updateHandler = new EventHandler<ActionEvent>() {
			@Override
	    	public void handle(ActionEvent event) {
				if (editMedicament != null) {
					int id = editMedicament.getId();
					updateMedicament(id);
				}
			}
		};
		
		EventHandler<ActionEvent> deleteHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (editMedicament != null) {
					int id = editMedicament.getId();
					deleteMedicament(id);
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
