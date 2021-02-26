package application.models;

public class Medecin {
	private int id;
	private String nom;
	private String telephone;
	private String categorie;
	private String totalConsultation;

	public Medecin() {}
	
	public Medecin(int id, String nom, String telephone, String categorie, String totalConsultation) {
		super();
		this.id = id;
		this.nom = nom;
		this.telephone = telephone;
		this.categorie = categorie;
		this.totalConsultation = totalConsultation;
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

	public String getTotalConsultation() {
		return totalConsultation;
	}

	public void setTotalConsultation(String totalConsultation) {
		this.totalConsultation = totalConsultation;
	}
}
