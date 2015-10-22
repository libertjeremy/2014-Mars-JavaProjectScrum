package modele;

import java.util.ArrayList;
import java.util.List;

import modele.Cotisation;

public class ListeCotisations {

	public ArrayList<Cotisation> elements;
	
	public ListeCotisations() {
		
		this.elements = new ArrayList<Cotisation>();
	}
	
	public List<Cotisation> getElements() {
		
		return this.elements;
		
	}
	
	public void setElements(ArrayList<Cotisation> elements) {
		
		this.elements = elements;
		
	}
	
	public void ajouterElement(Cotisation cotis, boolean fin) {
		
		int position = 0;
		if (fin) position = elements.size();
		
		elements.add(position, cotis);
		
	}
	
	public void ajouterElement(Cotisation cotis, int position) {
		
		elements.add(position, cotis);
		
	}

	public double getTauxCotisation(int numero) {

		double valeur = elements.get(numero).getTaux();
		
		return valeur;

	}
	
	
	public String getLibelleCotisation(int numero) {

		String valeur = elements.get(numero).getLibelle();
		
		return valeur;

	}

	public int nombreElements() {
		
		return elements.size();
		
	}

	public void supprimerElement(int numero) {

		elements.remove(numero);
		
	}
	
}
