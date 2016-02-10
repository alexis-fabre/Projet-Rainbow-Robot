/*
 * JeuRainbow.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package metier;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

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

	/** Niveau actuel du joueur */
	private int niveauCourant;

	/** Carte des parties enregistrées dans le fichier */
	private ArrayList<Partie> partiesEnregistrees;

	/** Carte de la partie courante du mode Story */
	private transient Partie partieJouable;

	/** Nom du fichier ou se trouve les parties jouables dans le mode solo */
	public static final String CHEMIN_FICHIER_PARTIE = "./Ressource/lib/partie_mode_solo.bin";

	/**
	 * Constructeur par défaut pour créer les parties
	 */
	public JeuRainbow() {
		partiesEnregistrees = new ArrayList<Partie>();
		niveauCourant = DEFAULT_NIVEAU;
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
		setNiveau(niveauCourant + 1);
	}

	/**
	 * Réinitialise la partie courante
	 */
	public void reinitialiserPartie() {
		partieJouable = null;
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
		int debutX = -((nbColonne - 1) / 2);
		int debutY = -((nbLigne - 1) / 2);

		// On ne calcule les positions inaccessibles
		Position[] positionsInaccessibles = new Position[16];
		// Angle en haut à gauche
		positionsInaccessibles[0] = new Position(debutX, debutY);
		positionsInaccessibles[1] = new Position(debutX, debutY + 1);
		positionsInaccessibles[2] = new Position(debutX + 1, debutY);
		positionsInaccessibles[3] = new Position(debutX + 1, debutY + 1);

		// Angle en haut à droite
		positionsInaccessibles[4] = new Position(-debutX, debutY);
		positionsInaccessibles[5] = new Position(-debutX, debutY + 1);
		positionsInaccessibles[6] = new Position(-debutX - 1, debutY);
		positionsInaccessibles[7] = new Position(-debutX - 1, debutY + 1);

		// Angle en bas à gauche
		positionsInaccessibles[8] = new Position(debutX, -debutY);
		positionsInaccessibles[9] = new Position(debutX, -debutY - 1);
		positionsInaccessibles[10] = new Position(debutX + 1, -debutY);
		positionsInaccessibles[11] = new Position(debutX + 1, -debutY - 1);

		// Angle en bas à droite
		positionsInaccessibles[12] = new Position(-debutX, -debutY);
		positionsInaccessibles[13] = new Position(-debutX, -debutY - 1);
		positionsInaccessibles[14] = new Position(-debutX - 1, -debutY);
		positionsInaccessibles[15] = new Position(-debutX - 1, -debutY - 1);

		ArrayList<Caisse> caisseARecuperee = new ArrayList<Caisse>();
		caisseARecuperee.add(new Caisse(Color.RED));
		caisseARecuperee.add(new Caisse(Color.BLUE));
		caisseARecuperee.add(new Caisse(Color.YELLOW));

		Caisse[] caissePlateau = new Caisse[5];
		caissePlateau[0] = new Caisse(Color.RED, new Position(-4, 2));
		caissePlateau[1] = new Caisse(Color.BLUE, new Position(3, 2));
		caissePlateau[2] = new Caisse(Color.YELLOW, new Position(2, 3));
		caissePlateau[3] = new Caisse(Color.ORANGE, new Position(5, 2));
		caissePlateau[4] = new Caisse(Color.GREEN, new Position(2, 4));

		// Le vortex
		Vortex vortex = new Vortex(new Position(0, 0));

		// On créer le robot
		Robot robot = new Robot(Robot.ORIENTATION_GAUCHE, new Position(1, 0));

		// On créer la partie
		Partie partie = new Partie(nbLigne, nbColonne, positionsInaccessibles,
				robot, vortex, caisseARecuperee, caissePlateau);

		// On associe la partie au robot
		robot.setPartie(partie);

		// On ajoute la partie au jeu
		jeu.addPartie(partie);

		// ---------------------------------------------------------------------
		// 2ème partie
		// ---------------------------------------------------------------------
		nbLigne = 9;
		nbColonne = 11;
		debutX = -((nbColonne - 1) / 2);
		debutY = -((nbLigne - 1) / 2);

		positionsInaccessibles = new Position[16];
		positionsInaccessibles[0] = new Position(debutX, debutY);
		positionsInaccessibles[1] = new Position(debutX, debutY + 1);
		positionsInaccessibles[2] = new Position(debutX + 1, debutY);
		positionsInaccessibles[3] = new Position(debutX + 1, debutY + 1);
		positionsInaccessibles[4] = new Position(-debutX, debutY);
		positionsInaccessibles[5] = new Position(-debutX, debutY + 1);
		positionsInaccessibles[6] = new Position(-debutX - 1, debutY);
		positionsInaccessibles[7] = new Position(-debutX - 1, debutY + 1);
		positionsInaccessibles[8] = new Position(debutX, -debutY);
		positionsInaccessibles[9] = new Position(debutX, -debutY - 1);
		positionsInaccessibles[10] = new Position(debutX + 1, -debutY);
		positionsInaccessibles[11] = new Position(debutX + 1, -debutY - 1);
		positionsInaccessibles[12] = new Position(-debutX, -debutY);
		positionsInaccessibles[13] = new Position(-debutX, -debutY - 1);
		positionsInaccessibles[14] = new Position(-debutX - 1, -debutY);
		positionsInaccessibles[15] = new Position(-debutX - 1, -debutY - 1);

		caisseARecuperee = new ArrayList<Caisse>();
		caisseARecuperee.add(new Caisse(Color.YELLOW));
		caisseARecuperee.add(new Caisse(Color.BLUE));

		caissePlateau = new Caisse[18];
		caissePlateau[0] = new Caisse(Color.YELLOW, new Position(0, 3));
		caissePlateau[1] = new Caisse(Color.RED, new Position(-1, 4));
		caissePlateau[2] = new Caisse(Color.RED, new Position(0, 4));
		caissePlateau[3] = new Caisse(Color.RED, new Position(1, 4));
		caissePlateau[4] = new Caisse(Color.RED, new Position(-1, 2));
		caissePlateau[5] = new Caisse(Color.RED, new Position(0, 2));
		caissePlateau[6] = new Caisse(Color.RED, new Position(1, 2));
		caissePlateau[7] = new Caisse(Color.RED, new Position(-1, 3));
		caissePlateau[8] = new Caisse(Color.RED, new Position(1, 3));

		caissePlateau[9] = new Caisse(Color.BLUE, new Position(0, -3));
		caissePlateau[10] = new Caisse(Color.GREEN, new Position(-1, -4));
		caissePlateau[11] = new Caisse(Color.GREEN, new Position(0, -4));
		caissePlateau[12] = new Caisse(Color.GREEN, new Position(1, -4));
		caissePlateau[13] = new Caisse(Color.GREEN, new Position(-1, -2));
		caissePlateau[14] = new Caisse(Color.GREEN, new Position(0, -2));
		caissePlateau[15] = new Caisse(Color.GREEN, new Position(1, -2));
		caissePlateau[16] = new Caisse(Color.GREEN, new Position(-1, -3));
		caissePlateau[17] = new Caisse(Color.GREEN, new Position(1, -3));

		vortex = new Vortex(new Position(3, 1));

		robot = new Robot(Robot.ORIENTATION_DROITE, new Position(-5, 0));

		partie = new Partie(nbLigne, nbColonne, positionsInaccessibles, robot,
				vortex, caisseARecuperee, caissePlateau);
		robot.setPartie(partie);

		jeu.addPartie(partie);

		// On enregistre dans le fichier
		// On le lit au cas ou il n'existerais pas
		lectureFichier();
		enregistrerFichier(jeu);
	}

}
