package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField tfpseudo;

    @FXML
    private TextField tfmdp;

    @FXML
    private Button logbtn;

    @FXML
    void loginButton(ActionEvent event) throws Exception {
    	System.out.println("pseudo" + tfpseudo.getText());
    	System.out.println("password" + tfmdp.getText());
    	
    	if(tfpseudo.getText().equals("admin") && tfmdp.getText().equals("1234")) {
    		Stage primaryStage = new Stage();
    		AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Accueil.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Plateforme de gestion de patient");
			primaryStage.show();
			
    	} 
    }

}
