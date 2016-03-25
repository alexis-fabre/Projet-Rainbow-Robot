/*
 * TestFenetreJeu.java							21 févr. 2016
 * IUT Info2 2015-2016
 */
package launcher;

import java.util.ArrayList;

import metier.Caisse;
import metier.JeuRainbow;
import metier.Partie;
import metier.Position;
import metier.Robot;
import metier.Vortex;
import vue.F_jeuRainbow;
import evenement.ClicSouris;
import evenement.ToucheClavier;

/**
 * Teste la création d'une partie et lance sur la fenêtre de jeu la partie
 * configurée au préalable
 * 
 * @author Rainbow Robot
 */
public class TestFenetreJeu {

	/**
	 * Programme principal
	 * 
	 * @param args
	 *            non utilisé
	 */
	public static void main(String[] args) {
		// testConstructeurPartie();

		JeuRainbow jeu = getJeu();

		// On construit le contrôleur à partir de la partie métier
		ClicSouris gestion = new ClicSouris(jeu);

		// Détecte les appuie sur les touches de clavier
		ToucheClavier clavier = new ToucheClavier(jeu, false);
		// On détecte les fins de partie et les pauses
		F_jeuRainbow nouvelleFenetre = new F_jeuRainbow(gestion, clavier,
				F_jeuRainbow.MODE_ARCADE, F_jeuRainbow.MODE_ECRAN_SOLO_JOUEUR);
		gestion.setObserver();
		gestion.setFenetre(nouvelleFenetre);
		clavier.setFenetre(nouvelleFenetre);

		nouvelleFenetre.setVisible(true);
	}

	/**
	 * @return une référence sur un jeu contenant une partie
	 */
	public static JeuRainbow getJeu() {
		JeuRainbow jeu = new JeuRainbow();

		// ---------------------------------------------------------------------
		// 1ère partie
		// ---------------------------------------------------------------------
		int nbLigne = 3;
		int nbColonne = 3;

		Position[] positionsInaccessibles = new Position[1];
		positionsInaccessibles[0] = new Position(0, 0);

		ArrayList<Caisse> caisseARecuperee = new ArrayList<Caisse>();
		caisseARecuperee.add(new Caisse(2));

		Caisse[] caissePlateau = new Caisse[1];
		caissePlateau[0] = new Caisse(2, new Position(1, 1));

		Vortex vortex = new Vortex(new Position(2, 1));

		Robot robot = new Robot(Robot.ORIENTATION_DROITE, new Position(0, 1));

		Partie partie = new Partie(nbLigne, nbColonne, null, robot, vortex,
				caisseARecuperee, caissePlateau, false);

		jeu.addPartie(partie);

		return jeu;
	}

