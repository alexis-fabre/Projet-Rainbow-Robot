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
	public static int niveauCourant;

	
	/** Niveau maximum */
	private int niveauMaxAtteint;


	/** Carte de la partie */
	private ArrayList<Partie> parties = new ArrayList<Partie>(); 

	
	/**
	 * Constructeur par défaut pour créer les parties
	 */
	public JeuRainbow() {
		niveauCourant = DEFAULT_NIVEAU;
		
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
		return parties.get(niveauCourant);
	}

}
