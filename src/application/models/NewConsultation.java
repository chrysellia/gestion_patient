package application.models;

import java.sql.Date;

public class NewConsultation {
	private int id;
	private String patient;
	private String medecin;
	private String date_consultation;
	private String observations;
	
	public NewConsultation() {}
	
	public NewConsultation(int id, String patient, String medecin, String date_consultation, String observations) {
		this.id = id;
		this.patient = patient;
		this.medecin = medecin;
		this.date_consultation = date_consultation;
		this.observations = observations;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) {
		this.patient = patient;
	}

	public String getMedecin() {
		return medecin;
	}

	public void setMedecin(String medecin) {
		this.medecin = medecin;
	}

	public String getDate_consultation() {
		return date_consultation;
	}
	
	public void setDate_consultation(String date_consultation) {
		this.date_consultation = date_consultation;
		
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	
	
	
}
