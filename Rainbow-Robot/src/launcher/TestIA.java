/*
 * TestIA.java							24 févr. 2016
 * IUT Info2 2015-2016
 */
package launcher;

import java.util.ArrayList;

import metier.Caisse;
import metier.IntelligenceArtificielle;
import metier.JeuRainbow;
import metier.Partie;
import metier.Position;
import metier.Robot;
import metier.Vortex;
import vue.F_jeuRainbow;
import evenement.ClicSouris;
import evenement.ToucheClavier;

/**
 * Test de l'IA est de ces méthodes
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class TestIA {

	/**
	 * Programme testant les fonctionnalités de l'IA
	 * 
	 * @param args
	 *            non utilisé
	 */
	public static void main(String[] args) {
		Partie partie = getPartie();

		JeuRainbow jeu = new JeuRainbow();
		jeu.addPartie(partie);
		// On construit le contrôleur à partir de la partie métier
		ClicSouris gestion = new ClicSouris(jeu);

		// Détecte les appuie sur les touches de clavier
		ToucheClavier clavier = new ToucheClavier(jeu);
		// On détecte les fins de partie et les pauses
		F_jeuRainbow nouvelleFenetre = new F_jeuRainbow(gestion, clavier);
		gestion.setObserver();
		gestion.setFenetre(nouvelleFenetre);
		clavier.setFenetre(nouvelleFenetre);

		nouvelleFenetre.setVisible(true);

		// On force le jeu à se lancer
		clavier.startPartie();
		new IntelligenceArtificielle(jeu.getPartieCourante()).start();
	}

	/**
	 * @return une partie de test
	 */
	private static Partie getPartie() {
		// //
		// ---------------------------------------------------------------------
		// // 1ère partie
		// //
		// ---------------------------------------------------------------------

		// int nbLigne = 10;
		// int nbColonne = 12;
		//
		// ArrayList<Caisse> caisseARecuperee = new ArrayList<Caisse>();
		// caisseARecuperee.add(new Caisse(Caisse.JAUNE));
		// caisseARecuperee.add(new Caisse(Caisse.BLEU));
		// caisseARecuperee.add(new Caisse(Caisse.JAUNE));
		// caisseARecuperee.add(new Caisse(Caisse.VIOLET));
		// caisseARecuperee.add(new Caisse(Caisse.ROUGE));
		// caisseARecuperee.add(new Caisse(Caisse.VERT));
		//
		// Caisse[] caissePlateau = new Caisse[16];
		// caissePlateau[0] = new Caisse(Caisse.JAUNE, new Position(0, 0));
		// caissePlateau[1] = new Caisse(Caisse.JAUNE, new Position(9, 5));
		// caissePlateau[2] = new Caisse(Caisse.BLEU, new Position(1, 1));
		// caissePlateau[3] = new Caisse(Caisse.BLEU, new Position(2, 2));
		// caissePlateau[4] = new Caisse(Caisse.BLEU, new Position(4, 9));
		// caissePlateau[5] = new Caisse(Caisse.BLEU, new Position(3, 4));
		// caissePlateau[6] = new Caisse(Caisse.BLEU, new Position(8, 4));
		// caissePlateau[7] = new Caisse(Caisse.BLEU, new Position(5, 9));
		// caissePlateau[15] = new Caisse(Caisse.BLEU, new Position(3, 3));
		// caissePlateau[8] = new Caisse(Caisse.JAUNE, new Position(2, 5));
		// caissePlateau[9] = new Caisse(Caisse.VIOLET, new Position(5, 1));
		// caissePlateau[10] = new Caisse(Caisse.VERT, new Position(7, 8));
		// caissePlateau[11] = new Caisse(Caisse.VERT, new Position(8, 6));
		// caissePlateau[12] = new Caisse(Caisse.ROUGE, new Position(6, 7));
		// caissePlateau[13] = new Caisse(Caisse.ROUGE, new Position(8, 5));
		// caissePlateau[14] = new Caisse(Caisse.ROUGE, new Position(9, 3));
		//
		// Vortex vortex = new Vortex(new Position(5, 5));
		//
		// Robot robot = new Robot(Robot.ORIENTATION_DROITE, new Position(4,
		// 5));
		//
		// return new Partie(nbLigne, nbColonne, null, robot, vortex,
		// caisseARecuperee, caissePlateau);
		int nbLigne = 5;
		int nbColonne = 7;

		ArrayList<Caisse> caisseARecuperee = new ArrayList<Caisse>();
		caisseARecuperee.add(new Caisse(Caisse.ROUGE));
		caisseARecuperee.add(new Caisse(Caisse.BLEU));
		caisseARecuperee.add(new Caisse(Caisse.JAUNE));
		caisseARecuperee.add(new Caisse(Caisse.BLEU));
		caisseARecuperee.add(new Caisse(Caisse.ROUGE));
		caisseARecuperee.add(new Caisse(Caisse.JAUNE));

		Caisse[] caissePlateau = new Caisse[6];
		caissePlateau[0] = new Caisse(Caisse.JAUNE, new Position(1, 0));
		caissePlateau[1] = new Caisse(Caisse.JAUNE, new Position(6, 4));
		caissePlateau[2] = new Caisse(Caisse.BLEU, new Position(5, 0));
		caissePlateau[3] = new Caisse(Caisse.BLEU, new Position(1, 4));
		caissePlateau[4] = new Caisse(Caisse.ROUGE, new Position(2, 1));
		caissePlateau[5] = new Caisse(Caisse.ROUGE, new Position(0, 2));

		Vortex vortex = new Vortex(new Position(5, 2));

		Robot robot = new Robot(Robot.ORIENTATION_HAUT, new Position(3, 4));

		return new Partie(nbLigne, nbColonne, null, robot, vortex,
				caisseARecuperee, caissePlateau);
		// return JeuRainbow.carteAleatoire();

	}

}
