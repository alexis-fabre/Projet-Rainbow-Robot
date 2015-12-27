/*
 * Robot.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package metier;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Observable;

import javax.imageio.ImageIO;

import vue.UtilitaireFenetre;

/**
 * Classe gérant le Robot présent sur la carte et pour une partie. Il pourra se
 * déplacer grâce a des commandes qu'effectuera le Robot. Notamment avancer,
 * reculer et pivoter. Il pourra aussi attraper un caisse et soit la déposer,
 * soit la fusionner avec une autre caisse.
 * 
 * @author Rainbow Robot
 */
public class Robot extends Observable implements Dessinable, Serializable {

	/**
	 * Générer automatiquement par Eclipse
	 */
	private static final long serialVersionUID = -2940704803433530495L;

	/**
	 * Chemin de l'image du robot
	 */
	public static final String CHEMIN_IMAGE_ROBOT = "./Ressource/img/robot.png";

	/**
	 * <p>
	 * Orientation du robot vers la gauche.<br />
	 * Ne pas modifier la valeur de cette variable sous peine de devoir refaire
	 * tous les algorithmes.
	 * </p>
	 */
	public static final int ORIENTATION_GAUCHE = 0;

	/**
	 * <p>
	 * Orientation du robot vers le haut.<br />
	 * Ne pas modifier la valeur de cette variable sous peine de devoir refaire
	 * tous les algorithmes.
	 * </p>
	 */
	public static final int ORIENTATION_HAUT = 1;

	/**
	 * <p>
	 * Orientation du robot vers la droite.<br />
	 * Ne pas modifier la valeur de cette variable sous peine de devoir refaire
	 * tous les algorithmes.
	 * </p>
	 */
	public static final int ORIENTATION_DROITE = 2;
	/**
	 * <p>
	 * Orientation du robot vers le bas.<br />
	 * Ne pas modifier la valeur de cette variable sous peine de devoir refaire
	 * tous les algorithmes.
	 * </p>
	 */
	public static final int ORIENTATION_BAS = 3;

	/** Facteur de transformation de l'orientation du robot en degré de l'angle */
	public static final int FACTEUR_TRANSFORMATION_ORIENTATION_ANGLE = 90;

	/** Permet de faire pivoter le robot vers la gauche */
	public static final int PIVOTER_GAUCHE = 1;

	/** Permet de faire pivoter le robot vers la droite */
	public static final int PIVOTER_DROITE = 2;

	/** Unité de temps (en seconde) */
	public static final int UNITE_TEMPS = 1;

	/**
	 * Indique que le robot n'a fait aucune action, c'est le cas lors de
	 * l'initialisation du robot.
	 */
	public static final int AUCUNE_ACTION = 0;

	/** Indique que la dernière action du robot a été d'avancer */
	public static final int ACTION_AVANCER = 1;

	/** Indique que la dernière action du robot a été de reculer */
	public static final int ACTION_RECULER = 2;

	/** Indique que la dernière action du robot a été de pivoter */
	public static final int ACTION_PIVOTER = 3;

	/**
	 * <p>
	 * Vitesse lorsque l'on avance à vide ou sans caisse<br />
	 * (en nombre de case / unite de temps)
	 * </p>
	 */
	public static final int VITESSE_AVANCER_VIDE = 2;

	/**
	 * <p>
	 * Vitesse lorsque l'on recule à vide ou sans caisse<br />
	 * (en nombre de case / unite de temps)
	 * </p>
	 */
	public static final float VITESSE_RECULER_VIDE = 1.5f;

	/**
	 * <p>
	 * Vitesse lorsque l'on pivote à vide ou sans caisse<br />
	 * (en nombre de case / unite de temps)
	 * </p>
	 */
	public static final int VITESSE_PIVOTER_VIDE = 1;

	/**
	 * <p>
	 * Vitesse lorsque l'on avance avec une caisse<br />
	 * (en nombre de case / unite de temps)
	 * </p>
	 */
	public static final int VITESSE_AVANCER_CHARGE = 1;

	/**
	 * <p>
	 * Vitesse lorsque l'on recule avec une caisse<br />
	 * (en nombre de case / unite de temps)
	 * </p>
	 */
	public static final float VITESSE_RECULER_CHARGE = 0.5f;

	/**
	 * <p>
	 * Vitesse lorsque l'on pivote avec une caisse<br />
	 * (en nombre de case / unite de temps)
	 * </p>
	 */
	public static final int VITESSE_PIVOTER_CHARGE = 2;

