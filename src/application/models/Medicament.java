package application.models;

public class Medicament {
	private int id;
	private String nom;
	private String designation;
	private Double prix;
	
	public Medicament() {}
	
	public Medicament(int id, String nom, String designation, Double prix) {
		super();
		this.id = id;
		this.nom = nom;
		this.designation = designation;
		this.prix = prix;
	}

	public Integer getId() {
		return id;
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

	public Double getPrix() {
		return prix;
	}

	public void setPrix(Double prix) {
		this.prix = prix;
	}

	
	
}
