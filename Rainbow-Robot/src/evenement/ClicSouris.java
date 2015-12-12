/*
 * ClicSouris.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package evenement;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import metier.JeuRainbow;
import vue.ChoixLangue;
import vue.F_aPropos;
import vue.F_abstractModeJeu;
import vue.F_accueil;
import vue.F_arcade;
import vue.F_choixMode;
import vue.F_custom;
import vue.F_jeuRainbow;
import vue.F_reccords;
import vue.F_story;

/**
 * Controleur lors d'un clic de la souris. Utilisé surtout pour naviguer entre
 * les pages.
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class ClicSouris implements MouseListener, Observer {

	/**
	 * Représentation du jeu Rainbow Robot (partie métier). Il permet de passer
	 * d'une partie à un autre.
	 */
	private JeuRainbow metier;

	/**
	 * Détection des clics sur une fenêtre. On peut traiter qu'une seule fenêtre
	 * car il n'y aura jamais deux fenêtres affichées en même temps.
	 */
	private JFrame vue;

	/**
	 * On initialise le constructeur par défaut.
	 */
	public ClicSouris() {
	}

	/**
	 * On initialise le constructeur avec la partie métier du jeu.
	 * 
	 * @param jeu
	 *            représentation du jeu Rainbow Robot (partie métier). Il
	 *            contient notamment les différents niveaux.
	 */
	public ClicSouris(JeuRainbow jeu) {
		this.metier = jeu;
	}

	/**
	 * Actualise la nouvelle partie a observé
	 */
	public void setObserver() {
		this.metier.getPartieCourante().addObserver(this);
	}

	/*
	 * Quand on presse la souris sur un composant (mais sans le relacher la
	 * pression) (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
	}

	/*
	 * Quand la souris quitte un composant. (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
	}

	/*
	 * Quand la souris sort d'un composant (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
	}

	/*
	 * Quand la souris presse et relache le clic sur un même composant un
	 * composant (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
		// On vérifie si la fenêtre que l'on contrôle est bien la fenêtre
		// d'accueil
		if (vue instanceof F_accueil) {
			F_accueil fenetreAccueil = (F_accueil) vue;

			// On vérifie quel bouton a été utilisé
			// Bouton Jouer
			if (e.getSource() == fenetreAccueil.getBt_Jouer()) {
				// On lance la fenêtre Jouer ChoixMode.java
				F_choixMode nouvelleFenetre = new F_choixMode(this);
				vue.setVisible(false);
				nouvelleFenetre.setVisible(true);
				setFenetre(nouvelleFenetre);
			}

			// Bouton Reccords
			if (e.getSource() == fenetreAccueil.getBt_Reccords()) {
				// On lance la page des reccords Reccords.java
				F_reccords nouvelleFenetre = new F_reccords(this);
				vue.setVisible(false);
				nouvelleFenetre.setVisible(true);
				setFenetre(nouvelleFenetre);
			}

			// Bouton Langue
			if (e.getSource() == fenetreAccueil.getBt_Langue()) {
				String[] traductionLangue = ChoixLangue.getChoixLangue()
						.getLangue();
				// On lance la page des langues ChoixLangue.java
				String[] traductionToutesLangues = ChoixLangue.getChoixLangue()
						.getToutesLangues();
				String resultat = (String) JOptionPane.showInputDialog(
						fenetreAccueil, traductionLangue[1],
						traductionLangue[0], JOptionPane.QUESTION_MESSAGE,
						null, traductionToutesLangues,
						traductionToutesLangues[0]);

				// On change la langue choisie
				ChoixLangue.getChoixLangue().setLangue(resultat);
				// On réactualise la fenêtre
				fenetreAccueil.setLangue();
			}

			// Bouton A Propos
			if (e.getSource() == fenetreAccueil.getBt_Apropos()) {
				// On lance la page d'a propos A Propos.java
				F_aPropos nouvelleFenetre = new F_aPropos(this);
				vue.setVisible(false);
				nouvelleFenetre.setVisible(true);
				setFenetre(nouvelleFenetre);
			}

			// Bouton Quitter
			if (e.getSource() == fenetreAccueil.getBt_Quitter()) {
				// On quitte l'application
				// Tableau contenant les mots pour quitter l'application
				// traduits selon la langue choisie
				String[] traductionQuitter = ChoixLangue.getChoixLangue()
						.getQuitter();
				// On demande si l'utilisateur veux vraiment quitter ou s'il a
				// commit une erreur

				int resultat = JOptionPane
						.showConfirmDialog(fenetreAccueil,
								traductionQuitter[1], traductionQuitter[0],
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
				if (resultat == JOptionPane.OK_OPTION) {
					// On quitte l'application
					vue.setVisible(false);
					// On arrête le déroulement logique de l'application
					System.exit(0);
				}
			}
		}

		// On vérifie si la fenêtre que l'on contrôle est bien la fenêtre
		// des reccords
		if (vue instanceof F_reccords) {
			F_reccords fenetreReccords = (F_reccords) vue;
			// On vérifie quel bouton a été utilisé
			// Bouton Retour
			if (e.getSource() == fenetreReccords.getBt_Retour()) {
				// On lance la fenêtre Accueil F_accueil.java
				F_accueil nouvelleFenetre = new F_accueil(this);
				vue.setVisible(false);
				nouvelleFenetre.setVisible(true);
				setFenetre(nouvelleFenetre);
			}
		}

		// On vérifie si la fenêtre que l'on contrôle est bien la fenêtre
		// de choix du mode
		if (vue instanceof F_choixMode) {
			F_choixMode fenetreChoixMode = (F_choixMode) vue;

			// On vérifie quel bouton a été utilisé
			// Bouton Story
			if (e.getSource() == fenetreChoixMode.getBt_Story()) {
				// On lance la fenêtre Story F_story.java
				F_story nouvelleFenetre = new F_story(this);
				vue.setVisible(false);
				nouvelleFenetre.setVisible(true);
				setFenetre(nouvelleFenetre);
			}

			// On vérifie quel bouton a été utilisé
			// Bouton Arcade
			if (e.getSource() == fenetreChoixMode.getBt_Arcade()) {
				// On lance la fenêtre Arcade F_arcade.java
				F_arcade nouvelleFenetre = new F_arcade(this);
				vue.setVisible(false);
				nouvelleFenetre.setVisible(true);
				setFenetre(nouvelleFenetre);
			}

			// On vérifie quel bouton a été utilisé
			// Bouton Custom
			if (e.getSource() == fenetreChoixMode.getBt_Custom()) {
				// On lance la fenêtre Custom F_custom.java
				F_custom nouvelleFenetre = new F_custom(this);
				vue.setVisible(false);
				nouvelleFenetre.setVisible(true);
				setFenetre(nouvelleFenetre);
			}

			// On vérifie quel bouton a été utilisé
			// Bouton Retour
			if (e.getSource() == fenetreChoixMode.getBt_Retour()) {
				// On lance la fenêtre Accueil F_accueil.java
				F_accueil nouvelleFenetre = new F_accueil(this);
				vue.setVisible(false);
				nouvelleFenetre.setVisible(true);
				setFenetre(nouvelleFenetre);
			}
		}

		if (vue instanceof F_abstractModeJeu) {
			F_abstractModeJeu fenetreAbastractModeJeu = (F_abstractModeJeu) vue;
			// On vérifie quel bouton a été utilisé
			// Bouton Jouer
			if (e.getSource() == fenetreAbastractModeJeu.getBt_Jouer()
					&& fenetreAbastractModeJeu.getBt_Jouer().isEnabled()) {
				// On lance la fenêtre Accueil F_accueil.java
				// Détecte les appuie sur les touches de clavier
				ToucheClavier clavier = new ToucheClavier(metier);
				// On détecte les fins de partie et les pauses
				F_jeuRainbow nouvelleFenetre = new F_jeuRainbow(this, clavier);
				setObserver();
				vue.setVisible(false);
				nouvelleFenetre.setVisible(true);
				setFenetre(nouvelleFenetre);
			}
			// On vérifie quel bouton a été utilisé
			// Bouton Retour
			if (e.getSource() == fenetreAbastractModeJeu.getBt_Retour()) {
				// On lance la fenêtre Accueil F_accueil.java
				F_choixMode nouvelleFenetre = new F_choixMode(this);
				vue.setVisible(false);
				nouvelleFenetre.setVisible(true);
				setFenetre(nouvelleFenetre);
			}
		}

		if (vue instanceof F_custom) {
			F_custom fenetreCustom = (F_custom) vue;
			if (e.getSource() == fenetreCustom.getBt_Parcourir()) {
				// Objet qui permet de naviguer dans les dossiers personnels
				JFileChooser chooser = new JFileChooser();
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					fenetreCustom.getTf_cheminFichier().setText(
							chooser.getSelectedFile().getAbsolutePath()
									.toString());
				}
			}
		}
		if (vue instanceof F_jeuRainbow) {
			F_jeuRainbow fenetreJeu = (F_jeuRainbow) vue;
			if (e.getSource() == fenetreJeu.getBt_Pause()) {
				// On vérifie quel bouton a été utilisé
				// Bouton Pause
				// 1ère fenêtre de discussion avec l'utilisateur
				fenetreJeu.stopChrono();
				String[] traductionMenuPause = ChoixLangue.getChoixLangue()
						.getMenuPause();
				String[] traductionBouton = Arrays.copyOfRange(
						traductionMenuPause, 2, traductionMenuPause.length);
				int retour = JOptionPane.showOptionDialog(null,
						traductionMenuPause[0], traductionMenuPause[1],
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, traductionBouton,
						traductionBouton[0]);
				switch (retour) {
				case 0: // Continuer
					fenetreJeu.startChrono();
					fenetreJeu.requestFocus();
					break;
				case 1: // Recommencer
					metier.reinitialiserPartie();
					fenetreJeu.getPartieDessinable().setJeuRainbowRobot(
							metier.getPartieCourante());
					setObserver();
					fenetreJeu.restartChrono();
					fenetreJeu.requestFocus();
					break;
				case 2: // Quitter
					// On revient à l'accueil
					String[] traductionMenuQuitterPartie = ChoixLangue
							.getChoixLangue().getQuitterPartie();

					int option = JOptionPane.showConfirmDialog(null,
							traductionMenuQuitterPartie[0],
							traductionMenuQuitterPartie[1],
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (option == JOptionPane.YES_OPTION) {
						metier.reinitialiserPartie();
						F_accueil fenetreAccueil = new F_accueil(this);
						vue.setVisible(false);
						setFenetre(fenetreAccueil);
						vue.setVisible(true);
					} else {
						fenetreJeu.startChrono();
						fenetreJeu.requestFocus();
					}
					break;
				}
			}

		}

		// if (fenetre instanceof MenuPause) {
		// MenuPause fenetrePause = (MenuPause) fenetre;
		// if (e.getSource() == fenetrePause.getBt_Reprendre()) {
		// fenetre.setVisible(false);
		// }
		// if (e.getSource() == fenetrePause.getBt_Recommencer()) {
		// ToucheClavier clavier = new ToucheClavier(jeu);
		// FenetreJeu nouvelleFenetre = new FenetreJeu(this, clavier);
		// fenetre.setVisible(false);
		// setFenetre(nouvelleFenetre);
		// }
		// if (e.getSource() == fenetrePause.getBt_Quitter()) {
		// int option = JOptionPane.showConfirmDialog(null,
		// "Voulez-vous vraiment quitter la partie en cours ?",
		// "Quitter", JOptionPane.YES_NO_CANCEL_OPTION,
		// JOptionPane.QUESTION_MESSAGE);
		// if (option == JOptionPane.YES_OPTION) {
		// F_accueil nouvelleFenetre = new F_accueil(this);
		// fenetre.setVisible(false);
		// setFenetre(nouvelleFenetre);
		// } else if (option == JOptionPane.NO_OPTION) {
		// setFenetre(fenetre);
		// } else if (option == JOptionPane.CANCEL_OPTION) {
		// setFenetre(fenetre);
		// }
		//
		// }
		// }
	}

	/*
	 * Quand la souris est relaché sur un composant (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
	}

	/**
	 * @param nouvelleFenetre
	 *            la nouvelle fenêtre à controller
	 */
	public void setFenetre(JFrame nouvelleFenetre) {
		this.vue = nouvelleFenetre;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (arg == metier.getPartieCourante()) {
			F_jeuRainbow fenetre = (F_jeuRainbow) vue;
			// On reinitialise les paramètres
			metier.reinitialiserPartie();
			fenetre.restartChrono();
			fenetre.stopChrono();

			String[] traductionFinPartie = ChoixLangue.getChoixLangue()
					.getFinPartie();
			String[] traductionBouton = Arrays.copyOfRange(traductionFinPartie,
					2, traductionFinPartie.length);
			int retour = JOptionPane.showOptionDialog(null,
					traductionFinPartie[0], traductionFinPartie[1],
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, traductionBouton, traductionBouton[0]);
			switch (retour) {
			case 0: // Recommencer
				fenetre.getPartieDessinable().setJeuRainbowRobot(
						metier.getPartieCourante());
				setObserver();
				fenetre.startChrono();
				break;
			case 1: // Partie suivante
				metier.setNiveauSuivant();
				fenetre.getPartieDessinable().setJeuRainbowRobot(
						metier.getPartieCourante());
				setObserver();
				fenetre.startChrono();
				break;
			case 2: // Quitter
				// On revient à l'accueil
				F_accueil fenetreAccueil = new F_accueil(this);
				vue.setVisible(false);
				setFenetre(fenetreAccueil);
				fenetreAccueil.setVisible(true);
				break;
			}
		}
	}

}
