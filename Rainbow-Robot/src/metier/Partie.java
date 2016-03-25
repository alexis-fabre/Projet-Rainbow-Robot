/*
 * Partie.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package metier;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

import vue.UtilitaireFenetre;

/**
 * Classe gérant une partie du Jeu Rainbow Robot. Elle contient un Robot qui va
 * devoir déposer des caisses dans un vortex. Le robot ne pourra déposer que les
 * caisses dont la couleur est la même que celles demandées.
 * 
 * @author Rainbow Robot
 */
public class Partie extends Observable implements Dessinable, Serializable {

	/** Générer automatiquement par Eclipse */
	private static final long serialVersionUID = -6712326189565570979L;

	/** Nombre de colonne maximal que peut contenir une partie */
	public static final int NB_COLONNE_MAX = 19;

	/** Nombre de ligne maximal que peut contenir une partie */
	public static final int NB_LIGNE_MAX = 12;

	/** Nombre de colonne maximal que peut contenir une partie */
	public static final int NB_COLONNE_MIN = 2;

	/** Nombre de ligne maximal que peut contenir une partie */
	public static final int NB_LIGNE_MIN = 2;

	/** Nombre colonne de la carte */
	private int nbColonne;

	/** Nombre de lignes de la carte */
	private int nbLigne;

	/** Robot sur la carte */
	private Robot robot;

	/** Tableau des caisses sur le plateau de jeu */
	private Caisse[] caissePlateau;

	/** Niveau de la partie */
	private int niveau;

	/** Vortex de la carte */
	private Vortex vortex;

	/** caisse a recuperée pour finir une partie */
	private ArrayList<Caisse> caisseARecuperer;

	/**
	 * Position où le robot ne pourra pas se déplacer. On ne représente que le
	 * carré en bas à gauche. On obtient les autres carré en récupérant la
	 * valeur absolue des X, des Y et des deux en mêmes temps.
	 */
	public Position[] positionsInaccessibles;

	/**
	 * Créer une carte avec un nombre de nigne et de colonne précis. Initialise
	 * le robot à la position (1,0) et dirigé vers la gauche. Le vortex est
	 * initialisé à la position (0,0). Il y a 3 caisses a récupérée et 3 caisses
	 * sur le plateau de jeu avec les mêmes couleurs.
	 * 
	 * @param nbLigne
	 *            nombre de ligne que doit faire la carte
	 * @param nbColonne
	 *            nombre de colonne que doit faire la carte
	 * @throws IllegalArgumentException
	 *             si le nombre de ligne est différent de 9 et le nombre de
	 *             colonne est différent de 11.
	 */
	@Deprecated
	public Partie(int nbLigne, int nbColonne) throws IllegalArgumentException {
		// Si nbLigne != 9 ou nbColonne != 11, on aura des problèmes pour créer
		// les zones inaccessibles (POSITIONS_INACCESSIBLES)
		if (nbLigne != 9 || nbColonne != 11) {
			throw new IllegalArgumentException(
					"La partie doit être crée avec 9 lignes et 11 colonnes "
							+ "sinon, elle ne sera pas correctement gérée");
		}
		this.nbLigne = nbLigne;
		this.nbColonne = nbColonne;

		// On ne calcule les positions inaccessibles
		positionsInaccessibles = new Position[4];
		positionsInaccessibles[0] = new Position(0, 0);
		positionsInaccessibles[1] = new Position(0, 1);
		positionsInaccessibles[2] = new Position(1, 0);
		positionsInaccessibles[3] = new Position(1, 1);

		caisseARecuperer = new ArrayList<Caisse>();
		caisseARecuperer.add(new Caisse(Caisse.ROUGE));
		caisseARecuperer.add(new Caisse(Caisse.BLEU));
		caisseARecuperer.add(new Caisse(Caisse.JAUNE));
		// Caisse.CaisseARecuperer(caisseARecup, 1, Color.RED);

		caissePlateau = new Caisse[3];
		caissePlateau[0] = new Caisse(Caisse.ROUGE, new Position(-4, 2));
		caissePlateau[1] = new Caisse(Caisse.BLEU, new Position(3, 2));
		caissePlateau[2] = new Caisse(Caisse.JAUNE, new Position(2, 3));

		// Le robot
		robot = new Robot(Robot.ORIENTATION_GAUCHE, new Position(1, 0));

		// Le vortex
		vortex = new Vortex(new Position(0, 0));
	}

