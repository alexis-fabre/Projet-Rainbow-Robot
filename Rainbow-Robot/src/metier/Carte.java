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
public class Carte {

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

	/** Carte de la partie */
	private List map;

	/**
	 * Constrcuteur par défaut pour créer une carte
	 */
	public Carte() {
		// TODO écrire le corps
		// X = -5..-4 Y = 3..4 OU X = -5..-4 Y = -4..-3  OU  X = 4..5 Y = 3..4  OU X = 4..5 Y = -4..-3
		
	}
	
	/**
	 * Créer une carte avec un niveau donné
	 */
	public Carte(int niveau){
		// TODO ecrire le corps
	}

	/**
	 * Méthode pour recommencer uen partie
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
	 * Méthode pour réinitialiser une partie
	 * 
	 * @return
	 */
	public boolean reinitialiser() {
		// TODO écrire le corps
		return false;
	}
}
