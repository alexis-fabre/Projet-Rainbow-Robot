/*
 * ChoixLangue.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package vue;

import javax.swing.JButton;
import javax.swing.JFrame;

import evenement.ClicSouris;

/**
 * Fenêtre lancée à partir de la fenêtre d'accueil (Accueil.java). Elle permet
 * de changer la langue de l'application.
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class ChoixLangue extends JFrame {

	/**
	 * Bouton qui traduit la langue en Français
	 */
	private JButton bt_fr;

	/**
	 * Bouton qui traduit la langue en Anglais (English)
	 */
	private JButton bt_en;

	/**
	 * Bouton retour qui permet de revenir sur l'accueil
	 */
	private JButton bt_retour;

	/**
	 * Langue choisie par l'utilisateur
	 */
	private int langue;

	/**
	 * Constante permettant à l'utilisateur de choisir la langue française
	 */
	public static final int LANGUE_FR = 1;

	/**
	 * Constante permettant à l'utilisateur de choisir la langue anglaise
	 */
	public static final int LANGUE_EN = 2;

	/**
	 * Comprend les traductions pour la page Accueil La 1ère ligne correspond au
	 * tradution française La 2nde ligne correspond à la traduction anglaise
	 */
	private final String[][] ACCUEIL = {
			{ "Jouer", "Records", "Langue", "A Propos", "Quitter" },
			{ "Play", "Records", "Language", "By the way", "Exit" } };

	/**
	 * Initialise les composants et les disposent sur un contexte graphique 2D.
	 * La fenêtre s'affiche au centre de l'écran et n'est pas redimensionnable
	 * pour éviter tous soucis de disposition.
	 * 
	 * @param gestion
	 *            le contrôleur qui va controler cette vue = cible
	 *            evenementielle
	 */
	public ChoixLangue(ClicSouris gestion) {

	}

	/**
	 * @return la langue
	 */
	public int getLangue() {
		return langue;
	}

	/**
	 * @param langue
	 *            la langue à modifier
	 */
	public void setLangue(int langue) {
		if (langue == LANGUE_FR || langue == LANGUE_EN) {
			this.langue = langue;
		}
	}

	/**
	 * @return les traductions utilisées pour le menu Accueil selon la langue
	 *         choisie
	 */
	public String[] getACCUEIL() {
		return langue == LANGUE_FR ? ACCUEIL[0] : ACCUEIL[1];
	}

}