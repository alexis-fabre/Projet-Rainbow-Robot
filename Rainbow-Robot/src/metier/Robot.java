/*
 * Robot.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package metier;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.Observable;

import javax.imageio.ImageIO;

import vue.UtilitaireFenetre;

/**
 * Classe gérant le Robot présent sur la carte et pour une partie Il pourra se
 * déplacer grâce a des commandes qu'effectuera le Robot : Notamment avancer,
 * reculer et pivoter. Il sera aussi possible de fusionner deux caisses entre
 * elle.
 * 
 * @author Rainbow Robot
 */
public class Robot extends Observable implements Dessinable {

	/**
	 * Chemin de l'image du robot
	 */
	public static final String CHEMIN_IMAGE_ROBOT = "./img/robot.jpg";

	/** Orientation du robot vers la gauche */
	public static final int ORIENTATION_GAUCHE = 1;

	/** Orientation du robot vers la bas */
	public static final int ORIENTATION_BAS = 2;

	/** Orientation du robot vers la droite */
	public static final int ORIENTATION_DROITE = 3;

	/** Orientation du robot vers la haut */
	public static final int ORIENTATION_HAUT = 4;

	public static final int PIVOTER_GAUCHE = 1;

	public static final int PIVOTER_DROITE = 2;

	/** Orientation du robot */
	private int orientation;

	/** Position courante du robot courante */
	private Position pos_courante;

	/** Caisse que le robot à dans ces mains */
	private Caisse caisse;

	/** Partie du robot */
	private Partie partie;

	/** */
	public Robot(int orientation, Position pos_ini, Partie partie)
			throws IllegalArgumentException {
		if (partie.isPositionOKAvecCaisse(pos_ini)) {
			this.pos_courante = pos_ini;
		} else {
			throw new IllegalArgumentException(
					"La position initiale du robot n'est pas sur le plateau"
							+ " de la partie." + pos_ini);
		}

		this.orientation = orientation;

		this.partie = partie;

	}

