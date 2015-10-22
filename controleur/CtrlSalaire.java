package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import outils.GestionCotisations;
import outils.GestionRegles;
import outils.GestionSalaire;
import outils.VerificationSalaire;
import modele.ListeRegles;
import modele.Regle;
import modele.SalaireEmploye;
import vue.interfaces.IPanneauSalaire;

public class CtrlSalaire implements ActionListener {
	
	private IPanneauSalaire vue;
	private String dateAujourdhui, salaireNetEnString, salaireBrutSaisiParUtilisateur, action, cheminDuFichierXMLCotisations, cheminDuFichierXMLRegles, cheminDuFichierXMLEmployes;
	private double salaireNetEnDouble, valeurTotaleCotisationAPartirDuBrut, salaireBrutSaisiParUtilisateurEnDouble;
	private Regle regle;
	private GestionSalaire gS;
	private Calendar calendrier;
	private int anneeActuelle, moisActuel, jourActuel;
	private SalaireEmploye employeAajouter;
	private ListeRegles listeDeToutesLesRegles;
	double tauxCotisationCourante;
	
	public void setVue(IPanneauSalaire panneau) {
		
		this.vue = panneau;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		action = e.getActionCommand();
		
		if (action.startsWith("Calculer")) {
			
			cheminDuFichierXMLCotisations = "src/Cotisations.xml";
			cheminDuFichierXMLRegles = "src/Regles.xml";
			
			salaireBrutSaisiParUtilisateur = vue.getSalaireBrutSaisiParUtilisateur();

			regle = new Regle();
			
			if(salaireBrutSaisiParUtilisateur.length() == 0) vue.MessageErreurSaisieVide();

			else if (regle.isInteger(salaireBrutSaisiParUtilisateur) == false && regle.isDouble(salaireBrutSaisiParUtilisateur) == false) vue.MessageErreurSalaireCaractere();

			else {

				salaireBrutSaisiParUtilisateurEnDouble = Double.parseDouble(salaireBrutSaisiParUtilisateur);

				listeDeToutesLesRegles = new ListeRegles();
				
				GestionCotisations gC = null;
				GestionRegles gR = null;

				gC = new GestionCotisations(cheminDuFichierXMLCotisations);

				gR = new GestionRegles(cheminDuFichierXMLRegles);
				listeDeToutesLesRegles = gR.ListeRegles();

				valeurTotaleCotisationAPartirDuBrut = 0;

				for(int i = 0; i < listeDeToutesLesRegles.nombreElements(); i++) {

					VerificationSalaire vS = new VerificationSalaire(salaireBrutSaisiParUtilisateurEnDouble, vue.getStatutSaisiParUtilisateur(), listeDeToutesLesRegles.getCondition(i));
					
					tauxCotisationCourante = gC.RecupererTauxCotisation(listeDeToutesLesRegles.getCotisationDeLaRegle(i));

					if(vS.Verification() && String.valueOf(tauxCotisationCourante).length() > 0) valeurTotaleCotisationAPartirDuBrut += salaireBrutSaisiParUtilisateurEnDouble * ( tauxCotisationCourante / 100 );

				}

				salaireNetEnDouble = salaireBrutSaisiParUtilisateurEnDouble - valeurTotaleCotisationAPartirDuBrut;
				
				salaireNetEnString = String.valueOf(salaireNetEnDouble);
				vue.setTexteSalaireNet(salaireNetEnString);
				
				vue.ActiverBoutonEnregistrer();
				
				if(vue.DemandeEnregistrementEmploye()) vue.LancerBoutonEnregistrer();
			
			}
			
		}
		
		else if(action.startsWith("Enregistrer")) {
			
			cheminDuFichierXMLEmployes = "src/Employes.xml";
			
			gS = new GestionSalaire(cheminDuFichierXMLEmployes);
			
			calendrier = Calendar.getInstance();
			anneeActuelle = calendrier.get(Calendar.YEAR);
			moisActuel = calendrier.get(Calendar.MONTH);
			jourActuel = calendrier.get(Calendar.DAY_OF_MONTH);
			
			dateAujourdhui = jourActuel+"/"+moisActuel+"/"+anneeActuelle;
			
			employeAajouter = new SalaireEmploye(
				vue.getNomSaisiParUtilisateur(), 
				vue.getPrenomSaisiParUtilisateur(), 
				vue.getStatutSaisiParUtilisateur(), 
				dateAujourdhui, 
				Double.parseDouble(vue.getSalaireBrutSaisiParUtilisateur()), 
				Double.parseDouble(vue.getSalaireNet()));

			gS.EcrireEmploye(employeAajouter);

			vue.MessageEmployeEnregistre();
			
		}
		
	}

}
