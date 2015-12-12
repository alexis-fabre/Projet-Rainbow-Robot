/*
 * P_CaisseADessiner.java							12 déc. 2015
 * IUT Info2 2015-2016
 */
package vue;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import metier.Caisse;
import metier.JeuRainbow;
import metier.Partie;
import metier.Robot;

/**
 * TODO Expliquer le fonctionnement de la classe
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class P_caisseADessiner extends JPanel implements Observer {

	/**
	 * Partie courante du jeu Rainbow Robot
	 */
	Partie partieCourante;

	/**
	 * TODO Expliquer le fonctionnement du constructeur
	 * 
	 * @param gestion
	 */
	public P_caisseADessiner(JeuRainbow jeu) {
		super();
		partieCourante = jeu.getPartieCourante();
		partieCourante.getRobot().addObserver(this);
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
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		// System.out.println("Partie courante : " + partieCourante);
		super.paintComponent(g);

		Graphics2D contexte = (Graphics2D) g.create();

		// ---------------------------------------------------------------------
		// On dessine le fond du Panneau
		// ---------------------------------------------------------------------
		// On dessine le fond de la 1ère caisse
		contexte.setColor(UtilitaireFenetre.COULEUR_FOND_CAISSE_UN);
		contexte.fillRect(0, 0, UtilitaireFenetre.DIM_CAISSE_RECUPEREE.width,
				UtilitaireFenetre.DIM_CAISSE_RECUPEREE.height);

		// On dessine le fond dest autres caisses
		contexte.setColor(UtilitaireFenetre.COULEUR_FOND_CAISSE);
		contexte.fillRect(UtilitaireFenetre.DIM_CAISSE_RECUPEREE.width, 0,
				this.getWidth() - UtilitaireFenetre.DIM_CAISSE_RECUPEREE.width,
				UtilitaireFenetre.DIM_CAISSE_RECUPEREE.height);

		// On dessine la bordure principale
		contexte.setStroke(new BasicStroke(
				UtilitaireFenetre.LARGEUR_BORDURE_CAISSE));
		contexte.setColor(UtilitaireFenetre.COULEUR_BORDURE);
		contexte.drawRect(0, 0, this.getWidth(), this.getHeight());

		// On dessine la ligne séparatrice
		contexte.setColor(UtilitaireFenetre.COULEUR_BORDURE);
		contexte.drawLine(UtilitaireFenetre.DIM_CAISSE_RECUPEREE.width, 0,
				UtilitaireFenetre.DIM_CAISSE_RECUPEREE.width,
				UtilitaireFenetre.DIM_CAISSE_RECUPEREE.height);

		// ---------------------------------------------------------------------
		// On dessine les caisses a récupérées
		// On affiche seulement les 5 premières caisses
		// ---------------------------------------------------------------------
		ArrayList<Caisse> aDessiner = partieCourante.getCaisseARecuperee();
		// Contexte graphique de la caisse a dessiné
		Graphics2D contexteCaisse;
		for (int i = 0; i < UtilitaireFenetre.NB_CAISSE_AFFICHE
				&& i < aDessiner.size(); i++) {
			int abscisseCaisse = (i * ((UtilitaireFenetre.DIM_CAISSE_RECUPEREE.width) + UtilitaireFenetre.MARGE_ENTRE_CAISSE))
					+ ((UtilitaireFenetre.DIM_CAISSE_RECUPEREE.width / 2) - (UtilitaireFenetre.DIM_CAISSE.width / 2));
			int ordonneCaisse = ((UtilitaireFenetre.DIM_CAISSE_RECUPEREE.height / 2) - (UtilitaireFenetre.DIM_CAISSE.height / 2));
			contexteCaisse = (Graphics2D) g.create(abscisseCaisse,
					ordonneCaisse, UtilitaireFenetre.DIM_CAISSE.width,
					UtilitaireFenetre.DIM_CAISSE.height);
			aDessiner.get(i).dessiner(contexteCaisse);
		}
		contexte.dispose();
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
