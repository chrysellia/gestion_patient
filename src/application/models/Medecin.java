package application.models;

public class Medecin {
	private int id;
	private String nom;
	private String telephone;
	private String categorie;
	private String etat;

	public Medecin() {}
	
	public Medecin(int id, String nom, String telephone, String categorie, String etat) {
		super();
		this.id = id;
		this.nom = nom;
		this.telephone = telephone;
		this.categorie = categorie;
		this.etat = etat;
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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie (String categorie) {
		this.categorie = categorie;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}
}
