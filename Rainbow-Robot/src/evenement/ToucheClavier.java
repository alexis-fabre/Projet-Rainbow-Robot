/*
 * ToucheClavier.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package evenement;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import metier.Partie;
import metier.Robot;

/**
 * @author Rainbow Robot
 *
 */
public class ToucheClavier implements KeyListener {

	/**
	 * On contrôle les déplacements du robot de la partie métier.
	 */
	private Partie metier;

	/**
	 * Détection des touches du clavier sur la fenêtre de jeu.
	 */
	private JFrame fenetre;

	/**
	 * Constructeur par défaut qui permet de détecter les touches du claviers
	 */
	public ToucheClavier() {

	}

	/**
	 * TODO Faire le constructeur
	 */
	public ToucheClavier(Partie metier, JFrame fenetreAControle) {
		this.metier = metier;
		fenetre = fenetreAControle;
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
			metier.getRobot().avancer();
			break;
		case KeyEvent.VK_LEFT: // Flêche de gauche
			metier.getRobot().pivoter(Robot.PIVOTER_GAUCHE);
			break;
		case KeyEvent.VK_RIGHT: // Flêche de droite
			metier.getRobot().pivoter(Robot.PIVOTER_DROITE);
			break;
		case KeyEvent.VK_DOWN: // Flêche du bas
			metier.getRobot().reculer();
			break;
		case KeyEvent.VK_CONTROL: // Touche Contrôle
			break;
		case KeyEvent.VK_SPACE: // Touche Espace
			// On attrape la prochaine caisse
			metier.getRobot().setCaisse();
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
	public Partie getMetier() {
		return metier;
	}

}