	/**
	 * <p>
	 * Vitesse lorsque l'on fusionne deux caisses<br />
	 * (en nombre de case / unite de temps)
	 * </p>
	 */
	public static final int VITESSE_FUSIONNER = 1;

	/**
	 * <p>
	 * Vitesse lorsque l'on charge/décharge une caisse<br />
	 * (en nombre de case / unite de temps)
	 * </p>
	 */
	public static final int VITESSE_CHARGER = 1;

	/** Orientation du robot */
	private int orientation;

	/** Position courante du robot courante */
	private Position pos_courante;

	/** Caisse que le robot à attraper */
	private Caisse caisse;

	/** Partie du robot */
	private Partie partie;

	/** Abscisse où le robot sera dessiné. Utiliser pour l'animation */
	private int abscisseDessin;

	/** Ordonnee où le robot sera dessiné. Utiliser pour l'animation */
	private int ordonneeDessin;

	/**
	 * Angle en degré du robotet de la caisse lorsqu'ils seront dessinés.
	 * Utiliser pour l'animation
	 */
	private int angleDessin;

	/**
	 * Abscisse où le robot et de la caisse sera dessiné à la fin de l'animation
	 */
	private int abscisseDessinMax;

	/**
	 * Ordonnee où le robot et de la caisse sera dessiné à la fin de l'animation
	 */
	private int ordonneeDessinMax;

	/**
	 * Angle en degré du robot et de la caisse lorsqu'ils seront dessinés à la
	 * fin de l'animation
	 */
	private int angleDessinMax;

	/** Abscisse où la caisse sera dessinée. Utiliser pour l'animation */
	private int abscisseDessinCaisse;

	/** Ordonnee où le caisse sera dessinée. Utiliser pour l'animation */
	private int ordonneeDessinCaisse;

	/** Dernière action effectué par le robot */
	private int derniereAction;

	/**
	 * Construit un robot avec une position et une orientation initiale.
	 * 
	 * @param orientation
	 *            orientation initiale du robot
	 * @param pos_ini
	 *            position initiale du robot
	 */
	public Robot(int orientation, Position pos_ini) {
		this.pos_courante = pos_ini;
		this.orientation = orientation;
		derniereAction = AUCUNE_ACTION;
		// On initialise les variables de dessin lorsque l'on associe une partie
		// au robot
	}

	/**
	 * Modifie les variables internes pour dessiner le robot. Cette fonction est
	 * à appeler avant chaque déplacement du robot.
	 */
	private void setVariableDessin() {
		angleDessin = orientation * FACTEUR_TRANSFORMATION_ORIENTATION_ANGLE;
		abscisseDessin = (pos_courante.getX() - partie.getDebutX())
				* UtilitaireFenetre.DIM_CASE_VIDE.width
				+ ((UtilitaireFenetre.DIM_CASE_VIDE.width / 2) - ((UtilitaireFenetre.DIM_ROBOT.width / 2)));
		ordonneeDessin = (pos_courante.getY() - partie.getDebutY())
				* UtilitaireFenetre.DIM_CASE_VIDE.height
				+ ((UtilitaireFenetre.DIM_CASE_VIDE.height / 2) - (UtilitaireFenetre.DIM_ROBOT.height / 2));

		if (caisse != null) {
			abscisseDessinCaisse = (caisse.getPosCaisse().getX() - partie
					.getDebutX())
					* UtilitaireFenetre.DIM_CASE_VIDE.width
					+ ((UtilitaireFenetre.DIM_CASE_VIDE.width / 2) - ((UtilitaireFenetre.DIM_CAISSE.width / 2)));
			ordonneeDessinCaisse = (caisse.getPosCaisse().getY() - partie
					.getDebutY())
					* UtilitaireFenetre.DIM_CASE_VIDE.height
					+ ((UtilitaireFenetre.DIM_CASE_VIDE.height / 2) - (UtilitaireFenetre.DIM_CAISSE.height / 2));
		}
	}

