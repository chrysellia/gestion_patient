package application.models;

import java.sql.Date;

public class Patient {
	private int id;
	private String nom;
	private String prenom;
	private Date date_naissance;
	private String adresse;
	private String telephone;
	
	public Patient() {}
	
	public Patient(int id, String nom, String prenom, Date date_naissance, String adresse, String telephone,
			int medecin_id) {
		this.nom = nom;
		this.prenom = prenom;
		this.date_naissance = date_naissance;
		this.adresse = adresse;
		this.telephone = telephone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Date getDateNaissance() {
		return date_naissance;
	}

	public void setDateNaissance(Date date_naissance) {
		this.date_naissance = date_naissance;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
}
