/*
 * Caisse.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package metier;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Rainbow Robot
 */
public class Caisse implements Dessinable{
    
    /** Couleur de la caisse */
    private Color couleur;
    
    
    public Caisse(Color couleur){
    	this.couleur = couleur;
    }
    /**
     * 
     * @return 
     */
    public Color getCouleur() {
        return couleur;

    }
    
    /**
     * 
     * @param couleur 
     */
    public void setCouleur( Color couleur) {
        // TODO écrire le corps
    	this.couleur = couleur;
    }

    @Override
    public void dessiner(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

	/**
	 * Créer une liste de caisse pour que l'utilisateur puisse les recuperer dans sa partie
	 * @param nbCaisse nombre de caisses a récuperer
	 */
	public void CaisseARecuperer(ArrayList<Caisse> caisseArecup ,int nbCaisse){
		for(int i = 0; i < nbCaisse ; i++)
			caisseArecup.add(new Caisse(couleur));
	}
    
}