	/**
	 * Actualise les données pour le dessin et envoie un signal aux observers du
	 * robot pour se redessiner. Cette fonction est à appeler après que l'on est
	 * déplacer le robot.
	 */
	private void updateObserver() {
		abscisseDessinMax = (pos_courante.getX() - partie.getDebutX())
				* UtilitaireFenetre.DIM_CASE_VIDE.width
				+ ((UtilitaireFenetre.DIM_CASE_VIDE.width / 2) - ((UtilitaireFenetre.DIM_ROBOT.width / 2)));
		ordonneeDessinMax = (pos_courante.getY() - partie.getDebutY())
				* UtilitaireFenetre.DIM_CASE_VIDE.height
				+ ((UtilitaireFenetre.DIM_CASE_VIDE.height / 2) - (UtilitaireFenetre.DIM_ROBOT.height / 2));
		angleDessinMax = orientation * FACTEUR_TRANSFORMATION_ORIENTATION_ANGLE;

		// On envoie au Observers que l'on s'est déplacé
		setChanged();
		notifyObservers(this);
	}

	/**
	 * Méthode pour faire avancer le robot selon son orientation et s'il dispose
	 * d'un caisse ou non. Si le déplacement est impossible, alors le robot ne
	 * se déplace pas (ni la caisse s'il en a une qui lui est rattachée).
	 */
	public void avancer() {
		// On garde les anciennes positions
		setVariableDessin();
		derniereAction = ACTION_AVANCER;

		// Avancer d'un indice dans la liste
		// faire une switch en fonction de orientation
		switch (orientation) {
		case ORIENTATION_GAUCHE: // Robot orienté vers la gauche
			// On vérifie si le robot détient une caisse
			if (caisse != null) {
				// On vérifie si le déplacement de la caisse est possible
				if (deplacementCaisseOK(caisse.getPosCaisse().getX() - 1,
						caisse.getPosCaisse().getY())) {
					// On change la position de la caisse
					setPositionCaisse(caisse.getPosCaisse().getX() - 1, caisse
							.getPosCaisse().getY());
					// On change directement la position du robot
					pos_courante.setX(pos_courante.getX() - 1);
					updateObserver();
				}
			} else { // Aucune caisse associée au robot
				// On vérifie si la place est libre
				if (partie.isPositionOKAvecCaisse(pos_courante.getX() - 1,
						pos_courante.getY())) {
					// On déplace le robot
					pos_courante.setX(pos_courante.getX() - 1);
					updateObserver();
				}
			}
			break;

		case ORIENTATION_BAS:// Robot orienté vers le bas
			if (caisse != null) {
				if (deplacementCaisseOK(caisse.getPosCaisse().getX(), caisse
						.getPosCaisse().getY() + 1)) {
					setPositionCaisse(caisse.getPosCaisse().getX(), caisse
							.getPosCaisse().getY() + 1);
					pos_courante.setY(pos_courante.getY() + 1);
					updateObserver();
				}
			} else {
				if (partie.isPositionOKAvecCaisse(pos_courante.getX(),
						pos_courante.getY() + 1)) {
					pos_courante.setY(pos_courante.getY() + 1);
					updateObserver();
				}
			}
			break;

		case ORIENTATION_DROITE:// Robot orienté vers la droite
			if (caisse != null) {
				if (deplacementCaisseOK(caisse.getPosCaisse().getX() + 1,
						caisse.getPosCaisse().getY())) {
					setPositionCaisse(caisse.getPosCaisse().getX() + 1, caisse
							.getPosCaisse().getY());
					pos_courante.setX(pos_courante.getX() + 1);
					updateObserver();
				}
			} else {
				if (partie.isPositionOKAvecCaisse(pos_courante.getX() + 1,
						pos_courante.getY())) {
					pos_courante.setX(pos_courante.getX() + 1);
					updateObserver();
				}
			}
			break;

		case ORIENTATION_HAUT:// Robot orienté vers le haut
			if (caisse != null) {
				if (deplacementCaisseOK(caisse.getPosCaisse().getX(), caisse
						.getPosCaisse().getY() - 1)) {
					setPositionCaisse(caisse.getPosCaisse().getX(), caisse
							.getPosCaisse().getY() - 1);
					pos_courante.setY(pos_courante.getY() - 1);
					updateObserver();
				}
			} else {
				if (partie.isPositionOKAvecCaisse(pos_courante.getX(),
						pos_courante.getY() - 1)) {
					pos_courante.setY(pos_courante.getY() - 1);
					updateObserver();
				}
			}
			break;
		}
	}

