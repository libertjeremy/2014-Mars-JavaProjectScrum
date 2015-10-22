package vue.interfaces;

public interface IPanneauRegles {

	void Ajouter();

	void FermerAjout();

	void setFocusCondition();

	void setFocusCotisation();

	void ViderChamps();

	void MessageErreurChampVide();

	void MessageErreurConditionInvalide();

	void MessageErreurCotisationInexistante();

	void MessageRegleAjoutee();

	void MessageErreurInattendue();

	String getConditionSaisieParUtilisateur();

	String getCotisationSaisieParUtilisateur();

	void ConstructionAffichage();

	void Actualiser();

	void MessageRegleModifiee();

	String getConditionSaisieParUtilisateur(int numeroLigneRegle);

	String getCotisationSaisieParUtilisateur(int numeroLigneRegle);

	void MessageRegleSupprimee();

	void VoirTauxCotisation(int numeroLigneRegle);

	void MessageErreurRegleDoublon();


}
