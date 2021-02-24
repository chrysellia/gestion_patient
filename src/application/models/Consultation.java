package application.models;

import java.sql.Date;

public class Consultation {
	private int id;
	private int patient_id;
	private int medecin_id;
	private String medecin;
	private String patient;
	private String dateConsultation;
	
	public String getDateConsultation() {
		return dateConsultation;
	}

	public void setDateConsultation(String dateConsultation) {
		this.dateConsultation = dateConsultation;
	}

	public String getMedecin() {
		return medecin;
	}

	public void setMedecin(String medecin) {
		this.medecin = medecin;
	}

	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) {
		this.patient = patient;
	}

	private String date_consultation;
	private String observations;
	
	public Consultation() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(int patient_id) {
		this.patient_id = patient_id;
	}

	public int getMedecin_id() {
		return medecin_id;
	}

	public void setMedecin_id(int medecin_id) {
		this.medecin_id = medecin_id;
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

	@Override
	public String toString() {
		return "Consultation [id=" + id + ", patient_id=" + patient_id + ", medecin_id=" + medecin_id + ", medecin="
				+ medecin + ", patient=" + patient + ", dateConsultation=" + dateConsultation + ", date_consultation="
				+ date_consultation + ", observations=" + observations + "]";
	}
}
