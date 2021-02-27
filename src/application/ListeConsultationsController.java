package application;

import javafx.scene.control.Button;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

import application.holder.ConsultationHolder;
import application.models.Consultation;
import application.models.Database;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class ListeConsultationsController {
	
	private ArrayList<Consultation> listConsultations = new ArrayList<Consultation>();

	@FXML private TableView<Consultation> tblListConsultation;
	@FXML private TableColumn<Consultation, Integer> colId;
	@FXML private TableColumn<Consultation, Integer> colPatient;
	@FXML private TableColumn<Consultation, Integer> colMedecin;
	@FXML private TableColumn<Consultation, Date> colDateConsultation;
	@FXML private TableColumn<Consultation, String> colObservations;
	@FXML private Button btnAddFacture;
	@FXML private Button btnPrint;
	
	private Consultation selectedConsultation;
	
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
    			"   DATE_FORMAT(consultations.dateConsultation, \"%d/%m/%Y\") AS dateConsultation,\r\n" + 
    			"   consultations.observations\r\n" + 
    			"FROM `consultations`\r\n" + 
    			"INNER JOIN patients ON patients.id = consultations.patient_id\r\n" + 
    			"INNER JOIN medecins ON medecins.id = consultations.medecin_id");
    	
    	return rs;
	}
	
	public void initialize() {
		initTable();
		btnAddFacture.setDisable(true);
		btnPrint.setDisable(true);
		btnAddFacture.setOnAction(this.addHandler);
		btnPrint.setOnAction(this.printHandler);
		
		tblListConsultation.getSelectionModel().selectedItemProperty().addListener((object, oldSelection, newSelection) -> {
			if (newSelection != null) {
				selectConsultation(newSelection);
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
    	
    	tblListConsultation.getItems().setAll(listConsultations);
	}

    private void createFacture() {

        ConsultationHolder cHolder = ConsultationHolder.getInstance();
        cHolder.setConsultation(this.selectedConsultation);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/Facture.fxml"));
        Parent root;
        
        try {
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private void printFacture() {
    	//Creating PDF document object 
        PDDocument document = new PDDocument();   
        

    	//Creating a blank page 
    	PDPage blankPage = new PDPage();

    	//Adding the blank page to the document
    	document.addPage( blankPage );
         
        //Saving the document
        try {
        	String pdf_name = this.selectedConsultation.getId() + "_facture" +  this.selectedConsultation.getPatient() + ".pdf";
        	//File file = new File(pdf_name);

	        //Retrieving the pages of the document 
	        PDPage page = document.getPage(0);
	        PDPageContentStream contentStream = new PDPageContentStream(document, page);
	        
	        //Begin the Content stream 
	        contentStream.beginText(); 
	        
	        //Setting the font to the Content stream  
	        contentStream.setFont(PDType1Font.COURIER, 12);
	        
	        //Setting the position for the line 
	        contentStream.newLineAtOffset(25, 725);
	        
	        String text = "FACTURE DU CLIENT";
	        contentStream.newLine(); 
	        String nom_medecin = "Nom du médecin";
	        contentStream.newLine(); 
	        String nom_patient = "Nom du patient";

	        //Adding text in the form of string 
	        contentStream.showText(text);  
	        contentStream.showText(nom_medecin);  
	        contentStream.showText(nom_patient);  
	        
	        //Ending the content stream
	        contentStream.endText();
	        
	        //Closing the content stream
	        contentStream.close();
	        
	        //Creating the PDDocumentInformation object 
	        PDDocumentInformation pdd = document.getDocumentInformation();
	        
	        // Setting the title of the document
	        pdd.setTitle("FACTURE " +  this.selectedConsultation.getPatient()); 
	        
	        document.save("./pdf/" + pdf_name);
	        
	        //Closing the document  
	        document.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	EventHandler<ActionEvent> addHandler = new EventHandler<ActionEvent>() {
		@Override
    	public void handle(ActionEvent event) {
			createFacture();
		}
	};
	
	EventHandler<ActionEvent> printHandler = new EventHandler<ActionEvent>() {
		@Override
    	public void handle(ActionEvent event) {
			printFacture();
		}
	};

	
	private void selectConsultation(Consultation consultation) {
		this.selectedConsultation = consultation;
		this.btnAddFacture.setDisable(false);
		this.btnPrint.setDisable(false);
	}
}
