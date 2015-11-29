/*
 * ChoixLangue.java							29 nov. 2015
 * IUT Info2 2015-2016
 */
package vue;

import java.util.Locale;

import javax.swing.JComponent;

/**
 * <p>
 * Représentation de la traduction de l'application en différentes langues.<br />
 * Pour l'instant seul la langue française ou anglaise.<br />
 * On utilise le Pattern Singleton pour n'avoir qu'une seule langue pour toutes
 * les fenêtres.<br />
 * </p>
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class ChoixLangue {

	/**
	 * Unique représentation de la classe
	 */
	private static ChoixLangue langueChoisie;

	/**
	 * Langue choisie par l'utilisateur
	 */
	private int langue;

	/**
	 * Constante permettant à l'utilisateur de choisir la langue française. Pour
	 * une meilleure utilisation la valeur de cette variable soit correspondre à
	 * l'indice du tableau LANGUE.
	 */
	public static final int LANGUE_FR = 0;

	/**
	 * Constante permettant à l'utilisateur de choisir la langue anglaise
	 */
	public static final int LANGUE_EN = 1;

	/**
	 * <p>
	 * Comprend les traductions pour la JFrame Accueil (F_accueil.java).<br />
	 * </p>
	 * <ul>
	 * <li>La 1ère ligne correspond au tradution française.</li>
	 * <li>La 2nde ligne correspond à la traduction anglaise.</li>
	 * <li>
	 * <ul>
	 * <li>La 1ère colonne correspond au nom de la page.</li>
	 * <li>La 2ème colonne correspond au titre de la page.</li>
	 * <li>La 3ème colonne correspond au contenu du bouton Jouer.</li>
	 * <li>La 4ème colonne correspond au contenu du bouton Reccors.</li>
	 * <li>La 5ème colonne correspond au contenu du bouton Langue.</li>
	 * <li>La 6ème colonne correspond au contenu du bouton A Propos.</li>
	 * <li>La 7ème colonne correspond au contenu du bouton Quitter.</li>
	 * <li></li>
	 * </ul>
	 * </li>
	 * </ul>
	 */
	private final String[][] ACCUEIL = {
			{ "Accueil", "Rainbow Robot", "Jouer", "Reccors", "Langue",
					"A Propos", "Quitter" },
			{ "Reception", "Rainbow Robot", "Play", "Records", "Language",
					"By the way", "Exit" } };

	/**
	 * <p>
	 * Comprend les traductions pour la JOptionPane Langue (ClicSouris.java /
	 * mouseClicked / Bouton Langue).<br />
	 * </p>
	 * <ul>
	 * <li>La 1ère ligne correspond au tradution française.</li>
	 * <li>La 2nde ligne correspond à la traduction anglaise.</li>
	 * <li>
	 * <ul>
	 * <li>La 1ère colonne correspond au nom de la page.</li>
	 * <li>La 2ème colonne correspond à la traduction de la question.</li>
	 * <li>La 3ème colonne correspond à la tradution française.</li>
	 * <li>La 4ème colonne correspond à la traduction anglaise.</li>
	 * <li></li>
	 * </ul>
	 * </li>
	 * </ul>
	 */
	private final String[][] LANGUE = {
			{ "Choix de la langue", "Veuillez choisir une langue : ",
					"Français", "English" },
			{ "Language selection", "Please select a language : ", "Français",
					"English" } };

	/**
	 * Contient toutes les traductions de langues connues pour cette application
	 */
	private final String[] TOUTES_LANGUES = { "Français", "English" };

	/**
	 * <p>
	 * Comprend les traductions pour la JOptionPane Quitter (ClicSouris.java /
	 * mouseClicked / Bouton Quitter).<br />
	 * </p>
	 * <ul>
	 * <li>La 1ère ligne correspond au tradution française.</li>
	 * <li>La 2nde ligne correspond à la traduction anglaise.</li>
	 * <li>
	 * <ul>
	 * <li>La 1ère colonne correspond au nom de la page.</li>
	 * <li>La 2ème colonne correspond à la traduction de la question.</li>
	 * <li>La 3ème colonne correspond à la tradution française.</li>
	 * <li>La 4ème colonne correspond à la traduction anglaise.</li>
	 * <li></li>
	 * </ul>
	 * </li>
	 * </ul>
	 */
	private final String[][] QUITTER = {
			{ "Quitter l'application", "Voulez-vous quitter l'application" },
			{ "Exit Application", "Do you want to exit the application ?" } };

	/**
	 * Pour n'avoir qu'une seule instance de la classe que l'on récuère via
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
	 *            la langue à modifier
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
	 * entières des langues correspondent aux indices du tableau TOUTE_LANGUES.
	 * 
	 * @param langue
	 *            la langue à modifier
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
	 * @return les traductions utilisées pour la JFrame Accueil
	 *         (F_accueil.java).<br />
	 */
	public String[] getAccueil() {
		return ACCUEIL[langue];
	}

	/**
	 * @return les traductions utilisées pour la JOptionPane Langue
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
	 * @return les traductions utilisées pour pour la JOptionPane Quitter
	 *         (ClicSouris.java / mouseClicked / Bouton Quitter)
	 */
	public String[] getQuitter() {
		return QUITTER[langue];
	}

}
