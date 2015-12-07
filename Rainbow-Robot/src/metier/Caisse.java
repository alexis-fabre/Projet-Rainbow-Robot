/*
 * Caisse.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package metier;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import vue.UtilitaireFenetre;

/**
 * Classe gérant les caisses présentent sur la carte et dans les commandes que
 * devra effectuer le joueur via le robot
 * 
 * Classe gérant les caisses présentent sur la carte et dans les commandes que
 * devra effectuer le joueur via le robot
 * 
 * @author Rainbow Robot
 */
public class Caisse implements Dessinable {

	/** Couleur de la caisse */
	private Color couleur;

	/** Position de la Caisse sur la carte */
	private Position pos_courante;

	public static final Color[] COULEUR_AUTORISEE = { Color.RED, Color.YELLOW,
			Color.MAGENTA, Color.GREEN, Color.BLUE, Color.ORANGE };

	public static final String[] CHEMIN_IMAGE_CAISSE = { "./img/CaseRouge.PNG",
			"./img/CaseJaune.PNG", "./img/CaseViolette.PNG",
			"./img/CaseVerte.PNG", "./img/CaseBleue.PNG",
			"./img/CaseOrange.PNG" };

	/**
	 * Constructeur initialisant une caisse en lui attribuant une couleur
	 * 
	 * @param couleur
	 *            la couleur de la caisse à sa création
	 */
	public Caisse(Color couleur) {
		this.couleur = couleur;
	}

	/**
	 * Création d'une caisse en lui attribuant une couleur et une position sur
	 * la carte
	 * 
	 * @param couleur
	 *            la couleur de la caisse
	 * @param p_initial
	 *            position intiale de la caisse sur la carte
	 */
	public Caisse(Color couleur, Position p_initial) {
		this.couleur = couleur;
		this.pos_courante = p_initial;
	}

	/**
	 * TODO Cas d'erreur mal géré et méthode à finir
	 * 
	 * Fusion des couleurs des deux caisses lorsque le robot effectuera une
	 * fusion de caisse
	 * 
	 * @param c1
	 *            première caisse à fusionner tenue par le robot
	 * @param c2
	 *            deuxième caisse à fusionner
	 * @return c3 la caisse de nouvelle couleur
	 */
	public static Caisse fusionCouleur(Caisse c1, Caisse c2) {
		// tableau pour récupérer les valeurs RVB définissant la couleur de c1
		float tabc1[] = new float[3];
		// tableau pour récupérer les valeurs RVB définissant la couleur de c2
		float tabc2[] = new float[3];
		// tableau contenant les valeurs RVB après la synthèse additive
		float tabc3[] = new float[3];

		Caisse c3; // nouvelle caisse à retourner

		c1.getCouleur().getRGBColorComponents(tabc1);
		c2.getCouleur().getRGBColorComponents(tabc2);

		if ((c1.getCouleur()).equals(c2.getCouleur())) {
			System.out.println("impossible de fusionner "
					+ "deux caisses de même couleur");
		} else {
			for (int i = 0; i < tabc1.length; i++) {
				tabc3[i] = tabc1[i] + tabc2[i];
			}
			c3 = new Caisse(new Color(tabc3[0], tabc3[1], tabc3[2]));
		}
		return c2;
	}

	/**
	 * Accesseur sur la couleur de la caisse
	 * 
	 * @return couleur la couleur de la caisse
	 */
	public Color getCouleur() {
		return couleur;

	}

	/**
	 * Modification de la couleur de caisse par une nouvelle couleur
	 * 
	 * @param couleur
	 *            la nouvelle couleur de la caisse
	 */
	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}

	/**
	 * Vérifie si une couleur peut être dessiné car l'application ne permet pas
	 * encore de pouvoir dessiner toutes les couleurs des caisses.
	 * 
	 * @param aVerifier
	 *            couleur a vérifiée
	 * @return true si la couleur appartient aux couleurs autorisées faux sinon
	 */
	public static boolean isColorOK(Color aVerifier) {
		boolean couleurOK = false;
		for (int i = 0; i < COULEUR_AUTORISEE.length && !couleurOK; i++) {
			if (aVerifier.equals(COULEUR_AUTORISEE[i])) {
				couleurOK = true;
			}
		}
		return couleurOK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see metier.Dessinable#dessiner(java.awt.Graphics2D)
	 */
	@Override
	public void dessiner(Graphics2D g) {
		boolean couleurTrouvee = false;
		for (int i = 0; i < COULEUR_AUTORISEE.length && !couleurTrouvee; i++) {
			if (couleur.equals(COULEUR_AUTORISEE[i])) {
				// dessiner
				couleurTrouvee = true;
				try {
					g.drawImage(ImageIO.read(new File(CHEMIN_IMAGE_CAISSE[i])),
							0, 0, UtilitaireFenetre.DIM_CAISSE.width,
							UtilitaireFenetre.DIM_CAISSE.height, null);
				} catch (IOException e) {
					System.out.println("Caisse : dessiner : "
							+ "Chemin introuvable");
				}
			}
		}
	}

	/**
	 * Retourne la position de la caisse
	 * 
	 * @return pos_courante la position courante
	 */
	public Position getPosCaisse() {
		return pos_courante;
	}

	/**
	 * Créer une liste de caisse pour que l'utilisateur puisse les recuperer
	 * dans sa partie
	 * 
	 * @param nbCaisse
	 *            nombre de caisses a récuperer
	 */
	public static void CaisseARecuperer(ArrayList<Caisse> caisseArecup,
			int nbCaisse, Color couleur) {
		for (int i = 0; i < nbCaisse; i++)
			caisseArecup.add(new Caisse(couleur, new Position(2, 0)));
	}

}
