/*
 * testCaisse.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package metier.test;


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
	public static final int[] JEU_TEST_OK = { 1, 2, 3, 4, 5, 6 };

	/**
	 * Jeu de test comportant toutes les couleurs qui ne sont pas acceptés pour
	 * une caisse
	 */
	public static final int[] JEU_TEST_NOK = { 7, 10, 55, 8, -7 };

	/**
	 * Jeu de test comportant des couleurs valides pour la fusion 
	 */
	public static final int[] JEU_FUSION_OK1 = {1, 2, 3, 4, 5, 6};
	
	/**
	 * Jeu de test comportant des couleurs valides pour la fusion 
	 */
	public static final Caisse[] JEU_FUSION_CAISSE = { new Caisse(1), 
		   new Caisse(2),new Caisse(3), new Caisse(4), new Caisse(5), 
		   new Caisse(6)};
			

	/**
	 * Test de la fonction isCouleurOK de la classe Caisse.java
	 */
	public static void testCouleurOK() {
		System.out.println("*********************************************");
		System.out.println("Test de la méthode isColorOK");
		
		// Vérifie si les tests se sont correctements déroulé
		boolean testOK = true;

		for (int aTester : JEU_TEST_OK) {
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
		for (int aTester : JEU_TEST_NOK) {
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
	 * Test de la méthode isFusionOk
	 * Pour les résultats L1 correspond à la première ligne de la Matrice
	 * C1 correspond à la première colonne de la matrice ainsi de suite
	 * Mais dans le corps du programme les premières lignes correspondent 
	 * à l'indice 0 car se sont des tableaux 
	 */
	public static void fusionCouleur() {
		int compteur =  0;
		int i;
        int j = 0;
		
		System.out.println("\n\n*********************************************");
		System.out.println("Test de la méthode isFusionOk avec toutes les"
	    + " fusions possibles dans l'application.  "
		+"\nLes vérifications peuvent se faire via la matrice 'FUSION_COULEUR'."
		+ "\nLes fusions donnant des résultats vont s'afficher : "
		+ "ci-dessous.");
		int cpt = 0;
		// Premier argument de la méthode
		for( i = 0 ; i < JEU_FUSION_OK1.length; i++) {
            // Deuxième argument de la méthode
			for (j = 0; j < JEU_FUSION_OK1.length; j++) {
				if(Caisse.isFusionOk(JEU_FUSION_OK1[i], JEU_FUSION_OK1[j] )) {
					compteur ++; 
					System.out.println("L" + JEU_FUSION_OK1[i] + "+" 
							+ "C" + JEU_FUSION_OK1[j] + " = " 
							+ Caisse.FUSION_COULEUR[JEU_FUSION_OK1[i]-1]
									[JEU_FUSION_OK1[j]-1]);
				} else {
					cpt ++ ;
				}
			}
		}
		System.out.println("\n" + compteur + " tests OK réussis sur 8\n" 
				+ cpt +   " tests nok réussis sur 28\n"
				+ "sur un total de "+ i*j +" tests effectués");

	}
	/**
	 *   BIDON A SUPPRIMER J'AI LA FLEMME
	 */
	public static void fusionCaisse() {
			
			int a = JEU_FUSION_CAISSE[1].getCouleur();
			int b = JEU_FUSION_CAISSE[4].getCouleur();
			System.out.println(Caisse.isFusionOk(a,b));
			System.out.println(a  + " et " + b);
			System.out.println(Caisse.fusionCouleur(JEU_FUSION_CAISSE[1],
					                         JEU_FUSION_CAISSE[4]));
		
	}

	/**
	 * Programme principal lançant les différentes méthodes de tests
	 * 
	 * @param args
	 *            non utilisé
	 */
	public static void main(String[] args) {

		// On test la fonction isCouleurOK
		testCouleurOK();
		// On test la fonction isFusionOk
		fusionCouleur();
		//fusionCaisse();
	}

}
