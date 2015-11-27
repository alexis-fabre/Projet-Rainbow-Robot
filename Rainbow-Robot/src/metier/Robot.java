/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
     * Méthode pour faire avancer le robot
     */
    public void avancer() {
        // TODO écrire le corps
    }
    
    /**
     * Méthode pour faire reculer le robot
     */
    public void reculer () {
        // TODO écrire le corps
    }
    
    /**
     * Méthode pour faire pivoter le robot
     */
    public void pivoter () {
        // TODO écrire le corps
    }
    
    /**
     * Méthode pour que le robot saisisse une caisse
     */
    public void saisirCaisse() {
        
    }
    
    /**
     * Méthode pour faire fusionner deux caisses
     */
    public void fusionner () {
        // TODO écrire le corps
    }
}
