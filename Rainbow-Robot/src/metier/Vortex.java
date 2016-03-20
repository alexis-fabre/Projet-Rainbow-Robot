/*
 * Vortex.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package metier;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.List;
import java.util.Observable;

import vue.UtilitaireFenetre;

/**
 * Classe gérant le vortex qui est uniquement défini par une position initiale
 * (il ne peut pas se déplacer). Le vortex est utilisé dans un autre Thread pour
 * qu'il puisse absorbé des caisses sans pour autant dérangé le déplacement du
 * robot ou d'autre action faites par celui-ci.
 *
 * @author Rainbow Robot
 */
public class Vortex extends Observable implements Serializable, Runnable {

	/**
	 * Générer automatiquement par Eclipse
	 */
	private static final long serialVersionUID = 6297297964624363269L;

	/** Pause en millisecondes entre chaques vérification */
	public static final int PAUSE = 50;

	/** Position initiale du vortex */
	private Position pos_vortex;

	/** Temps que doit mettre l'absorption (en seconde) */
	public static final float TEMPS_ABSORPTION = 3f;

	/** Nombre de tour complet que devra faire la caisse avant de disparaître */
	public static final float NOMBRE_TOUR = 2f;

	/** Référence de la partie courante */
	private Partie partie;

	/** Caisse qui se fait absorbé par le vortex. Utiliser pour l'animation. */
	private Caisse aAbsorbe;

	/** Il permet de faire tourner la caisse lorsque le vortex absorbe celle-ci */
	private int angleDessin;

	/** Il permet de faire réduire la taille de la caisse lors de l'animation */
	private double echelleDessin;

	/** Permet de savoir si le vortex est en cours d'animation. */
	private boolean estOccupe;

	/**
	 * Construit un vortex avec sa position initiale
	 * 
	 * @param pos
	 *            position initiale du vortex
	 */
	public Vortex(Position pos) {
		pos_vortex = pos;
		resetDessin();
	}

	/**
	 * @return pos_courante la position courante du vortex
	 */
	public Position getPosVortex() {
		return pos_vortex;
	}

	/**
	 * @param partie
	 *            la partie à modifier
	 */
	public void setPartie(Partie partie) {
		this.partie = partie;
	}

	/**
	 * <p>
	 * Vérifie si le vortex peut absorber la caisse. C'est à dire si la caisse à
	 * la même couleur que celle demandée et que le robot ne la charge pas. <br />
	 * Si c'est le cas, on fait disparaître la caisse et on actualise la
	 * nouvelle caisse a récupéré.
	 * </p>
	 * 
	 * @return true si la partie est finie, false sinon.
	 */
	public boolean vortexAspire() {
		List<Caisse> caisseARecuperee = partie.getCaisseARecuperee();
		// On vérifie s'il reste des caisses à récupérées
		if (caisseARecuperee.isEmpty()) {
			// On ne finit la partie qu'à la fin de l'animation du vortex
			if (!estOccupe) {
				partie.partieFinie();
				return false;
			}
		} else {
			Caisse[] caissePlateau = partie.getCaissePlateau();
			Robot robot = partie.getRobot();
			// On parcours les caisses sur le plateau de jeu et on vérifie si
			// l'une
			// d'elle est positionné sur le vortex et si elle est de la même
			// couleur
			// que celle demandée dans la liste

			for (int i = 0; i < caissePlateau.length; i++) {
				if (caissePlateau[i] != null
						&& caissePlateau[i].getPosCaisse().equals(pos_vortex)
						&& caissePlateau[i].getCouleur() == caisseARecuperee
								.get(0).getCouleur()
						&& !caissePlateau[i].equals(robot.getCaisse())) {
					// On garde la référence de la caisse
					aAbsorbe = caissePlateau[i];
					// On fait disparaître la caisse
					caissePlateau[i] = null;
					// On réactualise les caisses a récupéré
					caisseARecuperee.remove(0);
					// On averti les Observers qu'une caisse peut être aspiré
					estOccupe = true;
					setChanged();
					notifyObservers();
				}
			}
		}
		return true;
	}

	/**
	 * @param addAngleDessin
	 *            le angleDessin à modifier
	 */
	public void addAngleDessin(int addAngleDessin) {
		this.angleDessin += addAngleDessin;
	}

	/**
	 * @param addEchelleDessin
	 *            le echelleDessin à modifier
	 */
	public void addEchelleDessin(double addEchelleDessin) {
		this.echelleDessin += addEchelleDessin;
	}

	/** Reset les variables utilisées pour l'animation */
	public void resetDessin() {
		angleDessin = 0;
		echelleDessin = 1;
		estOccupe = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Vortex((Position) pos_vortex.clone());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (vortexAspire()) {
			try {
				Thread.sleep(PAUSE);
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * Permet d'effectuer l'animation de l'absorption d'une caisse par le vortex
	 * 
	 * @param g
	 *            contexte graphique 2D
	 */
	public void animationAbsorption(Graphics2D g) {
		// Permet d'effectuer des opérations de transformation sur des contextes
		// graphiques
		AffineTransform transform = new AffineTransform();

		int abscisseCaisse = aAbsorbe.getPosCaisse().getX()
				* UtilitaireFenetre.DIM_CASE_VIDE.width
				+ ((UtilitaireFenetre.DIM_CASE_VIDE.width / 2) - (UtilitaireFenetre.DIM_CAISSE.width / 2));
		int ordonneCaisse = aAbsorbe.getPosCaisse().getY()
				* UtilitaireFenetre.DIM_CASE_VIDE.height
				+ ((UtilitaireFenetre.DIM_CASE_VIDE.height / 2) - (UtilitaireFenetre.DIM_CAISSE.height / 2));
		Graphics2D contexteCaisse = (Graphics2D) g
				.create((int) (abscisseCaisse + ((UtilitaireFenetre.DIM_CASE_VIDE.width * (1 - echelleDessin)) / 2)),
						(int) (ordonneCaisse + ((UtilitaireFenetre.DIM_CASE_VIDE.height * (1 - echelleDessin)) / 2)),
						UtilitaireFenetre.DIM_CAISSE.width,
						UtilitaireFenetre.DIM_CAISSE.height);

		// On définit un sens de rotation est une échelle
		transform.scale(echelleDessin, echelleDessin);
		transform.rotate((angleDessin * Math.PI) / 180,
				UtilitaireFenetre.DIM_CAISSE.width / 2,
				UtilitaireFenetre.DIM_CAISSE.height / 2);

		contexteCaisse.transform(transform);
		aAbsorbe.dessiner(contexteCaisse);
		contexteCaisse.dispose();
	}
}
