/*
 * ChoixLangue.java							29 nov. 2015
 * IUT Info2 2015-2016
 */
package vue;

import java.util.Locale;

import javax.swing.JComponent;

/**
 * <p>
 * Repr�sentation de la traduction de l'application en diff�rentes langues.<br />
 * Pour l'instant seul la langue fran�aise ou anglaise.<br />
 * On utilise le Pattern Singleton pour n'avoir qu'une seule langue pour toutes
 * les fen�tres.<br />
 * </p>
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class ChoixLangue {

	/**
	 * Unique repr�sentation de la classe
	 */
	private static ChoixLangue langueChoisie;

	/**
	 * Langue choisie par l'utilisateur
	 */
	private int langue;

	/**
	 * Constante permettant � l'utilisateur de choisir la langue fran�aise. Pour
	 * une meilleure utilisation la valeur de cette variable soit correspondre �
	 * l'indice du tableau LANGUE.
	 */
	public static final int LANGUE_FR = 0;

	/**
	 * Constante permettant � l'utilisateur de choisir la langue anglaise
	 */
	public static final int LANGUE_EN = 1;

	/**
	 * <p>
	 * Comprend les traductions pour la JFrame Accueil (F_accueil.java).<br />
	 * </p>
	 * <ul>
	 * <li>La 1�re ligne correspond au tradution fran�aise.</li>
	 * <li>La 2nde ligne correspond � la traduction anglaise.</li>
	 * <li>
	 * <ul>
	 * <li>La 1�re colonne correspond au nom de la page.</li>
	 * <li>La 2�me colonne correspond au titre de la page.</li>
	 * <li>La 3�me colonne correspond au contenu du bouton Jouer.</li>
	 * <li>La 4�me colonne correspond au contenu du bouton Reccors.</li>
	 * <li>La 5�me colonne correspond au contenu du bouton Langue.</li>
	 * <li>La 6�me colonne correspond au contenu du bouton A Propos.</li>
	 * <li>La 7�me colonne correspond au contenu du bouton Quitter.</li>
	 * <li></li>
	 * </ul>
	 * </li>
	 * </ul>
	 */
	private final String[][] ACCUEIL = {
			{ "Accueil", "Rainbow Robot", "Jouer", "Records", "Langue",
					"A Propos", "Quitter" },
			{ "Home", "Rainbow Robot", "Play", "Records", "Language",
					"About", "Exit" } };

	/**
	 * <p>
	 * Comprend les traductions pour la JOptionPane Langue (ClicSouris.java /
	 * mouseClicked / Bouton Langue).<br />
	 * </p>
	 * <ul>
	 * <li>La 1�re ligne correspond au tradution fran�aise.</li>
	 * <li>La 2nde ligne correspond � la traduction anglaise.</li>
	 * <li>
	 * <ul>
	 * <li>La 1�re colonne correspond au nom de la page.</li>
	 * <li>La 2�me colonne correspond � la traduction de la question.</li>
	 * <li>La 3�me colonne correspond � la tradution fran�aise.</li>
	 * <li>La 4�me colonne correspond � la traduction anglaise.</li>
	 * <li></li>
	 * </ul>
	 * </li>
	 * </ul>
	 */
	private final String[][] LANGUE = {
			{ "Choix de la langue", "Veuillez choisir une langue : ",
					"Fran�ais", "English" },
			{ "Language's selection", "Please select a language : ", "Fran�ais",
					"English" } };

	/**
	 * Contient toutes les traductions de langues connues pour cette application
	 */
	private final String[] TOUTES_LANGUES = { "Fran�ais", "English" };

	/**
	 * <p>
	 * Comprend les traductions pour la JOptionPane Quitter (ClicSouris.java /
	 * mouseClicked / Bouton Quitter).<br />
	 * </p>
	 * <ul>
	 * <li>La 1�re ligne correspond au tradution fran�aise.</li>
	 * <li>La 2nde ligne correspond � la traduction anglaise.</li>
	 * <li>
	 * <ul>
	 * <li>La 1�re colonne correspond au nom de la page.</li>
	 * <li>La 2�me colonne correspond � la traduction de la question.</li>
	 * <li>La 3�me colonne correspond � la tradution fran�aise.</li>
	 * <li>La 4�me colonne correspond � la traduction anglaise.</li>
	 * <li></li>
	 * </ul>
	 * </li>
	 * </ul>
	 */
	private final String[][] QUITTER = {
			{ "Quitter l'application", "Voulez-vous quitter l'application" },
			{ "Exit application", "Do you want to exit the application ?" } };

	/**
	 * Pour n'avoir qu'une seule instance de la classe que l'on r�cu�re via
	 * getLangue()
	 */
	private ChoixLangue() {
		langue = LANGUE_FR;
	}

	public static ChoixLangue getChoixLangue() {
		if (langueChoisie == null) {
			langueChoisie = new ChoixLangue();
		}
		return langueChoisie;
	}

	/**
	 * @param langue
	 *            la langue � modifier
	 */
	public void setLangue(int nouvelleLangue) {
		if (nouvelleLangue == LANGUE_FR) {
			langue = nouvelleLangue;
			// On change la langue locale des composants
			JComponent.setDefaultLocale(Locale.FRANCE);
		}
		if (nouvelleLangue == LANGUE_EN) {
			langue = nouvelleLangue;
			// On change la langue locale des composants
			JComponent.setDefaultLocale(Locale.ENGLISH);
		}

	}

	/**
	 * Cette fonction ne marche uniquement que si les valeurs des constantes
	 * enti�res des langues correspondent aux indices du tableau TOUTE_LANGUES.
	 * 
	 * @param langue
	 *            la langue � modifier
	 */
	public void setLangue(String nouvelleLangue) {
		if (nouvelleLangue != null) {
			for (int i = 0; i < TOUTES_LANGUES.length; i++) {
				if (nouvelleLangue.trim().equals(TOUTES_LANGUES[i])) {
					setLangue(i);
					break;
				}
			}
		}
	}

	/**
	 * @return les traductions utilis�es pour la JFrame Accueil
	 *         (F_accueil.java).<br />
	 */
	public String[] getAccueil() {
		return ACCUEIL[langue];
	}

	/**
	 * @return les traductions utilis�es pour la JOptionPane Langue
	 *         (ClicSouris.java / mouseClicked / Bouton Langue)
	 */
	public String[] getLangue() {
		return LANGUE[langue];
	}

	/**
	 * @return toutes les traductions de langues connues pour cette application
	 */
	public String[] getToutesLangues() {
		return TOUTES_LANGUES;
	}

	/**
	 * @return les traductions utilis�es pour pour la JOptionPane Quitter
	 *         (ClicSouris.java / mouseClicked / Bouton Quitter)
	 */
	public String[] getQuitter() {
		return QUITTER[langue];
	}

}
