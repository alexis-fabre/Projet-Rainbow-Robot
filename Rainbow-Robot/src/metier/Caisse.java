/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Rainbow Robot
 */
public class Caisse implements Dessinable{
    
    /** Couleur de la caisse */
    private Color couleur;
    
    /**
     * 
     * @return 
     */
    public Color getCouleur() {
        return null;
        // TODO écrire le corps
    }
    
    /**
     * 
     * @param couleur 
     */
    public void setColeur( Color couleur) {
        // TODO écrire le corps
    }

    @Override
    public void dessiner(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
