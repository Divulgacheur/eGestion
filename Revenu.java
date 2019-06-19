
import java.util.Scanner;

/* ===========================================================================================
 * Classe représentant les revenus reçus par les utilisateurs dans leurs différents comptes
 * @author Theo Peltier
 * @version 2.0
* =========================================================================================== */ 

public class Revenu extends Operation{
	
	// Constantes
	final static int NB_REVENUS_MAX = 64000;
	
	// Initialisation capture clavier 
	static Scanner clavier = new Scanner(System.in);
	
	// Variables globales
	public static int nb_revenus = 0 ;
	private static Revenu[] tab_revenus = new Revenu[NB_REVENUS_MAX] ; 
	

	private static String[] tab_categorie_revenus = {"salaire","allocation","cadeau","rente","revenu foncier","reboursement securité sociale","remboursement frais pro","vide","vide","vide","vide","vide","vide","vide","vide","vide"} ;

	
	
	// Constructeur
	public Revenu(float montant, int categorie, String destinataire, String description ,Compte compte_lie){
		super(montant,categorie,destinataire, description, compte_lie);
	}
	
	
	public static void nouveauRevenu() { //méthode permettant de créer un revenu pas à pas
	
		
		
		String new_provenance, new_description;
		System.out.println("Entrez le montant du revenu");
		float new_montant = clavier.nextFloat(); clavier.nextLine();

		System.out.println("Choissisez la catégorie du revenu");
		for (int i=0 ; i<15 ; i++) //liste les catégories actuelles
			System.out.print(i+1 + ": " + tab_categorie_revenus[i] + "  "); System.out.println("");
		int new_categorie = clavier.nextInt(); clavier.nextLine();

		System.out.println("Saissisez la provenance du revenu");
		new_provenance = clavier.nextLine();
		
		
		System.out.println("Saisissez une description du revenu");
		new_description = clavier.nextLine();		
		
		tab_revenus[nb_revenus] = new Revenu(new_montant, new_categorie, new_provenance, new_description, Compte.getCompteActuel());
		
		
		nb_revenus++ ;
		Compte.setSoldeEnPlus(new_montant) ;
	}
	
	public static void visualiserRevenu(Revenu le_revenu) { //méthode permettant d'afficher toutes les informations relatives à un revenu
		System.out.println("Une revenu de catégorie " + convCat((le_revenu).categorie) + " d'un montant de " + (le_revenu).montant + " € a été reçu le " + ClassePrincipale.getDateDuJour() + " en provenance de " + (le_revenu).destinataire );	}
	
	public static void visualiser_X_derniers_revenus(int x){
		for (int i = nb_revenus; i>(nb_revenus-x) ; i-- )
			visualiserRevenu(tab_revenus[i-1] );	}
		
	public static void rechercheMotCle(String mot) { //entrez mot clé à rechercher dans les champ 'destinataire' 'lieu' et 'description
		for (int i=0 ; i< nb_revenus ; i++) {
			if ( ( (tab_revenus[i].destinataire.toLowerCase()).contains(mot)  || ((tab_revenus[i].description).toLowerCase() ).contains(mot)) && tab_revenus[i].compte_lie==Compte.getCompteActuel() )
				visualiserRevenu(tab_revenus[i] );
		}
	}
	
	public int getNombreDeRevenus(){ //accesseur retournant le nombre de revenus
		return nb_revenus;	}
	
	
	public static String convCat(int num_cat) { //Permet d'éviter de stocker la chaine de caractère
		return tab_categorie_revenus[num_cat-1] ; 
	}
	
	public static void modifierCategorie() { //méthode permettant à l'utilisateur de modifier les catégories de revenus
		int num_cat ;
		String new_categorie ;
		
		for (int i=0 ; i<15 ; i++)
			System.out.print(i+1 + ": " + tab_categorie_revenus[i] + "  ");	
		
		System.out.println("\nQuelle catégorie voulez-vous modifier ?");
		num_cat = clavier.nextInt(); clavier.nextLine() ;
		System.out.println("Quel sera le nom de la categorie ?");
		new_categorie = clavier.nextLine();
		
		tab_categorie_revenus[num_cat-1] = new_categorie;
		System.out.println("La catégorie " + new_categorie + " a été ajoutée.");
	}
	
	public static int getNbRevenus(Compte compte) { //méthode retournant le nombre total de revenus d'un compte spécifié en paramètre
		int nb_revenus_du_compte = 0 ;
		for (int i=0; i<nb_revenus ; i++) {
			if (tab_revenus[i].compte_lie == compte)
				nb_revenus_du_compte++;
		}
		return nb_revenus_du_compte;
	}
	
	public static void afficherQuandMontantEntre(double montant_min, double montant_max) { //méthode permettant d'afficher les revenus dont le montant est compris entre ceux donnés en paramètres
		for (int i=0; i<nb_revenus ; i++) {
			if (tab_revenus[i].compte_lie == Compte.getCompteActuel() && ( tab_revenus[i].montant < montant_max && tab_revenus[i].montant > montant_min ))
				visualiserRevenu(tab_revenus[i]);
		}
	}
}