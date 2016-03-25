/*
 * IntelligenceArtificielle.java							24 févr. 2016
 * IUT Info2 2015-2016
 */
package metier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import evenement.ToucheClavier;

/**
 * <p>
 * Intelligence Artificielle (IA) qui doit résoudre une partie lorsque
 * l'utilisateur choisit de jouer contre une IA.<br />
 * L'IA repose sur trois principes pour rechercher la meilleure trajectoire pour
 * récupérer les caisses.<br />
 * <ul>
 * <li>Regarder quels sont les caisses qu'elles peut récupérer</li>
 * <li>En découler une trajectoire optimale pour récupérer les caisses</li>
 * <li>Faire cette trajectoire sur le plateau de jeu</li>
 * </ul>
 * Les différents niveaux de l'IA permettront de réduire le temps et d'optimiser
 * les déplacements du Robot de l'IA.
 * <ul>
 * <li>Niveau Facile : l'IA cherche à faire le moins de déplacements pour aller
 * chercher la caisse (On ne s'occupe pas du temps mis).L'IA cherche en mode
 * caisse par caisse.</li>
 * </ul>
 * <p>
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class IntelligenceArtificielle extends Thread {

	/** Temps que met le Robot pour pivoter sur une case */
	public static final float TEMPS_PIVOTER = (Robot.UNITE_TEMPS / Robot.VITESSE_PIVOTER_VIDE);

	/** Temps que met le Robot pour reculer d'une case */
	public static final float TEMPS_RECULER = (Robot.UNITE_TEMPS / Robot.VITESSE_RECULER_VIDE);

	/** Temps que met le robot pour avancer sur une case */
	public static final float TEMPS_AVANCER = (Robot.UNITE_TEMPS / Robot.VITESSE_AVANCER_VIDE);

	/** Temps que met le Robot pour pivoter sur une case avec une caisse */
	public static final float TEMPS_PIVOTER_CAISSE = (Robot.UNITE_TEMPS / Robot.VITESSE_PIVOTER_CHARGE);

	/** Temps que met le Robot pour reculer d'une case avec une caisse */
	public static final float TEMPS_RECULER_CAISSE = (Robot.UNITE_TEMPS / Robot.VITESSE_RECULER_CHARGE);

	/** Temps que met le robot pour avancer sur une case avec une caisse */
	public static final float TEMPS_AVANCER_CAISSE = (Robot.UNITE_TEMPS / Robot.VITESSE_AVANCER_CHARGE);

	/**
	 * Indicateur qui permet de signaler à l'IA que le robot a fait un 3/4 de
	 * tour sur la gauche.
	 */
	public static final float TEMPS_TROIS_QUART_GAUCHE = 1000.0f;

	/**
	 * Indicateur qui permet de signaler à l'IA que le robot a fait un 3/4 de
	 * tour sur la droite.
	 */
	public static final float TEMPS_TROIS_QUART_DROITE = 2000.0f;

	/**
	 * Indicateur qui permet de signaler à l'IA que le robot a fait un demi tour
	 * sur la gauche.
	 */
	public static final float TEMPS_DEMI_TOUR_GAUCHE = 3000.0f;

	/**
	 * Indicateur qui permet de signaler à l'IA que le robot a fait un demi tour
	 * sur la droite.
	 */
	public static final float TEMPS_DEMI_TOUR_DROITE = 4000.0f;

	/**
	 * Indicateur qui permet à l'IA de signaler que le robot devra déplacer une
	 * caisse
	 */
	public static final float TEMPS_DEPLACER_CAISSE = 100000.0f;

	/**
	 * Utilisé pour optimiser la recherche de l'indice minimal. Elle doit
	 * correspondre à une valeur inutilisé dans l'algorithme de Dijkstra
	 * 
	 * @see metier.IntelligenceArtificielle#getIndiceMinimal(int[], int[])
	 */
	private static final int INOCCUPE = -1;

	/**
	 * Déplacement du robot : Avancer
	 * 
	 * @see metier.IntelligenceArtificielle#getDeplacement(List)
	 */
	public static final Integer ACTION_AVANCER = new Integer(0);

	/**
	 * Déplacement du robot : Reculer
	 * 
	 * @see metier.IntelligenceArtificielle#getDeplacement(List)
	 */
	public static final Integer ACTION_RECULER = new Integer(1);

	/**
	 * Déplacement du robot : Pivoter à gauche
	 * 
	 * @see metier.IntelligenceArtificielle#getDeplacement(List)
	 */
	public static final Integer ACTION_PIVOTER_GAUCHE = new Integer(2);

	/**
	 * Déplacement du robot : Pivoter à droite
	 * 
	 * @see metier.IntelligenceArtificielle#getDeplacement(List)
	 */
	public static final Integer ACTION_PIVOTER_DROITE = new Integer(3);

	/**
	 * Déplacement du robot : Charger / Décharger une caisse
	 * 
	 * @see metier.IntelligenceArtificielle#getDeplacement(List)
	 */
	public static final Integer ACTION_CHARGER = new Integer(4);

	/**
	 * Pause en millisecondes pour que le Thread puisse recevoir des ordres
	 * d'interruption
	 */
	private static final long PAUSE = 50;

	/**
	 * Utiliser comme marque séparatrice dans un tableau d'orientations. Elle
	 * doit être différentes des constantes d'orientations définies dans Robot
	 * 
	 * @see metier.IntelligenceArtificielle#chercherChemin(HashMap)
	 */
	public static final Integer SEPARATION_ORIENTATION = new Integer(10);

	/**
	 * Identifie les positions à supprimer
	 * 
	 * @see metier.IntelligenceArtificielle#chercherPositionPourDeplacer(Position,
	 *      int)
	 */
	public static final Position POSITION_A_SUPPRIMER = new Position(-1, -1);

	/**
	 * Permet de séparer les différenters positions au sein d'un même niveau
	 * 
	 * @see metier.IntelligenceArtificielle#chercherPositionPourDeplacer(Position,
	 *      int)
	 */
	public static final Position POSITION_SEPARATRICE = new Position(-2, -2);

	/**
	 * Clone de la partie que l'IA doit résoudre. On utilise ici une copie pour
	 * pouvoir effectuer des scénarios de déplacements.
	 */
	private Partie partieClone;

	/**
	 * Partie que l'IA doit résoudre. On peut ainsi déplacer le robot dans un
	 * contexte graphique 2D
	 */
	private Partie partieReelle;

	/**
	 * <p>
	 * Tableau contenant les temps mis pour aller vers n'importe quel position
	 * du plateau de jeu à partir d'une position initiale.<br />
	 * Tableau initialisé dans la fonction suivante :
	 * </p>
	 * 
	 * @see metier.IntelligenceArtificielle#setTempsEtOrientationsSansCaisse(Position)
	 */
	private float[] temps;

	/**
	 * <p>
	 * Tableau contenant les orientations successives de n'importe quel position
	 * du plateau de jeu à partir d'une orientation de départ (celle de la
	 * position initiale).<br />
	 * Tableau initialisé dans la fonction suivante :
	 * </p>
	 * 
	 * @see metier.IntelligenceArtificielle#setTempsEtOrientationsSansCaisse(Position)
	 */
	private int[] orientations;

	/**
	 * <p>
	 * Tableau contenant les orientations successives de n'importe quel position
	 * du plateau de jeu à partir d'une orientation de départ (celle de la
	 * position initiale).<br />
	 * L'algorithme considère que le robot a déjà charger une caisse et qu'elle
	 * se trouve devant lui (selon son orientation).<br />
	 * Tableau initialisé dans la fonction suivante :
	 * </p>
	 * 
	 * @see metier.IntelligenceArtificielle#dijkstraAvecCaisse(Position)
	 */
	private int[] orientationsCaisse;

	/**
	 * <p>
	 * Tableau contenant les temps mis pour aller vers n'importe quelle position
	 * du plateau de jeu à partir d'une position initiale.<br />
	 * L'algorithme considère que le robot a déjà charger une caisse et qu'elle
	 * se trouve devant lui (selon son orientation).<br />
	 * Tableau initialisé dans la fonction suivante :
	 * </p>
	 * 
	 * @see metier.IntelligenceArtificielle#dijkstraAvecCaisse(Position)
	 */
	private float[] tempsCaisse;

	/**
	 * Permet de gérer dans un autre Thread les déplacements sur la partie
	 * graphique des calculs métiers
	 */
	private ThreadDeplacement threadDeplacement;

	/**
	 * Permet d'annoncer au Thread du déplacement que la partie métier est finie
	 */
	public boolean metierFinish;

	/**
	 * Position de départ du robot. Elle évolue chaque fois que l'on a trouvé un
	 * chemin (aller + retour) vers une caisse.
	 * 
	 * @see metier.IntelligenceArtificielle#chercherChemin(HashMap)
	 */
	private Position positionDepart;

	/**
	 * Orientation de départ du robot. Elle évolue chaque fois que l'on a trouvé
	 * un chemin (aller + retour) vers une caisse.
	 * 
	 * @see metier.IntelligenceArtificielle#chercherChemin(HashMap)
	 */
	private int orientationDepart;

	/** Caisse que l'IA a choisit et que le robot devra déplacer */
	private Caisse caisseCourante;

	/** Détermine si une partie est faisable ou non par l'IA */
	private boolean isPartieFaisable;

	/**
	 * Constructeur qui initialise une IA avec un niveau et une partie
	 * 
	 * @param partie
	 *            partie que l'IA doit résoudre
	 * 
	 * @param isTest
	 *            permet de savoir si on lance l'IA pour savoir si la partie est
	 *            faisable ou por résoudre la partie
	 * 
	 * @throws IllegalArgumentException
	 *             si le niveau ou la partie sont inccorects
	 */
	public IntelligenceArtificielle(Partie partie, boolean isTest)
			throws IllegalArgumentException {
		if (partie == null) {
			throw new IllegalArgumentException(
					"Impossibilité de construire l'IA car la partie n'existe pas");
		}
		partieReelle = partie;
		try {
			this.partieClone = (Partie) partie.clone();
		} catch (CloneNotSupportedException e) {
			System.out
					.println("IA : Constructeur : Impossible de cloner la partie");
		}
		this.positionDepart = this.partieClone.getRobot().getPosRobot();
		this.orientationDepart = this.partieClone.getRobot().getOrientation();

		// On lance le Thread pour dessiner les déplacements mais il ne fera
		// rien tant qu'on a pas envoyé des informations
		metierFinish = false;
		this.threadDeplacement = new ThreadDeplacement();
		if (!isTest) {
			this.threadDeplacement.start();
		}
	}

	/**
	 * @return le isPartieFaisable
	 */
	public boolean isPartieFaisable() {
		return isPartieFaisable;
	}

	/**
	 * @param aRechercher
	 *            tableau ou on doit effectuer la recherche
	 * @param indiceInutilisable
	 *            tableau qui contient les indices que l'on de doit pas utiliser
	 * @return retourne l'indice de la valeur minimal
	 */
	private static int getIndiceMinimal(float[] aRechercher,
			int[] indiceInutilisable) {
		int indice = 0;
		float valeurMin = Float.MAX_VALUE;
		BouclePrincipale: for (int i = 0; i < aRechercher.length; i++) {
			// On vérifie si l'indice n'est pas déjà utilisé
			for (int j = 0; j < indiceInutilisable.length; j++) {
				if (indiceInutilisable[j] == INOCCUPE) {
					break;
				}
				if (i == indiceInutilisable[j]) {
					continue BouclePrincipale;
				}
			}
			if (valeurMin > aRechercher[i]) {
				indice = i;
				valeurMin = aRechercher[i];
			}
		}
		return indice;
	}

	/**
	 * Inverse le contenu de la liste, c'est à dire que le 1er élément devient
	 * le dernier élément (et inversément), etc...
	 * 
	 * @param aInverser
	 *            Liste de déplacement à inverser
	 * @return une ArrayList des déplacements inverser
	 */
	@SuppressWarnings("unchecked")
	private static <T> List<T> inverserListe(List<T> aInverser) {
		// On récupère la liste sous la forme d'un tableau
		T[] tabInvertion = aInverser.toArray((T[]) (new Object[0]));
		// Variable de temporisation pour inverser le contenu
		T temp;

		// On inverse le contenu du tableau
		for (int i = 0; i < tabInvertion.length / 2; i++) {
			temp = tabInvertion[i];
			tabInvertion[i] = tabInvertion[tabInvertion.length - i - 1];
			tabInvertion[tabInvertion.length - i - 1] = temp;
		}

		// On recréer une nouvelle liste avec le tableau inversé
		return new ArrayList<T>(Arrays.asList(tabInvertion));
	}

	/**
	 * @param posRobot
	 *            position du robot
	 * @param oriRobot
	 *            orientation du robot
	 * @return la position de la caisse connaissant la position et l'orientation
	 *         du robot
	 */
	private static Position getCaisse(Position posRobot, int oriRobot) {
		if (oriRobot == Robot.ORIENTATION_BAS) {
			return new Position(posRobot.getX(), posRobot.getY() + 1);
		} else if (oriRobot == Robot.ORIENTATION_HAUT) {
			return new Position(posRobot.getX(), posRobot.getY() - 1);
		} else if (oriRobot == Robot.ORIENTATION_DROITE) {
			return new Position(posRobot.getX() + 1, posRobot.getY());
		} else { // Robot.ORIENTATION_GAUCHE
			return new Position(posRobot.getX() - 1, posRobot.getY());
		}
	}

	/**
	 * Affiche la liste des déplacements sur la console
	 *
	 * @param deplacement
	 *            liste de déplacements
	 */
	private static void afficherDeplacement(List<Integer> deplacement) {
		System.out.println("Liste de déplacement : ");
		for (Integer integer : deplacement) {
			if (integer == ACTION_AVANCER) {
				System.out.print("Avancer --> ");
			} else if (integer == ACTION_PIVOTER_GAUCHE) {
				System.out.print("Pivoter gauche --> ");
			} else if (integer == ACTION_PIVOTER_DROITE) {
				System.out.print("Pivoter droite --> ");
			} else if (integer == ACTION_RECULER) {
				System.out.print("Reculer --> ");
			} else if (integer == ACTION_CHARGER) {
				System.out.print("Chargement --> ");
			}
		}
		System.out.println();
	}

	/**
	 * Affiche les résultats de l'agorithme de Dijkstra.
	 *
	 * @param temps
	 *            temps calculé dans l'algorithme
	 * @param orientations
	 *            orientations succsessives du robot
	 * @param nbLigne
	 *            nombre de colonnes par ligne (utiliser pour représenter le
	 *            tableau en 2 dimensions)
	 */
	private static void afficherDijkstra(float[] temps, int[] orientations,
			int nbLigne) {
		System.out.println("\nDijkstra :\n\tTemps : ");
		for (int j = 0; j < temps.length; j++) {
			if (j % nbLigne == 0) {
				System.out.println();
			}
			if (temps[j] == Float.MAX_VALUE) {
				System.out.print("-1\t");
			} else {
				System.out.printf("%.2f\t", temps[j]);
			}
		}
		System.out.println("\n\n\tOrientations : ");
		for (int j = 0; j < orientations.length; j++) {
			if (j % nbLigne == 0) {
				System.out.println();
			}
			System.out.print(orientations[j] + "\t");
		}
		System.out.println();
	}

	/**
	 * Transforme l'indice d'un tableau (de nbLigne * nbColonne) en Position sur
	 * la partie
	 * 
	 * @param indice
	 *            indice d'un tableau de nbLigne * nbColonne
	 * @return la position associé
	 */
	private Position indiceToPosition(int indice) {
		return new Position(indice % partieClone.getNbColonne(), indice
				/ partieClone.getNbColonne());
	}

	/**
	 * Transforme une position de la partie en indice d'un tableau (de nbLigne *
	 * nbColonne)
	 * 
	 * @param pos
	 *            Position sur la partie
	 * @return l'indice associé
	 */
	private int positionToIndice(Position pos) {
		return pos.getX() + pos.getY() * partieClone.getNbColonne();
	}

	/**
	 * Recherche les caisses que l'IA doit récupérer sur le plateau de jeu. On
	 * les range selon un tableau de liste dont chaque indice correspond à une
	 * caisse à récupérer et chaque liste l'équivalent des caisses à récupérer
	 * sur le plateau de jeu.
	 * 
	 * @return un tableau de liste de caisse
	 */
	@SuppressWarnings("unchecked")
	private List<Caisse>[] getCaisseParCouleur() {
		// Liste retourné par la fonction
		List<Caisse>[] liste = new ArrayList[partieClone.getCaisseARecuperee()
				.size()];
		// Caisse à récupérer, initialiser à chaque tour de boucle
		Caisse aRecuperer;

		// On récupère les données de la partie courante
		// Liste des caisses à récupérer
		List<Caisse> listeCaisseRecuperer = partieClone.getCaisseARecuperee();
		// Tableau des caisses sur le plateau de jeu
		Caisse[] tableauCaissePlateau = partieClone.getCaissePlateau();

		BouclePrincipale: for (int i = 0; i < liste.length; i++) {
			// Caisse à récupérer
			aRecuperer = listeCaisseRecuperer.get(i);
			// Pour l'optimisation
			for (int j = 0; j < i; j++) {
				if (aRecuperer.getCouleur() == listeCaisseRecuperer.get(j)
						.getCouleur()) {
					liste[i] = liste[j];
					// On continue la boucle principale
					continue BouclePrincipale;
				}
			}

			// On initialise la liste du tableau
			liste[i] = new ArrayList<Caisse>();
			// On parcours les caisses sur le plateau de jeu
			for (int j = 0; j < tableauCaissePlateau.length; j++) {
				// On ajoute la caisse si elle a la même couleur que celle à
				// récupérer
				if (tableauCaissePlateau[j].getCouleur() == aRecuperer
						.getCouleur()) {
					liste[i].add(tableauCaissePlateau[j]);
					// Pour l'optimisation
					continue;
				}
				// TODO faire avec la fusion
			}
		}
		return liste;
	}

	/**
	 * Supprime la référence de la caisse dans la partie clonée et dans la liste
	 * des caisses de l'IA
	 *
	 * @param liste
	 *            liste des caisses de l'IA
	 */
	private void removeCaisseCourante(List<Caisse> liste) {
		if (caisseCourante != null) {
			Caisse[] caissePlateau = partieClone.getCaissePlateau();
			for (int i = 0; i < caissePlateau.length; i++) {
				// Pour optimiser on supprime aussi dans la liste
				// La liste aura toujours une taille inférieure ou égale aux
				// nombres de caisses sur le plateau
				if (i < liste.size()
						&& liste.get(i).getPosCaisse()
								.equals(caisseCourante.getPosCaisse())) {
					liste.remove(i);
				}

				// On supprime la caisse dans la partie clonée
				if (caissePlateau[i] != null
						&& caissePlateau[i].getPosCaisse().equals(
								caisseCourante.getPosCaisse())) {
					caissePlateau[i] = null;
					break;
				}
			}
		}
	}

	/**
	 * Déduit d'une liste de caisses potentielles, une suite de positions que le
	 * robot doit atteindre pour récupérer la caisse. L'algorithme tient compte
	 * de l'orientation d'arrivée du robot, c'est pour cela qu'on associe à
	 * chaque Position une liste de déplacement (ou une liste vide si il n'y a
	 * aucun déplacement supplémentaires à effectuer) pour prévenir des
	 * déplacements à effectuer avant de récupérer la caisse.
	 *
	 * @param caissePotentielle
	 *            liste des caisses potentielles
	 * @return HashMap contenant pour chaque Position (adjacentes aux caisses à
	 *         récupérer) une liste de déplacements supplémentaires pour
	 *         atteindre la caisse depuis l'une de ses positions adjacentes.
	 */
	private HashMap<Position, List<Integer>> chercherCaisses(
			List<Caisse> caissePotentielle) {
		// On initialise les temps et les orientations du plateau de jeu
		dijkstraSansCaisse(positionDepart, orientationDepart);
		// HashMap contenant pour chaque Position (adjacentes aux caisses à
		// récupérer) un liste de déplacements potentielles.
		HashMap<Position, List<Integer>> listeOrdonnee = new HashMap<Position, List<Integer>>();

		for (int i = 0; i < caissePotentielle.size(); i++) {

			// Position adjacentes à la caisse parcourue
			Position[] positionsAdjacentes = Position
					.getPositionsAdjacentes(caissePotentielle.get(i)
							.getPosCaisse());

			for (int j = 0; j < positionsAdjacentes.length; j++) {
				// Indice de la position adjacente
				int indiceCaisseAdjacente = positionToIndice(positionsAdjacentes[j]);

				// On vérifie si la position est valide dans le plateau de jeu
				// et qu'on puisse y accéder
				if (partieClone.isPositionOKAvecTout(positionsAdjacentes[j])
						&& temps[indiceCaisseAdjacente] != Float.MAX_VALUE) {

					// Liste des déplacements potentielles pour que le robot
					// soit en face de la caisse
					List<Integer> deplacement = new ArrayList<Integer>();

					getDeplacementSupplementaire(deplacement,
							positionsAdjacentes[j],
							orientations[indiceCaisseAdjacente],
							caissePotentielle.get(i).getPosCaisse(), false);
					// orientations[indiceCaisseAdjacente] == orientationFinale
					// <=> pas de déplacement à effectuer
					listeOrdonnee.put(positionsAdjacentes[j], deplacement);
				}
				// else
			}
		}
		return listeOrdonnee;
	}

	/**
	 * <p>
	 * Cherche le plus court chemin (aller/retour) et retourne la position ou le
	 * Robot doit aller pour chercher la caisse. <br />
	 * La fonction determine aussi les déplacements provisoires que le robot
	 * devra faire pour amener la caisse dans le vortex (dans le cas ou
	 * l'orientation finale du robot n'est pas en face du vortex). <br />
	 * Ces déplacements seront ajoutés à la suite de ceux déjà définis pour
	 * aller chercher la caisse
	 * {@link IntelligenceArtificielle#getDeplacement(List)}.<br />
	 * Pour les différenciés, on utilise
	 * {@link IntelligenceArtificielle#SEPARATION_ORIENTATION}.<br />
	 * Avant de se terminer la fonction calcule les déplacements à effectuer
	 * (aller/retour) puis appelle la fonction pour déplacer le robot.
	 * </p>
	 * 
	 * @param listeCaisses
	 *            Liste des caisses que doit récupérer le robot
	 * 
	 * @return true si le déplacement est possible, false sinon.
	 * @see metier.IntelligenceArtificielle#chercherCaisses(List)
	 */
	private boolean chercherChemin(List<Caisse> listeCaisses) {
		HashMap<Position, List<Integer>> positionPotentielle = chercherCaisses(listeCaisses);
		// Position du vortex
		final Position positionVortex = partieClone.getVortex().getPosVortex();

		// Position min <=> Position qui mettra le moins de temps aller +
		// retour
		Position posMinCaisse = null, //
		posMinVortex = null;
		// Liste des déplacements supplémentaires à effectuer pour les
		// déplacements supplémentaires
		List<Integer> deplacementMin = null;
		// Orientation du Robot au départ de l'algorithme de dijkstra avec des
		// caisses.
		int oriMin = 0;

		// Temps mis pour atteindre la position
		float tempsMis = Float.MAX_VALUE;
		// Parcours de la HashMap
		for (Entry<Position, List<Integer>> entry : positionPotentielle
				.entrySet()) {
			// Position parcourue dans la HashMap
			Position posCourante = entry.getKey();

			// On regarde l'orientation qu'aura le Robot lorsqu'il aura
			// récupérer la caisse
			int orientationDepart = orientations[positionToIndice(posCourante)];
			// On y applique les déplacements nécessaires pour récupérer la
			// caisse
			for (Integer integer : entry.getValue()) {
				// Déplacement ne peut contenir que ACTION_PIVOTER_GAUCHE ou
				// ACTION_PIVOTER_DROITE
				if (integer.equals(ACTION_PIVOTER_DROITE)) {
					orientationDepart = Robot.pivoterDroite(orientationDepart);
				} else if (integer.equals(ACTION_PIVOTER_GAUCHE)) {
					orientationDepart = Robot.pivoterGauche(orientationDepart);
				}
			}

			// On fait les déplacements de la caisse depuis cette position
			dijkstraAvecCaisse(posCourante, orientationDepart);

			// On récupère les positions adjacentes aux vortex
			Position[] positionsAdjacentesVortex = Position
					.getPositionsAdjacentes(positionVortex);
			// On parcours ces positions pour trouver le chemin le plus
			// court réalisable
			for (int i = 0; i < positionsAdjacentesVortex.length; i++) {

				// On vérifie que la position existe
				if (!partieClone
						.isPositionOKAvecTout(positionsAdjacentesVortex[i])) {
					continue;
				}

				// Liste des déplacements provisoires pour aller chercher la
				// caisse
				List<Integer> deplacement = new ArrayList<Integer>(
						entry.getValue());

				// On sépare les déplacements pour la caisse de ceux pour le
				// vortex
				deplacement.add(SEPARATION_ORIENTATION);

				// Indice de la position adjacente aux vortex
				// <=> Position du robot lorsqu'il dépose la caisse.
				int indicePositionAdjacenteVortex = positionToIndice(positionsAdjacentesVortex[i]);
				// On regarde s'il y a des déplacements à effectuer pour
				// amener la caisse dans le vortex
				if (!partieClone
						.isPositionOKAvecTout(positionsAdjacentesVortex[i])
						|| tempsCaisse[indicePositionAdjacenteVortex] == Float.MAX_VALUE
						|| !getDeplacementSupplementaire(
								deplacement,
								positionsAdjacentesVortex[i],
								orientationsCaisse[indicePositionAdjacenteVortex],
								positionVortex, true)) {
					// Impossible d'amener la caisse au vortex
					continue;
				}
				// On vérifie si le trajet aller + retour est possible
				if ((tempsCaisse[indicePositionAdjacenteVortex] % 1000)
						+ temps[positionToIndice(posCourante)]
						+ ((deplacement.size() - 1) * TEMPS_PIVOTER_CAISSE) < tempsMis) {
					tempsMis = (tempsCaisse[indicePositionAdjacenteVortex] % 1000)
							+ temps[positionToIndice(posCourante)]
							+ ((deplacement.size() - 1) * TEMPS_PIVOTER_CAISSE);
					posMinCaisse = posCourante;
					posMinVortex = positionsAdjacentesVortex[i];
					deplacementMin = deplacement;
					oriMin = orientationDepart;
				}
			}
		}
		// Aucun déplacement trouvé
		if (posMinCaisse == null) {
			Position positionBloquante = null;
			int nbCaisseDeplaceMin = Integer.MAX_VALUE;

			// On vérifie le vortex
			int nbCaisseDeplace = (int) (temps[positionToIndice(partieClone
					.getVortex().getPosVortex())] / TEMPS_DEPLACER_CAISSE);
			if (nbCaisseDeplace >= 1 && nbCaisseDeplace < nbCaisseDeplaceMin) {
				nbCaisseDeplaceMin = nbCaisseDeplace + 1;
				positionBloquante = partieClone.getVortex().getPosVortex();
			} else {
				// On vérifie la liste des caisses
				for (Caisse caisse : listeCaisses) {
					// Nombre de caisse qu'il faudra déplacer
					nbCaisseDeplace = (int) (temps[positionToIndice(caisse
							.getPosCaisse())] / TEMPS_DEPLACER_CAISSE);
					if (nbCaisseDeplace >= 2
							&& nbCaisseDeplace < nbCaisseDeplaceMin) {
						nbCaisseDeplaceMin = nbCaisseDeplace;
						positionBloquante = caisse.getPosCaisse();
					}
				}
			}
			if (positionBloquante == null) {
				System.out.println("IA : Trajectoire introuvable");
				return false;
			} else {
				List<Position>[] listePosition = chercherPositionPourDeplacer(
						positionBloquante, nbCaisseDeplaceMin,
						!positionBloquante.equals(partieClone.getVortex()
								.getPosVortex()));
				suppressionPositionInvalide(listePosition);
				List<Integer> deplacementDebloquage = getDeplacementBloquant(listePosition);
				if (deplacementDebloquage.isEmpty()) {
					// Retour impossible
					return false;
				}
				threadDeplacement.addDeplacement(deplacementDebloquage);
				return chercherChemin(listeCaisses);
			}
		} else {
			// Sachant que l'algorithme est rapide sur les petites cartes on
			// peut se permettre de rappeler la fonction pour calculer le retour
			dijkstraAvecCaisse(posMinCaisse, oriMin);

			// Compteur utilisé pour les déplacements supplémentaires
			int i;
			// Liste des déplacements aller / retour
			List<Integer> deplacement = new ArrayList<Integer>();
			// orientation de départ de la prochaine caisse
			int oriFuture = orientationsCaisse[positionToIndice(posMinVortex)];
			// Orientation finale du robot lorsqu'il récupère la caisse
			int oriCaisse = orientations[positionToIndice(posMinCaisse)];

			// Robot ---> Caisse
			deplacement.addAll(getDeplacement(posMinCaisse, true));
			// On ajoute les déplacements supplémentaires s'ils existent
			for (i = 0; i < deplacementMin.size(); i++) {
				if (!deplacementMin.get(i).equals(SEPARATION_ORIENTATION)) {
					if (deplacementMin.get(i).equals(ACTION_PIVOTER_DROITE)) {
						oriCaisse = Robot.pivoterDroite(oriCaisse);
					} else if (deplacementMin.get(i).equals(
							ACTION_PIVOTER_GAUCHE)) {
						oriCaisse = Robot.pivoterGauche(oriCaisse);
					}
					deplacement.add(deplacementMin.get(i));
				} else {
					break;
				}
			}
			// On charge la caisse
			deplacement.add(ACTION_CHARGER);

			// Robot + Caisse ---> Vortex
			deplacement.addAll(getDeplacement(posMinVortex, false));
			// On ajoute les déplacements supplémentaires s'ils existent
			for (i++; i < deplacementMin.size(); i++) {
				// Utiliser pour calculer l'orientation de dépar de la prochaine
				// caisse
				if (deplacementMin.get(i).equals(ACTION_PIVOTER_DROITE)) {
					oriFuture = Robot.pivoterDroite(oriFuture);
				} else if (deplacementMin.get(i).equals(ACTION_PIVOTER_GAUCHE)) {
					oriFuture = Robot.pivoterGauche(oriFuture);
				}
				deplacement.add(deplacementMin.get(i));
			}
			// On décharge la caisse
			deplacement.add(ACTION_CHARGER);
			// On modifie la nouvelle position et orientation de départ
			positionDepart = posMinVortex;
			orientationDepart = oriFuture;
			caisseCourante = partieClone.getCaisseJeu(getCaisse(posMinCaisse,
					oriCaisse));
			threadDeplacement.addDeplacement(deplacement);
			return true;
		}
	}

	/**
	 * Recherche les positions des caisses à déplacer et leurs positions
	 * adjacentes valides (autre caisse ou case vide). Fonction enveloppe de la
	 * fonction itérative pour initialiser les différents paramètres.
	 *
	 * @param posDepart
	 *            Position de départ puis on tourne autour de cette position
	 *            pour rechercher les positions adjacentes
	 * @param nbCaisseDeplaceMin
	 *            nombre de déplacement que l'on doit effectuer pour aller
	 *            chercher notre caisse
	 * @param isSuppression
	 *            permet de savoir si l'on gère le fait de supprimer ou non les
	 *            positions invalidés par l'algorithme
	 * @return une liste de position avec SEPARATEUR_POSITION pour séparer les
	 *         différents niveaux de caisses
	 */
	@SuppressWarnings("unchecked")
	private List<Position>[] chercherPositionPourDeplacer(Position posDepart,
			int nbCaisseDeplaceMin, boolean isSuppression) {
		List<Position>[] listePosition = new ArrayList[nbCaisseDeplaceMin + 1];
		listePosition[0] = new ArrayList<Position>();
		listePosition[0].add(posDepart);
		listePosition[0].add(POSITION_SEPARATRICE);
		return chercherPositionPourDeplacer(listePosition, listePosition[0], 1,
				nbCaisseDeplaceMin, isSuppression);
	}

	/**
	 * Fonction itérative qui parcourt de indice jusqu'à n. A chaque tour elle
	 * estime les positions qui sont liées à une positio d'un niveau supérieur
	 * et vérifie que celles-là soient valides. Et ainsi de suite pour tous les
	 * niveaux. Pour pouvoir identifier les positions d'un supérieur, on insère
	 * POSITION_SEPARATRICE des qu'on a fini de parcourir les positions
	 * adjacentes.
	 *
	 * @param listePosition
	 *            liste des positions adjacentes à la caisse bloquée sur n
	 *            niveaux
	 * @param positionPrec
	 *            liste des positions précédentes (niveau + 1)
	 * @param n
	 *            nombre de niveau
	 * @param isSuppression
	 *            permet de savoir si l'on gère le fait de supprimer ou non les
	 *            positions invalidés par l'algorithme
	 * @return liste des positions avec POSITION_SEPARATRICE pour séparer entre
	 *         les sous-niveaux
	 */
	private List<Position>[] chercherPositionPourDeplacer(
			List<Position>[] listePosition, List<Position> positionPrec,
			int indice, int n, boolean isSuppression) {
		if (indice > n) {
			return listePosition;
		}
		// else
		listePosition[indice] = new ArrayList<Position>();
		for (int i = 0; i < positionPrec.size(); i++) {
			Position posCourante = positionPrec.get(i);
			if (posCourante.equals(POSITION_A_SUPPRIMER)
					|| posCourante.equals(POSITION_SEPARATRICE)) {
				continue;
			}
			Position[] posAdjacentes = Position
					.getPositionsAdjacentes(posCourante);
			for (int j = 0; j < posAdjacentes.length; j++) {
				// On vérifie que ce ne soit pas une position invalide
				if (partieClone.isPositionOK(posAdjacentes[j])) {
					if ((int) (temps[positionToIndice(posAdjacentes[j])] / TEMPS_DEPLACER_CAISSE) == n
							- indice) {
						listePosition[indice].add(posAdjacentes[j]);
					}
				} else if (isSuppression) {
					listePosition[indice].add(POSITION_A_SUPPRIMER);
					break;
				}
			}
			listePosition[indice].add(POSITION_SEPARATRICE);
		}
		return chercherPositionPourDeplacer(listePosition,
				listePosition[indice], indice + 1, n, isSuppression);
	}

	/**
	 * Supprime les positions invalides dans la liste des positions. Chaque fois
	 * qu'une position est invalide on notifie à la position du dessus qu'elle
	 * est elle aussi invalide et ainsi de suite.
	 *
	 * @param listePosition
	 *            les listes contiennent les différentes positions pour un
	 *            niveau donnée (indice du tableau)
	 */
	private void suppressionPositionInvalide(List<Position>[] listePosition) {
		int niveauMax = listePosition.length - 1;
		// Indice des niveaux supérieurs pour pouvoir se retrouver dans la
		// hierarchie
		int indiceNiveauPrec = 0;

		// On garde en mémoire l'indice de départ (avant le changement d'un
		// niveau)
		int depart = 0;

		// On supprime les positions invalides du niveau le plus bas
		for (int i = 0; i < listePosition[niveauMax].size(); i++) {
			Position posCourante = listePosition[niveauMax].get(i);
			if (posCourante.equals(POSITION_SEPARATRICE)) {
				depart = i + 1;
				indiceNiveauPrec++;
			} else if (posCourante.equals(POSITION_A_SUPPRIMER)) {
				// On supprime toutes les positions précédentes
				for (int j = depart; j <= i + 1; j++) {
					listePosition[niveauMax].remove(depart);
				}
				// On signale que la position au niveau du dessus est invalide
				listePosition[niveauMax - 1].remove(indiceNiveauPrec);
				listePosition[niveauMax - 1].add(indiceNiveauPrec,
						POSITION_A_SUPPRIMER);
				i = depart - 1;
			}
		}

		// On supprime les positions invalides des niveaux supérieurs
		// On détecte s'il existe au moins une position dans le niveau courant
		boolean unePosition = false;
		// Parcours les niveaux (verticalement)
		int compteur = niveauMax - 1;
		while (compteur > 0) {
			// Reset
			indiceNiveauPrec = 0;
			depart = 0;

			// Parcours d'un niveau (horizontalement)
			for (int i = 0; i < listePosition[compteur].size(); i++) {
				Position posCourante = listePosition[compteur].get(i);

				if (posCourante.equals(POSITION_SEPARATRICE)) {
					depart = i + 1;
					indiceNiveauPrec++;
					if (!unePosition) {
						// On signale que la position au niveau du dessus est
						// invalide
						listePosition[compteur - 1].remove(indiceNiveauPrec);
						listePosition[compteur - 1].add(indiceNiveauPrec,
								POSITION_A_SUPPRIMER);
					}
					// Reset
					unePosition = false;
				} else if (posCourante.equals(POSITION_A_SUPPRIMER)) {
					listePosition[compteur].remove(i);
					i = depart - 1;
				} else {
					// Il existe au moins une position valide
					unePosition = true;
				}
			}

			// Niveau supérieur
			compteur--;
		}
		// compteur == 0
	}

	/**
	 * Analyse la liste du niveau le plus bas, et en détermine un ensemble de
	 * déplacements permettant de débloquer la caisse demandée.
	 *
	 * @param listePosition
	 *            liste des positions bloquantes. A chaque indice correspond un
	 *            niveau.
	 * @return liste de déplacement optimal pour déplacer les caisses
	 */
	private List<Integer> getDeplacementBloquant(List<Position>[] listePosition) {
		int niveauMax = listePosition.length;
		// Indice des niveaux supérieurs pour pouvoir se retrouver dans la
		// hierarchie
		int indiceNiveauPrec = 0;
		// Parcours les niveaux (verticalement)
		int compteur = niveauMax - 1;

		// Valeurs minimales permettant de rechercher le plus court chemin
		Position posMin = null;
		float tempsMin = Float.MAX_VALUE;
		List<Integer> deplacementMin = null;
		Position posFutureCaisseMin = null;
		int orientationMin = SEPARATION_ORIENTATION;
		boolean isPositionMin = false;

		// Liste de déplacements finaux (retour de la fonction)
		List<Integer> deplacementFinal = new ArrayList<Integer>();

		// Position de la caisse
		Position posCaisse = listePosition[compteur - 1].get(indiceNiveauPrec),
		// Copie de la position de la caisse
		posFutureCaisse = new Position(posCaisse.getX(), posCaisse.getY());

		while (compteur > 0) {
			// Reset
			indiceNiveauPrec = 0;

			// Parcours d'un niveau (horizontalement)
			for (int i = 0; i < listePosition[compteur].size(); i++) {
				// Position du robot
				Position posCourante = listePosition[compteur].get(i);

				if (posCourante.equals(POSITION_SEPARATRICE)) {
					// On change la position de la caisse
					Caisse caisse = partieClone.getCaisseJeu(posCaisse);
					if (caisse != null && isPositionMin) {
						caisse.setPosCaisse(posFutureCaisseMin);
					}
					isPositionMin = false;
					indiceNiveauPrec++;
					posCaisse = listePosition[compteur - 1]
							.get(indiceNiveauPrec);
				} else { // Position d'une caisse
					// On recherche la position la plus proche ou le robot
					// pourrait déposer la caisse

					int orientationRobot = orientations[positionToIndice(posCourante)];

					// Liste des déplacements pour amener la caisse bloquante
					// dans une situation qui l'est moins
					List<Integer> deplacement = new ArrayList<Integer>();
					getDeplacementSupplementaire(deplacement, posCourante,
							orientationRobot, posCaisse, false);
					for (Integer unDeplacement : deplacement) {
						if (unDeplacement.equals(ACTION_PIVOTER_DROITE)) {
							orientationRobot = Robot
									.pivoterDroite(orientationRobot);
						} else if (unDeplacement.equals(ACTION_PIVOTER_GAUCHE)) {
							orientationRobot = Robot
									.pivoterGauche(orientationRobot);
						}
					}

					deplacement.add(ACTION_CHARGER);

					// Temps mis pour faire les différents déplacements
					float tempsMis = (temps[positionToIndice(posCourante)] % 1000.0f)
							+ TEMPS_PIVOTER * deplacement.size();

					Position temp = new Position(posCourante.getX()
							- posCaisse.getX(), posCourante.getY()
							- posCaisse.getY()), //
					deltaG = new Position(-temp.getY(), temp.getX()), //
					deltaD = new Position(temp.getY(), temp.getX());

					// reset
					posFutureCaisse = new Position(posCaisse.getX(),
							posCaisse.getY());
					int n = -1;
					// Boucle infini <=> On sort uniquement lorsque l'on ne peux
					// plus reculer
					while (true) {
						// On essaye de pivoter à gauche
						if ( // Position à gauche de la caisse
						partieClone.isPositionOKAvecTout(
								n * temp.getX() + deltaG.getX()
										+ posCourante.getX(),
								n * temp.getY() + deltaG.getY()
										+ posCourante.getY())
								&& // Position à gauche du robot
								partieClone.isPositionOKAvecTout(
										(n + 1) * temp.getX() + deltaG.getX()
												+ posCourante.getX(), (n + 1)
												* temp.getY() + deltaG.getY()
												+ posCourante.getY())) {
							posFutureCaisse.setX(posFutureCaisse.getX()
									+ temp.getX() + deltaG.getX());
							posFutureCaisse.setY(posFutureCaisse.getY()
									+ temp.getY() + deltaG.getY());
							orientationRobot = Robot
									.pivoterGauche(orientationRobot);
							tempsMis += TEMPS_PIVOTER_CAISSE;
							deplacement.add(ACTION_PIVOTER_GAUCHE);
							break;
						}
						// On essaye de pivoter à droite
						if ( // Position à droite de la caisse
						partieClone.isPositionOKAvecTout(
								n * temp.getX() + deltaD.getX()
										+ posCourante.getX(),
								n * temp.getY() + deltaD.getY()
										+ posCourante.getY())
								&& // Position à droite du robot
								partieClone.isPositionOKAvecTout(
										(n + 1) * temp.getX() + deltaD.getX()
												+ posCourante.getX(), (n + 1)
												* temp.getY() + deltaD.getY()
												+ posCourante.getY())) {
							posFutureCaisse.setX(posFutureCaisse.getX()
									+ temp.getX() + deltaD.getX());
							posFutureCaisse.setY(posFutureCaisse.getY()
									+ temp.getY() + deltaD.getY());
							orientationRobot = Robot
									.pivoterDroite(orientationRobot);
							tempsMis += TEMPS_PIVOTER_CAISSE;
							deplacement.add(ACTION_PIVOTER_DROITE);
							break;
						}
						// On essaye de reculer
						if (partieClone.isPositionOKAvecTout(n * temp.getX()
								+ posCourante.getX(), n * temp.getY()
								+ posCourante.getY())) {
							posFutureCaisse.setX(posFutureCaisse.getX()
									+ temp.getX());
							posFutureCaisse.setY(posFutureCaisse.getY()
									+ temp.getY());
							n++;
							tempsMis += TEMPS_RECULER_CAISSE;
							deplacement.add(ACTION_RECULER);
						} else { // On ne peux pas reculer
							// Il ne faut pas prendre cette position
							tempsMis = Float.MAX_VALUE;
							break;
						}
					}
					deplacement.add(ACTION_CHARGER);
					if (tempsMis < tempsMin) {
						orientationMin = orientationRobot;
						tempsMin = tempsMis;
						deplacementMin = new ArrayList<Integer>(deplacement);
						posMin = posCourante;
						posFutureCaisseMin = posFutureCaisse;
						isPositionMin = true;
					}
				}
			}
			if (posMin != null) {
				orientationDepart = orientationMin;
				positionDepart = posMin;
				deplacementFinal.addAll(getDeplacement(posMin, true));
				deplacementFinal.addAll(deplacementMin);
			}
			// reset des variables minimales
			posMin = null;
			tempsMin = Float.MAX_VALUE;
			deplacementMin = null;
			posFutureCaisseMin = null;

			// Niveau supérieur
			compteur--;
		} // compteur == 0

		return deplacementFinal;
	}

	/**
	 * <p>
	 * Recherche des plus court chemin à partir de la position du robot.<br />
	 * On applique ici l'algorithme de Dijkstra qui permet de déterminer le
	 * temps mis pour aller de la position initiale à une autre position.<br />
	 * A chaque temps est associé une position pour permettre de retrouver le
	 * trajet<br />
	 * Le tableau est construit sur une dimension pour retrouver une position il
	 * suffit de faire : <br />
	 * x = i % nbLigne<br />
	 * y = i / nbLigne<br />
	 * Avec i l'indice du tableau.
	 * </p>
	 * 
	 * @param pos_ini
	 *            position initiale du départ de l'algorithme
	 * 
	 * @param ori_ini
	 *            orientation initiale du robot au départ de l'algorithme
	 * 
	 * @see metier.IntelligenceArtificielle#getDeplacement(float[], int[])
	 */
	@SuppressWarnings("unused")
	private void dijkstraSansCaisse(Position pos_ini, int ori_ini) {
		// Tableau contenant les résultats de l'algorithme de Dijkstra
		temps = new float[partieClone.getNbLigne() * partieClone.getNbColonne()];
		// Indice déjà utilisé dans l'algorithme
		int[] indiceDejaUtilise = new int[temps.length];
		// Tableau contenant les orientations du robot pour les plus court
		// chemin
		orientations = new int[temps.length];

		// Or indiceDejaUtilise.length = orientations.length = temps.length
		// Algorithme de Dijkstra
		// Initialisation du tableau
		// On initialise toutes les valeurs à la valeur maximal
		// (Float.MAX_VALUE)
		// sauf l'indice choisit qui est initialisé à 0
		for (int i = 0; i < indiceDejaUtilise.length; i++) {
			temps[i] = Float.MAX_VALUE;

			// On initialise les tableaux avec des valeurs négatives pour
			// optimiser le parcours
			indiceDejaUtilise[i] = INOCCUPE;
			orientations[i] = INOCCUPE;
		}
		temps[positionToIndice(pos_ini)] = 0.0f;
		// On garde néanmoins l'orientation de départ du robot
		orientations[positionToIndice(pos_ini)] = ori_ini;

		// Pour chaque tour,
		// On récupère l'indice de la valeur minimal du tableau sans tenir
		// compte des indice précédement utilisé
		// On regarde si les position adjacentes sont occupées ou non
		// On calcule le temps mis pour aller jusqu'à la position adjacente
		// On réactualise le tableau si le temps mis est inférieur au temps déjà
		// présent sur la position adjacente
		for (int i = 0; i < indiceDejaUtilise.length; i++) {

			// Indice de la valeur minimal du tableau (temps)
			int indiceCentral = getIndiceMinimal(temps, indiceDejaUtilise);

			// Position adjacente de l'indice précédent
			Position[] positionsAdjacentes = Position
					.getPositionsAdjacentes(indiceToPosition(indiceCentral));

			// Parcours des positions adjacentes
			for (int j = 0; j < positionsAdjacentes.length; j++) {

				// Indice de la position adjacente
				int indice = positionToIndice(positionsAdjacentes[j]);

				// Sachant que l'indice du tableau (positions adjacentes) est
				// équivalent à l'orientation du robot
				if (partieClone.isPositionOKAvecTout(positionsAdjacentes[j])) {

					if (j == orientations[indiceCentral]) {
						// Position qui suit l'orientation du robot

						if (TEMPS_AVANCER <= TEMPS_RECULER) {
							if (TEMPS_AVANCER + temps[indiceCentral] < temps[indice]) {
								temps[indice] = temps[indiceCentral]
										+ TEMPS_AVANCER;
								orientations[indice] = orientations[indiceCentral];
							}
						} else {
							// Nombre de place qui délimite quand est ce qu'il
							// est plus rentable de reculer que d'avancer
							float n = (-4 * TEMPS_PIVOTER)
									/ (TEMPS_RECULER - TEMPS_AVANCER);
							Position posRobot = indiceToPosition(indiceCentral), //
							posCaisse = getCaisse(posRobot, j);
							// Permet de parcourir les cases dans l'alignement
							// du robot
							Position delta = new Position(posCaisse.getX()
									- posRobot.getX(), posCaisse.getY()
									- posRobot.getX());
							int compteur;
							for (compteur = 1; compteur <= n; compteur++) {
								if (!partieClone.isPositionOKAvecTout(
										posRobot.getX() + compteur
												* delta.getX(), posRobot.getY()
												+ compteur * delta.getX())) {
									break;
								}
							}
							if (compteur > n) {
								// Il est plus rentable de reculer
								if (TEMPS_RECULER + 2 * TEMPS_PIVOTER
										+ temps[indiceCentral] < temps[indice]) {
									temps[indice] = temps[indiceCentral]
											+ TEMPS_RECULER + 2 * TEMPS_PIVOTER;
									orientations[indice] = Robot
											.demiTourOrientation(orientations[indiceCentral]);
								}
							} else {
								// Il est plus rentable d'avancer
								if (TEMPS_AVANCER + temps[indiceCentral] < temps[indice]) {
									temps[indice] = temps[indiceCentral]
											+ TEMPS_AVANCER;
									orientations[indice] = orientations[indiceCentral];
								}
							}
						}
					} else if (j == Robot
							.demiTourOrientation(orientations[indiceCentral])) {
						// Position opposée à l'orientation du robot
						if (TEMPS_RECULER <= TEMPS_AVANCER) {
							if (TEMPS_RECULER + temps[indiceCentral] < temps[indice]) {
								temps[indice] = temps[indiceCentral]
										+ TEMPS_RECULER;
								orientations[indice] = orientations[indiceCentral];
							}
						} else {
							// Nombre de place qui délimite quand est ce qu'il
							// est plus rentable de reculer que d'avancer
							float n = (-2 * TEMPS_PIVOTER)
									/ (TEMPS_AVANCER - TEMPS_RECULER);
							Position posRobot = indiceToPosition(indiceCentral), //
							posCaisse = getCaisse(posRobot, j);
							// Permet de parcourir les cases dans l'alignement
							// du robot
							Position delta = new Position(posCaisse.getX()
									- posRobot.getX(), posCaisse.getY()
									- posRobot.getY());
							int compteur;
							for (compteur = 1; compteur <= n; compteur++) {
								if (!partieClone.isPositionOKAvecTout(
										posRobot.getX() + compteur
												* delta.getX(), posRobot.getY()
												+ compteur * delta.getY())) {
									break;
								}
							}
							if (compteur > n) {
								// Il est plus rentable d'avancer
								if (TEMPS_AVANCER + 2 * TEMPS_PIVOTER
										+ temps[indiceCentral] < temps[indice]) {
									temps[indice] = temps[indiceCentral]
											+ TEMPS_AVANCER + 2 * TEMPS_PIVOTER;
									orientations[indice] = Robot
											.demiTourOrientation(orientations[indiceCentral]);
								}
							} else {
								// Il est plus rentable de reculer
								if (TEMPS_RECULER + temps[indiceCentral] < temps[indice]) {
									temps[indice] = temps[indiceCentral]
											+ TEMPS_RECULER;
									orientations[indice] = orientations[indiceCentral];
								}
							}
						}
					} else if (j == Robot
							.pivoterDroite(orientations[indiceCentral])) {
						// On vérifie si l'on met plus de temps pour avancer ou
						// pour reculer
						if (TEMPS_AVANCER < TEMPS_RECULER) {
							if (TEMPS_AVANCER + TEMPS_PIVOTER
									+ temps[indiceCentral] < temps[indice]) {
								temps[indice] = temps[indiceCentral]
										+ TEMPS_AVANCER + TEMPS_PIVOTER;
								orientations[indice] = Robot
										.pivoterDroite(orientations[indiceCentral]);
							}
						} else {
							if (TEMPS_RECULER + temps[indiceCentral] < temps[indice]) {
								temps[indice] = temps[indiceCentral]
										+ TEMPS_RECULER;
								orientations[indice] = Robot
										.pivoterGauche(orientations[indiceCentral]);
							}
						}
					} else { // j ==
								// Robot.pivoterGauche(orientations[indiceCentral])
						// On vérifie si l'on met plus de temps pour avancer ou
						// pour reculer
						if (TEMPS_AVANCER < TEMPS_RECULER) {
							if (TEMPS_AVANCER + TEMPS_PIVOTER
									+ temps[indiceCentral] < temps[indice]) {
								temps[indice] = temps[indiceCentral]
										+ TEMPS_AVANCER + TEMPS_PIVOTER;
								orientations[indice] = Robot
										.pivoterGauche(orientations[indiceCentral]);
							}
						} else {
							if (TEMPS_RECULER + TEMPS_PIVOTER
									+ temps[indiceCentral] < temps[indice]) {
								temps[indice] = temps[indiceCentral]
										+ TEMPS_RECULER + TEMPS_PIVOTER;
								orientations[indice] = Robot
										.pivoterDroite(orientations[indiceCentral]);
							}
						}
					}
				} else if (!partieClone
						.isPositionOKAvecCaisse(positionsAdjacentes[j])) {
					// On est sur une caisse
					if (temps[indiceCentral] + TEMPS_DEPLACER_CAISSE < temps[indice]) {
						temps[indice] = temps[indiceCentral]
								+ TEMPS_DEPLACER_CAISSE;
					}
				}
			}
			indiceDejaUtilise[i] = indiceCentral;
		}
	}

	/**
	 * <p>
	 * Recherche des plus court chemin à partir de la position du robot.<br />
	 * On applique ici l'algorithme de Dijkstra qui permet de déterminer le
	 * temps mis pour aller de la position initiale à une autre position.<br />
	 * A chaque temps est associé une position pour permettre de retrouver le
	 * trajet<br />
	 * Le tableau est construit sur une dimension pour retrouver une position il
	 * suffit de faire : <br />
	 * x = i % nbLigne<br />
	 * y = i / nbLigne<br />
	 * Avec i l'indice du tableau.
	 * </p>
	 * 
	 * @param pos_ini
	 *            position initiale du robot au départ de l'algorithme
	 * 
	 * @param ori_ini
	 *            orientation initiale du robot au départ de l'algorithme
	 * 
	 * @see metier.IntelligenceArtificielle#getDeplacementVersCaisse(float[],
	 *      int[])
	 */
	private void dijkstraAvecCaisse(Position pos_ini, int ori_ini) {
		// On initialise les positions qui permettent de faire un demi-tour
		// Tableau contenant les résultats de l'algorithme de Dijkstra
		tempsCaisse = new float[partieClone.getNbLigne()
				* partieClone.getNbColonne()];
		// Indice déjà utilisé dans l'algorithme
		int[] indiceDejaUtilise = new int[tempsCaisse.length];
		// Tableau contenant les orientations du robot pour les plus court
		// chemin
		orientationsCaisse = new int[tempsCaisse.length];

		// Or indiceDejaUtilise.length = orientationsCaisse.length =
		// tempsCaisse.length
		// Algorithme de Dijkstra
		// Initialisation du tableau
		// On initialise toutes les valeurs à la valeur maximal
		// (Float.MAX_VALUE)
		// sauf l'indice choisit qui est initialisé à 0
		for (int i = 0; i < indiceDejaUtilise.length; i++) {
			tempsCaisse[i] = Float.MAX_VALUE;

			// On initialise les tableaux avec des valeurs négatives pour
			// optimiser le parcours
			indiceDejaUtilise[i] = INOCCUPE;
			orientationsCaisse[i] = INOCCUPE;
		}

		tempsCaisse[positionToIndice(pos_ini)] = 0.0f;
		// On garde néanmoins l'orientation de départ du robot
		orientationsCaisse[positionToIndice(pos_ini)] = ori_ini;

		// Pour chaque tour,
		// On récupère l'indice de la valeur minimal du tableau sans tenir
		// compte des indice précédement utilisé
		// On regarde si les position adjacentes sont occupés ou non
		// On calcul le temps mis pour aller jusqu'à la position adjacentes
		// On réactualise le tableau si le temps mis est inférieur au temps déjà
		// présent sur la position adjacente
		for (int i = 0; i < indiceDejaUtilise.length; i++) {

			// Indice de la valeur minimal du tableau (temps)
			int indiceCentral = getIndiceMinimal(tempsCaisse, indiceDejaUtilise);

			// Position de l'indice central <=> Position du robot
			Position posRobot = indiceToPosition(indiceCentral);

			// indice de la position de la caisse
			int indiceCaisse = positionToIndice(getCaisse(posRobot,
					orientationsCaisse[indiceCentral]));

			// Position de la caisse
			Position posCaisse = indiceToPosition(indiceCaisse);

			// Position adjacente de l'indice précédent
			Position[] positionsAdjacentes = Position
					.getPositionsAdjacentes(indiceToPosition(indiceCentral));

			// Parcours des positions adjacentes
			for (int j = 0; j < positionsAdjacentes.length; j++) {
				// On vérifie que la position soit compris dans le tableau de
				// jeu
				if (!partieClone.isPositionOK(positionsAdjacentes[j])) {
					continue;
				}

				// Indice de la position adjacente
				int indice = positionToIndice(positionsAdjacentes[j]);

				// Sachant que l'indice du tableau (positions adjacentes)
				// est équivalent à l'orientation du robot
				if (j == orientationsCaisse[indiceCentral]) {
					// Position qui suit l'orientation du robot
					// <=> Position de la caisse que tient le robot
					// On essaye d'avancer et si on ne peut pas alors on fait un
					// demi-tour puis on recule
					Position delta = indiceToPosition(indiceCaisse
							- indiceCentral);
					// On vérifie si le déplacement est possible
					if (partieClone.isPositionOKAvecTout(posCaisse.getX()
							+ delta.getX(), posCaisse.getY() + delta.getY())
							&& TEMPS_AVANCER_CAISSE
									+ tempsCaisse[indiceCentral] < tempsCaisse[indice]) {
						tempsCaisse[indice] = tempsCaisse[indiceCentral]
								+ TEMPS_AVANCER_CAISSE;
						// On garde la même orientation
						orientationsCaisse[indice] = orientationsCaisse[indiceCentral];
					} else {
						// On essaye de reculer
						// Position commune au deux rotations
						if (partieClone.isPositionOKAvecTout(posRobot.getX()
								- delta.getX(), posRobot.getY() - delta.getY())) {

							Position delta2 = new Position(-delta.getY(),
									delta.getX());

							// Demi-tour vers la droite
							if (partieClone.isPositionOKAvecTout(
									posRobot.getX() + delta2.getX(),
									posRobot.getY() + delta2.getY())
									&& partieClone.isPositionOKAvecTout(
											posCaisse.getX() + delta2.getX(),
											posCaisse.getY() + delta2.getY())
									&& partieClone.isPositionOKAvecTout(
											posRobot.getX() + delta2.getX()
													- delta2.getY(),
											posRobot.getY() + delta2.getX()
													+ delta2.getY())
									&& TEMPS_RECULER_CAISSE + 2
											* TEMPS_PIVOTER_CAISSE
											+ tempsCaisse[indiceCentral] < tempsCaisse[indice]) {
								tempsCaisse[indice] = tempsCaisse[indiceCentral]
										+ TEMPS_RECULER_CAISSE
										+ (2 * TEMPS_PIVOTER_CAISSE)
										+ TEMPS_DEMI_TOUR_DROITE;
								// On inverse l'orientation
								orientationsCaisse[indice] = Robot
										.demiTourOrientation(orientationsCaisse[indiceCentral]);
								// Pour l'optimisation
								continue;
							}

							delta2 = new Position(delta.getY(), -delta.getX());

							// Demi-tour vers la gauche
							if (partieClone.isPositionOKAvecTout(
									posRobot.getX() + delta2.getX(),
									posRobot.getY() + delta2.getY())
									&& partieClone.isPositionOKAvecTout(
											posCaisse.getX() + delta2.getX(),
											posCaisse.getY() + delta2.getY())
									&& partieClone.isPositionOKAvecTout(
											posRobot.getX() + delta2.getX()
													+ delta2.getY(),
											posRobot.getY() - delta2.getX()
													+ delta2.getY())
									&& TEMPS_RECULER_CAISSE + 2
											* TEMPS_PIVOTER_CAISSE
											+ tempsCaisse[indiceCentral] < tempsCaisse[indice]) {

								tempsCaisse[indice] = tempsCaisse[indiceCentral]
										+ TEMPS_RECULER_CAISSE
										+ (2 * TEMPS_PIVOTER_CAISSE)
										+ TEMPS_DEMI_TOUR_GAUCHE;

								// On inverse l'orientation
								orientationsCaisse[indice] = Robot
										.demiTourOrientation(orientationsCaisse[indiceCentral]);
							}
						}
					}
				} else if (partieClone
						.isPositionOKAvecTout(positionsAdjacentes[j])) {
					// On est sur que l'indice de la position adjacente n'est
					// pas sur la position de la caisse
					if (j == Robot
							.demiTourOrientation(orientationsCaisse[indiceCentral])) {
						// Position opposée à l'orientation du robot
						// Temps mis = TEMPS_REEL_RECULER
						// On essaye d'avancer et si on ne peut pas alors on
						// recule

						Position delta = indiceToPosition(indiceCaisse
								- indiceCentral);

						// On essaye de pivoter puis d'avancer
						// Position commune au deux rotations
						if (partieClone.isPositionOKAvecTout(posRobot.getX()
								- delta.getX(), posRobot.getY() - delta.getY())) {

							Position delta2 = new Position(-delta.getY(),
									delta.getX());

							// Demi-tour vers la droite
							if (partieClone.isPositionOKAvecTout(
									posRobot.getX() + delta2.getX(),
									posRobot.getY() + delta2.getY())
									&& partieClone.isPositionOKAvecTout(
											posCaisse.getX() + delta2.getX(),
											posCaisse.getY() + delta2.getY())
									&& partieClone.isPositionOKAvecTout(
											posRobot.getX() + delta2.getX()
													- delta2.getY(),
											posRobot.getY() + delta2.getX()
													+ delta2.getY())
									&& TEMPS_AVANCER_CAISSE + 2
											* TEMPS_PIVOTER_CAISSE
											+ tempsCaisse[indiceCentral] < tempsCaisse[indice]) {
								tempsCaisse[indice] = tempsCaisse[indiceCentral]
										+ TEMPS_AVANCER_CAISSE
										+ (2 * TEMPS_PIVOTER_CAISSE)
										+ TEMPS_DEMI_TOUR_DROITE;
								// On inverse l'orientation
								orientationsCaisse[indice] = Robot
										.demiTourOrientation(orientationsCaisse[indiceCentral]);
								// Pour l'optimisation
								continue;
							}

							delta2 = new Position(delta.getY(), -delta.getX());

							// Demi-tour vers la gauche
							if (partieClone.isPositionOKAvecTout(
									posRobot.getX() + delta2.getX(),
									posRobot.getY() + delta2.getY())
									&& partieClone.isPositionOKAvecTout(
											posCaisse.getX() + delta2.getX(),
											posCaisse.getY() + delta2.getY())
									&& partieClone.isPositionOKAvecTout(
											posRobot.getX() + delta2.getX()
													+ delta2.getY(),
											posRobot.getY() - delta2.getX()
													+ delta2.getY())
									&& TEMPS_AVANCER_CAISSE + 2
											* TEMPS_PIVOTER_CAISSE
											+ tempsCaisse[indiceCentral] < tempsCaisse[indice]) {

								tempsCaisse[indice] = tempsCaisse[indiceCentral]
										+ TEMPS_AVANCER_CAISSE
										+ (2 * TEMPS_PIVOTER_CAISSE)
										+ TEMPS_DEMI_TOUR_GAUCHE;

								// On inverse l'orientation
								orientationsCaisse[indice] = Robot
										.demiTourOrientation(orientationsCaisse[indiceCentral]);
								continue;
							}
						}

						// On peut reculer
						if (TEMPS_RECULER_CAISSE + tempsCaisse[indiceCentral] < tempsCaisse[indice]) {
							tempsCaisse[indice] = tempsCaisse[indiceCentral]
									+ TEMPS_RECULER_CAISSE;
							// On garde la même orientation
							orientationsCaisse[indice] = orientationsCaisse[indiceCentral];
						}
					} else if (j == Robot
							.pivoterDroite(orientationsCaisse[indiceCentral])) {
						// Utiliser pour optimiser les calculs liés aux
						// détections des caisses qui pourraient bloqués le
						// déplacement
						Position temp = indiceToPosition(indiceCentral
								- indiceCaisse);
						Position deltaDroite = new Position(temp.getY(),
								-temp.getX());

						// On vérifie si le déplacement est possible
						if (
						// Position (deux fois) à droite du robot
						partieClone.isPositionOKAvecTout(posRobot.getX() + 2
								* deltaDroite.getX(), posRobot.getY() + 2
								* deltaDroite.getY())) {

							if (TEMPS_AVANCER_CAISSE + TEMPS_PIVOTER_CAISSE
									+ tempsCaisse[indiceCentral] < tempsCaisse[indice]
									// Position à droite de la caisse
									&& partieClone.isPositionOKAvecTout(
											posCaisse.getX()
													+ deltaDroite.getX(),
											posCaisse.getY()
													+ deltaDroite.getY())) {
								tempsCaisse[indice] = tempsCaisse[indiceCentral]
										+ TEMPS_AVANCER_CAISSE
										+ TEMPS_PIVOTER_CAISSE;
								orientationsCaisse[indice] = Robot
										.pivoterDroite(orientationsCaisse[indiceCentral]);
							} else if (TEMPS_AVANCER_CAISSE + 3
									* TEMPS_PIVOTER_CAISSE
									+ tempsCaisse[indiceCentral] < tempsCaisse[indice]) {

								Position deltaGauche = new Position(
										-temp.getY(), temp.getX());

								if (// Position à gauche du robot
								partieClone.isPositionOKAvecTout(
										posRobot.getX() + deltaGauche.getX(),
										posRobot.getY() + deltaGauche.getY())
								// Position à gauche de la caisse
										&& partieClone.isPositionOKAvecTout(
												posCaisse.getX()
														+ deltaGauche.getX(),
												posCaisse.getY()
														+ deltaGauche.getY())
										// Position à l'arrière centre du
										// robot
										&& partieClone.isPositionOKAvecTout(
												posCaisse.getX() + temp.getX(),
												posCaisse.getY() + temp.getY())
										// Position à l'arrière gauche du
										// robot
										&& partieClone.isPositionOKAvecTout(
												posCaisse.getX() + temp.getX()
														+ deltaGauche.getX(),
												posCaisse.getY() + temp.getY()
														+ deltaGauche.getY())
										// Position à l'arrière droite du
										// robot
										&& partieClone.isPositionOKAvecTout(
												posCaisse.getX() + temp.getX()
														+ deltaDroite.getX(),
												posCaisse.getY() + temp.getY()
														+ deltaDroite.getY())) {

									tempsCaisse[indice] = tempsCaisse[indiceCentral]
											+ TEMPS_AVANCER_CAISSE
											+ (3 * TEMPS_PIVOTER_CAISSE)
											+ TEMPS_TROIS_QUART_DROITE;
									orientationsCaisse[indice] = Robot
											.pivoterDroite(orientationsCaisse[indiceCentral]);
									continue;
								}
							}

						}
						if (TEMPS_RECULER_CAISSE + TEMPS_PIVOTER_CAISSE
								+ tempsCaisse[indiceCentral] < tempsCaisse[indice]) {

							Position deltaGauche = new Position(-temp.getY(),
									temp.getX());

							// On vérifie si le déplacement est possible
							if (
							// Position à gauche du robot
							partieClone.isPositionOKAvecTout(posRobot.getX()
									+ deltaGauche.getX(), posRobot.getY()
									+ deltaGauche.getY())
									// Position à gauche de la caisse
									&& partieClone.isPositionOKAvecTout(
											posCaisse.getX()
													+ deltaGauche.getX(),
											posCaisse.getY()
													+ deltaGauche.getY())) {

								tempsCaisse[indice] = tempsCaisse[indiceCentral]
										+ TEMPS_RECULER_CAISSE
										+ TEMPS_PIVOTER_CAISSE;
								orientationsCaisse[indice] = Robot
										.pivoterGauche(orientationsCaisse[indiceCentral]);
							} // else

						}
					} else { // j ==
								// Robot.pivoterGauche(orientationsCaisse[indiceCentral])

						if (TEMPS_AVANCER_CAISSE + TEMPS_PIVOTER_CAISSE
								+ tempsCaisse[indiceCentral] < tempsCaisse[indice]) {
							// Utiliser pour optimiser les calculs liés aux
							// détections des caisses qui pourraient bloqués le
							// déplacement
							Position temp = indiceToPosition(indiceCentral
									- indiceCaisse);
							Position deltaGauche = new Position(-temp.getY(),
									temp.getX());
							// On vérifie si le déplacement est possible
							if (
							// Position (deux fois) à gauche du robot
							partieClone.isPositionOKAvecTout(posRobot.getX()
									+ 2 * deltaGauche.getX(), posRobot.getY()
									+ 2 * deltaGauche.getY())) {
								if (
								// Position à gauche de la caisse
								partieClone.isPositionOKAvecTout(
										posCaisse.getX() + deltaGauche.getX(),
										posCaisse.getY() + deltaGauche.getY())) {
									tempsCaisse[indice] = tempsCaisse[indiceCentral]
											+ TEMPS_AVANCER_CAISSE
											+ TEMPS_PIVOTER_CAISSE;
									orientationsCaisse[indice] = Robot
											.pivoterGauche(orientationsCaisse[indiceCentral]);
									continue;
									// On tente de faire une 3/4 de tour vers la
									// gauche puis d'avancer
								} else if (TEMPS_AVANCER_CAISSE + 3
										* TEMPS_PIVOTER_CAISSE
										+ tempsCaisse[indiceCentral] < tempsCaisse[indice]) {
									Position deltaDroite = new Position(
											temp.getY(), -temp.getX());

									if (// Position à droite du robot
									partieClone.isPositionOKAvecTout(
											posRobot.getX()
													+ deltaDroite.getX(),
											posRobot.getY()
													+ deltaDroite.getY())
											// Position à droite de la caisse
											&& partieClone
													.isPositionOKAvecTout(
															posCaisse.getX()
																	+ deltaDroite
																			.getX(),
															posCaisse.getY()
																	+ deltaDroite
																			.getY())
											// Position à l'arrière centre du
											// robot
											&& partieClone
													.isPositionOKAvecTout(
															posCaisse.getX()
																	+ temp.getX(),
															posCaisse.getY()
																	+ temp.getY())
											// Position à l'arrière gauche du
											// robot
											&& partieClone
													.isPositionOKAvecTout(
															posCaisse.getX()
																	+ temp.getX()
																	+ deltaGauche
																			.getX(),
															posCaisse.getY()
																	+ temp.getY()
																	+ deltaGauche
																			.getY())
											// Position à l'arrière droite du
											// robot
											&& partieClone
													.isPositionOKAvecTout(
															posCaisse.getX()
																	+ temp.getX()
																	+ deltaDroite
																			.getX(),
															posCaisse.getY()
																	+ temp.getY()
																	+ deltaDroite
																			.getY())) {

										tempsCaisse[indice] = tempsCaisse[indiceCentral]
												+ TEMPS_AVANCER_CAISSE
												+ (3 * TEMPS_PIVOTER_CAISSE)
												+ TEMPS_TROIS_QUART_GAUCHE;
										orientationsCaisse[indice] = Robot
												.pivoterGauche(orientationsCaisse[indiceCentral]);
										continue;
									}
								}
							}
						}

						if (TEMPS_RECULER_CAISSE + TEMPS_PIVOTER_CAISSE
								+ tempsCaisse[indiceCentral] < tempsCaisse[indice]) {

							// Utiliser pour optimiser les calculs liés aux
							// détections des caisses qui pourraient bloqués le
							// déplacement
							Position temp = indiceToPosition(indiceCentral
									- indiceCaisse);
							Position delta = new Position(temp.getY(),
									-temp.getX());
							if (
							// Position à droite du robot
							partieClone.isPositionOKAvecTout(posRobot.getX()
									+ delta.getX(),
									posRobot.getY() + delta.getY())
									// Position à droite de la caisse
									&& partieClone.isPositionOKAvecTout(
											posCaisse.getX() + delta.getX(),
											posCaisse.getY() + delta.getY())) {
								// On vérifie si le déplacement est possible

								tempsCaisse[indice] = tempsCaisse[indiceCentral]
										+ TEMPS_RECULER_CAISSE
										+ TEMPS_PIVOTER_CAISSE;
								orientationsCaisse[indice] = Robot
										.pivoterDroite(orientationsCaisse[indiceCentral]);
							}
						}
					}
				}
			}
			indiceDejaUtilise[i] = indiceCentral;
		}
	}

	/**
	 * <p>
	 * Vérifie si le robot peut réaliser tous les déplacements pour atteindre
	 * son objectif (caisse ou vortex). <br />
	 * S'il peut alors on identifie les déplacements supplémentaires qu'il
	 * faudrait faire (dans le cas ou l'orientation finale du robot n'est pas en
	 * face du vortex).<br />
	 * Les déplacements seront ajoutés à la variable deplacement
	 * </p>
	 * 
	 * @param deplacement
	 *            Liste des déplacements que le robot devra faire en plus pour
	 *            atteindre la caisse et le vortex
	 * @param posRobot
	 *            position du robot
	 * @param oriRobot
	 *            orientation du robot
	 * @param posArrivee
	 *            position ou le robot doit se rendre
	 * @param withCaisse
	 *            Le robot est avec ou sans sa caisse
	 * @return true si le déplacement le robot peut atteindre le vortex avec sa
	 *         caisse, false sinon
	 */
	private boolean getDeplacementSupplementaire(List<Integer> deplacement,
			Position posRobot, int oriRobot, Position posArrivee,
			boolean withCaisse) {
		// indice de la position du robot
		int indiceRobot = positionToIndice(posRobot);

		// Calcul utilisé pout déterminier l'orientation que doit
		// avoir le robot pour atteindre la caisse
		Position delta = indiceToPosition(indiceRobot
				- positionToIndice(posArrivee));
		// Orientation que doit avoir le robot
		int orientationFinale;
		if (delta.getX() > 0) {
			orientationFinale = Robot.ORIENTATION_GAUCHE;
		} else if (delta.getX() < 0) {
			orientationFinale = Robot.ORIENTATION_DROITE;
		} else if (delta.getY() > 0) {
			orientationFinale = Robot.ORIENTATION_HAUT;
		} else { // delta.getY() < 0
			orientationFinale = Robot.ORIENTATION_BAS;
		}

		if (!withCaisse) {
			// On cherche la caisse

			// On regarde l'orientation du robot et l'orientation finale
			if (Robot.demiTourOrientation(oriRobot) == orientationFinale) {
				deplacement.add(ACTION_PIVOTER_DROITE);
				deplacement.add(ACTION_PIVOTER_DROITE);
			} else if (Robot.pivoterDroite(oriRobot) == orientationFinale) {
				deplacement.add(ACTION_PIVOTER_DROITE);
			} else if (Robot.pivoterGauche(oriRobot) == orientationFinale) {
				deplacement.add(ACTION_PIVOTER_GAUCHE);
			}
			// else orientation finale = orientation robot => Rien à faire

			// Aucun soucis
			return true;
		} else {
			// On cherche déposer la caisse dans le vortex

			// On récupère la position de la caisse
			Position posCaisse = getCaisse(posRobot, oriRobot);

			// On regarde l'orientation du robot et l'orientation finale
			if (Robot.demiTourOrientation(oriRobot) == orientationFinale) {
				// On regarde si l'on peut pivoter sur la droite

				// Variable utilisé pour facilite les calculs en faisant des
				// calculs génériques (quelque soit l'orientation du robot)
				Position delta2 = new Position(
						(posRobot.getY() - posArrivee.getY()),
						(posArrivee.getX() - posRobot.getX()));
				if (partieClone.isPositionOKAvecTout(
						(posRobot.getX() + delta2.getX()),
						(posRobot.getY() + delta2.getY()))
						&& partieClone.isPositionOKAvecTout(
								(posArrivee.getX() + delta2.getX()),
								(posArrivee.getY() + delta2.getY()))
						&& partieClone.isPositionOKAvecTout(
								(posCaisse.getX() + delta2.getX()),
								(posCaisse.getY() + delta2.getY()))) {
					deplacement.add(ACTION_PIVOTER_DROITE);
					deplacement.add(ACTION_PIVOTER_DROITE);
					return true;
				}

				// On regarde si on peut pivoter sur la gauche
				delta2.setX(-delta2.getX());
				delta2.setY(-delta2.getY());
				if (partieClone.isPositionOKAvecTout(
						(posRobot.getX() + delta2.getX()),
						(posRobot.getY() + delta2.getY()))
						&& partieClone.isPositionOKAvecTout(
								(posArrivee.getX() + delta2.getX()),
								(posArrivee.getY() + delta2.getY()))
						&& partieClone.isPositionOKAvecTout(
								(posCaisse.getX() + delta2.getX()),
								(posCaisse.getY() + delta2.getY()))) {
					deplacement.add(ACTION_PIVOTER_GAUCHE);
					deplacement.add(ACTION_PIVOTER_GAUCHE);
					return true;
				}

				// Déplacement impossible
				return false;
			} else if (Robot.pivoterDroite(oriRobot) == orientationFinale) {
				// On regarde si l'on peut pivoter sur la droite

				// Variable utilisé pour facilite les calculs en faisant des
				// calculs génériques (quelque soit l'orientation du robot)
				Position delta2 = new Position(
						(posArrivee.getY() - posCaisse.getY()),
						(posCaisse.getX() - posArrivee.getX()));
				if (partieClone.isPositionOKAvecTout(
						(posRobot.getX() + delta2.getX()),
						(posRobot.getY() + delta2.getY()))) {
					deplacement.add(ACTION_PIVOTER_DROITE);
					return true;
				}

				// Même fonctionnement que deltaAvecCaisse
				Position delta3 = new Position(
						(posRobot.getX() - posCaisse.getX()),
						(posRobot.getY() - posCaisse.getY()));
				// On regarde si l'on peut pivoter trois fois sur la gauche
				if (partieClone.isPositionOKAvecTout(
						(posRobot.getX() - delta2.getX()),
						(posRobot.getY() - delta2.getY()))
						&& partieClone.isPositionOKAvecTout(
								(posArrivee.getX() + delta3.getX()),
								(posArrivee.getY() + delta3.getY()))
						&& partieClone.isPositionOKAvecTout(
								(posRobot.getX() + delta3.getX()),
								(posRobot.getY() + delta3.getY()))
						&& partieClone.isPositionOKAvecTout(
								(posCaisse.getX() - delta3.getY()),
								(posCaisse.getY() + delta3.getX()))
						&& partieClone.isPositionOKAvecTout(
								(posRobot.getX() - delta3.getY()),
								(posRobot.getY() + delta3.getX()))) {
					deplacement.add(ACTION_PIVOTER_GAUCHE);
					deplacement.add(ACTION_PIVOTER_GAUCHE);
					deplacement.add(ACTION_PIVOTER_GAUCHE);
					return true;
				}

				// Déplacement impossible
				return false;
			} else if (Robot.pivoterGauche(oriRobot) == orientationFinale) {
				// On regarde si l'on peut pivoter sur la gauche

				// Variable utilisé pour facilite les calculs en faisant des
				// calculs génériques (quelque soit l'orientation du robot)
				Position delta2 = new Position(
						(posCaisse.getY() - posArrivee.getY()),
						(posArrivee.getX() - posCaisse.getX()));
				if (partieClone.isPositionOKAvecTout(
						(posRobot.getX() + delta2.getX()),
						(posRobot.getY() + delta2.getY()))) {
					deplacement.add(ACTION_PIVOTER_GAUCHE);
					return true;
				}

				// Même fonctionnement que deltaAvecCaisse
				Position delta3 = new Position(
						(posRobot.getX() - posCaisse.getX()),
						(posRobot.getY() - posCaisse.getY()));
				// On regarde si l'on peut pivoter trois fois sur la droite
				if (partieClone.isPositionOKAvecTout(
						(posRobot.getX() - delta2.getX()),
						(posRobot.getY() - delta2.getY()))
						&& partieClone.isPositionOKAvecTout(
								(posArrivee.getX() + delta3.getX()),
								(posArrivee.getY() + delta3.getY()))
						&& partieClone.isPositionOKAvecTout(
								(posRobot.getX() + delta3.getX()),
								(posRobot.getY() + delta3.getY()))
						&& partieClone.isPositionOKAvecTout(
								(posCaisse.getX() + delta3.getY()),
								(posCaisse.getY() - delta3.getX()))
						&& partieClone.isPositionOKAvecTout(
								(posRobot.getX() + delta3.getY()),
								(posRobot.getY() - delta3.getX()))) {
					deplacement.add(ACTION_PIVOTER_DROITE);
					deplacement.add(ACTION_PIVOTER_DROITE);
					deplacement.add(ACTION_PIVOTER_DROITE);
					return true;
				}

				// Déplacement impossible
				return false;
			}
			return true;
		}
	}

	/**
	 * <p>
	 * Détermine la meilleure trajectoire que l'IA puisse prendre pour aller
	 * rechercher une caisse parmis celle proposée. L'IA utilise un tableau
	 * contenant les temps que mettra le Robot pour atteindre une position et
	 * l'orientation qu'il devra avoir.<br />
	 * </p>
	 * 
	 * @param posFinal
	 *            position d'arrivée du robot
	 * @param isChercherCaisse
	 *            true si l'on recherche à aller vers une caisse, false si on
	 *            recherche à aller vers le vortex
	 * 
	 * @return Liste des déplacements pour aller chercher une caisse
	 * 
	 * @see metier.Robot#ACTION_AVANCER
	 * @see metier.Robot#ACTION_CHARGER
	 * @see metier.Robot#ACTION_RECULER
	 * @see metier.Robot#ACTION_PIVOTER
	 */
	private List<Integer> getDeplacement(Position posFinal,
			boolean isChercherCaisse) {
		if (posFinal == null) {
			System.out.println("IA : Impossible d'accéder à une des caisses");
			return null;
		}
		// Temps calculés par l'algorithme de Dijkstra
		final float[] temps;
		// Orientations calculées par l'algorithme de Dijkstra
		final int[] orientations;
		// Constantes utilisées pour la recherche des chemins
		// <=> poids des arcs dans un graphe
		final float TEMPS_RECULER, TEMPS_PIVOTER, TEMPS_AVANCER;

		if (isChercherCaisse) { // Recherche du chemin Robot -> Caisse
			temps = this.temps;
			orientations = this.orientations;
			TEMPS_RECULER = IntelligenceArtificielle.TEMPS_RECULER;
			TEMPS_PIVOTER = IntelligenceArtificielle.TEMPS_PIVOTER;
			TEMPS_AVANCER = IntelligenceArtificielle.TEMPS_AVANCER;
		} else { // Recherche du chemin Robot + Caisse -> Vortex
			temps = this.tempsCaisse;
			orientations = this.orientationsCaisse;
			TEMPS_RECULER = IntelligenceArtificielle.TEMPS_RECULER_CAISSE;
			TEMPS_PIVOTER = IntelligenceArtificielle.TEMPS_PIVOTER_CAISSE;
			TEMPS_AVANCER = IntelligenceArtificielle.TEMPS_AVANCER_CAISSE;
		}

		// else
		List<Integer> deplacement = new ArrayList<Integer>();

		// On recherche la trajectoire pour atteindre posMin en utilisant les
		// deux tableaux temps et orientations

		// Position courante à un instant t
		int indiceCourant = positionToIndice(posFinal);

		while (temps[indiceCourant] != 0.0f) {
			// On cherche les positions adjacentes à posMin
			Position[] posAdjacentes = Position
					.getPositionsAdjacentes(indiceToPosition(indiceCourant));

			for (int j = 0; j < posAdjacentes.length; j++) {

				if (!partieClone.isPositionOK(posAdjacentes[j])) {
					continue;
				}

				// Indice dans le tableau de la position adjacente
				int indiceAdj = positionToIndice(posAdjacentes[j]);

				if (temps[indiceCourant] > temps[indiceAdj]) {

					// Orientation de l'indice courant
					int orientationCourant = orientations[indiceCourant];
					// Orientation de la position adjacente
					int orientationAdj = orientations[indiceAdj];

					if (j == orientationCourant) {
						// Position qui suit l'orientation du robot

						if (orientationCourant == orientationAdj) {
							// Action = reculer
							if (temps[indiceCourant] == temps[indiceAdj]
									+ TEMPS_RECULER) {

								deplacement.add(ACTION_RECULER);

								indiceCourant = indiceAdj;
								// On cherche un chemin, on ne cherche pas tous
								// les chemins
								break;
							}

						} else if (orientationCourant == Robot
								.demiTourOrientation(orientationAdj)) {
							// Demi-tour à gauche + reculer
							if (temps[indiceCourant] == temps[indiceAdj] + 2
									* TEMPS_PIVOTER + TEMPS_RECULER
									|| temps[indiceCourant] == temps[indiceAdj]
											+ 2 * TEMPS_PIVOTER + TEMPS_RECULER
											+ TEMPS_DEMI_TOUR_GAUCHE) {

								deplacement.add(ACTION_RECULER);
								deplacement.add(ACTION_PIVOTER_GAUCHE);
								deplacement.add(ACTION_PIVOTER_GAUCHE);

								indiceCourant = indiceAdj;
								break;
							}

							// Demi-tour à droite + reculer
							if (temps[indiceCourant] == temps[indiceAdj] + 2
									* TEMPS_PIVOTER + TEMPS_RECULER
									+ TEMPS_DEMI_TOUR_DROITE) {

								deplacement.add(ACTION_RECULER);
								deplacement.add(ACTION_PIVOTER_DROITE);
								deplacement.add(ACTION_PIVOTER_DROITE);

								indiceCourant = indiceAdj;
								break;
							}

						} else if (orientationCourant == Robot
								.pivoterDroite(orientationAdj)) {
							// Action = Pivoter droite + Reculer
							if (temps[indiceCourant] == temps[indiceAdj]
									+ TEMPS_PIVOTER + TEMPS_RECULER) {

								deplacement.add(ACTION_RECULER);
								deplacement.add(ACTION_PIVOTER_DROITE);

								indiceCourant = indiceAdj;
								break;
							}
						} else {
							// orientationCourant ==
							// Robot.pivoterGauche(orientationAdj)

							// Action = Pivoter gauche + Reculer
							if (temps[indiceCourant] == temps[indiceAdj]
									+ TEMPS_PIVOTER + TEMPS_RECULER) {

								deplacement.add(ACTION_RECULER);
								deplacement.add(ACTION_PIVOTER_GAUCHE);

								indiceCourant = indiceAdj;
								break;
							}
						}

					} else if (j == Robot
							.demiTourOrientation(orientationCourant)) {
						// Position derrière l'orientation du robot

						if (orientationCourant == orientationAdj) {
							// Action = Avancer
							if (temps[indiceCourant] == temps[indiceAdj]
									+ TEMPS_AVANCER) {

								deplacement.add(ACTION_AVANCER);

								indiceCourant = indiceAdj;
								break;
							}
						} else if (orientationCourant == Robot
								.demiTourOrientation(orientationAdj)) {
							// Demi-tour à gauche + avancer
							if (temps[indiceCourant] == temps[indiceAdj] + 2
									* TEMPS_PIVOTER + TEMPS_AVANCER
									|| temps[indiceCourant] == temps[indiceAdj]
											+ 2 * TEMPS_PIVOTER + TEMPS_AVANCER
											+ TEMPS_DEMI_TOUR_GAUCHE) {

								deplacement.add(ACTION_AVANCER);
								deplacement.add(ACTION_PIVOTER_GAUCHE);
								deplacement.add(ACTION_PIVOTER_GAUCHE);

								indiceCourant = indiceAdj;
								break;
							}
							// Demi-tour à droite + avancer
							if (temps[indiceCourant] == temps[indiceAdj] + 2
									* TEMPS_PIVOTER + TEMPS_AVANCER
									+ TEMPS_DEMI_TOUR_DROITE) {

								deplacement.add(ACTION_AVANCER);
								deplacement.add(ACTION_PIVOTER_DROITE);
								deplacement.add(ACTION_PIVOTER_DROITE);

								indiceCourant = indiceAdj;
								break;
							}

						} else if (orientationCourant == Robot
								.pivoterDroite(orientationAdj)) {
							// Action = Pivoter droite + Avancer
							if (temps[indiceCourant] == temps[indiceAdj]
									+ TEMPS_PIVOTER + TEMPS_AVANCER) {

								deplacement.add(ACTION_AVANCER);
								deplacement.add(ACTION_PIVOTER_DROITE);

								indiceCourant = indiceAdj;
								break;
							} else if (temps[indiceCourant] == temps[indiceAdj]
									+ 3 * TEMPS_PIVOTER + TEMPS_AVANCER
									+ TEMPS_TROIS_QUART_DROITE) {

								deplacement.add(ACTION_AVANCER);
								deplacement.add(ACTION_PIVOTER_GAUCHE);
								deplacement.add(ACTION_PIVOTER_GAUCHE);
								deplacement.add(ACTION_PIVOTER_GAUCHE);

								indiceCourant = indiceAdj;
								break;
							}

						} else {
							// orientationCourant ==
							// Robot.pivoterGauche(orientationAdj)
							// Action = Pivoter gauche
							if (temps[indiceCourant] == temps[indiceAdj]
									+ TEMPS_PIVOTER + TEMPS_AVANCER) {

								deplacement.add(ACTION_AVANCER);
								deplacement.add(ACTION_PIVOTER_GAUCHE);

								indiceCourant = indiceAdj;
								break;
							} else if (temps[indiceCourant] == temps[indiceAdj]
									+ 3 * TEMPS_PIVOTER + TEMPS_AVANCER
									+ TEMPS_TROIS_QUART_GAUCHE) {

								deplacement.add(ACTION_AVANCER);
								deplacement.add(ACTION_PIVOTER_DROITE);
								deplacement.add(ACTION_PIVOTER_DROITE);
								deplacement.add(ACTION_PIVOTER_DROITE);

								indiceCourant = indiceAdj;
								break;
							}
						}
					}
				}
			}
		}
		// On inverse le sens de la liste
		return inverserListe(deplacement);
	}

	/**
	 * Déplace le Robot de l'IA selon une liste de déplacement dans l'ordre
	 * logique, c'est à dire que le 1er élément de la liste doit être le 1er
	 * déplacement, etc...
	 * 
	 * @param deplacement
	 *            liste de déplacement dans l'ordre logique que doit faire le
	 *            robot
	 */
	private void deplacerIA(List<Integer> deplacement) {
		Robot robot = partieReelle.getRobot();
		if (deplacement == null) {
			System.out.println("IA ; Déplacer IA : Pas de déplacement défini");
		} else {
			synchronized (deplacement) {
				try {
					while (!deplacement.isEmpty()) {
						if (!robot.estOccupe()) {
							// On fait une seconde pause au cas ou la partie
							// graphique n'est pas totalement fini de se
							// redessiner
							Thread.sleep(PAUSE);
							if (deplacement.get(0) == ACTION_AVANCER) {
								robot.avancer();
								deplacement.remove(0);
							} else if (deplacement.get(0) == ACTION_PIVOTER_GAUCHE) {
								robot.pivoter(Robot.PIVOTER_GAUCHE);
								deplacement.remove(0);
							} else if (deplacement.get(0) == ACTION_PIVOTER_DROITE) {
								robot.pivoter(Robot.PIVOTER_DROITE);
								deplacement.remove(0);
							} else if (deplacement.get(0) == ACTION_RECULER) {
								robot.reculer();
								deplacement.remove(0);
							} else if (deplacement.get(0) == ACTION_CHARGER) {
								robot.charger();
								deplacement.remove(0);
							}
						}
						// else
						Thread.sleep(PAUSE);
					}
				} catch (InterruptedException e) {
					this.interrupt();
				}
			}
		}
	}

	/**
	 * Getion de la partie métier et "déplacement" du robot dans la partie de
	 * l'IA.
	 * 
	 * @return true si l'IA détecte que la partie est faisable, false sinon
	 */
	private boolean startIA() {
		List<Caisse>[] liste = getCaisseParCouleur();
		// On cherche la liste des déplacements pour aller chercher la caisse
		for (int i = 0; i < liste.length; i++) {
			if (!chercherChemin(liste[i])) {
				return false;
			}
			// On supprime la caisse de la partie
			removeCaisseCourante(liste[i]);
		}
		metierFinish = true;
		if (threadDeplacement.isAlive()) {
			synchronized (this) {
				try {
					Thread.currentThread().wait();
				} catch (InterruptedException e) {
					interrupt();
				}
			}
		}
		return true;
	}

	/**
	 * Annonce au Thread IA qu'il a finit
	 */
	public synchronized void finIA() {
		this.notify();
		if (partieReelle.getCaisseARecuperee().isEmpty()) {
			partieReelle.partieFinie();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		isPartieFaisable = startIA();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#interrupt()
	 */
	@Override
	public void interrupt() {
		super.interrupt();
		threadDeplacement.interrupt();
	}

	/**
	 * Inner class pour le thread contenant les déplacements du robot
	 */
	public class ThreadDeplacement extends Thread {

		/** Liste des déplacements à effectuer */
		private List<Integer> deplacement;

		/**
		 * Créer un thread qui gère les déplacements de l'IA sur la partie
		 * graphique
		 */
		public ThreadDeplacement() {
			super();
			this.deplacement = new ArrayList<Integer>();
		}

		/**
		 * @param ajoutDeplacement
		 *            le deplacement à modifier
		 */
		public void addDeplacement(List<Integer> ajoutDeplacement) {
			synchronized (deplacement) {
				this.deplacement.addAll(ajoutDeplacement);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			super.run();
			// Le Thread ne s'arrête seulement lorsqu'il reçoit un ordre
			// d'interruption
			while (true) {
				if (ToucheClavier.isPartieCommence() && !deplacement.isEmpty()) {
					deplacerIA(deplacement);
					if (metierFinish) {
						break;
					}
				}

				// else on récupère des ordres venant de l'IA
				try {
					Thread.sleep(PAUSE);
				} catch (InterruptedException e) {
					break;
				}
			}
			finIA();
		}
	}
}
