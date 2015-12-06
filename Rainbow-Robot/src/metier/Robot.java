/*
 * Robot.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package metier;

import java.awt.Graphics2D;

/**
 *
 * @author Rainbow Robot
 */
public class Robot implements Dessinable {

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

	/** Caisse du robot */
	private Caisse caisse;

	@Override
	public void dessiner(Graphics2D g) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.

	}

	/** */
	public Robot(int orientation, Position pos_ini) {
		this.orientation = orientation;
		this.pos_courante = pos_ini;
	}

	/**
	 * Méthode pour faire avancer le robot
	 */
	public void avancer() {
		// Avancer d'un indice dans la list
		// faire une switch en fonction de orientation

		switch (orientation) {

		case ORIENTATION_GAUCHE:
			pos_courante.setX(pos_courante.getX() - 1);
			break;

		case ORIENTATION_BAS:
			pos_courante.setY(pos_courante.getY() + 1);
			break;

		case ORIENTATION_DROITE:
			pos_courante.setX(pos_courante.getX() + 1);
			break;

		case ORIENTATION_HAUT:
			pos_courante.setY(pos_courante.getY() - 1);

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
			pos_courante.setX(pos_courante.getX() + 1);
			break;

		case ORIENTATION_BAS:
			pos_courante.setY(pos_courante.getY() - 1);
			break;

		case ORIENTATION_DROITE:
			pos_courante.setX(pos_courante.getX() - 1);
			break;

		case ORIENTATION_HAUT:
			pos_courante.setY(pos_courante.getY() + 1);
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
				orientation = ORIENTATION_GAUCHE;
			}
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
	public void saisirCaisse() {
		if (caisse == null) {
			// Le robot peut saisir une caisse
			// Le robot attrape une caisse
		} else {
			// le robot ne peut pas saisir de caisse
			// le robot ne fait rien
		}
	}

	/**
	 * Méthode pour faire fusionner deux caisses
	 */
	public void fusionner (Caisse c2) {
		if(caisse != null ){
			// le robot peut fusionner une caisse
			// appeler la fonction dans caisse
			//if( c1.couleur() == c2.couleur){
				// fusionne pas
			//} else {
				//fusionne
			//}
		} else {
			// le robot ne peut pas fusionner
			// le robot ne fait rien
		}

	}

	// Faire un tostring pour afficher l'orientation et la position
	public String toString() {
		return "L'orientation est " + orientation + "\n et sa postion est"
				+ pos_courante.toString() + "\n";

	}
}
