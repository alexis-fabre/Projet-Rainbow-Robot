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

	/** Indique que la dernière action du robot a été de charger une caisse */
	public static final int ACTION_CHARGER = 4;

	/** Indique que la dernière action du robot a été de fusionner deux caisses */
	public static final int ACTION_FUSIONNER = 5;

	/** Unité de temps (en seconde) */
	public static final float UNITE_TEMPS = 1.0f;

	/** Vitesse lorsque l'on avance à vide ou sans caisse (en nombre de cases) */
	public static final float VITESSE_AVANCER_VIDE = 2.0f;

	/** Vitesse lorsque l'on recule à vide ou sans caisse (en nombre de cases) */
	public static final float VITESSE_RECULER_VIDE = 1.5f;

	/** Vitesse lorsque l'on pivote à vide ou sans caisse (en nombre de cases) */
	public static final float VITESSE_PIVOTER_VIDE = 1.0f;

	/** Vitesse lorsque l'on avance avec une caisse (en nombre de cases) */
	public static final float VITESSE_AVANCER_CHARGE = 1.0f;

	/** Vitesse lorsque l'on recule avec une caisse (en nombre de cases) */
	public static final float VITESSE_RECULER_CHARGE = 0.5f;

	/** Vitesse lorsque l'on pivote avec une caisse (en nombre de cases) */
	public static final float VITESSE_PIVOTER_CHARGE = 0.5f;

	/** Vitesse lorsque l'on fusionne deux caisses (en nombre de cases) */
	public static final float VITESSE_FUSIONNER = 1.0f;

	/** Vitesse lorsque l'on charge/décharge une caisse (en nombre de cases) */
	public static final float VITESSE_CHARGER = 1.0f;

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

	/**
	 * Abscisse où la caisse sera dessinée lorsque l'on pivote. Utiliser pour
	 * l'animation
	 */
	private int abscisseDessinCaissePivoter;

	/**
	 * Ordonnee où le caisse sera dessinée lorsque l'on pivote. Utiliser pour
	 * l'animation
	 */
	private int ordonneeDessinCaissePivoter;

	/** Dernière action effectué par le robot */
	private int derniereAction;

	/** true si le robot est en train d'effectuer une action, false sinon */
	private boolean estOccupe;

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
	}

	/**
	 * Permet de passer d'un angle en degré à une orientation de robot.
	 * 
	 * @param angle
	 *            angle dont on veux connaître son orientation
	 * @return orientatin de l'angle
	 */
	public static int angleToOrientation(int angle) {
		return (angle / FACTEUR_TRANSFORMATION_ORIENTATION_ANGLE) % 4;
	}

	/**
	 * Simule l'action d'une rotation sur la gauche selon une orientation de
	 * base.
	 * 
	 * @param orientation
	 *            orientation de base
	 * @return orientation de base plus un quart de tour vers la gauche
	 */
	public static int pivoterGauche(int orientation) {
		return (orientation - 1) < ORIENTATION_GAUCHE ? ORIENTATION_BAS
				: (orientation - 1);
	}

	/**
	 * Simule l'action d'une rotation sur la droite selon une orientation de
	 * base.
	 * 
	 * @param orientation
	 *            orientation de base
	 * @return orientation de base plus un quart de tour vers la droite
	 */
	public static int pivoterDroite(int orientation) {
		return (orientation + 1) % 4;
	}

	/**
	 * Effectue un demi-tour à une orientation de départ
	 * 
	 * @param orientationDepart
	 *            orientation de départ
	 * @return demi-tour après l'orientation de départ
	 */
	public static int demiTourOrientation(int orientationDepart) {
		return (orientationDepart + 2) & 3;
	}

	/**
	 * Modifie les variables internes pour dessiner le robot. Cette fonction est
	 * à appeler avant chaque déplacement du robot.
	 */
	private void initRobotAction() {
		angleDessin = orientation * FACTEUR_TRANSFORMATION_ORIENTATION_ANGLE;
		abscisseDessin = pos_courante.getX()
				* UtilitaireFenetre.DIM_CASE_VIDE.width
				+ ((UtilitaireFenetre.DIM_CASE_VIDE.width / 2) - ((UtilitaireFenetre.DIM_ROBOT.width / 2)));
		ordonneeDessin = pos_courante.getY()
				* UtilitaireFenetre.DIM_CASE_VIDE.height
				+ ((UtilitaireFenetre.DIM_CASE_VIDE.height / 2) - (UtilitaireFenetre.DIM_ROBOT.height / 2));

		if (caisse != null) {
			abscisseDessinCaisse = caisse.getPosCaisse().getX()
					* UtilitaireFenetre.DIM_CASE_VIDE.width
					+ ((UtilitaireFenetre.DIM_CASE_VIDE.width / 2) - ((UtilitaireFenetre.DIM_CAISSE.width / 2)));
			ordonneeDessinCaisse = caisse.getPosCaisse().getY()
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
		abscisseDessinMax = pos_courante.getX()
				* UtilitaireFenetre.DIM_CASE_VIDE.width
				+ ((UtilitaireFenetre.DIM_CASE_VIDE.width / 2) - ((UtilitaireFenetre.DIM_ROBOT.width / 2)));
		ordonneeDessinMax = pos_courante.getY()
				* UtilitaireFenetre.DIM_CASE_VIDE.height
				+ ((UtilitaireFenetre.DIM_CASE_VIDE.height / 2) - (UtilitaireFenetre.DIM_ROBOT.height / 2));
		angleDessinMax = orientation * FACTEUR_TRANSFORMATION_ORIENTATION_ANGLE;

		if (derniereAction == ACTION_PIVOTER) {
			abscisseDessinCaissePivoter = abscisseDessinCaisse;
			ordonneeDessinCaissePivoter = ordonneeDessinCaisse;
		}

		setEstOccupe(true);
		// On envoie au Observers que l'on s'est déplacé
		setChanged();
		notifyObservers(this);
		// L'observers renverra setEstOccupe(false) une fois qu'il aura fini son
		// traitement
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
		return partie.isPositionOKAvecTout(x, y);
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
	 * Méthode pour faire avancer le robot selon son orientation et s'il dispose
	 * d'un caisse ou non. Si le déplacement est impossible, alors le robot ne
	 * se déplace pas (ni la caisse s'il en a une qui lui est rattachée).
	 */
	public void avancer() {
		// On garde les anciennes positions
		initRobotAction();
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
				if (partie.isPositionOKAvecTout(pos_courante.getX() - 1,
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
				if (partie.isPositionOKAvecTout(pos_courante.getX(),
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
				if (partie.isPositionOKAvecTout(pos_courante.getX() + 1,
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
				if (partie.isPositionOKAvecTout(pos_courante.getX(),
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
		initRobotAction();
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
						&& partie.isPositionOKAvecTout(pos_courante.getX() + 1,
								pos_courante.getY())) {
					// On change la position de la caisse
					setPositionCaisse(caisse.getPosCaisse().getX() + 1, caisse
							.getPosCaisse().getY());
					// On change directement la position du robot
					pos_courante.setX(pos_courante.getX() + 1);
					updateObserver();
				}
			} else { // Aucune caisse associée au robot
				// On vérifie si la place est libre
				if (partie.isPositionOKAvecTout(pos_courante.getX() + 1,
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
						&& partie.isPositionOKAvecTout(pos_courante.getX(),
								pos_courante.getY() - 1)) {
					setPositionCaisse(caisse.getPosCaisse().getX(), caisse
							.getPosCaisse().getY() - 1);
					pos_courante.setY(pos_courante.getY() - 1);
					updateObserver();
				}
			} else {
				if (partie.isPositionOKAvecTout(pos_courante.getX(),
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
						&& partie.isPositionOKAvecTout(pos_courante.getX() - 1,
								pos_courante.getY())) {
					setPositionCaisse(caisse.getPosCaisse().getX() - 1, caisse
							.getPosCaisse().getY());
					pos_courante.setX(pos_courante.getX() - 1);
					updateObserver();
				}
			} else {
				if (partie.isPositionOKAvecTout(pos_courante.getX() - 1,
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
						&& partie.isPositionOKAvecTout(pos_courante.getX(),
								pos_courante.getY() + 1)) {
					setPositionCaisse(caisse.getPosCaisse().getX(), caisse
							.getPosCaisse().getY() + 1);
					pos_courante.setY(pos_courante.getY() + 1);
					updateObserver();
				}
			} else {
				if (partie.isPositionOKAvecTout(pos_courante.getX(),
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
		initRobotAction();
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
						orientation = pivoterGauche(orientation);
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
						orientation = pivoterGauche(orientation);
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
						orientation = pivoterGauche(orientation);
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
						orientation = pivoterGauche(orientation);
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
						orientation = pivoterDroite(orientation);
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
						orientation = pivoterDroite(orientation);
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
						orientation = pivoterDroite(orientation);
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
						orientation = pivoterDroite(orientation);
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
	 * Méthode pour que le robot saisisse ou relache une caisse qui est situé
	 * devant lui. Le robot relache la caisse si la référence de la caisse est
	 * null
	 */
	public void charger() {
		final long timerDebut = System.currentTimeMillis();
		derniereAction = ACTION_CHARGER;
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
		// On convertit de seconde en milli secondes
		float tempsPauseNominal = tempsDerniereAction() * 1000;
		final long tempsMis = System.currentTimeMillis() - timerDebut;
		final long tempsPause = (long) tempsPauseNominal - tempsMis;
		// On fait une pause dépendant du temps mis et du temps qu'il faut
		// patienter
		try {
			Thread.sleep(tempsPause);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Méthode pour faire fusionner deux caisses.
	 * TODO NullPointerException à gérer 
	 */
	public void fusionner() {
		initRobotAction();
		derniereAction = ACTION_FUSIONNER;
		Caisse c2 = new Caisse(0) ;
		Caisse[] caissePlateau = partie.getCaissePlateau();

		if (caisse != null) {
			// fusionne
			switch (orientation) {
			case ORIENTATION_GAUCHE:
				c2.setCouleur(partie.getCaisseJeu(new Position(
						pos_courante.getX() - 2, pos_courante.getY())).getCouleur());
				c2.setPosCaisse(new Position(pos_courante.getX() - 2, 
						pos_courante.getY()));
				updateObserver();
				break;

			case ORIENTATION_HAUT:
				c2.setCouleur(partie.getCaisseJeu(new Position(pos_courante.getX(),
						pos_courante.getY() - 2)).getCouleur());
				c2.setPosCaisse(new Position(pos_courante.getX(),
						pos_courante.getY() - 2));
				updateObserver();
				break;

			case ORIENTATION_DROITE:
				c2.setCouleur(partie.getCaisseJeu(new Position(
						pos_courante.getX() + 2,
						pos_courante.getY())).getCouleur());
				c2.setPosCaisse(new Position(pos_courante.getX() + 2,
						pos_courante.getY()));
				updateObserver();
				break;

			case ORIENTATION_BAS:
				c2.setCouleur(partie.getCaisseJeu(new Position(pos_courante.getX(),
						pos_courante.getY() + 2)).getCouleur());
				c2.setPosCaisse(new Position(pos_courante.getX(),
						pos_courante.getY() + 2));
				updateObserver();
				break;
			}

			// on initialise la caisse résultat fusion
			Caisse c3 = Caisse.fusionCouleur(caisse, c2);


			// On parcours les caisse du plateau

			for (int i = 0; i < caissePlateau.length; i++) {

                // si une caisse correpond à la caisse tenu on la fait disparaitre

				if (caissePlateau[i] != null
						&& caissePlateau[i].getPosCaisse().equals(

								caisse.getPosCaisse()) ) {

					// On fait disparaître la caisse
					caissePlateau[i] = null;


		            // on parcours de nouveau les caisses afin de chercher une
					// caisse correspondant à la caisse situer avant la caisse
					// tenue par le robot
					for (int j = 0; i < caissePlateau.length; j++)	 {
						
						if(caissePlateau[j] != null
								&& caissePlateau[j].getPosCaisse().equals(
										                      c2.getPosCaisse())
								&& c3.getCouleur() != 0){

							//Robot prend la position de Caisse
							pos_courante = new Position(caisse.getPosCaisse().getX(),
									caisse.getPosCaisse().getY());
							// On fait disparaître la caisse devant la caisse tenue
							c2 = null;
							// on fait disparaitre la caisse tenue
							caisse = null;
							// on fait apparaitre la caisse issue de la fusion 
							caissePlateau[j] = c3;
							updateObserver();
							break;


						} 
                       
					}
				} 

			}

		} else {
			// le robot ne peut pas fusionner
			// le robot ne fait rien
		}
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

	/**
	 * Retourne le temps qu'a mis la dernière action pour étre exécuté
	 * 
	 * @return temps en seconde
	 */
	public float tempsDerniereAction() {
		switch (derniereAction) {
		case ACTION_AVANCER:
			if (caisse != null) {
				return UNITE_TEMPS / VITESSE_AVANCER_CHARGE;
			} else {
				return UNITE_TEMPS / VITESSE_AVANCER_VIDE;
			}
		case ACTION_RECULER:
			if (caisse != null) {
				return UNITE_TEMPS / VITESSE_RECULER_CHARGE;
			} else {
				return UNITE_TEMPS / VITESSE_RECULER_VIDE;
			}
		case ACTION_PIVOTER:
			if (caisse != null) {
				return UNITE_TEMPS / VITESSE_PIVOTER_CHARGE;
			} else {
				return UNITE_TEMPS / VITESSE_PIVOTER_VIDE;
			}
		case ACTION_CHARGER:
			return UNITE_TEMPS / VITESSE_CHARGER;
		case ACTION_FUSIONNER:
			return UNITE_TEMPS / VITESSE_FUSIONNER;
		}
		// Ce cas ne devrait jamais arrivé
		return 0.0f;
	}

	/** @return la position courante du robot */
	public Position getPosRobot() {
		return pos_courante;
	}

	/** @return orientation du robot */
	public int getOrientation() {
		return orientation;
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
	 * @return true si le robot est en train d'effectuer une action, false sinon
	 */
	public boolean estOccupe() {
		return estOccupe;
	}

	/**
	 * @param estOccupe
	 *            nouveau statu du robot
	 */
	public void setEstOccupe(boolean estOccupe) {
		this.estOccupe = estOccupe;
	}

	/**
	 * @param aAjouter
	 *            Modifie l'abscisse de dessin du robot et de la caisse en lui
	 *            ajoutant un nombre (en pixel)
	 */
	public void addAbscisseDessin(int aAjouter) {
		abscisseDessin += aAjouter;
		abscisseDessinCaisse += aAjouter;
	}

	/**
	 * @param aAjouter
	 *            Modifie l'ordonnée de dessin du robot et de la caisse en lui
	 *            ajoutant un nombre (en pixel)
	 */
	public void addOrdonneeDessin(int aAjouter) {
		ordonneeDessin += aAjouter;
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
			angleDessin = 360 + angleDessin;
		}

		// On modifie les coordonnées de la caisse selon l'équation d'un cercle
		// Soit un cercle de centre C (a,b)
		// Soit r => le rayon du cercle
		// Soit alpha => un arc de cercle (en radian)
		// Le point M (x,y) qui se trouve à l'intersection entre le cercle et la
		// droite d qui passe par C et d'angle alpha par rapport à l'axe des
		// abscisses
		// Alors x = a + r*cos(alpha)
		// y = b + r*sin(alpha)

		// Convertion de l'angle de dessin en l'angle 'alpha'
		final int alpha = -(180 - angleDessin);
		// Centre du robot
		final int xr = abscisseDessin + (UtilitaireFenetre.DIM_ROBOT.width / 2);
		final int yr = ordonneeDessin
				+ (UtilitaireFenetre.DIM_ROBOT.height / 2);
		// Centre de la caisse
		final int xc = abscisseDessinCaissePivoter
				+ (UtilitaireFenetre.DIM_CAISSE.width / 2);
		final int yc = ordonneeDessinCaissePivoter
				+ (UtilitaireFenetre.DIM_CAISSE.height / 2);
		// distance entre le centre du robot et le centre de la caisse
		// On utilise la formule :
		// Sqrt( (xr-xc)² + (yr-yc)² )
		final int r = (int) Math.sqrt(Math.pow(xr - xc, 2)
				+ Math.pow(yr - yc, 2));

		// a => abscisseDessin
		// b => ordonneeDessin
		// x => abscisseDessinCaisse
		// y => ordonneeDessinCaisse
		abscisseDessinCaisse = (int) (xr + r
				* Math.cos((alpha * Math.PI) / 180) - (UtilitaireFenetre.DIM_CAISSE.width / 2));
		ordonneeDessinCaisse = (int) (yr + r
				* Math.sin((alpha * Math.PI) / 180) - (UtilitaireFenetre.DIM_CAISSE.height / 2));
	}

	/**
	 * @param partie
	 *            le partie à modifier
	 */
	public void setPartie(Partie partie) {
		this.partie = partie;
		abscisseDessinMax = pos_courante.getX()
				* UtilitaireFenetre.DIM_CASE_VIDE.width
				+ ((UtilitaireFenetre.DIM_CASE_VIDE.width / 2) - ((UtilitaireFenetre.DIM_ROBOT.width / 2)));
		ordonneeDessinMax = pos_courante.getY()
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
			int abscisseCaisse = caisse.getPosCaisse().getX()
					* UtilitaireFenetre.DIM_CASE_VIDE.width
					+ ((UtilitaireFenetre.DIM_CASE_VIDE.width / 2) - (UtilitaireFenetre.DIM_CAISSE.width / 2));
			int ordonneCaisse = caisse.getPosCaisse().getY()
					* UtilitaireFenetre.DIM_CASE_VIDE.height
					+ ((UtilitaireFenetre.DIM_CASE_VIDE.height / 2) - (UtilitaireFenetre.DIM_CAISSE.height / 2));
			Graphics2D contexteCaisse = (Graphics2D) g.create(abscisseCaisse,
					ordonneCaisse, UtilitaireFenetre.DIM_CAISSE.width,
					UtilitaireFenetre.DIM_CAISSE.height);
			caisse.dessiner(contexteCaisse);
			contexteCaisse.dispose();
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

		contexteRobot.dispose();
	}

	/**
	 * Permet de dessiner le robot et sa caisse dans un état différent de son
	 * état final (utilisé pour les animations).
	 * 
	 * @param g
	 *            contexte graphique 2D
	 */
	public void animationDeplacement(Graphics2D g) {

		// Permet de tourner l'image selon l'orientation du robot
		AffineTransform transform = new AffineTransform();

		if (caisse != null) {
			Graphics2D contexteCaisse = (Graphics2D) g.create(
					abscisseDessinCaisse, ordonneeDessinCaisse,
					UtilitaireFenetre.DIM_CAISSE.width,
					UtilitaireFenetre.DIM_CAISSE.height);
			caisse.dessiner(contexteCaisse);
			contexteCaisse.dispose();
		}

		Graphics2D contexteRobot = (Graphics2D) g.create(abscisseDessin,
				ordonneeDessin, UtilitaireFenetre.DIM_ROBOT.width,
				UtilitaireFenetre.DIM_ROBOT.height);

		transform.rotate((angleDessin * Math.PI) / 180,
				UtilitaireFenetre.DIM_ROBOT.width / 2,
				UtilitaireFenetre.DIM_ROBOT.height / 2);
		contexteRobot.transform(transform);

		try {
			contexteRobot.drawImage(ImageIO.read(new File(CHEMIN_IMAGE_ROBOT)),
					0, 0, null);
		} catch (IOException e) {
			System.out
			.println("Robot : dessiner : Chemin de l'image introuvable");
		}
		contexteRobot.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Robot : avec une orientation de " + orientation
				+ " et une postion a " + pos_courante.toString() + "\n";
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