	/**
	 * Méthode pour faire reculer le robot selon son orientation et s'il dispose
	 * d'un caisse ou non. Si le déplacement est impossible, alors le robot ne
	 * se déplace pas (ni la caisse s'il en a une qui lui est rattachée).
	 */
	public void reculer() {
		// On garde les anciennes positions
		setVariableDessin();
		derniereAction = ACTION_RECULER;

		// Reculer d'un indice dans la liste
		// faire une switch en fonction de orientation
		switch (orientation) {
		case ORIENTATION_GAUCHE: // Robot orienté vers la gauche
			// On vérifie si le robot détient une caisse
			if (caisse != null) {
				// On vérifie si le déplacement de la caisse est possible
				if (deplacementCaisseOK(caisse.getPosCaisse().getX() + 1,
						caisse.getPosCaisse().getY())
						&& partie.isPositionOKAvecCaisse(
								pos_courante.getX() + 1, pos_courante.getY())) {
					// On change la position de la caisse
					setPositionCaisse(caisse.getPosCaisse().getX() + 1, caisse
							.getPosCaisse().getY());
					// On change directement la position du robot
					pos_courante.setX(pos_courante.getX() + 1);
					updateObserver();
				}
			} else { // Aucune caisse associée au robot
				// On vérifie si la place est libre
				if (partie.isPositionOKAvecCaisse(pos_courante.getX() + 1,
						pos_courante.getY())) {
					// On déplace le robot
					pos_courante.setX(pos_courante.getX() + 1);
					updateObserver();
				}
			}
			break;

		case ORIENTATION_BAS: // Robot orienté vers le bas
			if (caisse != null) {
				if (deplacementCaisseOK(caisse.getPosCaisse().getX(), caisse
						.getPosCaisse().getY() - 1)
						&& partie.isPositionOKAvecCaisse(pos_courante.getX(),
								pos_courante.getY() - 1)) {
					setPositionCaisse(caisse.getPosCaisse().getX(), caisse
							.getPosCaisse().getY() - 1);
					pos_courante.setY(pos_courante.getY() - 1);
					updateObserver();
				}
			} else {
				if (partie.isPositionOKAvecCaisse(pos_courante.getX(),
						pos_courante.getY() - 1)) {
					pos_courante.setY(pos_courante.getY() - 1);
					updateObserver();
				}
			}
			break;

		case ORIENTATION_DROITE: // Robot orienté vers la droite
			if (caisse != null) {
				if (deplacementCaisseOK(caisse.getPosCaisse().getX() - 1,
						caisse.getPosCaisse().getY())
						&& partie.isPositionOKAvecCaisse(
								pos_courante.getX() - 1, pos_courante.getY())) {
					setPositionCaisse(caisse.getPosCaisse().getX() - 1, caisse
							.getPosCaisse().getY());
					pos_courante.setX(pos_courante.getX() - 1);
					updateObserver();
				}
			} else {
				if (partie.isPositionOKAvecCaisse(pos_courante.getX() - 1,
						pos_courante.getY())) {
					pos_courante.setX(pos_courante.getX() - 1);
					updateObserver();
				}
			}
			break;

		case ORIENTATION_HAUT: // Robot orienté vers le haut
			if (caisse != null) {
				if (deplacementCaisseOK(caisse.getPosCaisse().getX(), caisse
						.getPosCaisse().getY() + 1)
						&& partie.isPositionOKAvecCaisse(pos_courante.getX(),
								pos_courante.getY() + 1)) {
					setPositionCaisse(caisse.getPosCaisse().getX(), caisse
							.getPosCaisse().getY() + 1);
					pos_courante.setY(pos_courante.getY() + 1);
					updateObserver();
				}
			} else {
				if (partie.isPositionOKAvecCaisse(pos_courante.getX(),
						pos_courante.getY() + 1)) {
					pos_courante.setY(pos_courante.getY() + 1);
					updateObserver();
				}
			}
			break;
		}
	}

