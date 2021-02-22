package application;

public class ListeConsultations {
	private Integer id;
	private Integer medecin_id;
	private Integer patient_id;
	private Integer date_visite;
	private Double montant;
	
	public ListeConsultations(Integer id, Integer medecin_id, Integer patient_id, Integer date_visite,
			Double montant) {
		super();
		this.id = id;
		this.medecin_id = medecin_id;
		this.patient_id = patient_id;
		this.date_visite = date_visite;
		this.montant = montant;
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

	public Integer getDate_visite() {
		return date_visite;
	}

	public void setDate_visite(Integer date_visite) {
		this.date_visite = date_visite;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}
}
