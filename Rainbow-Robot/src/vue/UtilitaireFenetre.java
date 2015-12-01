/*
 * UtilitaireFenetre.java							29 nov. 2015
 * IUT Info2 2015-2016
 */
package vue;

import java.awt.Component;
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
	public static final Dimension DIM_COMPOSANT_PRINCIPAL = new Dimension(400,
			60);

	/**
	 * Dimension des boutons des fenêtres principales
	 */
	public static final Dimension DIM_COMPOSANT_SECONDAIRE = new Dimension(100,
			60);

	/**
	 * Dimension des fenêtres usuelles
	 */
	public static final Dimension DIM_FENETRE = new Dimension(700, 500);

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
	 *            largeur séparant deux composants horizontalement
	 * @param hauteur
	 *            hauteur séparant deux composants verticalement
	 */
	public static void addAComposantWithBoxLayout(JComponent aAjouter,
			Container pane, int largeur, int hauteur) {
		// On aligne le composant horizontalement par rapport à la fenêtre
		aAjouter.setAlignmentX(Component.CENTER_ALIGNMENT);
		pane.add(aAjouter);
		pane.add(Box.createRigidArea(new Dimension(largeur, hauteur)));
	}
}