	/**
	 * Méthode pour faire pivoter le robot et la caisse. Si le déplacement est
	 * impossible, alors le robot ne se déplace pas (ni la caisse s'il en a une
	 * qui lui est rattachée).
	 * 
	 * @param position
	 *            nouvelle orientation (prédéfini par les constantes
	 *            PIVOTER_GAUCHE et PIVOTER_DROITE) du robot
	 */
	public void pivoter(int position) {
		// On garde les anciennes positions
		setVariableDessin();
		derniereAction = ACTION_PIVOTER;

		if (caisse != null) { // Le robot a une caisse
			if (position == PIVOTER_GAUCHE) { // On pivote à gauche
				switch (orientation) {
				case ORIENTATION_GAUCHE:
					// On vérifie si la nouvelle position de la caisse existe
					// On vérifie deux cases qui sont :
					// - la future place de la caisse
					// - la case entre la place courante et la place future de
					// la caisse
					if (deplacementCaisseOK(caisse.getPosCaisse().getX(),
							caisse.getPosCaisse().getY() + 1)
							&& deplacementCaisseOK(
									caisse.getPosCaisse().getX() + 1, caisse
											.getPosCaisse().getY() + 1)) {
						// On modifie la position de la caisse
						setPositionCaisse(caisse.getPosCaisse().getX() + 1,
								caisse.getPosCaisse().getY() + 1);
						// Nouvelle orientation du robot
						orientation = (orientation - 1) < ORIENTATION_GAUCHE ? ORIENTATION_BAS
								: (orientation - 1);
						updateObserver();
					}
					break;
				case ORIENTATION_BAS:
					if (deplacementCaisseOK(caisse.getPosCaisse().getX() + 1,
							caisse.getPosCaisse().getY())
							&& deplacementCaisseOK(
									caisse.getPosCaisse().getX() + 1, caisse
											.getPosCaisse().getY() - 1)) {
						setPositionCaisse(caisse.getPosCaisse().getX() + 1,
								caisse.getPosCaisse().getY() - 1);
						orientation = (orientation - 1) < ORIENTATION_GAUCHE ? ORIENTATION_BAS
								: (orientation - 1);
						updateObserver();
					}
					break;
				case ORIENTATION_DROITE:
					if (deplacementCaisseOK(caisse.getPosCaisse().getX(),
							caisse.getPosCaisse().getY() - 1)
							&& deplacementCaisseOK(
									caisse.getPosCaisse().getX() - 1, caisse
											.getPosCaisse().getY() - 1)) {
						setPositionCaisse(caisse.getPosCaisse().getX() - 1,
								caisse.getPosCaisse().getY() - 1);
						orientation = (orientation - 1) < ORIENTATION_GAUCHE ? ORIENTATION_BAS
								: (orientation - 1);
						updateObserver();
					}
					break;
				case ORIENTATION_HAUT:
					if (deplacementCaisseOK(caisse.getPosCaisse().getX() - 1,
							caisse.getPosCaisse().getY())
							&& deplacementCaisseOK(
									caisse.getPosCaisse().getX() - 1, caisse
											.getPosCaisse().getY() + 1)) {
						setPositionCaisse(caisse.getPosCaisse().getX() - 1,
								caisse.getPosCaisse().getY() + 1);
						orientation = (orientation - 1) < ORIENTATION_GAUCHE ? ORIENTATION_BAS
								: (orientation - 1);
						updateObserver();
					}
					break;
				}
			}
			if (position == PIVOTER_DROITE) { // On pivote à droite
				switch (orientation) {
				case ORIENTATION_GAUCHE:
					if (deplacementCaisseOK(caisse.getPosCaisse().getX(),
							caisse.getPosCaisse().getY() - 1)
							&& deplacementCaisseOK(
									caisse.getPosCaisse().getX() + 1, caisse
											.getPosCaisse().getY() - 1)) {
						setPositionCaisse(caisse.getPosCaisse().getX() + 1,
								caisse.getPosCaisse().getY() - 1);
						// Nouvelle orientation du robot
						// Pour faire une rotation cyclique on fait le modulo 4
						orientation = (orientation + 1) % 4;
						updateObserver();
					}
					break;
				case ORIENTATION_HAUT:
					if (deplacementCaisseOK(caisse.getPosCaisse().getX() + 1,
							caisse.getPosCaisse().getY())
							&& deplacementCaisseOK(
									caisse.getPosCaisse().getX() + 1, caisse
											.getPosCaisse().getY() + 1)) {
						setPositionCaisse(caisse.getPosCaisse().getX() + 1,
								caisse.getPosCaisse().getY() + 1);
						// Nouvelle orientation du robot
						orientation = (orientation + 1) % 4;
						updateObserver();
					}
					break;
				case ORIENTATION_DROITE:
					if (deplacementCaisseOK(caisse.getPosCaisse().getX(),
							caisse.getPosCaisse().getY() + 1)
							&& deplacementCaisseOK(
									caisse.getPosCaisse().getX() - 1, caisse
											.getPosCaisse().getY() + 1)) {
						setPositionCaisse(caisse.getPosCaisse().getX() - 1,
								caisse.getPosCaisse().getY() + 1);
						// Nouvelle orientation du robot
						orientation = (orientation + 1) % 4;
						updateObserver();
					}
					break;
				case ORIENTATION_BAS:
					if (deplacementCaisseOK(caisse.getPosCaisse().getX() - 1,
							caisse.getPosCaisse().getY())
							&& deplacementCaisseOK(
									caisse.getPosCaisse().getX() - 1, caisse
											.getPosCaisse().getY() - 1)) {
						setPositionCaisse(caisse.getPosCaisse().getX() - 1,
								caisse.getPosCaisse().getY() - 1);
						// Nouvelle orientation du robot
						orientation = (orientation + 1) % 4;
						updateObserver();
					}

					break;
				}
			}
		} else { // Le robot n'a pas de caisse
			if (position == PIVOTER_GAUCHE) { // On pivote à gauche
				orientation = (orientation - 1) < ORIENTATION_GAUCHE ? ORIENTATION_BAS
						: (orientation - 1);
			}
			if (position == PIVOTER_DROITE) { // On pivote à droite
				orientation = (orientation + 1) % 4;

			}
			updateObserver();
		}
	}

