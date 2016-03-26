/* OperationsFichier.java                8 dÃ©c. 2015
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

import vue.F_records;

/**
 * Classe permettant de lire et d'écrire dans les fichiers highscore.txt
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
				if (username.length() > F_records.NB_MAX_CARACT_PSEUDO) {
				    username = username.substring(0, F_records.NB_MAX_CARACT_PSEUDO);
				}
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
	 * @return la partie créee grâce au fichier
	 * @throws IllegalArgumentException
	 *             si le fichier n'est pas correct. On initialise avec la nature
	 *             de l'erreur
	 */
	public static Partie recupFichier(File fichier)
			throws IllegalArgumentException {
		Partie partie = null;
		int numLigne = 0;
		int ligne; // nombre de ligne pour une carte
		int colonne; // nombre de colonne pour une carte
		String temp; // caisse à récupérer
		Robot robot = null;
		Vortex vortex = null;
		Caisse[] caissePlateau; // tableau de la map avec les caisses
		Caisse[] resultatCaisse; // tableau de la map avec les caisses mais sans
									// les null
		Position[] posInacessible;// tableau des positions inacessibles
		Position[] resultatPos;// tableau des positions inacessibles mais sans
								// les null
		int indice = 0;
		int parcours = 0;
		char tableau[][]; // tableau qui enregistre les ligne a partir de la
							// 4ieme ligne
		ArrayList<Caisse> caisseARecuperer = new ArrayList<Caisse>();
		try (BufferedReader fich = new BufferedReader(new FileReader(fichier))) {
			ligne = Integer.valueOf(fich.readLine()); // on recupere le nombre
														// de ligne dans la
														// premiere ligne du
														// fichier
			colonne = Integer.valueOf(fich.readLine());// on recupere le nombre
														// de colonne dans la
														// premiere ligne du
														// fichier
			temp = fich.readLine(); // On recupere les caisses a recuperer dans
									// la troisieme ligne

			for (int i = 0; i < temp.length(); i++) { // parcours de la ligne
														// temp
				if (Caisse.getCaisse(temp.charAt(i)) == -1) {
					throw new IllegalArgumentException(
							"Le fichier n'est pas bon");
				} else {
					caisseARecuperer.add(new Caisse(Caisse.getCaisse(temp
							.charAt(i)))); // ajout dans l'arrayList
											// caisseARecuperer
				}
			}

			tableau = new char[ligne][colonne];
			posInacessible = new Position[ligne * colonne];
			caissePlateau = new Caisse[ligne * colonne];
			do { // on parcours le fichier tant qu'on ne trouve pas une ligne
					// vide
				temp = fich.readLine();
				if (temp != null && temp.length() != 0) {
					for (int i = 0; i < temp.length(); i++) { // on parcours la
																// ligne i
						tableau[numLigne][i] = temp.charAt(i); // on ajoute le
																// caractere
																// dans
																// tableau
					}
					numLigne++;
				}
			} while (temp != null);

			for (int y = 0; y < tableau.length; y++) { // On parcours le tableau
														// de caractere
				for (int x = 0; x < tableau[y].length; x++) {
					switch (tableau[y][x]) { // on regarde quelle caractere est
												// a la position yx
					case 'Z': // char Z correspond au robot orienté vers le haut
						if (robot == null) {
							robot = new Robot(Robot.ORIENTATION_HAUT,
									new Position(x, y));
							break;
						} else {
							throw new IllegalArgumentException(
									"Le fichier n'est pas bon");
						}
					case 'Q': // char Q correspond au robot orienté vers la
								// gauche
						if (robot == null) {
							robot = new Robot(Robot.ORIENTATION_GAUCHE,
									new Position(x, y));
							break;
						} else {
							throw new IllegalArgumentException(
									"Le fichier n'est pas bon");
						}
					case 'W': // char W correspond au robot orienté vers le bas
						if (robot == null) {
							robot = new Robot(Robot.ORIENTATION_BAS,
									new Position(x, y));
							break;
						} else {
							throw new IllegalArgumentException(
									"Le fichier n'est pas bon");
						}
					case 'S': // char S correspond au robot orienté vers la
								// droite
						if (robot == null) {
							robot = new Robot(Robot.ORIENTATION_DROITE,
									new Position(x, y));
							break;
						} else {
							throw new IllegalArgumentException(
									"Le fichier n'est pas bon");
						}
					case 'V': // char V correspond au vortex
						if (vortex == null) {
							vortex = new Vortex(new Position(x, y));
							break;
						} else {
							throw new IllegalArgumentException(
									"Le fichier n'est pas bon");
						}
					case 'B': // char B correspond a une caisse de couleur bleu
						caissePlateau[x + y * tableau[y].length] = new Caisse(
								Caisse.getCaisse('B'), new Position(x, y));
						break;
					case 'G':// char G correspond a une caisse de couleur verte
						caissePlateau[x + y * tableau[y].length] = new Caisse(
								Caisse.getCaisse('G'), new Position(x, y));
						break;
					case 'R':// char R correspond a une caisse de couleur rouge
						caissePlateau[x + y * tableau[y].length] = new Caisse(
								Caisse.getCaisse('R'), new Position(x, y));
						break;
					case 'Y':// char Y correspond a une caisse de couleur jaune
						caissePlateau[x + y * tableau[y].length] = new Caisse(
								Caisse.getCaisse('Y'), new Position(x, y));
						break;
					case 'O':// char O correspond a une caisse de couleur orange
						caissePlateau[x + y * tableau[y].length] = new Caisse(
								Caisse.getCaisse('O'), new Position(x, y));
						break;
					case 'P':// char P correspond a une caisse de couleur violet
						caissePlateau[x + y * tableau[y].length] = new Caisse(
								Caisse.getCaisse('P'), new Position(x, y));
						break;
					case 'X':// char B correspond a une case inacessible
						posInacessible[x + y * tableau[y].length] = new Position(
								x, y);
						break;
					case ' ': // char espace correspond a une case vide
						break;
					}
				}
			}

			for (int i = 0; i < caissePlateau.length; i++) { // On compte le
																// nombre de
																// caisse
																// sur le
																// Plateau
				if (caissePlateau[i] != null) {
					indice++;
				}
			}

			resultatCaisse = new Caisse[indice]; // on initialise le tableau
													// avec le bon nombre de
													// caisse
			for (int y = 0; y < caissePlateau.length; y++) { // on ajoute les
																// caisse dans
																// le
																// tableau
																// resultatCaisse
				if (caissePlateau[y] != null) {
					resultatCaisse[parcours] = caissePlateau[y];
					parcours++;
				}
			}

			parcours = 0;
			indice = 0;
			// on fait pareril pour les positions inaccessibles
			for (int i = 0; i < posInacessible.length; i++) {
				if (posInacessible[i] != null) {
					indice++;
				}
			}
			resultatPos = new Position[indice]; // on fait pareil pour les
												// positions inacessibles
			for (int y = 0; y < posInacessible.length; y++) {
				if (posInacessible[y] != null) {
					resultatPos[parcours] = posInacessible[y];
					parcours++;
				}
			}
			// on crée la partie
			partie = new Partie(ligne, colonne, resultatPos, robot, vortex,
					caisseARecuperer, resultatCaisse, false);
		} catch (IOException e) {
			throw new IllegalArgumentException("Problème d'accès au fichier : "
					+ fichier);
		}
		return partie;
	}
}
