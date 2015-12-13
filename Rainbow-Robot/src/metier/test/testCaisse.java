/*
 * testCaisse.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package metier.test;

import java.awt.Color;

import metier.Caisse;

/**
 * Test des différentes méthodes de la classe Caisse
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class testCaisse {

	/**
	 * Jeu de test comportant toutes les couleurs qui sont acceptés pour une
	 * caisse
	 */
	public static final Color[] JEU_TEST_OK = { Color.RED, Color.YELLOW,
			Color.MAGENTA, Color.GREEN, Color.BLUE, Color.ORANGE };

	/**
	 * Jeu de test comportant toutes les couleurs qui ne sont pas acceptés pour
	 * une caisse
	 */
	public static final Color[] JEU_TEST_NOK = { Color.CYAN, Color.GRAY,
			Color.BLACK, Color.DARK_GRAY, Color.PINK };

	/**
	 * Test de la fonction isCouleurOK de la classe Caisse.java
	 */
	public static void testCouleurOK() {
		// Vérifie si les tests se sont correctements déroulés
		boolean testOK = true;

		for (Color aTester : JEU_TEST_OK) {
			if (!Caisse.isColorOK(aTester)) {
				testOK = false;
				System.out.println("La couleur : " + aTester
						+ " devrait être valide");
			}
		}

		System.out.println("Couleur valide avec un jeu de test"
				+ " de couleur valide : " + (testOK ? "OK" : "Problème"));

		// On réinitialise le jeu de test
		testOK = true;
		for (Color aTester : JEU_TEST_NOK) {
			if (Caisse.isColorOK(aTester)) {
				testOK = false;
				System.out.println("La couleur : " + aTester
						+ " devrait être invalide");
			}
		}

		System.out.println("Couleur valide avec un jeu de test"
				+ " de couleur invalide : " + (testOK ? "OK" : "Problème"));

	}

	/**
	 * Test de la méthode fusionCouleur
	 * 
	 * @param c1
	 *            1ère couleur a fusionné
	 * @param c2
	 *            2ème couleur a fusionné
	 */
	public void fusionCouleur(Caisse c1, Caisse c2) {
		// Caisse c3;

		if (c1.getCouleur() == c2.getCouleur()) {
			System.out.println("fusion impossible");
			// } else if (c1.getCouleur() == Color.RED && c2.getCouleur()) {

		}
	}

	/**
	 * Programme principal lançant les différentes méthodes de tests
	 * 
	 * @param args
	 *            non utilisé
	 */
	public static void main(String[] args) {
		Caisse c1 = new Caisse(Color.RED);
		Caisse c2 = new Caisse(Color.RED);
		// Color c3 = Color.BLUE;
		// System.out.println(Caisse.fusionCouleur(c1,c2).getCouleur());
		// System.out.println(c3.getRGB());
		// System.out.println(Color.YELLOW);

		if (c1.getCouleur() == c2.getCouleur()) {
			System.out.println("ok");

		} else {
			System.out.println("nok");
		}

		// On test la fonction isCouleurOK
		testCouleurOK();
	}

}
