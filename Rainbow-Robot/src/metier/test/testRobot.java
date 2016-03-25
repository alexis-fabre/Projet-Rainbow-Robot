/*
 * testRobot.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package metier.test;

import metier.Partie;
import metier.Robot;
import metier.Position;

/**
 * Test des différentes méthodes de la classe Robot
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class testRobot {

	/**
	 * Programme principal lançant les différentes méthodes de tests
	 * 
	 * @param args
	 *            non utilisé
	 */
	public static void main(String[] args) {
		testAvancer();
		testReculer();
		testPivoter();
	}

	/**
	 * Test de la méthode avancer
	 */
	@SuppressWarnings("deprecation")
	public static void testAvancer() {
		System.out
				.println("--------------------METHODE AVANCER-------------------\n");
		Position pos_ini = new Position(1, 0);
		int orientation = Robot.ORIENTATION_BAS;
		Partie partie = new Partie(9, 11);
		Robot robot = new Robot(orientation, pos_ini);
		robot.setPartie(partie);
		System.out.println("Position de départ \n" + robot.toString());
		robot.avancer();
		System.out
				.println("Position après avoir avancer \n" + robot.toString());
	}

	/**
	 * Test de la méthode reculer
	 */
	@SuppressWarnings("deprecation")
	public static void testReculer() {
		System.out
				.println("--------------------METHODE RECULER-------------------\n");
		Position pos_ini = new Position(1, 0);
		int orientation = Robot.ORIENTATION_BAS;
		Partie partie = new Partie(9, 11);
		Robot robot = new Robot(orientation, pos_ini);
		robot.setPartie(partie);
		System.out.println("Position de départ \n" + robot.toString());
		robot.reculer();
		System.out
				.println("Position après avoir avancer \n" + robot.toString());
	}

	/**
	 * Test de la méthode pivoter
	 */
	@SuppressWarnings("deprecation")
	public static void testPivoter() {
		System.out
				.println("--------------------METHODE PIVOTER-------------------\n");
		Position pos_ini = new Position(1, 0);

		int orientation = Robot.ORIENTATION_BAS;

		Partie partie = new Partie(9, 11);
		Robot robot = new Robot(orientation, pos_ini);
		robot.setPartie(partie);

		int position = Robot.PIVOTER_GAUCHE;

		System.out
				.println("Position de départ pour pivoter à GAUCHE -------------\n"
						+ robot.toString());

		robot.pivoter(position);
		System.out
				.println("Position après avoir avancer \n" + robot.toString());
		robot.pivoter(position);
		System.out
				.println("Position après avoir avancer \n" + robot.toString());
		robot.pivoter(position);
		System.out
				.println("Position après avoir avancer \n" + robot.toString());
		robot.pivoter(position);
		System.out
				.println("Position après avoir avancer \n" + robot.toString());

		int position2 = Robot.PIVOTER_DROITE;

		System.out
				.println("Position de départ pour pivoter à droite -------------\n"
						+ robot.toString());

		robot.pivoter(position2);
		System.out
				.println("Position après avoir avancer \n" + robot.toString());
		robot.pivoter(position2);
		System.out
				.println("Position après avoir avancer \n" + robot.toString());
		robot.pivoter(position2);
		System.out
				.println("Position après avoir avancer \n" + robot.toString());
		robot.pivoter(position2);
		System.out
				.println("Position après avoir avancer \n" + robot.toString());
	}

}
