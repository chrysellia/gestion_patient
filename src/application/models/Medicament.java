package application.models;

public class Medicament {
	private int id;
	private String designation;
	private String prix;
	
	public Medicament() {}
	
	public Medicament(int id, String nom, String designation, String prix) {
		super();
		this.id = id;
		this.designation = designation;
		this.prix = prix;
	}

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

	public String getPrix() {
		return prix;
	}

	public void setPrix(String prix) {
		// TODO Auto-generated method stub
		this.prix = prix;
	}	
	
}
