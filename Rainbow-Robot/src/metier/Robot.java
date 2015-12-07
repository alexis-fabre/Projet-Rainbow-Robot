/*
 * Robot.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package metier;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
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