package metier.test;

import metier.Robot;

import metier.Position;

public class testRobot {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testAvancer();
		testReculer();
		testPivoter();
	}
	
	public static void testAvancer(){
		System.out.println("--------------------METHODE AVANCER-------------------\n");
		Position pos_ini = new Position(10,10);
		int orientation = Robot.ORIENTATION_BAS;
		Robot robot = new Robot(orientation,pos_ini);
		System.out.println("Position de départ \n" + robot.toString());
		robot.avancer();
		System.out.println("Position après avoir avancer \n" + robot.toString());
	}

	public static void testReculer(){
		System.out.println("--------------------METHODE RECULER-------------------\n");
		Position pos_ini = new Position(10,10);
		int orientation = Robot.ORIENTATION_BAS;
		Robot robot = new Robot(orientation,pos_ini);
		System.out.println("Position de départ \n" + robot.toString());
		robot.reculer();
		System.out.println("Position après avoir avancer \n" + robot.toString());
	}
	
	public static void testPivoter(){
		System.out.println("--------------------METHODE PIVOTER-------------------\n");
		Position pos_ini = new Position(10,10);
		
		int orientation = Robot.ORIENTATION_BAS;
		
		Robot robot = new Robot(orientation,pos_ini);
		
		int position = Robot.PIVOTER_GAUCHE;
		
		System.out.println("Position de départ pour pivoter à GAUCHE -------------\n" + robot.toString());
		
		robot.pivoter(position);
		System.out.println("Position après avoir avancer \n" + robot.toString());
		robot.pivoter(position);
		System.out.println("Position après avoir avancer \n" + robot.toString());
		robot.pivoter(position);
		System.out.println("Position après avoir avancer \n" + robot.toString());
		robot.pivoter(position);
		System.out.println("Position après avoir avancer \n" + robot.toString());
		
		int position2 = Robot.PIVOTER_DROITE;
		
		System.out.println("Position de départ pour pivoter à droite -------------\n" + robot.toString());
		
		robot.pivoter(position2);
		System.out.println("Position après avoir avancer \n" + robot.toString());
		robot.pivoter(position2);
		System.out.println("Position après avoir avancer \n" + robot.toString());
		robot.pivoter(position2);
		System.out.println("Position après avoir avancer \n" + robot.toString());
		robot.pivoter(position2);
		System.out.println("Position après avoir avancer \n" + robot.toString());
		
		
	}

}
