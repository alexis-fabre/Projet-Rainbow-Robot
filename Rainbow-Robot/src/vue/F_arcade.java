/*
 * Arcade.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package vue;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import evenement.ClicSouris;

/**
 * TODO Expliquer le fonctionnement de la classe
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class F_arcade extends F_abstractModeJeu implements ChangementLangue {

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JPanel ZoneDescription;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private String cheminPhoto;
	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JRadioButton jeuSolo;
	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JRadioButton jeuIA;

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
	 * TODO Expliquer le fonctionnement du constructeur
	 * 
	 * @param titre
	 */
	public F_arcade(ClicSouris gestion) {
		// TODO - Création automaitque par VisualParadigm
		super(gestion);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see vue.Fa_modeJeu#setLangue()
	 */
	public void setLangue() {
		super.setLangue();
	}

	/**
	 * TODO Expliquer le fonctionnement de la méthode
	 * 
	 * @return
	 */
	public int getDifficulte() {
		return 0;
		// TODO - Création automaitque par VisualParadigm
	}
}