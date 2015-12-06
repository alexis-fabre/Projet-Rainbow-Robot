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
	 * Comprend les traductions pour la JFrame F_accueil.<br />
	 * </p>
	 * <ul>
	 * <li>La 1ère ligne correspond au tradution française.</li>
	 * <li>La 2nde ligne correspond à la traduction anglaise.</li>
	 * <li>
	 * <ul>
	 * <li>La 1ère colonne correspond au nom de la fenêtre.</li>
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
			{ "Accueil", "Rainbow Robot", "Jouer", "Records", "Langue",
					"A Propos", "Quitter" },
			{ "Home", "Rainbow Robot", "Play", "High Scores", "Language",
					"About", "Exit" } };

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
	 * <li>La 1ère colonne correspond au nom de la fenêtre.</li>
	 * <li>La 2ème colonne correspond à la traduction de la question.</li>
	 * <li></li>
	 * </ul>
	 * </li>
	 * </ul>
	 */
	private final String[][] LANGUE = {
			{ "Choix de la langue", "Veuillez choisir une langue : " },
			{ "Language's selection", "Please select a language : " } };

	/**
	 * Contient toutes les traductions de langues connues pour cette application
	 */
	private final String[] TOUTES_LANGUES = { "Français", "English" };

	/**
	 * Contient les traductions pour la fenêtre ChoixMode
	 */
	private final String[][] CHOIXMODE = {
			{
					"Choix du mode",
					"Jouer solo contre l'ordinateur avec des énigmes",
					"Jouer solo (records personnels) ou 1v1 contre l'ordinateur",
					"Jouer solo ou 1v1 contre l'ordinateur sur une carte personnalisée",
					"Retour" },
			{ "Gaming mode",
					"Single player against the computer with mysteries",
					"Single player (personnal bests) or 1vs1 against IA",
					"Single player or 1vs1 against IA on a personalized map",
					"Back" } };
	
	/**
	 * Contient les traductions pour la fenêtre Reccords
	 */
	private final String[][] MODE_RECCORD = { {"Reccords", "Joueur", "Temps"},
	        {"High Score", "Player", "Time"}};
	

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
	 * <li>La 1ère colonne correspond au nom de la fenêtre.</li>
	 * <li>La 2ème colonne correspond à la traduction de la question.</li>
	 * <li></li>
	 * </ul>
	 * </li>
	 * </ul>
	 */
	private final String[][] QUITTER = {
			{ "Quitter l'application", "Voulez-vous quitter l'application" },
			{ "Exit application", "Do you want to exit the application ?" } };

	/**
	 * <p>
	 * Comprend les traductions pour la JFrame F_abstractModeJeu.<br />
	 * </p>
	 * <ul>
	 * <li>La 1ère ligne correspond au tradution française.</li>
	 * <li>La 2nde ligne correspond à la traduction anglaise.</li>
	 * <li>
	 * <ul>
	 * <li>La 1ère colonne correspond au Bouton Retour.</li>
	 * <li>La 2ème colonne correspond au Bouton Jouer.</li>
	 * <li></li>
	 * </ul>
	 * </li>
	 * </ul>
	 */
	private final String[][] MODE_JEU = { { "Retour", "Jouer" },
			{ "Back", "Play" } };

	/**
	 * <p>
	 * Comprend les traductions pour la JFrame F_story.<br />
	 * </p>
	 * <ul>
	 * <li>La 1ère ligne correspond au tradution française.</li>
	 * <li>La 2nde ligne correspond à la traduction anglaise.</li>
	 * <li>
	 * <ul>
	 * <li>La 1ère colonne correspond au nom de la fenêtre.</li>
	 * <li>La 2ème colonne correspond au titre de la fenêtre.</li>
	 * <li>La 3ème colonne correspond au règle de ce mode.</li>
	 * <li>La 4ème colonne correspond à la descritpion des commandes
	 * utilisables.</li>
	 * <li></li>
	 * </ul>
	 * </li>
	 * </ul>
	 */
	private final String[][] MODE_STORY = {
			{ "Mode story", "MODE STORY", "Règle : ", "Commande : " }, //
			{ "Story mode", "STORY MODE", "Rules : ", "Control : " } };

	/**
	 * <p>
	 * Comprend les traductions pour la JFrame F_arcade.<br />
	 * </p>
	 * <ul>
	 * <li>La 1ère ligne correspond au tradution française.</li>
	 * <li>La 2nde ligne correspond à la traduction anglaise.</li>
	 * <li>
	 * <ul>
	 * <li>La 1ère colonne correspond au nom de la fenêtre.</li>
	 * <li>La 2ème colonne correspond au titre de la fenêtre.</li>
	 * <li>La 3ème colonne correspond au règle de ce mode.</li>
	 * <li>La 4ème colonne correspond à la descritpion des commandes
	 * utilisables.</li>
	 * <li>La 5ème colonne correspond au radio bouton Jeu solo.</li>
	 * <li>La 6ème colonne correspond au radio bouton Jeu IA.</li>
	 * <li>La 7ème colonne correspond au titre de la bordure.</li>
	 * <li>La 8ème colonne correspond au radio bouton Difficulté facile de l'IA.
	 * </li>
	 * <li>La 9ème colonne correspond au radio bouton Difficulté moyenne de
	 * l'IA.</li>
	 * <li>La 10ème colonne correspond au radio bouton Difficulté difficile de
	 * l'IA.</li>
	 * <li></li>
	 * </ul>
	 * </li>
	 * </ul>
	 */
	private final String[][] MODE_ARCADE = {
			{ "Mode arcade", "MODE ARCADE", "Règle : ", "Commande : ",
					"Jeu Solo", "Jeu contre l'IA", "Difficulté", "Facile",
					"Moyenne", "Difficile" }, //
			{ "Arcade mode", "ARCADE MODE", "Rules : ", "Control : ",
					"Play Solo", "Play against AI", "Level", "Easy", "Medium",
					"Hard" } };

	/**
	 * <p>
	 * Comprend les traductions pour la JFrame F_custom.<br />
	 * </p>
	 * <ul>
	 * <li>La 1ère ligne correspond au tradution française.</li>
	 * <li>La 2nde ligne correspond à la traduction anglaise.</li>
	 * <li>
	 * <ul>
	 * <li>La 1ère colonne correspond au nom de la fenêtre.</li>
	 * <li>La 2ème colonne correspond au titre de la fenêtre.</li>
	 * <li>La 3ème colonne correspond au règle de ce mode.</li>
	 * <li>La 4ème colonne correspond à la descritpion des commandes
	 * utilisables.</li>
	 * <li>La 5ème colonne correspond au radio bouton Jeu solo.</li>
	 * <li>La 6ème colonne correspond au radio bouton Jeu IA.</li>
	 * <li>La 7ème colonne correspond au titre de la bordure.</li>
	 * <li>La 8ème colonne correspond au radio bouton Difficulté facile de l'IA.
	 * </li>
	 * <li>La 9ème colonne correspond au radio bouton Difficulté moyenne de
	 * l'IA.</li>
	 * <li>La 10ème colonne correspond au radio bouton Difficulté difficile de
	 * l'IA.</li>
	 * <li>La 11ème colonne correspond au titre pour selectionner un fichier
	 * (JLabel).</li>
	 * <li>La 12ème colonne correspond au bouton pour aller chercher un fichier
	 * dans nos dossiers personnels.</li>
	 * </ul>
	 * </li>
	 * </ul>
	 */
	private final String[][] MODE_CUSTOM = {
			{ "Mode custom", "MODE CUSTOM", "Règle : ", "Commande : ",
					"Jeu Solo", "Jeu contre l'IA", "Difficulté", "Facile",
					"Moyenne", "Difficile", "Choisir un fichier", "Parcourir" }, //
			{ "Custom mode", "CUSTOM MODE", "Rules : ", "Control : ",
					"Play Solo", "Play against AI", "Level", "Easy", "Medium",
					"Hard", "Choose a file", "Search" } };

	/**
	 * Pour n'avoir qu'une seule instance de la classe que l'on récupère via
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
				if (nouvelleLangue.equals(TOUTES_LANGUES[i])) {
					setLangue(i);
					break;
				}
			}
		}
	}

	/**
	 * @return les traductions utilisées pour la JFrame F_accueil
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

	/**
	 * @return les traductions utilisées pour pour la JFrame F_abstractModeJeu
	 */
	public String[] getModeJeu() {
		return MODE_JEU[langue];
	}

	/**
	 * @return les traductions utilisées pour pour la JFrame ChoixMode
	 */
	public String[] getChoixMode() {
		return CHOIXMODE[langue];
	}

	/**
	 * @return les traductions utilisées pour pour la JFrame F_story
	 */
	public String[] getModeStory() {
		return MODE_STORY[langue];
	}

	
	/**
         * @return les traductions utilisées pour pour la JFrame Reccords
         */
        public String[] getReccords() {
                return MODE_RECCORD[langue];
        }


	/**
	 * @return les traductions utilisées pour pour la JFrame F_arcade
	 */
	public String[] getModeArcade() {
		return MODE_ARCADE[langue];
	}

	/**
	 * @return les traductions utilisées pour pour la JFrame F_custom
	 */
	public String[] getModeCustom() {
		return MODE_CUSTOM[langue];
	}

}
