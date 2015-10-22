package modele;

import javax.swing.JOptionPane;

public class Regle {

	String condition;
	String cotisation;

	public Regle(String condition, String cotisation) {
		
		this.setCondition(condition);
		this.setCotisation(cotisation);
		
	}
	
	public Regle() {
		
		this.condition = "";
		
	}
	
	public void setCotisation(String cotisation) {

		this.cotisation = cotisation;
	}
	
	public void setCondition(String contenu) {

		this.condition = contenu.trim();
		
	}
	
	public boolean isSplit(String expression) {
		
		String[] conditionAction = expression.split("/");
		
		return conditionAction.length==2;
	}
	
	public boolean isStatut(String expression) {
	
		expression = expression.replaceAll("\\s", "").toLowerCase();
		return expression.equals("cadre") || expression.equals("noncadre");
	
	}
	
	public boolean isInteger(String expression) {
		
		try {
			
	        Integer.parseInt(expression);
	        return true;
	        
	    }
	    catch(NumberFormatException nfe) {
	    	
	        return false;
	        
	    }
	}
	
	public boolean isDouble(String expression) {
		
		try {
			
	        Double.parseDouble(expression);
	        return true;
	        
	    }
	    catch(NumberFormatException nfe) {
	    	
	        return false;
	        
	    }
	}

	public boolean isCondition(String expression) {

		boolean retour = true;
		
		if(!expression.equals("toujours")) {
		
			String[] chaine = expression.split(" AND ");
			
			int i = 0;
		
			while(i < chaine.length && retour != false) {
				
				String[] condition = chaine[i].replaceAll("\\s", "").split("[<>=]");

				if( condition.length == 2 && !isInteger(condition[0]) && !isDouble(condition[0]) ) {
					
					String partieGauche = condition[0];
					String partieDroite = condition[1];
					
					int posPremierGuillemet = partieDroite.indexOf('"');
					int posDeuxiemeGuillemet = partieDroite.indexOf('"', posPremierGuillemet + 1);
					
					if(posPremierGuillemet > -1 && posDeuxiemeGuillemet > -1 && partieGauche.equals("statut")) retour = isStatut(partieDroite.substring(posPremierGuillemet+1, posDeuxiemeGuillemet));

					else retour = (isInteger(partieDroite) || isDouble(partieDroite)) && partieGauche.equals("brut");

				}
				else retour = false;

				i++;
				
			}
		
		}
		
		return retour;
		
    }

	public String getCondition() {

		return this.condition;
		
	}

	public String getCotisation() {
		
		return this.cotisation;
		
	}

}




