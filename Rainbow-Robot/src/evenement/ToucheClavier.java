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
     * Tableau contenant les touches de contrôles par défaut du mode relatif
     * Respectivement : avancer, rotation vers la gauche, rotation vers la
     * droite, reculer, fusionner, attraper/relâcher une caisse
     */
    public static final int[] TOUCHES_RELATIF_DEFAUT = { KeyEvent.VK_UP,
            KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN,
            KeyEvent.VK_CONTROL, KeyEvent.VK_SPACE };

    /**
     * Tableau contenant les touches de contrôles saisies par l'utilisateur pour
     * le mode relatif. Respectivement : avancer, rotation vers la gauche,
     * rotation vers la droite, reculer, fusionner, attraper/relâcher une caisse
     */
    public static int[] TOUCHES_RELATIF = new int[NB_TOUCHES];

    /**
     * Tableau contenant les touches de contrôles par défaut du mode absolu
     * Respectivement : aller en haut, aller à gauche, aller à droite aller en
     * bas, fusionner, attraper/relâcher une caisse
     */
    public static final int[] TOUCHES_ABSOLU_DEFAUT = { KeyEvent.VK_UP,
            KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN,
            KeyEvent.VK_CONTROL, KeyEvent.VK_SPACE };

    /**
     * Tableau contenant les touches de contrôles saisies par l'utilisateur pour
     * le mode absolu. Respectivement : aller en haut, aller à gauche, aller à
     * droite, aller en bas, fusionner, attraper/relâcher une caisse
     */
    public static int[] TOUCHES_ABSOLU = new int[NB_TOUCHES];

    /**
     * Boolean permettant de savoir dans quelle configuration des touches joue
     * l'utilisateur. Par défaut false (= mode absolu). Si true, on est dans le
     * mode relatif.
     */
    public static boolean isModeRelatif = false;

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
    private static Thread threadVortexJoueur, threadVortexIA;

    static {
        for (int i = 0; i < NB_TOUCHES; i++) {
            TOUCHES_RELATIF[i] = TOUCHES_RELATIF_DEFAUT[i];
            TOUCHES_ABSOLU[i] = TOUCHES_ABSOLU_DEFAUT[i];
        }

    }

    /**
     * On initialise le constructeur avec la partie métier du jeu.
     *
     * @param jeu
     *            Permet de récupérer les différentes parties utilisées (celle
     *            pour le joueur et celle pour l'IA)
     * @param isIA
     *            permet de savoir si le jeu dispose d'une IA ou non
     */
    public ToucheClavier(JeuRainbow jeu, boolean isIA) {
        this.metier = jeu;
        premiereAction = false;
        threadVortexJoueur = new Thread(metier.getPartieCouranteJoueur()
                .getVortex());
        if (isIA) {
            threadVortexIA = new Thread(metier.getPartieCouranteIA()
                    .getVortex());
        }
    }

    /**
     * @return true si la partie a commencé, false sinon
     */
    public static boolean isPartieCommence() {
        return premiereAction;
    }

    /**
     * @return la partie métier du jeu
     */
    public JeuRainbow getJeuRainbow() {
        return metier;
    }

    /**
     * @param nouvelle
     *            la nouvelle valeur (changement de mode)
     */
    public static void setModeRelatif(boolean nouvelle) {
        isModeRelatif = nouvelle;
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
     * détecter que la partie s'est lancée.
     */
    public void startPartie() {
        if (!premiereAction) {
            // On démarre le timer
            vue.startChrono();
            // On lance le vortex du joueur et de l'IA
            threadVortexJoueur = new Thread(metier.getPartieCouranteJoueur()
                    .getVortex());
            threadVortexJoueur.start();
            if (threadVortexIA != null) {
                threadVortexIA = new Thread(metier.getPartieCouranteIA()
                        .getVortex());
                threadVortexIA.start();
            }
            premiereAction = true;
        }
    }

    /** Restart les paramètres d'une partie */
    public static void restartPartie() {
        // On restart la 1ère action
        premiereAction = false;
        // On arrête les vortex
        threadVortexJoueur.interrupt();
        if (threadVortexIA != null) {
            threadVortexIA.interrupt();
        }
    }

    /*
     * Non utilisé (non-Javadoc)
     * 
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // On vérifie que le robot n'est pas déjà en train de faire une action
        if (!metier.getPartieCouranteJoueur().getRobot().estOccupe()) {
            // mode relatif
            if (isModeRelatif) {
                // touche pour avancer
                if (e.getKeyCode() == TOUCHES_RELATIF[0]) {
                    startPartie();
                    metier.getPartieCouranteJoueur().getRobot().avancer();
                } else if (e.getKeyCode() == TOUCHES_RELATIF[1]) {
                    // touche pour rotation gauche
                    startPartie();
                    metier.getPartieCouranteJoueur().getRobot()
                            .pivoter(Robot.PIVOTER_GAUCHE);
                } else if (e.getKeyCode() == TOUCHES_RELATIF[2]) {
                    // touche pour rotation droite
                    startPartie();
                    metier.getPartieCouranteJoueur().getRobot()
                            .pivoter(Robot.PIVOTER_DROITE);
                } else if (e.getKeyCode() == TOUCHES_RELATIF[3]) {
                    // touche pour reculer
                    startPartie();
                    metier.getPartieCouranteJoueur().getRobot().reculer();
                } else if (e.getKeyCode() == TOUCHES_RELATIF[4]) {
                    // touche pour fusionner
                    startPartie();
                    metier.getPartieCouranteJoueur().getRobot().fusionner();
                } else if (e.getKeyCode() == TOUCHES_RELATIF[5]) {
                    // touche pour attraper
                    startPartie();
                    metier.getPartieCouranteJoueur().getRobot().charger();
                }
            } else { // mode absolu
                if (e.getKeyCode() == TOUCHES_ABSOLU[0]) {
                    // touche aller en haut
                    switch (metier.getPartieCouranteJoueur().getRobot()
                            .getOrientation()) {
                    case Robot.ORIENTATION_HAUT:
                        startPartie();
                        metier.getPartieCouranteJoueur().getRobot().avancer();
                        break;
                    case Robot.ORIENTATION_GAUCHE:
                        startPartie();
                        metier.getPartieCouranteJoueur().getRobot()
                                .pivoter(Robot.PIVOTER_DROITE);
                        break;
                    case Robot.ORIENTATION_DROITE:
                        startPartie();
                        metier.getPartieCouranteJoueur().getRobot()
                                .pivoter(Robot.PIVOTER_GAUCHE);
                        break;
                    case Robot.ORIENTATION_BAS:
                        startPartie();
                        metier.getPartieCouranteJoueur().getRobot().reculer();
                        break;
                    }
                } else if (e.getKeyCode() == TOUCHES_ABSOLU[1]) {
                    // touche aller à gauche
                    switch (metier.getPartieCouranteJoueur().getRobot()
                            .getOrientation()) {
                    case Robot.ORIENTATION_HAUT:
                        startPartie();
                        metier.getPartieCouranteJoueur().getRobot()
                                .pivoter(Robot.PIVOTER_GAUCHE);
                        break;
                    case Robot.ORIENTATION_GAUCHE:
                        startPartie();
                        metier.getPartieCouranteJoueur().getRobot().avancer();
                        break;
                    case Robot.ORIENTATION_DROITE:
                        startPartie();
                        metier.getPartieCouranteJoueur().getRobot().reculer();
                        break;
                    case Robot.ORIENTATION_BAS:
                        startPartie();
                        metier.getPartieCouranteJoueur().getRobot()
                                .pivoter(Robot.PIVOTER_DROITE);
                        break;
                    }
                } else if (e.getKeyCode() == TOUCHES_ABSOLU[2]) {
                    // touche aller à droite
                    switch (metier.getPartieCouranteJoueur().getRobot()
                            .getOrientation()) {
                    case Robot.ORIENTATION_HAUT:
                        startPartie();
                        metier.getPartieCouranteJoueur().getRobot()
                                .pivoter(Robot.PIVOTER_DROITE);
                        break;
                    case Robot.ORIENTATION_GAUCHE:
                        startPartie();
                        metier.getPartieCouranteJoueur().getRobot().reculer();
                        break;
                    case Robot.ORIENTATION_DROITE:
                        startPartie();
                        metier.getPartieCouranteJoueur().getRobot().avancer();
                        break;
                    case Robot.ORIENTATION_BAS:
                        metier.getPartieCouranteJoueur().getRobot()
                                .pivoter(Robot.PIVOTER_GAUCHE);
                        break;
                    }
                } else if (e.getKeyCode() == TOUCHES_ABSOLU[3]) {
                    // touche aller en bas
                    switch (metier.getPartieCouranteJoueur().getRobot()
                            .getOrientation()) {
                    case Robot.ORIENTATION_HAUT:
                        startPartie();
                        metier.getPartieCouranteJoueur().getRobot().reculer();
                        break;
                    case Robot.ORIENTATION_GAUCHE:
                        startPartie();
                        metier.getPartieCouranteJoueur().getRobot()
                                .pivoter(Robot.PIVOTER_GAUCHE);
                        break;
                    case Robot.ORIENTATION_DROITE:
                        startPartie();
                        metier.getPartieCouranteJoueur().getRobot()
                                .pivoter(Robot.PIVOTER_DROITE);
                        break;
                    case Robot.ORIENTATION_BAS:
                        startPartie();
                        metier.getPartieCouranteJoueur().getRobot().avancer();
                        break;
                    }
                } else if (e.getKeyCode() == TOUCHES_ABSOLU[4]) {
                    // touche pour fusionner
                    startPartie();
                    metier.getPartieCouranteJoueur().getRobot().fusionner();
                } else if (e.getKeyCode() == TOUCHES_ABSOLU[5]) {
                    // touche pour attraper
                    startPartie();
                    metier.getPartieCouranteJoueur().getRobot().charger();
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
}
