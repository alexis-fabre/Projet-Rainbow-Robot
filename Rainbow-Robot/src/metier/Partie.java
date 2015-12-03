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

	/** Niveau par défaut de la partie */
	private static final int DEFAULT_NIVEAU = 1;
	
	/** Niveau actuel du joueur */
	private int niveauCourant;

	/** Niveau maximum */
	private int niveauMaxAtteint;

	/** Caisse à récuperer */
	private Caisse caisseARecuperer;
	
	/** */
	private ArrayList<Caisse> caisseARecup = new ArrayList<Caisse>();
	
	/** Carte de la partie */
	Carte carte; 

	/**
	 * Constructeur par défaut pour créer une partie
	 */
	public Partie() {
		// TODO écrire le corps de la méthode
		niveauCourant = DEFAULT_NIVEAU;
		carte = new Carte();
		caisseARecuperer = new CaisseARecuperer(caisseARecup,nbCaisse);
		
	}

	/**
	 * Constructeur avec un argument, pour créer une partie à partir d'un
	 * fichier mit en argument
	 * 
	 * @param nomFic
	 *            nom du fichier ou se trouve la carte
	 */
	public Partie(String nomFic) {
		// TODO écrie le corps
	}

	
	/**
	 * Pour récupérer la Carte du niveau suivant
	 * @return la carte du niveau suivant
	 */
	public Carte getNiveauSuivant(int niveau) {
		return carte = new Carte(niveauCourant + 1);
	}
	

	/**
	 * Pour récupérer la Carte du niveau courant 
	 * @return la carte du niveau courant
	 */
	public Carte getNiveauCourant(int niveau) {
		return carte = new Carte(niveauCourant);
	}
	

	/**
	 * récupérer La carte du niveau précédent
	 * @return la carte du niveau precedent
	 */
	public Carte getNiveauPrecedent(int niveau) {
		return new Carte(niveauCourant - 1) ;
	}
}
