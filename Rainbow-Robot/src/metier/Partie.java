/*
 * Partie.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package metier;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rainbow Robot
 */
public class Partie {

	/** Nombre colonne de la carte */
	private int nbColonne;

	/** Nombre de lignes de la carte */
	private int nbLigne;
	
	/** Position initial du robot */
	Position pos_ini = new Position(1,0);

	/** Robot sur la carte */
	private Robot robot ;
	

	/** Tableau de caisse */
	private Caisse[] caisses;
	
	/** Niveau de la partie */
	private int niveau;

	/** Vortex de la carte */
	private Vortex vortex;
	
	/** Position vortex */
	private Position pos_vortex = new Position(0,0);
		
	/** caisse à recuperer pour finir une partie */
	private ArrayList<Caisse> caisseARecup = new ArrayList<Caisse>();


	/**
	 * Créer une carte avec un niveau donné
	 */
	public Partie(int niveau) {


		// X = -5..-4 Y = 3..4 OU X = -5..-4 Y = -4..-3 OU X = 4..5 Y = 3..4 OU
		// X = 4..5 Y = -4..-3
		Caisse.CaisseARecuperer(caisseARecup, 1);
		vortex = new Vortex(pos_vortex);
		robot = new Robot(Robot.ORIENTATION_GAUCHE,pos_ini);
		
	}


	
	
	/**
	 * Savoir si la partie est fini
	 */
	public boolean isFinished() {
		// lorsque toutes les caisseARecup sont récupérer
		boolean ok = false;
		if(caisseARecup.isEmpty()){
			JeuRainbow.setNiveau(niveau);
			ok = true;
		}
		return ok;
	}
	
	
	/**
	 * Renvoie le robot 
	 * @return le robot 
	 */
	public Robot getRobot(){
		return robot;
	}

}
