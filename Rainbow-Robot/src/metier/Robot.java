/*
 * Robot.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package metier;

import java.awt.Graphics;

/**
 *
 * @author Rainbow Robot
 */
public class Robot implements Dessinable{
    
    /** Vitesse du robot */
    private int vitesse;
    
    /** Constante de la vitesse */
    private final int CONST_VITESSE = 0;
    
    /** Orientation du robot */
    private int orinetation;

    @Override
    public void dessiner(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * M�thode pour faire avancer le robot
     */
    public void avancer() {
        // TODO �crire le corps
    }
    
    /**
     * M�thode pour faire reculer le robot
     */
    public void reculer () {
        // TODO �crire le corps
    }
    
    /**
     * M�thode pour faire pivoter le robot
     */
    public void pivoter () {
        // TODO �crire le corps
    }
    
    /**
     * M�thode pour que le robot saisisse une caisse
     */
    public void saisirCaisse() {
        
    }
    
    /**
     * M�thode pour faire fusionner deux caisses
     */
    public void fusionner () {
        // TODO �crire le corps
    }
}
