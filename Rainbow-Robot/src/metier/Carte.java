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
	
	/** Vortex de la carte*/
	private Vortex vortex;
	
	/** Carte de la partie */
	private ArrayList<Carte> map = new ArrayList<Carte>();
    
    
    /**
     * Constrcuteur par d�faut pour cr�er une carte
     */
    public Carte() {
        // TODO �crire le corps
    	// Ne pas cr�er de caisses de X et Y = 0..1 ; X = 9..10 et Y = 1..2
    	// X = 0..1 et Y = 7..8 et X = 9..10 et Y = 7..8
    	
    }
    
    /**
     * M�thode pour recommencer une partie
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
     * @return 
     */
    public boolean reinitialiser() {
        //TODO �crire le corps
        return false;
    }
}
