package application.models;

import java.util.Date;

public class ListeConsultations {
	private Integer id;
	private Integer medecin_id;
	private Integer patient_id;
	private Date date_consultation;
	
	public ListeConsultations(Integer id, Integer medecin_id, Integer patient_id, Date date_consultation) {
		super();
		this.id = id;
		this.medecin_id = medecin_id;
		this.patient_id = patient_id;
		this.date_consultation = date_consultation;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMedecin_id() {
		return medecin_id;
	}

	public void setMedecin_id(Integer medecin_id) {
		this.medecin_id = medecin_id;
	}

	public Integer getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(Integer patient_id) {
		this.patient_id = patient_id;
	}

	public Date getDate_consultation() {
		return date_consultation;
	}

	public void setDate_Consultation(Date date_consultation) {
		this.date_consultation = date_consultation;
	}
}
