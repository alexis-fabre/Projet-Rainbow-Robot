/*
 * Partie.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package metier;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

import vue.UtilitaireFenetre;

/**
 * Classe gérant une partie du Jeu rainbow-robot. Elle gère un robot qui va
 * récuperer les différentes caisses.
 * 
 * @author Rainbow Robot
 */
public class Partie extends Observable implements Dessinable, Serializable {

	/**
	 * Générer automatiquement par Eclipse
	 */
	private static final long serialVersionUID = -6712326189565570979L;

	/** Nombre colonne de la carte */
	private int nbColonne;

	/** Nombre de lignes de la carte */
	private int nbLigne;

	/** Abscisse minimale que peut prendre une position. finX = -debutX */
	private int debutX;

	/** Ordonnée minimale que peut prendre une position. finY = -debutY */
	private int debutY;

	/** Robot sur la carte */
	private Robot robot;

	/** Tableau de caisse */
	private Caisse[] caissePlateau;

	/** Niveau de la partie */
	private int niveau;

	/** Vortex de la carte */
	private Vortex vortex;

	/** caisse à recuperer pour finir une partie */
	private ArrayList<Caisse> caisseARecuperee = new ArrayList<Caisse>();

	/**
	 * Position ou le robot ne pourra pas se déplacer. On ne représente que le
	 * carré en bas à gauche. On obtient les autres carré en récupérant la
	 * valeur absolue des X, des Y et des deux en mêmes temps.
	 */
	public Position[] positionsInaccessibles;

	/**
	 * Créer une carte avec un nombre de nigne et de colonne précis. Initialise
	 * le robot à la position (1,0) et dirigé vers la gauche. Le vortex est
	 * initialisé à la position (0,0).
	 * 
	 * @param nbLigne
	 *            nombre de ligne que doit faire la carte
	 * @param nbColonne
	 *            nombre de colonne que doit faire la carte
	 * @throws IllegalArgumentException
	 *             si le nombre de ligne est différent de 9 et le nombre de
	 *             colonne est différent de 11.
	 */
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
		// Si nbColonne = 11
		// debutX = -(11 - 1 / 2) = -5
		// -debutX = 5
		this.debutX = -((nbColonne - 1) / 2);
		this.debutY = -((nbLigne - 1) / 2);

		// On ne calcule les positions inaccessibles
		positionsInaccessibles = new Position[4];
		positionsInaccessibles[0] = new Position(debutX, debutY);
		positionsInaccessibles[1] = new Position(debutX, debutY + 1);
		positionsInaccessibles[2] = new Position(debutX + 1, debutY);
		positionsInaccessibles[3] = new Position(debutX + 1, debutY + 1);

		// TODO à généraliser
		caisseARecuperee.add(new Caisse(Color.RED));
		caisseARecuperee.add(new Caisse(Color.BLUE));
		caisseARecuperee.add(new Caisse(Color.YELLOW));
		// Caisse.CaisseARecuperer(caisseARecup, 1, Color.RED);

		// TODO à généraliser
		caissePlateau = new Caisse[3];
		caissePlateau[0] = new Caisse(Color.RED, new Position(-4, 2));
		caissePlateau[1] = new Caisse(Color.BLUE, new Position(3, 2));
		caissePlateau[2] = new Caisse(Color.YELLOW, new Position(2, 3));

		// Le robot
		robot = new Robot(Robot.ORIENTATION_GAUCHE, new Position(1, 0));

