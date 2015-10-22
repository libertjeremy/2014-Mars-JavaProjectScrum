package vue;

import java.awt.BorderLayout;
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

import vue.interfaces.IPanneauCotisations;
import vue.interfaces.IPanneauRegles;
import controleur.CtrlCotisations;

public class PanneauCotisations extends JPanel implements IPanneauCotisations {

	private static final long serialVersionUID = 1L;
	public static JPanel panneauCotisation, panneauTotal, panneauGeneral, panneauAjout, panneauAjoutLibelle, panneauAjoutTaux, panneauAjoutBouton, panneauGeneralAjout;
	private CtrlCotisations monControleur;
	private JScrollPane panneauAvecScroll;
	private JButton bModifier, bSupprimer, bAjout, bAnnuler;
	private JTextField champLibelle, champTaux;
	public static JTextField jtCotisationSaisie, jtTauxSaisi;
	private GridLayout grille, grille2;
	private JDialog dialog;
	private Border bordureScrollPane;
	
	public ArrayList<JButton> boutonsSupprimer;
	public ArrayList<JButton> boutonsModifier;
	public static ArrayList<JTextField> champsLibelle, champsTaux;
	public IPanneauRegles panneauRegles;

	public PanneauCotisations(CtrlCotisations controleur) {

		// Déclarations
		monControleur = controleur;
		monControleur.setVue(this);

		panneauGeneral = new JPanel();
		panneauAjout = new JPanel();
		panneauTotal = new JPanel();
		
		panneauAvecScroll = new JScrollPane(panneauGeneral);
		bordureScrollPane = BorderFactory.createEmptyBorder(0, 0, 0, 0);
		champsLibelle = new ArrayList<JTextField>();
		champsTaux = new ArrayList<JTextField>();
		
		panneauAvecScroll.setBorder(bordureScrollPane);

		ConstructionAffichage();

		panneauAvecScroll.setPreferredSize(new Dimension(980, 460));
		
		AffichageHaut();

		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints contrainteGridBagAjout = new GridBagConstraints();

		contrainteGridBagAjout.gridx = 0;
		contrainteGridBagAjout.gridy = 0;
		
		GridBagConstraints contrainteGridBagListe = new GridBagConstraints();

		contrainteGridBagListe.gridx = 0;
		contrainteGridBagListe.gridy = 1;

		panneauTotal.setLayout(gridbag);

		panneauTotal.add(panneauAjout, contrainteGridBagAjout);

		panneauTotal.add(panneauAvecScroll, contrainteGridBagListe);

		add(panneauTotal);

	}
	
	public void AffichageHaut() {
		
		bAjout = new JButton("Ajouter une cotisation");
		bAjout.addActionListener(monControleur);
		bAjout.setActionCommand("AJOUTER");
		
		panneauAjout.add(bAjout, BorderLayout.EAST);
		
	}
	
	@Override
	public void ConstructionAffichage() {
	
		panneauGeneral.removeAll();

		if(monControleur.nombreElements() > 0) {
		
			grille = new GridLayout(monControleur.nombreElements(), 0);
			panneauGeneral.setLayout(grille);
			
			for (int i = 0; i < monControleur.nombreElements(); i++) {
	
				panneauCotisation = new JPanel();
				
				bModifier = new JButton("Modifier");
				bSupprimer = new JButton("Supprimer");
				
				champLibelle = new JTextField();
				champLibelle.setPreferredSize(new Dimension(350, 25));
				champLibelle.setText(""+monControleur.getLibelleCotisation(i));
				
				champsLibelle.add(i, champLibelle);
				
				champTaux = new JTextField();
				champTaux.setPreferredSize(new Dimension(40, 25));
				champTaux.setText(""+monControleur.getTauxCotisation(i));
				champTaux.setHorizontalAlignment(JTextField.CENTER);
				champsTaux.add(i, champTaux);
				
				panneauCotisation.add(champLibelle);
				panneauCotisation.add(new JLabel("Taux : "));
				panneauCotisation.add(champTaux);
				panneauCotisation.add(new JLabel("%"));
	
				panneauCotisation.add(bModifier);
				panneauCotisation.add(bSupprimer);
				
				bModifier.addActionListener(monControleur);
				bModifier.setActionCommand("MODIFIER_COTI_"+i);
				bSupprimer.addActionListener(monControleur);
				bSupprimer.setActionCommand("SUPPRIMER_COTI_"+i);
	
				panneauGeneral.add(panneauCotisation);
				
			}
		
		}
		else panneauGeneral.add(new JLabel("<html><u style=\"font-size: 10px;\">Aucune cotisation n'a encore été établie</u></html>"));

	}
	
