/*
 * Caisse.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package vue;

import metier.Position;

/**
 * TODO Expliquer le fonctionnement de la classe
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class Caisse {

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private static final int TAILLE_CAISSE = 1;
	
	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private String couleur;
	
	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private Position position;

	/**
	 * TODO Expliquer le fonctionnement du constructeur
	 */
	public Caisse() {
		// TODO - Cr�ation automaitque par VisualParadigm
	}

	/**
	 * TODO Expliquer le fonctionnement de la m�thode
	 * 
	 * @return
	 */
	public String getCouleur() {
		return this.couleur;
	}

	/**
	 * TODO Expliquer le fonctionnement de la m�thode
	 * 
	 * @param nouvelle
	 */
	public void setCouleur(String nouvelle) {
		this.couleur = nouvelle;
	}

}