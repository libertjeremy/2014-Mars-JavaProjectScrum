package vue;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import vue.interfaces.IPanneauRegles;
import controleur.CtrlRegles;

public class PanneauRegles extends JPanel implements IPanneauRegles {

	private static final long serialVersionUID = 1L;
	private CtrlRegles monControleur;
	private JButton bModifier, bSupprimer, bAjout, bAnnuler, bActualiser;
	private JDialog dialog;

	public static JButton bOK;
	public static JPanel panneauRegle, panneauTotal, panneauGeneral, panneauAjout, panneauAjoutStatut, panneauAjoutLibelle, panneauAjoutTaux, panneauAjoutBouton, panneauGeneralAjout;
	private JScrollPane panneauAvecScroll;
	private Border bordureScrollPane;
	private GridLayout grille, grille2;
	private GridBagLayout grille3;
	
	private JTextField champCondition, champCotisation, jtConditionSaisie,  jtCotisationChoisie;
	private JButton bVoir;
	private Container panneauGeneralVoir;
	
	public static ArrayList<JTextField> champsCondition, champsCotisation;
	
	public PanneauRegles(CtrlRegles controleur) {

		// Déclarations
		monControleur = controleur;
		monControleur.setVue(this);

		panneauTotal = new JPanel();
		panneauGeneral = new JPanel();
		panneauAjout = new JPanel();
		panneauAvecScroll = new JScrollPane(panneauGeneral);

		grille3 = new GridBagLayout();

		panneauTotal.setLayout(grille3);
		
		panneauAvecScroll.setBorder(bordureScrollPane);
		panneauAvecScroll.setPreferredSize(new Dimension(980, 460));

		bordureScrollPane = BorderFactory.createEmptyBorder(0, 0, 0, 0);
		champsCondition = new ArrayList<JTextField>();
		champsCotisation = new ArrayList<JTextField>();
		
		AffichageHaut();
		ConstructionAffichage();

		GridBagConstraints contrainteGridBagAjout = new GridBagConstraints();

		contrainteGridBagAjout.gridx = 0;
		contrainteGridBagAjout.gridy = 0;
				
		GridBagConstraints contrainteGridBagListe = new GridBagConstraints();

		contrainteGridBagListe.gridx = 0;
		contrainteGridBagListe.gridy = 1;

		panneauTotal.add(panneauAjout, contrainteGridBagAjout);

		panneauTotal.add(panneauAvecScroll, contrainteGridBagListe);
		
		add(panneauTotal);

	}
	
	@Override
	public void ConstructionAffichage() {
	
		panneauGeneral.removeAll();
		
		if(monControleur.nombreElements() > 0) {

			grille = new GridLayout(monControleur.nombreElements(), 0);
			panneauGeneral.setLayout(grille);
			
			for (int i = 0; i < monControleur.nombreElements(); i++) {
	
				panneauRegle = new JPanel();
				
				bModifier = new JButton("Modifier");
				bSupprimer = new JButton("Supprimer");
				bVoir = new JButton("Voir taux");
				
				champCondition = new JTextField();
				champCondition.setPreferredSize(new Dimension(250,25));
				champCondition.setText(""+monControleur.getConditionDeLaRegle(i));
				champsCondition.add(i, champCondition);
				
				champCotisation = new JTextField();
				champCotisation.setPreferredSize(new Dimension(250,25));
				champCotisation.setText(""+monControleur.getCotisationDeLaRegle(i));
				champsCotisation.add(i, champCotisation);
				
				panneauRegle.add(new JLabel("Si : "));
				panneauRegle.add(champCondition);
				panneauRegle.add(new JLabel("Appliquer : "));
				panneauRegle.add(champCotisation);
				panneauRegle.add(bModifier);
				panneauRegle.add(bSupprimer);
				panneauRegle.add(bVoir);
				
				bModifier.addActionListener(monControleur);
				bModifier.setActionCommand("MODIFIER_REGLE_"+i);
				bSupprimer.addActionListener(monControleur);
				bSupprimer.setActionCommand("SUPPRIMER_REGLE_"+i);
				bVoir.addActionListener(monControleur);
				bVoir.setActionCommand("VOIR_COTI_"+i);
	
				panneauGeneral.add(panneauRegle);
				
			}
			
		}
		else panneauGeneral.add(new JLabel("<html><u style=\"font-size: 10px;\">Aucune règle n'a encore été crée</u></html>"));

	}
	
	public void AffichageHaut() {
		
		bActualiser = new JButton("Actualiser");
		bActualiser.addActionListener(monControleur);
		bActualiser.setActionCommand("ACTUALISER");
		
		bAjout = new JButton("Ajouter une règle");
		bAjout.addActionListener(monControleur);
		bAjout.setActionCommand("AJOUTER");

		panneauAjout = new JPanel(new GridLayout(1,3));
		panneauAjout.add(bAjout, BorderLayout.EAST);
		panneauAjout.add(bActualiser,BorderLayout.EAST);
		
	}
	
