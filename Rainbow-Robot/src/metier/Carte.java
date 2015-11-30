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
	private List<Carte> map;

	/**
	 * Constrcuteur par défaut pour créer une carte
	 */
	public Carte() {
		// TODO écrire le corps
		
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