	/**
	 * <p>
	 * Permet de vérifier si le déplacement de la caisse associée au robot (si
	 * elle existe) dans toutes les directions (horizontal (= avancer, reculer)
	 * ou circulaire (=pivoter) est possible.<br />
	 * La méthode ne vérifie pas si la caisse existe ou non.
	 * </p>
	 *
	 * @param x
	 *            nouvel abscisse de la caisse
	 * @param y
	 *            nouvel ordonnée de la caisse
	 * @return true si le déplacement est possible ou faux si la caisse ne peut
	 *         pas se déplacer
	 */
	private boolean deplacementCaisseOK(int x, int y) {
		return partie.isPositionOKAvecCaisse(x, y);
	}

	/**
	 * Méthode pour faire fusionner deux caisses. Ne pas l'utiliser
	 */
	public void fusionner(Caisse c2) {
		if (caisse != null) {
			// fusionne
			Caisse.fusionCouleur(caisse, c2);
		} else {
			// le robot ne peut pas fusionner
			// le robot ne fait rien
		}
	}

	/**
	 * Permet de passer d'un angle en degré à une orientation de robot. Cette
	 * fonction ne modifie pas l'orientation du robot
	 * 
	 * @param angle
	 *            angle dont on veux connaître son orientation
	 * @return orientatin de l'angle
	 */
	public static int angleToOrientation(int angle) {
		return (angle / FACTEUR_TRANSFORMATION_ORIENTATION_ANGLE) % 4;
	}

	/**
	 * Détermine, uniquement si la dernière action était de pivoter, le sens de
	 * pivotation du robot.
	 * 
	 * @return PIVOTER_DROITE si le robot a pivoté vers la droite,
	 *         PIVOTER_GAUCHE si le robot a pivoté vers la gauche ou -1 si la
	 *         dernière action n'était pas pivoter.
	 */
	public int sensPivoter() {
		if (derniereAction != ACTION_PIVOTER) {
			return -1;
		}
		final int orientationDepart = Robot.angleToOrientation(angleDessin);
		final int orientationArrivee = Robot.angleToOrientation(angleDessinMax);
		return ((orientationArrivee > orientationDepart || (orientationArrivee == Robot.ORIENTATION_GAUCHE && orientationDepart == Robot.ORIENTATION_BAS)) && (orientationArrivee != Robot.ORIENTATION_BAS || orientationDepart != Robot.ORIENTATION_GAUCHE)) //
		? PIVOTER_DROITE
				: PIVOTER_GAUCHE;
	}

	/** @return pos_courante la position courante du robot */
	public Position getPosRobot() {
		return pos_courante;
	}

	/**
	 * @return le derniereAction
	 */
	public int getDerniereAction() {
		return derniereAction;
	}

	/** @return le caisse récupérée par le robot */
	public Caisse getCaisse() {
		return caisse;
	}

	/** @return l'abscisse de dessin du robot à l'état précédent */
	public int getAbscisseDessin() {
		return abscisseDessin;
	}

	/**
	 * @return l'ordonnee de dessin du robot à l'état précédent
	 */
	public int getOrdonneeDessin() {
		return ordonneeDessin;
	}

	/**
	 * @return l'angle de dessin du robot à l'état précédent
	 */
	public int getAngleDessin() {
		return angleDessin;
	}

	/**
	 * @return l'abscisse de dessin de la caisse à l'état précédent
	 */
	public int getAbscisseDessinCaisse() {
		return abscisseDessinCaisse;
	}

