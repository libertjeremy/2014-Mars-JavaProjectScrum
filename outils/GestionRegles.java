package outils;

import java.io.File;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import modele.ListeRegles;
import modele.Regle;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GestionRegles {

	private File fichierXML;
	private DocumentBuilderFactory fabrique;
	private DocumentBuilder constructeur;
	private Document document;
	public String premierElement, conditionDeLaRegleAajouter, cotisationDeLaRegleAajouter,contenuCourant,cotisationCourante;
	private Element racine;
	public NodeList testExistanceBalisePourFichierVide, listeDesRegles, listeDesNoeudsContenu, listeDesNoeudsCotisation, listeDesNoeudsTaux;
	public int nombreDeReglesDansLeFichier;
	private Regle regleCourante;
	private boolean fichierActuellementVide, existance;
	private ListeRegles resultatListeRegles;
	private Element elementRacineDuFichier, tagCotisation, tagCondition, tagRegle;

	// Variables pour la création de fichiers XML
	private TransformerFactory transformerFactory;
	private Transformer transformer;
	private DOMSource source;
	private StreamResult result;
	
	private String cheminDuFichierXML;
	
	public GestionRegles(String cheminDuFichierXML) {
			
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
		
		if(premierElement.equals("regles")) {
			
			resultatListeRegles = new ListeRegles();
	
			listeDesRegles = document.getElementsByTagName("regle");
			nombreDeReglesDansLeFichier = listeDesRegles.getLength();
				
			listeDesNoeudsContenu = document.getElementsByTagName("condition");
			listeDesNoeudsCotisation = document.getElementsByTagName("cotisation");
			listeDesNoeudsTaux = document.getElementsByTagName("taux");
	
			for (int i = 0; i < nombreDeReglesDansLeFichier; i++) {
	
				regleCourante = new Regle(listeDesNoeudsContenu.item(i).getTextContent(), listeDesNoeudsCotisation.item(i).getTextContent());
					
		        resultatListeRegles.ajouterElement(regleCourante, i);
					
			}

		}
		
	}
	
	public ListeRegles ListeRegles() {
		
		return resultatListeRegles;
		
	}
	
	public void EcrireRegle(Regle regleAajouter) {
		
		fichierActuellementVide = false;
		
		conditionDeLaRegleAajouter = regleAajouter.getCondition();
		cotisationDeLaRegleAajouter = regleAajouter.getCotisation();

		try {

			testExistanceBalisePourFichierVide = document.getElementsByTagName("regles");
				
		} catch (Exception e) {
				
			fichierActuellementVide = true;
				
		}

		if(!fichierActuellementVide) elementRacineDuFichier = document.getDocumentElement();

		else {
				
			document = constructeur.newDocument();
			elementRacineDuFichier = document.createElement("regles");
			document.appendChild(elementRacineDuFichier);
				
		}

		tagRegle = document.createElement("regle");
		elementRacineDuFichier.appendChild(tagRegle);
			
		tagCondition = document.createElement("condition");
		tagCondition.setTextContent(conditionDeLaRegleAajouter);
		tagRegle.appendChild(tagCondition);

		tagCotisation = document.createElement("cotisation");
		tagCotisation.setTextContent(cotisationDeLaRegleAajouter);
		tagRegle.appendChild(tagCotisation);
		
		FinaliserInsertion();

	}
	
	public boolean RegleExiste(String condition, String cotisation) {
		
		existance = false;
		int i = 0;
		
		while(i < resultatListeRegles.nombreElements() && existance == false) {
			
			listeDesNoeudsContenu = document.getElementsByTagName("condition");
            contenuCourant = listeDesNoeudsContenu.item(i).getChildNodes().item(0).getNodeValue();
            cotisationCourante = listeDesNoeudsCotisation.item(i).getChildNodes().item(0).getNodeValue();

				if(condition.equals(contenuCourant) && (cotisation.equals(cotisationCourante))) existance = true;
			
			i++;
			
		}
		
		return existance;
		
	}

	public void SupprimerRegle(int numero) {
		
		Node node = listeDesRegles.item(numero);

		node.getParentNode().removeChild(node);

		FinaliserInsertion();

	}

	public void ModifierRegle(int numero, Regle regleAajouter) {

		SupprimerRegle(numero);

		EcrireRegle(regleAajouter);

		FinaliserInsertion();
		
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
	
}
