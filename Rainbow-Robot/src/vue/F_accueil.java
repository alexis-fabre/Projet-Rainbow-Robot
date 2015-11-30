/*
 * Accueil.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package vue;

import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import evenement.ClicSouris;

/**
 * Fenêtre principale, c'est celle qui sera lancée en première lors de
 * l'exécution du programme. Elle permet entre autre de pouvoir jouer,
 * selectionner la langue ou consulter les reccords. Il est aussi possible de
 * consulter la page permettant d'avoir des informations diverses sur le
 * l'élaboration du logiciel.
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class F_accueil extends JFrame {

	/**
	 * Titre de la fenêtre
	 */
	private JLabel la_titre;

	/**
	 * Tableau contenant les traductions effectuées dans ChoixLangue.java
	 */
	private String[] traductionAccueil;

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
	 * Initialise les composants et les disposent sur un contexte graphique 2D.
	 * La fenêtre s'affiche au centre de l'écran et n'est pas redimensionnable
	 * pour éviter tous soucis de disposition. Cette fenêtre détecte uniquement
	 * les cliques de la souris sur les boutons.
	 * 
	 * 
	 * @param gestion
	 *            le contrôleur qui va controler cette vue = cible
	 *            evenementielle
	 */
	public F_accueil(ClicSouris gestion) {
		super();

		super.setSize(UtilitaireFenetre.DIM_FENETRE);
		// On rend la fenêtre non redimenssionable
		super.setResizable(false);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// On définit le layoutManager
		Container containerPane = super.getContentPane();
		containerPane.setLayout(new BoxLayout(containerPane, BoxLayout.Y_AXIS));

		// On ajoute les composants dans la fenêtre
		UtilitaireFenetre.addAComposantWithBoxLayout(getLa_titre(),
				containerPane, 0, 20);
		UtilitaireFenetre.addAComposantWithBoxLayout(getBt_Jouer(),
				containerPane, 0, 20);
		UtilitaireFenetre.addAComposantWithBoxLayout(getBt_Reccords(),
				containerPane, 0, 20);
		UtilitaireFenetre.addAComposantWithBoxLayout(getBt_Langue(),
				containerPane, 0, 20);
		UtilitaireFenetre.addAComposantWithBoxLayout(getBt_Apropos(),
				containerPane, 0, 20);
		UtilitaireFenetre.addAComposantWithBoxLayout(getBt_Quitter(),
				containerPane, 0, 20);

		// On ajoute le nom des composants en fonction de la langue choisie
		traductionAccueil = ChoixLangue.getChoixLangue().getAccueil();
		setLangue();

		// On centre la fenêtre par rapport à la taille de l'écran
		super.setLocation(((int) java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize().getWidth() / 2)
				- super.getWidth() / 2, ((int) java.awt.Toolkit
				.getDefaultToolkit().getScreenSize().getHeight() / 2)
				- super.getHeight() / 2);

		// On ajoute les êvenements sur les boutons
		getBt_Jouer().addMouseListener(gestion);
		getBt_Reccords().addMouseListener(gestion);
		getBt_Langue().addMouseListener(gestion);
		getBt_Apropos().addMouseListener(gestion);
		getBt_Quitter().addMouseListener(gestion);
	}

	/**
	 * Permet de raffraichir les objets en cas de changement de langue
	 */
	public void setLangue() {
		// On actualise la langue
		traductionAccueil = ChoixLangue.getChoixLangue().getAccueil();
		this.setTitle(traductionAccueil[0]);
		getLa_titre().setText(traductionAccueil[1]);
		getBt_Jouer().setText(traductionAccueil[2]);
		getBt_Reccords().setText(traductionAccueil[3]);
		getBt_Langue().setText(traductionAccueil[4]);
		getBt_Apropos().setText(traductionAccueil[5]);
		getBt_Quitter().setText(traductionAccueil[6]);
	}

	/**
	 * @return le JLabel la_titre titre de la fenêtre
	 */
	public JLabel getLa_titre() {
		if (la_titre == null) {
			la_titre = new JLabel();
			la_titre.setPreferredSize(UtilitaireFenetre.DIM_COMPOSANT);
		}
		return la_titre;
	}

	/**
	 * @return le JButton bt_Jouer qui lance la fenêtre du choix des modes de
	 *         jeux
	 */
	public JButton getBt_Jouer() {
		if (bt_Jouer == null) {
			bt_Jouer = new JButton();
			// On définit une taille pour le bouton
			bt_Jouer.setMaximumSize(UtilitaireFenetre.DIM_COMPOSANT);
			bt_Jouer.setMinimumSize(UtilitaireFenetre.DIM_COMPOSANT);
			bt_Jouer.setPreferredSize(UtilitaireFenetre.DIM_COMPOSANT);
		}
		return bt_Jouer;
	}

	/**
	 * @return le JButton bt_Reccords qui lance la fenêtre pour consulter les
	 *         reccords
	 */
	public JButton getBt_Reccords() {
		if (bt_Reccords == null) {
			bt_Reccords = new JButton();
			// On définit une taille pour le bouton
			bt_Reccords.setMaximumSize(UtilitaireFenetre.DIM_COMPOSANT);
			bt_Reccords.setMinimumSize(UtilitaireFenetre.DIM_COMPOSANT);
			bt_Reccords.setPreferredSize(UtilitaireFenetre.DIM_COMPOSANT);
		}
		return bt_Reccords;
	}

	/**
	 * @return le JButton bt_Langue qui lance la fenêtre pour pouvoir changer de
	 *         langue
	 */
	public JButton getBt_Langue() {
		if (bt_Langue == null) {
			bt_Langue = new JButton();
			// On définit une taille pour le bouton
			bt_Langue.setMaximumSize(UtilitaireFenetre.DIM_COMPOSANT);
			bt_Langue.setMinimumSize(UtilitaireFenetre.DIM_COMPOSANT);
			bt_Langue.setPreferredSize(UtilitaireFenetre.DIM_COMPOSANT);
		}
		return bt_Langue;
	}

	/**
	 * @return le JButton bt_Apropos qui lance la fenêtre permettant de voir
	 *         comment c'est élaborer ce projet
	 */
	public JButton getBt_Apropos() {
		if (bt_Apropos == null) {
			bt_Apropos = new JButton();
			// On définit une taille pour le bouton
			bt_Apropos.setMaximumSize(UtilitaireFenetre.DIM_COMPOSANT);
			bt_Apropos.setMinimumSize(UtilitaireFenetre.DIM_COMPOSANT);
			bt_Apropos.setPreferredSize(UtilitaireFenetre.DIM_COMPOSANT);
		}
		return bt_Apropos;
	}

	/**
	 * @return le JButton bt_Quitter permettant de quitter l'application (en
	 *         plus de la croix rouge)
	 */
	public JButton getBt_Quitter() {
		if (bt_Quitter == null) {
			bt_Quitter = new JButton();
			// On définit une taille pour le bouton
			bt_Quitter.setMaximumSize(UtilitaireFenetre.DIM_COMPOSANT);
			bt_Quitter.setMinimumSize(UtilitaireFenetre.DIM_COMPOSANT);
			bt_Quitter.setPreferredSize(UtilitaireFenetre.DIM_COMPOSANT);
		}
		return bt_Quitter;
	}
}