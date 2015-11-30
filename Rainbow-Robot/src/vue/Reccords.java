/*
 * Reccords.java							é8 nov é015
 * IUT Info2 2015-é016
 */
package vue;

import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import evenement.ClicSouris;

/**
 * TODO Expliquer le fonctionnement de la classe
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class Reccords extends JFrame {

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JLabel titre;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JButton bt_retour;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JLabel nomJoueur;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JLabel Score;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JLabel[] leJoueur;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JLabel[] leScore;

	/**
	 * TODO Expliquer le fonctionnement du constructeur
	 * 
	 * @param gestion
	 */
	public Reccords(ClicSouris gestion) {
		super();
	}

	/**
	 * TODO Expliquer le fonctionnement de la méthode
	 * 
	 * @param nomFichier
	 */
	public void recupNomScore(String nomFichier) {
		// TODO - Création automaitque par VisualParadigm
	}

}