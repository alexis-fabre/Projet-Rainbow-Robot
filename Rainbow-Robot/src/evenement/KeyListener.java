/**
 * 
 */
package evenement;

import java.awt.event.KeyEvent;

/**
 * @author Rainbow Robot
 *
 */
public interface KeyListener {

	/**
	 * Méthode pour reconnaitre la touche préssée
	 * @param e
	 */
	public void keyPressed(KeyEvent e);
	
	/**
	 * Méthode pour détecter le touche relachée
	 * @param e
	 */
	public void keyReleased(KeyEvent e);
	
	/**
	 * Méthode pour reconnaitre le caractére tapé
	 * @param e
	 */
	public void keyTyped(KeyEvent e);
}
