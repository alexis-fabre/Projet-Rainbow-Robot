/*
 * JeuRainbow.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package metier;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Classe qui va permettre de gérer plusieurs partie et de les récupérer à
 * partir d'un fichier créer par l'utilisateur.
 * 
 * @author Rainbow Robot
 */
public class JeuRainbow implements Serializable {

	/**
	 * Génerer automatiquement par Eclipse
	 */
	private static final long serialVersionUID = 1725976228595779419L;

	/** Niveau par défaut de la partie */
	public static final int DEFAULT_NIVEAU = 0;

	/** Niveau maximal que le joueur a atteint */
	private static int niveauMax;

	/** Niveau actuel du joueur */
	private int niveauCourant;

	/** Carte des parties enregistrées dans le fichier */
	private ArrayList<Partie> partiesEnregistrees;

	/** Carte de la partie courante du mode Story */
	private transient Partie partieJouable;

	/** Nom du fichier ou se trouve les parties jouables dans le mode solo */
	public static final String CHEMIN_FICHIER_PARTIE = "./Ressource/lib/partie_mode_solo.bin";

	/** Nom du fichier ou se trouve la sauvegarde du mode Story */
	public static final String CHEMIN_FICHIER_SAUVEGARDE = "./Ressource/sauvegarde.txt";

	/**
	 * Constructeur par défaut pour créer les parties
	 */
	public JeuRainbow() {
		partiesEnregistrees = new ArrayList<Partie>();
		niveauCourant = DEFAULT_NIVEAU;
		// On restaure la sauvegarde
		restaurerSauvegarde();
	}

	/**
	 * Ajoute un niveau (ou une partie) pour le mode story.
	 * 
	 * @param aAjouter
	 *            nouveau niveau à ajouter
	 */
	public void addPartie(Partie aAjouter) {
		partiesEnregistrees.add(aAjouter);
	}