	/**
	 * Permet d'intialiser une partie avec tous ces composants. Pour l'instant
	 * aucune exception n'est levé.
	 * 
	 * @param ligne
	 *            nombre de case vide sur les lignes (sens horizontal)
	 * @param colonne
	 *            nombre de case vide sur les colonnes (sens vertical)
	 * @param posInaccessible
	 *            position inaccessible par le robot
	 * @param robot
	 *            Robot à contrôlé pour la partie
	 * @param vortex
	 *            vortex qui va avalé les caisses à récupérer
	 * @param caisseARecuperer
	 *            caisse à récupérée. On a besoin uniquement de la couleur de la
	 *            caisse
	 * @param caissePlateau
	 *            caisse placé sur le plateau. On a besoin de connaître la
	 *            position et la couleur de chaque caisse
	 * @throws IllegalArgumentException
	 *             <p>
	 *             si le nombre de ligne est supérieur au nombre de ligne
	 *             maximale,<br />
	 *             si le nombre de colonne est supérieur au nombre de colonne
	 *             maximale,<br />
	 *             si les caissePlateau, robot, vortex ne contienne pas de
	 *             position ou si leurs positions sont invalides,<br />
	 *             si les caisseARecupere et les caissePlateau n'ont pas tous
	 *             une couleur définie,<br />
	 *             si robot n'a pas une orientation de départ.
	 *             </p>
	 */
	public Partie(int ligne, int colonne, Position[] posInaccessible,
			Robot robot, Vortex vortex, ArrayList<Caisse> caisseARecuperer,
			Caisse[] caissePlateau, boolean isIA)
			throws IllegalArgumentException {
		// On teste les limites du nombre de ligne et de colonne
		if (ligne > NB_LIGNE_MAX || ligne < NB_LIGNE_MIN) {
			throw new IllegalArgumentException(
					"Le nombre de lignes est supérieur au nombre de lignes maximales : "
							+ NB_LIGNE_MAX + " ou inférieur ou égal à "
							+ NB_LIGNE_MIN);
		}
		if (colonne > NB_COLONNE_MAX || colonne < NB_COLONNE_MIN) {
			throw new IllegalArgumentException(
					"Le nombre de colonnes est supérieur au nombre de colonnes maximales : "
							+ NB_COLONNE_MAX + " ou inférieur ou égal à "
							+ NB_COLONNE_MIN);
		}
		this.nbLigne = ligne;
		this.nbColonne = colonne;

		// On vérifie si les positions inaccessibles existent, c'est à dire si
		// elles sont situées sur le plateau et s'il n'y en a pas deux sur la
		// même position
		if (posInaccessible != null) {
			for (int i = 0; i < posInaccessible.length; i++) {
				if (posInaccessible[i] == null) {
					throw new IllegalArgumentException("Position à l'indice "
							+ i + " est introuvable");
				}
				if (isPositionOK(posInaccessible[i])) {
					for (int j = i + 1; j < posInaccessible.length; j++) {
						if (posInaccessible[i].equals(posInaccessible[j])) {
							throw new IllegalArgumentException(
									"La position inaccessible en "
											+ posInaccessible[i]
											+ " est présente deux fois");
						}
					}
				} else {
					throw new IllegalArgumentException(
							"La position inaccessible en " + posInaccessible[i]
									+ " est en dehors du plateau de jeu");
				}
			}
		}
		this.positionsInaccessibles = posInaccessible;

		// On vérifie si le vortex est en dehors du plateau ou si il est situé
		// sur une position inaccessible
		if (vortex == null) {
			throw new IllegalArgumentException(
					"Référence du vortex introuvable");
		}
		if (!isPositionOK(vortex.getPosVortex())) {
			throw new IllegalArgumentException(
					"Position du vortex en dehors du plateau de jeu");
		}
		if (!isPositionOKAvecPositionInaccessible(vortex.getPosVortex())) {
			throw new IllegalArgumentException(
					"Position du vortex sur une position inaccessible");
		}
		this.vortex = vortex;

		// On vérifie si les caisses sont situées sur le plateau de jeu et
		// qu'elles ne se trouvent pas sur des positions inaccessibles.
		if (caissePlateau == null) {
			throw new IllegalArgumentException(
					"Référence des caisses sur le plateau introuvables");
		}
		for (int i = 0; i < caissePlateau.length; i++) {
			if (caissePlateau[i] != null) {
				if (isPositionOK(caissePlateau[i].getPosCaisse())) {
					if (isPositionOKAvecPositionInaccessible(caissePlateau[i]
							.getPosCaisse())) {
						if (!caissePlateau[i].getPosCaisse().equals(
								vortex.getPosVortex())) {
							for (int j = i + 1; j < caissePlateau.length; j++) {
								if (caissePlateau[j] != null
										&& caissePlateau[i].getPosCaisse()
												.equals(caissePlateau[j]
														.getPosCaisse())) {
									throw new IllegalArgumentException(
											"La caisse à la position "
													+ caissePlateau[i]
															.getPosCaisse()
													+ " est présente deux fois");
								}
							}
						} else {
							throw new IllegalArgumentException(
									"Une caisse est sur la position du vortex : "
											+ vortex.getPosVortex());
						}
					} else {
						throw new IllegalArgumentException(
								"La caisse sur le plateau de jeu à la position "
										+ caissePlateau[i].getPosCaisse()
										+ " est sur une des positions inaccessibles");
					}
				} else {
					throw new IllegalArgumentException(
							"La caisse sur le plateau de jeu à la position "
									+ caissePlateau[i].getPosCaisse()
									+ " est en dehors du plateau de jeu");
				}
			}
		}
		this.caissePlateau = caissePlateau;

		// On vérifie si le Robot est en dehors du plateau de jeu ou s'il est
		// situé sur une position inaccessible
		if (robot == null) {
			throw new IllegalArgumentException("Référence du robot introuvable");
		}
		if (!isPositionOK(robot.getPosRobot())) {
			throw new IllegalArgumentException(
					"Position du robot en dehors du plateau de jeu");
		}
		if (!isPositionOKAvecPositionInaccessible(robot.getPosRobot())) {
			throw new IllegalArgumentException(
					"Position du robot sur une position inaccessible");
		}
		if (!isPositionOKAvecCaisse(robot.getPosRobot())) {
			throw new IllegalArgumentException(
					"Position du robot sur une caisse");
		}
		this.robot = robot;

		// On vérifie si la couleur des caisses à récupérer existent sur le
		// plateau de jeu ou si elles s'obtiennent par la fusion
		if (caisseARecuperer == null) {
			throw new IllegalArgumentException(
					"Référence des caisses à récupérer introuvable");
		}
		if (caisseARecuperer.size() > caissePlateau.length) {
			throw new IllegalArgumentException(
					"Il y a plus de caisse à récupérer que de caisse sur le plateau");
		}
		// Permet de connaître les indices des caisses du plateau de jeu déjà
		// utilisé dans les caisses à récupérer
		int[] indiceDejaUtilise = new int[caisseARecuperer.size()];
		// On initialise le tableau avec des valeurs erronées
		for (int i = 0; i < indiceDejaUtilise.length; i++) {
			indiceDejaUtilise[i] = -1;
		}
		// Compteur du tableau indiceDejaUtilise pour gérer l'incrémentation
		int compteur = 0;
		BouclePrincipale: for (int i = 0; i < caisseARecuperer.size(); i++) {
			if (caisseARecuperer.get(i) == null
					|| caisseARecuperer.get(i).getCouleur() == 0) { // == null
				throw new IllegalArgumentException(
						"La caisse à récupérer à l'indice " + i
								+ " est introuvable");
			}
			// On vérifie si la couleur de la caisse à récupérer existe dans les
			// couleurs des caisses sur le plateau ou si on peut l'obtenir par
			// la fusion
			// On utilise un label pour simplifier l'algorithme
			BoucleVerification: for (int j = 0; j < caissePlateau.length; j++) {
				for (int indice : indiceDejaUtilise) {
					if (j == indice) {
						continue BoucleVerification;
					}
				}
				if (caisseARecuperer.get(i).getCouleur() == caissePlateau[j]
						.getCouleur()) {
					indiceDejaUtilise[compteur] = j;
					compteur++;
					continue BouclePrincipale;
				}
				// TODO vérifier avec la fusion
			}
			// La couleur de la caisse à récupérer ne s'obtient ni par la
			// fusion, ni par une couleur du plateau de jeu.
			throw new IllegalArgumentException(
					"La caisse à la couleur "
							+ caisseARecuperer.get(i).getCouleur()
							+ " ne peut pas s'obtenir ni par la fusion, ni directement par une caisse sur le plateau de jeu");
		}
		this.caisseARecuperer = caisseARecuperer;
		if (isIA && !verificationPartieResolvable()) {
			throw new IllegalArgumentException(
					"La partie n'est pas résolvable. Il est impossible d'amener toutes les caisses à récupérer dans le vortex");
		}
		// Une fois que tous c'est bien passé, on set la partie au classe qui en
		// ont besoin
		robot.setPartie(this);
		vortex.setPartie(this);
	}

