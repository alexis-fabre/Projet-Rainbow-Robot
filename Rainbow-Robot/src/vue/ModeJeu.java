/*
 * ModeJeu.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package vue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.border.Border;

/**
 * TODO Expliquer le fonctionnement de la classe
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public abstract class ModeJeu extends JFrame {

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private final String DESC_COMMANDES = "";

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private String REGLES_STORY;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private String REGLES_ARCADE;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private String REGLES_CUSTOM;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JLabel titre;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private Border bordure;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JButton bt_Retour;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JButton bt_Jouer;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JLabel lb_difficulte;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JRadioButton rb_Facile;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JRadioButton rb_Moyen;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JRadioButton rb_Difficile;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JTextArea regles;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JTextArea Commandes;

	/**
	 * TODO Expliquer le fonctionnement de la méthode
	 */
	public void retourChoixMode() {
		// TODO - Création automaitque par VisualParadigm
	}

	/**
	 * TODO Expliquer le fonctionnement de la méthode
	 */
	public abstract void jouer();

	/**
	 * TODO Expliquer le fonctionnement de la méthode
	 * 
	 * @param nouveau
	 */
	public void setTitre(String nouveau) {
		// TODO - Création automaitque par VisualParadigm
	}

	/**
	 * TODO Expliquer le fonctionnement de la méthode
	 * 
	 * @param aModifier
	 */
	public void setCheminPhoto(String aModifier) {
		// TODO - Création automaitque par VisualParadigm
	}

	/**
	 * TODO Expliquer le fonctionnement de la méthode
	 * 
	 * @param texte
	 */
	public void setRegles(String texte) {
		// TODO - Création automaitque par VisualParadigm
	}

	/**
	 * TODO Expliquer le fonctionnement de la méthode
	 * 
	 * @param texte
	 */
	public void setCommandes(String texte) {
		// TODO - Création automaitque par VisualParadigm
	}

}