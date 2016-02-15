/*
 * PartieDessinable.java							6 déc. 2015
 * IUT Info2 2015-2016
 */
package vue;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import metier.Partie;
import metier.Robot;

/**
 * Panneau qui dessine et contrôle une partie de jeu Rainbow Robot. On dessine
 * les composants d'une partie.
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class P_partieDessinable extends JPanel implements Observer {

	/** Généré automatiquement par Eclipse */
	private static final long serialVersionUID = -1455979274616855880L;

	/** Partie courante du jeu Rainbow Robot */
	private Partie partieCourante;

	/** Temps passé entre deux animation en (millisecondes) */
	private static final int PAUSE_ANIMATION = 1;

	/**
	 * Thread gérant le rafraichissement de la fenêtre lorsque l'utilisateur
	 * fait une action dans le jeu.
	 */
	private ThreadAnimation threadAnimation;

	/**
	 * Permet de savoir si la fenêtre est en train d'effectuer une animation ou
	 * non
	 */
	private boolean isAnimation;

	/**
	 * Initialise la partie courante au travers de la référence du jeu. On
	 * ajoute un Observer pour savoir quand est-ce que le robot fait une action
	 * et des qu'il en fait une, on redessine le plateau de jeu.
	 * 
	 * @param partie
	 */
	public P_partieDessinable(Partie partie) {
		super();
		// Permet d'éviter quelques latences
		super.setDoubleBuffered(true);
		// abonner cette vue aux changements du modèle (DP observateur)
		partieCourante = partie;
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
		super.paintComponent(g);
		// Nouvelle position pour centrer le jeu dans le JPanel
		final int largeurPlateau = ((UtilitaireFenetre.DIM_CASE_VIDE.width + (int) UtilitaireFenetre.LARGEUR_BORDURE) * //
		partieCourante.getNbColonne());
		final int hauteurPlateau = ((UtilitaireFenetre.DIM_CASE_VIDE.height + (int) UtilitaireFenetre.LARGEUR_BORDURE) * //
		partieCourante.getNbLigne());

		final int x = (super.getWidth() / 2) - (largeurPlateau / 2);
		final int y = (super.getHeight() / 2) - (hauteurPlateau / 2);
		Graphics2D contexte = (Graphics2D) g.create(x, y, largeurPlateau,
				hauteurPlateau);

		// Animation du robot
		if (isAnimation) {
			partieCourante.dessiner(contexte);
			partieCourante.getRobot().animation(contexte);
		} else {
			// On dessine le robot et le plateau à sa position finale
			partieCourante.dessiner(contexte);
			partieCourante.getRobot().dessiner(contexte);
		}

		// On libère les ressources
		contexte.dispose();
		g.dispose();
	}

	/**
	 * Animation du robot
	 * 
	 * @param contexte
	 *            contexte graphique 2D
	 */
	public void animationRobot() {
		isAnimation = true;
		// Déplacement du robot (en pixel)
		float deplacementPixel = 0;
		// Référence du robot
		Robot robot = partieCourante.getRobot();
		// Temps que doit mettre l'animation
		final float TEMPS_PAUSE_NOMINAL = robot.tempsDerniereAction() * 1000;
		// Temps en début et fin pour gérer les dépassements de temps
		long debutboucle, finboucle;
		// Temps mis pour effectuer un tour de boucle
		long tempsMis = PAUSE_ANIMATION;
		// Mesure l'évolution (de l'angle ou de la distance) du robot au cours
		// du temps
		long deplacementRobot = 0;

		// Animation
		switch (robot.getDerniereAction()) {
		case Robot.ACTION_AVANCER:// Cas similaire à ACTION_RECULER
		case Robot.ACTION_RECULER:

			// On récupère toutes les variables nécessaires pour connaître
			// l'évolution du robot
			// Utilisé pour connaître le sens de direction du robot
			int deltaX = robot.getAbscisseDessinMax()
					- robot.getAbscisseDessin();
			int deltaY = robot.getOrdonneeDessinMax()
					- robot.getOrdonneeDessin();

			// Sachant que le robot se déplace dans un sens unilatéral, s'il se
			// déplace selon le sens deltaX alors deltaY = 0 et inversement

			if (deltaX > 0) { // Axe Horizontal et vers la droite

				final int distanceArrivee = UtilitaireFenetre.DIM_CASE_VIDE.width;

				while (deplacementRobot < distanceArrivee) {
					
					debutboucle = System.currentTimeMillis();
					deplacementPixel = deplacementPixel
							+ ((deltaX * tempsMis) / TEMPS_PAUSE_NOMINAL);

					if (deplacementPixel >= 1.0f) {
						deplacementRobot += (int) deplacementPixel;

						// Du à des latences imprévisibles
						if (deplacementRobot >= distanceArrivee) {
							break;
						}

						robot.addAbscisseDessin((int) deplacementPixel);
						deplacementPixel = deplacementPixel
								- (int) deplacementPixel;

						// On redessine le robot
						repaint();
					}

					// Pause entre les animations
					try {
						Thread.sleep(PAUSE_ANIMATION);
					} catch (InterruptedException e) {
					}

					finboucle = System.currentTimeMillis();
					tempsMis = finboucle - debutboucle;
				}
			} else if (deltaX < 0) { // Axe Horizontal et vers la gauche

				final int distanceArrivee = UtilitaireFenetre.DIM_CASE_VIDE.width;

				while (deplacementRobot < distanceArrivee) {

					debutboucle = System.currentTimeMillis();
					deplacementPixel = deplacementPixel
							+ ((deltaX * tempsMis) / TEMPS_PAUSE_NOMINAL);

					if (deplacementPixel <= -1.0f) {

						deplacementRobot += -((int) deplacementPixel);

						if (deplacementRobot >= distanceArrivee) {
							break;
						}

						robot.addAbscisseDessin((int) deplacementPixel);
						deplacementPixel = deplacementPixel
								- (int) deplacementPixel;

						repaint();
					}

					try {
						Thread.sleep(PAUSE_ANIMATION);
					} catch (InterruptedException e) {
					}

					finboucle = System.currentTimeMillis();
					tempsMis = finboucle - debutboucle;
				}
			} else if (deltaY > 0) { // Axe vertical et vers le bas

				final int distanceArrivee = UtilitaireFenetre.DIM_CASE_VIDE.height;

				while (deplacementRobot < distanceArrivee) {

					debutboucle = System.currentTimeMillis();
					deplacementPixel = deplacementPixel
							+ ((deltaY * tempsMis) / TEMPS_PAUSE_NOMINAL);

					if (deplacementPixel >= 1.0f) {

						deplacementRobot += (int) deplacementPixel;

						if (deplacementRobot >= distanceArrivee) {
							break;
						}

						robot.addOrdonneeDessin((int) deplacementPixel);
						deplacementPixel = deplacementPixel
								- (int) deplacementPixel;

						repaint();
					}

					try {
						Thread.sleep(PAUSE_ANIMATION);
					} catch (InterruptedException e) {
					}

					finboucle = System.currentTimeMillis();
					tempsMis = finboucle - debutboucle;
				}
			} else { // Axe vertical et vers le haut <=> deltaY < 0

				final int distanceArrivee = UtilitaireFenetre.DIM_CASE_VIDE.height;

				while (deplacementRobot < distanceArrivee) {

					debutboucle = System.currentTimeMillis();
					deplacementPixel = deplacementPixel
							+ ((deltaY * tempsMis) / TEMPS_PAUSE_NOMINAL);
					if (deplacementPixel <= -1.0f) {

						deplacementRobot += -((int) deplacementPixel);

						if (deplacementRobot >= distanceArrivee) {
							break;
						}

						robot.addOrdonneeDessin((int) deplacementPixel);
						deplacementPixel = deplacementPixel
								- (int) deplacementPixel;

						repaint();
					}

					try {
						Thread.sleep(PAUSE_ANIMATION);
					} catch (InterruptedException e) {
					}

					finboucle = System.currentTimeMillis();
					tempsMis = finboucle - debutboucle;
				}
			}
			break;
		case Robot.ACTION_PIVOTER:
			if (robot.sensPivoter() == Robot.PIVOTER_DROITE) { // Rotation à
				// droite
				while (deplacementRobot < Robot.FACTEUR_TRANSFORMATION_ORIENTATION_ANGLE) {

					debutboucle = System.currentTimeMillis();
					deplacementPixel = deplacementPixel
							+ (Robot.FACTEUR_TRANSFORMATION_ORIENTATION_ANGLE * (tempsMis / TEMPS_PAUSE_NOMINAL));

					if (deplacementPixel >= 1.0f) {

						deplacementRobot += (int) deplacementPixel;

						if (deplacementRobot >= Robot.FACTEUR_TRANSFORMATION_ORIENTATION_ANGLE) {
							break;
						}

						robot.addAngleDessin((int) deplacementPixel);
						deplacementPixel = deplacementPixel
								- (int) deplacementPixel;

						repaint();
					}

					try {
						Thread.sleep(PAUSE_ANIMATION);
					} catch (InterruptedException e) {
					}

					finboucle = System.currentTimeMillis();
					tempsMis = finboucle - debutboucle;
				}
			} else { // Rotation à gauche

				while (deplacementRobot < Robot.FACTEUR_TRANSFORMATION_ORIENTATION_ANGLE) {

					debutboucle = System.currentTimeMillis();
					deplacementPixel = deplacementPixel
							- (Robot.FACTEUR_TRANSFORMATION_ORIENTATION_ANGLE * (tempsMis / TEMPS_PAUSE_NOMINAL));

					if (deplacementPixel <= -1.0f) {

						deplacementRobot += -((int) deplacementPixel);

						if (deplacementRobot >= Robot.FACTEUR_TRANSFORMATION_ORIENTATION_ANGLE) {
							break;
						}

						robot.addAngleDessin((int) deplacementPixel);
						deplacementPixel = deplacementPixel
								- (int) deplacementPixel;

						repaint();
					}

					try {
						Thread.sleep(PAUSE_ANIMATION);
					} catch (InterruptedException e) {
					}

					finboucle = System.currentTimeMillis();
					tempsMis = finboucle - debutboucle;
				}
			}
			break;
		}

		// Fin de l'animation
		isAnimation = false;
		repaint();
		robot.setEstOccupe(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object aRedessiner) {
		if (aRedessiner instanceof Robot) {
			if (threadAnimation == null || !threadAnimation.isAlive()) {
				threadAnimation = new ThreadAnimation();
				threadAnimation.start();
			}
		}
	}

	/**
	 * Gestion de l'animation en parallèle pour ne pas perturber le
	 * fonctionnement des autres composants graphiques.
	 * 
	 * @author Rainbow Robot
	 * @version 1.0
	 */
	public class ThreadAnimation extends Thread {

		/** Constructeur par défaut */
		public ThreadAnimation() {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			animationRobot();
		}
	}

}
