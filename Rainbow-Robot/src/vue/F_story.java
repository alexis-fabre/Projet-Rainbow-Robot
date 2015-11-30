/*
 * Story.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package vue;

import javax.swing.JPanel;

import evenement.ClicSouris;

/**
 * TODO Expliquer le fonctionnement de la classe
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class F_story extends Fa_modeJeu implements ChangementLangue {

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private String cheminPhoto;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JPanel ZoneDescription;

	/**
	 * TODO Expliquer le fonctionnement du constructeur
	 * 
	 * @param titre
	 */
	public F_story(ClicSouris gestion) {
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

}