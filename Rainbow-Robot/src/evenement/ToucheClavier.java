/*
 * ToucheClavier.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package evenement;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import metier.JeuRainbow;
import metier.Robot;
import vue.F_jeuRainbow;

/**
 * Contrôle les touches du clavier. Elle permet de contrôler le robot lors d'une
 * partie du jeu.
 * 
 * @author Rainbow Robot
 *
 */
public class ToucheClavier implements KeyListener {

    /**
     * Nombre de touches clavier pour jouer
     */
    public final static int NB_TOUCHES = 6;

    /**
     * Tableau contenant les touches de contrôles par défaut du mode absolu
     * Respectivement : avancer, rotation vers la gauche, rotation vers la
     * droite, reculer, fusionner, attraper/relâcher une caisse
     */
    public static final int[] TOUCHES_ABSOLU_DEFAUT = { KeyEvent.VK_UP,
            KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN,
            KeyEvent.VK_CONTROL, KeyEvent.VK_SPACE };

    /**
     * Tableau contenant les touches de contrôles saisies par l'utilisateur pour
     * le mode absolu. Respectivement : avancer, rotation vers la gauche,
     * rotation vers la droite, reculer, fusionner, attraper/relâcher une caisse
     */
    public static int[] TOUCHES_ABSOLU = new int[NB_TOUCHES];

    /**
     * Tableau contenant les touches de contrôles par défaut du mode relatif
     * Respectivement : aller en haut, aller à gauche, aller à droite aller en
     * bas, fusionner, attraper/relâcher une caisse
     */
    public static final int[] TOUCHES_RELATIF_DEFAUT = { KeyEvent.VK_UP,
            KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN,
            KeyEvent.VK_CONTROL, KeyEvent.VK_SPACE };

    /**
     * Tableau contenant les touches de contrôles saisies par l'utilisateur pour
     * le mode relatif. Respectivement : aller en haut, aller à gauche, aller à
     * droite aller en bas, fusionner, attraper/relâcher une caisse
     */
    public static int[] TOUCHES_RELATIF = new int[NB_TOUCHES];

    /**
     * Boolean permettant de savoir dans quelle configuration des touches joue
     * l'utilisateur
     */
    public static boolean isModeAbsolu = false;

    /** On garde une référence du jeu pour pouvoir changer de niveaux */
    private JeuRainbow metier;

    /** Référence de la vue contrôlée. Cela permet de ouvoir démarrer le timer */
    private F_jeuRainbow vue;

    /**
     * Permet de détecter la 1ère action du joueur et de lancer le timer et le
     * vortex
     */
    private static boolean premiereAction;

    /** Gestion du thread du vortex */
    private static Thread threadVortex;

    static {
        for (int i = 0; i < NB_TOUCHES; i++) {
            TOUCHES_ABSOLU[i] = TOUCHES_ABSOLU_DEFAUT[i];
            TOUCHES_RELATIF[i] = TOUCHES_RELATIF_DEFAUT[i];
        }

    }

    /**
     * On initialise le constructeur avec la partie métier du jeu.
     * 
     * @param jeu
     *            représentation du jeu Rainbow Robot (partie métier). Il
     *            contient notamment les différents niveaux.
     */
    public ToucheClavier(JeuRainbow jeu) {
        this.metier = jeu;
        premiereAction = false;
    }

    /**
     * @return la partie métier du jeu
     */
    public JeuRainbow getJeuRainbow() {
        return metier;
    }

    /**
     * @param nouvelleVue
     *            nouvelle vue que l'on contrôle
     */
    public void setFenetre(F_jeuRainbow nouvelleVue) {
        this.vue = nouvelleVue;
    }

    /**
     * Détecte la 1ère action du joueur dans la partie et lance le timer, le
     * thread du vortex. Il abonne aussi la classe à la partie courante pour
     * détecter que la partie c'est lancé.
     */
    private void startPartie() {
        if (!premiereAction) {
            // On démarre le timer
            vue.startChrono();
            // On lance le vortex
            threadVortex = new Thread(metier.getPartieCourante().getVortex());
            threadVortex.start();
            premiereAction = true;
        }
    }

    /** Restart les paramètres d'une partie */
    public static void restartPartie() {
        // On restart la 1ère action
        premiereAction = false;
        // On arrête le vortex
        threadVortex.interrupt();
    }

