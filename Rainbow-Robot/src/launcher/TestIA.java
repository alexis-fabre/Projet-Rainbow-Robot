/*
 * TestIA.java							24 févr. 2016
 * IUT Info2 2015-2016
 */
package launcher;

import java.awt.Color;
import java.util.ArrayList;

import metier.Caisse;
import metier.IntelligenceArtificielle;
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
		new IntelligenceArtificielle(IntelligenceArtificielle.NIVEAU_FACILE,
				getPartie()).start();
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
		caisseARecuperee.add(new Caisse(Color.YELLOW));
		caisseARecuperee.add(new Caisse(Color.BLUE));
		caisseARecuperee.add(new Caisse(Color.YELLOW));
		caisseARecuperee.add(new Caisse(Color.MAGENTA));
		caisseARecuperee.add(new Caisse(Color.RED));
		caisseARecuperee.add(new Caisse(Color.GREEN));

		Caisse[] caissePlateau = new Caisse[15];
		caissePlateau[0] = new Caisse(Color.YELLOW, new Position(0, 0));
		caissePlateau[1] = new Caisse(Color.BLUE, new Position(1, 1));
		caissePlateau[2] = new Caisse(Color.BLUE, new Position(2, 2));
		caissePlateau[3] = new Caisse(Color.MAGENTA, new Position(2, 5));
		caissePlateau[4] = new Caisse(Color.BLUE, new Position(3, 4));
		caissePlateau[5] = new Caisse(Color.GREEN, new Position(7, 8));
		caissePlateau[6] = new Caisse(Color.BLUE, new Position(4, 9));
		caissePlateau[7] = new Caisse(Color.MAGENTA, new Position(5, 1));
		caissePlateau[8] = new Caisse(Color.BLUE, new Position(5, 9));
		caissePlateau[9] = new Caisse(Color.RED, new Position(6, 7));
		caissePlateau[10] = new Caisse(Color.GREEN, new Position(8, 4));
		caissePlateau[11] = new Caisse(Color.RED, new Position(8, 5));
		caissePlateau[12] = new Caisse(Color.GREEN, new Position(8, 6));
		caissePlateau[13] = new Caisse(Color.RED, new Position(9, 3));
		caissePlateau[14] = new Caisse(Color.YELLOW, new Position(9, 4));

		Vortex vortex = new Vortex(new Position(5, 5));

		Robot robot = new Robot(Robot.ORIENTATION_DROITE, new Position(5, 5));

		return new Partie(nbLigne, nbColonne, null, robot, vortex,
				caisseARecuperee, caissePlateau);
	}

}
