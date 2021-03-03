package application;

import application.models.Database;
import application.models.Facture;
import application.models.FactureContenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.EventHandler;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class ListeFacturesController {

	private ArrayList<Facture> listFactures = new ArrayList<Facture>();
	
	private Facture selectedFacture;
	
	@FXML private TableView<Facture> tbListeFacture;

    @FXML private Button btnPrintFacture;

    @FXML private TableColumn<Facture, Integer> factureId;

    @FXML private TableColumn<Facture, String> colMedecin;

    @FXML private TableColumn<Facture, String> colPatient;

    @FXML private TableColumn<Facture, Date> colDateConsultation;

    @FXML private TableColumn<Facture, String> colMontantTotal;
    
    private ResultSet getAllFactures() {
    	Database db = new Database();
    	db.connect();
    	ResultSet rs = db.execute("SELECT\r\n" + 
    			"	factures.id,\r\n" + 
    			"    consultations.dateConsultation,\r\n" + 
    			"    medecins.nom AS medecin,\r\n" + 
    			"    CONCAT(patients.nom, \" \", patients.prenom) AS patient, \r\n" + 
    			"    factures.montantTotal\r\n" + 
    			"FROM factures\r\n" + 
    			"INNER JOIN consultations ON consultations.id = factures.consultation_id\r\n" + 
    			"INNER JOIN medecins ON medecins.id = consultations.medecin_id\r\n" + 
    			"INNER JOIN patients ON patients.id = consultations.patient_id");
		return rs;
    }
    
    private ResultSet getLigneFacture(int factureId) {
    	Database db = new Database();
    	db.connect();
    	ResultSet rs = db.execute("SELECT * FROM `facture_contenus` WHERE facture_id = " + factureId);
		return rs;
    }
    
    public void initialize() {
    	initTable();
    	this.btnPrintFacture.setDisable(true);
    	this.btnPrintFacture.setOnAction(this.printHandler);
    	
    	tbListeFacture.getSelectionModel().selectedItemProperty().addListener((object, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	selectFacture(newSelection);
		    }
		});
    }
    
    private void selectFacture(Facture facture) {
    	this.selectedFacture = facture;
    	this.btnPrintFacture.setDisable(false);
    	System.out.println("facture" + facture.toString());
    }
    
    private void initTable() {
    	factureId.setCellValueFactory(new PropertyValueFactory<Facture, Integer>("factureId"));
    	factureId.setStyle( "-fx-alignment: CENTER;");
    	colMedecin.setCellValueFactory(new PropertyValueFactory<Facture, String>("medecin"));
    	colPatient.setCellValueFactory(new PropertyValueFactory<Facture, String>("patient"));
    	colDateConsultation.setCellValueFactory(new PropertyValueFactory<Facture, Date>("dateConsultation"));
    	colDateConsultation.setStyle( "-fx-alignment: CENTER;");
    	colMontantTotal.setCellValueFactory(new PropertyValueFactory<Facture, String>("montantTotal"));
    	colMontantTotal.setStyle( "-fx-alignment: CENTER-RIGHT;");
    	
    	listFactures = new ArrayList<Facture>();
    	ResultSet rs = this.getAllFactures();
    	
    	try {
			while(rs.next()) {
				int factureId  = rs.getInt("id");
				String medecin = rs.getString("medecin");
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
    
    private void printFacture() {       
        //Saving the document
        try {
        	//Loading an existing document
            File file = new File("C:/PdfBox_Examples/my_pdf.pdf");
            PDDocument document = new PDDocument(); 
          //Creating a blank page 
        	PDPage blankPage = new PDPage();
            document.addPage( blankPage );
             
            //Creating a PDF Document
            PDPage page = document.getPage(0);  
             
            PDPageContentStream contentStream = new PDPageContentStream(document, page); 
             
            //Begin the Content stream 
            contentStream.beginText(); 
             
            //Setting the font to the Content stream
            contentStream.setFont( PDType1Font.TIMES_ROMAN, 16 );
             
            //Setting the leading
            contentStream.setLeading(14.5f);

            //Setting the position for the line
            contentStream.newLineAtOffset(25, 725);

            //String text1 = this.selectedFacture.getFactureId(); 
            String text1 = "FACTURE";
            String text2 = this.selectedFacture.getPatient();
            String text3 = this.selectedFacture.getMedecin();
            Date text4 = this.selectedFacture.getDateConsultation();
            String text5 = this.selectedFacture.getMontantTotal();
            String text6 = "Centre Hospitalier Universitaire Befelatanana";

            //Adding text in the form of string
            contentStream.showText(text1);
            contentStream.newLine();
            contentStream.showText("Nom du patient : " + text2);
            contentStream.newLine();
            contentStream.showText("Nom du médecin : " + text3);
            contentStream.newLine();
            contentStream.showText("Date de consultation: " + text4);
            
            ResultSet rsLigneFacture = this.getLigneFacture(this.selectedFacture.getFactureId());
            
            try {
    			while(rsLigneFacture.next()) {
    				int id  = rsLigneFacture.getInt("id");
    				String designation = rsLigneFacture.getString("designation");
    				// int medicamentId = rsLigneFacture.getInt("medicamentId");
    				// String posologie = rsLigneFacture.getString("posologie");
    				String quantite = rsLigneFacture.getString("quantite");
    				// String prixUnitaire = rsLigneFacture.getString("prixUnitaire");
    				String sousTotal = rsLigneFacture.getString("sousTotal");
    				
    				contentStream.newLine();
    	            contentStream.showText(designation + " x " + quantite + " " + sousTotal);
    			}
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            
            contentStream.newLine();
            contentStream.showText("Montant total : " + text5);
            contentStream.newLine();
            contentStream.showText("Lieu : " + text6);
            //Ending the content stream
            contentStream.endText();

            System.out.println("Content added");

            //Closing the content stream
            contentStream.close();

            //Saving the document
            document.save(new File("C:/PdfBox_Examples/new.pdf"));
                  
            //Closing the document
            document.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    EventHandler<ActionEvent> printHandler = new EventHandler<ActionEvent>() {
	@Override
		public void handle(ActionEvent event) {
			printFacture();
		}
    };
}