    /*
     * Non utilisé (non-Javadoc)
     * 
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // On vérifie que le robot n'est pas déjà en train de faire une action
        if (!metier.getPartieCourante().getRobot().estOccupe()) {
            // mode absolu
            if (isModeAbsolu) {
                // touche pour avancer
                if (e.getKeyCode() == TOUCHES_ABSOLU[0]) {
                    startPartie();
                    metier.getPartieCourante().getRobot().avancer();
                } else if (e.getKeyCode() == TOUCHES_ABSOLU[1]) {
                    // touche pour rotation gauche
                    startPartie();
                    metier.getPartieCourante().getRobot()
                            .pivoter(Robot.PIVOTER_GAUCHE);
                } else if (e.getKeyCode() == TOUCHES_ABSOLU[2]) {
                    // touche pour rotation droite
                    startPartie();
                    metier.getPartieCourante().getRobot()
                            .pivoter(Robot.PIVOTER_DROITE);
                } else if (e.getKeyCode() == TOUCHES_ABSOLU[3]) {
                    // touche pour reculer
                    startPartie();
                    metier.getPartieCourante().getRobot().reculer();
                } else if (e.getKeyCode() == TOUCHES_ABSOLU[4]) {
                    // touche pour fusionner
                    startPartie();
                } else if (e.getKeyCode() == TOUCHES_ABSOLU[5]) {
                    // touche pour attraper
                    startPartie();
                    metier.getPartieCourante().getRobot().charger();
                }
            } else { // mode relatif

                if (e.getKeyCode() == TOUCHES_RELATIF[0]) {
                    // touche aller en haut
                    startPartie();
                    switch (metier.getPartieCourante().getRobot()
                            .getOrientation()) {
                    case Robot.ORIENTATION_HAUT:
                        metier.getPartieCourante().getRobot().avancer();
                    case Robot.ORIENTATION_GAUCHE:
                        metier.getPartieCourante().getRobot()
                                .pivoter(Robot.PIVOTER_DROITE);
                        metier.getPartieCourante().getRobot().avancer();
                    case Robot.ORIENTATION_DROITE:
                        metier.getPartieCourante().getRobot()
                                .pivoter(Robot.PIVOTER_GAUCHE);
                        metier.getPartieCourante().getRobot().avancer();
                    case Robot.ORIENTATION_BAS:
                        metier.getPartieCourante().getRobot().reculer();
                    }
                } else if (e.getKeyCode() == TOUCHES_RELATIF[1]) {
                    // touche aller à gauche
                    startPartie();
                    switch (metier.getPartieCourante().getRobot()
                            .getOrientation()) {
                    case Robot.ORIENTATION_HAUT:
                        metier.getPartieCourante().getRobot()
                                .pivoter(Robot.PIVOTER_GAUCHE);
                        metier.getPartieCourante().getRobot().avancer();
                    case Robot.ORIENTATION_GAUCHE:
                        metier.getPartieCourante().getRobot().avancer();
                    case Robot.ORIENTATION_DROITE:
                        metier.getPartieCourante().getRobot().reculer();
                    case Robot.ORIENTATION_BAS:
                        metier.getPartieCourante().getRobot()
                                .pivoter(Robot.PIVOTER_DROITE);
                        metier.getPartieCourante().getRobot().avancer();
                    }
                } else if (e.getKeyCode() == TOUCHES_RELATIF[2]) {
                    // touche aller à droite
                    startPartie();
                    switch (metier.getPartieCourante().getRobot()
                            .getOrientation()) {
                    case Robot.ORIENTATION_HAUT:
                        metier.getPartieCourante().getRobot()
                                .pivoter(Robot.PIVOTER_DROITE);
                        metier.getPartieCourante().getRobot().avancer();
                    case Robot.ORIENTATION_GAUCHE:
                        metier.getPartieCourante().getRobot().reculer();
                    case Robot.ORIENTATION_DROITE:
                        metier.getPartieCourante().getRobot().avancer();
                    case Robot.ORIENTATION_BAS:
                        metier.getPartieCourante().getRobot()
                                .pivoter(Robot.PIVOTER_GAUCHE);
                        metier.getPartieCourante().getRobot().avancer();
                    }
                } else if (e.getKeyCode() == TOUCHES_RELATIF[3]) {
                    // touche aller en bas
                    startPartie();
                    switch (metier.getPartieCourante().getRobot()
                            .getOrientation()) {
                    case Robot.ORIENTATION_HAUT:
                        metier.getPartieCourante().getRobot().reculer();
                    case Robot.ORIENTATION_GAUCHE:
                        metier.getPartieCourante().getRobot()
                                .pivoter(Robot.PIVOTER_GAUCHE);
                        metier.getPartieCourante().getRobot().avancer();
                    case Robot.ORIENTATION_DROITE:
                        metier.getPartieCourante().getRobot()
                                .pivoter(Robot.PIVOTER_DROITE);
                        metier.getPartieCourante().getRobot().avancer();
                    case Robot.ORIENTATION_BAS:
                        metier.getPartieCourante().getRobot().avancer();
                    }
                } else if (e.getKeyCode() == TOUCHES_RELATIF[4]) {
                    // touche pour fusionner
                    startPartie();
                } else if (e.getKeyCode() == TOUCHES_RELATIF[5]) {
                    // touche pour attraper
                    startPartie();
                    metier.getPartieCourante().getRobot().charger();
                }
            }
        }
    }

    /*
     * On détecte lors du "relachement" d'une touche du clavier (non-Javadoc)
     * 
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    /*
     * Non utilisé (non-Javadoc)
     * 
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * @param nouvelle
     *            la nouvelle valeur (changement de mode)
     */
    public static void setModeAbsolu(boolean nouvelle) {
        isModeAbsolu = nouvelle;
    }

}
