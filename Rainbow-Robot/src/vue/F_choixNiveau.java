/*
 * D_choixNiveau.java							23 févr. 2016
 * IUT Info2 2015-2016
 */
package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import metier.JeuRainbow;
import evenement.ClicSouris;

/**
 * Fenêtre modal permettant à l'utilisateur de choisir le niveau pour le mode
 * Story.
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class F_choixNiveau extends JFrame implements ChangementLangue {

	/** Généré automatiquement par Eclipse */
	private static final long serialVersionUID = 1L;

	/** Tableau de boutons contenant les différents niveaux du mode story */
	JButton[] bt_niveaux;

	/** Revient à la fenêtre précédente, c'est à dire F_Story */
	JButton bt_precedent;

	/** Lance le mode story */
	JButton bt_jouer;

	/** Niveau que l'utilisateur a choisi */
	private int niveauChoisi;

	/**
	 * TODO Expliquer le fonctionnement du constructeur
	 * 
	 * @param gestion
	 */
	public F_choixNiveau(ClicSouris gestion) {
		super();
		// TODO Redéfinir une taille
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

		contentBoutons.add(getBt_Precedent());
		contentBoutons.add(getBt_Jouer());

		// On place les deux boutons
		// Bouton Retour
		layout_boutons.putConstraint(SpringLayout.WEST, getBt_Precedent(), 20,
				SpringLayout.WEST, contentBoutons);
		layout_boutons.putConstraint(SpringLayout.NORTH, getBt_Precedent(), 20,
				SpringLayout.NORTH, contentBoutons);
		// Bouton Jouer
		// -10 <=> 10 pixels entre le panneau des boutons et le boutons bt_jouer
		layout_boutons.putConstraint(SpringLayout.EAST, getBt_Jouer(), -20,
				SpringLayout.EAST, contentBoutons);
		layout_boutons.putConstraint(SpringLayout.NORTH, getBt_Jouer(), 20,
				SpringLayout.NORTH, contentBoutons);

		// ---------------------------------------------------------------------
		// On dispose les boutons représentant les niveaux
		// ---------------------------------------------------------------------
		Container contentNiveaux = new JPanel(
				new FlowLayout(
						FlowLayout.LEFT,
						(UtilitaireFenetre.DIM_FENETRE.width - UtilitaireFenetre.NB_NIVEAU_LIGNE
								* UtilitaireFenetre.DIM_COMPOSANT_SECONDAIRE.width)
								/ UtilitaireFenetre.NB_NIVEAU_LIGNE + 1, 50));
		final int nbNiveaux = gestion.getMetier().getNbNiveau();
		final int niveauMax = gestion.getMetier().getNiveauMax();
		bt_niveaux = new JButton[nbNiveaux];

		for (int i = 0; i < bt_niveaux.length; i++) {
			if (i > niveauMax) {
				bt_niveaux[i] = new JButton("" + i);
				UtilitaireFenetre.setAllSize(bt_niveaux[i],
						UtilitaireFenetre.DIM_COMPOSANT_SECONDAIRE);
				bt_niveaux[i].setEnabled(false);
			} else {
				// On créé un sous genre de Bouton mais pour faire simple, on
				// créé une classe anonyme.
				bt_niveaux[i] = new JButton();
				// On ajoute ne détection du clic.
				// On gère les évenements des boutons niveaux en interne pour
				// éviter de boucler dans ClicSouris
				bt_niveaux[i].addActionListener(new ActionListener() {
					/*
					 * Détection du clic de la souris sur le bouton
					 * 
					 * @see
					 * java.awt.event.ActionListener#actionPerformed(java.awt
					 * .event .ActionEvent)
					 */
					@Override
					public void actionPerformed(ActionEvent e) {
						niveauChoisi = Integer.parseInt(((JButton) e
								.getSource()).getText());
					}

				});
				bt_niveaux[i].setText("" + i);
				UtilitaireFenetre.setAllSize(bt_niveaux[i],
						UtilitaireFenetre.DIM_COMPOSANT_SECONDAIRE);
			}
			contentNiveaux.add(bt_niveaux[i]);
			// TODO Vérouiller l'accès au niveau que l'on a pas atteint
		}

		// ---------------------------------------------------------------------
		// On dispose les éléments dans la fenêtre
		// ---------------------------------------------------------------------
		// On ajoute le panneaux des boutons sur la fenêtre
		contentPane.add(contentNiveaux, BorderLayout.CENTER);
		contentPane.add(contentBoutons, BorderLayout.SOUTH);

		// On ajoute la langue
		setLangue();

		// On centre l'écran
		UtilitaireFenetre.centrerFenetre(this);

		// On ajoute les évènements sur les boutons
		getBt_Precedent().addMouseListener(gestion);
		getBt_Jouer().addMouseListener(gestion);

		// Niveau par défaut
		niveauChoisi = JeuRainbow.DEFAULT_NIVEAU;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see vue.ChangementLangue#setLangue()
	 */
	@Override
	public void setLangue() {
		String[] traduction = ChoixLangue.getChoixLangue().getChoixNiveau();
		super.setTitle(traduction[0]);
		getBt_Precedent().setText(traduction[1]);
		getBt_Jouer().setText(traduction[2]);
	}

	/**
	 * @return le bu_precedent
	 */
	public JButton getBt_Precedent() {
		if (bt_precedent == null) {
			bt_precedent = new JButton();
			UtilitaireFenetre.setAllSize(bt_precedent,
					UtilitaireFenetre.DIM_COMPOSANT_SECONDAIRE);
		}
		return bt_precedent;
	}

	/**
	 * @return le bu_jouer
	 */
	public JButton getBt_Jouer() {
		if (bt_jouer == null) {
			bt_jouer = new JButton();
			UtilitaireFenetre.setAllSize(bt_jouer,
					UtilitaireFenetre.DIM_COMPOSANT_SECONDAIRE);
		}
		return bt_jouer;
	}

	/**
	 * @return le niveauChoisi
	 */
	public int getNiveauChoisi() {
		return niveauChoisi;
	}
}
