package exe;

import vue.FenetrePrincipale;
import vue.interfaces.IFenetrePrincipale;
import controleur.CtrlPrincipal;

public class Application {
	
	private static CtrlPrincipal controleur;
	private static IFenetrePrincipale vue;
	
	public static void main(String[] args) {
		
		controleur = new CtrlPrincipal();
		vue = new FenetrePrincipale(controleur);
		controleur.setVue(vue);

	}

}