	/**
	 * Méthode pour faire avancer le robot
	 */
	public void avancer() {
		// Avancer d'un indice dans la list
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
				}
			} else { // Aucune caisse associée au robot
				// On vérifie si la place est libre
				if (partie.isPositionOKAvecCaisse(pos_courante.getX() - 1,
						pos_courante.getY())) {
					// On déplace le robot
					pos_courante.setX(pos_courante.getX() - 1);
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
				}
			} else {
				if (partie.isPositionOKAvecCaisse(pos_courante.getX(),
						pos_courante.getY() + 1)) {
					pos_courante.setY(pos_courante.getY() + 1);
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
				}
			} else {
				if (partie.isPositionOKAvecCaisse(pos_courante.getX() + 1,
						pos_courante.getY())) {
					pos_courante.setX(pos_courante.getX() + 1);
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
				}
			} else {
				if (partie.isPositionOKAvecCaisse(pos_courante.getX(),
						pos_courante.getY() - 1)) {
					pos_courante.setY(pos_courante.getY() - 1);
				}
			}
			break;
		}

		// Indique que l'état du modèle a changé
		setChanged();
		// Met à jour les Observers (ici => FenetreJeu)
		notifyObservers(this);
	}

	/**
	 * Méthode pour faire reculer le robot
	 */
	public void reculer() {
		// Reculer d'un indice dans la liste
		// faire une switch en fonction de orientation
		switch (orientation) {
		case ORIENTATION_GAUCHE: // Robot orienté vers la gauche
			// On vérifie si le robot détient une caisse
			if (caisse != null) {
				// On vérifie si le déplacement de la caisse est possible
				if (deplacementCaisseOK(caisse.getPosCaisse().getX() + 1,
						caisse.getPosCaisse().getY())) {
					// On change la position de la caisse
					setPositionCaisse(caisse.getPosCaisse().getX() + 1, caisse
							.getPosCaisse().getY());
					// On change directement la position du robot
					pos_courante.setX(pos_courante.getX() + 1);
				}
			} else { // Aucune caisse associée au robot
				// On vérifie si la place est libre
				if (partie.isPositionOKAvecCaisse(pos_courante.getX() + 1,
						pos_courante.getY())) {
					// On déplace le robot
					pos_courante.setX(pos_courante.getX() + 1);
				}
			}
			break;
		case ORIENTATION_BAS: // Robot orienté vers le bas
			if (caisse != null) {
				if (deplacementCaisseOK(caisse.getPosCaisse().getX(), caisse
						.getPosCaisse().getY() - 1)) {
					setPositionCaisse(caisse.getPosCaisse().getX(), caisse
							.getPosCaisse().getY() - 1);
					pos_courante.setY(pos_courante.getY() - 1);
				}
			} else {
				if (partie.isPositionOKAvecCaisse(pos_courante.getX(),
						pos_courante.getY() - 1)) {
					pos_courante.setY(pos_courante.getY() - 1);
					;
				}
			}
			break;

		case ORIENTATION_DROITE: // Robot orienté vers la droite
			if (caisse != null) {
				if (deplacementCaisseOK(caisse.getPosCaisse().getX() - 1,
						caisse.getPosCaisse().getY())) {
					setPositionCaisse(caisse.getPosCaisse().getX() - 1, caisse
							.getPosCaisse().getY());
					pos_courante.setX(pos_courante.getX() - 1);
				}
			} else {
				if (partie.isPositionOKAvecCaisse(pos_courante.getX() - 1,
						pos_courante.getY())) {
					pos_courante.setX(pos_courante.getX() - 1);
				}
			}
			break;

		case ORIENTATION_HAUT: // Robot orienté vers le haut
			if (caisse != null) {
				if (deplacementCaisseOK(caisse.getPosCaisse().getX(), caisse
						.getPosCaisse().getY() + 1)) {
					setPositionCaisse(caisse.getPosCaisse().getX(), caisse
							.getPosCaisse().getY() + 1);
					pos_courante.setY(pos_courante.getY() + 1);
				}
			} else {
				if (partie.isPositionOKAvecCaisse(pos_courante.getX(),
						pos_courante.getY() + 1)) {
					pos_courante.setY(pos_courante.getY() + 1);
				}
			}
			break;
		}
		// Indique que l'état du modèle a changé
		setChanged();
		// Met à jour les Observers (ici => FenetreJeu)
		notifyObservers(this);

	}

	/**
	 * Méthode pour faire pivoter le robot et la caisse.
	 * 
	 * @param position
	 *            nouvelle angle (prédéfini par les constantes PIVOTER_GAUCHE et
	 *            PIVOTER_DROITE) du robot
	 */
	public void pivoter(int position) {
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
						orientation++;
						// Pour faire une rotation cyclique
						if (orientation > ORIENTATION_HAUT) {
							orientation = ORIENTATION_GAUCHE;
						}
						// Sinon on ne fait rien
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
						orientation++;
						if (orientation > ORIENTATION_HAUT) {
							orientation = ORIENTATION_GAUCHE;
						}
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
						orientation++;
						if (orientation > ORIENTATION_HAUT) {
							orientation = ORIENTATION_GAUCHE;
						}
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
						orientation++;
						if (orientation > ORIENTATION_HAUT) {
							orientation = ORIENTATION_GAUCHE;
						}
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
						orientation--;
						// Pour faire une rotation cyclique
						if (orientation < ORIENTATION_GAUCHE) {
							orientation = ORIENTATION_HAUT;
						}
						// Sinon on ne fait rien
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
						orientation--;
						// Pour faire une rotation cyclique
						if (orientation < ORIENTATION_GAUCHE) {
							orientation = ORIENTATION_HAUT;
						}
						// Sinon on ne fait rien
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
						orientation--;
						// Pour faire une rotation cyclique
						if (orientation < ORIENTATION_GAUCHE) {
							orientation = ORIENTATION_HAUT;
						}
						// Sinon on ne fait rien
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
						orientation--;
						// Pour faire une rotation cyclique
						if (orientation < ORIENTATION_GAUCHE) {
							orientation = ORIENTATION_HAUT;
						}
						// Sinon on ne fait rien
					}
					break;
				}
			}
		} else { // Le robot n'a pas de caisse
			if (position == PIVOTER_GAUCHE) { // On pivote à gauche
				orientation++;
				if (orientation > ORIENTATION_HAUT) {
					orientation = ORIENTATION_GAUCHE;
				}
				// Sinon on ne fait rien
			}
			if (position == PIVOTER_DROITE) { // On pivote à droite
				orientation--;
				if (orientation < ORIENTATION_GAUCHE) {
					orientation = ORIENTATION_HAUT;
				}
			}
		}

		// Indique que l'état du modèle a changé
		setChanged();
		// Met à jour les Observers (ici => FenetreJeu)
		notifyObservers(this);
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
	public boolean deplacementCaisseOK(int x, int y) {
		return partie.isPositionOKAvecCaisse(x, y);
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
	public void setPositionCaisse(int x, int y) {
		caisse.getPosCaisse().setX(x);
		caisse.getPosCaisse().setY(y);
	}

	/**
	 * Méthode pour que le robot saisisse ou relache une caisse qui est situé
	 * devant lui. Le robot relache la caisse si la référence de la caisse est
	 * null
	 * 
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
	 * Méthode pour faire fusionner deux caisses
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
	 * Retourne la position du Robot
	 * 
	 * @return pos_courante la position courante
	 */
	public Position getPosRobot() {
		return pos_courante;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see metier.Dessinable#dessiner(java.awt.Graphics2D)
	 */
	@Override
	public void dessiner(Graphics2D g) {
		// Abscisse du centre de la case vide associée à la position du robot
		int abscisseCentre = (pos_courante.getX() - partie.getDebutX())
				* UtilitaireFenetre.DIM_CASE_VIDE.width
				+ ((UtilitaireFenetre.DIM_CASE_VIDE.width / 2) - ((UtilitaireFenetre.DIM_ROBOT.width / 2)));
		int ordonneCentre = (pos_courante.getY() - partie.getDebutY())
				* UtilitaireFenetre.DIM_CASE_VIDE.height
				+ ((UtilitaireFenetre.DIM_CASE_VIDE.height / 2) - (UtilitaireFenetre.DIM_ROBOT.height / 2));

		// Marge due à ce que la photo du robot n'est pas centré sur le robot
		// Ces marges ont été trouvé en testant, il se peut qu'elle ne soit pas
		// correcte dans certain cas
		// Les marges par défaut correspondent à ORIENTATION_GAUCHE
		int margeX = -12, // Marge du côté des abscisses
		margeY = 15;// Marge du côté des ordonnées

		// Permet de tourner l'image selon l'orientation du robot
		AffineTransform transform = new AffineTransform();
		// L'orientation de base de l'image est vers la gauche
		switch (orientation) {
		case ORIENTATION_BAS:
			// On tourne de 270° (= 3*PI/2 radians) par rapport à l'axe de la
			// photo
			transform.rotate(3 * Math.PI / 2,
					UtilitaireFenetre.DIM_ROBOT.width / 2,
					UtilitaireFenetre.DIM_ROBOT.height / 2);
			// On redéfinie le contexte en identifiant les marges
			margeX = 15;
			margeY = 12;
			break;
		case ORIENTATION_HAUT:
			// On tourne de 90° (= 3*PI/2 radians) par rapport à l'axe de la
			// photo
			transform.rotate(Math.PI / 2,
					UtilitaireFenetre.DIM_ROBOT.width / 2,
					UtilitaireFenetre.DIM_ROBOT.height / 2);
			// On redéfinie le contexte en identifiant les marges
			margeX = -15;
			margeY = -12;
			break;
		case ORIENTATION_DROITE:
			// On tourne de 180° (= 3*PI/2 radians) par rapport à l'axe de la
			// photo
			transform.rotate(Math.PI, UtilitaireFenetre.DIM_ROBOT.width / 2,
					UtilitaireFenetre.DIM_ROBOT.height / 2);
			// On redéfinie le contexte en identifiant les marges
			margeX = 12;
			margeY = -15;
			break;
		}

		// Permet de centre l'image du robot dans le centre de la case vide
		Graphics2D contexteRobot = (Graphics2D) g.create(abscisseCentre
				+ margeX, ordonneCentre + margeY,
				UtilitaireFenetre.DIM_ROBOT.width,
				UtilitaireFenetre.DIM_ROBOT.height);

		try {
			contexteRobot.drawImage(ImageIO.read(new File(CHEMIN_IMAGE_ROBOT)),
					transform, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		contexteRobot.dispose();
	}

	/**
	 * Méthode Permettant d'afficher la position et l'orientation du robot
	 * 
	 * @return L'orientation et la position du robot
	 */
	public String toString() {
		return "L'orientation est " + orientation + "\n et sa postion est"
				+ pos_courante.toString() + "\n";

	}
}