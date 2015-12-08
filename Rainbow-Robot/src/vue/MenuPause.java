/*
 * MenuPause.java							28 nov 2015
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
 * TODO Expliquer le fonctionnement de la classe
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class MenuPause extends JFrame implements ChangementLangue{

	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JLabel titre = new JLabel("Pause");
	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JButton bt_Reprendre;
	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JButton bt_Recommencer;
	/**
	 * TODO Expliquer le fonctionnement de la variable d'instance
	 */
	private JButton bt_Quitter;

	/**
	 * TODO Expliquer le fonctionnement du constructeur
	 */
	public MenuPause(ClicSouris gestion) {
		super();

		super.setSize(UtilitaireFenetre.DIM_FENETRE_PAUSE);
		// On rend la fenêtre non redimenssionable
		super.setResizable(false);
		
		// ---------------------------------------------------------------------
		// Ajout des boutons Story, Arcade et Custom ainsi que de leurs
		// descriptions
		// ---------------------------------------------------------------------
		Container contentMenuPause = new JPanel();
		// On admet que la disposition des boutons et leurs descriptions ne
		// dépassent pas les 2/3 de la fenêtre
		contentMenuPause.setPreferredSize(new Dimension(
				UtilitaireFenetre.DIM_FENETRE_PAUSE.width,
				(2 * UtilitaireFenetre.DIM_FENETRE_PAUSE.height) /3));
		contentMenuPause
		.setLayout(new BoxLayout(contentMenuPause, BoxLayout.Y_AXIS));
		
		Container contentPane = super.getContentPane();
		contentPane.add(contentMenuPause, BorderLayout.CENTER);

		// On ajoute les composants dans la fenêtre
		UtilitaireFenetre.addAComposantWithBoxLayout(getLa_titre(),
				contentMenuPause, 0, 30, Component.CENTER_ALIGNMENT);
		UtilitaireFenetre.addAComposantWithBoxLayout(getBt_Reprendre(),
				contentMenuPause, 0, 50, Component.CENTER_ALIGNMENT);
		UtilitaireFenetre.addAComposantWithBoxLayout(getBt_Recommencer(),
				contentMenuPause, 0, 50, Component.CENTER_ALIGNMENT);
		UtilitaireFenetre.addAComposantWithBoxLayout(getBt_Quitter(),
				contentMenuPause, 0, 50, Component.CENTER_ALIGNMENT);
		
		// On ajoute le nom des composants en fonction de la langue choisie
		setLangue();

		// On centre l'écran
		UtilitaireFenetre.centrerFenetre(this);

		// On ajoute les évènements sur les boutons
		getBt_Reprendre().addMouseListener(gestion);
		getBt_Recommencer().addMouseListener(gestion);
		getBt_Quitter().addMouseListener(gestion);


	}

	/**
	 * TODO Expliquer le fonctionnement de la méthode
	 */
	public void quitter() {
		// TODO - Création automaitque par VisualParadigm
	}
	
	/**
	 * @return the bt_Retour
	 */
	public JButton getBt_Reprendre() {
		if (bt_Reprendre == null) {
			bt_Reprendre = new JButton();
			// On définit une taille pour le bouton
			UtilitaireFenetre.setAllSize(bt_Reprendre,
					UtilitaireFenetre.DIM_COMPOSANT_SECONDAIRE);
			bt_Reprendre.setText("Reprendre");
		}
		return bt_Reprendre;
	}
	
	/**
	 * @return the la_titre
	 */
	public JLabel getLa_titre() {
		if (titre == null) {
			titre = new JLabel();
			titre.setPreferredSize(UtilitaireFenetre.DIM_COMPOSANT_PRINCIPAL);
		}
		return titre;
	}
	
	/**
	 * @return bt_Recommencer
	 */
	public JButton getBt_Recommencer() {
		if (bt_Recommencer == null) {
			bt_Recommencer = new JButton();
			// On définit une taille pour le bouton
			UtilitaireFenetre.setAllSize(bt_Recommencer,
					UtilitaireFenetre.DIM_COMPOSANT_SECONDAIRE);
			bt_Recommencer.setText("Recommencer");
		}
		return bt_Recommencer;
	}
	
	/**
	 * @return bt_Quitter
	 */
	public JButton getBt_Quitter () {
		if (bt_Quitter == null) {
			bt_Quitter = new JButton();
			// On définit une taille pour le bouton
			UtilitaireFenetre.setAllSize(bt_Quitter,
					UtilitaireFenetre.DIM_COMPOSANT_SECONDAIRE);
			bt_Quitter.setText("Quitter");
		}
		return bt_Quitter;
	}

	@Override
	public void setLangue() {
		// TODO Auto-generated method stub
		
	}

}