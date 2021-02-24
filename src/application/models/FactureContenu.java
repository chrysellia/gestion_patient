package application.models;

public class FactureContenu {
	private Integer id;
	private String designation;
	private String prixUnitaire;
	private String quantite;
	private String sousTotal;
	
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

	@Override
	public String toString() {
		return "FactureContenu [id=" + id + ", designation=" + designation + ", prixUnitaire=" + prixUnitaire
				+ ", quantite=" + quantite + ", sous_total=" + sousTotal + "]";
	}
	
	
}

