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
public class JeuRainbow {

	/** Niveau par défaut de la partie */
	private static final int DEFAULT_NIVEAU = 0;
	
	/** Niveau actuel du joueur */
	private int niveauCourant;

	/** Niveau maximum */
	private int niveauMaxAtteint;

	/** Caisse à récuperer */
	private Caisse caisseARecuperer;
	
	/** */
	private ArrayList<Caisse> caisseARecup = new ArrayList<Caisse>();
	
	/** Carte de la partie */
	private ArrayList<Partie> carte = new ArrayList<Partie>(); 

	
	/**
	 * Constructeur par défaut pour créer une partie
	 */
	public JeuRainbow() {

		niveauCourant = DEFAULT_NIVEAU;

		Caisse.CaisseARecuperer(caisseARecup,1);
		
	}

	/**
	 * Constructeur avec un argument, pour créer une partie à partir d'un
	 * fichier mit en argument
	 * 
	 * @param nomFic
	 *            nom du fichier ou se trouve la carte
	 */
	public JeuRainbow(String nomFic) {
		// TODO écrie le corps
	}

	
	/**
	 * Pour récupérer la Carte du niveau suivant
	 * @return la carte du niveau suivant
	 */
	public Partie getNiveau() {
		return carte.get(niveauCourant);
	}

}
