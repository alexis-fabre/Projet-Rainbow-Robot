/*
 * Partie.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package metier;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;	
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

import javax.imageio.ImageIO;

import vue.UtilitaireFenetre;

/**
 *
 * @author Rainbow Robot
 */
public class Partie extends Observable implements Dessinable {

	/** Nombre colonne de la carte */
	private int nbColonne;

	/** Nombre de lignes de la carte */
	private int nbLigne;

	/**
	 * Abscisse minimale que peut prendre une position. finX = -debutX
	 */
	private int debutX;

	/**
	 * Ordonnée minimale que peut prendre une position. finY = -debutY
	 */
	private int debutY;

	/** Robot sur la carte */
	private Robot robot ;
	

	/** Tableau de caisse */
	private Caisse[] caisses;
	
	/** Niveau de la partie */
	private int niveau;

	/** Vortex de la carte */
	private Vortex vortex;


	/** caisse à recuperer pour finir une partie */
	private ArrayList<Caisse> caisseARecup = new ArrayList<Caisse>();

	/**
	 * Position ou le robot ne pourra pas se déplacer. On ne représente que le
	 * carré en bas à gauche. On obtient les autres carré en récupérant la
	 * valeur absolue des X, des Y et des deux en mêmes temps.
	 */
	public Position[] positionsInaccessibles;

	/**
	 * Créer une carte avec un niveau donné
	 */
	public Partie(int niveau) {


		// X = -5..-4 Y = 3..4 OU X = -5..-4 Y = -4..-3 OU X = 4..5 Y = 3..4 OU
		// X = 4..5 Y = -4..-3

		vortex = new Vortex(new Position(0,0));
		robot = new Robot(Robot.ORIENTATION_GAUCHE,new Position(1,0));

		Caisse.CaisseARecuperer(caisseARecup, 1, Color.RED);

	}

	/**
	 * Créer une carte avec un nombre de nigne et de colonne précis.
	 * 
	 * @param nbLigne
	 *            nombre de ligne que doit faire la carte
	 * @param nbColonne
	 *            nombre de colonne que doit faire la carte
	 * @throws IllegalArgumentException
	 *             si le nombre de ligne est différent de 9 et le nombre de
	 *             colonne est différent de 11.
	 */
	public Partie(int nbLigne, int nbColonne) throws IllegalArgumentException {
		// Si nbLigne != 9 ou nbColonne != 11, on aura des problèmes pour créer
		// les zones inaccessibles (POSITIONS_INACCESSIBLES)
		if (nbLigne != 9 || nbColonne != 11) {
			throw new IllegalArgumentException(
					"La partie doit être crée avec 9 lignes et 11 colonnes "
							+ "sinon, elle ne sera pas correctement gérée");
		}
		this.nbLigne = nbLigne;
		this.nbColonne = nbColonne;
		// Si nbColonne = 11
		// debutX = -(11 - 1 / 2) = -5
		// -debutX = 5
		this.debutX = -((nbColonne - 1) / 2);
		this.debutY = -((nbLigne - 1) / 2);

		// On ne calcule les positions inaccessibles
		positionsInaccessibles = new Position[4];
		positionsInaccessibles[0] = new Position(debutX, debutY);
		positionsInaccessibles[1] = new Position(debutX, debutY + 1);
		positionsInaccessibles[2] = new Position(debutX + 1, debutY);
		positionsInaccessibles[3] = new Position(debutX + 1, debutY + 1);

		// Le robot
		robot = new Robot(Robot.ORIENTATION_GAUCHE, new Position(1, 0));

		// Les caisses à récupérées
		Caisse.CaisseARecuperer(caisseARecup, 1, Color.RED);

		// Le vortex
		vortex = new Vortex(new Position(0, 0));
	}

	/**
	 * Savoir si la partie est fini
	 */
	public boolean isFinished() {
		// lorsque toutes les caisseARecup sont récupérer
		boolean ok = false;

		if(caisseARecup.isEmpty()){
			JeuRainbow.setNiveau(niveau);
			ok = true;
		}
		return ok;
	}