	public void Actualiser() {
		
		ConstructionAffichage();
		panneauGeneral.repaint();
		panneauGeneral.revalidate();
		
	}

	public void SupprimerLigne(int noLigne) {
		
		boutonsSupprimer.get(noLigne).setEnabled(false);
		
	}
	
	public void Ajouter() {
		
		dialog = new JDialog(FenetrePrincipale.jFrame, "Ajouter une cotisation");
		
		grille2 = new GridLayout(3,0);
		
		jtCotisationSaisie = new JTextField(10);
		
		jtTauxSaisi =  new JTextField(8);

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

		panneauAjoutLibelle.add(new JLabel("Libéllé de la cotisation :"));
		panneauAjoutLibelle.add(jtCotisationSaisie);
		panneauAjoutTaux.add(new JLabel("Taux en pourcentage :"));
		panneauAjoutTaux.add(jtTauxSaisi);
		panneauAjoutBouton.add(bAjout);
		panneauAjoutBouton.add(bAnnuler);

		panneauGeneralAjout.setLayout(grille2);
		panneauGeneralAjout.add(panneauAjoutLibelle);
		panneauGeneralAjout.add(panneauAjoutTaux);
		panneauGeneralAjout.add(panneauAjoutBouton);
		
		dialog.add(panneauGeneralAjout);
		dialog.setModal(true);

		dialog.setLocation(200,200);
		dialog.setSize(new Dimension(400,155));

		dialog.setVisible(true);
		
	}
	
	public void FermerAjout() {
		
		dialog.setVisible(false);
		
	}

	@Override
	public void MessageErreurChampVide() {
		
		JOptionPane.showMessageDialog(null, "Un champ est vide !");
		
	}

	@Override
	public void MessageErreurLibelleNumerique() {
		
		JOptionPane.showMessageDialog(null, "Le libellé de la cotisation ne doit pas être numérique !", "Erreur", JOptionPane.ERROR_MESSAGE);
		
	}

	@Override
	public void MessageErreurCotisationCaractere() {
		
		JOptionPane.showMessageDialog(null, "Le taux de la cotisation ne doit pas contenir de caractères !", "Erreur", JOptionPane.ERROR_MESSAGE);
		
	}

	@Override
	public void MessageErreurInattendue() {
		
		JOptionPane.showMessageDialog(null, "Erreur inattendue", "Erreur", JOptionPane.ERROR_MESSAGE);
		
	}
	
	@Override
	public void MessageErreurDoublon() {
		
		JOptionPane.showMessageDialog(null, "Cette cotisation existe deja !","Erreur", JOptionPane.ERROR_MESSAGE);
		
	}


	@Override
	public void MessageCotisationSupprimee() {
		
		JOptionPane.showMessageDialog(null, "La cotisation a été supprimée", "Information", JOptionPane.INFORMATION_MESSAGE);
		
	}

	@Override
	public void MessageCotisationModifiee() {
		
		JOptionPane.showMessageDialog(null, "La cotisation a été modifiée", "Information", JOptionPane.INFORMATION_MESSAGE);
		
	}

	@Override
	public void MessageCotisationAjoutee() {
		
		JOptionPane.showMessageDialog(null, "La cotisation a été ajoutée", "Information", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	@Override
	public String getCotisationSaisieParUtilisateur(int numeroLigneCotisation) {
		
		return champsTaux.get(numeroLigneCotisation).getText();
		
	}

	@Override
	public String getLibelleSaisieParUtilisateur(int numeroLigneCotisation) {
		
		return champsLibelle.get(numeroLigneCotisation).getText();
		
	}


	@Override
	public String getCotisationSaisieParUtilisateur() {
		
		return jtTauxSaisi.getText();
		
	}

	@Override
	public String getLibelleSaisieParUtilisateur() {
		
		return jtCotisationSaisie.getText();
		
	}

	@Override
	public void ViderChamps() {
		
		jtCotisationSaisie.setText("");
		jtTauxSaisi.setText("");
		
	}

	@Override
	public void setFocusTaux() {

		jtTauxSaisi.requestFocus();
		jtTauxSaisi.selectAll();
		
	}

	@Override
	public void setFocusLibelle() {
		
		jtCotisationSaisie.requestFocus();
		jtCotisationSaisie.selectAll();
		
	}

}
