/*
 * ModeJeu.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package vue;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import evenement.ClicSouris;

/**
 * <p>
 * Fenêtre abstraite comprenant les composants principaux pour tous les modes de
 * jeux (Story, Arcade et Custon).<br />
 * Elle permet de pouvoir revenir sur la page précédente et d'accéder aux
 * différentes modes de jeux. Les labels titre, description des règles et
 * commande sont juste initialisés et seront placés dans les classes filles.<br />
 * La fenêtre respecte le modèle MVC. C'est pour cela que chaque composant
 * dispose d'un getter afin de faciliter les transitions entre les fenêtres.<br />
 * </p>
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public abstract class F_abstractModeJeu extends JFrame implements
		ChangementLangue {

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
	private JLabel la_titre;

	/**
	 * Bouton qui revient à la page pour choisir le mode de jeu
	 */
	private JButton bt_Retour;

	/**
	 * Bouton qui lance une partie de Rainbow Robot selon le mode de jeu définie
	 * et choisie
	 */
	private JButton bt_Jouer;

	/**
	 * <p>
	 * Initialise les composants et les disposent sur un contexte graphique 2D.
	 * Ici sont placé uniquement le label Titre ainsi que les boutons retour et
	 * jouer.<br />
	 * Les deux labels description des règles et commande sont initialisés mais
	 * ils ne sont ni disposés, ni nommés.<br />
	 * La fenêtre s'affiche au centre de l'écran et n'est pas redimensionnable
	 * pour éviter tous soucis de disposition. Cette fenêtre détecte uniquement
	 * les cliques de la souris sur les boutons.<br />
	 * </p>
	 * 
	 * @param gestion
	 *            le contrôleur qui va controler cette vue = cible
	 *            evenementielle
	 */
	protected F_abstractModeJeu(ClicSouris gestion) {
		super();

		super.setSize(UtilitaireFenetre.DIM_FENETRE);
		// On rend la fenêtre non redimenssionable
		super.setResizable(false);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container contentPane = super.getContentPane();
		contentPane.setLayout(new BorderLayout());

		// ---------------------------------------------------------------------
		// Ajout des boutons Retour et Jouer
		// ---------------------------------------------------------------------
		// LayoutManager pour placer les deux boutons
		SpringLayout layout_boutons = new SpringLayout();
		// Panneau contenant les deux boutons Retour et Jouer
		Container contentBoutons = new JPanel(layout_boutons);

		// On laisse une petite marge sur l'axe des y
		contentBoutons.setPreferredSize(new Dimension(
				UtilitaireFenetre.DIM_FENETRE.width,
				UtilitaireFenetre.DIM_COMPOSANT_SECONDAIRE.height + 50));

		contentBoutons.add(getBt_Retour());
		contentBoutons.add(getBt_Jouer());

		// On place les deux boutons
		// Bouton Retour
		layout_boutons.putConstraint(SpringLayout.WEST, getBt_Retour(), 20,
				SpringLayout.WEST, contentBoutons);
		layout_boutons.putConstraint(SpringLayout.NORTH, getBt_Retour(), 20,
				SpringLayout.NORTH, contentBoutons);
		// Bouton Jouer
		// -10 <=> 10 pixels entre le panneau des boutons et le boutons bt_jouer
		layout_boutons.putConstraint(SpringLayout.EAST, getBt_Jouer(), -20,
				SpringLayout.EAST, contentBoutons);
		layout_boutons.putConstraint(SpringLayout.NORTH, getBt_Jouer(), 20,
				SpringLayout.NORTH, contentBoutons);

		// ---------------------------------------------------------------------
		// Ajout du label titre
		// ---------------------------------------------------------------------
		Container contentTitre = new JPanel();
		contentTitre.setLayout(new BoxLayout(contentTitre, BoxLayout.Y_AXIS));
		contentTitre.setPreferredSize(new Dimension(
				UtilitaireFenetre.DIM_FENETRE.width, 50));

		UtilitaireFenetre.addAComposantWithBoxLayout(getLa_titre(),
				contentTitre, 0, 15, Component.CENTER_ALIGNMENT);

		// ---------------------------------------------------------------------
		// On dispose les éléments dans la fenêtre
		// ---------------------------------------------------------------------
		// On ajoute le panneaux des boutons sur la fenêtre
		contentPane.add(contentTitre, BorderLayout.NORTH);
		contentPane.add(contentBoutons, BorderLayout.SOUTH);

		// On ajoute la langue
		setLangue();

		// On centre l'écran
		UtilitaireFenetre.centrerFenetre(this);

		// On ajoute les évènements sur les boutons
		getBt_Retour().addMouseListener(gestion);
		getBt_Jouer().addMouseListener(gestion);
	}

	/*
	 * La fonction est appelé par les classes filles (non-Javadoc)
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
			UtilitaireFenetre.setAllSize(bt_Retour,
					UtilitaireFenetre.DIM_COMPOSANT_SECONDAIRE);

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
			UtilitaireFenetre.setAllSize(bt_Jouer,
					UtilitaireFenetre.DIM_COMPOSANT_SECONDAIRE);
		}
		return bt_Jouer;
	}

	/**
	 * @return le JLabel la_commande contient les descriptions des commandes que
	 *         l'utilisateur pourra effectuer s'il choisi ce mode de jeu
	 */
	protected JLabel getLa_commande() {
		if (la_commande == null) {
			la_commande = new JLabel();
		}
		return la_commande;
	}

	/**
	 * @return le JLabel la_descRegle contient la description des règles du jeux
	 */
	protected JLabel getLa_descRegle() {
		if (la_descRegle == null) {
			la_descRegle = new JLabel();
		}
		return la_descRegle;
	}

	/**
	 * @return le JLabel la_titre titre de la fenêtre
	 */
	protected JLabel getLa_titre() {
		if (la_titre == null) {
			la_titre = new JLabel();
		}
		return la_titre;
	}
}