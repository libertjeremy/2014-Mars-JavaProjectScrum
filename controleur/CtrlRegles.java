package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import outils.GestionCotisations;
import outils.GestionRegles;
import modele.ListeRegles;
import modele.Regle;
import vue.interfaces.IPanneauRegles;

public class CtrlRegles implements ActionListener {

	private IPanneauRegles vue;
	private ListeRegles listeRegles;
	private Regle regleAajouter, reglePourVerifierSaisie;
	private String action, conditionRegleSaisieParUtilisateur, libelleCotisationChoisieParUtilisateur;
	private GestionRegles gestionRegles, gestionReglesModifier, gestionReglesSupprimer;
	private GestionCotisations cotisationPourTestExistence;
	private int numeroLigneRegle;
	private String cheminDuFichierXMLCotisations;
	private String cheminDuFichierXMLRegles;
	private GestionRegles reglePourDoublons;

	public CtrlRegles() {

		cheminDuFichierXMLRegles = "src/Regles.xml";
		cheminDuFichierXMLCotisations = "src/Cotisations.xml";

		listeRegles = new ListeRegles();

		gestionRegles = new GestionRegles(cheminDuFichierXMLRegles);
		listeRegles = gestionRegles.ListeRegles();

	}
	
	public void setVue(IPanneauRegles vue) {

		this.vue = vue;
		
	}
	
	public int nombreElements() {
		
		if (this.listeRegles == null) return 0;
		else return listeRegles.nombreElements();
		
	}
	
	public String getConditionDeLaRegle(int numero) {

		return listeRegles.getCondition(numero);
	
	}
	
	public String getCotisationDeLaRegle(int numero) {

		return listeRegles.getCotisationDeLaRegle(numero);
	}
	
	public IPanneauRegles getVue() {

		return vue;
		
	}

	public ListeRegles getListeRegles() {

		return this.listeRegles;
		
	}
	
	public double getTauxCotisationDeLaRegle(String libelle) {
		
		GestionCotisations gC = new GestionCotisations(cheminDuFichierXMLCotisations);

		return gC.RecupererTauxCotisation(libelle);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		action = e.getActionCommand();

		if(action.startsWith("VOIR")) {
			
			numeroLigneRegle = Integer.parseInt(action.substring(action.lastIndexOf("_")+1));
			
			vue.VoirTauxCotisation(numeroLigneRegle);
			
		}
		
		else if(action.startsWith("AJOUTER")) vue.Ajouter();

		else if(action.startsWith("ANNULER")) vue.FermerAjout();

		else if(action.startsWith("CONFIRMER")) {

			conditionRegleSaisieParUtilisateur = vue.getConditionSaisieParUtilisateur();
			libelleCotisationChoisieParUtilisateur = vue.getCotisationSaisieParUtilisateur();

			reglePourVerifierSaisie = new Regle();
			reglePourDoublons = new GestionRegles(cheminDuFichierXMLRegles);				
			cotisationPourTestExistence = new GestionCotisations(cheminDuFichierXMLCotisations);
	
			if(libelleCotisationChoisieParUtilisateur.length() == 0 || conditionRegleSaisieParUtilisateur.length() == 0) {
	
				vue.MessageErreurChampVide();
				vue.setFocusCondition();
					
			}
			else if (!reglePourVerifierSaisie.isCondition(conditionRegleSaisieParUtilisateur)) {
	
				vue.MessageErreurConditionInvalide();
				vue.setFocusCondition();
					
			}
			else if(!cotisationPourTestExistence.CotisationExiste(libelleCotisationChoisieParUtilisateur)) {
	
				vue.MessageErreurCotisationInexistante();
				vue.setFocusCotisation();
					
			}
			else if(reglePourDoublons.RegleExiste(conditionRegleSaisieParUtilisateur,libelleCotisationChoisieParUtilisateur)) {
					
				vue.MessageErreurRegleDoublon();
				vue.setFocusCondition();
					
			}
			else {
					
				regleAajouter = new Regle(conditionRegleSaisieParUtilisateur, libelleCotisationChoisieParUtilisateur);

				gestionRegles = new GestionRegles(cheminDuFichierXMLRegles);
				gestionRegles.EcrireRegle(regleAajouter);
	
				listeRegles.ajouterElement(regleAajouter, true);
						
				vue.FermerAjout();
						
				vue.MessageRegleAjoutee();
	
				vue.ViderChamps();
	
			}

		}
		else if(action.startsWith("ACTUALISER")) {
			
		vue.Actualiser();


		}
		
		else if(action.startsWith("SUPPRIMER")) {
			
			numeroLigneRegle = Integer.parseInt(action.substring(action.lastIndexOf("_")+1));

			gestionReglesSupprimer = new GestionRegles(cheminDuFichierXMLRegles);

			gestionReglesSupprimer.SupprimerRegle(numeroLigneRegle);
			vue.MessageRegleSupprimee();
			listeRegles.supprimerElement(numeroLigneRegle);


		}
		else if(action.startsWith("MODIFIER")) {

			numeroLigneRegle = Integer.parseInt(action.substring(action.lastIndexOf("_")+1));

			reglePourVerifierSaisie = new Regle();
			
			cotisationPourTestExistence = new GestionCotisations(cheminDuFichierXMLCotisations);
			
			libelleCotisationChoisieParUtilisateur = vue.getCotisationSaisieParUtilisateur(numeroLigneRegle);
			conditionRegleSaisieParUtilisateur = vue.getConditionSaisieParUtilisateur(numeroLigneRegle);

			if(libelleCotisationChoisieParUtilisateur.length() == 0 || conditionRegleSaisieParUtilisateur.length() == 0) {

				vue.MessageErreurChampVide();

			}
			else if (!reglePourVerifierSaisie.isCondition(conditionRegleSaisieParUtilisateur)) {

				vue.MessageErreurConditionInvalide();

			}
			else if(!cotisationPourTestExistence.CotisationExiste(libelleCotisationChoisieParUtilisateur)) {

				vue.MessageErreurCotisationInexistante();
				
			}
			else {
				
				gestionReglesModifier = new GestionRegles(cheminDuFichierXMLRegles);
				
				regleAajouter = new Regle(conditionRegleSaisieParUtilisateur, libelleCotisationChoisieParUtilisateur);
				
				listeRegles.supprimerElement(numeroLigneRegle);
				listeRegles.ajouterElement(regleAajouter, true);

				gestionReglesModifier.ModifierRegle(numeroLigneRegle, regleAajouter);
				vue.MessageRegleModifiee();

			}

		}
		
		vue.Actualiser();

	}

}
