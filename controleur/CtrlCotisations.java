package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import outils.GestionCotisations;
import outils.GestionRegles;
import vue.interfaces.IPanneauCotisations;
import modele.Cotisation;
import modele.ListeCotisations;
import modele.Regle;

public class CtrlCotisations implements ActionListener {
	
	public ListeCotisations listeCotisations;
	
	private IPanneauCotisations vue;
	private String cheminDuFichierXML, cheminDuFichierReglesXML, action, libelleCotisationSaisiParUtilisateur, tauxCotisationSaisiParUtilisateur;
	private Regle regle;
	private GestionCotisations gestionCotisations, gestionCotisationsModifier;
	private Cotisation cotisationAajouter;
	private int numeroLigneCotisation;
	private GestionRegles gestionRegles;

	private Regle regleAmodifier;
	
	public CtrlCotisations() {

		cheminDuFichierXML = "src/Cotisations.xml";
		cheminDuFichierReglesXML = "src/Regles.xml";

		listeCotisations = new ListeCotisations();

		gestionCotisations = new GestionCotisations(cheminDuFichierXML);
		listeCotisations = gestionCotisations.ListeCotisations();
		gestionRegles = new GestionRegles(cheminDuFichierXML);

	}
	
	public void setVue(IPanneauCotisations vue) {
		
		this.vue = vue;
		
	}

	public int nombreElements() {
		
		if (this.listeCotisations == null) return 0;
		else return listeCotisations.nombreElements();
		
	}

	public String getLibelleCotisation(int numero) {

		return listeCotisations.getLibelleCotisation(numero);
	
	}
	
	public double getTauxCotisation(int numero) {

		return listeCotisations.getTauxCotisation(numero);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		action = e.getActionCommand();

		if(action.startsWith("AJOUTER")) vue.Ajouter();

		else if(action.startsWith("ANNULER")) vue.FermerAjout();

		else if(action.startsWith("CONFIRMER")) {

			libelleCotisationSaisiParUtilisateur = vue.getLibelleSaisieParUtilisateur();
			tauxCotisationSaisiParUtilisateur = vue.getCotisationSaisieParUtilisateur();

			regle = new Regle();
			
			if(libelleCotisationSaisiParUtilisateur.length() == 0 || tauxCotisationSaisiParUtilisateur.length() == 0) {
			
				vue.MessageErreurChampVide();
			
				vue.setFocusLibelle();
			
			}
			else if (regle.isInteger(libelleCotisationSaisiParUtilisateur) == true || regle.isDouble(libelleCotisationSaisiParUtilisateur) == true) {
			
				vue.MessageErreurLibelleNumerique();

				vue.setFocusLibelle();
			
			}
			else if (regle.isInteger(tauxCotisationSaisiParUtilisateur) == false && regle.isDouble(tauxCotisationSaisiParUtilisateur) == false) {
			
				vue.MessageErreurCotisationCaractere();
			
				vue.setFocusTaux();
			
			}
			else if (gestionCotisations.CotisationExiste(libelleCotisationSaisiParUtilisateur)){
				
				vue.MessageErreurDoublon();

				vue.setFocusLibelle();
				
			}

			else {
				
				cotisationAajouter = new Cotisation(libelleCotisationSaisiParUtilisateur, Double.parseDouble(tauxCotisationSaisiParUtilisateur));
					
				gestionCotisations = new GestionCotisations(cheminDuFichierXML);
				gestionCotisations.EcrireCotisations(cotisationAajouter);

				listeCotisations.ajouterElement(cotisationAajouter, true);
					
				vue.FermerAjout();
					
				vue.MessageCotisationAjoutee();
					
				vue.ViderChamps();


			}

		}
		else if(action.startsWith("SUPPRIMER")) {
			
			numeroLigneCotisation = Integer.parseInt(action.substring(action.lastIndexOf("_")+1));

			gestionCotisations = new GestionCotisations(cheminDuFichierXML);

			gestionCotisations.SupprimerCotisation(numeroLigneCotisation);
			
			listeCotisations.supprimerElement(numeroLigneCotisation);
			
			vue.MessageCotisationSupprimee();

		}
		else if(action.startsWith("MODIFIER")) {

			numeroLigneCotisation = Integer.parseInt(action.substring(action.lastIndexOf("_")+1));

			libelleCotisationSaisiParUtilisateur = vue.getLibelleSaisieParUtilisateur(numeroLigneCotisation);
			tauxCotisationSaisiParUtilisateur = vue.getCotisationSaisieParUtilisateur(numeroLigneCotisation);

			regle = new Regle();
			
			if(libelleCotisationSaisiParUtilisateur.length() == 0 || tauxCotisationSaisiParUtilisateur.length() == 0) {
			
				vue.MessageErreurChampVide();

			}
			else if (regle.isInteger(libelleCotisationSaisiParUtilisateur) == true || regle.isDouble(libelleCotisationSaisiParUtilisateur) == true) {
			
				vue.MessageErreurLibelleNumerique();

			}
			else if (regle.isInteger(tauxCotisationSaisiParUtilisateur) == false && regle.isDouble(tauxCotisationSaisiParUtilisateur) == false) {
			
				vue.MessageErreurCotisationCaractere();
			
			}

			else {

				gestionCotisationsModifier = new GestionCotisations(cheminDuFichierXML);
				
				cotisationAajouter = new Cotisation(vue.getLibelleSaisieParUtilisateur(numeroLigneCotisation), Double.parseDouble(vue.getCotisationSaisieParUtilisateur(numeroLigneCotisation)));
	
				gestionRegles = new GestionRegles(cheminDuFichierReglesXML);
				
				for (int i=0; i< gestionRegles.nombreDeReglesDansLeFichier; i++){

					if (gestionRegles.listeDesNoeudsCotisation.item(i).getTextContent().equals(getLibelleCotisation(numeroLigneCotisation))) {

						regleAmodifier = new Regle(gestionRegles.listeDesNoeudsContenu.item(i).getTextContent(), cotisationAajouter.getLibelle());
									
						gestionRegles.ModifierRegle(i, regleAmodifier);

					}
					
				}
			
				listeCotisations.supprimerElement(numeroLigneCotisation);
				listeCotisations.ajouterElement(cotisationAajouter, true);

				gestionCotisationsModifier.ModifierCotisation(numeroLigneCotisation, cotisationAajouter);
				vue.MessageCotisationModifiee();
			
			}
		
		}

		vue.Actualiser();

	}

}