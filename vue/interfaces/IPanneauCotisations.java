package vue.interfaces;

public interface IPanneauCotisations {

	public void ConstructionAffichage();

	public void Actualiser();

	public void Ajouter();

	public void FermerAjout();

	public void MessageErreurChampVide();

	public void MessageErreurLibelleNumerique();

	public void MessageErreurCotisationCaractere();

	public void MessageErreurInattendue();

	public void MessageCotisationSupprimee();

	public void MessageCotisationModifiee();

	public void MessageCotisationAjoutee();

	public String getCotisationSaisieParUtilisateur(int numeroLigneCotisation);

	public String getLibelleSaisieParUtilisateur(int numeroLigneCotisation);
	
	public String getCotisationSaisieParUtilisateur();
	
	public String getLibelleSaisieParUtilisateur();

	public void ViderChamps();

	public void setFocusTaux();

	public void setFocusLibelle();

	void MessageErreurDoublon();
	
}
