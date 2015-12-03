/*
 *testCaisse                       2 déc. 2015
 *Iut info2 2014/2016
 */
package metier.test;

import metier.Caisse;
import java.awt.Color;

import metier.Caisse;

/**
 * Test des différentes méthodes de la classe Caisse
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class testCaisse {

	public void fusionCouleur(Caisse c1, Caisse c2) {
		Caisse c3;

		if (c1.getCouleur() == c2.getCouleur()) {
			System.out.println("fusion impossible");
			// } else if (c1.getCouleur() == Color.RED && c2.getCouleur()) {

		}
	}

	/**
	 * TODO commenter le rôle de cette méthode A COMPLéTER
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Caisse c1 = new Caisse(Color.RED);
		Caisse c2 = new Caisse(Color.RED);
		Color c3 = Color.BLUE;
		// System.out.println(Caisse.fusionCouleur(c1,c2).getCouleur());
		// System.out.println(c3.getRGB());
		// System.out.println(Color.YELLOW);

		if (c1.getCouleur() == c2.getCouleur()) {
			System.out.println("ok");

		} else {
			System.out.println("nok");
		}
	}

}
