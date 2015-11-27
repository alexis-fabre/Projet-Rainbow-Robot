/**
 * 
 */
package evenement;

import java.awt.event.MouseEvent;

/**
 * @author Rainbow Robot
 *
 */
public interface MouseListener {
	
	/**
	 * Méthode pour savoir si on clique sur une touche de la souris
	 * @param e
	 */
	public void mousePressed(MouseEvent e);
	
	/**
	 * Méhode pour savoir si on relache une touche de la souris
	 * @param e
	 */
	public void mouseRealeased(MouseEvent e);
	
	/**
	 * Méthode est invoqué lorque le curseur de la souris entre dans un composant
	 * @param e
	 */
	public void mouseEntered(MouseEvent e);
	
	/**
	 * Méthode é invoquer lorque le curseur de la souris sort d'un composant
	 * @param e
	 */
	public void mouseExited(MouseEvent e);
	
	/**
	 * Méthode pour connaétre le clic (préssée et relachée) de souris
	 * @param e
	 */
	public void mouseClicked(MouseEvent e);
}
