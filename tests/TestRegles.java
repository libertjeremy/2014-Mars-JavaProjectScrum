package tests;

import static org.junit.Assert.*;
import modele.ListeRegles;
import modele.Regle;

import org.junit.Test;

import outils.GestionCotisations;
import outils.GestionRegles;
import outils.VerificationSalaire;

public class TestRegles {
	
	String fichierXMLtestCotisations = "src/tests/XML/TestCotisations.xml";
	String fichierXMLtestRegles = "src/tests/XML/TestRegles.xml";
	String fichierCotisationsFixes = "src/tests/XML/Cotisation50.xml";
	String fichierXMLtestReglesAappliquer = "src/tests/XML/TestReglesAappliquer.xml";
	
	@Test
	public void testExpressionTextFalse() {
		
		Regle regle = new Regle();
		assertFalse(regle.isSplit("toto"));
	}
	
	@Test
	public void testExpressionTextTrue() {
		
		Regle regle = new Regle();
		assertTrue(regle.isSplit("toto/titi"));
		
	}
	
	@Test
	public void testExpressionTextEmptyFalse() {
		
		Regle regle = new Regle();
		assertFalse(regle.isSplit("/"));
		
	}
	
	@Test
	public void testExpressionIsIntegerTrue() {
		
		Regle regle = new Regle();
		assertTrue(regle.isInteger("5"));
		
	}
	
	@Test
	public void testExpressionStringIsIntegerFalse() {
		
		Regle regle = new Regle();
		assertFalse(regle.isInteger("titi"));
		
	}

	@Test
	public void testExpressionDoubleIsIntegerFalse() {
	
		Regle regle = new Regle();
		assertFalse(regle.isInteger("4.8"));
		
	}
	
	@Test
	public void testExpressionIsDoubleTrue() {
	
		Regle regle = new Regle();
		assertTrue(regle.isDouble("1445.38"));
	
	}
	
	@Test
	public void testExpressionStringIsDoubleFalse() {
	
		Regle regle = new Regle();
		assertFalse(regle.isDouble("toto"));
	
	}
	
	@Test
	public void testIsConditionFalse() {
	
		Regle regle = new Regle();
		assertFalse(regle.isCondition("toto"));
	
	}


	@Test
	public void testIsConditionLowerTrue() {
	
		Regle regle = new Regle();
		assertTrue(regle.isCondition("brut < 8"));
	
	}
	
	@Test
	public void testIsConditionLowerNoBrutFalse() {
	
		Regle regle = new Regle();
		assertFalse(regle.isCondition("toto < 8"));
	
	}
	
	@Test
	public void testIsConditionLowerNoBrut2False() {
	
		Regle regle = new Regle();
		assertFalse(regle.isCondition("brut < brut"));
	
	}
	
	@Test
	public void testIsConditionStatutCadreTrue() {
	
		Regle regle = new Regle();
		assertTrue(regle.isCondition("statut = \"cadre\""));
	
	}
	
	@Test
	public void testIsConditionStatutNonCadreTrue() {
	
		Regle regle = new Regle();
		assertTrue(regle.isCondition("statut = \"non cadre\"") && regle.isCondition("statut = \"non cadre\""));
	
	}

	@Test
	public void testIsConditionStatutFalse() {
	
		Regle regle = new Regle();
		assertFalse(regle.isCondition("statut = \"chomeur\""));
	
	}
	
	@Test
	public void testIsConditionNoStatutFalse() {
	
		Regle regle = new Regle();
		assertFalse(regle.isCondition("bonjour = \"cadre\""));
	
	}
	
	@Test
	public void testIsStatutTrue() {
		
		Regle regle = new Regle();
		assertTrue(regle.isStatut("cadre") && regle.isStatut("noncadre") && regle.isStatut("non cadre"));
		
	}
	
	@Test
	public void testIsStatutFalse() {
		
		Regle regle = new Regle();
		assertFalse(regle.isStatut("chomeur"));
		
	}
	
	@Test
	public void testVerificationRegleTrue() {
		
		VerificationSalaire vS = new VerificationSalaire(1000, "Cadre", "brut > 999 AND brut < 1001 AND statut=\"cadre\"");
		
		assertTrue(vS.Verification());
		
	}
	
	@Test
	public void testVerificationRegleFalse() {
		
		VerificationSalaire vS = new VerificationSalaire(1000, "Cadre", "brut < 999 AND brut > 1001 AND statut=\"cadre\"");
		
		assertFalse(vS.Verification());
		
	}
	
	@Test
	public void ExistanceCotisationDansUneRegleTrue() {

		ListeRegles listeRegles = new ListeRegles();

		GestionCotisations gc = new GestionCotisations(fichierXMLtestCotisations);

		GestionRegles gr = new GestionRegles(fichierXMLtestRegles);
		listeRegles = gr.ListeRegles();

		int randomNum = (int) (Math.random()*listeRegles.nombreElements()); 

		assertTrue(gc.CotisationExiste(listeRegles.getCotisationDeLaRegle(randomNum)));
		
	}
	
	@Test
	public void testAppliquerCotisationEtRegleTrue() {
		
		String brut = "1000";
		String statut = "Cadre";
		
		GestionCotisations gC = new GestionCotisations(fichierCotisationsFixes);

		GestionRegles gR = new GestionRegles(fichierXMLtestReglesAappliquer);
		ListeRegles listeDeToutesLesRegles = gR.ListeRegles();

		double cotisations = 0;

		for(int i = 0; i < listeDeToutesLesRegles.nombreElements(); i++) {

			VerificationSalaire vS = new VerificationSalaire(Double.parseDouble(brut), statut, listeDeToutesLesRegles.getCondition(i));
			
			double tauxCotisationCourante = gC.RecupererTauxCotisation(listeDeToutesLesRegles.getCotisationDeLaRegle(i));
			
			if(vS.Verification() && String.valueOf(tauxCotisationCourante).length() > 0) cotisations += Double.parseDouble(brut) * ( tauxCotisationCourante / 100 );

		}

		double net = Double.parseDouble(brut) - cotisations;

		assertTrue(net == 500);
		
	}
	
}

