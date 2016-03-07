/*
 * TestIA.java							24 févr. 2016
 * IUT Info2 2015-2016
 */
package launcher;

import java.util.ArrayList;

import vue.F_jeuRainbow;
import evenement.ClicSouris;
import evenement.ToucheClavier;
import metier.Caisse;
import metier.IntelligenceArtificielle;
import metier.JeuRainbow;
import metier.Partie;
import metier.Position;
import metier.Robot;
import metier.Vortex;

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
		new IntelligenceArtificielle(jeu.getPartieCourante()).start();
	}

	/**
	 * @return une partie de test
	 */
	private static Partie getPartie() {
		// ---------------------------------------------------------------------
		// 1ère partie
		// ---------------------------------------------------------------------
		int nbLigne = 10;
		int nbColonne = 10;

		ArrayList<Caisse> caisseARecuperee = new ArrayList<Caisse>();
		caisseARecuperee.add(new Caisse(2));
		caisseARecuperee.add(new Caisse(5));
		caisseARecuperee.add(new Caisse(2));
		caisseARecuperee.add(new Caisse(3));
		caisseARecuperee.add(new Caisse(1));
		caisseARecuperee.add(new Caisse(4));

		Caisse[] caissePlateau = new Caisse[15];
		caissePlateau[0] = new Caisse(2, new Position(0, 0));
		caissePlateau[1] = new Caisse(5, new Position(1, 1));
		caissePlateau[2] = new Caisse(5, new Position(2, 2));
		caissePlateau[3] = new Caisse(3, new Position(2, 5));
		caissePlateau[4] = new Caisse(5, new Position(3, 4));
		caissePlateau[5] = new Caisse(4, new Position(7, 8));
		caissePlateau[6] = new Caisse(5, new Position(4, 9));
		caissePlateau[7] = new Caisse(3, new Position(5, 1));
		caissePlateau[8] = new Caisse(5, new Position(5, 9));
		caissePlateau[9] = new Caisse(1, new Position(6, 7));
		caissePlateau[10] = new Caisse(5, new Position(8, 4));
		caissePlateau[11] = new Caisse(1, new Position(8, 5));
		caissePlateau[12] = new Caisse(4, new Position(8, 6));
		caissePlateau[13] = new Caisse(1, new Position(9, 3));
		caissePlateau[14] = new Caisse(2, new Position(9, 4));

		Vortex vortex = new Vortex(new Position(5, 5));

		Robot robot = new Robot(Robot.ORIENTATION_DROITE, new Position(5, 5));

		return new Partie(nbLigne, nbColonne, null, robot, vortex,
				caisseARecuperee, caissePlateau);
	}

}
