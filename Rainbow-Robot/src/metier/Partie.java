/*
 * Partie.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package metier;

/**
 *
 * @author Rainbow Robot
 */
public class Partie {

	/** Niveau actuel du joueur */
	private int niveauCourant;

	/** Niveau maximum */
	private int niveauMaxAtteint;

	/** Caisse à récuperer */
	private CaisseARecuperer caisseARecuperer;

	/**
	 * Constructeur par défaut pour créer une partie
	 */
	public Partie() {
		// TODO écrire le corps de la méthode
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
	 * Pour récupérer le niveau suivant
	 * 
	 * @param niveau
	 *            niveau actuel du joueur
	 * @return
	 */
	public Carte getNiveauSuivant(int niveau) {
		// TODO écrire le corps
		return null;
	}

	/**
	 * Pour récupérer le niveau courant du joueur
	 * 
	 * @return
	 */
	public Carte getNiveauCourant() {
		// TODO écrire le corps
		// return this.niveauCourant + 1;
		return null;
	}

	/**
	 * @param niveau
	 * @return
	 */
	public Carte getNiveauPrecedent(int niveau) {
		// TODO écrire le corps
		return null;
	}
}
