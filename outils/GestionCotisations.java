package outils;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import modele.Cotisation;
import modele.ListeCotisations;

public class GestionCotisations {

	private String premierElement, libelleCourant, cheminDuFichierXML;
	private int nombreDeCotisationsDansLeFichier;
	private NodeList listeDesCotisations, listeDesNoeudsLibelle, listeDesNoeudsTaux, listeDesNoeudsStatut, testExistanceBalisePourFichierVide;
	private Cotisation cotisationCourante;
	private boolean existance, fichierActuellementVide;
	private Element racine, elementRacineDuFichier, tagCotisation, tagTaux, tagLibelle;
	private ListeCotisations resultatListeCotisations;

	// Variables pour la création de fichiers XML
	private File fichierXML;
	private DocumentBuilderFactory fabrique;
	private DocumentBuilder constructeur;
	private Document document;
	private TransformerFactory transformerFactory;
	private Transformer transformer;
	private DOMSource source;
	private StreamResult result;
	private boolean trouve;
	
	public GestionCotisations(String cheminDuFichierXML) {
		
		this.cheminDuFichierXML = cheminDuFichierXML;
	
		fichierXML = new File(cheminDuFichierXML);

		fabrique = DocumentBuilderFactory.newInstance();

		try {
			
			constructeur = fabrique.newDocumentBuilder();
			
		} catch (ParserConfigurationException e) {
			
			e.printStackTrace();
			
		}

		try {
				
			document = constructeur.parse(fichierXML);
				
		} catch (Exception e) {
				
			e.printStackTrace();
				
		}
	
		racine = document.getDocumentElement();
		premierElement = racine.getNodeName();
		
		resultatListeCotisations = new ListeCotisations();
		
		if(premierElement.equals("cotisations")) {

			listeDesCotisations = document.getElementsByTagName("cotisation");
			nombreDeCotisationsDansLeFichier = listeDesCotisations.getLength();
				
			listeDesNoeudsLibelle = document.getElementsByTagName("libelle");
			listeDesNoeudsTaux = document.getElementsByTagName("taux");
	
			for (int i = 0; i < nombreDeCotisationsDansLeFichier; i++) {
	
		        cotisationCourante = new Cotisation(listeDesNoeudsLibelle.item(i).getTextContent(), Double.parseDouble(listeDesNoeudsTaux.item(i).getTextContent()));
	
				resultatListeCotisations.ajouterElement(cotisationCourante, i);
	
			}
		
		}
		else resultatListeCotisations = null;

	}
	
	public ListeCotisations ListeCotisations() {
		
		return resultatListeCotisations;
		
	}

	public void ModifierCotisation(int numero, Cotisation cotisationAajouter) {

		/*String libelleDeDepart = document.getElementsByTagName("libelle").item(numero).getTextContent();
		String tauxDeDepart = document.getElementsByTagName("taux").item(numero).getTextContent();
		String statutDeDepart = document.getElementsByTagName("statut").item(numero).getTextContent();*/

		//if(this.CotisationExiste(cotisationAajouter.getLibelle()) && !cotisationAajouter.getLibelle().equals(libelleDeDepart)) {
	
			// On supprime la cotisation
			SupprimerCotisation(numero);
			
			// On la rajoute à nouveau
			EcrireCotisations(cotisationAajouter);

			FinaliserInsertion();

		/*}
		else {
			
			JOptionPane.showMessageDialog(null, "La cotisation existe déjà");
			
		}*/
		
	}
	
	public void SupprimerCotisation(int numero) {
		
		Node node = listeDesCotisations.item(numero);

		node.getParentNode().removeChild(node);

		FinaliserInsertion();

	}

	public void EcrireCotisations(Cotisation cotisationAajouter) {
		
		fichierActuellementVide = false;
		
		String cotisation = cotisationAajouter.getLibelle();
		double tauxDouble = cotisationAajouter.getTaux();
		String tauxString = String.valueOf(tauxDouble);

		// On tente de récuperer la balise "cotisations"
		try {

			testExistanceBalisePourFichierVide = document.getElementsByTagName("cotisations");
				
		} catch (Exception e) {
				
			fichierActuellementVide = true;
				
		}
			
		// Si le fichier est vide de base, on recrée tout sinon on lit ce qui y a deja
		if(!fichierActuellementVide) elementRacineDuFichier = document.getDocumentElement();

		else {
				
			document = constructeur.newDocument();
			elementRacineDuFichier = document.createElement("cotisations");
			document.appendChild(elementRacineDuFichier);
				
		}

		tagCotisation = document.createElement("cotisation");
		elementRacineDuFichier.appendChild(tagCotisation);
			
		tagLibelle = document.createElement("libelle");
		tagLibelle.setTextContent(cotisation);
		tagCotisation.appendChild(tagLibelle);
			
		tagTaux = document.createElement("taux");
		tagTaux.setTextContent(tauxString);
		tagCotisation.appendChild(tagTaux);
			
		FinaliserInsertion();

	}
	
	public boolean CotisationExiste(String libelle) {
		
		existance = false;
		int i = 0;
		
		while(i < resultatListeCotisations.nombreElements() && existance == false) {
			
			listeDesNoeudsLibelle = document.getElementsByTagName("libelle");
            libelleCourant = listeDesNoeudsLibelle.item(i).getChildNodes().item(0).getNodeValue();

			if(libelle.equals(libelleCourant)) existance = true;
			
			i++;
			
		}
		
		return existance;
		
	}
	
	private void FinaliserInsertion() {
		
		transformerFactory = TransformerFactory.newInstance();
		
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
		
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		source = new DOMSource(document);
		result = new StreamResult(cheminDuFichierXML);

		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		
	}
	
	public double RecupererTauxCotisation(String libelle) {

		trouve = false;
		int i = 0;
		double retour = 0;
		
		while(i < resultatListeCotisations.nombreElements() && trouve == false) {
			
			listeDesNoeudsLibelle = document.getElementsByTagName("libelle");
            libelleCourant = listeDesNoeudsLibelle.item(i).getChildNodes().item(0).getNodeValue();

				if(libelle.equals(libelleCourant)) {
					retour = Double.parseDouble(listeDesNoeudsTaux.item(i).getTextContent());
					trouve = true;
				}
			
			i++;
			
		}
		
		return retour;
		
	}
	
}
