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
	 * M�thode pour reconnaitre la touche pr�ss�e
	 * @param e
	 */
	public void keyPressed(KeyEvent e);
	
	/**
	 * M�thode pour d�tecter le touche relach�e
	 * @param e
	 */
	public void keyReleased(KeyEvent e);
	
	/**
	 * M�thode pour reconnaitre le caract�re tap�
	 * @param e
	 */
	public void keyTyped(KeyEvent e);
}