	/**
	 * Vérifie si la partie est réalisable, c'est à dire si le robot peut amener
	 * toutes les caisses dans le vortex.
	 * 
	 * @return true si la partie est réalisable, false sinon
	 */
	private boolean verificationPartieResolvable() {
		IntelligenceArtificielle IA = new IntelligenceArtificielle(this, true);
		IA.start();
		long temps = 0, depart, fin;
		while (IA.isAlive()) {
			depart = System.currentTimeMillis();
			fin = System.currentTimeMillis();
			temps = temps + (fin - depart);

			if (temps > 10000) { // 10s max
				break;
			}
		}
		boolean ok = IA.isPartieFaisable();
		IA.interrupt();
		return ok;
	}

	/**
	 * Envoie un signal à la classe ClicSouris pour dire que la partie est finie
	 */
	public void partieFinie() {
		setChanged();
		notifyObservers();
	}

	/**
	 * Vérifie si la position envoyé en paramètre est correcte, c'est à dire si
	 * elle existe sur le plateau de jeu.
	 * 
	 * @param aVerifier
	 *            position à vérifier
	 * @return true si la position est correcte, false sinon
	 */
	public boolean isPositionOK(Position aVerifier) {
		// On vérifie si la position ne dépasse pas des dimensions de la partie.
		if (aVerifier.getX() < 0 || aVerifier.getX() >= nbColonne) {
			return false;
		}
		if (aVerifier.getY() < 0 || aVerifier.getY() >= nbLigne) {
			return false;
		}
		// else
		return true;
	}

