/*
 * Caisse.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package metier;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

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
	private static Color couleur;

	/** Position de la Caisse sur la carte */
	private Position pos_courante;

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

	@Override
	public void dessiner(Graphics2D g) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	/**
	 * Créer une liste de caisse pour que l'utilisateur puisse les recuperer
	 * dans sa partie
	 * 
	 * @param nbCaisse
	 *            nombre de caisses a récuperer
	 */
	public static void CaisseARecuperer(ArrayList<Caisse> caisseArecup,
			int nbCaisse) {
		for (int i = 0; i < nbCaisse; i++)
			caisseArecup.add(new Caisse(couleur));
	}

}
