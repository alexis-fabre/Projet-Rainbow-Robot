/*
 * Fenetre.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package vue;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import metier.Partie;
import evenement.ClicSouris;
import evenement.ToucheClavier;

/**
 * <p>
 * Fenêtre qui affiche une partie du jeu Rainbow Robot.<br />
 * Elle contient un timer qui s'actualise chaque seconde, un panneau contenant
 * les caisses à récupérer, un bouton Pause pour mettre le jeu en pause et un
 * panneau contenant le plateau de jeu.<br />
 * Sur le plateau de jeu, on peut y voir un Robot que l'on peut contrôler grâce
 * aux touches directionnelles du clavier, la touche espace pour récupérer une
 * caisse et la touche Ctrl pour faire fusionner deux caisses. Mais aussi des
 * caisses qu'il faut déposer dans le vortex (case grisé dans le plateau de jeu)
 * <br />
 * La fenêtre respecte le modèle MVC. C'est pour cela que chaque composant
 * dispose d'un getter afin de faciliter les transitions entre les fenêtres.
 * </p>
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class F_jeuRainbow extends JFrame implements ChangementLangue {

	/**
	 * Panneau du jeu RainbowRobot qui contient l'interface graphique 2D du jeu
	 * (avec le robot, les caisses et le vortex).
	 */
	private P_partieDessinable partieDessinable;

	/**
	 * Label contenant le timer qui s'actualise toutes les secondes
	 */
	private JLabel la_timer = new JLabel("00 : 00");

	/**
	 * Panneau qui contient l'interface graphique 2D des caisses a récupérées.
	 */
	private P_caisseADessiner caisseARecuperer;

	/**
	 * Bouton qui permet de mettre en pause la partie en cours
	 */
	private JButton bt_Pause;

	/**
	 * Chronomètre qui permet de calculer le temps mis pour finir le niveau
	 */
	private Timer chrono;

	/**
	 * Délais d'une seconde (en microsecondes)
	 */
	public static final int DELAIS = 1000;

	/**
	 * Variable pour afficher le timer
	 */
	private int seconde, minute;

	/**
	 * <p>
	 * Initialise les composants et les disposent sur un contexte graphique 2D.<br />
	 * La fenêtre s'affiche au centre de l'écran et n'est pas redimensionnable
	 * pour éviter tous soucis de disposition.<br />
	 * Cette fenêtre détecte uniquement les cliques de la souris sur les
	 * boutons.
	 * </p>
	 * 
	 * @param gestion
	 *            le contrôleur qui va controler cette vue = cible
	 *            evenementielle
	 * @param gestionClavier
	 *            Le contrôleur qui va détecter les touches du clavier
	 */
	public F_jeuRainbow(ClicSouris gestion, ToucheClavier gestionClavier) {
		super();

		super.setSize(UtilitaireFenetre.DIM_FENETRE);
		// On rend la fenêtre non redimenssionable
		super.setResizable(false);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Titre de la fenêtre
		super.setTitle("Mode Jeu (Story)");

		Container contentPane = super.getContentPane();
		Container contentMenuHaut = new JPanel();
		contentMenuHaut.setLayout(new BoxLayout(contentMenuHaut,
				BoxLayout.X_AXIS));

		// On ajoute les composants dans la fenêtre
		contentMenuHaut.add(Box.createHorizontalStrut(30));
		contentMenuHaut.add(getTimer());
		contentMenuHaut.add(Box.createGlue());
		// On créer les caisses a récupérées
		caisseARecuperer = new P_caisseADessiner(gestionClavier.getMetier());
		UtilitaireFenetre.setAllSize(caisseARecuperer,
				UtilitaireFenetre.DIM_CAISSE_RECUPEREE.width
						* UtilitaireFenetre.NB_CAISSE_AFFICHE
						+ UtilitaireFenetre.MARGE_ENTRE_CAISSE
						* UtilitaireFenetre.NB_CAISSE_AFFICHE + 1,
				UtilitaireFenetre.DIM_CAISSE_RECUPEREE.height);

		contentMenuHaut.add(caisseARecuperer);
		contentMenuHaut.add(Box.createGlue());
		contentMenuHaut.add(getBt_Pause());
		contentMenuHaut.add(Box.createHorizontalStrut(30));

		// Mise en forme du JLabel
		// Nouvelle police d'écriture
		Font font = new Font("Arial", Font.BOLD, 24);
		la_timer.setFont(font);
		// Ajout d'une bordure
		la_timer.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

		// Instanciation du timer
		chrono = new Timer(DELAIS, new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				if (seconde == 59) {
					seconde = -1;
					minute++;
				}
				seconde++;
				if (minute < 10 && seconde < 10) {
					la_timer.setText("0" + minute + " : " + "0" + seconde);
				} else if (minute < 10) {
					la_timer.setText("0" + minute + " : " + seconde);
				} else {
					la_timer.setText(minute + " : " + seconde);
				}
			}
		});
		chrono.start();

		// On ajoute le nom des composants en fonction de la langue choisie
		setLangue();

		partieDessinable = new P_partieDessinable(gestionClavier.getMetier());

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
	 * @return le JLabel la_timer qui affiche le timer de la partie
	 */
	public JLabel getTimer() {
		if (la_timer == null) {
			la_timer = new JLabel();
			UtilitaireFenetre.setAllSize(la_timer,
					UtilitaireFenetre.DIM_COMPOSANT_SECONDAIRE);
		}
		return la_timer;
	}

	/**
	 * @return le score de la partie lorsque celle-ci est finie
	 */
	public String getScore() {
		return minute + ":" + seconde;
	}

	/**
	 * @return le JButton bt_Pause qui permet de mettre en pause le jeu
	 */
	public JButton getBt_Pause() {
		if (bt_Pause == null) {
			bt_Pause = new JButton();
			// On définit une taille pour le bouton
			UtilitaireFenetre.setAllSize(bt_Pause,
					UtilitaireFenetre.DIM_COMPOSANT_SECONDAIRE);
			bt_Pause.setText("Pause");
		}
		return bt_Pause;
	}

	/**
	 * Permet de lancer le chronomètre
	 */
	public void startChrono() {
		chrono.start();
	}

	/**
	 * Permet de stopper le chronomètre
	 */
	public void stopChrono() {
		chrono.stop();
	}

	/**
	 * Permet de faire redémarrer le chrono
	 */
	public void restartChrono() {
		minute = seconde = 0;
		la_timer.setText("00 : 00");
		chrono.start();
	}

	/**
	 * @return le "JPanel" partieDessinable qui contient l'interface graphique
	 *         2D du jeu (avec le robot, les caisses et le vortex).
	 */
	public P_partieDessinable getPartieDessinable() {
		return partieDessinable;
	}

	/**
	 * @return le "JPanel" caisseARecuperer qui contient l'interface graphique
	 *         2D des caisses a récupérées.
	 */
	public P_caisseADessiner getCaisseADessiner() {
		return caisseARecuperer;
	}

	/**
	 * Réactualise la partie courante envoyé dans JeuRainbow
	 */
	public void setJeuRainbowRobot(Partie nouvellePartie) {
		getCaisseADessiner().setJeuRainbowRobot(nouvellePartie);
		getPartieDessinable().setJeuRainbowRobot(nouvellePartie);
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
