/*
 * Custom.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import evenement.ClicSouris;

/**
 * TODO Expliquer le fonctionnement de la classe
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class F_custom extends F_abstractModeJeu implements ChangementLangue {

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
	 * Label qui affiche le titre permettant de selectionner un fichier
	 */
	private JLabel la_titreParcourir;

	/**
	 * Bouton qui permet de parcourir les fichiers dans notre propre repertoire
	 */
	private JButton bt_parcourir;

	/**
	 * L'utilisateur peut directement saisir le chemin du fichier à selectionner
	 */
	private JTextField tf_cheminFichier;

	/**
	 * TODO Expliquer le fonctionnement du constructeur
	 * 
	 * @param titre
	 */
	public F_custom(ClicSouris gestion) {
		super(gestion);

		// ---------------------------------------------------------------------
		// Contient tous les composants de la fenêtre story + les JLabels
		// Description mode et règles de F_abstractModeJeu
		// ---------------------------------------------------------------------
		JPanel contentCustom = new JPanel();
		contentCustom.setLayout(new BoxLayout(contentCustom, BoxLayout.X_AXIS));

		// ---------------------------------------------------------------------
		// Contient la description + les règles + tous les radios boutons + les
		// composants pour choisir un fichier
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

		// ---------------------------------------------------------------------
		// Contient tous les composants pour rechercher un fichier
		// ---------------------------------------------------------------------
		SpringLayout layoutFichier = new SpringLayout();
		// Panneau contenant les deux boutons Retour et Jouer
		JPanel contentFichier = new JPanel(layoutFichier);

		// On laisse une petite marge sur l'axe des y
		UtilitaireFenetre.setAllSize(contentFichier, new Dimension(
				UtilitaireFenetre.DIM_FENETRE.width, 100));

		contentFichier.add(getLa_titreParcourir());
		contentFichier.add(getTf_cheminFichier());
		contentFichier.add(getBt_Parcourir());

		// Label titre
		layoutFichier.putConstraint(SpringLayout.WEST, getLa_titreParcourir(),
				0, SpringLayout.WEST, contentFichier);
		layoutFichier.putConstraint(SpringLayout.NORTH, getLa_titreParcourir(),
				20, SpringLayout.NORTH, contentFichier);
		// TextField indiquant le chemin fichier
		layoutFichier.putConstraint(SpringLayout.WEST, getTf_cheminFichier(),
				0, SpringLayout.WEST, contentFichier);
		layoutFichier.putConstraint(SpringLayout.NORTH, getTf_cheminFichier(),
				5, SpringLayout.SOUTH, getLa_titreParcourir());
		// Bouton parcourir
		layoutFichier.putConstraint(SpringLayout.WEST, getBt_Parcourir(), 20,
				SpringLayout.EAST, getTf_cheminFichier());
		layoutFichier.putConstraint(SpringLayout.NORTH, getBt_Parcourir(), 0,
				SpringLayout.NORTH, getTf_cheminFichier());

		// On ajoute les composants dans le panneau
		// On définit un groupe de bouton
		ButtonGroup group = new ButtonGroup();
		group.add(getRb_jeuSolo());
		group.add(getRb_jeuIA());
		UtilitaireFenetre.addAComposantWithBoxLayout(contentFichier,
				contentWest, 50, 10, Component.LEFT_ALIGNMENT);
		UtilitaireFenetre.addAComposantWithBoxLayout(getRb_jeuSolo(),
				contentWest, 50, 10, Component.LEFT_ALIGNMENT);
		UtilitaireFenetre.addAComposantWithBoxLayout(getRb_jeuIA(),
				contentWest, 50, 20, Component.LEFT_ALIGNMENT);
		UtilitaireFenetre.addAComposantWithBoxLayout(getJp_difficulte(),
				contentWest, 50, 20, Component.LEFT_ALIGNMENT);
		UtilitaireFenetre.addAComposantWithBoxLayout(super.getLa_descRegle(),
				contentWest, 50, 20, Component.LEFT_ALIGNMENT);
		UtilitaireFenetre.addAComposantWithBoxLayout(super.getLa_commande(),
				contentWest, 50, 20, Component.LEFT_ALIGNMENT);

		// On ajoute les composants sur le JPanel contentStory
		// On ajoute une marge entre le composant et le bord de la fenêtre
		contentCustom.add(Box.createHorizontalStrut(30));
		contentCustom.add(contentWest);

		// On rend inactif le bouton jouer (il sera actif dans une future
		// version)
		super.getBt_Jouer().setEnabled(false);

		setLangue();

		Container contentPane = super.getContentPane();
		contentPane.add(contentCustom, BorderLayout.CENTER);

		// On ajoute les évenements
		getBt_Parcourir().addMouseListener(gestion);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see vue.Fa_modeJeu#setLangue()
	 */
	public void setLangue() {
		super.setLangue();
		String[] traductionArcade = ChoixLangue.getChoixLangue()
				.getModeCustom();
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
		getLa_titreParcourir().setText(traductionArcade[10]);
		getBt_Parcourir().setText(traductionArcade[11]);
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

	/**
	 * @return le la_titreParcourir
	 */
	public JLabel getLa_titreParcourir() {
		if (la_titreParcourir == null) {
			la_titreParcourir = new JLabel();
		}
		return la_titreParcourir;
	}

	/**
	 * @return le bt_parcourir
	 */
	public JButton getBt_Parcourir() {
		if (bt_parcourir == null) {
			bt_parcourir = new JButton();
		}
		return bt_parcourir;
	}

	/**
	 * @return le tf_cheminFichier
	 */
	public JTextField getTf_cheminFichier() {
		if (tf_cheminFichier == null) {
			tf_cheminFichier = new JTextField();
			UtilitaireFenetre.setAllSize(tf_cheminFichier, 500, 20);
		}
		return tf_cheminFichier;
	}

}