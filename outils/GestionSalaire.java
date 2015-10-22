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
import modele.SalaireEmploye;

public class GestionSalaire {

	private String cheminDuFichierXML;
	private Node tagNet;
	private boolean fichierActuellementVide;
	private Element tagNom, tagPrenom, tagDate, tagBrut, elementRacineDuFichier, tagEmploye, tagStatut;
	private NodeList testExistanceBalisePourFichierVide, listeDesEmployes;

	// Variables pour la création de fichiers XML
	private File fichierXML;
	private DocumentBuilderFactory fabrique;
	private DocumentBuilder constructeur;
	private Document document;
	private TransformerFactory transformerFactory;
	private Transformer transformer;
	private DOMSource source;
	private StreamResult result;
	private double brutSaisiPourEcriture, netSaisiPourEcriture;
	private String statutSaisiPourEcriture, nomSaisiPourEcriture, prenomSaisiPourEcriture, dateSaisiPourEcriture;

	public GestionSalaire(String cheminDuFichierXML) {
		
		this.cheminDuFichierXML = cheminDuFichierXML;
	
		fichierXML = new File(cheminDuFichierXML);

		// Création d'une fabrique de documents
		fabrique = DocumentBuilderFactory.newInstance();
				
		// Création d'un constructeur de documents
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

	}
	
	public void SupprimerEmploye(int numero) {
		
		Node node = listeDesEmployes.item(numero);

		node.getParentNode().removeChild(node);

		FinaliserInsertion();

	}

	public void EcrireEmploye(SalaireEmploye salaireEmployeAajouter) {

		nomSaisiPourEcriture = salaireEmployeAajouter.getNom();
		statutSaisiPourEcriture = salaireEmployeAajouter.getStatut();
		brutSaisiPourEcriture = salaireEmployeAajouter.getBrut();
		netSaisiPourEcriture = salaireEmployeAajouter.getNet();
		prenomSaisiPourEcriture = salaireEmployeAajouter.getPrenom();
		dateSaisiPourEcriture = salaireEmployeAajouter.getDate();
			
		fichierActuellementVide = false;
		
		// On tente de récuperer la balise "cotisations"
		try {

			testExistanceBalisePourFichierVide = document.getElementsByTagName("employes");
				
		} catch (Exception e) {
				
			fichierActuellementVide = true;
				
		}
			
		// Si le fichier est vide de base, on recrée tout sinon on lit ce qui y a deja
		if(!fichierActuellementVide) elementRacineDuFichier = document.getDocumentElement();

		else {
				
			document = constructeur.newDocument();
			elementRacineDuFichier = document.createElement("employes");
			document.appendChild(elementRacineDuFichier);
				
		}

		tagEmploye = document.createElement("employe");
		elementRacineDuFichier.appendChild(tagEmploye);
			
		tagNom = document.createElement("nom");
		tagNom.setTextContent(nomSaisiPourEcriture);
		tagEmploye.appendChild(tagNom);
			
		tagPrenom = document.createElement("prenom");
		tagPrenom.setTextContent(prenomSaisiPourEcriture);
		tagEmploye.appendChild(tagPrenom);
			
		tagStatut = document.createElement("statut");
		tagStatut.setTextContent(statutSaisiPourEcriture);
		tagEmploye.appendChild(tagStatut);
		
		tagDate = document.createElement("date");
		tagDate.setTextContent(dateSaisiPourEcriture);
		tagEmploye.appendChild(tagDate);
		
		tagBrut = document.createElement("salaireBrut");
		tagBrut.setTextContent(String.valueOf(brutSaisiPourEcriture));
		tagEmploye.appendChild(tagBrut);
		
		tagNet = document.createElement("salaireNet");
		tagNet.setTextContent(String.valueOf(netSaisiPourEcriture));
		tagEmploye.appendChild(tagNet);
			
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
