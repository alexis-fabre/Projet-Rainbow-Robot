/*
 * Lanceur.java							28 nov. 2015
 * IUT Info2 2015-2016
 */
package launcher;

import javax.swing.UIManager;

import vue.F_accueil;
import vue.F_commandes;
import metier.JeuRainbow;
import vue.F_accueil;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;

import evenement.ClicSouris;

/**
 * <p>
 * Déroulement logique de Rainbow Robot. C'est une application représentant un
 * jeu de type "taquin".<br >
 * Le joueur contrôle en temps réel un petit robot qui doit aller chercher des
 * caisses colorées dans un entrepôt. Si la caisse à aller chercher est derrière
 * d'autre caisse, il va falloir déplacer celles-ci de façon optimisée.<br />
 * La particularité de ce jeu réside dans la possibilité de fusionner des
 * caisses (fusionner une caisse rouge et une jaune donne un caisse orange).<br />
 * Le jeu dispose de trois modes : Story, Arcade et Custom
 * <ul>
 * <li>Le mode Story permet de jouer seul contre l'ordi avec des énigmes qui
 * débloquent des possibilités (en évoluant dans les niveaux).</li>
 * <li>Le mode arcade qui permet de jouer solo ou en 1 contre 1 contre l'ordi
 * (IA).</li>
 * <li>Le mode custom qui charge une carte personnalisée, puis permet de jouer
 * en solo ou contre l'ordi (IA).</li>
 * </ul>
 * </p>
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class Lanceur {

	/**
	 * Déroulement logique du jeu Rainbow Robot
	 * 
	 * @param args
	 *            non utilisé
	 */
	public static void main(String[] args) {
		try {
			// select Look and Feel
			UIManager.setLookAndFeel(new AcrylLookAndFeel());
			// start application
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// On initialise la partie métier
		JeuRainbow.restartFichierPartie();
		JeuRainbow jeu = JeuRainbow.lectureFichier();

		// On construit le contrôleur à partir de la partie métier
		ClicSouris gestion = new ClicSouris(jeu);

		// On construit la fenêtre avec le contrôleur
		F_accueil fenetreActive = new F_accueil(gestion);

		// On ajoute la nouvelle fenêtre à contrôler
		gestion.setFenetre(fenetreActive);

		// On rend la fenêtre visible
		fenetreActive.setVisible(true);
		
	}
}
