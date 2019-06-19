
import java.util.Date;
import java.util.Scanner;
import java.text.SimpleDateFormat;

/* ===========================================================================================
* Classe contenant la méthode main ainsi que les méthodes relatives à l'affichage des menus
* @author Theo Peltier, Stephane Maurin
* @version 2.0
* =========================================================================================== */ 

public class ClassePrincipale {

	// Initialisation capture clavier 
	static Scanner clavier = new Scanner(System.in);	
	
	public static String getDateDuJour() { //méthode retournant la date actuelle
		Date aujourdhui = new Date();
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		return formater.format(aujourdhui);
	}

	public static void main(String[] args) {
		System.out.println("Bienvenue dans votre application de gestion de dépenses, fait pour vous, étudiants aux fins de mois difficiles, pour mieux gérer votre budget.");
		lancement();
		afficher_menu_principal();
	  }
	
	public static void lancement() { //Méthode exécutant les actions minimales nécessaires au bon fonctionnement de l'application, c'est à dire la création d'au moins un utilisateur, sa sélction et la création d'au moins un compte et sa sélection
		Utilisateur.creerUtilisateur();
		Utilisateur.choix_Utilisateur();	
		Compte.nouveauCompte();
		Compte.changerCompte();
	}
	
	public static void afficher_menu_principal() { //affiche le menu de choix principal de l'application
		
		System.out.println("Que voulez-vous faire ?");
		System.out.println("1:Nouvelle dépense  2:Nouveau revenu  3:Recherche  4:Recapitulatif du compte  5:Menu Compte  6:Menu Utilisateur 7:Paramètres");
		byte choix = clavier.nextByte(); clavier.nextLine();
		switch(choix) {
			case 1 : Depense.nouvelleDepense(); break;
			case 2 : Revenu.nouveauRevenu(); break;
			case 3 : afficher_menu_recherche();
			case 4 : Compte.afficherInfoCompte( Compte.getCompteActuel() );
					 Compte.calculTaux(Compte.getCompteActuel()); break;
			case 5 : afficher_menu_compte(); break;
			case 6 : afficher_menu_utilisateur(); break;
			case 7 : afficher_menu_parametres() ; break ;
		}
		afficher_menu_principal();
	}
	
	public static void afficher_menu_compte() { //affiche le menu des actions relatives aux comptes
		
		System.out.println("1:Lister compte(s)  2:Nouveau compte  3:Supprimer compte  4:Menu principal ");
		byte choix = clavier.nextByte(); clavier.nextLine();
		switch(choix) {
			case 1 : Compte.changerCompte(); break;
			case 2 : Compte.nouveauCompte(); break;
			case 3 : Compte.supprimerCompte(); break;
			case 4 : afficher_menu_principal(); break;
		}
		afficher_menu_principal();
	}
	
	public static void afficher_menu_utilisateur() { //affiche le menu des actions relatives aux utilisateurs
		
		System.out.println("1:Lister utilisateur(s)  2:Nouvel utilisateur  3:Menu principal");
		byte choix = clavier.nextByte(); clavier.nextLine();
		switch(choix) {
			case 1 : Utilisateur.choix_Utilisateur(); break;
			case 2 : Utilisateur.creerUtilisateur(); break;

			case 3 : afficher_menu_principal(); break;
		}
		afficher_menu_principal();
	}
	
	public static void afficher_menu_recherche() { //affiche le menu relatif à la recherche d'opérations
		
		System.out.println("1: Rechercher opération par mot-clé  | 2: Recherche opération par montant | 3: Recherche par catégorie | 4: Menu principal");
		byte choix = clavier.nextByte(); clavier.nextLine();
		switch(choix) {
			case 1 : rechercheOperationMotCle(); break;
			case 2 : rechercherMontantOperation(); break;
		//	case 3 : rechercheOperationCategorie(); break;		 à écrire
			case 4 : afficher_menu_principal(); break;
		}
		afficher_menu_principal();
	}
	
	public static void afficher_menu_parametres() { //affiche le menu relatif aux paramètres modifiables et propres à l'application
		
		System.out.println("1: Modifier catégories dépenses | 2: Modifier catégories revenus | 3: Menu principal");
		byte choix = clavier.nextByte(); clavier.nextLine();
		switch(choix) {
			case 1 : Depense.modifierCategorie(); break;
			case 2 : Revenu.modifierCategorie(); break;
			case 3 : afficher_menu_principal(); break;
		}
		afficher_menu_principal();
		
	}
	public static void rechercheOperationMotCle() { //méthode utilisée lors du choix de la recherche d'une opération par mot-clé
		System.out.println("Entrez le mot clé à rechercher");
		String chaine_a_rechercher = clavier.nextLine();
		Depense.rechercheMotCle(chaine_a_rechercher);
		Revenu.rechercheMotCle(chaine_a_rechercher);
	}

	public static void rechercherMontantOperation() { //méthode utilisée lors du choix de la recherche d'une opération par montant
		System.out.print("Je veux chercher les opérations dont le montant en euros est compris entre ");
		double montant_min = clavier.nextDouble();
		System.out.print(" € et ");
		double montant_max = clavier.nextDouble();
		System.out.print(" €.");
		Depense.afficherQuandMontantEntre(montant_min,montant_max);
		Revenu.afficherQuandMontantEntre(montant_min,montant_max);		
	}
	
	
}
