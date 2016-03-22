/*
 * PartieDessinable.java							6 déc. 2015
 * IUT Info2 2015-2016
 */
package vue;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import metier.Partie;
import metier.Robot;
import metier.Vortex;

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
	private ThreadAnimation threadDeplacement, threadAspiration;

	/** Le robot est en train de se déplacer (animationRobot) */
	private volatile boolean isAnimationRobot;

	/** Le vortex est en train d'aspirer une caisse */
	private volatile boolean isAnimationVortex;

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
		// On définit une taille par défaut
		super.setPreferredSize(new Dimension(
				UtilitaireFenetre.DIM_FENETRE.width / 2,
				UtilitaireFenetre.DIM_FENETRE.height));
		// abonner cette vue aux changements du modèle (DP observateur)
		setPartieCourante(partie);
		isAnimationRobot = false;
		isAnimationVortex = false;
	}

	/**
	 * Réactualise la partie courante envoyé dans JeuRainbow
	 * 
	 * @param nouvellePartie
	 *            la partie actualisée
	 */
	public void setPartieCourante(Partie nouvellePartie) {
		this.partieCourante = nouvellePartie;
		partieCourante.getRobot().addObserver(this);
		partieCourante.getVortex().addObserver(this);
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

		final int x = (super.getWidth() - largeurPlateau) / 2;
		final int y = (super.getHeight() - hauteurPlateau) / 2;
		Graphics2D contexte = (Graphics2D) g.create(x, y, largeurPlateau,
				hauteurPlateau);

		partieCourante.dessiner(contexte);

		// Animation du vortex
		if (isAnimationVortex) {
			partieCourante.getVortex().animationAbsorption(contexte);
		}

		// Animation du robot
		if (!isAnimationRobot) {
			// On dessine le robot et le plateau à sa position finale
			partieCourante.getRobot().dessiner(contexte);
		} else {
			// On dessine le robot à sa position à l'instant t
			partieCourante.getRobot().animationDeplacement(contexte);
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
		isAnimationRobot = true;
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
		isAnimationRobot = false;
		repaint();
		robot.setEstOccupe(false);
	}

	/** Simule l'animation d'une caisse qui se fait aspirer dans le vortex */
	public void animationAspiration() {
		isAnimationVortex = true;
		// Temps nominal pour l'animation en millisecondes
		final double TEMPS_NOMINAL_TOTAL = Vortex.TEMPS_ABSORPTION * 1000;
		// Temps en début et fin pour gérer les dépassements de temps
		long debutboucle, finboucle;
		// Temps réel mis pour faire un tour de boucle
		long tempsBoucle = PAUSE_ANIMATION;

		// Taux de diminution de la caisse
		double echelleCaisse = 0;
		// Rotation de la caisse
		double rotationCaisse = 0;
		// Référence du vortex
		Vortex vortex = partieCourante.getVortex();

		// Mesure du taux de diminution au cours du temps
		float echelle = 0;
		// Mesure de l'angle au cours du temps
		float rotation = 0;
		// Limite pour la rotation
		final double LIMITE_ROTATION = Vortex.NOMBRE_TOUR * 360;

		while (echelle < 1 && rotation < LIMITE_ROTATION) {
			debutboucle = System.currentTimeMillis();

			echelleCaisse = echelleCaisse + (tempsBoucle / TEMPS_NOMINAL_TOTAL);
			rotationCaisse = rotationCaisse
					+ ((LIMITE_ROTATION * tempsBoucle) / TEMPS_NOMINAL_TOTAL);

			// On actualise l'échelle de la caisse
			if (echelleCaisse > 0.01) {

				vortex.addEchelleDessin(-echelleCaisse);

				echelle += ((int) (echelleCaisse * 100)) / 100.0;
				echelleCaisse = ((echelleCaisse * 100) - (int) (echelleCaisse * 100)) / 100.0;
				if (echelle >= 1) {
					break;
				}
			}
			if (rotationCaisse > 1) {

				rotation += (int) rotationCaisse;
				if (rotation > LIMITE_ROTATION) {
					break;
				}

				vortex.addAngleDessin((int) rotationCaisse);
				rotationCaisse -= (int) rotationCaisse;

			}

			repaint();

			try {
				Thread.sleep(PAUSE_ANIMATION);
			} catch (InterruptedException e) {
			}

			finboucle = System.currentTimeMillis();
			tempsBoucle = finboucle - debutboucle;
		}

		// Fin de l'animation
		isAnimationVortex = false;
		vortex.resetDessin();
		repaint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object aRedessiner) {
		if (o instanceof Robot) {
			if (threadDeplacement == null || !threadDeplacement.isAlive()) {
				threadDeplacement = new ThreadAnimation(
						ThreadAnimation.ANIMATION_DEPLACEMENT);
				threadDeplacement.start();
			}
		}
		if (o instanceof Vortex) {
			if (threadAspiration == null || !threadAspiration.isAlive()) {
				threadAspiration = new ThreadAnimation(
						ThreadAnimation.ANIMATION_VORTEX);
				threadAspiration.start();
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

		/**
		 * On force la fenêtre à dessiner les animations de déplacements du
		 * robot et de sa caisse
		 */
		public static final int ANIMATION_DEPLACEMENT = 1;

		/**
		 * On force la fenêtre à dessiner les animations de l'aspiration d'une
		 * caisse par le vortex
		 */
		public static final int ANIMATION_VORTEX = 2;

		/**
		 * Permet de savoir quelles animations le thread doit lancer. On double
		 * la référence car deux Thread peuvent la modifier en même temps, ainsi
		 * on est sur du résultat.
		 * 
		 * @see vue.P_partieDessinable.ThreadAnimation#ANIMATION_DEPLACEMENT
		 * @see vue.P_partieDessinable.ThreadAnimation#ANIMATION_VORTEX
		 */
		private final int animation;

		/** Constructeur pour l'animation d'un déplacement du robot */
		public ThreadAnimation(int animation) {
			this.animation = animation;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// Animation du déplacement du robot
			if (animation == ANIMATION_DEPLACEMENT) {
				animationRobot();
			} else { // Animation de l'aspiration du vortex
				animationAspiration();
			}
		}
	}
}
