/*
 * Robot.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package metier;

import java.awt.Graphics;

/**
 *
 * @author Rainbow Robot
 */
public class Robot implements Dessinable{

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

	/** Vitesse du robot */
	private int vitesse;

	/** Constante de la vitesse */
	private final int CONST_VITESSE = 0;

	/** Orientation du robot */
	private int orientation ;

	/** Position de depart du robot */
	private Position pos_ini;

	/** Position courante du robot courante */
	private Position pos_courante;

	@Override
	public void dessiner(Graphics g) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	/** */
	public Robot(int orientation , Position pos_ini){
		this.orientation = orientation;
		this.pos_courante = pos_ini;
	}

	/**
	 * Méthode pour faire avancer le robot
	 */
	public void avancer() {
		// TODO écrire le corps
		// Avancer d'un indice dans la list
		// faire une switch en fonction de orientation
		switch(orientation){
		
			case ORIENTATION_GAUCHE:
				pos_courante.setX(pos_courante.getX()-1);
				break;
				
			case ORIENTATION_BAS:
				pos_courante.setY(pos_courante.getY()+1);
				break;
				
			case ORIENTATION_DROITE:
				pos_courante.setX(pos_courante.getX()+1);
				break;
				
			case ORIENTATION_HAUT:
				pos_courante.setY(pos_courante.getY()-1);

				break;
		}

	}

	/**
	 * Méthode pour faire reculer le robot
	 */
	public void reculer () {
		// TODO écrire le corps
		// Reculer d'un indice dans la liste
		// faire une switch en fonction de orientation
		switch(orientation){
			case ORIENTATION_GAUCHE:
				pos_courante.setX(pos_courante.getX()+1);
				break;
				
			case ORIENTATION_BAS:
				pos_courante.setY(pos_courante.getY()-1);
				break;
				
			case ORIENTATION_DROITE:
				pos_courante.setX(pos_courante.getX()-1);
				break;
				
			case ORIENTATION_HAUT:	
				pos_courante.setY(pos_courante.getY()+1);
				break;
		}


	}

	/**
	 * Méthode pour faire pivoter le robot
	 */
	public void pivoter (int position) {
		// TODO écrire le corps
		if(position == PIVOTER_GAUCHE){

			orientation++;

			if(orientation > ORIENTATION_HAUT){
				orientation = ORIENTATION_GAUCHE;
			}
		}    	

		if(position == PIVOTER_DROITE){
			orientation--;
			if(orientation < ORIENTATION_GAUCHE){
				orientation = ORIENTATION_HAUT;
			}

		}

	}

	/**
	 * Méthode pour que le robot saisisse une caisse
	 */
	public void saisirCaisse() {
		
	}

	/**
	 * Méthode pour faire fusionner deux caisses
	 */
	public void fusionner () {
		// TODO écrire le corps
		
	}

	// Faire un tostring pour afficher l'orientation et la position
	public String toString(){
		return "L'orientation est " + orientation + "\n et sa postion est" + pos_courante.toString() + "\n";
		
	}
}
