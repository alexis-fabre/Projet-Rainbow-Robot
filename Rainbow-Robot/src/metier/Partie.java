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

	/** Timer de la partie */
	private Timer timer;

	/** Menu de la partie */
	private Menu menu;

	/** Caisse � r�cuperer */
	private CaisseARecuperer caisseARecuperer;

	/**
	 * Constructeur par d�faut pour cr�er une partie
	 */
	public Partie() {
		// TODO �crire le corps de la m�thode
	}

	/**
	 * Constructeur avec un argument, pour cr�er une partie � partir d'un
	 * fichier mit en argument
	 * 
	 * @param nomFic
	 *            nom du fichier ou se trouve la carte
	 */
	public Partie(String nomFic) {
		// TODO �crie le corps
	}

	/**
	 * Pour r�cup�rer le niveau suivant
	 * 
	 * @param niveauCourant
	 *            niveau actuel du joueur
	 * @return
	 */
	public Carte getNiveauSuivant(int niveauCourant) {
		// TODO �crire le corps
		return null;
	}

	/**
	 * Pour r�cup�rer le niveau courant du joueur
	 * 
	 * @return
	 */
	public Carte getNiveauCourant() {
		// TODO �crire le corps
		// return this.niveauCourant + 1;
		return null;
	}

	/**
	 * @param niveau
	 * @return
	 */
	public Carte getNiveau(int niveau) {
		// TODO �crire le corps
		return null;
	}
}
