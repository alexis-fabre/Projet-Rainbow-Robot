/*
 * Arcade.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package vue;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import evenement.ClicSouris;

/**
 * <p>
 * Fenêtre qui décrit ce que contiendra le mode de jeu Arcade.<br />
 * Elle permet de consulter les règles du jeux, les commandes utilisables et une
 * photo montrant une image représentative du jeu.<br />
 * Il sera possible de choisir si l'on joue contre l'IA ou contre l'ordi ainsi
 * que le niveau de difficulté de l'IA.<br />
 * La fenêtre dispose des composants de son parent, c'est à dire les boutons
 * jouer et retour.<br />
 * La fenêtre respecte le modèle MVC. C'est pour cela que chaque composant
 * dispose d'un getter afin de faciliter les transitions entre les fenêtres.
 * </p>
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class F_arcade extends F_abstractModeJeu implements ChangementLangue {

	/**
	 * Chemin de la photo d'illustration du mode Arcade
	 */
	private final static String CHEMIN_PHOTO = "./img/test.jpg";

	/**
	 * JPanel qui permet de selctionner la difficulté de l'IA. Il n'est affiché
	 * seulement si l'utilisateur à cocher le bouton "Jouer contre l'IA"
	 */
	private JPanel jp_difficulte;

	/**
	 * Permet de selectionner le mode Solo
	 */
	private JRadioButton rb_jeuSolo;

	/**
	 * Permet de selectionner le mode 1 vs IA
	 */
	private JRadioButton rb_jeuIA;

	/**
	 * Permet de selectionner la difficulté facile de l'IA
	 */
	private JRadioButton rb_facile;

	/**
	 * Permet de selectionner la difficulté moyenne de l'IA
	 */
	private JRadioButton rb_moyen;

	/**
	 * Permet de selectionner la difficulté difficile de l'IA
	 */
	private JRadioButton rb_difficile;

	/**
	 * <p>
	 * Menu permettant d'accéder au mode de jeu Arcade avec un bref aperçu des
	 * commandes, règles ainsi que d'une image du mode de jeu Story.<br />
	 * Il sera aussi possible de choisir si l'on joue contre l'IA ou contre
	 * l'ordi ainsi que le niveau de difficulté de l'IA.<br />
	 * Initialise les composants et les disposent sur un contexte graphique 2D.<br />
	 * La fenêtre s'affiche au centre de l'écran et n'est pas redimensionnable
	 * pour éviter tous soucis de disposition.<br />
	 * </p>
	 * 
	 * @param gestion
	 *            le contrôleur qui va controler cette vue = cible
	 *            evenementielle
	 */
	public F_arcade(ClicSouris gestion) {
		super(gestion);

		// ---------------------------------------------------------------------
		// Contient tous les composants de la fenêtre story + les JLabels
		// Description mode et règles de F_abstractModeJeu
		// ---------------------------------------------------------------------
		JPanel contentArcade = new JPanel();
		contentArcade.setLayout(new BoxLayout(contentArcade, BoxLayout.X_AXIS));

		// ---------------------------------------------------------------------
		// Contient la photo
		// ---------------------------------------------------------------------
		Photo contentPhoto = new Photo(CHEMIN_PHOTO);

		// ---------------------------------------------------------------------
		// Contient la description + les règles + tous les radios boutons
		// ---------------------------------------------------------------------
		JPanel contentWest = new JPanel();
		// On force les dimensions au 2/3 de la fenêtre sur la largeur.
		// Pour la hauteur on prend la toutes la hauteur disponible (elle sera
		// redimensionné lorsqu'on l'ajoutera au contentStory)
		UtilitaireFenetre.setAllSize(contentWest,
				2 * UtilitaireFenetre.DIM_FENETRE.width / 3,
				UtilitaireFenetre.DIM_FENETRE.height);

		contentWest.setLayout(new BoxLayout(contentWest, BoxLayout.Y_AXIS));

		// On force la taille des JLabels
		UtilitaireFenetre.setAllSize(super.getLa_descRegle(), 500, 50);

		UtilitaireFenetre.setAllSize(super.getLa_commande(), 500, 50);

		// On ajoute les composants dans le panneau
		// On définit un groupe de bouton
		ButtonGroup group = new ButtonGroup();
		group.add(getRb_jeuSolo());
		group.add(getRb_jeuIA());
		UtilitaireFenetre.addAComposantWithBoxLayout(getRb_jeuSolo(),
				contentWest, 70, 50, Component.LEFT_ALIGNMENT);
		UtilitaireFenetre.addAComposantWithBoxLayout(getRb_jeuIA(),
				contentWest, 70, 20, Component.LEFT_ALIGNMENT);
		UtilitaireFenetre.addAComposantWithBoxLayout(getJp_difficulte(),
				contentWest, 70, 50, Component.LEFT_ALIGNMENT);
		UtilitaireFenetre.addAComposantWithBoxLayout(super.getLa_descRegle(),
				contentWest, 70, 50, Component.LEFT_ALIGNMENT);
		UtilitaireFenetre.addAComposantWithBoxLayout(super.getLa_commande(),
				contentWest, 70, 20, Component.LEFT_ALIGNMENT);

		// On ajoute les composants sur le JPanel contentStory
		// On ajoute une marge entre le composant et le bord de la fenêtre
		contentArcade.add(Box.createHorizontalStrut(30));
		contentArcade.add(contentPhoto);
		contentArcade.add(contentWest);

		// On rend inactif le bouton jouer (il sera actif dans une future
		// version)
		super.getBt_Jouer().setEnabled(false);

		setLangue();

		Container contentPane = super.getContentPane();
		contentPane.add(contentArcade, BorderLayout.CENTER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see vue.Fa_modeJeu#setLangue()
	 */
	public void setLangue() {
		super.setLangue();
		String[] traductionArcade = ChoixLangue.getChoixLangue()
				.getModeArcade();
		super.setTitle(traductionArcade[0]);
		super.getLa_titre().setText(traductionArcade[1]);
		super.getLa_descRegle().setText(traductionArcade[2]);
		super.getLa_commande().setText(traductionArcade[3]);
		getRb_jeuSolo().setText(traductionArcade[4]);
		getRb_jeuIA().setText(traductionArcade[5]);
		// On définit une bordure avec un titre
		getJp_difficulte().setBorder(
				BorderFactory.createTitledBorder(traductionArcade[6]));
		getRb_facile().setText(traductionArcade[7]);
		getRb_moyen().setText(traductionArcade[8]);
		getRb_difficile().setText(traductionArcade[9]);

	}

	/**
	 * @return le jp_difficulte
	 */
	public JPanel getJp_difficulte() {
		if (jp_difficulte == null) {
			jp_difficulte = new JPanel();
			jp_difficulte.setLayout(new BoxLayout(jp_difficulte,
					BoxLayout.Y_AXIS));

			UtilitaireFenetre.setAllSize(jp_difficulte, 500, 100);
			// On ajoute les boutons radions dans un groupe
			ButtonGroup group = new ButtonGroup();
			group.add(getRb_facile());
			group.add(getRb_moyen());
			group.add(getRb_difficile());

			// On ajoute les boutons radios dans le panel
			jp_difficulte.add(getRb_facile());
			jp_difficulte.add(getRb_moyen());
			jp_difficulte.add(getRb_difficile());
		}
		return jp_difficulte;
	}

	/**
	 * @return le rb_jeuSolo
	 */
	public JRadioButton getRb_jeuSolo() {
		if (rb_jeuSolo == null) {
			rb_jeuSolo = new JRadioButton();
			rb_jeuSolo.setSelected(true);
		}
		return rb_jeuSolo;
	}

	/**
	 * @return le rb_jeuIA
	 */
	public JRadioButton getRb_jeuIA() {
		if (rb_jeuIA == null) {
			rb_jeuIA = new JRadioButton();
		}
		return rb_jeuIA;
	}

	/**
	 * @return le rb_facile
	 */
	public JRadioButton getRb_facile() {
		if (rb_facile == null) {
			rb_facile = new JRadioButton();
			rb_facile.setSelected(true);
		}
		return rb_facile;
	}

	/**
	 * @return le rb_moyen
	 */
	public JRadioButton getRb_moyen() {
		if (rb_moyen == null) {
			rb_moyen = new JRadioButton();
		}
		return rb_moyen;
	}

	/**
	 * @return le rb_difficile
	 */
	public JRadioButton getRb_difficile() {
		if (rb_difficile == null) {
			rb_difficile = new JRadioButton();
		}
		return rb_difficile;
	}
}