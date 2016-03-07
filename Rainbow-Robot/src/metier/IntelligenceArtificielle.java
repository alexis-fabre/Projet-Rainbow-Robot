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

	/** Temps que met le Robot pour pivoter sur une case */
	public static final float TEMPS_PIVOTER = (Robot.UNITE_TEMPS / Robot.VITESSE_PIVOTER_VIDE);

	/** Temps que met le Robot pour reculer d'une case */
	public static final float TEMPS_RECULER = (Robot.UNITE_TEMPS / Robot.VITESSE_RECULER_VIDE);

	/** Temps que met le robot pour avancer sur une case */
	public static final float TEMPS_AVANCER = (Robot.UNITE_TEMPS / Robot.VITESSE_AVANCER_VIDE);

	/**
	 * <p>
	 * Temps réel que met le Robot pour avancer sur une case.<br />
	 * On tient compte ici que pour avancer on peut aussi faire 2 rotations plus
	 * reculer. On calcule donc qu'est ce qui est le plus rapide.
	 * </p>
	 */
	public static final float TEMPS_REEL_AVANCER = (2 * TEMPS_PIVOTER + TEMPS_RECULER) < TEMPS_AVANCER ? (2 * TEMPS_PIVOTER + TEMPS_RECULER)
			: TEMPS_AVANCER;

	/**
	 * <p>
	 * Temps réel que met le Robot pour reculer sur une case.<br />
	 * On tient compte ici que pour reculer on peut aussi faire 2 rotations plus
	 * avancer. On calcule donc qu'est ce qui est le plus rapide.
	 * </p>
	 */
	public static final float TEMPS_REEL_RECULER = (2 * TEMPS_PIVOTER + TEMPS_AVANCER) < TEMPS_RECULER ? (2 * TEMPS_PIVOTER + TEMPS_AVANCER)
			: TEMPS_RECULER;

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
	 * @see metier.IntelligenceArtificielle#getDeplacementVersCaisse(List)
	 */
	public static final Integer DEPLACEMENT_AVANCER = new Integer(0);

	/**
	 * Déplacement du robot : Reculer
	 * 
	 * @see metier.IntelligenceArtificielle#getDeplacementVersCaisse(List)
	 */
	public static final Integer DEPLACEMENT_RECULER = new Integer(1);

	/**
	 * Déplacement du robot : Pivoter à gauche
	 * 
	 * @see metier.IntelligenceArtificielle#getDeplacementVersCaisse(List)
	 */
	public static final Integer DEPLACEMENT_PIVOTER_GAUCHE = new Integer(2);

	/**
	 * Déplacement du robot : Pivoter à droite
	 * 
	 * @see metier.IntelligenceArtificielle#getDeplacementVersCaisse(List)
	 */
	public static final Integer DEPLACEMENT_PIVOTER_DROITE = new Integer(3);

	/**
	 * Pause en millisecondes pour que le Thread puisse recevoir des ordres
	 * d'interruption
	 */
	private static final long PAUSE = 50;

	/** Partie que l'IA doit résoudre */
	private Partie partieCourante;

	/**
	 * <p>
	 * Tableau contenant les temps mis pour aller vers n'importe quel position
	 * du plateau de jeu à partir d'une position initiale.<br />
	 * Tableau initialisé dans la fonction suivante :
	 * </p>
	 * 
	 * @see metier.IntelligenceArtificielle#setTempsEtOrientations(Position)
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
	 * @see metier.IntelligenceArtificielle#setTempsEtOrientations(Position)
	 */
	private int[] orientations;

	/**
	 * Constructeur qui initialise une IA avec un niveau et une partie
	 * 
	 * @param partie
	 *            partie que l'IA doit résoudre
	 * 
	 * @throws IllegalArgumentException
	 *             si le niveau ou la partie sont inccorects
	 */
	public IntelligenceArtificielle(Partie partie)
			throws IllegalArgumentException {
		if (partie == null) {
			throw new IllegalArgumentException(
					"Impossibilité de construire l'IA car la partie n'existe pas");
		}
		this.partieCourante = partie;
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
	 * Inverse le contenu de la liste, c'est à dire que le 1er élément devient
	 * le dernier élément (et inversément), etc...
	 * 
	 * @param aInverser
	 *            Liste de déplacement à inverser
	 * @return une ArrayList des déplacements inverser
	 */
	private List<Integer> inverserListe(List<Integer> aInverser) {
		// On récupère la liste sous la forme d'un tableau
		Integer[] tabInvertion = aInverser.toArray(new Integer[0]);
		// Variable de temporisation pour inverser le contenu
		Integer temp;

		// On inverse le contenu du tableau
		for (int i = 0; i < tabInvertion.length / 2; i++) {
			temp = tabInvertion[i];
			tabInvertion[i] = tabInvertion[tabInvertion.length - i - 1];
			tabInvertion[tabInvertion.length - i - 1] = temp;
		}

		// On recréer une nouvelle liste avec le tableau inversé
		return new ArrayList<Integer>(Arrays.asList(tabInvertion));
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
				if (aRecuperer.getCouleur() == listeCaisseRecuperer.get(j)
						.getCouleur()) {
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
	 * La fonction élabore un ensemble de déplacement pour former une
	 * trajectoire capable d'aller chercher toutes les caisses et de les ramener
	 * au vortex. Les déplacements sont différents selon le niveau.
	 * 
	 * @param liste
	 *            tableau de liste contenant les caisses à récupérer sur le
	 *            plateau pour chaque couleur (indice)
	 */
	private void getCheminVersCaisse(List<Caisse>[] liste) {
		// On initialise les temps et les orientations du plateau de jeu
		setTempsEtOrientations(partieCourante.getRobot().getPosRobot());
		// On cherche la liste des déplacements pour aller chercher la caisse
		deplacerIA(getDeplacementVersCaisse(liste[0]));
	}

	/**
	 * <p>
	 * Recherche des plus court chemin à partir d'un position initiale.<br />
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
	 * @param pos_initiale
	 *            position initiale d'ou commence l'algorithme
	 * @see metier.IntelligenceArtificielle#getDeplacementVersCaisse(float[],
	 *      int[])
	 */
	@SuppressWarnings("unused")
	private void setTempsEtOrientations(Position pos_initiale) {
		// Tableau contenant les résultats de l'algorithme de Dijkstra
		temps = new float[partieCourante.getNbLigne()
				* partieCourante.getNbColonne()];
		// Indice déjà utilisé dans l'algorithme
		int[] indiceDejaUtilise = new int[temps.length];
		// Tableau contenant les orientations du robot pour les plus court
		// chemin
		orientations = new int[temps.length];

		// Or indiceDejaUtilise.length = orientations.length = temps.length
		// Algorithme de Dijkstra
		// Initialisation du tableau
		// On initialise toutes les valeurs à la valeur maximal
		// (Integer.MAX_VALUE)
		// sauf l'indice choisit qui est initialisé à 0
		for (int i = 0; i < indiceDejaUtilise.length; i++) {
			temps[i] = Float.MAX_VALUE;

			// On initialise les tableaux avec des valeurs négatives pour
			// optimiser le parcours
			indiceDejaUtilise[i] = INOCCUPE;
			orientations[i] = INOCCUPE;
		}

		temps[positionToIndice(pos_initiale)] = 0;
		// On garde néanmoins l'orientation de départ du robot
		orientations[positionToIndice(pos_initiale)] = partieCourante
				.getRobot().getOrientation();

		// Pour chaque tour,
		// On récupère l'indice de la valeur minimal du tableau sans tenir
		// compte des indice précédement utilisé
		// On regarde si les position adjacentes sont occupés ou non
		// On calcul le temps mis pour aller jusqu'à la position adjacentes
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

				// Sachant que l'indice du tableau (positions adjacentes) est
				// équivalent à l'orientation du robot
				if (partieCourante.isPositionOKAvecTout(positionsAdjacentes[j])) {

					// Indice de la position adjacente
					int indice = positionToIndice(positionsAdjacentes[j]);

					if (j == orientations[indiceCentral]) {
						// Position qui suit l'orientation du robot
						// Temps mis = TEMPS_REEL_AVANCER

						if (TEMPS_REEL_AVANCER + temps[indiceCentral] < temps[indice]) {
							temps[indice] = temps[indiceCentral]
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

						if (TEMPS_REEL_RECULER + temps[indiceCentral] < temps[indice]) {
							temps[indice] = temps[indiceCentral]
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
							if (TEMPS_RECULER + TEMPS_PIVOTER
									+ temps[indiceCentral] < temps[indice]) {
								temps[indice] = temps[indiceCentral]
										+ TEMPS_RECULER + TEMPS_PIVOTER;
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
				}
			}
			indiceDejaUtilise[i] = indiceCentral;
		}

		for (int j = 0; j < temps.length; j++) {
			if (j % partieCourante.getNbLigne() == 0) {
				System.out.println();
			}
			if (temps[j] == Float.MAX_VALUE) {
				System.out.print("-1\t");
			} else {
				System.out.printf("%.3f\t", temps[j]);
			}
		}
		System.out.println();
		for (int j = 0; j < orientations.length; j++) {
			if (j % partieCourante.getNbLigne() == 0) {
				System.out.println();
			}
			System.out.print(orientations[j] + "\t");
		}
	}

	/**
	 * <p>
	 * Détermine la meilleure trajectoire que l'IA puisse prendre pour aller
	 * rechercher une caisse parmis celle proposée. L'IA utilise un tableau
	 * contenant les temps que mettra le Robot pour atteindre une position et
	 * l'orientation qu'il devra avoir.<br />
	 * ATTENTION L'algorithme ne tient pas compte de l'orientation d'arrivée et
	 * il est possible que l'on choississe d'aller chercher une caisse qui ne
	 * soit pas la plus courte en temps.
	 * </p>
	 * 
	 * @param caissePotentielle
	 *            liste de caisse potentielle que le robot doit aller chercher
	 * @return Liste des déplacements pour aller chercher une caisse
	 * 
	 * @see metier.Robot#ACTION_AVANCER
	 * @see metier.Robot#ACTION_CHARGER
	 * @see metier.Robot#ACTION_RECULER
	 * @see metier.Robot#ACTION_PIVOTER
	 */
	private List<Integer> getDeplacementVersCaisse(
			List<Caisse> caissePotentielle) {
		if (orientations == null || temps == null) {
			System.out
					.println("IA : Trajectoire incalculable : Temps ou orientation indéfinie");
			return null;
		}
		// else

		// Position la plus proche du robot en temps
		Position posMin = null;
		// Temps mis pour atteindre posMin
		float tempsMis = Float.MAX_VALUE;
		// Positions adjacentes à la caisse
		Position[] positionsAdjacentesCaisses;
		for (int i = 0; i < caissePotentielle.size(); i++) {
			positionsAdjacentesCaisses = Position
					.getPositionsAdjacentes(caissePotentielle.get(i)
							.getPosCaisse());
			for (int j = 0; j < positionsAdjacentesCaisses.length; j++) {
				if (partieCourante
						.isPositionOKAvecTout(positionsAdjacentesCaisses[j])
						&& temps[positionToIndice(positionsAdjacentesCaisses[j])] < tempsMis) {
					tempsMis = temps[positionToIndice(positionsAdjacentesCaisses[j])];
					posMin = positionsAdjacentesCaisses[j];
				}
			}
		}
		if (posMin == null) {
			System.out.println("IA : Impossible d'accéder à une des caisses");
			return null;
		}
		// else

		// Liste des déplacements (variable de retour de la fonction)
		List<Integer> deplacement = new ArrayList<Integer>();

		// On recherche la trajectoire pour atteindre posMin en utilisant les
		// deux tableaux temps et orientations

		// Position courante à un instant t
		int indiceCourant = positionToIndice(posMin);

		while (temps[indiceCourant] != 0.0f) {
			// On cherche les positions adjacentes à posMin
			Position[] posAdjacentes = Position
					.getPositionsAdjacentes(indiceToPosition(indiceCourant));

			for (int j = 0; j < posAdjacentes.length; j++) {

				// Indice dans le tableau de la position adjacente
				int indiceAdj = positionToIndice(posAdjacentes[j]);

				if (partieCourante.isPositionOKAvecTout(posAdjacentes[j])
						&& temps[indiceCourant] > temps[indiceAdj]) {

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

								// Utilisation de l'autoboxing
								// <=> int to Integer
								deplacement.add(DEPLACEMENT_RECULER);

								indiceCourant = indiceAdj;
								// On cherche un chemin, on ne cherche pas tous
								// les chemins
								break;
							}

						} else if (orientationCourant == Robot
								.demiTourOrientation(orientationAdj)) {
							// Action = 2 rotations + reculer
							if (temps[indiceCourant] == temps[indiceAdj] + 2
									* TEMPS_PIVOTER + TEMPS_RECULER) {

								deplacement.add(DEPLACEMENT_RECULER);
								deplacement.add(DEPLACEMENT_PIVOTER_GAUCHE);
								deplacement.add(DEPLACEMENT_PIVOTER_GAUCHE);

								indiceCourant = indiceAdj;
								break;
							}

						} else if (orientationCourant == Robot
								.pivoterDroite(orientationAdj)) {
							// Action = Pivoter droite + Reculer
							if (temps[indiceCourant] == temps[indiceAdj]
									+ TEMPS_PIVOTER + TEMPS_RECULER) {

								deplacement.add(DEPLACEMENT_RECULER);
								deplacement.add(DEPLACEMENT_PIVOTER_DROITE);

								indiceCourant = indiceAdj;
								break;
							}
						} else {
							// orientationCourant ==
							// Robot.pivoterGauche(orientationAdj)

							// Action = Pivoter gauche + Reculer
							if (temps[indiceCourant] == temps[indiceAdj]
									+ TEMPS_PIVOTER + TEMPS_RECULER) {

								deplacement.add(DEPLACEMENT_RECULER);
								deplacement.add(DEPLACEMENT_PIVOTER_GAUCHE);

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

								deplacement.add(DEPLACEMENT_AVANCER);

								indiceCourant = indiceAdj;
								break;
							}
						} else if (orientationCourant == Robot
								.demiTourOrientation(orientationAdj)) {
							// Action = 2 rotations + Avancer
							if (temps[indiceCourant] == temps[indiceAdj] + 2
									* TEMPS_PIVOTER + TEMPS_AVANCER) {

								deplacement.add(DEPLACEMENT_AVANCER);
								deplacement.add(DEPLACEMENT_PIVOTER_GAUCHE);
								deplacement.add(DEPLACEMENT_PIVOTER_GAUCHE);

								indiceCourant = indiceAdj;
								break;
							}

						} else if (orientationCourant == Robot
								.pivoterDroite(orientationAdj)) {
							// Action = Pivoter droite + Avancer
							if (temps[indiceCourant] == temps[indiceAdj]
									+ TEMPS_PIVOTER + TEMPS_AVANCER) {

								deplacement.add(DEPLACEMENT_AVANCER);
								deplacement.add(DEPLACEMENT_PIVOTER_DROITE);

								indiceCourant = indiceAdj;
								break;
							}

						} else {
							// orientationCourant ==
							// Robot.pivoterGauche(orientationAdj)
							// Action = Pivoter gauche
							if (temps[indiceCourant] == temps[indiceAdj]
									+ TEMPS_PIVOTER + TEMPS_AVANCER) {

								deplacement.add(DEPLACEMENT_AVANCER);
								deplacement.add(DEPLACEMENT_PIVOTER_GAUCHE);

								indiceCourant = indiceAdj;
								break;
							}
						}
					}
				}
			}
		}
		System.out.println("\nCalcul déplacement : ");
		System.out.println("Sans invertion : ");
		deplacement = inverserListe(deplacement);
		for (Integer integer : deplacement) {
			if (integer == DEPLACEMENT_AVANCER) {
				System.out.print("Avancer --> ");
			} else if (integer == DEPLACEMENT_PIVOTER_GAUCHE) {
				System.out.print("Pivoter gauche --> ");
			} else if (integer == DEPLACEMENT_PIVOTER_DROITE) {
				System.out.print("Pivoter droite --> ");
			} else if (integer == DEPLACEMENT_RECULER) {
				System.out.print("Reculer --> ");
			}
		}
		// On inverse le sens de la liste
		return deplacement;
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
		// Référence du robot de la partie
		Robot robot = partieCourante.getRobot();
		while (!deplacement.isEmpty()) {
			if (!robot.estOccupe()) {
				if (deplacement.get(0) == DEPLACEMENT_AVANCER) {
					robot.avancer();
					deplacement.remove(0);
				} else if (deplacement.get(0) == DEPLACEMENT_PIVOTER_GAUCHE) {
					robot.pivoter(Robot.PIVOTER_GAUCHE);
					deplacement.remove(0);
				} else if (deplacement.get(0) == DEPLACEMENT_PIVOTER_DROITE) {
					robot.pivoter(Robot.PIVOTER_DROITE);
					deplacement.remove(0);
				} else if (deplacement.get(0) == DEPLACEMENT_RECULER) {
					robot.reculer();
					deplacement.remove(0);
				}
			}
			// else
			// On fait une mini-pause car si on demande au Thread (de l'IA) de
			// s'arrêter, il recevra l'appel ici
			try {
				Thread.sleep(PAUSE);
			} catch (InterruptedException e) {
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		getCheminVersCaisse(getCaisseParCouleur());
	}
}
