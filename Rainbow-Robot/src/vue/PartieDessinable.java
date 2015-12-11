/*
 * PartieDessinable.java							6 déc. 2015
 * IUT Info2 2015-2016
 */
package vue;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import metier.JeuRainbow;
import metier.Partie;
import metier.Robot;

/**
 * Panneau qui dessine et contrôle une partie de jeu Rainbow Robot. On dessine
 * les composants d'une partie et on détecte les actions faites par
 * l'utilisateur sur le clavier.
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class PartieDessinable extends JPanel implements Observer, Serializable {

	/**
	 * Générer automatiquement par Eclipse
	 */
	private static final long serialVersionUID = -8946384062710778140L;

	/**
	 * Partie courante du jeu Rainbow Robot
	 */
	Partie partieCourante;

	/**
	 * TODO Expliquer le fonctionnement du constructeur
	 * 
	 * @param gestion
	 */
	public PartieDessinable(JeuRainbow jeu) {
		super();
		// abonner cette vue aux changements du modèle (DP observateur)
		partieCourante = jeu.getPartieCourante();
		partieCourante.getRobot().addObserver(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		//System.out.println("Partie courante : " + partieCourante);
		super.paintComponent(g);

		// Nouvelle position pour centrer le jeu dans le JPanel
		int x = (super.getWidth() / 2)
				- (((UtilitaireFenetre.DIM_CASE_VIDE.width + (int) UtilitaireFenetre.LARGEUR_BORDURE) * //
				partieCourante.getNbColonne()) / 2);
		int y = (super.getHeight() / 2)
				- (((UtilitaireFenetre.DIM_CASE_VIDE.height + (int) UtilitaireFenetre.LARGEUR_BORDURE) * //
				partieCourante.getNbLigne()) / 2);

		Graphics2D contexte = (Graphics2D) g.create(x, y, super.getWidth() - x,
				super.getHeight() - y);
		partieCourante.dessiner(contexte);

		contexte.dispose();
	}

	/**
	 * Réactualise la partie courante envoyé dans JeuRainbow
	 */
	public void setJeuRainbowRobot(Partie nouvellePartie) {
		this.partieCourante = nouvellePartie;
		partieCourante.getRobot().addObserver(this);
		super.repaint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object aRedessiner) {
		if (aRedessiner instanceof Robot) {
			super.repaint();
		}
	}
}
