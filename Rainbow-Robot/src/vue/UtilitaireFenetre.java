/*
 * UtilitaireFenetre.java							29 nov. 2015
 * IUT Info2 2015-2016
 */
package vue;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Window;

import javax.swing.Box;
import javax.swing.JComponent;

/**
 * <p>
 * Classe utilitaire regroupant les différentes fonctions et constantes
 * utilisées dans la création des fenêtres. <br />
 * </p>
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class UtilitaireFenetre {
    
        /**
         * Dimension des boutons dans la fenêtre de modification des 
         * commandes
         */
        public static final Dimension DIM_BOUTON_COMMANDE = new Dimension(55,
                        30);
        
        /**
         * Dimension des composants(JLabels) de la fenêtre commande
         */
        public static final Dimension DIM_COMPOSANT_COMMANDE = new Dimension(150,
                        60);
        
        /**
         * Dimension des boutons du bas (sauvegarder, annuler, reset)
         * de la fenêtre commande
         */
        public static final Dimension DIM_BOUTON_BAS_COMMANDE = new Dimension(110,
                        30);

	/** Dimension des boutons des fenêtres principales */
	public static final Dimension DIM_COMPOSANT_PRINCIPAL = new Dimension(650,
			75);

	/** Dimension des boutons des fenêtres principales */
	public static final Dimension DIM_COMPOSANT_SECONDAIRE = new Dimension(100,
			60);
	/** Dimension des fenêtres usuelles */
	public static final Dimension DIM_FENETRE = new Dimension(1000, 750);
	
	/** Dimensions de la fenêtre d'accueil */
        public static final Dimension DIM_FENETRE_ACCUEIL = new Dimension(1000, 800);
        
	/**
         * Dimension des fenêtres usuelles
         */
        public static final Dimension DIM_FENETRE_COMMANDES = new Dimension(550, 500);
        
	/**
	 * Dimension d'une case vide. Une case vide est une case en fond représentée
	 * lors d'une partie du jeu.
	 */
	public static final Dimension DIM_CASE_VIDE = new Dimension(50, 50);

	/** Dimension de l'image d'une caisse */
	public static final Dimension DIM_CAISSE = new Dimension(40, 40);

	/** Dimension de l'image du robot */
	public static final Dimension DIM_ROBOT = new Dimension(100, 100);

	/** Dimension d'une case de caisse à récupérée */
	public static final Dimension DIM_CAISSE_RECUPEREE = new Dimension(60, 70);

	/**
	 * Largeur de la bordure d'une case vide. Une case vide est une case en fond
	 * représentée lors d'une partie du jeu.
	 */
	public static final float LARGEUR_BORDURE = 2.0f;

	/**
	 * Couleur de fond d'une case vide. Une case vide est une case en fond
	 * représentée lors d'une partie du jeu.
	 */
	public static final Color COULEUR_FOND = new Color(210, 211, 213);

	/**
	 * Couleur de la bordure d'une case vide. Une case vide est une case en fond
	 * représentée lors d'une partie du jeu.
	 */
	public static final Color COULEUR_BORDURE = new Color(52, 102, 164);

	/** Couleur du vortex */
	public static final Color COULEUR_VORTEX = new Color(88, 88, 90);

	/** Largeur de la bordure des caisses a récupérées */
	public static final float LARGEUR_BORDURE_CAISSE = 5.0f;

	/** Couleur de fond des caisses a récupérées */
	public static final Color COULEUR_FOND_CAISSE = new Color(190, 191, 193);

	/** Couleur de fond de la première caisse à récupérée */
	public static final Color COULEUR_FOND_CAISSE_UN = new Color(231, 231, 233);

	/** Marge entre deux caisses a récupérées */
	public static final int MARGE_ENTRE_CAISSE = 5;

	/** Nombre de caisse affichée dans le Panneau */
	public static final int NB_CAISSE_AFFICHE = 5;

	/** Nombre de niveaux par ligne. Utilisé dans F_choixNiveau */
	public static final int NB_NIVEAU_LIGNE = 5;

	/**
	 * Centre la fenêtre par rapport au dimension de l'écran, c'est à dire le
	 * centre de la fenêtre sera placé au centre de l'écran de l'utilisateur.
	 * 
	 * @param aCentrer
	 *            fenêtre que l'on doit centrer
	 */
	public static void centrerFenetre(Window aCentrer) {
		// On centre la fenêtre par rapport à la taille de l'écran
		aCentrer.setLocation(((int) java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize().getWidth() / 2)
				- aCentrer.getWidth() / 2, ((int) java.awt.Toolkit
				.getDefaultToolkit().getScreenSize().getHeight() / 2)
				- aCentrer.getHeight() / 2);
	}

	/**
	 * Ajoute un nouveau composant dans le container en utilisant le Layout
	 * Manager BoxLayout.
	 * 
	 * @param aAjouter
	 *            nouveau composant à ajouter
	 * @param pane
	 *            container où on ajoute le composant
	 * @param largeur
	 *            largeur séparant le composant de l'ancien composant ou du
	 *            composant parent s'il n'y en a pas horizontalement
	 * @param hauteur
	 *            hauteur séparant le composant de l'ancien composant ou du
	 *            composant parent s'il n'y en a pas verticalement
	 * @param alignementX
	 *            alignement horizontal par rapport au container parent (pane)
	 */
	public static void addAComposantWithBoxLayout(JComponent aAjouter,
			Container pane, int largeur, int hauteur, float alignementX) {
		// On aligne le composant horizontalement par rapport à la fenêtre
		aAjouter.setAlignmentX(alignementX);
		if (largeur > 0 || hauteur > 0) {
			pane.add(Box.createRigidArea(new Dimension(largeur, hauteur)));
		}
		pane.add(aAjouter);
	}

	/**
	 * On force le composant à prendre la forme qu'il désire, c'est à dire on
	 * définit la taille minimum, préferrée et maximale qu'un composant peut
	 * prendre. Il est possible qu'avec certain layout, même en forçant cela ne
	 * marche pas.
	 * 
	 * @param component
	 *            composant dont on veut forcer sa taille
	 * @param largeur
	 *            largeur que doit prendre le composant
	 * @param hauteur
	 *            hauteur que doit prendre le composant
	 */
	public static void setAllSize(JComponent component, int largeur, int hauteur) {
		component.setMinimumSize(new Dimension(largeur, hauteur));
		component.setPreferredSize(new Dimension(largeur, hauteur));
		component.setMaximumSize(new Dimension(largeur, hauteur));
	}

	/**
	 * On force le composant à prendre la forme qu'il désire, c'est à dire on
	 * définit la taille minimum, préférée et maximale qu'un composant peut
	 * prendre. Il est possible qu'avec certain layout, même en forçant cela ne
	 * marche pas.
	 * 
	 * @param component
	 *            composant dont on veut forcer sa taille
	 * @param dim
	 *            la dimension souhaitée
	 */
	public static void setAllSize(JComponent component, Dimension dim) {
		component.setMinimumSize(dim);
		component.setPreferredSize(dim);
		component.setMaximumSize(dim);
	}
}
