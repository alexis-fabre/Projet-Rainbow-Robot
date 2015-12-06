/*
 * Partie.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package metier;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author Rainbow Robot
 */
public class Partie extends Observable implements Dessinable{

	/** Nombre colonne de la carte */
	private int nbColonne;

	/** Nombre de lignes de la carte */
	private int nbLigne;
	
	/** Position initial du robot */
	Position pos_ini = new Position(1,0);

	/** Robot sur la carte */
	private Robot robot = new Robot(Robot.ORIENTATION_GAUCHE,pos_ini);
	

	/** Tableau de caisse */
	private Caisse[] caisses;

	/** Vortex de la carte */
	private Vortex vortex;
		
	/** caisse à recuperer pour finir une partie */
	private ArrayList<Caisse> caisseARecup = new ArrayList<Caisse>();


	/**
	 * Créer une carte avec un niveau donné
	 */
	public Partie(int niveau) {
		// TODO ecrire le corps

		// X = -5..-4 Y = 3..4 OU X = -5..-4 Y = -4..-3 OU X = 4..5 Y = 3..4 OU
		// X = 4..5 Y = -4..-3
		Caisse.CaisseARecuperer(caisseARecup, 1);

	}


	
	
	/**
	 * Savoir si la partie est fini
	 */
	public boolean isFinished() {
		// lorsque toutes les caisseARecup sont récupérer
		boolean ok = false;
		if(caisseARecup.isEmpty()){
			JeuRainbow.niveauCourant++;
			ok = true;
			return ok;
		} else {
			return ok;
		}	
	}
	
	
	/**
	 * Renvoie le robot 
	 * @return le robot 
	 */
	public Robot getRobot(){
		return robot;
	}

	/* (non-Javadoc)
	 * @see metier.Dessinable#dessiner(java.awt.Graphics2D)
	 */
	@Override
	public void dessiner(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

}
