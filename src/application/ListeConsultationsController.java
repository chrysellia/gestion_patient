package application;

import java.sql.Date;

import javafx.fxml.FXML;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class ListeConsultationsController {
	@FXML
	private TableView<ListeConsultations> tbView4;
	@FXML
	private TableColumn<ListeConsultations, Integer> colId;
	@FXML
	private TableColumn<ListeConsultations, String> colPatient;
	@FXML
	private TableColumn<ListeConsultations, String> colMedecin;
	@FXML
	private TableColumn<ListeConsultations, Date> colDateConsultation;
	@FXML
	private TableColumn<ListeConsultations, String> colObservations;
	@FXML
	private TableColumn<ListeConsultations, Double> colMontant;

}
