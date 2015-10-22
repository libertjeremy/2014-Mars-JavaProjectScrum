package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import modele.Cotisation;
import modele.ListeCotisations;

import org.junit.Test;

import outils.GestionCotisations;

public class TestCotisations {
	
	String fichierXMLtestCotisations = "src/tests/XML/TestCotisations.xml";
	String fichierTestCreation = "src/tests/XML/UnFichierDeTest.xml";
	String fichierNonXML = "src/tests/XML/TestCotisations.txt";
	String fichierCotisationsFixes = "src/tests/XML/Cotisation50.xml";
	
	@Test
	public void testCotisationIsIntegerAndIntegerReturnFalse() {
		
		boolean erreur = false;
		
		try {
			
			Cotisation cotis = new Cotisation("2.0", 2.0);
			
		} catch (Exception e) {
			
			erreur = true;
			
		}
		
		assertTrue(erreur);
		
	}
	
	@Test
	public void testCreationFichierTrue() {
		
		File fichierDeTest = new File(fichierTestCreation);
		
		boolean existanceAvantCreation = fichierDeTest.exists();

		try {
			
			fichierDeTest.createNewFile();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		boolean existanceApresCreation = fichierDeTest.exists();
		
		fichierDeTest.delete();
		
		boolean existanceApresSuppression = fichierDeTest.exists();
		assertTrue(!existanceAvantCreation && existanceApresCreation && !existanceApresSuppression);
	}
	
	@Test
	public void testLectureFichierTrue() {

		ListeCotisations liste = new ListeCotisations();
		boolean erreur = false;
		
		try {
			
			GestionCotisations g = new GestionCotisations(fichierXMLtestCotisations);
			liste = g.ListeCotisations();
			
		} catch (Exception e) {
			
			erreur = true;
			
		}
		
		// La liste des cotisations n'est pas nulle
		erreur = liste != null;
		
		assertTrue(erreur);
		
	}
	
	@Test
	public void testLectureFichierFalse() {

		ListeCotisations liste = new ListeCotisations();
		liste = null;
		boolean erreur = false;
		
		try {
			
			GestionCotisations g = new GestionCotisations(fichierNonXML);
			liste = g.ListeCotisations();

		} catch (Exception e) {
			
			erreur = true;
			
		}

		assertTrue(liste == null && erreur);
		
	}
	
	@Test
	public void testSalaireBrut1000Net500True() {

		GestionCotisations g = new GestionCotisations(fichierCotisationsFixes);
		ListeCotisations testListeCotisations = g.ListeCotisations();

		double cotisations = 0;
		double brut = 1000;
		
		for (int i = 0; i < testListeCotisations.nombreElements(); i++) {
			
			double tauxCotisation = testListeCotisations.getTauxCotisation(i);
			cotisations += brut * tauxCotisation / 100;

		}
		
		double net = brut - cotisations;

		assertTrue(net == 500);

	}

	@Test
	public void testExistanceCotisationTrue() {
		
		GestionCotisations g = new GestionCotisations(fichierXMLtestCotisations);

		assertTrue(g.CotisationExiste("cotisationExistante"));
		
	}
	
	@Test
	public void testExistanceCotisationFalse() {
		
		GestionCotisations g = new GestionCotisations(fichierXMLtestCotisations);

		assertFalse(g.CotisationExiste("salutcava"));
		
	}
	
	@Test
	public void testAjoutCotisationTrue() {
	
		String libelleCotisationAajouter = "testCotisationAjout";

		GestionCotisations g = new GestionCotisations(fichierXMLtestCotisations);

		boolean resultat = true;
		
		try {
			
			g.EcrireCotisations(new Cotisation(libelleCotisationAajouter, 0.9));
			
		} catch (Exception e) {
			
			resultat = false;
			e.printStackTrace();

		}
		
		GestionCotisations g2 = new GestionCotisations(fichierXMLtestCotisations);

		assertTrue(g2.CotisationExiste(libelleCotisationAajouter) && resultat);

	}
	
	@Test
	public void testSuppressionCotisationTrue() {

		GestionCotisations g = new GestionCotisations(fichierXMLtestCotisations);
		
		int nombreAleatoire = 1 + (int)(Math.random() * ((99999) + 1));
		String libelleCotisationAsupprimer = "testCotisationSupp"+nombreAleatoire;

		g.EcrireCotisations(new Cotisation(libelleCotisationAsupprimer, 0.9));
		
		g = new GestionCotisations(fichierXMLtestCotisations);
		boolean existanceDepart = g.CotisationExiste(libelleCotisationAsupprimer);
		
		g = new GestionCotisations(fichierXMLtestCotisations);
		g.SupprimerCotisation(g.ListeCotisations().nombreElements()-1);
		
		g = new GestionCotisations(fichierXMLtestCotisations);
		boolean existanceFin = g.CotisationExiste(libelleCotisationAsupprimer);
		
		assertTrue(existanceDepart && !existanceFin);
		
	}
	
}