	/**
	 * Vérifie si les coordonnées envoyées en paramètre sont correctes, c'est à
	 * dire si elles existent sur le plateau de jeu.
	 * 
	 * @param x
	 *            abscisse de la position à vérifier
	 * @param y
	 *            ordonnée de la position à vérifier
	 * @return true si la position est correcte, false sinon
	 */
	public boolean isPositionOK(int x, int y) {
		return isPositionOK(new Position(x, y));
	}

	/**
	 * Vérifie si la position envoyé en paramètre est correcte, c'est à dire si
	 * elle n'est pas située sur une position inaccessible.
	 * 
	 * @param aVerifier
	 *            position à vérifier
	 * @return true si la position est correcte, false sinon
	 */
	public boolean isPositionOKAvecPositionInaccessible(Position aVerifier) {
		if (positionsInaccessibles == null) {
			return true;
		}

		// On vérifie si la position fait partie des position que l'on ne doit
		// pas atteindre (POSITIONS_INACCESSIBLES)
		for (Position inaccessibles : positionsInaccessibles) {
			if (aVerifier.equals(inaccessibles)) {
				return false;
			}
		}

		// Sinon la position est correcte
		return true;
	}

	/**
	 * Vérifie si les coordonnées envoyées en paramètre sont correctes, c'est à
	 * dire si elle n'est pas située sur une position inaccessible.
	 * 
	 * @param x
	 *            abscisse de la position à vérifier
	 * @param y
	 *            ordonnée de la position à vérifier
	 * @return true si la position est correcte, false sinon
	 */
	public boolean isPositionOKAvecPositionInaccessible(int x, int y) {
		return isPositionOKAvecPositionInaccessible(new Position(x, y));
	}

