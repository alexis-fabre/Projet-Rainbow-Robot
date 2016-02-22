/*
 * ToucheClavier.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package evenement;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import metier.JeuRainbow;
import metier.Robot;
import vue.F_jeuRainbow;

/**
 * Contrôle les touches du clavier. Elle permet de contrôler le robot lors d'une
 * partie du jeu.
 * 
 * @author Rainbow Robot
 *
 */
public class ToucheClavier implements KeyListener {

	/** On garde une référence du jeu pour pouvoir changer de niveaux */
	private JeuRainbow metier;

	/** Référence de la vue contrôlée. Cela permet de ouvoir démarrer le timer */
	private F_jeuRainbow vue;

	/**
	 * Permet de détecter la 1ère action du joueur et de lancer le timer et le
	 * vortex
	 */
	private static boolean premiereAction;

	/** Gestion du thread du vortex */
	private static Thread threadVortex;

	/**
	 * On initialise le constructeur avec la partie métier du jeu.
	 * 
	 * @param metier
	 *            représentation du jeu Rainbow Robot (partie métier). Il
	 *            contient notamment les différents niveaux.
	 */
	public ToucheClavier(JeuRainbow jeu) {
		this.metier = jeu;
		premiereAction = false;
	}

	/**
	 * @return la partie métier du jeu
	 */
	public JeuRainbow getJeuRainbow() {
		return metier;
	}

	/**
	 * @param nouvelleVue
	 *            nouvelle vue que l'on contrôle
	 */
	public void setFenetre(F_jeuRainbow nouvelleVue) {
		this.vue = nouvelleVue;
	}

	/**
	 * Détecte la 1ère action du joueur dans la partie et lance le timer, le
	 * thread du vortex. Il abonne aussi la classe à la partie courante pour
	 * détecter que la partie c'est lancé.
	 */
	private void startPartie() {
		if (!premiereAction) {
			// On démarre le timer
			vue.startChrono();
			// On lance le vortex
			threadVortex = new Thread(metier.getPartieCourante().getVortex());
			threadVortex.start();
			premiereAction = true;
		}
	}

	/** Restart les paramètres d'une partie */
	public static void restartPartie() {
		// On restart la 1ère action
		premiereAction = false;
		// On arrête le vortex
		threadVortex.interrupt();
	}

	/*
	 * Non utilisé (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// On vérifie que le robot n'est pas déjà en train de faire une action
		if (!metier.getPartieCourante().getRobot().estOccupe()) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP: // Flêche du haut
				startPartie();
				metier.getPartieCourante().getRobot().avancer();
				break;
			case KeyEvent.VK_LEFT: // Flêche de gauche
				startPartie();
				metier.getPartieCourante().getRobot()
						.pivoter(Robot.PIVOTER_GAUCHE);
				break;
			case KeyEvent.VK_RIGHT: // Flêche de droite
				startPartie();
				metier.getPartieCourante().getRobot()
						.pivoter(Robot.PIVOTER_DROITE);
				break;
			case KeyEvent.VK_DOWN: // Flêche du bas
				startPartie();
				metier.getPartieCourante().getRobot().reculer();
				break;
			case KeyEvent.VK_CONTROL: // Touche Contrôle
				startPartie();
				break;
			case KeyEvent.VK_SPACE: // Touche Espace
				startPartie();
				metier.getPartieCourante().getRobot().charger();
				break;
			}
		}
	}

	/*
	 * On détecte lors du "relachement" d'une touche du clavier (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {

	}

	/*
	 * Non utilisé (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}
}
