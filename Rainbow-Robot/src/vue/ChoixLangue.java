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
 * Pour l'instant seul la langue française ou anglaise sont disponible.<br />
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
	 * l'indice du tableau TOUTES_LANGUES.
	 */
	public static final int LANGUE_FR = 0;

	/**
	 * Constante permettant à l'utilisateur de choisir la langue anglaise. Pour
	 * une meilleure utilisation la valeur de cette variable soit correspondre à
	 * l'indice du tableau TOUTES_LANGUES.
	 */
	public static final int LANGUE_EN = 1;

	/**
	 * Contient toutes les traductions de langues connues pour cette application
	 */
	private static final String[] TOUTES_LANGUES = { "Français", "English" };

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
	private static final String[][] ACCUEIL = {
			{ "Accueil", "Rainbow Robot", "Jouer", "Records", "Langue",
					"A Propos", "Quitter" },
			{ "Home", "Rainbow Robot", "Play", "High Scores", "Language",
					"About", "Exit" } };

	/**
	 * *
	 * <p>
	 * Comprend les traductions pour la JFrame F_aPropos.<br />
	 * </p>
	 * <ul>
	 * <li>La 1ère ligne correspond au tradution française.</li>
	 * <li>La 2nde ligne correspond à la traduction anglaise.</li>
	 * <li>
	 * <ul>
	 * <li>La 1ère colonne correspond au nom de la fenêtre.</li>
	 * <li>La 2ème colonne correspond au titre de la page.</li>
	 * <li>La 3ème colonne correspond à la description du projet.</li>
	 * <li>La 4ème colonne correspond au contenu du bouton Retour.</li>
	 * <li></li>
	 * </ul>
	 * </li>
	 * </ul>
	 */
	private static final String[][] A_PROPOS = {
			{
					"A propos",
					"A propos du projet Rainbow Robot",
					"<html><body style=\"margin-left: 25px;\">Le jeu Rainbow Robot a été réalisé dans le cadre du projet du DUT Informatique (semestre 3 et 4).<br />"
							+ "Le jeu a été imaginé par le client Mr MONCEL : Responsable du département QLIO.<br />"
							+ "Le but du jeu est de faire connaître le département QLIO au travers d'une application ludique.<br />"
							+ "Rainbow Robot répresente la logistique d'un entrepôt, à savoir l'optimisation des tournées de <i>picking</i>.<br />"
							+ "Le jeu a été réalisé par :"
							+ "<ul>"
							+ "<li>Alexis FABRE : Chef de projet</li>"
							+ "<li>Sylvain BENARD : Sous-chef de projet</li>"
							+ "<li>Florian LOUARGANT : Secrétaire</li>"
							+ "<li>Quentin MASSELLAMANY-SORNOM : Gestionnaire de l'organisation informatique</li>"
							+ "<li>Benjamin MAZOYER : Gestionnaire des configurations</li>"
							+ "</ul></body></html>", "Retour" },
			{
					"About",
					"About the project Rainbow Robot",
					"<html><body style=\"margin-left: 25px;\">The game Rainbow Robot has been elaborated in the context of the 2nd year DUT project (semesters 3 and 4).<br />"
							+ "The game was designed by the client M. MONCEL : Teacher in the QLIO branch.<br />"
							+ "The goal is to promote the QLIO branch through a fun application.<br />"
							+ "Rainbow Robot represents the warehouse logistic, namely the order <i>picking</i> optimisations. <br />"
							+ "This game has been established by :"
							+ "<ul>"
							+ "<li>Alexis FABRE: Project Manager</li>"
							+ "<li>Sylvain BENARD : Deputy Chief</li>"
							+ "<li>Florian LOUARGANT :  Secretary</li>"
							+ "<li>Quentin MASSELLAMANY-SORNOM : IT organization Manager </li>"
							+ "<li>Benjamin MAZOYER : Configuration Manager</li>"
							+ "</ul></body></html>", "Back" } };

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
	private static final String[][] LANGUE = {
			{ "Choix de la langue", "Veuillez choisir une langue : " },
			{ "Language's selection", "Please select a language : " } };

	/**
	 * Contient les traductions pour la fenêtre ChoixMode
	 */
	private static final String[][] CHOIX_MODE = {
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
	private static final String[][] MODE_RECCORD = {
			{ "Reccords", "Joueur", "Temps", "Niveau", "Retour" },
			{ "High Score", "Player", "Time", "Level", "Back" } };

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
	private static final String[][] QUITTER = {
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
	private static final String[][] MODE_JEU = { { "Retour", "Jouer" },
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
	private static final String[][] MODE_STORY = {
			{
					"Mode story",
					"MODE STORY",
					"<html>Règle : Attraper toutes les caisses affichées dans le panneau du haut et les déposer dans le vortex (trou sombre).<br /> Si vous êtes le plus rapide, vous pourrez peut-être apparaître dans les reccords.<br /> A vous de jouer !!!</html>",
					"<html>Commande du robot : <br /><pre>↑ pour avancer<br />↓ pour reculer<br />← pour pivoter à gauche<br />→ pour pivoter à droite<br />\"Espace\" pour récupérer une caisse</pre></html>" }, //
			{
					"Story mode",
					"STORY MODE",
					"<html>Rules : Catch all the boxes (cash registers) shown into the trap by the top and put down them in the whirlpool ( dark hole) .<br /> If you are the fastest, you can maybe seem in reccords.<br /> Let's play!!!</html>",
					"<html>Control : Command of robot:<br/><pre>↑ To move forward<br/>↓ To move back<br/>← To swing to the left<br/>→ To swing to the right <br/> \"Space\" to catch a box </pre> </html>" } };

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
	private static final String[][] MODE_ARCADE = {
			{
					"Mode arcade",
					"MODE ARCADE",
					"<html>Règle : Attraper toutes les caisses affichées dans le panneau du haut et les déposer dans le vortex (trou sombre).<br /> Si vous êtes le plus rapide, vous pourrez peut-être apparaître dans les reccords.<br /> A vous de jouer !!!</html>",
					"<html>Commande du robot : <br /><pre>↑ pour avancer<br />↓ pour reculer<br />← pour pivoter à gauche<br />→ pour pivoter à droite<br />\"Espace\" pour récupérer une caisse</pre></html>",
					"Jeu Solo", "Jeu contre l'IA", "Difficulté", "Facile",
					"Moyenne", "Difficile" }, //
			{
					"Arcade mode",
					"ARCADE MODE",
					"<html>Rules : Catch all the boxes (cash registers) shown into the trap by the top and put down them in the whirlpool ( dark hole) .<br /> If you are the fastest, you can maybe seem in reccords.<br /> Let's play!!!</html>",
					"<html>Control : Command of robot:<br/><pre>↑ To move forward<br/>↓ To move back<br/>← To swing to the left<br/>→ To swing to the right <br/> \"Space\" to catch a box </pre> </html>",
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
	private static final String[][] MODE_CUSTOM = {
			{
					"Mode custom",
					"MODE CUSTOM",
					"<html>Règle : Attraper toutes les caisses affichées dans le panneau du haut et les déposer dans le vortex (trou sombre).<br /> Si vous êtes le plus rapide, vous pourrez peut-être apparaître dans les reccords.<br /> A vous de jouer !!!</html>",
					"<html>Commande du robot : <br /><pre>↑ pour avancer<br />↓ pour reculer<br />← pour pivoter à gauche<br />→ pour pivoter à droite<br />\"Espace\" pour récupérer une caisse</pre></html>",
					"Jeu Solo", "Jeu contre l'IA", "Difficulté", "Facile",
					"Moyenne", "Difficile", "Choisir un fichier", "Parcourir" }, //
			{
					"Custom mode",
					"CUSTOM MODE",
					"<html>Rules : Catch all the boxes (cash registers) shown into the trap by the top and put down them in the whirlpool ( dark hole) .<br /> If you are the fastest, you can maybe seem in reccords.<br /> Let's play!!!</html>",
					"<html>Control : Command of robot:<br/><pre>↑ To move forward<br/>↓ To move back<br/>← To swing to the left<br/>→ To swing to the right <br/> \"Space\" to catch a box </pre> </html>",
					"Play Solo", "Play against AI", "Level", "Easy", "Medium",
					"Hard", "Choose a file", "Browse" } };

	/**
	 * <p>
	 * Comprend les traductions pour la fin d'une partie de Rainbow Robot.<br />
	 * </p>
	 * <ul>
	 * <li>La 1ère ligne correspond au tradution française.</li>
	 * <li>La 2nde ligne correspond à la traduction anglaise.</li>
	 * <li>
	 * <ul>
	 * <li>La 1ère colonne correspond au nom de la fenêtre.</li>
	 * <li>La 2ème colonne correspond au titre de la fenêtre.</li>
	 * <li>La 3ème colonne correspond à la traduction du bouton "Recommencer".</li>
	 * <li>La 4ème colonne correspond à la traduction du bouton
	 * "Passer au niveau suivant".</li>
	 * <li>La 5ème colonne correspond à la traduction du bouton "Quitter".</li>
	 * </ul>
	 * </li>
	 * </ul>
	 */
	private static final String[][] FIN_PARTIE = {
			{ "Fin de la partie", "Fin de la partie", "Recommencer",
					"Passer au niveau suivant", "Quitter" }, //
			{ "Game over", "Game over", "Restart", "Next level", "Exit" } };

	/**
	 * <p>
	 * Comprend les traductions pour la fin d'une partie de Rainbow Robot.<br />
	 * </p>
	 * <ul>
	 * <li>La 1ère ligne correspond au tradution française.</li>
	 * <li>La 2nde ligne correspond à la traduction anglaise.</li>
	 * <li>
	 * <ul>
	 * <li>La 1ère colonne correspond au nom de la fenêtre.</li>
	 * <li>La 2ème colonne correspond au titre de la fenêtre.</li>
	 * <li>La 3ème colonne correspond à la traduction du bouton "Recommencer".</li>
	 * <li>La 4ème colonne correspond à la traduction du bouton
	 * "Passer au niveau suivant".</li>
	 * <li>La 5ème colonne correspond à la traduction du bouton "Quitter".</li>
	 * </ul>
	 * </li>
	 * </ul>
	 */
	private static final String[][] MENU_PAUSE = {
			{ "Pause", "Pause", "Continuer", "Recommencer", "Quitter" }, //
			{ "Pause", "Pause", "Start", "Restart", "Exit" } };

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
	private static final String[][] QUITTER_PARTIE = {
			{ "Voulez-vous vraiment quitter la partie en cours ?",
					"Quitter la partie" },
			{ "Do you want to exit the game ?", "Exit game" } };

	private static final String[][] CHOIX_NIVEAU = {
			{ "Choisissez un niveau du mode story", "Retour", "Jouer" },
			{ "Choose a level of story mode", "Back", "Play" } };

	/**
	 * Pour n'avoir qu'une seule instance de la classe que l'on récupère via
	 * getLangue()
	 */
	private ChoixLangue() {
		langue = LANGUE_FR;
	}

	/**
	 * Singleton Instanciation de ChoixLangue
	 * 
	 * @return langueChoisie
	 */
	public static ChoixLangue getChoixLangue() {
		if (langueChoisie == null) {
			langueChoisie = new ChoixLangue();
		}
		return langueChoisie;
	}

	/**
	 * @param nouvelleLangue
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
	 * @param nouvelleLangue
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
	 * @return les traductions utilisées pour la JFrame F_aPropos
	 *         (F_aPropos.java).<br />
	 */
	public String[] getApropos() {
		return A_PROPOS[langue];
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
		return CHOIX_MODE[langue];
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

	/**
	 * @return les traductions pour la fin d'une partie de jeu <br />
	 *         (ClicSouris.java / update )
	 */
	public String[] getFinPartie() {
		return FIN_PARTIE[langue];
	}

	/**
	 * @return les traductions pour le menu pause dans une partie de jeu
	 *         (ClicSouris.java / mouseClicked / Bouton Pause )
	 */
	public String[] getMenuPause() {
		return MENU_PAUSE[langue];
	}

	/**
	 * @return les traductions pour le menu quitter la partie de jeu
	 *         (ClicSouris.java / mouseClicked / Bouton Quitter )
	 */
	public String[] getQuitterPartie() {
		return QUITTER_PARTIE[langue];
	}

	/**
	 * @return les traductions pour la JFrame F_choixNiveau
	 */
	public String[] getChoixNiveau() {
		return CHOIX_NIVEAU[langue];
	}
}
