/*
 * Lanceur.java							28 nov. 2015
 * IUT Info2 2015-2016
 */
package vue;

import java.awt.Dimension;

import evenement.ClicSouris;

/**
 * <p>
 * D�roulement logique de Rainbow Robot. C'est une application repr�sentant un
 * jeu de type "taquin".<br >
 * Le joueur contr�le en temps r�el un petit robot qui doit aller chercher des
 * caisses color�es dans un entrep�t. Si la caisse � aller chercher est derri�re
 * d'autre caisse, il va falloir d�placer celles-ci de façon optimis�e.<br />
 * La particularit� de ce jeu r�side dans la possibilit� de fusionner des
 * caisses (fusionner une caisse rouge et une jaune donne un caisse orange).<br />
 * Le jeu dispose de trois modes : Story, Arcade et Custom
 * <ul>
 * <li>Le mode Story permet de jouer seul contre l'ordi avec des �nigmes qui
 * d�bloquent des possibilit�s (en �voluant dans les niveaux).</li>
 * <li>Le mode arcade qui permet de jouer solo ou en 1 contre 1 contre l'ordi
 * (IA).</li>
 * <li>Le mode custom qui charge une carte personnalis�e, puis permet de jouer
 * en solo ou contre l'ordi (IA).</li>
 * </ul>
 * </p>
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class Lanceur {

	/**
	 * D�roulement logique du jeu Rainbow Robot
	 * 
	 * @param args
	 *            non utilis�
	 */
	public static void main(String[] args) {
		// On initialise la partie m�tier

		// On construit le contr�leur � partir de la partie m�tier
		ClicSouris gestion = new ClicSouris();

		// On construit la fen�tre avec le contr�leur
		F_accueil fenetreActive = new F_accueil(gestion);
		fenetreActive.setVisible(true);

		// On ajoute la nouvelle fen�tre � contr�ler
		gestion.setFenetre(fenetreActive);
	}

}
