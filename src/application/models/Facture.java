package application.models;

public class Facture {
	private Integer id;
	private String nom;
	private String designation;
	private String prix;
	private String total;
	private String quantite;
	
	public Facture(Integer id, String nom, String designation, String prix, String total, String quantite) {
		super();
		this.id = id;
		this.nom = nom;
		this.designation = designation;
		this.prix = prix;
		this.total = total;
		this.quantite = quantite;
	}

	public String getId() {
		return nom;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getPrix() {
		return prix;
	}

	public void setPrix(String prix) {
		this.prix = prix;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getQuantite() {
		return quantite;
	}

	public void setQuantite(String quantite) {
		this.quantite = quantite;
	}
	
	
	
}