	/**
	 * @return l'ordonnee de dessin de la caisse à l'état précédent
	 */
	public int getOrdonneeDessinCaisse() {
		return ordonneeDessinCaisse;
	}

	/**
	 * @return l'abscisse de dessin du robot et de la caisse à la fin de
	 *         l'animation
	 */
	public int getAbscisseDessinMax() {
		return abscisseDessinMax;
	}

	/**
	 * @return l'ordonnee de dessin du robot et de la caisse à la fin de
	 *         l'animation
	 */
	public int getOrdonneeDessinMax() {
		return ordonneeDessinMax;
	}

	/**
	 * @return l'angle de dessin du robot et de la caisse à la fin de
	 *         l'animation
	 */
	public int getAngleDessinMax() {
		return angleDessinMax;
	}

	/**
	 * @param aAjouter
	 *            Modifie l'abscisse de dessin du robot en lui ajoutant un
	 *            nombre (en pixel)
	 */
	public void addAbscisseDessin(int aAjouter) {
		abscisseDessin += aAjouter;
	}

	/**
	 * @param aAjouter
	 *            Modifie l'ordonnée de dessin du robot en lui ajoutant un
	 *            nombre (en pixel)
	 */
	public void addOrdonneeDessin(int aAjouter) {
		ordonneeDessin += aAjouter;
	}

	/**
	 * @param aAjouter
	 *            Modifie l'abscisse de dessin de la caisse transporté par le
	 *            robot en lui ajoutant un nombre (en pixel)
	 */
	public void addAbscisseDessinCaisse(int aAjouter) {
		abscisseDessinCaisse += aAjouter;
	}

	/**
	 * @param aAjouter
	 *            Modifie l'ordonnée de dessin de la caisse transporté par le
	 *            robot en lui ajoutant un nombre (en pixel)
	 */
	public void addOrdonneeDessinCaisse(int aAjouter) {
		ordonneeDessinCaisse += aAjouter;
	}

	/**
	 * @param aAjouter
	 *            Modifie l'angle de dessin du robot et de la caisse en lui
	 *            ajoutant un nombre (en degré)
	 */
	public void addAngleDessin(int aAjouter) {
		angleDessin += aAjouter;
		angleDessin %= 360;
		if (angleDessin < 0) {
			angleDessin = 359;
		}
	}

	/**
	 * Modifie directement la position de la caisse. Il ne vérifie pas si la
	 * caisse existe ou si la place est libre (voir deplacementCaisseOK).
	 * 
	 * @param x
	 *            nouvel abscisse de la caisse
	 * @param y
	 *            nouvel ordonnée de la caisse
	 */
	private void setPositionCaisse(int x, int y) {
		caisse.getPosCaisse().setX(x);
		caisse.getPosCaisse().setY(y);
	}

	/**
	 * Méthode pour que le robot saisisse ou relache une caisse qui est situé
	 * devant lui. Le robot relache la caisse si la référence de la caisse est
	 * null
	 */
	public void setCaisse() {
		if (caisse != null) {// Si on a déjà une caisse, on la relache ...
			caisse = null;
		} else {// ... sinon on cherche la position juste devant le robot
			switch (orientation) {
			case ORIENTATION_GAUCHE:
				caisse = partie.getCaisseJeu(new Position(
						pos_courante.getX() - 1, pos_courante.getY()));
				break;
			case ORIENTATION_HAUT:
				caisse = partie.getCaisseJeu(new Position(pos_courante.getX(),
						pos_courante.getY() - 1));
				break;
			case ORIENTATION_DROITE:
				caisse = partie.getCaisseJeu(new Position(
						pos_courante.getX() + 1, pos_courante.getY()));
				break;
			case ORIENTATION_BAS:
				caisse = partie.getCaisseJeu(new Position(pos_courante.getX(),
						pos_courante.getY() + 1));
				break;
			}
		}
	}

