package vue.interfaces;

public interface IPanneauSalaire {

	public String getSalaireBrutSaisiParUtilisateur();

	public void MessageErreurSalaireCaractere();

	public void MessageErreurSaisieVide();

	public void MessageErreurFichierInexistant();

	public String getStatutSaisiParUtilisateur();

	public void setTexteSalaireNet(String salaireNetEnString);

	public String getSalaireNet();

	public String getPrenomSaisiParUtilisateur();

	public String getNomSaisiParUtilisateur();

	public void MessageEmployeEnregistre();

	public boolean DemandeEnregistrementEmploye();

	public void ActiverBoutonEnregistrer();

	public void LancerBoutonEnregistrer();
	
}