		// Le vortex
		vortex = new Vortex(new Position(0, 0));
	}

	/**
	 * Permet d'intialiser une partie avec tous ces composants
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
	 * @param caisseARecupere
	 *            caisse à récupérée. On a besoin uniquement de la couleur de la
	 *            caisse
	 * @param caissePlateau
	 *            caisse placé sur le plateau. On a besoin de connaître la
	 *            position et la couleur de chaque caisse
	 * @throws IllegalArgumentException
	 *             <p>
	 *             si les caissePlateau, robot, vortex ne contienne pas de
	 *             position ou si leurs positions sont invalides,<br />
	 *             si les caisseARecupere et les caissePlateau n'ont pas tous
	 *             une couleur définie,<br />
	 *             si robot n'a pas une orientation de départ
	 *             </p>
	 */
	public Partie(int ligne, int colonne, Position[] posInaccessible,
			Robot robot, Vortex vortex, ArrayList<Caisse> caisseARecupere,
			Caisse[] caissePlateau) throws IllegalArgumentException {
		// TODO continuer le constructeur
		this.nbLigne = ligne;
		this.nbColonne = colonne;
		// Si nbColonne = 11
		// debutX = -(11 - 1 / 2) = -5
		// -debutX = 5
		this.debutX = -((nbColonne - 1) / 2);
		this.debutY = -((nbLigne - 1) / 2);

		// On initialise les différents composants
		this.vortex = vortex;
		this.robot = robot;
		this.caissePlateau = caissePlateau;
		this.caisseARecuperee = caisseARecupere;
		this.positionsInaccessibles = posInaccessible;

	}

	/**
	 * Vérifie si le vortex peut absorber la caisse (si la caisse à la même
	 * couleur que celle demandée). Si c'est le cas, on fait disparaître la
	 * caisse et on actualise la nouvelle caisse a récupéré.
	 * 
	 * @return true si le vortex a apsiré la caisse ou false sinon
	 */
	public boolean vortexAspire() {
		// On parcours les caisses sur le plateau de jeu et on vérifie si l'une
		// d'elle est positionné sur le vortex et si elle est de la même couleur
		// que celle demandée dans la liste
		for (int i = 0; i < caissePlateau.length; i++) {
			if (caissePlateau[i] != null
					&& caissePlateau[i].getPosCaisse().equals(
							vortex.getPosVortex())
					&& caissePlateau[i].getCouleur().equals(
							caisseARecuperee.get(0).getCouleur())) {
				// On fait disparaître la caisse
				caissePlateau[i] = null;
				// On réactualise les caisses a récupéré
				caisseARecuperee.remove(0);
				// On supprime la caisse du robot
				robot.setCaisse();
				// On vérifie s'il reste des caisses à récupérées
				if (caisseARecuperee.isEmpty()) {
					setChanged();
					notifyObservers(this);
				}
				return true;
			}
		}
		return false;
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

		if (aVerifier.getX() < debutX || aVerifier.getX() > -debutX) {
			return false;
		}
		if (aVerifier.getY() < debutY || aVerifier.getY() > -debutY) {
			return false;
		}

		// On vérifie si la position fait partie des position que l'on ne doit
		// pas atteindre (POSITIONS_INACCESSIBLES)
		for (Position inaccessibles : positionsInaccessibles) {
			// On vérifie si x et y négatif
			if (aVerifier.equals(inaccessibles)) {
				return false;
			}
			// On vérifie si x positif et y négatif
			if (aVerifier.equals(new Position(-inaccessibles.getX(),
					inaccessibles.getY()))) {
				return false;
			}
			// On vérifie si x négatif et y positif
			if (aVerifier.equals(new Position(inaccessibles.getX(),
					-inaccessibles.getY()))) {
				return false;
			}
			// On vérifie si x et y positif
			if (aVerifier.equals(new Position(-inaccessibles.getX(),
					-inaccessibles.getY()))) {
				return false;
			}
		}

		// Sinon la position est correcte
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
	 * elle existe sur le plateau de jeu et si elle n'est pas occupé par une
	 * caisse.
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
		return isPositionOK(aVerifier);
	}

	/**
	 * Vérifie si les coordonnées envoyées en paramètre sont correctes, c'est à
	 * dire si elles existent sur le plateau de jeu et si elle ne sont pas
	 * occupés par les coordonnées d'une caisse.
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
	 * Cherche la caisse sur le plateau de jeu à la position mentionnée. Si la
	 * caisse ou la position n'existe pas, on retourne null
	 * 
	 * @param position
	 *            position probable de la caisse
	 * @return la caisse à la position spécifique ou null si la position ou la
	 *         caisse n'existe pas
	 */
	public Caisse getCaisseJeu(Position position) {
		// Aucune caisse n'a été trouvée à cette position
		// Ou la position n'existe pas
		if (isPositionOKAvecCaisse(position)) {
			return null;
		}

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
	 * @return le nbColonne
	 */
	public int getNbColonne() {
		return nbColonne;
	}

	/**
	 * @return le nbLigne
	 */
	public int getNbLigne() {
		return nbLigne;
	}

	/**
	 * Renvoie le robot
	 * 
	 * @return le robot
	 */
	public Robot getRobot() {
		return robot;
	}

	/**
	 * @return le debutX
	 */
	public int getDebutX() {
		return debutX;
	}

	/**
	 * @return le debutY
	 */
	public int getDebutY() {
		return debutY;
	}

	/**
	 * @return le caisseARecuperee
	 */
	public ArrayList<Caisse> getCaisseARecuperee() {
		return caisseARecuperee;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see metier.Dessinable#dessiner(java.awt.Graphics2D)
	 */
	@Override
	public void dessiner(Graphics2D g) {
		// On vérifie si une caisse se trouve sur le vortex
		vortexAspire();

		for (int y = 0; y < nbLigne; y++) {// Axe des ordonnées
			for (int x = 0; x < nbColonne; x++) { // Axe des abscisses
				Position posADessiner = new Position(x + debutX, y + debutY);
				if (isPositionOK(posADessiner)) {
					// ---------------------------------------------------------
					// On dessine les cases vides et le vortex
					// ---------------------------------------------------------
					if (posADessiner.equals(vortex.getPosVortex())) { // Vortex
						g.setColor(UtilitaireFenetre.COULEUR_VORTEX);
					} else { // Case vide
						g.setColor(UtilitaireFenetre.COULEUR_FOND);
					}

					// On dessine le fond de la case
					g.fillRect(x * UtilitaireFenetre.DIM_CASE_VIDE.width, y
							* UtilitaireFenetre.DIM_CASE_VIDE.height,
							UtilitaireFenetre.DIM_CASE_VIDE.width,
							UtilitaireFenetre.DIM_CASE_VIDE.height);

					// On dessine la bordure de la case
					g.setStroke(new BasicStroke(
							UtilitaireFenetre.LARGEUR_BORDURE));
					g.setColor(UtilitaireFenetre.COULEUR_BORDURE);
					g.drawRect(x * UtilitaireFenetre.DIM_CASE_VIDE.width, y
							* UtilitaireFenetre.DIM_CASE_VIDE.height,
							UtilitaireFenetre.DIM_CASE_VIDE.width,
							UtilitaireFenetre.DIM_CASE_VIDE.height);
				}
			}
		}

		// ---------------------------------------------------------
		// On dessine les caisses
		// ---------------------------------------------------------
		for (Caisse caisseADessiner : caissePlateau) {
			// On calcule la nouvel abscisse et ordonnée pour
			// positionner l'image de la caisse
			// On fait cela pour centrer l'image dans la case
			// vide
			if (caisseADessiner != null) {
				int abscisseCaisse = (caisseADessiner.getPosCaisse().getX() - debutX)
						* UtilitaireFenetre.DIM_CASE_VIDE.width
						+ ((UtilitaireFenetre.DIM_CASE_VIDE.width / 2) - (UtilitaireFenetre.DIM_CAISSE.width / 2));
				int ordonneCaisse = (caisseADessiner.getPosCaisse().getY() - debutY)
						* UtilitaireFenetre.DIM_CASE_VIDE.height
						+ ((UtilitaireFenetre.DIM_CASE_VIDE.height / 2) - (UtilitaireFenetre.DIM_CAISSE.height / 2));
				Graphics2D contexteCaisse = (Graphics2D) g.create(
						abscisseCaisse, ordonneCaisse,
						UtilitaireFenetre.DIM_CAISSE.width,
						UtilitaireFenetre.DIM_CAISSE.height);
				caisseADessiner.dessiner(contexteCaisse);

				contexteCaisse.dispose();
			}
		}

		// ---------------------------------------------------------
		// On dessine le robot
		// ---------------------------------------------------------
		robot.dessiner(g);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Partie [nbColonne=" + nbColonne + ", nbLigne=" + nbLigne
				+ ", debutX=" + debutX + ", debutY=" + debutY + ", robot="
				+ robot + ", caisses=" + Arrays.toString(caissePlateau)
				+ ", niveau=" + niveau + ", vortex=" + vortex
				+ ", caisseARecup=" + caisseARecuperee
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
		Position[] posInaccessible = new Position[positionsInaccessibles.length];
		for (int i = 0; i < positionsInaccessibles.length; i++) {
			posInaccessible[i] = (Position) positionsInaccessibles[i].clone();
		}

		// On clone les caisse à récupérée
		ArrayList<Caisse> caisseARecup = new ArrayList<Caisse>(
				caisseARecuperee.size());
		for (Caisse caisse : caisseARecuperee) {
			caisseARecup.add((Caisse) caisse.clone());
		}

		// On clone les caisses disponibles sur le plateau
		Caisse[] caissePlat = new Caisse[caissePlateau.length];
		for (int i = 0; i < caissePlateau.length; i++) {
			caissePlat[i] = (Caisse) caissePlateau[i].clone();
		}

		return new Partie(nbLigne, nbColonne, posInaccessible,
				(Robot) robot.clone(), (Vortex) vortex.clone(), caisseARecup,
				caissePlat);
	}
}
