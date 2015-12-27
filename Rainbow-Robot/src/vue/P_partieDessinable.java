/*
 * PartieDessinable.java							6 déc. 2015
 * IUT Info2 2015-2016
 */
package vue;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;
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
public class P_partieDessinable extends JPanel implements Observer {

	/**
	 * Généré automatiquement par Eclipse
	 */
	private static final long serialVersionUID = -1455979274616855880L;
	/**
	 * Partie courante du jeu Rainbow Robot
	 */
	Partie partieCourante;

	/**
	 * Initialise la partie courante au travers de la référence du jeu. On
	 * ajoute un Observer pour savoir quand est-ce que le robot fait une action
	 * et des qu'il en fait une, on redessine le plateau de jeu.
	 * 
	 * @param gestion
	 */
	public P_partieDessinable(JeuRainbow jeu) {
		super();
		// abonner cette vue aux changements du modèle (DP observateur)
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

	/**
	 * Permet d'avoir une animation lorsque le robot se déplace
	 * 
	 * @param g
	 *            contexte de dessin
	 */
	public void animation(Graphics g) {
		Robot robot = partieCourante.getRobot();

		// Nouvelle position pour centrer le jeu dans le JPanel
		final int x = (super.getWidth() / 2)
				- (((UtilitaireFenetre.DIM_CASE_VIDE.width + (int) UtilitaireFenetre.LARGEUR_BORDURE) * //
				partieCourante.getNbColonne()) / 2);
		final int y = (super.getHeight() / 2)
				- (((UtilitaireFenetre.DIM_CASE_VIDE.height + (int) UtilitaireFenetre.LARGEUR_BORDURE) * //
				partieCourante.getNbLigne()) / 2);
		Graphics2D contexte = (Graphics2D) g.create(x, y, super.getWidth() - x,
				super.getHeight() - y);

		switch (robot.getDerniereAction()) {
		case Robot.ACTION_AVANCER:// Cas similaire à ACTION_RECULER
		case Robot.ACTION_RECULER:
			// On récupère toutes les variables nécessaires pour connaître
			// l'évolution du robot
			// Utilisé pour connaître le sens de direction du robot
			final int deltaX = robot.getAbscisseDessinMax()
					- robot.getAbscisseDessin();
			final int deltaY = robot.getOrdonneeDessinMax()
					- robot.getOrdonneeDessin();
			// Sachant que le robot se déplace dans un sens unilatéral, s'il se
			// déplace selon le sens deltaX alors deltaY = 0
			if (deltaX > 0) { // Axe Horizontal et vers la droite
				final int abscisseArrivee = robot.getAbscisseDessinMax();
				super.paintComponent(g);
				while (robot.getAbscisseDessin() < abscisseArrivee) {
					robot.addAbscisseDessin(1);
					robot.addAbscisseDessinCaisse(1);
					partieCourante.dessiner(contexte);
					partieCourante.getRobot().animation(contexte);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else if (deltaX < 0) { // Axe Horizontal et vers la gauche
				final int abscisseArrivee = robot.getAbscisseDessinMax();
				super.paintComponent(g);
				while (robot.getAbscisseDessin() > abscisseArrivee) {
					robot.addAbscisseDessin(-1);
					robot.addAbscisseDessinCaisse(-1);
					partieCourante.dessiner(contexte);
					partieCourante.getRobot().animation(contexte);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else if (deltaY > 0) { // Axe vertical et vers le bas
				final int ordonneeArrivee = robot.getOrdonneeDessinMax();
				super.paintComponent(g);
				while (robot.getOrdonneeDessin() < ordonneeArrivee) {
					robot.addOrdonneeDessin(1);
					robot.addOrdonneeDessinCaisse(1);
					partieCourante.dessiner(contexte);
					partieCourante.getRobot().animation(contexte);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else { // Axe vertical et vers le haut <=> deltaY < 0
				final int ordonneeArrivee = robot.getOrdonneeDessinMax();
				super.paintComponent(g);
				while (robot.getOrdonneeDessin() > ordonneeArrivee) {
					robot.addOrdonneeDessin(-1);
					robot.addOrdonneeDessinCaisse(-1);
					partieCourante.dessiner(contexte);
					partieCourante.getRobot().animation(contexte);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			break;
		case Robot.ACTION_PIVOTER:
			final int orientationDepart = Robot.angleToOrientation(robot
					.getAngleDessin());
			final int orientationArrivee = Robot.angleToOrientation(robot
					.getAngleDessinMax());
			System.out.println("Angle départ : " + orientationDepart
					+ "\nAngle d'arrivée : " + orientationArrivee);
			if (robot.sensPivoter() == Robot.PIVOTER_DROITE) { // Rotation à
																// droite
				final int angleArrivee = robot.getAngleDessinMax();
				while (robot.getAngleDessin() != angleArrivee) {
					robot.addAngleDessin(1);
					partieCourante.dessiner(contexte);
					partieCourante.getRobot().animation(contexte);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			} else { // Rotation à gauche
				final int angleArrivee = robot.getAngleDessinMax();
				while (robot.getAngleDessin() != angleArrivee) {
					robot.addAngleDessin(-1);
					partieCourante.dessiner(contexte);
					partieCourante.getRobot().animation(contexte);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			break;
		}
		// Sinon aucune action n'a été faite par le robot

		// On dessine le robot et le plateau à sa position finale
		super.paintComponent(g);
		partieCourante.dessiner(contexte);
		partieCourante.getRobot().dessiner(contexte);
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
				partieCourante.getNbColonne()) / 2);
		int y = (super.getHeight() / 2)
				- (((UtilitaireFenetre.DIM_CASE_VIDE.height + (int) UtilitaireFenetre.LARGEUR_BORDURE) * //
				partieCourante.getNbLigne()) / 2);

		Graphics2D contexte = (Graphics2D) g.create(x, y, super.getWidth() - x,
				super.getHeight() - y);

		// On dessine le robot et le plateau à sa position finale
		partieCourante.dessiner(contexte);
		partieCourante.getRobot().dessiner(contexte);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object aRedessiner) {
		if (aRedessiner instanceof Robot) {
			animation(getGraphics());
		}
	}
}
