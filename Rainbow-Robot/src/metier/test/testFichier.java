package metier.test;

import java.io.File;

import metier.OperationsFichier;

public class testFichier {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testPartie();
	}
	
	
	public static void testPartie(){
		OperationsFichier.recupFichier(new File("./Ressource/testFichier.txt"));
	}

}
