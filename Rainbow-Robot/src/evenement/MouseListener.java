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
	 * M�thode pour savoir si on clique sur une touche de la souris
	 * @param e
	 */
	public void mousePressed(MouseEvent e);
	
	/**
	 * M�hode pour savoir si on relache une touche de la souris
	 * @param e
	 */
	public void mouseRealeased(MouseEvent e);
	
	/**
	 * M�thode est invoqu� lorque le curseur de la souris entre dans un composant
	 * @param e
	 */
	public void mouseEntered(MouseEvent e);
	
	/**
	 * M�thode � invoquer lorque le curseur de la souris sort d'un composant
	 * @param e
	 */
	public void mouseExited(MouseEvent e);
	
	/**
	 * M�thode pour conna�tre le clic (pr�ss�e et relach�e) de souris
	 * @param e
	 */
	public void mouseClicked(MouseEvent e);
}
