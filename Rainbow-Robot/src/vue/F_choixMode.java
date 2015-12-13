/*
 * ChoixMode.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package vue;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import evenement.ClicSouris;

/**
 * *
 * <p>
 * Fenêtre qui permet d'accéder au différent mode de jeu (Story, Arcade et
 * Custom).<br />
 * Elle permet de consulter un rapide description des modes de jeu.<br />
 * La fenêtre dispose des boutons pour aller vers les descriptions plus précise
 * des modes de jeu.<br />
 * La fenêtre respecte le modèle MVC. C'est pour cela que chaque composant
 * dispose d'un getter afin de faciliter les transitions entre les fenêtres.
 * </p>
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class F_choixMode extends JFrame implements ChangementLangue {

	/**
	 * Généré automatiquement par Eclipse
	 */
	private static final long serialVersionUID = -1947027691507109322L;

	/**
	 * Titre de la fenêtre
	 */
	private JLabel la_titre;

	/**
	 * Référence des traductions effectuées dans ChoixLangue.java
	 */
	private ChoixLangue traducteur = ChoixLangue.getChoixLangue();

	/**
	 * Bouton qui lance la fenêtre expliquant le mode "Story"
	 */
	private JButton bt_Story;

	/**
	 * Bouton qui lance la fenêtre expliquant le mode "Arcade"
	 */
	private JButton bt_Arcade;

	/**
	 * Bouton qui lance la fenêtre expliquant le mode "Custom"
	 */
	private JButton bt_Custom;

	/**
	 * Bouton qui permet de retourner au menu précédent
	 */
	private JButton bt_Retour;

	/**
	 * Brêve description du mode Story
	 */
	private JLabel la_story;

	/**
	 * Brêve description du mode Arcade
	 */
	private JLabel la_arcade;

	/**
	 * Brêve description du mode Custom
	 */
	private JLabel la_custom;

	/**
	 * <p>
	 * Menu permettant d'accéder au description des modes de jeu Story, Arcade
	 * et Custom.<br />
	 * Initialise les composants et les disposent sur un contexte graphique 2D.<br />
	 * La fenêtre s'affiche au centre de l'écran et n'est pas redimensionnable
	 * pour éviter tous soucis de disposition.<br />
	 * </p>
	 * 
	 * @param gestion
	 *            le contrôleur qui va controler cette vue = cible
	 *            evenementielle
	 */
	public F_choixMode(ClicSouris gestion) {
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
		UtilitaireFenetre.addAComposantWithBoxLayout(getBt_Story(),
				contentModeJeu, 0, 50, Component.CENTER_ALIGNMENT);
		UtilitaireFenetre.addAComposantWithBoxLayout(getDescStory(),
				contentModeJeu, 0, 10, Component.CENTER_ALIGNMENT);
		UtilitaireFenetre.addAComposantWithBoxLayout(getBt_Arcade(),
				contentModeJeu, 0, 50, Component.CENTER_ALIGNMENT);
		UtilitaireFenetre.addAComposantWithBoxLayout(getDescArcade(),
				contentModeJeu, 0, 10, Component.CENTER_ALIGNMENT);
		UtilitaireFenetre.addAComposantWithBoxLayout(getBt_Custom(),
				contentModeJeu, 0, 50, Component.CENTER_ALIGNMENT);
		UtilitaireFenetre.addAComposantWithBoxLayout(getDescCustom(),
				contentModeJeu, 0, 10, Component.CENTER_ALIGNMENT);

		// ---------------------------------------------------------------------
		// Ajout du bouton Retour
		// ---------------------------------------------------------------------
		Container contentRetour = new JPanel();
		contentRetour.setPreferredSize(new Dimension(
				UtilitaireFenetre.DIM_FENETRE.width, 100));
		contentRetour.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 25));
		contentRetour.add(getBt_Retour());

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
		getBt_Story().addMouseListener(gestion);
		getBt_Arcade().addMouseListener(gestion);
		getBt_Custom().addMouseListener(gestion);
		getBt_Retour().addMouseListener(gestion);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see vue.ChangementLangue#setLangue()
	 */
	@Override
	public void setLangue() {
		String[] traducionChoixMode = traducteur.getChoixMode();
		this.setTitle(traducionChoixMode[0]);
		getLa_titre().setText(traducionChoixMode[0]);
		getDescStory().setText(traducionChoixMode[1]);
		getDescArcade().setText(traducionChoixMode[2]);
		getDescCustom().setText(traducionChoixMode[3]);
		getBt_Retour().setText(traducionChoixMode[4]);

	}

	/**
	 * @return le JLabel la_titre titre de la fenêtre
	 */
	public JLabel getLa_titre() {
		if (la_titre == null) {
			la_titre = new JLabel();
			la_titre.setPreferredSize(UtilitaireFenetre.DIM_COMPOSANT_PRINCIPAL);
		}
		return la_titre;
	}

	/**
	 * @return le JButton bt_Story permettant d'accéder à la description du mode
	 *         Story.
	 */
	public JButton getBt_Story() {
		if (bt_Story == null) {
			bt_Story = new JButton();
			// On définit une taille pour le bouton
			UtilitaireFenetre.setAllSize(bt_Story,
					UtilitaireFenetre.DIM_COMPOSANT_PRINCIPAL);
			bt_Story.setText("Story");
		}
		return bt_Story;
	}

	/**
	 * @return le JButton bt_Arcade permettant d'accéder à la description du
	 *         mode Arcade.
	 */
	public JButton getBt_Arcade() {
		if (bt_Arcade == null) {
			bt_Arcade = new JButton();
			// On définit une taille pour le bouton
			UtilitaireFenetre.setAllSize(bt_Arcade,
					UtilitaireFenetre.DIM_COMPOSANT_PRINCIPAL);
			bt_Arcade.setText("Arcade");
		}
		return bt_Arcade;
	}

	/**
	 * @return le JButton bt_Custom permettant d'accéder à la description du
	 *         mode Custom.
	 */
	public JButton getBt_Custom() {
		if (bt_Custom == null) {
			bt_Custom = new JButton();
			// On définit une taille pour le bouton
			UtilitaireFenetre.setAllSize(bt_Custom,
					UtilitaireFenetre.DIM_COMPOSANT_PRINCIPAL);
			bt_Custom.setText("Custom");
		}
		return bt_Custom;
	}

	/**
	 * @returnle JButton bt_Retour qui revient à la page d'accueil F_accueil
	 */
	public JButton getBt_Retour() {
		if (bt_Retour == null) {
			bt_Retour = new JButton();
			// On définit une taille pour le bouton
			UtilitaireFenetre.setAllSize(bt_Retour,
					UtilitaireFenetre.DIM_COMPOSANT_SECONDAIRE);
		}
		return bt_Retour;
	}

	/**
	 * @return le JLabel la_story permettant d'avoir un rapide aperçu de ce que
	 *         contient le mode story.
	 */
	public JLabel getDescStory() {
		if (la_story == null) {
			la_story = new JLabel();
		}
		return la_story;
	}

	/**
	 * @return le JLabel la_arcade permettant d'avoir un rapide aperçu de ce que
	 *         contient le mode arcade.
	 */
	public JLabel getDescArcade() {
		if (la_arcade == null) {
			la_arcade = new JLabel();
		}
		return la_arcade;
	}

	/**
	 * @return le JLabel la_custom permettant d'avoir un rapide aperçu de ce que
	 *         contient le mode custom.
	 */
	public JLabel getDescCustom() {
		if (la_custom == null) {
			la_custom = new JLabel();
		}
		return la_custom;
	}

}