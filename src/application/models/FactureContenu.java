package application.models;

public class FactureContenu {
	private Integer id;
	private String designation;
	private Integer medicamentId;
	private String prixUnitaire;
	private String quantite;
	private String sousTotal;
	private String posologie;
	
	public FactureContenu() {}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDesignation() {
		return designation;
	}
	
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	public String getPrixUnitaire() {
		return prixUnitaire;
	}
	
	public void setPrixUnitaire(String prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}
	
	public String getQuantite() {
		return quantite;
	}
	
	public void setQuantite(String quantite) {
		this.quantite = quantite;
	}
	
	public String getSousTotal() {
		return sousTotal;
	}
	
	public void setSousTotal(String sousTotal) {
		this.sousTotal = sousTotal;
	}

	public String getPosologie() {
		return posologie;
	}

	public void setPosologie(String posologie) {
		this.posologie = posologie;
	}

	public Integer getMedicamentId() {
		return medicamentId;
	}

	public void setMedicamentId(Integer medicamentId) {
		this.medicamentId = medicamentId;
	}

	@Override
	public String toString() {
		return "FactureContenu [id=" + id + ", designation=" + designation + ", prixUnitaire=" + prixUnitaire
				+ ", quantite=" + quantite + ", sous_total=" + sousTotal + ", posologie=" + posologie+", medicamentId=" + medicamentId+"]";
	}
	
	
}

