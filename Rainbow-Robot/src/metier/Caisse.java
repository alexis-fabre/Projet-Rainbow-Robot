/*
 * Caisse.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package metier;


import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import vue.UtilitaireFenetre;

/**
 * Classe gérant les caisses présentent sur la carte et dans les commandes que
 * devra effectuer le joueur via le robot.
 * 
 * @author Rainbow Robot
 */
public class Caisse implements Dessinable, Serializable {

	/**
	 * Génerer automatiquement par Eclipse
	 */
	private static final long serialVersionUID = -7480744260167787591L;


	/** Couleur par défaut de la caisse 
	 *  Une caisse possédant cette couleur ne pourra ni être dessiner 
	 *  ni être fusionner 
	 */
	public static final int COULEUR_DEFAUT = 0;
	
	/**
	 * Couleur autorisé pour une caisse
	 * <ul>
	 *     <li>1: Rouge  </li>
	 *     <li>2: Jaune  </li>
	 *     <li>3: Violet </li>
	 *     <li>4: Vert   </li>
	 *     <li>5: Bleu   </li>
	 *     <li>6: Orange </li>
	 * </ul>
	 */
	public static final int[] COULEUR_AUTORISEE = { 1, 2, 3, 4, 5, 6 };

	/**
	 * Matrice carré d'ordre "COULEUR_AUTORISEE" 
	 * représentant le résultat obtenu en fusionnant les couleurs autorisees.
	 * Une fusion représente le mélange de deux couleurs 
	 * (intersection L/C de la matrice). 
	 * Lorqu'une fusion est impossible l'intersection de ces deux couleurs vaut 0. 
	 */
	public static final int[][] FUSION_COULEUR = {
		{ 0, 6, 0, 2, 3, 0 },
		{ 6, 0, 0, 0, 4, 0 },
		{ 0, 0, 0, 0, 0, 0 },
		{ 2, 0, 0, 0, 0, 0 },
		{ 3, 4, 0, 0, 0, 0 },
		{ 0, 0, 0, 0, 0, 0 }
	};

	/**
	 * Chemin vers les images des caisses (pour l'affichage sur la partie
	 * graphique). Il faut que l'indice des images correspondent aux indices des
	 * couleurs autorisées. Cela facilite le traitement de l'affichage.
	 */
	public static final String[] CHEMIN_IMAGE_CAISSE = {
		"./Ressource/img/CaseRouge.PNG", "./Ressource/img/CaseJaune.PNG",
		"./Ressource/img/CaseViolette.PNG",
		"./Ressource/img/CaseVerte.PNG", "./Ressource/img/CaseBleue.PNG",
	"./Ressource/img/CaseOrange.PNG" };
	
	/** Couleur de la caisse */
	private int couleur;

	/** Position de la Caisse sur la carte */
	private Position pos_courante;

	
	/**
	 * Constructeur initialisant une caisse en lui attribuant une couleur
	 * 
	 * @param couleur
	 *            la couleur de la caisse à sa création
	 */
	public Caisse(int couleur) {
		// Cas d'erreur
		if(!isColorOK(couleur) ) {
			throw new IllegalArgumentException("Cette couleur n'est pas "
					+ "autorisé dans l'application"); 
		}
		// else
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
	public Caisse(int couleur, Position p_initial) {
		// Cas d'erreur
		if(!isColorOK(couleur)) {
			throw new IllegalArgumentException("Cette couleur n'est pas "
					+ "autorisé dans l'application"); 
		}
		
		this.couleur = couleur;
		this.pos_courante = p_initial;
	}

	/**
	 * Fusion des couleurs de deux caisses par le procédé de la synthèse
	 * additive, synthèse soustrative... Lorsque le Robot effectuera l'action "fusionner"
	 * 
	 * @param c1
	 *            première caisse à fusionner tenue par le robot
	 * @param c2
	 *            deuxième caisse à fusionner
	 * @return c3 la caisse de nouvelle couleur, ou caisse de couleur 0
	 *                                           si la fusion n'est pas possible
	 *         
	 */
	public static Caisse fusionCouleur(Caisse c1, Caisse c2) {
		// caisse à retourner si fusion impossible
		Caisse c3 = new Caisse(0);

		// Vérification que la fusion donne un couleur 
		if(isFusionOk(c1.getCouleur(), c2.getCouleur())) {	
			// la nouvelle caisse prend comme couleur 
			// la fusion des couleurs des caisses c1 et c2
			// couleur -1 car le premier indice d'un tableau est 0
			c3.setCouleur(FUSION_COULEUR[c1.getCouleur()-1][c2.getCouleur()-1]);
		}

		return c3;
	}

	/**
	 * @return couleur la couleur de la caisse
	 */
	public int getCouleur() {
		return couleur;

	}

	/**
	 * @param couleur
	 *            la nouvelle couleur de la caisse
	 */
	public void setCouleur(int couleur) {
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
	public static boolean isColorOK(int aVerifier) {
		boolean couleurOK = false;
		for (int i = 0; i < COULEUR_AUTORISEE.length && !couleurOK; i++) {
			if (aVerifier == COULEUR_AUTORISEE[i] || aVerifier == COULEUR_DEFAUT ) {
					                          
				couleurOK = true;
			}
		}
		return couleurOK;
	}
	/**
	 * Détermine si la fusion entre deux couleurs donne un résultat 
	 * ou non P/R à la matrice "FUSION_COULEUR"
	 * @param c1
	 *          1ère couleur à fusionner
	 * @param c2
	 *          2ème couleur à fusionner
	 * @return true si la fusion donne un couleur, false sinon
	 */
	public static boolean isFusionOk(int c1, int c2) {
		boolean fusionOk = false;
		int i,j;

		// parcourt des lignes jusqu'à arriver à la ligne c1 de la matrice  
		for( i = 0; i < FUSION_COULEUR.length && i != c1-1 ; i++) {	
			// empty body
		}

		// parcourt la ligne c1 jusqu'à arriver à l'indice c2 de cette ligne 
		for( j = 0; j < FUSION_COULEUR[i].length && j != c2-1 ; j++) {
			// empty body
		}

		// si le résultat de l'intersection de Li et Cj
		// différent de 0 alors fusion valide  fusion valide
		if ( FUSION_COULEUR[i][j] != 0) {
			fusionOk = true;
		}
		
		return fusionOk;
	}

	/**
	 * @return pos_courante la position courante de la caisse
	 */
	public Position getPosCaisse() {
		return pos_courante;
	}

	/**
	 * Créer une liste de caisses pour que l'utilisateur puisse les récupérer
	 * dans sa partie
	 * @param caisseArecup 
	 * 
	 * @param nbCaisse
	 *            nombre de caisses a récuperer
	 * @param couleur couleur de la caisse
	 */
	public static void CaisseARecuperer(ArrayList<Caisse> caisseArecup,
			int nbCaisse, int couleur) {
		for (int i = 0; i < nbCaisse; i++)
			caisseArecup.add(new Caisse(couleur, new Position(2, 0)));
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
			if (couleur==(COULEUR_AUTORISEE[i])) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Caisse couleur=" + couleur + ", pos_courante=" + pos_courante
				+ "\n";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		if (pos_courante == null) {
			// La couleur n'est jamais changé donc il n'est pas nécessaire de la
			// cloner
			return new Caisse(couleur);
		} else {
			return new Caisse(couleur, (Position) pos_courante.clone());
		}
	}
}
