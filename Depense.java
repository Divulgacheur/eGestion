
import java.util.Scanner;

/* ===========================================================================================
* Classe représentant les dépenses réalisés par les utilisateurs dans leurs différents comptes
* @author Theo Peltier & Stephane Maurin
* @version 2.0
* =========================================================================================== */ 

public class Depense extends Operation {

	// Constantes
	final static int NB_DEPENSES_MAX = 64000;
	
	// Initialisation capture clavier 
	final static Scanner clavier = new Scanner(System.in);

	// Variables globales
	public static int nb_depenses = 0 ;
	public static Depense[] tab_depenses = new Depense[NB_DEPENSES_MAX] ;


	private static String[] tab_categorie_depense = {"alimentation","transports","logement","impots","loisirs","dons/cadeaux","autres","vide","vide","vide","vide","vide","vide","vide","vide"} ;

	

	// Variables d'instance
	private String lieu;


	// Constructeur
	public Depense(float montant, int categorie, String destinataire, String description, String lieu, Compte compte_lie){
		super(montant, categorie, destinataire, description, compte_lie);
		this.lieu = lieu;
	
	}


	public static void nouvelleDepense() { //méthode permettant de créér une nouvelle dépense pas à pas

		System.out.println("Entrez le montant de la dépense");
		float new_montant = clavier.nextFloat(); clavier.nextLine();

		System.out.println("Choissisez la catégorie de la dépense");
		for (int i=0 ; i<15 ; i++) //liste les catégories actuelles
			System.out.print(i+1 + ": " + tab_categorie_depense[i] + "  "); System.out.println("");
		int new_categorie = clavier.nextInt(); clavier.nextLine();

		System.out.println("Saissisez le destinataire de la dépense");
		String new_destinataire = clavier.nextLine();

		System.out.println("Saissisez le lieu où a été effectué la dépense");
		String new_lieu = clavier.nextLine();

		System.out.println("Saisissez une description de la dépense");
		String new_description = clavier.nextLine();

		tab_depenses[nb_depenses] = new Depense(new_montant, new_categorie, new_destinataire, new_description, new_lieu, Compte.getCompteActuel());
		//stocke la nouvelle dépense dans le tableau tab_depenses, dans la prochaine case dispo

		nb_depenses++ ;
		Compte.setSoldeEnMoins(new_montant) ; //met à jour le solde du compte en soustrayant au solde le montant de la dépense 
	}

	public static void visualiserDepense(Depense la_depense) { //méthode affichant toutes les informations relatives à une dépense
		System.out.println("Une dépense de " + convCat((la_depense).categorie) + " d'un montant de " + (la_depense).montant + " € a été effectué le " + ClassePrincipale.getDateDuJour() + " à destination de " + (la_depense).destinataire + " à " + (la_depense).lieu );	}

	public static void visualiser_X_dernieres_depenses(int x){ //méthode affichant toutes les informations relatives au x dernières dépenses
		for (int i = nb_depenses; i>(nb_depenses-x) ; i-- )
			visualiserDepense(tab_depenses[i-1] );	}

	public static void rechercheMotCle(String mot) { //méthode affichant toutes les dépenses comportant dans un de leurs champs 'destinataire' 'description' ou 'lieu' le mot clé donnant en paramètre 
		boolean trouve=false;
		for (int i=0 ; i< nb_depenses ; i++) {
			if ( ( (tab_depenses[i].destinataire).toLowerCase() ).contains(mot) || ((tab_depenses[i].lieu)).toLowerCase() .contains(mot) || ( (tab_depenses[i].description)).toLowerCase() .contains(mot) && tab_depenses[i].compte_lie == Compte.getCompteActuel()) {
				visualiserDepense(tab_depenses[i] );
				trouve=true;
			}
		}
		if (!trouve)
			System.out.println("Aucun résultats");
	}

	public static String convCat(int num_cat) { //Méthode retournant la chaine de caractère correspondant à la catégorie donnée en paramètre, permet d'éviter de stocker la chaine de caractère
		return tab_categorie_depense[num_cat-1] ;
	}

	public static void modifierCategorie() { //Méthode permettant à l'utilisateur de modifier les différentes catégories de dépenses disponibles
		int num_cat ;
		String new_categorie ;

		for (int i=0 ; i<15 ; i++) //liste les catégories actuelles
			System.out.print(i+1 + ": " + tab_categorie_depense[i] + "  ");

		System.out.println("\nQuelle catégorie voulez-vous modifier ?");
		num_cat = clavier.nextInt(); clavier.nextLine() ;
		System.out.println("Quel sera le nom de la categorie ?");
		new_categorie = clavier.nextLine();

		tab_categorie_depense[num_cat-1] = new_categorie;
		System.out.println("La catégorie " + new_categorie + " a été ajoutée.");
	}
	
	public static int getNbRevenus(Compte compte) { // retourne le nombre de revenus recus par le compte spécifié en paramètre
		int nb_depenses_du_compte = 0 ;
		for (int i=0; i<nb_depenses ; i++) {
			if (tab_depenses[i].compte_lie == compte)
				nb_depenses_du_compte++;
		}
		return nb_depenses_du_compte;
	}
	
	public static void afficherQuandMontantEntre(double montant_min, double montant_max) { //Méthode qui affiche les dépenses dont le montant est compris entre ceux donnés en paramètre
		for (int i=0;i<nb_depenses ; i++) {
			if (tab_depenses[i].compte_lie == Compte.getCompteActuel() && tab_depenses[i].montant >= montant_min && tab_depenses[i].montant <= montant_max) 
				visualiserDepense(tab_depenses[i]);
		}

	}
}