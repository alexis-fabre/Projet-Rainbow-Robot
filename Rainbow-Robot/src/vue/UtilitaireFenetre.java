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
 * Classe utilitaire regroupant les diff�rentes fonctions et constantes
 * utilis�es dans la cr�ation des fen�tres. <br />
 * </p>
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class UtilitaireFenetre {
	/**
	 * Dimension des boutons des fen�tres principales
	 */
	public static final Dimension DIM_COMPOSANT = new Dimension(400, 60);

	/**
	 * Dimension des fen�tres usuelles
	 */
	public static final Dimension DIM_FENETRE = new Dimension(700, 500);

	/**
	 * Ajoute un nouveau composant dans le container
	 * 
	 * @param aAjouter
	 *            nouveau composant � ajouter
	 * @param pane
	 *            container o� on ajoute le composant
	 * @param largeur
	 *            largeur s�parant deux composants horizontalement
	 * @param hauteur
	 *            hauteur s�parant deux composants verticalement
	 */
	public static void addAComposantWithBoxLayout(JComponent aAjouter,
			Container pane, int largeur, int hauteur) {
		// On aligne le composant horizontalement par rapport � la fen�tre
		aAjouter.setAlignmentX(Component.CENTER_ALIGNMENT);
		pane.add(aAjouter);
		pane.add(Box.createRigidArea(new Dimension(largeur, hauteur)));
	}
}