	/**
	 * Vérifie si la position envoyé en paramètre est correcte, c'est à dire si
	 * elle n'est pas occupé par une caisse.
	 * 
	 * @param aVerifier
	 *            position à vérifier
	 * @return true si la position est correcte, false sinon
	 */
	public boolean isPositionOKAvecCaisse(Position aVerifier) {
		// On regarde si la position a vérifié est sur une caisse déjà existante
		for (int i = 0; i < caissePlateau.length; i++) {
			if (caissePlateau[i] != null
					&& caissePlateau[i].getPosCaisse().equals(aVerifier)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Vérifie si la position envoyé en paramètre est correcte, c'est à dire si
	 * elle n'est pas occupé par une caisse.
	 * 
	 * @param x
	 *            abscisse de la position à vérifier
	 * @param y
	 *            ordonnée de la position à vérifier
	 * @return true si la position est correcte, false sinon
	 */
	public boolean isPositionOKAvecCaisse(int x, int y) {
		return isPositionOKAvecCaisse(new Position(x, y));
	}

	/**
	 * Vérifie si la position envoyé en paramètre est correcte, c'est à dire si
	 * elle existe sur le plateau de jeu, si elle n'est pas située sur une
	 * position inaccessible, si elle n'est pas occupé par une caisse et si elle
	 * n'est pas occupé par le robot.
	 * 
	 * @param aVerifier
	 *            position à vérifier
	 * @return true si la position est correcte, false sinon
	 */
	public boolean isPositionOKAvecTout(Position aVerifier) {
		return isPositionOK(aVerifier)
				&& isPositionOKAvecPositionInaccessible(aVerifier)
				&& isPositionOKAvecCaisse(aVerifier);
	}

	/**
	 * Vérifie si la position envoyé en paramètre est correcte, c'est à dire si
	 * elle existe sur le plateau de jeu, si elle n'est pas située sur une
	 * position inaccessible, si elle n'est pas occupé par une caisse et si elle
	 * n'est pas occupé par le robot.
	 * 
	 * @param x
	 *            abscisse de la position à vérifier
	 * @param y
	 *            ordonnée de la position à vérifier
	 * @return true si la position est correcte, false sinon
	 */
	public boolean isPositionOKAvecTout(int x, int y) {
		return isPositionOKAvecTout(new Position(x, y));
	}

	/**
	 * Cherche la caisse sur le plateau de jeu à la position mentionnée. Si la
	 * caisse ou la position n'existe pas, on retourne null
	 * 
	 * @param position
	 *            position probable de la caisse
	 * @return la caisse à la position spécifique ou null si la position ou la
	 *         caisse n'existe pas
	 */
	public Caisse getCaisseJeu(Position position) {
		// On recherche la caisse
		for (Caisse caisse : caissePlateau) {
			if (caisse != null && caisse.getPosCaisse().equals(position)) {
				return caisse;
			}
		}
		// Ce cas ne devrait jamais arrivé
		return null;
	}

	/**
	 * @return le nombre de colonne de la partie
	 */
	public int getNbColonne() {
		return nbColonne;
	}

	/**
	 * @return le nombre de ligne de la partie
	 */
	public int getNbLigne() {
		return nbLigne;
	}

	/**
	 * @return le robot de la partie courante
	 */
	public Robot getRobot() {
		return robot;
	}

	/**
	 * @return le vortex de la partie courante
	 */
	public Vortex getVortex() {
		return vortex;
	}

	/**
	 * @return le caissePlateau
	 */
	public Caisse[] getCaissePlateau() {
		return caissePlateau;
	}

	/**
	 * @return les caisses a récupérée
	 */
	public ArrayList<Caisse> getCaisseARecuperee() {
		return caisseARecuperer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see metier.Dessinable#dessiner(java.awt.Graphics2D)
	 */
	@Override
	public void dessiner(Graphics2D g) {
		Graphics2D fond = (Graphics2D) g.create();
		for (int y = 0; y < nbLigne; y++) {// Axe des ordonnées
			for (int x = 0; x < nbColonne; x++) { // Axe des abscisses
				Position posADessiner = new Position(x, y);
				if (isPositionOKAvecPositionInaccessible(posADessiner)) {
					// ---------------------------------------------------------
					// On dessine les cases vides et le vortex
					// ---------------------------------------------------------
					if (posADessiner.equals(vortex.getPosVortex())) { // Vortex
						fond.setColor(UtilitaireFenetre.COULEUR_VORTEX);
					} else { // Case vide
						fond.setColor(UtilitaireFenetre.COULEUR_FOND);
					}

					// On dessine le fond de la case
					fond.fillRect(x * UtilitaireFenetre.DIM_CASE_VIDE.width, y
							* UtilitaireFenetre.DIM_CASE_VIDE.height,
							UtilitaireFenetre.DIM_CASE_VIDE.width,
							UtilitaireFenetre.DIM_CASE_VIDE.height);

					// On dessine la bordure de la case
					fond.setStroke(new BasicStroke(
							UtilitaireFenetre.LARGEUR_BORDURE));
					fond.setColor(UtilitaireFenetre.COULEUR_BORDURE);
					fond.drawRect(x * UtilitaireFenetre.DIM_CASE_VIDE.width, y
							* UtilitaireFenetre.DIM_CASE_VIDE.height,
							UtilitaireFenetre.DIM_CASE_VIDE.width,
							UtilitaireFenetre.DIM_CASE_VIDE.height);
				}
			}
		}
		fond.dispose();

		// ---------------------------------------------------------
		// On dessine les caisses
		// ---------------------------------------------------------
		for (Caisse caisseADessiner : caissePlateau) {
			// On ne redessine pas la caisse du robot
			if (caisseADessiner != null && caisseADessiner != robot.getCaisse()) {
				// On calcule la nouvel abscisse et ordonnée pour
				// positionner l'image de la caisse
				// On fait cela pour centrer l'image dans la case
				// vide
				int abscisseCaisse = (caisseADessiner.getPosCaisse().getX())
						* UtilitaireFenetre.DIM_CASE_VIDE.width
						+ ((UtilitaireFenetre.DIM_CASE_VIDE.width / 2) - (UtilitaireFenetre.DIM_CAISSE_JEU.width / 2));
				int ordonneCaisse = (caisseADessiner.getPosCaisse().getY())
						* UtilitaireFenetre.DIM_CASE_VIDE.height
						+ ((UtilitaireFenetre.DIM_CASE_VIDE.height / 2) - (UtilitaireFenetre.DIM_CAISSE_JEU.height / 2));
				Graphics2D contexteCaisse = (Graphics2D) g.create(
						abscisseCaisse, ordonneCaisse,
						UtilitaireFenetre.DIM_CAISSE_JEU.width,
						UtilitaireFenetre.DIM_CAISSE_JEU.height);
				caisseADessiner.dessiner(contexteCaisse);
				contexteCaisse.dispose();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Partie [nbColonne=" + nbColonne + ", nbLigne=" + nbLigne
				+ ", robot=" + robot + ", caisses="
				+ Arrays.toString(caissePlateau) + ", niveau=" + niveau
				+ ", vortex=" + vortex + ", caisseARecup=" + caisseARecuperer
				+ ", positionsInaccessibles="
				+ Arrays.toString(positionsInaccessibles) + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// On clone les positions inaccessibles
		Position[] posInaccessibles;
		if (positionsInaccessibles != null) {
			posInaccessibles = new Position[positionsInaccessibles.length];
			for (int i = 0; i < positionsInaccessibles.length; i++) {
				posInaccessibles[i] = (Position) positionsInaccessibles[i]
						.clone();
			}
		} else {
			posInaccessibles = null;
		}

		// On clone les caisse à récupérée
		ArrayList<Caisse> caisseARecup = new ArrayList<Caisse>(
				caisseARecuperer.size());
		for (Caisse caisse : caisseARecuperer) {
			caisseARecup.add((Caisse) caisse.clone());
		}

		// On clone les caisses disponibles sur le plateau
		Caisse[] caissePlat = new Caisse[caissePlateau.length];
		for (int i = 0; i < caissePlateau.length; i++) {
			if (caissePlateau[i] != null) {
				caissePlat[i] = (Caisse) caissePlateau[i].clone();
			} else {
				caissePlat[i] = null;
			}
		}

		return new Partie(nbLigne, nbColonne, posInaccessibles,
				(Robot) robot.clone(), (Vortex) vortex.clone(), caisseARecup,
				caissePlat, false);
	}
}
