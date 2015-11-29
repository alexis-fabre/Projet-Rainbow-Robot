package metier.test;

import metier.Robot;

import metier.Position;

public class testRobot {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static void testAvancer(){
		
		Position pos_ini = new Position(10,10);
		int orientation = Robot.ORIENTATION_GAUCHE;
		Robot robot = new Robot(orientation,pos_ini);
		
	}


}
