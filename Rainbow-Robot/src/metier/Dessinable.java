/*
 * Dessinable.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package metier;

import java.awt.Graphics2D;

/**
 * Classe qui va gérer les différentes méthodes dessier de chaque composant sur
 * un plateau comme un robot, un vortex, une partie , des caisses
 * 
 * @author Rainbow Robot
 */
public interface Dessinable {

	/**
	 * Méhode pour dessiner
	 * 
	 * @param g
	 */
	public void dessiner(Graphics2D g);

}
