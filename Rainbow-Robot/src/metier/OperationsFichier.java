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
import java.util.Arrays;

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
		Robot robot = null;
		Vortex vortex = null;
		Caisse[] caissePlateau;
		Caisse[] resultatCaisse;
		Position[] posInacessible;
		Position[] resultatPos;
		int indice = 0;
		int parcours = 0;
		char tableau[][];
		ArrayList<Caisse> caisseARecuperer = new ArrayList<Caisse>();
		try (BufferedReader fich = new BufferedReader(new FileReader(fichier))) {
			ligne = Integer.valueOf(fich.readLine());
			colonne = Integer.valueOf(fich.readLine());

			temp = fich.readLine();

			for (int i = 0; i < temp.length(); i++) {
				if (Caisse.getCaisse(temp.charAt(i)) == -1) {
					throw new IllegalArgumentException(
							"Le fichier n'est pas bon");
				} else {
					caisseARecuperer.add(new Caisse(Caisse.getCaisse(temp
							.charAt(i))));
				}
			}

			tableau = new char[ligne][colonne];
			posInacessible = new Position[ligne * colonne];
			caissePlateau = new Caisse[ligne * colonne];
			do {
				temp = fich.readLine();
				if (temp != null && temp.length() != 0) {
					for (int i = 0; i < temp.length(); i++) {
						tableau[numLigne][i] = temp.charAt(i);
					}
					numLigne++;
				}
			} while (temp != null);

			for (int y = 0; y < tableau.length; y++) {
				System.out.println();
				for (int x = 0; x < tableau[y].length; x++) {
					switch (tableau[y][x]) {
					case 'Z':
						if (robot == null) {
							robot = new Robot(Robot.ORIENTATION_HAUT,
									new Position(x, y));
							break;
						} else {
							throw new IllegalArgumentException(
									"Le fichier n'est pas bon");
						}
					case 'Q':
						if (robot == null) {
							robot = new Robot(Robot.ORIENTATION_GAUCHE,
									new Position(x, y));
							break;
						} else {
							throw new IllegalArgumentException(
									"Le fichier n'est pas bon");
						}
					case 'W':
						if (robot == null) {
							robot = new Robot(Robot.ORIENTATION_BAS,
									new Position(x, y));
							break;
						} else {
							throw new IllegalArgumentException(
									"Le fichier n'est pas bon");
						}
					case 'S':
						if (robot == null) {
							robot = new Robot(Robot.ORIENTATION_DROITE,
									new Position(x, y));
							break;
						} else {
							throw new IllegalArgumentException(
									"Le fichier n'est pas bon");
						}
					case 'V':
						if (vortex == null) {
							vortex = new Vortex(new Position(x, y));
							break;
						} else {
							throw new IllegalArgumentException(
									"Le fichier n'est pas bon");
						}
					case 'B':
						caissePlateau[x + y * tableau[x].length] = new Caisse(
								Caisse.getCaisse('B'), new Position(x, y));
						break;
					case 'G':
						caissePlateau[x + y * tableau[x].length] = new Caisse(
								Caisse.getCaisse('G'), new Position(x, y));
						break;
					case 'R':
						caissePlateau[x + y * tableau[x].length] = new Caisse(
								Caisse.getCaisse('R'), new Position(x, y));
						break;
					case 'Y':
						caissePlateau[x + y * tableau[x].length] = new Caisse(
								Caisse.getCaisse('Y'), new Position(x, y));
						break;
					case 'O':
						caissePlateau[x + y * tableau[x].length] = new Caisse(
								Caisse.getCaisse('O'), new Position(x, y));
						break;
					case 'P':
						caissePlateau[x + y * tableau[x].length] = new Caisse(
								Caisse.getCaisse('P'), new Position(x, y));
						break;
					case 'X':
						posInacessible[x + y * tableau[x].length] = new Position(
								x, y);

						break;
					case ' ':
						break;
					}
				}
			}

			for (int i = 0; i < caissePlateau.length; i++) {
				if (caissePlateau[i] != null) {
					indice++;
				}
			}

			resultatCaisse = new Caisse[indice];
			for (int y = 0; y < caissePlateau.length; y++) {
				if (caissePlateau[y] != null) {
					resultatCaisse[parcours] = caissePlateau[y];
					parcours++;
				}

			}

			parcours = 0;
			indice = 0;
			for (int i = 0; i < posInacessible.length; i++) {
				if (posInacessible[i] != null) {
					indice++;
				}
			}
			resultatPos = new Position[indice];
			for (int y = 0; y < posInacessible.length; y++) {
				if (posInacessible[y] != null) {
					resultatPos[parcours] = posInacessible[y];
					parcours++;
				}
			}

			System.out.println(Arrays.toString(resultatCaisse));
			System.out.println(Arrays.toString(resultatPos));
			new Partie(ligne, colonne, resultatPos, robot, vortex,
					caisseARecuperer, resultatCaisse);
		} catch (IOException e) {
			System.out.println("Problème d'accès au fichier " + fichier);
		}
	}

}
