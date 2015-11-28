/*
 * Accueil.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package vue;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

import evenement.ClicSouris;

/**
 * Fenêtre principale, c'est celle qui sera lancé en première lors de
 * l'exécution du programme. Elle permet entre autre de pouvoir jouer,
 * selectionner la langue ou consulter les reccords. Il est aussi possible de
 * consulter la page permettant d'avoir des informations diverses sur le
 * l'élaboration du logiciel.
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class Accueil extends JFrame {

	/**
	 * Titre de la fenêtre
	 */
	private JLabel la_titre;

	/**
	 * Dimension des boutons
	 */
	public static final Dimension DIM_COMPOSANT = new Dimension(400, 60);

	/**
	 * Bouton qui lance la fenêtre du choix des modes de jeux
	 */
	private JButton bt_Jouer;

	/**
	 * Bouton qui lance la fenêtre pour consulter les reccords
	 */
	private JButton bt_Reccords;

	/**
	 * Bouton qui lance la fenêtre pour pouvoir changer de langue
	 */
	private JButton bt_Langue;

	/**
	 * Bouton qui lance la fenêtre permettant de voir comment c'est élaborer ce
	 * projet
	 */
	private JButton bt_Apropos;

	/**
	 * Bouton permettant de quitter l'application (en plus de la croix rouge)
	 */
	private JButton bt_Quitter;

	/**
	 * Initialise les composants et les dispose sur le contexte 2D
	 * 
	 * @param gestion
	 *            le contrôleut qui va controler cette vue = cible
	 *            evenementielle
	 */
	public Accueil(ClicSouris gestion) {
		super("Accueil");

		super.setSize(new Dimension(700, 500));
		// On rend la fenêtre non redimenssionable
		super.setResizable(false);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// On définit le layoutManager
		Container containerPane = super.getContentPane();
		containerPane.setLayout(new BoxLayout(containerPane, BoxLayout.Y_AXIS));

		// On ajoute les composants dans la fenêtre
		addAComposant(getLa_titre(), containerPane);
		addAComposant(getBt_Jouer(), containerPane);
		addAComposant(getBt_Reccords(), containerPane);
		addAComposant(getBt_Langue(), containerPane);
		addAComposant(getBt_Apropos(), containerPane);
		addAComposant(getBt_Quitter(), containerPane);

		// On centre la fenêtre par rapport à la taille de l'écran
		super.setLocation(((int) java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize().getWidth() / 2)
				- super.getWidth() / 2, ((int) java.awt.Toolkit
				.getDefaultToolkit().getScreenSize().getHeight() / 2)
				- super.getHeight() / 2);

		// On ajoute les évenements sur les boutons
		getBt_Jouer().addMouseListener(gestion);
		getBt_Reccords().addMouseListener(gestion);
		getBt_Langue().addMouseListener(gestion);
		getBt_Apropos().addMouseListener(gestion);
		getBt_Quitter().addMouseListener(gestion);
	}

	/**
	 * Ajoute un nouveau composant dans le container
	 * 
	 * @param aAjouter
	 *            nouveau composant à ajouter
	 * @param pane
	 *            container ou on ajoute le composant
	 */
	private void addAComposant(JComponent aAjouter, Container pane) {
		// On aligne le composant horizontalement par rapport à la fenêtre
		aAjouter.setAlignmentX(Component.CENTER_ALIGNMENT);
		pane.add(aAjouter);
		// On créé une marge vertical de 10 pixels
		pane.add(Box.createRigidArea(new Dimension(0, 20)));
	}

	/**
	 * @return le la_titre
	 */
	public JLabel getLa_titre() {
		if (la_titre == null) {
			la_titre = new JLabel("Accueil");
			la_titre.setPreferredSize(DIM_COMPOSANT);
		}
		return la_titre;
	}

	/**
	 * @return le bt_Jouer
	 */
	public JButton getBt_Jouer() {
		if (bt_Jouer == null) {
			bt_Jouer = new JButton("Jouer");
			// On définit une taille pour le bouton
			bt_Jouer.setMaximumSize(DIM_COMPOSANT);
			bt_Jouer.setMinimumSize(DIM_COMPOSANT);
			bt_Jouer.setPreferredSize(DIM_COMPOSANT);
		}
		return bt_Jouer;
	}

	/**
	 * @return le bt_Reccords
	 */
	public JButton getBt_Reccords() {
		if (bt_Reccords == null) {
			bt_Reccords = new JButton("Reccords");
			// On définit une taille pour le bouton
			bt_Reccords.setMaximumSize(DIM_COMPOSANT);
			bt_Reccords.setMinimumSize(DIM_COMPOSANT);
			bt_Reccords.setPreferredSize(DIM_COMPOSANT);
		}
		return bt_Reccords;
	}

	/**
	 * @return le bt_Langue
	 */
	public JButton getBt_Langue() {
		if (bt_Langue == null) {
			bt_Langue = new JButton("Langue");
			// On définit une taille pour le bouton
			bt_Langue.setMaximumSize(DIM_COMPOSANT);
			bt_Langue.setMinimumSize(DIM_COMPOSANT);
			bt_Langue.setPreferredSize(DIM_COMPOSANT);
		}
		return bt_Langue;
	}

	/**
	 * @return le bt_Apropos
	 */
	public JButton getBt_Apropos() {
		if (bt_Apropos == null) {
			bt_Apropos = new JButton("A Propos");
			// On définit une taille pour le bouton
			bt_Apropos.setMaximumSize(DIM_COMPOSANT);
			bt_Apropos.setMinimumSize(DIM_COMPOSANT);
			bt_Apropos.setPreferredSize(DIM_COMPOSANT);
		}
		return bt_Apropos;
	}

	/**
	 * @return le bt_Quitter
	 */
	public JButton getBt_Quitter() {
		if (bt_Quitter == null) {
			bt_Quitter = new JButton("Quitter");
			// On définit une taille pour le bouton
			bt_Quitter.setMaximumSize(DIM_COMPOSANT);
			bt_Quitter.setMinimumSize(DIM_COMPOSANT);
			bt_Quitter.setPreferredSize(DIM_COMPOSANT);
		}
		return bt_Quitter;
	}
}