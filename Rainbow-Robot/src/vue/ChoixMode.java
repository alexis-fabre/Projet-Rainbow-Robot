/*
 * ChoixMode.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package vue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import evenement.ClicSouris;

/**
 * TODO Expliquer le fonctionnement de la classe
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class ChoixMode extends JFrame {

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private String titre;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JButton bt_Story;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JButton bt_Arcade;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JButton bt_Custom;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JButton bt_Menu;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JTextArea desc_Story;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JTextArea desc_Arcade;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JTextArea desc_Custom;

	/**
	 * TODO Expliquer le fonctionnement du constructeur
	 * 
	 * @param gestion
	 */
	public ChoixMode(ClicSouris gestion) {
		// TODO - Cr�ation automaitque par VisualParadigm
	}

	/**
	 * TODO Expliquer le fonctionnement de la m�thode
	 */
	public void retourAccueil() {
		// TODO - Cr�ation automaitque par VisualParadigm
	}

	/**
	 * TODO Expliquer le fonctionnement de la m�thode
	 * 
	 * @return
	 */
	public String getChoix() {
		return titre;
		// TODO - Cr�ation automaitque par VisualParadigm
	}
}