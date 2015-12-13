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
 * <p>
 * Panneau contenant l'ensemble des caisses à récupérer pour finir le niveau du
 * jeu. Le panneau est définies par les constantes disponibles dans la classe
 * utilitaire : UtilitaireFenetre. Ces constantes sont :
 * <ul>
 * <li>NB_CAISSE_AFFICHE : Nombre de caisse maximum à afficher dans le panneau</li>
 * <li>COULEUR_FOND_CAISSE_UN : Couleur de fond de la première caisse (celle que
 * l'on doit récupérer).</li>
 * <li>COULEUR_FOND_CAISSE : Couleur de fond des autres caisses.</li>
 * <li>COULEUR_BORDURE : Couleur de la bordure</li>
 * <li>DIM_CAISSE_RECUPEREE : Dimension d'une case dont la caisse sera affiché
 * en son centre</li>
 * <li>DIM_CAISSE : Dimension d'une caisse qui sera centré dans
 * DIM_CAISSE_RECUPEREE</li>
 * </ul>
 * </p>
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class P_caisseADessiner extends JPanel implements Observer {

	/**
	 * Généré automatiquement par Eclipse
	 */
	private static final long serialVersionUID = 2888426363409236951L;
	/**
	 * Partie courante du jeu Rainbow Robot
	 */
	Partie partieCourante;

	/**
	 * Initialise la partie courante au travers de la référence du jeu. On
	 * ajoute un Observer pour savoir quand est-ce que le vortex aspire un
	 * caisse pour ensuite actualiser la liste des caisses à récupérées.
	 * 
	 * @param jeu
	 *            la partie métier du jeu contenant l'ensemble des instructions
	 *            pour faire avancer la partie.
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
