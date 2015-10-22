package modele;

import java.util.ArrayList;

public class ListeRegles {

	public ArrayList<Regle> elements;
	
	public ListeRegles() {
		
		this.elements = new ArrayList<Regle>();
	}
	
	public ArrayList<Regle> getElements() {
		
		return this.elements;
		
	}
	
	public void setElements(ArrayList<Regle> elements) {
		
		this.elements = elements;
		
	}
	
	public void ajouterElement(Regle regle, boolean fin) {
		
		int position = 0;
		if (fin) position = elements.size();
		
		elements.add(position, regle);
		
	}
	
	public void ajouterElement(Regle regle, int position) {
		
		elements.add(position, regle);
		
	}
	
	public String getCondition(int numero) {

		String valeur = elements.get(numero).getCondition();
		
		return valeur;

	}
	
	public String getCotisationDeLaRegle(int numero) {

		String valeur = elements.get(numero).getCotisation();
		
		return valeur;

	}

	public int nombreElements() {
		
		return elements.size();
		
	}

	public void supprimerElement(int numero) {
		
		elements.remove(numero);
		
	}
	
}
