/*
 * Custom.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package vue;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import evenement.ClicSouris;

/**
 * TODO Expliquer le fonctionnement de la classe
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class F_custom extends Fa_modeJeu implements ChangementLangue {

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JPanel ZoneDescription;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JButton parcourir;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JTextField cheminFichier;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JRadioButton jeuSolo;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JRadioButton jeuIA;

	/**
	 * TODO Expliquer le fonctionnement du constructeur
	 * 
	 * @param titre
	 */
	public F_custom(ClicSouris gestion) {
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
	 * @param chemin
	 */
	public void ouvrirFichier(String chemin) {
		// TODO - Création automaitque par VisualParadigm
	}

	/**
	 * TODO Expliquer le fonctionnement de la méthode
	 * 
	 * @return
	 */
	public int getDifficulte() {
		return 0; // bouchon
		// TODO - Création automaitque par VisualParadigm
	}
}