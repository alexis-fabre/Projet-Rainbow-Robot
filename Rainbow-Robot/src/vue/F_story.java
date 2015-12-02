/*
 * Story.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import evenement.ClicSouris;

/**
 * <p>
 * Fenêtre de descritpion précise du mode de jeu Story.<br />
 * Elle permet de pouvoir consulter les règles du jeu, les commandes que l'on
 * pourra utiliser ainsi qu'une photo du mode de jeu.<br />
 * La fenêtre dispose des composants de son parent, c'est à dire les boutons
 * jouer et retour.<br />
 * La fenêtre respecte le modèle MVC. C'est pour cela que chaque composant
 * dispose d'un getter afin de faciliter les transitions entre les fenêtres.
 * </p>
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class F_story extends F_abstractModeJeu implements ChangementLangue {

	/**
	 * Chemin de la photo que l'on désire afficher
	 */
	private final static String CHEMIN_PHOTO = "./img/test.jpg";

	/**
	 * <p>
	 * Menu permettant d'accéder au mode de jeu Story avec un bref aperçu des
	 * commandes, règles ainsi que d'une image du mode de jeu Story.<br />
	 * Initialise les composants et les disposent sur un contexte graphique 2D.<br />
	 * La fenêtre s'affiche au centre de l'écran et n'est pas redimensionnable
	 * pour éviter tous soucis de disposition.<br />
	 * </p>
	 * 
	 * @param gestion
	 *            le contrôleur qui va controler cette vue = cible
	 *            evenementielle
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
		Photo contentPhoto = new Photo(CHEMIN_PHOTO);

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