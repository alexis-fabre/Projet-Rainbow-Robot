/*
 * Carte.java							28 nov 2015
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

	/** Robot sur la carte */
	private Robot robot;

	/** Tableau de caisse */
	private Caisse[] caisses;

	/** Vortex de la carte */
	private Vortex vortex;
		
	/** caisse à recuperer pour finir une partie */
	private ArrayList<Caisse> caisseARecup = new ArrayList<Caisse>();

	/**
	 * Constrcuteur par défaut pour créer une carte
	 */
	public Partie() {
		// TODO écrire le corps

		// X = -5..-4 Y = 3..4 OU X = -5..-4 Y = -4..-3 OU X = 4..5 Y = 3..4 OU
		// X = 4..5 Y = -4..-3
		Caisse.CaisseARecuperer(caisseARecup, 1);

	}

	/**
	 * Créer une carte avec un niveau donné
	 */
	public Partie(int niveau) {
		// TODO ecrire le corps
	}

	/**
	 * Méthode pour recommencer une partie
	 */
	public void recommencer() {
		// TODO écrire le corps
	}

	/**
	 * Métode pour jouer une partie
	 */
	public void jouer() {
		// TODO écrire le corps
	}

	/**
	 * Savoir si la partie est fini
	 */
	public void isFinished() {
		// lorsque toutes les caisseARecup sont récupérer
		if(caisseARecup.isEmpty()){
			JeuRainbow.niveauCourant++;
		}
	}
	
	
	/**
	 * Renvoie le robot 
	 * @return le robot 
	 */
	public Robot getRobot(){
		return robot;
	}

}
