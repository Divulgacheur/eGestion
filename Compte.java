
import java.util.Scanner;

/* ===========================================================================================
 * Classe représentant les différents comptes bancaires des utilisateurs de l'application
 * @author Theo Peltier, Stephane Maurin
 * @version 2.0
 * =========================================================================================== */ 

public class Compte {
	
	// Constantes
	final static int NB_COMPTES_MAX = 50 ;
	
	// Initialisation capture clavier 
	final static Scanner clavier = new Scanner(System.in);
	
	// Variables globales
	private static Compte[] tab_comptes = new Compte[NB_COMPTES_MAX] ;
	private static int nb_Comptes_Enregistres_Total = 0;
	private static Compte compte_actuel ;
	
	private static String[] tab_type_compte_nom = {"Compte courant","Livret A","Plan d'Epargne Logement","Plan d'Epargne en Actions","Livret Jeune"} ;
	private static double [] tab_type_compte_taux = {0.00, 0.75, 1.00, 0.00, 1.75 };
	
	
	// Variables d'instance
	private int type_compte;
	private double solde;
	private Utilisateur id_utilisateur_proprietaire;
	private String nom;
	
	//Constructeur
	Compte(int type_compte, String nom, double solde, Utilisateur id_utilisateur_proprietaire){
		this.nom =nom;
		this.type_compte=type_compte;
		this.solde=solde;
		this.id_utilisateur_proprietaire=id_utilisateur_proprietaire;	}
	
	
	public static void nouveauCompte() { //méthode permettant de créer un compte pas à pas
		
		System.out.println("Création d'un nouveau compte bancaire pour l'utilisateur " + Utilisateur.getNomUtilisateurActif() );
		
		System.out.println("Veuillez choisir le type de compte souhaité");
		System.out.println("1. Compte courant - 2. Livret A - 3. Plan d'épargne logement 4. Plan d'épargne en actions 5. Livret jeune");
		int choix_type_compte = clavier.nextInt(); clavier.nextLine();
		while (choix_type_compte < 1 || choix_type_compte > 5) {
			System.out.println("Choisissiez un des types existants : 1, 2, 3, 4 ou 5");
			choix_type_compte = clavier.nextInt(); clavier.nextLine();	}
		
		System.out.println("Veuillez entrer un nom pour le compte bancaire");
		String new_nom = clavier.nextLine();
		
		System.out.println("Entrez le montant déjà présent sur votre compte");
		double new_solde = clavier.nextDouble(); clavier.nextLine();
		
		System.out.println("Votre nouveau compte " + new_nom + " a été créé avec succès.");
		
		tab_comptes[nb_Comptes_Enregistres_Total] = new Compte(choix_type_compte, new_nom, new_solde, Utilisateur.getUtilisateurActif() ); //un objet de type Compte est créé et stocké dans tab_comptes
		nb_Comptes_Enregistres_Total++ ;
		
		System.out.println("Voulez-vous créer un autre compte bancaire ? Tapez 1 pour Oui");
		byte choix_creation_encore_un_autre_compte = clavier.nextByte(); clavier.nextLine();
		if (choix_creation_encore_un_autre_compte == 1)
			nouveauCompte();
	}
	
	public static void afficherInfoCompte(Compte compte_a_afficher) { //méthode permettant d'afficher toutes les informations relatives à un compte
		System.out.println("Ce compte "+ tab_type_compte_nom[compte_a_afficher.type_compte-1] + " contient actuellement " + String.format("%.2f",compte_a_afficher.solde) + "€. son taux est de "+ tab_type_compte_taux[compte_a_afficher.type_compte-1] + "%");
		System.out.println(Depense.getNbRevenus(compte_a_afficher) + " dépenses ont été effectuées et " + Revenu.getNbRevenus(compte_a_afficher) + " revenus ont été reçus.");
		if (compte_a_afficher.solde < 0)
			System.out.println("Vous êtes à découvert ! :C ");
	}
	
	public static void changerCompte() { //méthode permettant à l'utilisateur de naviguer parmi ses comptes et de sélectionner celui sur lequel il souhaite effectuer des opérations
		int choix_compte ;
		int i;
		if (nb_Comptes_Enregistres_Total < 2) {
			choix_compte = 1;
		}
		else {
			System.out.println("Veuillez sélectionner un compte");
			for (i=0 ; i< nb_Comptes_Enregistres_Total ; i++) {
				if (tab_comptes[i].id_utilisateur_proprietaire == Utilisateur.getUtilisateurActif() )
					System.out.println((i+1) +": "+ tab_comptes[i].nom);
			}
			choix_compte = clavier.nextInt(); clavier.nextLine();
		}
		compte_actuel = tab_comptes[choix_compte-1] ;
		System.out.println("Compte actuel : " + compte_actuel.nom);
		
	}
	
	public static void supprimerCompte() { //méthode permettant à l'utilisateur de supprimer l'un de ses comptes
		int choix;
		System.out.println("Quel compte voulez-vous supprimer ?");
		
		for (int i=0 ; i< nb_Comptes_Enregistres_Total ; i++) {
			if (tab_comptes[i].id_utilisateur_proprietaire == Utilisateur.getUtilisateurActif() )
				System.out.println((i+1) +": "+ tab_comptes[i].nom);
		}
		choix = clavier.nextInt(); clavier.nextLine() ;
		Compte compte_a_supprimer = tab_comptes[choix-1] ;
		
		if (compte_a_supprimer.solde > 0) { //Un compte doit être vide pour être supprimé
			System.out.println("Impossible de supprimer ce compte, il y a encore de l'argent dedans");
			return; 
		}
		
		String nom_mechant = compte_a_supprimer.nom ;
		int index = 0;
		for (int i=0 ; i<nb_Comptes_Enregistres_Total ; i++) { 
			if (tab_comptes[i] == compte_a_supprimer) { // 
				index=i;
				break;
			}
		}
		for (int i=index ; i< (nb_Comptes_Enregistres_Total-1) ; i++ ) {
			tab_comptes[i] = tab_comptes[i+1] ;
		}
		System.out.println("Votre compte "+ nom_mechant + " été supprimé.");
		nb_Comptes_Enregistres_Total-- ;
	}
	
	public static void calculTaux(Compte compte) { //méthode permettant de calculer les interets d'un compte en fonction de son taux
		System.out.println("Vos intêrets cette année : " + String.format("%.2f", tab_type_compte_taux[compte.type_compte-1]* 0.01 * compte.solde) + "€.") ;
	}

	public static void setSoldeEnMoins(double new_solde) {	//méthode permettant de soustraire une somme au solde d'un compte
		compte_actuel.solde -= new_solde;
	}
	public static void setSoldeEnPlus(double new_solde) { //méthode permettant d'ajouter une somme au solde d'un compte
		compte_actuel.solde += new_solde;
	}
	public static Compte getCompteActuel() { //méthode retournant le compte qui est actuellement celui où l'on effectue les opérations
		return compte_actuel;
	}

}