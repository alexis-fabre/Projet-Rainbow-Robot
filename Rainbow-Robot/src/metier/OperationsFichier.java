/* OperationsFichier.java                8 déc. 2015
 * IUT Info2 2015-2016 
 */
package metier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import metier.LigneUsernameScore;

/**
 * Classe permettant de lire et d'écrire dans le fichier highscores.txt
 * 
 * @author Rainbow Robot
 * @version 0.1
 */
public class OperationsFichier {

	/**
	 * Lis le fichier des reccords et retourne les données présentes dans une
	 * ArrayList
	 * 
	 * @param fichier
	 *            le fichier qui contient les noms des utilisateurs et les
	 *            reccords
	 * @return Une liste contenant les données
	 */
	public static ArrayList<LigneUsernameScore> lectureFichierReccord(
			File fichier) {
		String ligne = null;
		BufferedReader tampon = null;
		ArrayList<LigneUsernameScore> resultat = new ArrayList<LigneUsernameScore>();

		// ouverture fichier
		try {
			tampon = new BufferedReader(new FileReader(fichier));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		do {
			// lecture d'une ligne
			try {
				ligne = tampon.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// ajout du contenu de la ligne dans l'ArrayList
			String username;
			String score;
			if (ligne != null) {
				username = ligne.split("#")[0];
				score = ligne.split("#")[1];
				resultat.add(new LigneUsernameScore(username, score));
			}
		} while (ligne != null);

		// fermeture fichier
		try {
			tampon.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultat;
	}

	/**
	 * Ecrit les couples username/score dans le fichier
	 * 
	 * @param fichier
	 *            le fichier qui contient les noms des utilisateurs et les
	 *            reccords
	 * @param ligneUsernameScore
	 *            les nouveaux couples noms/scores a enregistré
	 */
	public static void ecrireFichierReccord(File fichier,
			ArrayList<LigneUsernameScore> ligneUsernameScore) {
		Writer tampon = null;
		try {
			tampon = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fichier)));
			for (LigneUsernameScore ligne : ligneUsernameScore) {
				tampon.write(ligne.getUsername() + "#" + ligne.getScore()
						+ "\n");
			}
			tampon.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lit un fichier et le transforme en une partie. Le fichier doit respecter
	 * certaines règles et des vérifications seront faites sur la cohérence et
	 * la resolvabilité de la partie.
	 *
	 * @param fichier
	 *            contenu de la partie
	 */
	public static void recupFichier(File fichier) {
		int numLigne = 0;
		int ligne;
		int colonne;
		String temp;
		char tableau[][];
		ArrayList<Caisse> caisseARecuperer = null;
		try (BufferedReader fich = new BufferedReader(new FileReader(fichier))) {
			ligne = Integer.valueOf(fich.readLine());
			colonne = Integer.valueOf(fich.readLine());

			temp = fich.readLine();

			for (int i = 0; i < temp.length(); i++) {
				// créer une focntion pour recuperer le nombre en fonction de la
				// lettre
				// caisseARecuperer.add(temp.charAt(i));
			}

			tableau = new char[ligne][colonne];

			do {
				temp = fich.readLine();
				if (temp != null && temp.length() != 0) {

					for (int i = 0; i < temp.length(); i++) {
						tableau[numLigne][i] = temp.charAt(i);
					}
					numLigne++;
				}
			} while (temp != null);

			new Partie(Integer.valueOf(tableau[0][0]),
					Integer.valueOf(tableau[1][0]), null, null, null, null,
					null);
		} catch (IOException e) {
			System.out.println("Probl鮥 d'acc鳠au fichier " + fichier);
		}
	}

}
