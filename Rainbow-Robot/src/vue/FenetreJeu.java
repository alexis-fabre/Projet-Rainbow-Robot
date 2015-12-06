/*
 * Fenetre.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package vue;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Partie;
import evenement.ClicSouris;

/**
 * TODO Expliquer le fonctionnement de la classe
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class FenetreJeu extends JFrame implements ChangementLangue, Observer {

	/**
	 * Panneau du jeu RainbowRobot ou se déroule réelement une partie
	 */
	private PartieDessinable partie;

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private PanneauCarte carte;
	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JLabel getTimer = new JLabel("00:00");
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
	private JLabel getCaiseARecuperer = new JLabel(
			"Liste des caisses à récupérer");
	/**
	 * Référence des traductions effectuées dans ChoixLangue.java
	 */
	private ChoixLangue traducteur = ChoixLangue.getChoixLangue();

	/**
	 * Bouton qui permet de mettre en pause la partie en cours
	 */
	private JButton bt_Pause;

	/**
	 * Initialise les composants et les disposent sur un contexte graphique 2D.
	 * La fenêtre s'affiche au centre de l'écran et n'est pas redimensionnable
	 * pour éviter tous soucis de disposition. Cette fenêtre détecte uniquement
	 * les cliques de la souris sur les boutons.
	 */
	public FenetreJeu(ClicSouris gestion) {
		super();

		super.setSize(UtilitaireFenetre.DIM_FENETRE);
		// On rend la fenêtre non redimenssionable
		super.setResizable(false);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container contentPane = super.getContentPane();
		Container contentMenuHaut = new JPanel();
		contentMenuHaut.setLayout(new FlowLayout(FlowLayout.CENTER));

		// On ajoute les composants dans la fenêtre
		contentMenuHaut.add(getTimer());
		contentMenuHaut.add(getCaiseARecuperer());
		contentMenuHaut.add(getBt_Pause());

		// On ajoute le nom des composants en fonction de la langue choisie
		setLangue();

		// *********************************************************************
		// Test expérimental
		// *********************************************************************
		partie = new PartieDessinable(null, new Partie(9, 11));

		contentPane.add(contentMenuHaut, BorderLayout.PAGE_START);
		contentPane.add(partie, BorderLayout.CENTER);

		getBt_Pause().addMouseListener(gestion);

		// On centre l'écran
		UtilitaireFenetre.centrerFenetre(this);
	}

	/**
	 * Affichage des caisses que le joueur doit récypérer
	 * 
	 * @return la liste de caisses
	 */
	private JLabel getCaiseARecuperer() {
		if (getCaiseARecuperer == null) {
			getCaiseARecuperer = new JLabel();
			getCaiseARecuperer
					.setPreferredSize(UtilitaireFenetre.DIM_COMPOSANT_SECONDAIRE);
		}
		return getCaiseARecuperer;
	}

	/**
	 * Affichage du timer de la partie
	 * 
	 * @return le timer
	 */
	public JLabel getTimer() {
		if (getTimer == null) {
			getTimer = new JLabel();
			getTimer.setPreferredSize(UtilitaireFenetre.DIM_COMPOSANT_SECONDAIRE);
		}
		return getTimer;

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

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}