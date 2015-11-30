/*
 * ModeJeu.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import evenement.ClicSouris;

/**
 * TODO Expliquer le fonctionnement de la classe
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public abstract class Fa_modeJeu extends JFrame implements ChangementLangue {

	/**
	 * Référence des traductions effectuées dans ChoixLangue.java
	 */
	private ChoixLangue traducteur = ChoixLangue.getChoixLangue();

	/**
	 * Contient les descriptions des commandes que l'utilisateur pourra
	 * effectuer s'il choisi ce mode de jeu
	 */
	private JLabel la_commande;

	/**
	 * Contient la description des règles du jeux
	 */
	private JLabel la_descRegle;

	/**
	 * Titre de la fenêtre
	 */
	private JLabel titre;

	/**
	 * Bouton qui revient à la page pour choisir le mode de jeu
	 */
	private JButton bt_Retour;

	/**
	 * Bouton qui lance une partie de Rainbow Robot selon le mode de jeu définie
	 * et choisie
	 */
	private JButton bt_Jouer;

	protected Fa_modeJeu(ClicSouris gestion) {
		super();

		super.setSize(UtilitaireFenetre.DIM_FENETRE);
		// On rend la fenêtre non redimenssionable
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container contentPane = super.getContentPane();
		contentPane.setLayout(new BorderLayout());

		// LayoutManager pour placer les deux boutons
		SpringLayout layout_boutons = new SpringLayout();
		// Panneau contenant les deux boutons Retour et Jouer
		Container boutons = new JPanel(layout_boutons);

		// On laisse une petite marge sur l'axe des y
		boutons.setPreferredSize(new Dimension(
				UtilitaireFenetre.DIM_FENETRE.width,
				UtilitaireFenetre.DIM_BOUTON_SECONDAIRE.height + 20));

		boutons.add(getBt_Retour());
		boutons.add(getBt_Jouer());

		// On place les deux boutons
		// Bouton Retour
		layout_boutons.putConstraint(SpringLayout.WEST, getBt_Retour(), 10,
				SpringLayout.WEST, boutons);
		layout_boutons.putConstraint(SpringLayout.NORTH, getBt_Retour(), 10,
				SpringLayout.NORTH, boutons);
		// Bouton Jouer
		// -10 <=> 10 pixels entre le panneau des boutons et le boutons bt_jouer
		layout_boutons.putConstraint(SpringLayout.EAST, getBt_Jouer(), -10,
				SpringLayout.EAST, boutons);
		layout_boutons.putConstraint(SpringLayout.NORTH, getBt_Jouer(), 10,
				SpringLayout.NORTH, boutons);

		// On ajoute le panneaux des boutons sur la fenêtre
		contentPane.add(boutons, BorderLayout.SOUTH);

		// On ajoute le nom des composants en fonction de la langue choisie
		setLangue();

		// On centre l'écran
		UtilitaireFenetre.centrerFenetre(this);

		// On ajoute les évènements sur les boutons
		getBt_Retour().addMouseListener(gestion);
		getBt_Jouer().addMouseListener(gestion);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see vue.ChangementLangue#setLangue()
	 */
	public void setLangue() {
		String[] traductionModeJeu = traducteur.getModeJeu();
		getBt_Retour().setText(traductionModeJeu[0]);
		getBt_Jouer().setText(traductionModeJeu[1]);
	}

	/**
	 * @return le JButton bt_Retour qui revient à la page d'accueil
	 */
	public JButton getBt_Retour() {
		if (bt_Retour == null) {
			bt_Retour = new JButton();
			// On définit une taille pour le bouton
			bt_Retour.setMaximumSize(UtilitaireFenetre.DIM_BOUTON_SECONDAIRE);
			bt_Retour.setMinimumSize(UtilitaireFenetre.DIM_BOUTON_SECONDAIRE);
			bt_Retour.setPreferredSize(UtilitaireFenetre.DIM_BOUTON_SECONDAIRE);

		}
		return bt_Retour;
	}

	/**
	 * @return le JButton bt_Jouer qui lance une partie de Rainbow Robot selon
	 *         le mode de jeu définie et choisie
	 */
	public JButton getBt_Jouer() {
		if (bt_Jouer == null) {
			bt_Jouer = new JButton();
			// On définit une taille pour le bouton
			bt_Jouer.setMaximumSize(UtilitaireFenetre.DIM_BOUTON_SECONDAIRE);
			bt_Jouer.setMinimumSize(UtilitaireFenetre.DIM_BOUTON_SECONDAIRE);
			bt_Jouer.setPreferredSize(UtilitaireFenetre.DIM_BOUTON_SECONDAIRE);
		}
		return bt_Jouer;
	}
}