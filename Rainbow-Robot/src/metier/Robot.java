/*
 * Robot.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package metier;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

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
public class Robot implements Dessinable {

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
		if (partie.isPositionOK(pos_ini)) {
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

		case ORIENTATION_GAUCHE:
			if (partie.isPositionOK(pos_courante.getX() - 1,
					pos_courante.getY())) {
				pos_courante.setX(pos_courante.getX() - 1);
			}
			break;

		case ORIENTATION_BAS:
			if (partie.isPositionOK(pos_courante.getX(),
					pos_courante.getY() + 1)) {
				pos_courante.setY(pos_courante.getY() + 1);
			}
			break;

		case ORIENTATION_DROITE:
			if (partie.isPositionOK(pos_courante.getX() + 1,
					pos_courante.getY())) {
				pos_courante.setX(pos_courante.getX() + 1);
			}
			break;

		case ORIENTATION_HAUT:
			if (partie.isPositionOK(pos_courante.getX(),
					pos_courante.getY() - 1)) {
				pos_courante.setY(pos_courante.getY() - 1);
			}

			break;
		}

	}

	/**
	 * Méthode pour faire reculer le robot
	 */
	public void reculer() {
		// Reculer d'un indice dans la liste
		// faire une switch en fonction de orientation
		switch (orientation) {
		case ORIENTATION_GAUCHE:
			if (partie.isPositionOK(pos_courante.getX() + 1,
					pos_courante.getY())) {
				pos_courante.setX(pos_courante.getX() + 1);
			}
			break;

		case ORIENTATION_BAS:
			if (partie.isPositionOK(pos_courante.getX(),
					pos_courante.getY() - 1)) {
				pos_courante.setY(pos_courante.getY() - 1);
			}
			break;

		case ORIENTATION_DROITE:
			if (partie.isPositionOK(pos_courante.getX() - 1,
					pos_courante.getY())) {
				pos_courante.setX(pos_courante.getX() - 1);
			}
			break;

		case ORIENTATION_HAUT:
			if (partie.isPositionOK(pos_courante.getX(),
					pos_courante.getY() + 1)) {
				pos_courante.setY(pos_courante.getY() + 1);
			}
			break;
		}

	}

	/**
	 * Méthode pour faire pivoter le robot
	 */
	public void pivoter(int position) {
		if (position == PIVOTER_GAUCHE) {

			orientation++;

			if (orientation > ORIENTATION_HAUT) {
				// Si il n'y pas de caisse a cote alors on peut pivoter
				orientation = ORIENTATION_GAUCHE;
			}
			// Sinon on ne fait rien
		}

		if (position == PIVOTER_DROITE) {
			orientation--;
			if (orientation < ORIENTATION_GAUCHE) {
				orientation = ORIENTATION_HAUT;
			}

		}

	}

	/**
	 * Méthode pour que le robot saisisse une caisse
	 */
	public void saisirCaisse(Caisse aAttraper) {
		if (caisse == null) {
			// Le robot peut saisir une caisse
			// Le robot attrape une caisse
			caisse = aAttraper;
		} else {
			// le robot ne peut pas saisir de caisse
			// le robot ne fait rien
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
		switch (orientation) {
		case ORIENTATION_BAS:

			break;
		case ORIENTATION_HAUT:
			break;
		case ORIENTATION_DROITE:
			break;
		case ORIENTATION_GAUCHE:
			try {
				g.drawImage(ImageIO.read(new File(CHEMIN_IMAGE_ROBOT)), 0, 0,
						UtilitaireFenetre.DIM_ROBOT.width,
						UtilitaireFenetre.DIM_ROBOT.height, null);
			} catch (IOException e) {
				System.out.println("Robot : dessiner : Image inexistante");
			}
			break;
		}

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
