package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AccueilController {
	@FXML
	private Button btnNewConsultation;
	@FXML
	private Button btnMedecins;
	@FXML
	private Button btnPatients;
	@FXML
	private Button btnConsultations;
	@FXML
	private Button btnMedicaments;
	@FXML
	private Button btnParametres;
	
	@FXML
    void handleClick(javafx.event.ActionEvent mouseEvent) {
		if(mouseEvent.getSource() == btnNewConsultation ) {
			loadStage("/application/NewConsultation.fxml");
		}
		else
		if(mouseEvent.getSource() == btnMedecins ) {
			loadStage("/application/Medecins.fxml");
		}
		else
		if(mouseEvent.getSource() == btnPatients ) {
			loadStage("/application/Patients.fxml");
		}
		else
		if(mouseEvent.getSource() == btnConsultations ) {
			loadStage("/application/ListeConsultations.fxml");
		}
		else
		if(mouseEvent.getSource() == btnMedicaments ) {
			loadStage("/application/Medicaments.fxml");
		}
		else
		if(mouseEvent.getSource() == btnParametres ) {
			loadStage("/application/Parametres.fxml");
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
}
