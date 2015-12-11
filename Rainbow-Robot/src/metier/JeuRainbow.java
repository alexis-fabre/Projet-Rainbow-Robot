/*
 * JeuRainbow.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package metier;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe qui va permettre de gérer plusieurs partie et de les récupérer à
 * partir d'un fichier créer par l'utilisateur. Voir la classe Fichier.java
 * (package metier) qui permet d'initaliser un JeuRainbow à partir d'un fichier
 * 
 * @author Rainbow Robot
 */
public class JeuRainbow implements Serializable {

	/**
	 * Génerer automatiquement par Eclipse
	 */
	private static final long serialVersionUID = 1725976228595779419L;

	/** Niveau par défaut de la partie */
	public static final int DEFAULT_NIVEAU = 0;

	/** Niveau actuel du joueur */
	private int niveauCourant;

	/** Carte de la partie */
	private ArrayList<Partie> parties;

	/** Nom du fichier ou se trouve les parties jouables dans le mode solo */
	public static final String CHEMIN_FICHIER_PARTIE = "./lib/partie_mode_solo.bin";

	/**
	 * Constructeur par défaut pour créer les parties
	 */
	public JeuRainbow() {
		parties = new ArrayList<Partie>();
		niveauCourant = DEFAULT_NIVEAU;
	}

	/**
	 * Ajoute un niveau (ou une partie) pour le mode story.
	 * 
	 * @param aAjouter
	 *            nouveau niveau à ajouter
	 */
	public void addPartie(Partie aAjouter) {
		parties.add(aAjouter);
	}

	/**
	 * Pour récupérer la Carte du niveau courant
	 * 
	 * @return la carte du niveau suivant
	 */
	public Partie getPartieCourante() {
		return parties.get(niveauCourant);
	}

	/**
	 * @return le niveau courant
	 */
	public int getNiveau() {
		return niveauCourant;
	}

	/**
	 * Change le niveau courant par le niveau demandé. Si vous appelez
	 * getPartieCourante() après cette fonction vous obtiendrez la partie
	 * demandée.
	 * 
	 * @param niveau
	 *            nouveau niveau de jeu
	 */
	public void setNiveau(int niveau) {
		niveauCourant = niveau;
	}

	/**
	 * Enregistre l'état courant du jeu Rainbow Robot et de ses parties
	 * associées
	 * 
	 * @param aEnregistrer
	 *            jeu à enregistrer
	 */
	public static void enregistrerFichier(JeuRainbow aEnregistrer) {
		try (
		// On détecte le fichier avec le nom NOM_FICHIER avec un analyseur de
		// flux de sortie de données sous forme d'octets
		// On utilise un buffer pour gagner en temps
		BufferedOutputStream fichier = new BufferedOutputStream(
				new FileOutputStream(CHEMIN_FICHIER_PARTIE));

		// ObjectOutputStream permet de sérialiser un objet ou ici un graphe
		// d'objet
				ObjectOutputStream fluxSortie = new ObjectOutputStream(fichier)) {

			// On écrit l'objet dans le fichier
			fluxSortie.writeObject(aEnregistrer);

			// Avec le try-with-resources les objets ObjectOutputStream et
			// BufferedOutputStream se ferme (close()) automatiquement

		} catch (FileNotFoundException fichierInexistant) {
			// Utilisé pour le FileOutputStream
			// Si le fichier n'existe pas

		} catch (IOException donneeErronee) {
			// Utilisé pour le ObjectOutputStream
			// Si un problème avec le fichier apparaît
			// L'exception est liée à l'entrée/sortie des données sur le fichier

		}
	}

	/**
	 * Permet d'initialiser le JeuRainbow à partir d'un fichier (le nom du
	 * fichier est mentionné dans la variable interne CHEMIN_FICHIER_PARTIE
	 * 
	 * @return le JeuRainbow fournit par le fichier
	 */
	public static JeuRainbow lectureFichier() {
		JeuRainbow tampon = null;
		try (

		// On détecte le fichier avec le nom NOM_FICHIER avec un analyseur de
		// flux d'entrée de données sous forme d'octets
		// On utilise un buffer pour gagner en temps
		BufferedInputStream fichier = new BufferedInputStream(
				new FileInputStream(CHEMIN_FICHIER_PARTIE));

		// ObjectInputStream permet de lire un objet ou ici un graphe d'objet
				ObjectInputStream fluxEntree = new ObjectInputStream(fichier)) {

			// On lit l'objet du fichier et on le convertis en Agenda
			tampon = (JeuRainbow) fluxEntree.readObject();

			// Avec le try-with-resources les objets ObjectInputStream et
			// BufferedInputStream se ferme (close()) automatiquement

		} catch (FileNotFoundException fichierInexistant) {
			// Utilisé pour le FileInputStream et le BufferedInputStream
			// Si le fichier n'existe pas

		} catch (IOException donneeErronee) {
			// Utilisé pour le ObjectInputStream
			// Si un problème avec le fichier apparaît
			// L'exception est liée à l'entrée/sortie des données sur le fichier

		} catch (ClassNotFoundException classeInexistant) {
			// Si la classe n'existe plus dans l'application
		}

		return tampon;
	}

}
