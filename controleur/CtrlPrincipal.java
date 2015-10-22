package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vue.interfaces.IFenetrePrincipale;

public class CtrlPrincipal implements ActionListener {

	private IFenetrePrincipale vue;

	private CtrlRegles controleurRegles;
	private CtrlSalaire controleurSalaire;
	private CtrlCotisations controleurListeCotisations;

	public CtrlPrincipal() {

		controleurRegles = new CtrlRegles();
		controleurSalaire = new CtrlSalaire();
		controleurListeCotisations = new CtrlCotisations();
		
	}

	public void setVue(IFenetrePrincipale vue) {
		
		this.vue = vue;
		
	}

	public IFenetrePrincipale getVue() {
		
		return vue;
		
	}
	
	public CtrlRegles getControleurRegles() {

		return this.controleurRegles;
		
	}
	
	public CtrlSalaire getControleurSalaire() {

		return this.controleurSalaire;
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}

	public CtrlCotisations getControleurListeCotisations() {
		
		return this.controleurListeCotisations;
		
	}
	
}
