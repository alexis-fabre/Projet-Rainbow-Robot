/*
 * PartieDessinable.java							6 déc. 2015
 * IUT Info2 2015-2016
 */
package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import metier.Partie;
import evenement.ToucheClavier;

/**
 * Panneau qui dessine et contrôle une partie de jeu Rainbow Robot. On dessine
 * les composants d'une partie et on détecte les actions faites par
 * l'utilisateur sur le clavier.
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class PartieDessinable extends JPanel {

	/**
	 * Référence de la classe qui gère une partie du jeu.
	 */
	Partie jeuRainbowRobot;

	/**
	 * TODO Expliquer le fonctionnement du constructeur
	 * 
	 * @param gestion
	 */
	public PartieDessinable(ToucheClavier gestion, Partie partie) {
		super();
		setPreferredSize(new Dimension(UtilitaireFenetre.DIM_FENETRE.width,
				2 * UtilitaireFenetre.DIM_FENETRE.height / 3));
		jeuRainbowRobot = partie;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Nouvelle position pour centrer le jeu dans le JPanel
		int x = (super.getWidth() / 2)
				- (((UtilitaireFenetre.DIM_CASE_VIDE.width + (int) UtilitaireFenetre.LARGEUR_BORDURE) * //
				jeuRainbowRobot.getNbColonne()) / 2);
		int y = (super.getHeight() / 2)
				- (((UtilitaireFenetre.DIM_CASE_VIDE.height + (int) UtilitaireFenetre.LARGEUR_BORDURE) * //
				jeuRainbowRobot.getNbLigne()) / 2);

		Graphics2D contexte = (Graphics2D) g.create(x, y, super.getWidth() - x,
				super.getHeight() - y);
		jeuRainbowRobot.dessiner(contexte);

		contexte.dispose();

	}
}
