/*
 * Story.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import evenement.ClicSouris;

/**
 * TODO Expliquer le fonctionnement de la classe
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class F_story extends F_abstractModeJeu implements ChangementLangue {

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private String cheminPhoto;

	/**
	 * TODO Expliquer le fonctionnement du constructeur
	 * 
	 * @param titre
	 */
	public F_story(ClicSouris gestion) {
		super(gestion);

		// ---------------------------------------------------------------------
		// Contient tous les composants de la fenêtre story + les JLabels
		// Description mode et règles de F_abstractModeJeu
		// ---------------------------------------------------------------------
		JPanel contentStory = new JPanel();
		contentStory.setLayout(new BoxLayout(contentStory, BoxLayout.X_AXIS));

		// ---------------------------------------------------------------------
		// Contient la photo
		// ---------------------------------------------------------------------
		JPanel contentPhoto = new JPanel();
		UtilitaireFenetre.setAllSize(contentPhoto, 250, 350);
		contentPhoto.setBorder(BorderFactory.createLineBorder(Color.black));
		contentPhoto.setAlignmentY(CENTER_ALIGNMENT);

		// ---------------------------------------------------------------------
		// Contient la description + les règles
		// ---------------------------------------------------------------------
		JPanel contentDescReg = new JPanel();
		// On force les dimensions au 2/3 de la fenêtre sur la largeur.
		// Pour la hauteur on prend la toutes la hauteur disponible (elle sera
		// redimensionné lorsqu'on l'ajoutera au contentStory)
		contentDescReg.setMinimumSize(new Dimension(
				2 * UtilitaireFenetre.DIM_FENETRE.width / 3,
				UtilitaireFenetre.DIM_FENETRE.height));
		contentDescReg.setPreferredSize(new Dimension(
				2 * UtilitaireFenetre.DIM_FENETRE.width / 3,
				UtilitaireFenetre.DIM_FENETRE.height));
		contentDescReg.setMaximumSize(new Dimension(
				2 * UtilitaireFenetre.DIM_FENETRE.width / 3,
				UtilitaireFenetre.DIM_FENETRE.height));

		contentDescReg
				.setLayout(new BoxLayout(contentDescReg, BoxLayout.Y_AXIS));

		// On force la taille des JLabels
		UtilitaireFenetre.setAllSize(super.getLa_descRegle(), 500, 50);

		UtilitaireFenetre.setAllSize(super.getLa_commande(), 500, 50);

		UtilitaireFenetre.addAComposantWithBoxLayout(super.getLa_descRegle(),
				contentDescReg, 0, 100);
		UtilitaireFenetre.addAComposantWithBoxLayout(super.getLa_commande(),
				contentDescReg, 0, 50);

		// On ajoute les composants sur le JPanel contentStory
		// On ajoute une marge entre le composant et le bord de la fenêtre
		contentStory.add(Box.createHorizontalStrut(30));
		contentStory.add(contentPhoto);
		contentStory.add(Box.createGlue());
		contentStory.add(contentDescReg);

		setLangue();

		Container contentPane = super.getContentPane();
		contentPane.add(contentStory, BorderLayout.CENTER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see vue.Fa_modeJeu#setLangue()
	 */
	public void setLangue() {
		super.setLangue();
		String[] traductionStory = ChoixLangue.getChoixLangue().getModeStory();
		super.setTitle(traductionStory[0]);
		super.getLa_titre().setText(traductionStory[1]);
		super.getLa_descRegle().setText(traductionStory[2]);
		super.getLa_commande().setText(traductionStory[3]);
	}

}