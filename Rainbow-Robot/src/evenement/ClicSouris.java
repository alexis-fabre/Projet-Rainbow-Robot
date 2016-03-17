/*
 * ClicSouris.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package evenement;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import metier.JeuRainbow;
import metier.OperationsFichier;
import metier.Partie;
import vue.ChoixLangue;
import vue.F_aPropos;
import vue.F_abstractModeJeu;
import vue.F_accueil;
import vue.F_arcade;
import vue.F_choixMode;
import vue.F_commandes;
import vue.F_choixNiveau;
import vue.F_custom;
import vue.F_jeuRainbow;
import vue.F_records;
import vue.F_story;

/**
 * Controleur lors d'un clic de la souris. Utilisé surtout pour naviguer entre
 * les fenêtres. Elle contrôle aussi la fin d'une partie grâce au Pattern
 * Observer/Observable.
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
	private Window vue;

	/**
	 * On initialise le constructeur par défaut.
	 */
	@Deprecated
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
	 * @return le metier
	 */
	public JeuRainbow getMetier() {
		return metier;
	}

	/**
	 * Actualise la nouvelle partie a observé
	 */
	public void setObserver() {
		this.metier.getPartieCourante().addObserver(this);
	}

	/**
	 * @param nouvelleFenetre
	 *            la nouvelle fenêtre à controller
	 */
	public void setFenetre(Window nouvelleFenetre) {
		this.vue = nouvelleFenetre;
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
				F_records nouvelleFenetre = new F_records(this);
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
			// Bouton Commandes
			if (e.getSource() == fenetreAccueil.getBt_Commande()) {
				// On lance la page de modificaion des commandes
				F_commandes nouvelleFenetre = new F_commandes(this,
						(JFrame) vue);
				nouvelleFenetre.setVisible(true);
				setFenetre(nouvelleFenetre);
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

		if (vue instanceof F_aPropos) {
			F_aPropos fenetreAPropos = (F_aPropos) vue;
			if (e.getSource() == fenetreAPropos.getBt_retour()) {
				// On lance la fenêtre Accueil F_accueil.java
				F_accueil nouvelleFenetre = new F_accueil(this);
				vue.setVisible(false);
				nouvelleFenetre.setVisible(true);
				setFenetre(nouvelleFenetre);
			}
		}

		// On vérifie si la fenêtre que l'on contrôle est bien la fenêtre
		// des reccords
		if (vue instanceof F_records) {
			F_records fenetreReccords = (F_records) vue;
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

		if (vue instanceof F_commandes) {
			F_commandes fenetreCommande = (F_commandes) vue;

			// on modifie le texte des touches par rapport au mode
			// absolu
			fenetreCommande.getBr_absolu().addActionListener(
					new ActionListener() {

						public void actionPerformed(ActionEvent f) {
							System.out
									.println("Selected Button = passé dans abs");
							ToucheClavier.setModeAbsolu(true);
							fenetreCommande.setTextBt();

						}
					});
			// on modifie le texte des touches par rapport au mode
			// relatif
			fenetreCommande.getBr_relatif().addActionListener(
					new ActionListener() {

						public void actionPerformed(ActionEvent g) {
							System.out
									.println("Selected Button = passé dans rel");
							ToucheClavier.setModeAbsolu(false);
							fenetreCommande.setTextBt();
						}
					});

			// bouton avancer
			if (e.getSource() == fenetreCommande.getBt_avancer()) {
				F_commandes
						.afficherPressKey(0, fenetreCommande.getBt_avancer());
			}
			// bouton gauche
			if (e.getSource() == fenetreCommande.getBt_gauche()) {
				F_commandes.afficherPressKey(1, fenetreCommande.getBt_gauche());
			}
			// bouton droit
			if (e.getSource() == fenetreCommande.getBt_droite()) {
				F_commandes.afficherPressKey(2, fenetreCommande.getBt_droite());
			}
			// bouton reculer
			if (e.getSource() == fenetreCommande.getBt_reculer()) {
				F_commandes
						.afficherPressKey(3, fenetreCommande.getBt_reculer());
			}
			// bouton fusion
			if (e.getSource() == fenetreCommande.getBt_fusion()) {
				F_commandes.afficherPressKey(4, fenetreCommande.getBt_fusion());
			}
			// bouton attraper
			if (e.getSource() == fenetreCommande.getBt_attraper()) {
				F_commandes.afficherPressKey(5,
						fenetreCommande.getBt_attraper());
			}
			// bouton reset
			if (e.getSource() == fenetreCommande.getBt_reset()) {
				if (ToucheClavier.isModeAbsolu) {
					for (int i = 0; i < ToucheClavier.NB_TOUCHES; i++) {
						ToucheClavier.TOUCHES_ABSOLU[i] = ToucheClavier.TOUCHES_ABSOLU_DEFAUT[i];
					}
				} else {
					for (int i = 0; i < ToucheClavier.NB_TOUCHES; i++) {
						ToucheClavier.TOUCHES_RELATIF[i] = ToucheClavier.TOUCHES_RELATIF_DEFAUT[i];
					}
				}
				fenetreCommande.setTextBt();
			}
			// bouton sauvegarder
			if (e.getSource() == fenetreCommande.getBt_save()) {
				setFenetre(fenetreCommande.getOwner());
				fenetreCommande.dispose();
			}
			// bouton annuler
			if (e.getSource() == fenetreCommande.getBt_annuler()) {
				// tableau temporaire ayant stocké les touche à
				// l'ouverture de la fenêtre
				int[] leTmp = new int[ToucheClavier.NB_TOUCHES];
				leTmp = F_commandes.getTemp();
				if (ToucheClavier.isModeAbsolu) {
					for (int i = 0; i < ToucheClavier.NB_TOUCHES; i++) {
						ToucheClavier.TOUCHES_ABSOLU[i] = leTmp[i];
					}
				} else {
					for (int i = 0; i < ToucheClavier.NB_TOUCHES; i++) {
						ToucheClavier.TOUCHES_RELATIF[i] = leTmp[i];
					}
				}
				fenetreCommande.setTextBt();
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
			// Bouton Retour
			if (e.getSource() == fenetreAbastractModeJeu.getBt_Retour()) {
				// On lance la fenêtre Accueil F_accueil.java
				F_choixMode nouvelleFenetre = new F_choixMode(this);
				vue.setVisible(false);
				nouvelleFenetre.setVisible(true);
				setFenetre(nouvelleFenetre);
			}
		}

		if (vue instanceof F_story) {
			F_abstractModeJeu fenetreAbastractModeJeu = (F_abstractModeJeu) vue;
			// Bouton Jouer
			if (e.getSource() == fenetreAbastractModeJeu.getBt_Jouer()
					&& fenetreAbastractModeJeu.getBt_Jouer().isEnabled()) {
				// On lance la fenêtre Accueil F_accueil.java
				// Détecte les appuie sur les touches de clavier

				F_choixNiveau nouvelleFenetre = new F_choixNiveau(this);
				vue.setVisible(false);
				nouvelleFenetre.setVisible(true);
				setFenetre(nouvelleFenetre);
			}
		}
		if (vue instanceof F_choixNiveau) {
			F_choixNiveau fenetreChoixNiveau = (F_choixNiveau) vue;
			if (e.getSource() == fenetreChoixNiveau.getBt_Jouer()) {
				// On regarde quel niveau a choisi l'utilisateur
				metier.setNiveau(fenetreChoixNiveau.getNiveauChoisi());
				ToucheClavier clavier = new ToucheClavier(metier);
				// On détecte les fins de partie et les pauses
				F_jeuRainbow nouvelleFenetre = new F_jeuRainbow(this, clavier);
				clavier.setFenetre(nouvelleFenetre);
				setObserver();
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
			if (e.getSource() == fenetreCustom.getBt_Jouer()) {
				// Objet qui permet de naviguer dans les dossiers personnels
				String fichier;
				fichier = fenetreCustom.getTf_cheminFichier().getText();
				Partie partie = OperationsFichier
						.recupFichier(new File(fichier));
				metier = new JeuRainbow();
				metier.addPartie(partie);
				ToucheClavier clavier = new ToucheClavier(metier);
				F_jeuRainbow nouvelleFenetre = new F_jeuRainbow(this, clavier);
				clavier.setFenetre(nouvelleFenetre);
				setObserver();
				vue.setVisible(false);
				nouvelleFenetre.setVisible(true);
				setFenetre(nouvelleFenetre);
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
					fenetreJeu.setPartieCourante(metier.getPartieCourante());
					setObserver();
					fenetreJeu.restartChrono();
					ToucheClavier.restartPartie();
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Partie) {
			F_jeuRainbow fenetre = (F_jeuRainbow) vue;

			fenetre.stopChrono();

			String[] traductionFinPartie = ChoixLangue.getChoixLangue()
					.getFinPartie();
			String[] traductionBouton = Arrays.copyOfRange(traductionFinPartie,
					2, traductionFinPartie.length);
			// Si le joueur fait un score dans le top 10 du niveau
			if (F_records.estRecord(fenetre.getScore()) != -1) {
				int classement = F_records.estRecord(fenetre.getScore());

				String pseudo = JOptionPane.showInputDialog(null,
						"Vous avez fait le " + classement + "ième score\n"
								+ "Veuillez entrer votre pseudo",
						"Nouveau record !", JOptionPane.QUESTION_MESSAGE);
				try {
					File temp = new File(".Ressource/tempo.txt");
					temp.createNewFile();
					PrintWriter nouvFichier = new PrintWriter(temp);
					FileReader temp2 = new FileReader(
							"./Ressource/highcrore1.txt");
					BufferedReader fichier = new BufferedReader(temp2);
					File aSupprimer = new File("./Ressource/highcrore1.txt");
					String ligne;
					int compteur = 1;
					// On lit le fichier jusqu'au classement du joueur ou la fin
					// du fichier
					while ((ligne = fichier.readLine()) != null
							&& compteur <= 10) {
						// Si on arrive au classement du joueur
						if (compteur == classement) {
							// On écrit la ligne
							nouvFichier.println(pseudo + "#"
									+ fenetre.getScore());
						}
						// else
						// On recopie la ligne;
						nouvFichier.println(ligne);
						compteur++;
					}
					aSupprimer.delete();
					temp.renameTo(new File("./Ressource/highcrore1.txt"));
				} catch (IOException erreur) {
					System.out.println("Fichier records non trouvé");
				}
			}
			int retour = JOptionPane.showOptionDialog(null,
					traductionFinPartie[0] + " " + fenetre.getScore(),
					traductionFinPartie[1], JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, traductionBouton,
					traductionBouton[0]);

			// On reinitialise les paramètres
			metier.reinitialiserPartie();
			fenetre.restartChrono();
			ToucheClavier.restartPartie();
			switch (retour) {
			case 0: // Recommencer
				fenetre.setPartieCourante(metier.getPartieCourante());
				setObserver();
				break;
			case 1: // Partie suivante
				metier.setNiveauSuivant();
				fenetre.setPartieCourante(metier.getPartieCourante());
				setObserver();
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
