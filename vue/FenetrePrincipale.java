package vue;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import vue.interfaces.IFenetrePrincipale;
import controleur.CtrlPrincipal;

public class FenetrePrincipale extends JFrame implements IFenetrePrincipale {

	private static final long serialVersionUID = 1L;
	private CtrlPrincipal monControleur;
	public static JTabbedPane ongletsDeNavigation;
	private JLabel titreApplication;
	
	private Font policePersonnaliseeTitreApplication;
	public static JFrame jFrame;
	
	private static JPanel panneauTitreApplication;

	public FenetrePrincipale(CtrlPrincipal controleur) {
		
		super("Ma fenetre principale");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		monControleur = controleur;

		jFrame = new JFrame();
		jFrame.getContentPane().setLayout(new GridLayout(2,1));
		
		panneauTitreApplication = new JPanel(new FlowLayout());
		
		titreApplication = new JLabel("Générateur de fiche de paye", JLabel.CENTER);
		policePersonnaliseeTitreApplication = new Font("Arial", Font.BOLD,20);
		titreApplication.setFont(policePersonnaliseeTitreApplication);

		add(panneauTitreApplication, BorderLayout.NORTH);

		panneauTitreApplication.add(titreApplication);

    	AjoutOnglets();
    	
		setSize(1000,600);
		
		setVisible(true);
	}
	
	
	private void AjoutOnglets() {
		
		ongletsDeNavigation = new JTabbedPane();

		PanneauRegles panneauSaisieRegle = new PanneauRegles(this.monControleur.getControleurRegles());
		PanneauSalaire panneauSalaire = new PanneauSalaire(this.monControleur.getControleurSalaire());
		PanneauCotisations panneauListeCotisations = new PanneauCotisations(this.monControleur.getControleurListeCotisations());
		
		ongletsDeNavigation.add("Saisie d'un salaire", panneauSalaire);
		ongletsDeNavigation.add("Gestion des règles", panneauSaisieRegle);
		ongletsDeNavigation.add("Gestion des cotisations", panneauListeCotisations);

    	add(ongletsDeNavigation);
		
	}
	
}
