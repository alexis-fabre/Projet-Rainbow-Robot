/*
 * ClicSouris.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package evenement;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import metier.JeuRainbow;
import vue.ChoixLangue;
import vue.ChoixMode;
import vue.F_aPropos;
import vue.F_abstractModeJeu;
import vue.F_accueil;
import vue.F_arcade;
import vue.F_custom;
import vue.F_story;
import vue.FenetreJeu;
import vue.MenuPause;
import vue.Reccords;

/**
 * Controleur lors d'un clic de la souris. Utilisé surtout pour naviguer entre
 * les pages.
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class ClicSouris implements MouseListener {

	/**
	 * Représentation du jeu Rainbow Robot (partie métier). Il permet de passer
	 * d'une partie à un autre.
	 */
	private JeuRainbow jeu;

	/**
	 * Détection des clics sur une fenêtre. On peut traiter qu'une seule fenêtre
	 * car il n'y aura jamais deux fenêtres affichées en même temps.
	 */
	private JFrame fenetre;

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
		this.jeu = jeu;
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
		if (fenetre instanceof F_accueil) {
			F_accueil fenetreAccueil = (F_accueil) fenetre;

			// On vérifie quel bouton a été utilisé
			// Bouton Jouer
			if (e.getSource() == fenetreAccueil.getBt_Jouer()) {
				// On lance la fenêtre Jouer ChoixMode.java
				ChoixMode nouvelleFenetre = new ChoixMode(this);
				fenetre.setVisible(false);
				nouvelleFenetre.setVisible(true);
				setFenetre(nouvelleFenetre);
			}

			// Bouton Reccords
			if (e.getSource() == fenetreAccueil.getBt_Reccords()) {
				// On lance la page des reccords Reccords.java
				Reccords nouvelleFenetre = new Reccords(this);
				fenetre.setVisible(false);
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
				fenetre.setVisible(false);
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
					fenetre.setVisible(false);
					// On arrête le déroulement logique de l'application
					System.exit(0);
				}
			}
		}

		// On vérifie si la fenêtre que l'on contrôle est bien la fenêtre
		// des reccords
		if (fenetre instanceof Reccords) {
			Reccords fenetreReccords = (Reccords) fenetre;
			// On vérifie quel bouton a été utilisé
			// Bouton Retour
			if (e.getSource() == fenetreReccords.getBt_Retour()) {
				// On lance la fenêtre Accueil F_accueil.java
				F_accueil nouvelleFenetre = new F_accueil(this);
				fenetre.setVisible(false);
				nouvelleFenetre.setVisible(true);
				setFenetre(nouvelleFenetre);
			}
		}

		// On vérifie si la fenêtre que l'on contrôle est bien la fenêtre
		// de choix du mode
		if (fenetre instanceof ChoixMode) {
			ChoixMode fenetreChoixMode = (ChoixMode) fenetre;

			// On vérifie quel bouton a été utilisé
			// Bouton Story
			if (e.getSource() == fenetreChoixMode.getBt_Story()) {
				// On lance la fenêtre Story F_story.java
				F_story nouvelleFenetre = new F_story(this);
				fenetre.setVisible(false);
				nouvelleFenetre.setVisible(true);
				setFenetre(nouvelleFenetre);
			}

			// On vérifie quel bouton a été utilisé
			// Bouton Arcade
			if (e.getSource() == fenetreChoixMode.getBt_Arcade()) {
				// On lance la fenêtre Arcade F_arcade.java
				F_arcade nouvelleFenetre = new F_arcade(this);
				fenetre.setVisible(false);
				nouvelleFenetre.setVisible(true);
				setFenetre(nouvelleFenetre);
			}

			// On vérifie quel bouton a été utilisé
			// Bouton Custom
			if (e.getSource() == fenetreChoixMode.getBt_Custom()) {
				// On lance la fenêtre Custom F_custom.java
				F_custom nouvelleFenetre = new F_custom(this);
				fenetre.setVisible(false);
				nouvelleFenetre.setVisible(true);
				setFenetre(nouvelleFenetre);
			}

			// On vérifie quel bouton a été utilisé
			// Bouton Retour
			if (e.getSource() == fenetreChoixMode.getBt_Retour()) {
				// On lance la fenêtre Accueil F_accueil.java
				F_accueil nouvelleFenetre = new F_accueil(this);
				fenetre.setVisible(false);
				nouvelleFenetre.setVisible(true);
				setFenetre(nouvelleFenetre);
			}
		}

		if (fenetre instanceof F_abstractModeJeu) {
			F_abstractModeJeu fenetreAbastractModeJeu = (F_abstractModeJeu) fenetre;
			// On vérifie quel bouton a été utilisé
			// Bouton Jouer
			if (e.getSource() == fenetreAbastractModeJeu.getBt_Jouer()
					&& fenetreAbastractModeJeu.getBt_Jouer().isEnabled()) {
				// On lance la fenêtre Accueil F_accueil.java
				// Détecte les appuie sur les touches de clavier
				ToucheClavier clavier = new ToucheClavier(jeu);
				// On détecte les fins de partie et les pauses
				PartieEvent evenementPartie = new PartieEvent(this, jeu);
				FenetreJeu nouvelleFenetre = new FenetreJeu(this, clavier,
						evenementPartie);
				fenetre.setVisible(false);
				nouvelleFenetre.setVisible(true);
				setFenetre(nouvelleFenetre);
			}
			// On vérifie quel bouton a été utilisé
			// Bouton Retour
			if (e.getSource() == fenetreAbastractModeJeu.getBt_Retour()) {
				// On lance la fenêtre Accueil F_accueil.java
				ChoixMode nouvelleFenetre = new ChoixMode(this);
				fenetre.setVisible(false);
				nouvelleFenetre.setVisible(true);
				setFenetre(nouvelleFenetre);
			}
		}

		if (fenetre instanceof F_custom) {
			F_custom fenetreCustom = (F_custom) fenetre;
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
		if (fenetre instanceof FenetreJeu) {
			FenetreJeu fenetreJeu = (FenetreJeu) fenetre;
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
					jeu.reinitialiserPartie();
					fenetreJeu.getPartieDessinable().setJeuRainbowRobot(
							jeu.getPartieCourante());
					fenetreJeu.startChrono();
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
						jeu.reinitialiserPartie();
						F_accueil fenetreAccueil = new F_accueil(this);
						fenetre.setVisible(false);
						setFenetre(fenetreAccueil);
						fenetre.setVisible(true);
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
		this.fenetre = nouvelleFenetre;
	}

}
