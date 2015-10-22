package modele;

public class SalaireEmploye {

	private String nom, prenom, date, statut;
	private double brut, net;

	public SalaireEmploye(String nom, String prenom, String statut, String date, double brut,
			double net) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.statut = statut;
		this.date = date;
		this.brut = brut;
		this.net = net;
	}
	
	public String getNom() {
		return nom;
	}
	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getBrut() {
		return brut;
	}
	public void setBrut(double brut) {
		this.brut = brut;
	}
	public double getNet() {
		return net;
	}
	public void setNet(double net) {
		this.net = net;
	}
	
	
	
}
