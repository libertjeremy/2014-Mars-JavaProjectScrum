package vue;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import vue.interfaces.IPanneauSalaire;
import controleur.CtrlSalaire;

public class PanneauSalaire extends JPanel implements IPanneauSalaire {

	private CtrlSalaire monControleur;
	public static JTextField jtSalaireBrut, jtNetCalculeGraceCotisations, jtPrenomSaisi, jtNomSaisi;
	public static JButton bCalculNet, bEnregistrerSalaire;
	private JPanel panneauDescription, panneauSaisiePrenom, panneauSaisieNom, panneauSaisieBrut, panneauValidation, panneauResultat;
	private JLabel descriptionSalaire;
	private String[] statuts = { "Cadre", "Non cadre" };
	public static JComboBox listeStatuts;
	private Container panneauSaisieStatut;
	private GridLayout grille;
	
	public PanneauSalaire(CtrlSalaire controleur) {

		monControleur = controleur;
		monControleur.setVue(this);
		
		grille = new GridLayout(10,0);
		setLayout(grille);
		
		jtSalaireBrut = new JTextField(10);
		jtNetCalculeGraceCotisations = new JTextField(10);
		jtPrenomSaisi = new JTextField(10);
		jtNomSaisi = new JTextField(10);
		
		bCalculNet = new JButton("Calculer le salaire net");
		bEnregistrerSalaire = new JButton("Enregistrer ce salarié");

		panneauDescription = new JPanel();
		panneauSaisieStatut = new JPanel();
		panneauSaisieBrut = new JPanel();
		panneauValidation = new JPanel();
		panneauResultat = new JPanel();
		panneauSaisiePrenom = new JPanel();
		panneauSaisieNom = new JPanel();

		listeStatuts = new JComboBox(statuts);

		descriptionSalaire = new JLabel("Calculer un salaire net à partir du brut et des cotisations spécifiques", JLabel.CENTER);
		
		panneauDescription.add(descriptionSalaire);

		panneauSaisieNom.add(new JLabel("Nom : "));
		panneauSaisieNom.add(jtNomSaisi);
		
		panneauSaisiePrenom.add(new JLabel("Prénom : "));
		panneauSaisiePrenom.add(jtPrenomSaisi);
		
		panneauSaisieStatut.add(new JLabel("Statut du salarié : "));
		panneauSaisieStatut.add(listeStatuts);
		
		panneauSaisieBrut.add(new JLabel("Salaire : "));
		panneauSaisieBrut.add(jtSalaireBrut);

		panneauValidation.add(bCalculNet);
		
		bEnregistrerSalaire.setEnabled(false);
		
		panneauValidation.add(bEnregistrerSalaire);
		
		bCalculNet.addActionListener(monControleur);
		bEnregistrerSalaire.addActionListener(monControleur);
		
		panneauResultat.add(new JLabel("Résultat salaire net : "));
		panneauResultat.add(jtNetCalculeGraceCotisations);

		jtNetCalculeGraceCotisations.setEditable(false);

		// On ajoute les panneaux à la mise en page
		add(panneauDescription);
		add(panneauSaisieNom);
		add(panneauSaisiePrenom);
		add(panneauSaisieStatut);
		add(panneauSaisieBrut);
		add(panneauValidation);
		add(panneauResultat);

	}

	@Override
	public String getSalaireBrutSaisiParUtilisateur() {
		
		return jtSalaireBrut.getText();
		
	}

	@Override
	public void MessageErreurSalaireCaractere() {

		JOptionPane.showMessageDialog(null, "La saisie ne peut pas contenir de caractères !", "Erreur", JOptionPane.ERROR_MESSAGE);
		
	}

	@Override
	public void MessageErreurSaisieVide() {

		JOptionPane.showMessageDialog(null, "La saisie est vide !", "Erreur", JOptionPane.ERROR_MESSAGE);
		
	}

	@Override
	public void MessageErreurFichierInexistant() {
		
		JOptionPane.showMessageDialog(null, "Le fichier de cotisations n'existe pas", "Erreur", JOptionPane.ERROR_MESSAGE);
		
	}

	@Override
	public String getStatutSaisiParUtilisateur() {
		
		return listeStatuts.getSelectedItem().toString();
	
	}

	@Override
	public void setTexteSalaireNet(String salaireAafficher) {
		
		jtNetCalculeGraceCotisations.setText(salaireAafficher);
		
	}

	@Override
	public String getSalaireNet() {
		
		return jtNetCalculeGraceCotisations.getText();
		
	}

	@Override
	public String getPrenomSaisiParUtilisateur() {
		return jtPrenomSaisi.getText();
	}

	@Override
	public String getNomSaisiParUtilisateur() {
		return jtNomSaisi.getText();
	}

	@Override
	public void MessageEmployeEnregistre() {
		
		JOptionPane.showMessageDialog(null, "L'employé a été enregistré", "Information", JOptionPane.INFORMATION_MESSAGE);
		
	}

	@Override
	public boolean DemandeEnregistrementEmploye() {
		
		int reponse = JOptionPane.showConfirmDialog(this, "Voulez-vous enregistrer cet employé et son salaire ?", "Information", JOptionPane.INFORMATION_MESSAGE);
		
		return reponse == JOptionPane.YES_OPTION;

	}

	@Override
	public void ActiverBoutonEnregistrer() {
		
		bEnregistrerSalaire.setEnabled(true);
		
	}

	@Override
	public void LancerBoutonEnregistrer() {
		
		bEnregistrerSalaire.doClick();
		
	}
	
}
