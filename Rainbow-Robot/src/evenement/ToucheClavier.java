/*
 * ToucheClavier.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package evenement;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import metier.JeuRainbow;
import metier.Robot;

/**
 * @author Rainbow Robot
 *
 */
public class ToucheClavier implements KeyListener {

	/**
	 * Permet de passer d'un niveau à un autre
	 */
	private JeuRainbow metier;

	/**
	 * Constructeur par défaut qui permet de détecter les touches du claviers
	 */
	public ToucheClavier() {
	}

	/**
	 * TODO Faire le constructeur
	 */
	public ToucheClavier(JeuRainbow metier) {
		this.metier = metier;
	}

	/*
	 * Non utilisé (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
	}

	/*
	 * On détecte lors du "relachement" d'une touche du clavier (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP: // Flêche du haut
			metier.getPartieCourante().getRobot().avancer();
			break;
		case KeyEvent.VK_LEFT: // Flêche de gauche
			metier.getPartieCourante().getRobot().pivoter(Robot.PIVOTER_GAUCHE);
			break;
		case KeyEvent.VK_RIGHT: // Flêche de droite
			metier.getPartieCourante().getRobot().pivoter(Robot.PIVOTER_DROITE);
			break;
		case KeyEvent.VK_DOWN: // Flêche du bas
			metier.getPartieCourante().getRobot().reculer();
			break;
		case KeyEvent.VK_CONTROL: // Touche Contrôle
			break;
		case KeyEvent.VK_SPACE: // Touche Espace
			// On attrape la prochaine caisse
			metier.getPartieCourante().getRobot().setCaisse();
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * @return la partie métier du jeu
	 */
	public JeuRainbow getMetier() {
		return metier;
	}

}
