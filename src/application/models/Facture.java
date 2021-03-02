package application.models;

import java.util.Date;

public class Facture {
	private Integer factureId;
	private String medecin;
	private String patient;
	private Date dateConsultation;
	private String montantTotal;
	
	public Facture( ) {}
	
	public Integer getFactureId() {
		return factureId;
	}
	
	public void setFactureId(Integer factureId) {
		this.factureId = factureId;
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
	
	public Date getDateConsultation() {
		return dateConsultation;
	}
	
	public void setDateConsultation(Date dateConsultation) {
		this.dateConsultation = dateConsultation;
	}
	
	public String getMontantTotal() {
		return montantTotal;
	}
	
	public void setMontantTotal(String montantTotal) {
		this.montantTotal = montantTotal;
	}

	@Override
	public String toString() {
		return "Facture [factureId=" + factureId + ", medecin=" + medecin + ", patient=" + patient
				+ ", dateConsultation=" + dateConsultation + ", montantTotal=" + montantTotal + "]";
	}
}

