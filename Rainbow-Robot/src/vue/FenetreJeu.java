/*
 * Fenetre.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package vue;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import evenement.ClicSouris;
import evenement.ToucheClavier;

/**
 * TODO Expliquer le fonctionnement de la classe
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class FenetreJeu extends JFrame implements ChangementLangue,
		Serializable {

	/**
	 * Générer automatiquement par Eclipse
	 */
	private static final long serialVersionUID = -8242465387808534110L;

	/**
	 * Panneau du jeu RainbowRobot ou se déroule réelement une partie
	 */
	private PartieDessinable partieDessinable;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private PanneauCarte carte;
	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JLabel timer = new JLabel("00 : 00");
	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JButton menu;
	/**
	 * Titre de la fenêtre
	 */
	private JLabel la_titre = new JLabel("MODE STORY");
	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JLabel caisseARecuperer = new JLabel(
			"Liste des caisses à récupérer");
	/**
	 * Référence des traductions effectuées dans ChoixLangue.java
	 */
	private ChoixLangue traducteur = ChoixLangue.getChoixLangue();

	/**
	 * Bouton qui permet de mettre en pause la partie en cours
	 */
	private JButton bt_Pause;

	ActionListener tache_timer;

	private int seconde, minute, delais = 1000;

	/**
	 * Initialise les composants et les disposent sur un contexte graphique 2D.
	 * La fenêtre s'affiche au centre de l'écran et n'est pas redimensionnable
	 * pour éviter tous soucis de disposition. Cette fenêtre détecte uniquement
	 * les cliques de la souris sur les boutons.
	 */
	public FenetreJeu(ClicSouris gestion, ToucheClavier gestionClavier) {
		super();

		super.setSize(UtilitaireFenetre.DIM_FENETRE);
		// On rend la fenêtre non redimenssionable
		super.setResizable(false);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Titre de la fenêtre
		super.setTitle("Mode Jeu (Story)");

		Container contentPane = super.getContentPane();
		Container contentMenuHaut = new JPanel();
		contentMenuHaut.setLayout(new FlowLayout(FlowLayout.CENTER));

		// On ajoute les composants dans la fenêtre
		contentMenuHaut.add(getTimer());
		contentMenuHaut.add(getCaiseARecuperer());
		contentMenuHaut.add(getBt_Pause());

		// Mise en forme du JLabel
		// Nouvelle police d'écriture
		Font font = new Font("Arial", Font.BOLD, 24);
		timer.setFont(font);
		caisseARecuperer.setFont(font);
		// Ajout d'une bordure
		timer.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

		tache_timer = new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				if (seconde == 59) {
					seconde = -1;
					minute++;
				}
				seconde++;
				if (minute < 10 && seconde < 10) {
					timer.setText("0" + minute + " : " + "0" + seconde);
				} else if (minute < 10) {
					timer.setText("0" + minute + " : " + seconde);
				} else {
					timer.setText(minute + " : " + seconde);
				}
			}
		};

		// Instanciation du timer
		final Timer timer1 = new Timer(delais, tache_timer);

		timer1.start();

		// TODO arrêter le timer à la fin de la partie et lors du clic sur le
		// bouton pause
		// if(Partie.isFinished()) {
		// timer1.stop();
		// }
		//
		// On ajoute le nom des composants en fonction de la langue choisie
		setLangue();

		partieDessinable = new PartieDessinable(gestionClavier.getMetier());

		contentPane.add(contentMenuHaut, BorderLayout.PAGE_START);
		contentPane.add(partieDessinable, BorderLayout.CENTER);

		// On ajoute les évènements lors de l'appuie d'une touche sur le clavier
		super.addKeyListener(gestionClavier);
		super.setFocusable(true);
		super.requestFocus();

		getBt_Pause().addMouseListener(gestion);

		// On centre l'écran
		UtilitaireFenetre.centrerFenetre(this);
	}

	/**
	 * Affichage des caisses que le joueur doit récupérer
	 * 
	 * @return la liste de caisses
	 */
	public JLabel getCaiseARecuperer() {
		if (caisseARecuperer == null) {
			caisseARecuperer = new JLabel();
			caisseARecuperer
					.setPreferredSize(UtilitaireFenetre.DIM_COMPOSANT_SECONDAIRE);
		}
		return caisseARecuperer;
	}

	/**
	 * Affichage du timer de la partie
	 * 
	 * @return le timer
	 */
	public JLabel getTimer() {
		if (timer == null) {
			timer = new JLabel();
			timer.setPreferredSize(UtilitaireFenetre.DIM_COMPOSANT_SECONDAIRE);
		}
		return timer;

	}

	public JButton getBt_Pause() {
		if (bt_Pause == null) {
			bt_Pause = new JButton();
			// On définit une taille pour le bouton
			bt_Pause.setMaximumSize(UtilitaireFenetre.DIM_COMPOSANT_SECONDAIRE);
			bt_Pause.setMinimumSize(UtilitaireFenetre.DIM_COMPOSANT_SECONDAIRE);
			bt_Pause.setPreferredSize(UtilitaireFenetre.DIM_COMPOSANT_SECONDAIRE);
			bt_Pause.setText("Pause");
		}
		return bt_Pause;
	}

	/**
	 * TODO Expliquer le fonctionnement de la méthode
	 */
	public void pause() {
		// TODO - Création automaitque par VisualParadigm
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see vue.ChangementLangue#setLangue()
	 */
	@Override
	public void setLangue() {

	}
}
