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
	public static final Dimension DIM_COMPOSANT = new Dimension(400, 60);

	/**
	 * Dimension des fenêtres usuelles
	 */
	public static final Dimension DIM_FENETRE = new Dimension(700, 500);

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