	/**
	 * @param partie
	 *            le partie à modifier
	 * @throws IllegalArgumentException
	 *             si la position du Robot est déjà occupé dans la partie
	 */
	public void setPartie(Partie partie) throws IllegalArgumentException {
		if (!partie.isPositionOKAvecCaisse(pos_courante)) {
			throw new IllegalArgumentException(
					"La position initiale du robot n'est pas sur le plateau"
							+ " de la partie." + pos_courante);
		}
		this.partie = partie;
		abscisseDessinMax = (pos_courante.getX() - partie.getDebutX())
				* UtilitaireFenetre.DIM_CASE_VIDE.width
				+ ((UtilitaireFenetre.DIM_CASE_VIDE.width / 2) - ((UtilitaireFenetre.DIM_ROBOT.width / 2)));
		ordonneeDessinMax = (pos_courante.getY() - partie.getDebutY())
				* UtilitaireFenetre.DIM_CASE_VIDE.height
				+ ((UtilitaireFenetre.DIM_CASE_VIDE.height / 2) - (UtilitaireFenetre.DIM_ROBOT.height / 2));
		angleDessinMax = orientation * FACTEUR_TRANSFORMATION_ORIENTATION_ANGLE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see metier.Dessinable#dessiner(java.awt.Graphics2D)
	 */
	@Override
	public void dessiner(Graphics2D g) {
		// Permet de tourner l'image selon l'orientation du robot
		AffineTransform transform = new AffineTransform();

		if (caisse != null) {
			int abscisseCaisse = (caisse.getPosCaisse().getX() - partie
					.getDebutX())
					* UtilitaireFenetre.DIM_CASE_VIDE.width
					+ ((UtilitaireFenetre.DIM_CASE_VIDE.width / 2) - (UtilitaireFenetre.DIM_CAISSE.width / 2));
			int ordonneCaisse = (caisse.getPosCaisse().getY() - partie
					.getDebutY())
					* UtilitaireFenetre.DIM_CASE_VIDE.height
					+ ((UtilitaireFenetre.DIM_CASE_VIDE.height / 2) - (UtilitaireFenetre.DIM_CAISSE.height / 2));
			Graphics2D contexteCaisse = (Graphics2D) g.create(abscisseCaisse,
					ordonneCaisse, UtilitaireFenetre.DIM_CAISSE.width,
					UtilitaireFenetre.DIM_CAISSE.height);
			caisse.dessiner(contexteCaisse);
		}
		transform.rotate((angleDessinMax * Math.PI) / 180,
				UtilitaireFenetre.DIM_ROBOT.width / 2,
				UtilitaireFenetre.DIM_ROBOT.height / 2);
		Graphics2D contexteRobot = (Graphics2D) g.create(abscisseDessinMax,
				ordonneeDessinMax, UtilitaireFenetre.DIM_ROBOT.width,
				UtilitaireFenetre.DIM_ROBOT.height);
		try {
			contexteRobot.transform(transform);
			contexteRobot.drawImage(ImageIO.read(new File(CHEMIN_IMAGE_ROBOT)),
					0, 0, null);
		} catch (IOException e) {
			System.out
					.println("Robot : dessiner : Chemin de l'image introuvable");
		}
	}

	/**
	 * Permet de dessiner le robot et sa caisse dans un état différent de son
	 * état final (utilisé pour les animations).
	 * 
	 * @param g
	 *            contexte graphique 2D
	 */
	public void animation(Graphics2D g) {

		// Permet de tourner l'image selon l'orientation du robot
		AffineTransform transform = new AffineTransform();

		if (caisse != null) {
			Graphics2D contexteCaisse = (Graphics2D) g.create(
					abscisseDessinCaisse, ordonneeDessinCaisse,
					UtilitaireFenetre.DIM_CAISSE.width,
					UtilitaireFenetre.DIM_CAISSE.height);
			caisse.dessiner(contexteCaisse);
		}
		transform.rotate((angleDessin * Math.PI) / 180,
				UtilitaireFenetre.DIM_ROBOT.width / 2,
				UtilitaireFenetre.DIM_ROBOT.height / 2);
		Graphics2D contexteRobot = (Graphics2D) g.create(abscisseDessin,
				ordonneeDessin, UtilitaireFenetre.DIM_ROBOT.width,
				UtilitaireFenetre.DIM_ROBOT.height);
		try {
			contexteRobot.transform(transform);
			contexteRobot.drawImage(ImageIO.read(new File(CHEMIN_IMAGE_ROBOT)),
					0, 0, null);
		} catch (IOException e) {
			System.out
					.println("Robot : dessiner : Chemin de l'image introuvable");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Robot : avec une orientation de " + orientation
				+ " et une postion a" + pos_courante.toString() + "\n";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// La caisse n'est pas récupéré car le robot ne peut être cloné avec sa
		// caisse (ou elle ne sera pas attaché à lui)
		// La partie n'est pas non plus cloner car sinon il y aurait une boucle
		// infini entre Partie et Robot
		return new Robot(orientation, (Position) pos_courante.clone());
	}

}