	public void Ajouter() {
		
		dialog = new JDialog(FenetrePrincipale.jFrame, "Ajouter une règle");
		
		grille2 = new GridLayout(3,0);
		
		jtConditionSaisie =  new JTextField(15);
		jtCotisationChoisie = new JTextField(15);

		bAjout = new JButton("Ajouter");
		bAjout.addActionListener(monControleur);
		bAjout.setActionCommand("CONFIRMER");
		
		bAnnuler = new JButton("Annuler");
		bAnnuler.addActionListener(monControleur);
		bAnnuler.setActionCommand("ANNULER");
		
		panneauAjoutLibelle = new JPanel();
		panneauAjoutTaux = new JPanel();
		panneauAjoutBouton = new JPanel();
		panneauGeneralAjout = new JPanel();

		panneauAjoutLibelle.add(new JLabel("Condition :"));
		panneauAjoutLibelle.add(jtConditionSaisie);
		panneauAjoutTaux.add(new JLabel("Appliquer la cotisation :"));
		panneauAjoutTaux.add(jtCotisationChoisie);
		panneauAjoutBouton.add(bAjout);
		panneauAjoutBouton.add(bAnnuler);

		panneauGeneralAjout.setLayout(grille2);
		panneauGeneralAjout.add(panneauAjoutLibelle);
		panneauGeneralAjout.add(panneauAjoutTaux);
		panneauGeneralAjout.add(panneauAjoutBouton);
		
		dialog.add(panneauGeneralAjout);
		dialog.setModal(true);

		dialog.setLocation(200,200);
		dialog.setSize(new Dimension(400,170));

		dialog.setVisible(true);
		
	}

	public void FermerAjout() {
	
		dialog.setVisible(false);
	
	}

	@Override
	public void setFocusCondition() {
		
		jtConditionSaisie.requestFocus();
		jtConditionSaisie.selectAll();
		
	}

	@Override
	public void setFocusCotisation() {
		
		jtCotisationChoisie.requestFocus();
		jtCotisationChoisie.selectAll();
		
	}

	@Override
	public void ViderChamps() {
		
		jtConditionSaisie.setText("");
		jtCotisationChoisie.setText("");
		
	}

	@Override
	public String getConditionSaisieParUtilisateur() {

		return jtConditionSaisie.getText();
		
	}

	@Override
	public String getCotisationSaisieParUtilisateur() {
		
		return jtCotisationChoisie.getText();
		
	}

	@Override
	public void Actualiser() {

		ConstructionAffichage();
		
		panneauGeneral.repaint();
		panneauGeneral.revalidate();
		
	}

	@Override
	public String getConditionSaisieParUtilisateur(int numeroLigneRegle) {
		return champsCondition.get(numeroLigneRegle).getText();
	}

	@Override
	public String getCotisationSaisieParUtilisateur(int numeroLigneRegle) {
		
		return champsCotisation.get(numeroLigneRegle).getText();
		
	}

	@Override
	public void VoirTauxCotisation(int numeroLigneRegle) {

		dialog = new JDialog(FenetrePrincipale.jFrame, "Voir une cotisation");

		panneauGeneralVoir = new JPanel();

		grille2 = new GridLayout(2,0);
		
		String libelle = monControleur.getCotisationDeLaRegle(numeroLigneRegle);
		
		double taux = monControleur.getTauxCotisationDeLaRegle(libelle);
		
		panneauGeneralVoir.add(new JLabel("Libellé : " + libelle, JLabel.CENTER));
		panneauGeneralVoir.add(new JLabel("Taux : " + taux + "%", JLabel.CENTER));
		
		panneauGeneralVoir.setLayout(grille2);
		
		dialog.add(panneauGeneralVoir);
		
		dialog.setModal(true);
		dialog.setLocation(200,200);
		dialog.setSize(new Dimension(250,100));

		dialog.setVisible(true);
		
	}

	@Override
	public void MessageRegleModifiee() {
		
		JOptionPane.showMessageDialog(null, "Règle modifiée", "Information", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	@Override
	public void MessageErreurChampVide() {
		
		JOptionPane.showMessageDialog(null, "Un des champs est vide !", "Erreur", JOptionPane.ERROR_MESSAGE);
		
	}

	@Override
	public void MessageErreurConditionInvalide() {

		JOptionPane.showMessageDialog(null, "La condition n'est pas valide", "Erreur", JOptionPane.ERROR_MESSAGE);
		
	}

	@Override
	public void MessageErreurCotisationInexistante() {
		
		JOptionPane.showMessageDialog(null, "La cotisation n'existe pas", "Erreur", JOptionPane.ERROR_MESSAGE);
		
	}

	@Override
	public void MessageRegleAjoutee() {
		
		JOptionPane.showMessageDialog(null, "La règle a été ajoutée", "Information", JOptionPane.INFORMATION_MESSAGE);
		
	}

	@Override
	public void MessageErreurInattendue() {

		JOptionPane.showMessageDialog(null, "Erreur inattendue", "Erreur", JOptionPane.ERROR_MESSAGE);
		
	}
	
	@Override
	public void MessageErreurRegleDoublon() {

		JOptionPane.showMessageDialog(null, "Cette règle existe deja !", "Erreur", JOptionPane.ERROR_MESSAGE);
		
	}
	
	@Override
	public void MessageRegleSupprimee() {
		
		JOptionPane.showMessageDialog(null, "Règle supprimée", "Information", JOptionPane.INFORMATION_MESSAGE);
		
	}

	
}
