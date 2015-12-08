/*
 * UtilitaireFenetre.java							29 nov. 2015
 * IUT Info2 2015-2016
 */
package vue;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * <p>
 * Classe utilitaire regroupant les différentes fonctions et constantes
 * utilisées dans la création des fenêtres. <br />
 * </p>
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class UtilitaireFenetre {
	/**
	 * Dimension des boutons des fenêtres principales
	 */
	public static final Dimension DIM_COMPOSANT_PRINCIPAL = new Dimension(650,
			75);

	/**
	 * Dimension des boutons des fenêtres principales
	 */
	public static final Dimension DIM_COMPOSANT_SECONDAIRE = new Dimension(100,
			60);

	/**
	 * Dimension des fenêtres usuelles
	 */
	public static final Dimension DIM_FENETRE = new Dimension(1000, 750);
	
	/**
	 * Dimension des fenêtres usuelles
	 */
	public static final Dimension DIM_FENETRE_PAUSE = new Dimension(600, 500);

	/**
	 * Dimension d'une case vide. Une case vide est une case en fond représentée
	 * lors d'une partie du jeu.
	 */
	public static final Dimension DIM_CASE_VIDE = new Dimension(50, 50);

	/**
	 * Dimension de l'image d'une caisse.
	 */
	public static final Dimension DIM_CAISSE = new Dimension(40, 40);

	/**
	 * Dimension de l'image du robot.
	 */
	public static final Dimension DIM_ROBOT = new Dimension(80, 80);

	/**
	 * Largeur de la bordure d'une case vide. Une case vide est une case en fond
	 * représentée lors d'une partie du jeu.
	 */
	public static final float LARGEUR_BORDURE = 2.0f;

	/**
	 * Couleur de fond d'une case vide. Une case vide est une case en fond
	 * représentée lors d'une partie du jeu.
	 */
	public static final Color COULEUR_FOND = new Color(210, 211, 213);

	/**
	 * Couleur de la bordure d'une case vide. Une case vide est une case en fond
	 * représentée lors d'une partie du jeu.
	 */
	public static final Color COULEUR_BORDURE = new Color(52, 102, 164);

	/**
	 * Couleur du vortex
	 */
	public static final Color COULEUR_VORTEX = new Color(88, 88, 90);

	/**
	 * Centre la fenêtre par rapport au dimension de l'écran, c'est à dire le
	 * centre de la fenêtre sera placé au centre de l'écran de l'utilisateur.
	 * 
	 * @param aCentrer
	 *            fenêtre que l'on doit centrer
	 */
	public static void centrerFenetre(JFrame aCentrer) {
		// On centre la fenêtre par rapport à la taille de l'écran
		aCentrer.setLocation(((int) java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize().getWidth() / 2)
				- aCentrer.getWidth() / 2, ((int) java.awt.Toolkit
				.getDefaultToolkit().getScreenSize().getHeight() / 2)
				- aCentrer.getHeight() / 2);
	}

	/**
	 * Ajoute un nouveau composant dans le container
	 * 
	 * @param aAjouter
	 *            nouveau composant à ajouter
	 * @param pane
	 *            container où on ajoute le composant
	 * @param largeur
	 *            largeur séparant le composant de l'ancien composant ou du
	 *            composant parent s'il n'y en a pas horizontalement
	 * @param hauteur
	 *            hauteur séparant le composant de l'ancien composant ou du
	 *            composant parent s'il n'y en a pas verticalement
	 * @param alignementX
	 *            alignement horizontal par rapport au container parent (pane)
	 */
	public static void addAComposantWithBoxLayout(JComponent aAjouter,
			Container pane, int largeur, int hauteur, float alignementX) {
		// On aligne le composant horizontalement par rapport à la fenêtre
		if (largeur > 0 || hauteur > 0) {
			pane.add(Box.createRigidArea(new Dimension(largeur, hauteur)));
		}
		aAjouter.setAlignmentX(alignementX);
		pane.add(aAjouter);
	}

	/**
	 * On force le composant à prendre la forme qu'il désire, c'est à dire on
	 * définit la taille minimum, préferrée et maximale qu'un composant peut
	 * prendre. Il est possible qu'avec certain layout, même en forçant cela ne
	 * marche pas.
	 * 
	 * @param component
	 *            composant dont on veut forcer sa taille
	 * @param largeur
	 *            largeur que doit prendre le composant
	 * @param hauteur
	 *            hauteur que doit prendre le composant
	 */
	public static void setAllSize(JComponent component, int largeur, int hauteur) {
		component.setMinimumSize(new Dimension(largeur, hauteur));
		component.setPreferredSize(new Dimension(largeur, hauteur));
		component.setMaximumSize(new Dimension(largeur, hauteur));
	}

	/**
	 * On force le composant à prendre la forme qu'il désire, c'est à dire on
	 * définit la taille minimum, préferrée et maximale qu'un composant peut
	 * prendre. Il est possible qu'avec certain layout, même en forçant cela ne
	 * marche pas.
	 * 
	 * @param component
	 *            composant dont on veut forcer sa taille
	 * @param largeur
	 *            largeur que doit prendre le composant
	 * @param hauteur
	 *            hauteur que doit prendre le composant
	 */
	public static void setAllSize(JComponent component, Dimension dim) {
		component.setMinimumSize(dim);
		component.setPreferredSize(dim);
		component.setMaximumSize(dim);
	}
}
