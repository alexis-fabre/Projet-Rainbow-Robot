/*
 * JeuRainbow.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package metier;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
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
 * Classe qui va permettre de gérer plusieurs parties et de les récupérer à
 * partir d'un fichier créer par l'utilisateur.
 * 
 * @author Rainbow Robot
 */
public class JeuRainbow implements Serializable {

	/**
	 * Géneré automatiquement par Eclipse
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

	/** Carte de la partie courante du joueur */
	private transient Partie partieJoueur;

	/** Carte de la partie courante de l'IA */
	private transient Partie partieIA;

	/** Nom du fichier ou se trouvent les parties jouables dans le mode solo */
	public static final String CHEMIN_FICHIER_PARTIE = "./Ressource/lib/partie_mode_solo.bin";

	/** Nom du fichier ou se trouve la sauvegarde du mode Story */
	public static final String CHEMIN_FICHIER_SAUVEGARDE = "./Ressource/sauvegarde.txt";

	/** chemins des fichiers contenant les niveaux du mode Story */
	public static final String[] FICHIER_STORY = {
			"./Ressource/fichierStory/Level1.txt",
			"./Ressource/fichierStory/Level2.txt",
			"./Ressource/fichierStory/Level3.txt",
			"./Ressource/fichierStory/Level4.txt",
			"./Ressource/fichierStory/Level5.txt",
			"./Ressource/fichierStory/Level6.txt",
			"./Ressource/fichierStory/Level8.txt",
			"./Ressource/fichierStory/Level7.txt",
			"./Ressource/fichierStory/Level9.txt",
			"./Ressource/fichierStory/Level10.txt" };

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
	 * @return story la partie du mode story
	 */
	public static JeuRainbow getStory() {
		JeuRainbow story = new JeuRainbow();
		for (int i = 0; i < FICHIER_STORY.length; i++) {
			story.addPartie(OperationsFichier.recupFichier(new File(
					FICHIER_STORY[i])));
		}
		return story;
	}

	/**
	 * Pour récupérer un clone de la Carte du niveau courant. Cela permet entre
	 * autre une fois le niveau fini de pouvoir le recommencer.
	 * 
	 * @return la carte du niveau courant
	 */
	public Partie getPartieCouranteJoueur() {
		if (partieJoueur == null) {
			partieJoueur = partiesEnregistrees.get(niveauCourant);
			try {
				partieJoueur = (Partie) partiesEnregistrees.get(niveauCourant)
						.clone();
			} catch (CloneNotSupportedException e) {
				System.out.println("Problème de clonage");
			}
		}
		partieJoueur.getRobot().setPartie(partieJoueur);
		return partieJoueur;
	}

	/**
	 * @return la partie courante clonée. Utiliser pour initialiser la partie de
	 *         l'IA
	 */
	public Partie getPartieCouranteIA() {
		if (partieIA == null) {
			partieIA = partiesEnregistrees.get(niveauCourant);
			try {
				partieIA = (Partie) partiesEnregistrees.get(niveauCourant)
						.clone();
			} catch (CloneNotSupportedException e) {
				System.out.println("Problème de clonage");
			}
		}
		partieIA.getRobot().setPartie(partieIA);
		return partieIA;
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
	public static int getNiveauMax() {
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
		partieJoueur = null;
		partieIA = null;
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
	 * Créé une partie aléatoire.
	 *
	 * @param withIA
	 *            détermine si on doit vérifier la faisabilité avec l'IA ou sans
	 * @return une nouvelle Partie
	 */
	public static Partie carteAleatoire(boolean withIA) {
		Random rand = new Random();
		int colonne = (rand.nextInt(17)) + 2; // nombre de colonne pour la
												// partie
		int ligne = (rand.nextInt(10)) + 2; // nombre de colonne pour la partie
		int nbCaisses = ((ligne * colonne) / (rand.nextInt(5) + 1)); // nombre
																		// de
																		// caisse
																		// sur
																		// un
																		// plateau
		Caisse[] caissePlateau = new Caisse[nbCaisses];
		Position random = new Position(rand.nextInt(colonne),
				rand.nextInt(ligne)); // création des positions aéatoire pour
										// les caisses
		Robot robot = new Robot(rand.nextInt(4), new Position(
				rand.nextInt(colonne), rand.nextInt(ligne))); // creation du
																// robot
		Vortex vortex = new Vortex(new Position(rand.nextInt(colonne),
				rand.nextInt(ligne))); // creation du vortex
		ArrayList<Caisse> caisseARecuperer = new ArrayList<Caisse>();

		int indice = rand.nextInt(5) + 1;
		for (int i = 0; i <= indice; i++) {
			// initialisation de la liste des caisses a recuperer
			caisseARecuperer.add(new Caisse(rand.nextInt(6) + 1));
		}

		for (int i = 0; i < nbCaisses; i++) {
			random = new Position(rand.nextInt(colonne), rand.nextInt(ligne));
			// on regarde si la pos de la caisse est sur le vortex
			if (random != vortex.getPosVortex()) { // si c'est on refait la
													// fonction
				// sinon on ajoute la caisse dans le tableau
				caissePlateau[i] = new Caisse((rand.nextInt(6) + 1), random);
			} else {
				return carteAleatoire(withIA);
			}
		}

		try {
			// on créé la partie
			return new Partie(ligne, colonne, null, robot, vortex,
					caisseARecuperer, caissePlateau, withIA);
		} catch (IllegalArgumentException e) {
			return carteAleatoire(withIA); // sinon on refait la fonction
		}
	}

}
