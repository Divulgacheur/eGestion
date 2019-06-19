
import java.util.Scanner;

/* ===========================================================================================
 * Classe représentant les différents utilisateurs de l'application
 * @author Theo Peltier, Stephane Maurin
 * @version 2.0
* =========================================================================================== */ 

public class Utilisateur {
	
	// Initialisation capture clavier 
	static Scanner clavier = new Scanner(System.in);
	
	// Constantes
	final static int NB_UTILISATEURS_MAX = 10;
	
	// Variables globales
	public static Utilisateur ID_Utilisateur_Actuel;
	public static int nb_Utilisateurs_Enregistres = 0;
	public static Utilisateur[] tab_utilisateurs = new Utilisateur[NB_UTILISATEURS_MAX] ;


	// Variables d'instances
	private String nom;
	
	// Constructeur
	public Utilisateur( String nom){
		this.nom = nom;	
	}
	
	public static void creerUtilisateur() { // méthode pour créer un nouvel utilisateur pas à pas
		
		String new_nom;
		boolean new_nom_ok = false ;
		System.out.println("Création d'un nouvel utilisateur.");
		System.out.println("Entrez votre nom :");
		
		new_nom = clavier.nextLine();
		
		//On parcourt le tableau des utilisateurs pour vérifier si le nom n'est pas déjà utilisé
		while (new_nom_ok == false) {
			new_nom_ok = true;
			for (int i=0; i<nb_Utilisateurs_Enregistres ; i++) {
				if (new_nom.equals(tab_utilisateurs[i].nom) ) {  //si le nom donné est déjà le champ nom de l'un des utilisateurs enregistrés
					System.out.println("Ce nom de compte est déjà utilisé.");
					new_nom = clavier.nextLine();	//on redemande un autre nom pour l'utilisateur et on re-test s'il il est déjà donné
					new_nom_ok = false;
				}	
			}
		}

		
		tab_utilisateurs[nb_Utilisateurs_Enregistres] = new Utilisateur(new_nom); //On enregistre le nouvel utilisateur dans le tableau des utilisateurs
		nb_Utilisateurs_Enregistres++ ; //On incrémente la variable représentant le nombre d'utilisateurs inscrits
		System.out.println("Le nouvel utilisateur " + new_nom + " a été créé avec succés.");
		

		System.out.println("Voulez-vous créer un autre utilisateur ? Tapez 1 pour Oui");
		byte choix_creation_encore_un_autre_utilisateur = clavier.nextByte(); clavier.nextLine();
		if ( choix_creation_encore_un_autre_utilisateur == 1)
			creerUtilisateur();
	}
	
	public static void choix_Utilisateur() { //Permet de changer de compte utilisateur sur l'application
		int choix_utilisateur;
		if(nb_Utilisateurs_Enregistres < 2) {
			choix_utilisateur = 1;
		}
		else {	
			
			System.out.println("Veuillez sélectionner l'utilisateur sur lequel vous souhaitez travailler.");
			
			for (int i=0; i<nb_Utilisateurs_Enregistres ; i++) // listage de tous les utilisateurs existants
				System.out.println( (i+1) + ": " + tab_utilisateurs[i].nom);
	
			try {
				choix_utilisateur = clavier.nextInt(); clavier.nextLine(); //entrée du choix du compte utilisateur
			} catch (java.util.InputMismatchException e) { //si l'utilisateur entre autre chose qu'un entier, l'erreur est catché
				choix_utilisateur=0;	} //et on simule une saisie erronée	
				
			while (choix_utilisateur < 1 || choix_utilisateur > nb_Utilisateurs_Enregistres) { //si le numéro de l'utilisateur n'existe pas
					
				System.out.println("Veuillez choisir un utilisateur existant."); //on refuse tant que le numéro entré est faux
				try {
					choix_utilisateur = clavier.nextInt(); clavier.nextLine(); //entrée du choix du compte utilisateur
				} catch (java.util.InputMismatchException e) {
					choix_utilisateur=0; }
			}
		}
		ID_Utilisateur_Actuel = tab_utilisateurs[choix_utilisateur-1] ; //mis à jour de l'utilisateur actuel
		System.out.println("Vous êtes bien connecté en tant que " + ID_Utilisateur_Actuel.nom +"\n");
	}
	
	
	public static Utilisateur getUtilisateurActif() { //retourne l'utilisateur actuel
		return ID_Utilisateur_Actuel;	}
	
	public static String getNomUtilisateurActif() { //méthode retournant le nom de l'utilisateur actuel
		return ID_Utilisateur_Actuel.nom ; }
	
	
}


