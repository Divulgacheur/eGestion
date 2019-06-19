

/* ===========================================================================================
* Classe mère des classes Depense & Revenus
* @author Theo Peltier & Stephane Maurin
* @version 2.0
* =========================================================================================== */ 

public class Operation {

	
	// Variables d'instance
	protected float montant;
	protected int categorie;
	protected String description;
	protected Compte compte_lie;
	protected String destinataire ;



	// Constructeur
	public Operation(float montant, int categorie, String destinataire, String description, Compte compte_lie){
		this.montant = montant;
		this.categorie = categorie;
		this.compte_lie = compte_lie ;
		this.destinataire=destinataire;
		this.description=description;
	}

	
}
