/*
 * APropos.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package vue;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import evenement.ClicSouris;

/**
 * TODO Expliquer le fonctionnement de la classe
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class F_aPropos extends JFrame implements ChangementLangue {

	/**
	 * Titre de la fenêtre
	 */
	private JLabel la_titre;

	/**
	 * Texte central de la fenêtre qui affiche des informations relatives à la
	 * création du logiciel
	 */
	private JLabel la_texte;

	/**
	 * Bouton permettant de revenir à la page d'accueil
	 */
	private JButton bt_retour;

	/**
	 * Initialise et place les composants dans un contexte graphique 2D
	 * 
	 * @param gestion
	 *            le contrôleur qui va controler cette vue = cible
	 *            evenementielle
	 */
	public F_aPropos(ClicSouris gestion) {
		super();

		super.setSize(UtilitaireFenetre.DIM_FENETRE);
		// On rend la fenêtre non redimenssionable
		super.setResizable(false);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ---------------------------------------------------------------------
		// Ajout des boutons Story, Arcade et Custom ainsi que de leurs
		// descriptions
		// ---------------------------------------------------------------------
		Container contentModeJeu = new JPanel();
		// On admet que la disposition des boutons et leurs descriptions ne
		// dépassent pas les 2/3 de la fenêtre
		contentModeJeu.setPreferredSize(new Dimension(
				UtilitaireFenetre.DIM_FENETRE.width,
				(2 * UtilitaireFenetre.DIM_FENETRE.height) / 3));
		contentModeJeu
				.setLayout(new BoxLayout(contentModeJeu, BoxLayout.Y_AXIS));

		// On ajoute les composants dans la fenêtre
		UtilitaireFenetre.addAComposantWithBoxLayout(getLa_titre(),
				contentModeJeu, 0, 30, Component.CENTER_ALIGNMENT);
		UtilitaireFenetre.addAComposantWithBoxLayout(getLa_texte(),
				contentModeJeu, 0, 50, Component.CENTER_ALIGNMENT);

		// ---------------------------------------------------------------------
		// Ajout du bouton Retour
		// ---------------------------------------------------------------------
		Container contentRetour = new JPanel();
		contentRetour.setPreferredSize(new Dimension(
				UtilitaireFenetre.DIM_FENETRE.width, 100));
		contentRetour.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 25));
		contentRetour.add(getBt_retour());

		// On ajoute les panels dans la fenêtre principal
		Container contentPane = super.getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(contentModeJeu, BorderLayout.CENTER);
		contentPane.add(contentRetour, BorderLayout.PAGE_END);

		// On ajoute le nom des composants en fonction de la langue choisie
		setLangue();

		// On centre l'écran
		UtilitaireFenetre.centrerFenetre(this);

		// On ajoute les évènements sur les boutons
		getBt_retour().addMouseListener(gestion);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see vue.ChangementLangue#setLangue()
	 */
	@Override
	public void setLangue() {
		// On actualise la langue
		String[] traductionReccords = ChoixLangue.getChoixLangue().getApropos();
		this.setTitle(traductionReccords[0]);
		getLa_titre().setText(traductionReccords[1]);
		getLa_texte().setText(traductionReccords[2]);
		getBt_retour().setText(traductionReccords[3]);

	}

	/**
	 * @return le titre
	 */
	public JLabel getLa_titre() {
		if (la_titre == null) {
			la_titre = new JLabel();
		}
		return la_titre;
	}

	/**
	 * @return le texte
	 */
	public JLabel getLa_texte() {
		if (la_texte == null) {
			la_texte = new JLabel();
		}
		return la_texte;
	}

	/**
	 * @return le bt_retour
	 */
	public JButton getBt_retour() {
		if (bt_retour == null) {
			bt_retour = new JButton();
			UtilitaireFenetre.setAllSize(bt_retour,
					UtilitaireFenetre.DIM_COMPOSANT_SECONDAIRE);
		}
		return bt_retour;
	}

}