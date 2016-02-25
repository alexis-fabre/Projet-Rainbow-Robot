/*
 * IntelligenceArtificielle.java							24 févr. 2016
 * IUT Info2 2015-2016
 */
package metier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
 * <li>Niveau Facile : l'IA cherche à faire le moins de déplacement pour aller
 * chercher la caisse (On ne s'occupe pas du temps mis).L'IA cherche en mode
 * caisse par caisse.</li>
 * <li>Niveau Moyen : l'IA cherche à faire la partie en un minimum de temps en
 * optimisant ses déplacements. L'IA cherche en mode caisse par caisse.</li>
 * <li>Niveau Difficile : l'IA cherche à faire la partie en un minimum de temps
 * en optimisant ses déplacements. L'IA cherche son parcours en regardant les
 * caisses qui sont après.</li>
 * </ul>
 * <p>
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class IntelligenceArtificielle extends Thread {

	/** Niveau de difficulté de l'IA : Ici niveau facile */
	public static final int NIVEAU_FACILE = 0;

	/** Niveau de difficulté de l'IA : Ici niveau moyen */
	public static final int NIVEAU_MOYEN = 1;

	/** Niveau de difficulté de l'IA : Ici niveau difficile */
	public static final int NIVEAU_DIFFICILE = 2;

	/**
	 * Utilisé pour optimiser la recherche de l'indice minimal. Elle doit
	 * correspondre à une valeur inutilisé dans l'algorithme de Dijkstra
	 * 
	 * @see metier.IntelligenceArtificielle#getIndiceMinimal(int[], int[])
	 */
	private static final int INOCCUPE = -1;

	/** Partie que l'IA doit résoudre */
	private Partie partieCourante;

	/** Niveau de l'IA choisit lors de l'initialisation */
	private int niveau;

	/**
	 * Constructeur qui initialise une IA avec un niveau et une partie
	 * 
	 * @param niveau
	 *            niveau de l'IA
	 * @param partie
	 *            partie que l'IA doit résoudre
	 * 
	 * @throws IllegalArgumentException
	 *             si le niveau ou la partie sont inccorects
	 */
	public IntelligenceArtificielle(int niveau, Partie partie)
			throws IllegalArgumentException {
		if (partie == null) {
			throw new IllegalArgumentException(
					"Impossibilité de construire l'IA car la partie n'existe pas");
		}
		switch (niveau) {
		case NIVEAU_FACILE:
		case NIVEAU_MOYEN:
		case NIVEAU_DIFFICILE:
			break;
		default:
			throw new IllegalArgumentException(
					"Impossibilité de construire l'IA car le niveau n'est pas défini");
		}
		this.niveau = niveau;
		this.partieCourante = partie;
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
	private List<Caisse>[] chercherCaisse() {
		// Liste retourné par la fonction
		List<Caisse>[] liste = new ArrayList[partieCourante
				.getCaisseARecuperee().size()];
		// Caisse à récupérer, initialiser à chaque tour de boucle
		Caisse aRecuperer;

		// On récupère les données de la partie courante
		// Liste des caisses à récupérer
		List<Caisse> listeCaisseRecuperer = partieCourante
				.getCaisseARecuperee();
		// Tableau des caisses sur le plateau de jeu
		Caisse[] tableauCaissePlateau = partieCourante.getCaissePlateau();

		BouclePrincipale: for (int i = 0; i < liste.length; i++) {
			// Caisse à récupérer
			aRecuperer = listeCaisseRecuperer.get(i);
			// Pour l'optimisation
			for (int j = 0; j < i; j++) {
				if (aRecuperer.getCouleur().equals(
						listeCaisseRecuperer.get(j).getCouleur())) {
					liste[i] = liste[j];
					// On continue la boucle principale
					continue BouclePrincipale;
				}
			}

			// On initialise les listes du tableau
			liste[i] = new ArrayList<Caisse>();
			// On parcours les caisses sur le plateau de jeu
			for (int j = 0; j < tableauCaissePlateau.length; j++) {
				// On ajoute la caisse si elle a la même couleur que celle à
				// récupérer
				if (tableauCaissePlateau[j].getCouleur().equals(
						aRecuperer.getCouleur())) {
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
	 * La fonction élabore un ensemble de déplacement pour former une
	 * trajectoire capable d'aller chercher toutes les caisses et de les ramener
	 * au vortex. Les déplacements sont différents selon le niveau.
	 * 
	 * @param liste
	 *            tableau de liste contenant les caisses à récupérer sur le
	 *            plateau pour chaque couleur (indice)
	 */
	private void chercherChemin(List<Caisse>[] liste) {
		// Robot que l'IA doit contrôler
		Robot robot = partieCourante.getRobot();
		// On regarde les 4 positions adjacentes aux Robots
		// <=> 4 positions initiales pour l'algorithme de Dijkstra-Hoore
		Position[] posAdjacentes = getPositionsAdjacentes(robot.getPosRobot());

		parcoursChemin(posAdjacentes[0]);
		switch (niveau) {
		case NIVEAU_FACILE:

			break;
		case NIVEAU_MOYEN:
			break;
		case NIVEAU_DIFFICILE:
			break;
		}
	}

	/**
	 * <p>
	 * Recherche des plus court chemin à partir d'un position initiale. On
	 * applique ici l'algorithme de Dijkstra qui permet de déterminer le temps
	 * mis pour aller de la position initiale à une autre position.<br />
	 * Le tableau est construit sur une dimension pour retrouver une position il
	 * suffit de faire : <br />
	 * x = i % nbLigne<br />
	 * y = i / nbColonne<br />
	 * Avec i l'indice du tableau.
	 * </p>
	 * 
	 * @param pos_initiale
	 *            position initiale d'ou commence l'algorithme
	 * 
	 * @return tableau regroupant l'ensemble des temps mis pour aller de la
	 *         position initiale à la position finale.
	 */
	@SuppressWarnings("unused")
	private int[] parcoursChemin(Position pos_initiale) {
		// Temps mis pour effectuer les différentes actions
		// On tient en compte si l'action Avancer et plus longue que de faire
		// demi-tour (2 rotation) plus reculer.
		// De même pour Reculer
		// Temps nominal mis pour parcourir une case
		final float TEMPS_PIVOTER = (Robot.UNITE_TEMPS / Robot.VITESSE_PIVOTER_VIDE), //
		TEMPS_RECULER = (Robot.UNITE_TEMPS / Robot.VITESSE_RECULER_VIDE), //
		TEMPS_AVANCER = (Robot.UNITE_TEMPS / Robot.VITESSE_AVANCER_VIDE);

		// Temps réel mis pour parcourir une case
		final float TEMPS_REEL_AVANCER = (2 * TEMPS_PIVOTER + TEMPS_RECULER) < TEMPS_AVANCER ? (2 * TEMPS_PIVOTER + TEMPS_RECULER)
				: TEMPS_AVANCER;
		final float TEMPS_REEL_RECULER = (2 * TEMPS_PIVOTER + TEMPS_AVANCER) < TEMPS_RECULER ? (2 * TEMPS_PIVOTER + TEMPS_AVANCER)
				: TEMPS_RECULER;
		// Tableau contenant les résultats de l'algorithme de Dijkstra
		float[] aRetourner = new float[partieCourante.getNbLigne()
				* partieCourante.getNbColonne()];
		// Indice déjà utilisé dans l'algorithme
		int[] indiceDejaUtilise = new int[aRetourner.length];
		// Tableau contenant les orientations du robot pour les plus court
		// chemin
		int[] orientations = new int[aRetourner.length];

		// Indice de la valeur minimal du tableau
		// <=> Indice recherché par l'algorithme
		int indiceCentral;
		// Positions aux alentours de la position recherché par l'algorithme
		Position[] positionsAdjacentes;

		// On initialise les tableaux avec des valeurs négatives pour optimiser
		// le
		// parcours
		for (int i = 0; i < indiceDejaUtilise.length; i++) {
			indiceDejaUtilise[i] = INOCCUPE;
		}
		for (int i = 0; i < orientations.length; i++) {
			orientations[i] = INOCCUPE;
		}
		// On garde néanmoins l'orientation de départ du robot
		orientations[positionToIndice(pos_initiale)] = partieCourante
				.getRobot().getOrientation();

		// Algorithme de Dijkstra
		// Initialisation du tableau
		// On initialise toutes les valeurs à la valeur maximal
		// (Integer.MAX_VALUE)
		// sauf l'indice choisit qui est initialisé à 0
		for (int i = 0; i < aRetourner.length; i++) {
			aRetourner[i] = Float.MAX_VALUE;
		}
		aRetourner[positionToIndice(pos_initiale)] = 0;

		// Pour chaque tour,
		// On récupère l'indice de la valeur minimal du tableau, on ne tient pas
		// compte des précédents indices utilisés
		// On regarde si les position adjacentes sont occupés ou non
		// On calcul le temps mis pour aller jusqu'à la position adjacentes
		// On réactualise le tableau

		indiceCentral = getIndiceMinimal(aRetourner, indiceDejaUtilise);

		positionsAdjacentes = getPositionsAdjacentes(indiceToPosition(indiceCentral));

		for (int j = 0; j < positionsAdjacentes.length; j++) {
			if (partieCourante.isPositionOKAvecTout(positionsAdjacentes[j])) {
				// Sachant que l'indice du tableau est équivalent à
				// l'orientation du robot
				int indice = positionToIndice(positionsAdjacentes[j]);

				if (j == orientations[indiceCentral]) {
					// Position qui suit l'orientation du robot
					// Temps mis = TEMPS_REEL_AVANCER

					if (TEMPS_REEL_AVANCER < aRetourner[indice]) {
						aRetourner[indice] = aRetourner[indiceCentral]
								+ TEMPS_REEL_AVANCER;
						// On regarde l'orientation du robot
						if (TEMPS_REEL_AVANCER == TEMPS_AVANCER) {
							// On garde la même orientation
							orientations[indice] = orientations[indiceCentral];
						} else { // On a fait un demi-tour puis on a reculer
							orientations[indice] = Robot
									.demiTourOrientation(orientations[indiceCentral]);
						}
					}
				} else if (j == Robot
						.demiTourOrientation(orientations[indiceCentral])) {
					// Position opposée à l'orientation du robot
					// Temps mis = TEMPS_REEL_RECULER

					if (TEMPS_REEL_RECULER < aRetourner[indice]) {
						aRetourner[indice] = aRetourner[indiceCentral]
								+ TEMPS_REEL_RECULER;
						// On regarde l'orientation du robot
						if (TEMPS_REEL_RECULER == TEMPS_RECULER) {
							// On garde la même orientation
							orientations[indice] = orientations[indiceCentral];
						} else { // On a fait un demi-tour puis on a reculer
							orientations[indice] = Robot
									.demiTourOrientation(orientations[indiceCentral]);
						}
					}

				} else if (j == Robot
						.pivoterDroite(orientations[indiceCentral])) {
					// On vérifie si l'on met plus de temps pour avancer ou pour
					// reculer
					if (TEMPS_AVANCER < TEMPS_RECULER) {
						if (TEMPS_AVANCER + TEMPS_PIVOTER < aRetourner[indice]) {
							aRetourner[indice] = aRetourner[indiceCentral]
									+ TEMPS_AVANCER + TEMPS_PIVOTER;
							orientations[indice] = Robot
									.pivoterDroite(orientations[indiceCentral]);
						}
					} else {
						if (TEMPS_RECULER + TEMPS_PIVOTER < aRetourner[indice]) {
							aRetourner[indice] = aRetourner[indiceCentral]
									+ TEMPS_RECULER + TEMPS_PIVOTER;
							orientations[indice] = Robot
									.pivoterGauche(orientations[indiceCentral]);
						}
					}

				} else { // j ==
							// Robot.pivoterGauche(orientations[indiceCentral])
					// On vérifie si l'on met plus de temps pour avancer ou pour
					// reculer
					if (TEMPS_AVANCER < TEMPS_RECULER) {
						if (TEMPS_AVANCER + TEMPS_PIVOTER < aRetourner[indice]) {
							aRetourner[indice] = aRetourner[indiceCentral]
									+ TEMPS_AVANCER + TEMPS_PIVOTER;
							orientations[indice] = Robot
									.pivoterDroite(orientations[indiceCentral]);
						}
					} else {
						if (TEMPS_RECULER + TEMPS_PIVOTER < aRetourner[indice]) {
							aRetourner[indice] = aRetourner[indiceCentral]
									+ TEMPS_RECULER + TEMPS_PIVOTER;
							orientations[indice] = Robot
									.pivoterGauche(orientations[indiceCentral]);
						}
					}
				}
				// On calcul le temps pour aller à la position
			}
		}
		return null; // bouchon
	}

	/**
	 * @param aRechercher
	 *            tableau ou on doit effectuer la recherche
	 * @param indiceInutilisable
	 *            tableau qui contient les indices que l'on de doit pas utiliser
	 * @return retourne l'indice de la valeur minimal
	 */
	private int getIndiceMinimal(float[] aRechercher, int[] indiceInutilisable) {
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
	 * Transforme l'indice d'un tableau (de nbLigne * nbColonne) en Position sur
	 * la partie
	 * 
	 * @param indice
	 *            indice d'un tableau de nbLigne * nbColonne
	 * @return la position associé
	 */
	private Position indiceToPosition(int indice) {
		return new Position(indice % partieCourante.getNbLigne(), indice
				/ partieCourante.getNbLigne());
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
		return pos.getX() + pos.getY() * partieCourante.getNbLigne();
	}

	/**
	 * @param pos
	 *            position centrale
	 * @return les positions adjacentes (gauche, haut, droite, bas dans cet
	 *         ordre) à la position centrale
	 */
	private Position[] getPositionsAdjacentes(Position pos) {
		return new Position[] { new Position(pos.getX() - 1, pos.getY()),
				new Position(pos.getX(), pos.getY() - 1),
				new Position(pos.getX() + 1, pos.getY()),
				new Position(pos.getX(), pos.getY() + 1), };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		List<Caisse> listeCaisseRecuperer = partieCourante
				.getCaisseARecuperee();
		List<Caisse>[] test = chercherCaisse();
		for (int i = 0; i < test.length; i++) {
			System.out.println("Couleur rechercher : "
					+ listeCaisseRecuperer.get(i).getCouleur() + "\nTableau : "
					+ Arrays.toString(test[i].toArray()));
			chercherChemin(test);
		}
	}
}
