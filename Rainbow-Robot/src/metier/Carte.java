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
     * Constrcuteur par défaut pour créer une carte
     */
    public Carte() {
        // TODO écrire le corps
    	// Ne pas créer de caisses de X et Y = 0..1 ; X = 9..10 et Y = 1..2
    	// X = 0..1 et Y = 7..8 et X = 9..10 et Y = 7..8
    	
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
     * Méthode pour réinitialiser une partie
     * @return 
     */
    public boolean reinitialiser() {
        //TODO écrire le corps
        return false;
    }
}
