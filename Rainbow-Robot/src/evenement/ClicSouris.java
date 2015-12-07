/*
 * ClicSouris.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package evenement;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import vue.ChoixLangue;
import vue.ChoixMode;
import vue.F_aPropos;
import vue.F_abstractModeJeu;
import vue.F_accueil;
import vue.F_arcade;
import vue.F_custom;
import vue.F_story;
import vue.FenetreJeu;
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
     * On initialise le constructeur par défaut.
     */
    public ClicSouris() {
    }

    /**
     * Détection des clics sur une fenêtre. On peut traiter qu'une seule fenêtre
     * car il n'y aura jamais deux fenêtres affichées en même temps.
     */
    private JFrame fenetre;

    /*
     * Quand on presse la souris sur un composant (mais sans le relacher la
     * pression) (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    /*
     * Quand la souris quitte un composant. (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    /*
     * Quand la souris sort d'un composant (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

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
        if (fenetre instanceof FenetreJeu) {
            FenetreJeu fenetreJeu = (FenetreJeu) fenetre;
            // On vérifie quel bouton a été utilisé
            // Bouton Retour
            if (e.getSource() == fenetreJeu.getBt_Pause()) {
                Object[] optionsBoutons = { new JButton("Reprendre"),
                        new JButton("Recommncer"), new JButton("Quitter") };
                // 3 boutons donc optionType = YES_NO_CANCEL_OPTION
                int r = JOptionPane.showOptionDialog(null, "Choix", "Pause",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, optionsBoutons,
                        optionsBoutons[0]);
                // TODO faire la gestion des boutons
            }
        }
        if (fenetre instanceof F_abstractModeJeu) {
            F_abstractModeJeu fenetreAbastractModeJeu = (F_abstractModeJeu) fenetre;
            // On vérifie quel bouton a été utilisé
            // Bouton Jouer

            if (e.getSource() == fenetreAbastractModeJeu.getBt_Jouer()) {
                // On lance la fenêtre Accueil F_accueil.java
                FenetreJeu nouvelleFenetre = new FenetreJeu(this);
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

        // On vérifie si la fenêtre que l'on contrôle est bien la fenêtre
        // de choix du mode
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
    }

    /*
     * Quand la souris est relaché sur un composant (non-Javadoc)
     * 
     * @see
     * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * @param nouvelleFenetre
     *            la nouvelle fenêtre à controller
     */
    public void setFenetre(JFrame nouvelleFenetre) {
        this.fenetre = nouvelleFenetre;
    }

}