	/**
	 * Pour récupérer un clone de la Carte du niveau courant. Cela permet entre
	 * autre une fois le niveau fini de pouvoir le recommencer.
	 * 
	 * @return la carte du niveau courant
	 */
	public Partie getPartieCourante() {
		if (partieJouable == null) {
			partieJouable = partiesEnregistrees.get(niveauCourant);
			try {
				partieJouable = (Partie) partiesEnregistrees.get(niveauCourant)
						.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		partieJouable.getRobot().setPartie(partieJouable);
		return partieJouable;
	}

	/**
	 * @return le niveau courant
	 */
	public int getNiveau() {
		return niveauCourant;
	}

	/**
	 * @return le nombre de niveau de mode story
	 */
	public int getNbNiveau() {
		return partiesEnregistrees.size();
	}

	/**
	 * @return le niveau max que le joueur a atteint
	 */
	public int getNiveauMax() {
		return niveauMax;
	}

	/**
	 * Change le niveau courant par le niveau demandé. Si vous appelez
	 * getPartieCourante() après cette fonction vous obtiendrez la partie
	 * demandée.
	 * 
	 * @param niveau
	 *            nouveau niveau de jeu
	 */
	public void setNiveau(int niveau) {
		// On réactualise partieJouable
		reinitialiserPartie();
		niveauCourant = niveau < partiesEnregistrees.size() ? niveau
				: DEFAULT_NIVEAU;
	}

	/**
	 * Permet de passer au niveau suivant ou de retourner au niveau 0
	 */
	public void setNiveauSuivant() {
		if (niveauMax < niveauCourant + 1) {
			niveauMax = niveauCourant + 1;
			// On sauvegarde dans un autre Thread pour ne pas perturber
			// l'utilisateur dans sa nouvelle partie
			new Thread(new Runnable() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see java.lang.Runnable#run()
				 */
				@Override
				public void run() {
					sauvegarderJeu();
				}
			}).start();
			;
		}
		setNiveau(niveauCourant + 1);
	}

	/**
	 * Réinitialise la partie courante
	 */
	public void reinitialiserPartie() {
		partieJouable = null;
	}

	/**
	 * Sauvegarde la progression du mode Story
	 */
	public static void sauvegarderJeu() {
		try (PrintWriter fichier = new PrintWriter(new FileWriter(
				CHEMIN_FICHIER_SAUVEGARDE))) {
			fichier.print(niveauMax);
		} catch (IOException fichierInexistant) {
			System.out.println("Fichier inexistant à l'emplacement "
					+ CHEMIN_FICHIER_SAUVEGARDE);
		}
	}

	/** Restaure la sauvegarde du mode story */
	public static void restaurerSauvegarde() {
		try (BufferedReader fichier = new BufferedReader(new FileReader(
				CHEMIN_FICHIER_SAUVEGARDE))) {
			niveauMax = Integer.parseInt(fichier.readLine());
		} catch (IOException donneeErronee) {
			System.out.println("Fichier inexistant à l'emplacement "
					+ CHEMIN_FICHIER_SAUVEGARDE);
		}
	}

	/**
	 * Enregistre l'état courant du jeu Rainbow Robot et de ses parties
	 * associées
	 * 
	 * @param aEnregistrer
	 *            jeu à enregistrer
	 */
	public static void enregistrerFichier(JeuRainbow aEnregistrer) {
		try (
		// On détecte le fichier avec le nom NOM_FICHIER avec un analyseur de
		// flux de sortie de données sous forme d'octets
		// On utilise un buffer pour gagner en temps
		BufferedOutputStream fichier = new BufferedOutputStream(
				new FileOutputStream(CHEMIN_FICHIER_PARTIE));

		// ObjectOutputStream permet de sérialiser un objet ou ici un graphe
		// d'objet
				ObjectOutputStream fluxSortie = new ObjectOutputStream(fichier)) {

			// On écrit l'objet dans le fichier
			fluxSortie.writeObject(aEnregistrer);

			// Avec le try-with-resources les objets ObjectOutputStream et
			// BufferedOutputStream se ferme (close()) automatiquement

		} catch (FileNotFoundException fichierInexistant) {
			// Utilisé pour le FileOutputStream
			// Si le fichier n'existe pas

		} catch (IOException donneeErronee) {
			// Utilisé pour le ObjectOutputStream
			// Si un problème avec le fichier apparaît
			// L'exception est liée à l'entrée/sortie des données sur le fichier

		}
	}

	/**
	 * Permet d'initialiser le JeuRainbow à partir d'un fichier (le nom du
	 * fichier est mentionné dans la variable interne CHEMIN_FICHIER_PARTIE
	 * 
	 * @return le JeuRainbow fournit par le fichier
	 */
	public static JeuRainbow lectureFichier() {
		JeuRainbow tampon = null;
		try (

		// On détecte le fichier avec le nom NOM_FICHIER avec un analyseur de
		// flux d'entrée de données sous forme d'octets
		// On utilise un buffer pour gagner en temps
		BufferedInputStream fichier = new BufferedInputStream(
				new FileInputStream(CHEMIN_FICHIER_PARTIE));

		// ObjectInputStream permet de lire un objet ou ici un graphe d'objet
				ObjectInputStream fluxEntree = new ObjectInputStream(fichier)) {

			// On lit l'objet du fichier et on le convertis en Agenda
			tampon = (JeuRainbow) fluxEntree.readObject();

			// Avec le try-with-resources les objets ObjectInputStream et
			// BufferedInputStream se ferme (close()) automatiquement

		} catch (FileNotFoundException fichierInexistant) {
			// Utilisé pour le FileInputStream et le BufferedInputStream
			// Si le fichier n'existe pas

		} catch (IOException donneeErronee) {
			// Utilisé pour le ObjectInputStream
			// Si un problème avec le fichier apparaît
			// L'exception est liée à l'entrée/sortie des données sur le fichier

		} catch (ClassNotFoundException classeInexistant) {
			// Si la classe n'existe plus dans l'application
		}

		return tampon;
	}

	/**
	 * Permet de faire revenir le fichier des parties à une état stable défini
	 * par le modèle sur le cahier des charges.
	 */
	public static void restartFichierPartie() {

		JeuRainbow jeu = new JeuRainbow();

		// ---------------------------------------------------------------------
		// 1ère partie
		// ---------------------------------------------------------------------
		int nbLigne = 9;
		int nbColonne = 11;

		// On ne calcule les positions inaccessibles
		Position[] positionsInaccessibles = new Position[16];
		// Angle en haut à gauche
		positionsInaccessibles[0] = new Position(0, 0);
		positionsInaccessibles[1] = new Position(0, 1);
		positionsInaccessibles[2] = new Position(1, 0);
		positionsInaccessibles[3] = new Position(1, 1);

		// Angle en haut à droite
		positionsInaccessibles[4] = new Position(nbColonne - 1, 0);
		positionsInaccessibles[5] = new Position(nbColonne - 1, 1);
		positionsInaccessibles[6] = new Position(nbColonne - 2, 0);
		positionsInaccessibles[7] = new Position(nbColonne - 2, 1);

		// Angle en bas à gauche
		positionsInaccessibles[8] = new Position(0, nbLigne - 1);
		positionsInaccessibles[9] = new Position(1, nbLigne - 1);
		positionsInaccessibles[10] = new Position(0, nbLigne - 2);
		positionsInaccessibles[11] = new Position(1, nbLigne - 2);

		// Angle en bas à droite
		positionsInaccessibles[12] = new Position(nbColonne - 1, nbLigne - 1);
		positionsInaccessibles[13] = new Position(nbColonne - 2, nbLigne - 1);
		positionsInaccessibles[14] = new Position(nbColonne - 1, nbLigne - 2);
		positionsInaccessibles[15] = new Position(nbColonne - 2, nbLigne - 2);

		ArrayList<Caisse> caisseARecuperee = new ArrayList<Caisse>();
		caisseARecuperee.add(new Caisse(Caisse.ROUGE));
		caisseARecuperee.add(new Caisse(Caisse.BLEU));
		caisseARecuperee.add(new Caisse(Caisse.JAUNE));

		Caisse[] caissePlateau = new Caisse[5];
		caissePlateau[0] = new Caisse(Caisse.ROUGE, new Position(5, 2));
		caissePlateau[1] = new Caisse(Caisse.BLEU, new Position(8, 6));
		caissePlateau[2] = new Caisse(Caisse.JAUNE, new Position(7, 7));
		caissePlateau[3] = new Caisse(Caisse.ORANGE, new Position(10, 6));
		caissePlateau[4] = new Caisse(Caisse.VERT, new Position(7, 8));

		// Le vortex
		Vortex vortex = new Vortex(new Position(5, 4));

		// On créer le robot
		Robot robot = new Robot(Robot.ORIENTATION_GAUCHE, new Position(6, 4));

		// On créer la partie
		Partie partie = new Partie(nbLigne, nbColonne, positionsInaccessibles,
				robot, vortex, caisseARecuperee, caissePlateau);

		// On ajoute la partie au jeu
		jeu.addPartie(partie);

		// ---------------------------------------------------------------------
		// 2ème partie
		// ---------------------------------------------------------------------
		nbLigne = 9;
		nbColonne = 11;

		positionsInaccessibles = new Position[16];
		positionsInaccessibles[0] = new Position(0, 0);
		positionsInaccessibles[1] = new Position(0, 1);
		positionsInaccessibles[2] = new Position(1, 0);
		positionsInaccessibles[3] = new Position(1, 1);
		positionsInaccessibles[4] = new Position(nbColonne - 1, 0);
		positionsInaccessibles[5] = new Position(nbColonne - 1, 1);
		positionsInaccessibles[6] = new Position(nbColonne - 2, 0);
		positionsInaccessibles[7] = new Position(nbColonne - 2, 1);
		positionsInaccessibles[8] = new Position(0, nbLigne - 1);
		positionsInaccessibles[9] = new Position(1, nbLigne - 1);
		positionsInaccessibles[10] = new Position(0, nbLigne - 2);
		positionsInaccessibles[11] = new Position(1, nbLigne - 2);
		positionsInaccessibles[12] = new Position(nbColonne - 1, nbLigne - 1);
		positionsInaccessibles[13] = new Position(nbColonne - 2, nbLigne - 1);
		positionsInaccessibles[14] = new Position(nbColonne - 1, nbLigne - 2);
		positionsInaccessibles[15] = new Position(nbColonne - 2, nbLigne - 2);

		caisseARecuperee = new ArrayList<Caisse>();
		caisseARecuperee.add(new Caisse(Caisse.JAUNE));
		caisseARecuperee.add(new Caisse(Caisse.BLEU));

		caissePlateau = new Caisse[18];
		caissePlateau[0] = new Caisse(Caisse.JAUNE, new Position(5, 7));
		caissePlateau[1] = new Caisse(Caisse.ROUGE, new Position(4, 6));
		caissePlateau[2] = new Caisse(Caisse.ROUGE, new Position(5, 6));
		caissePlateau[3] = new Caisse(Caisse.ROUGE, new Position(6, 6));
		caissePlateau[4] = new Caisse(Caisse.ROUGE, new Position(4, 7));
		caissePlateau[5] = new Caisse(Caisse.ROUGE, new Position(6, 7));
		caissePlateau[6] = new Caisse(Caisse.ROUGE, new Position(4, 8));
		caissePlateau[7] = new Caisse(Caisse.ROUGE, new Position(5, 8));
		caissePlateau[8] = new Caisse(Caisse.ROUGE, new Position(6, 8));

		caissePlateau[9] = new Caisse(Caisse.BLEU, new Position(5, 1));
		caissePlateau[10] = new Caisse(Caisse.VERT, new Position(4, 0));
		caissePlateau[11] = new Caisse(Caisse.VERT, new Position(5, 0));
		caissePlateau[12] = new Caisse(Caisse.VERT, new Position(6, 0));
		caissePlateau[13] = new Caisse(Caisse.VERT, new Position(4, 2));
		caissePlateau[14] = new Caisse(Caisse.VERT, new Position(5, 2));
		caissePlateau[15] = new Caisse(Caisse.VERT, new Position(6, 2));
		caissePlateau[16] = new Caisse(Caisse.VERT, new Position(4, 1));
		caissePlateau[17] = new Caisse(Caisse.VERT, new Position(6, 1));

		vortex = new Vortex(new Position(8, 5));

		robot = new Robot(Robot.ORIENTATION_DROITE, new Position(0, 4));

		partie = new Partie(nbLigne, nbColonne, positionsInaccessibles, robot,
				vortex, caisseARecuperee, caissePlateau);

		jeu.addPartie(partie);

		// On enregistre dans le fichier
		// On le lit au cas ou il n'existerais pas
		lectureFichier();
		enregistrerFichier(jeu);
	}

	public static Partie carteAleatoire() {
		Random rand = new Random();
		int colonne = (rand.nextInt(17)) + 2;
		int ligne = (rand.nextInt(10)) + 2;
		int nbCaisses = ((ligne * colonne) / (rand.nextInt(5) + 1));
		Caisse[] caissePlateau = new Caisse[nbCaisses];
		Robot robot = new Robot(rand.nextInt(4), new Position(
				rand.nextInt(colonne), rand.nextInt(ligne)));
		Vortex vortex = new Vortex(new Position(rand.nextInt(colonne),
				rand.nextInt(ligne)));
		ArrayList<Caisse> caisseARecuperer = new ArrayList<Caisse>();
		int indice = rand.nextInt(6) + 1;
		for (int i = 0; i <= indice; i++) {
			caisseARecuperer.add(new Caisse(rand.nextInt(6) + 1));

		}

		for (int i = 0; i < nbCaisses; i++) {
			caissePlateau[i] = new Caisse((rand.nextInt(6) + 1), new Position(
					rand.nextInt(colonne), rand.nextInt(ligne)));
		}

		try {
			return new Partie(ligne, colonne, null, robot, vortex,
					caisseARecuperer, caissePlateau);
		} catch (IllegalArgumentException e) {
			return carteAleatoire();
		}
	}

}
