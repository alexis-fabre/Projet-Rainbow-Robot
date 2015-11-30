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
	 * Constrcuteur par d�faut pour cr�er une carte
	 */
	public Carte() {
		// TODO �crire le corps
	}

	/**
	 * M�thode pour recommencer uen partie
	 */
	public void recommencer() {
		// TODO �crire le corps
	}

	/**
	 * M�tode pour jouer une partie
	 */
	public void jouer() {
		// TODO �crire le corps
	}

	/**
	 * M�thode pour r�initialiser une partie
	 * 
	 * @return
	 */
	public boolean reinitialiser() {
		// TODO �crire le corps
		return false;
	}
}
