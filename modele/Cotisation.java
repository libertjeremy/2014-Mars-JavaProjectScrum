package modele;

public class Cotisation {

	private String libelle;
	private double taux;
	Regle regle;
	
	public Cotisation() {
		
		this.libelle = "";
		this.taux = 0;
		
	}

	public Cotisation(String libelle, double taux) {

		regle = new Regle();
		
		if (!regle.isDouble(libelle) && !regle.isInteger(libelle)){
			
			this.libelle = libelle;
			
		} else throw new IllegalArgumentException("Le libelle de la cotisation doit être une chaine de caractères!");

		if (regle.isDouble(String.valueOf(taux)) || regle.isInteger(String.valueOf(taux))){
			
			this.taux = taux;
			
		} else throw new IllegalArgumentException("Le libelle de la cotisation doit être une chaine de caractères!");

	}
		

	public String getLibelle() {
		
		return libelle;
		
	}


	public void setLibelle(String libelle) {
		
		this.libelle = libelle;
		
	}

	public double getTaux() {
		
		return taux;
		
	}

	public void setTaux(double taux) {
		
		this.taux = taux;
		
	}

	@Override
	public String toString() {
		
		return "Cotisation \"" + libelle + "\" de taux " + taux;
		
	}

}