	public static void testConstructeurPartie() {
		System.out.println("Test constructeur Partie : ");
		// Variable de test avec des valeurs cohérentes
		int nbLigne = 9;
		int nbColonne = 11;
		// Position inaccessible
		Position[] positionsInaccessibles = new Position[4];
		positionsInaccessibles[0] = new Position(0, 0);
		positionsInaccessibles[1] = new Position(0, 1);
		positionsInaccessibles[2] = new Position(1, 0);
		positionsInaccessibles[3] = new Position(1, 1);
		// Caisse à récupérer
		ArrayList<Caisse> caisseARecuperee = new ArrayList<Caisse>();
		caisseARecuperee.add(new Caisse(Caisse.JAUNE));
		caisseARecuperee.add(new Caisse(Caisse.BLEU));
		// Caisse sur le plateau de jeu
		Caisse[] caissePlateau = new Caisse[4];
		caissePlateau[0] = new Caisse(Caisse.ROUGE, new Position(3, 4));
		caissePlateau[1] = new Caisse(Caisse.BLEU, new Position(5, 5));
		caissePlateau[2] = new Caisse(Caisse.JAUNE, new Position(4, 4));
		caissePlateau[3] = new Caisse(Caisse.ORANGE, new Position(2, 2));
		// Vortex
		Vortex vortex = new Vortex(new Position(8, 5));
		// Robot
		Robot robot = new Robot(Robot.ORIENTATION_DROITE, new Position(0, 4));

		// Vérifie si le jeu de test se déroule correctement
		boolean testOK = true;

		try {
			// Test Valide
			new Partie(nbLigne, nbColonne, positionsInaccessibles, robot,
					vortex, caisseARecuperee, caissePlateau, false);
		} catch (IllegalArgumentException e) {
			System.out.println("ERREUR : Jeu de test de base incorrect");
			e.printStackTrace();
		}

		// --------------------------------------------------------------------
		// Test avec des nombres de lignes et de colonne CORRECT
		// --------------------------------------------------------------------
		try {
			new Partie(Partie.NB_LIGNE_MAX, nbColonne, positionsInaccessibles,
					robot, vortex, caisseARecuperee, caissePlateau, false);
		} catch (IllegalArgumentException e) {
			testOK = false;
			System.out.println("ERREUR : Impossibilité de créer une partie "
					+ "avec un nombre de ligne égal au nombre de ligne "
					+ "maximal");
		}
		try {
			new Partie(nbLigne, Partie.NB_COLONNE_MAX, positionsInaccessibles,
					robot, vortex, caisseARecuperee, caissePlateau, false);
		} catch (IllegalArgumentException e) {
			testOK = false;
			System.out.println("ERREUR : Impossibilité de créer une partie "
					+ "avec un nombre de colonne égal au nombre de colonne "
					+ "maximal");
		}

		// --------------------------------------------------------------------
		// Test avec des nombres de lignes et de colonne INCORRECT
		// --------------------------------------------------------------------
		try {
			new Partie(Partie.NB_LIGNE_MIN - 1, nbColonne,
					positionsInaccessibles, robot, vortex, caisseARecuperee,
					caissePlateau, false);
			testOK = false;
			System.out.println("ERREUR : Possibilité de créer une partie avec "
					+ "un nombre de ligne de 0");
		} catch (IllegalArgumentException e) {
		}
		try {
			new Partie(nbLigne, Partie.NB_COLONNE_MIN - 1,
					positionsInaccessibles, robot, vortex, caisseARecuperee,
					caissePlateau, false);
			testOK = false;
			System.out.println("ERREUR : Possibilité de créer une partie avec "
					+ "un nombre de colonne de 0");
		} catch (IllegalArgumentException e) {
		}
		try {
			new Partie(Partie.NB_LIGNE_MAX + 1, nbColonne,
					positionsInaccessibles, robot, vortex, caisseARecuperee,
					caissePlateau, false);
			testOK = false;
			System.out.println("ERREUR : Possibilité de créer une partie avec "
					+ "un nombre de ligne supérieur au nombre "
					+ "maximal de ligne");
		} catch (IllegalArgumentException e) {
		}
		try {
			new Partie(nbLigne, Partie.NB_COLONNE_MAX + 1,
					positionsInaccessibles, robot, vortex, caisseARecuperee,
					caissePlateau, false);
			testOK = false;
			System.out.println("ERREUR : Possibilité de créer une partie avec "
					+ "un nombre de colonne supérieur au nombre "
					+ "maximal de colonne");
		} catch (IllegalArgumentException e) {
		}

		System.out.println("\tSur le nombre de ligne ou de colonne : "
				+ (testOK ? "OK" : "ERREUR"));

		// --------------------------------------------------------------------
		// Test avec des positions inaccessibles INCORRECT
		// --------------------------------------------------------------------
		// On réinitialise la variable de test
		testOK = true;
		{
			Position[] positionsInaccessiblesErronees = new Position[4];

			// Test avec une position en dehors du plateau de jeu
			try {
				positionsInaccessiblesErronees[0] = new Position(-1, 0);
				positionsInaccessiblesErronees[1] = new Position(0, 1);
				positionsInaccessiblesErronees[2] = new Position(1, 0);
				positionsInaccessiblesErronees[3] = new Position(1, 1);
				new Partie(nbLigne, nbColonne, positionsInaccessiblesErronees,
						robot, vortex, caisseARecuperee, caissePlateau, false);
				testOK = false;
				System.out
						.println("ERREUR : Possibilité de créer une partie avec "
								+ "une position inaccessible en dehors du plateau de jeu");
			} catch (IllegalArgumentException e) {
			}

			// Test avec une position null
			try {
				positionsInaccessiblesErronees[0] = new Position(0, 0);
				positionsInaccessiblesErronees[1] = null;
				positionsInaccessiblesErronees[2] = new Position(1, 0);
				positionsInaccessiblesErronees[3] = new Position(1, 1);
				new Partie(nbLigne, nbColonne, positionsInaccessiblesErronees,
						robot, vortex, caisseARecuperee, caissePlateau, false);
				testOK = false;
				System.out
						.println("ERREUR : Possibilité de créer une partie avec "
								+ "une position non référencée (null)");
			} catch (IllegalArgumentException e) {
			}

			// Test avec deux positions inaccessibles à la même position
			try {
				positionsInaccessiblesErronees[0] = new Position(0, 0);
				positionsInaccessiblesErronees[1] = new Position(0, 1);
				positionsInaccessiblesErronees[2] = new Position(1, 0);
				positionsInaccessiblesErronees[3] = new Position(0, 0);
				new Partie(nbLigne, nbColonne, positionsInaccessiblesErronees,
						robot, vortex, caisseARecuperee, caissePlateau, false);
				testOK = false;
				System.out
						.println("ERREUR : Possibilité de créer une partie avec "
								+ "deux positions inaccessibles sur la même position");
			} catch (IllegalArgumentException e) {
			}
		}

		System.out.println("\tSur les positions inaccessibles : "
				+ (testOK ? "OK" : "ERREUR"));

		// --------------------------------------------------------------------
		// Test avec des caisses sur le plateau de jeu INCORRECT
		// --------------------------------------------------------------------
		// On réinitialise la variable de test
		testOK = true;
		{
			Caisse[] caissePlateauErronee = new Caisse[4];

			// Test avec une caisse null
			try {
				caissePlateauErronee[0] = new Caisse(1, //
						new Position(3, 4));
				caissePlateauErronee[1] = new Caisse(5, //
						new Position(5, 5));
				caissePlateauErronee[2] = null;
				caissePlateauErronee[3] = new Caisse(6,//
						new Position(2, 2));
				new Partie(nbLigne, nbColonne, positionsInaccessibles, robot,
						vortex, caisseARecuperee, caissePlateauErronee, false);
				testOK = false;
				System.out
						.println("ERREUR : Possibilité de créer une partie avec "
								+ "une caisse non référencée (null)");
			} catch (IllegalArgumentException e) {
			}

			// Test avec une position en dehors du plateau de jeu
			try {
				caissePlateauErronee[0] = new Caisse(1, //
						new Position(3, 4));
				caissePlateauErronee[1] = new Caisse(5, //
						new Position(5, 5));
				caissePlateauErronee[2] = new Caisse(2,//
						new Position(4, Partie.NB_LIGNE_MAX + 1));
				caissePlateauErronee[3] = new Caisse(6,//
						new Position(2, 2));
				new Partie(nbLigne, nbColonne, positionsInaccessibles, robot,
						vortex, caisseARecuperee, caissePlateauErronee, false);
				testOK = false;
				System.out
						.println("ERREUR : Possibilité de créer une partie avec "
								+ "une caisse en dehors du plateau de jeu");
			} catch (IllegalArgumentException e) {
			}

			// Test avec une caisse sur une position inaccessible
			try {
				caissePlateauErronee[0] = new Caisse(1, //
						new Position(3, 4));
				caissePlateauErronee[1] = new Caisse(5, //
						new Position(5, 5));
				caissePlateauErronee[2] = new Caisse(2,//
						new Position(0, 0));
				caissePlateauErronee[3] = new Caisse(6,//
						new Position(5, 5));
				new Partie(nbLigne, nbColonne, positionsInaccessibles, robot,
						vortex, caisseARecuperee, caissePlateauErronee, false);
				testOK = false;
				System.out
						.println("ERREUR : Possibilité de créer une partie avec "
								+ "une caisse sur une position inaccessible");
			} catch (IllegalArgumentException e) {
			}

			// Test avec deux caisses à la même position
			try {
				caissePlateauErronee[0] = new Caisse(1, //
						new Position(3, 4));
				caissePlateauErronee[1] = new Caisse(5, //
						new Position(5, 5));
				caissePlateauErronee[2] = new Caisse(2,//
						new Position(3, 4));
				caissePlateauErronee[3] = new Caisse(6,//
						new Position(5, 5));
				new Partie(nbLigne, nbColonne, positionsInaccessibles, robot,
						vortex, caisseARecuperee, caissePlateauErronee, false);
				testOK = false;
				System.out
						.println("ERREUR : Possibilité de créer une partie avec "
								+ "deux caisses sur la même position");
			} catch (IllegalArgumentException e) {
			}
		}

		System.out.println("\tSur les caisses du plateau de jeu : "
				+ (testOK ? "OK" : "ERREUR"));

		// --------------------------------------------------------------------
		// Test avec des caisses à récupérer INCORRECT
		// --------------------------------------------------------------------
		// On réinitialise la variable de test
		testOK = true;
		{
			// caissePlateau[0] = new Caisse(Color.RED, new Position(3, 4));
			// caissePlateau[1] = new Caisse(Color.BLUE, new Position(5, 5));
			// caissePlateau[2] = new Caisse(Color.YELLOW, new Position(4, 4));
			// caissePlateau[3] = new Caisse(Color.ORANGE, new Position(2, 2));
			ArrayList<Caisse> caisseARecupereeErronee = new ArrayList<Caisse>();

			// Test avec une caisse à récupérer null
			try {
				caisseARecupereeErronee.add(new Caisse(2));
				caisseARecupereeErronee.add(null);
				caisseARecupereeErronee.add(new Caisse(6));
				new Partie(nbLigne, nbColonne, positionsInaccessibles, robot,
						vortex, caisseARecupereeErronee, caissePlateau, false);
				testOK = false;
				System.out
						.println("ERREUR : Possibilité de créer une partie avec "
								+ "une caisse à récupérer non référencée (null)");
			} catch (IllegalArgumentException e) {
			}
			caisseARecupereeErronee.clear();

			// Test avec plus de caisses à récupérer que de caisse sur le
			// plateau de jeu
			try {
				caisseARecupereeErronee.add(new Caisse(2));
				caisseARecupereeErronee.add(new Caisse(5));
				caisseARecupereeErronee.add(new Caisse(6));
				caisseARecupereeErronee.add(new Caisse(1));
				caisseARecupereeErronee.add(new Caisse(4));
				new Partie(nbLigne, nbColonne, positionsInaccessibles, robot,
						vortex, caisseARecupereeErronee, caissePlateau, false);
				testOK = false;
				System.out
						.println("ERREUR : Possibilité de créer une partie avec "
								+ "plus de caisse à récupérer que de caisse sur le plateau de jeu");
			} catch (IllegalArgumentException e) {
			}
			caisseARecupereeErronee.clear();

			// Test avec des caisses à récupérer impossible à récupérer
			try {
				caisseARecupereeErronee.add(new Caisse(5));
				caisseARecupereeErronee.add(new Caisse(3));
				new Partie(nbLigne, nbColonne, positionsInaccessibles, robot,
						vortex, caisseARecupereeErronee, caissePlateau, false);
				testOK = false;
				System.out
						.println("ERREUR : Possibilité de créer une partie avec "
								+ "une des caisses à récupérer qui sera impossible à récupérer");
			} catch (IllegalArgumentException e) {
			}
			caisseARecupereeErronee.clear();

			// TODO Faire le test avec la fusion de couleur
		}

		System.out.println("\tSur les caisses à récupérer : "
				+ (testOK ? "OK" : "ERREUR"));

	}
}