	/**
	 * Vérifie si la position envoyé en paramètre est correcte. Elle renvoie
	 * aussi un message sur la console indiquant la nature du message.
	 * 
	 * @param aVerifier
	 *            position à vérifier
	 * @return true si la position est correcte, false sinon
	 */
	public boolean isPositionOK(Position aVerifier) {
		// On vérifie si la position ne dépasse pas des dimensions de la partie.
		if (aVerifier.getX() < debutX || aVerifier.getX() > -debutX) {
			return false;
		}
		if (aVerifier.getY() < debutY || aVerifier.getY() > -debutY) {
			return false;
		}

		// On vérifie si la position fait partie des position que l'on ne doit
		// pas atteindre (POSITIONS_INACCESSIBLES)
		for (Position inaccessibles : positionsInaccessibles) {
			// On vérifie si x et y négatif
			if (aVerifier.equals(inaccessibles)) {
				return false;
			}
			// On vérifie si x positif et y négatif
			if (aVerifier.equals(new Position(-inaccessibles.getX(),
					inaccessibles.getY()))) {
				return false;
			}
			// On vérifie si x négatif et y positif
			if (aVerifier.equals(new Position(inaccessibles.getX(),
					-inaccessibles.getY()))) {
				return false;
			}
			// On vérifie si x et y positif
			if (aVerifier.equals(new Position(-inaccessibles.getX(),
					-inaccessibles.getY()))) {
				return false;
			}
		}

		// Sinon la position est correcte
		return true;
	}

	/**
	 * Vérifie si les coordonnées envoyées en paramètre sont correctes. La
	 * fonction renvoie aussi un message sur la console indiquant la nature du
	 * message.
	 * 
	 * @param x
	 *            abscisse de la position à vérifier
	 * @param y
	 *            ordonnée de la position à vérifier
	 * @return true si la position est correcte, false sinon
	 */
	public boolean isPositionOK(int x, int y) {
		Position aVerifier = new Position(x, y);
		return isPositionOK(aVerifier);
	}

	/**
	 * @return le nbColonne
	 */
	public int getNbColonne() {
		return nbColonne;
	}

	/**
	 * @return le nbLigne
	 */
	public int getNbLigne() {
		return nbLigne;
	}

	/**
	 * Renvoie le robot
	 * 
	 * @return le robot
	 */
	public Robot getRobot() {
		return robot;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see metier.Dessinable#dessiner(java.awt.Graphics2D)
	 */
	@Override
	public void dessiner(Graphics2D g) {
		for (int y = 0; y < nbLigne; y++) {// Axe des ordonnées
			for (int x = 0; x < nbColonne; x++) { // Axe des abscisses
				Position aDessiner = new Position(x + debutX, y + debutY);
				if (isPositionOK(aDessiner)) {
					if (aDessiner.equals(vortex.getPosVortex())) { // Vortex
						g.setColor(UtilitaireFenetre.COULEUR_VORTEX);
					} else { // Case vide
						g.setColor(UtilitaireFenetre.COULEUR_FOND);
					}

					// On dessine le fond de la case
					g.fillRect(x * UtilitaireFenetre.DIM_CASE_VIDE.width, y
							* UtilitaireFenetre.DIM_CASE_VIDE.height,
							UtilitaireFenetre.DIM_CASE_VIDE.width,
							UtilitaireFenetre.DIM_CASE_VIDE.height);

					// On dessine la bordure de la case
					g.setStroke(new BasicStroke(
							UtilitaireFenetre.LARGEUR_BORDURE));
					g.setColor(UtilitaireFenetre.COULEUR_BORDURE);
					g.drawRect(x * UtilitaireFenetre.DIM_CASE_VIDE.width, y
							* UtilitaireFenetre.DIM_CASE_VIDE.height,
							UtilitaireFenetre.DIM_CASE_VIDE.width,
							UtilitaireFenetre.DIM_CASE_VIDE.height);
				}
			}
		}
	}
}
