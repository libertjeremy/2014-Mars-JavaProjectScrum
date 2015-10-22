package outils;

public class VerificationSalaire {

	private String statut, condition, contenuTemp, typeCondition, partieGauche, partieDroite;
	private String[] conditionSepareeParAnd, conditionAtester;
	private int positionSuperieur, positionInferieur, positionEgal;
	private double brut;
	private boolean resultat;

	public VerificationSalaire(double brut, String statut, String condition) {
		
		this.brut = brut;
		this.statut = statut;
		this.condition = condition;
		conditionSepareeParAnd = condition.split(" AND ");
		
	}

	public boolean Verification() { 

		resultat = true;
		
		if(conditionSepareeParAnd.length > 0 && !condition.toLowerCase().equals("toujours")) {

			int i = 0;
			
			while(i < conditionSepareeParAnd.length && resultat) {
			
				contenuTemp = conditionSepareeParAnd[i].replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("\\s", "");

				positionSuperieur = contenuTemp.indexOf('>');
				positionInferieur = contenuTemp.indexOf('<');
				positionEgal = contenuTemp.indexOf('=');
	
				typeCondition = null;
				
				if(positionEgal > 0) typeCondition = "=";
				else if(positionInferieur > 0) typeCondition = "<";
				else if(positionSuperieur > 0) typeCondition = ">";
				
				conditionAtester = contenuTemp.split("[<>=]");
				
				partieGauche = conditionAtester[0];
				partieDroite = conditionAtester[1];
	
				if(partieGauche.equals("brut")) {
	
					if(typeCondition.equals("=")) resultat = Double.parseDouble(partieDroite) == brut;

					else if(typeCondition.equals("<")) resultat = brut < Double.parseDouble(partieDroite);

					else if(typeCondition.equals(">")) resultat = brut > Double.parseDouble(partieDroite);

					else resultat = false;
					
				}
				else if(partieGauche.equals("statut")) resultat = statut.toLowerCase().replaceAll("\\s", "").equals(partieDroite.replaceAll("\"", "").toLowerCase());

				else resultat = false;

				i++;
				
			}
		
		}
		
		return resultat;

	}
	
}
