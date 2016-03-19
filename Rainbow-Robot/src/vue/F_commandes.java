/* F_commandes.java                21 févr. 2016
 * IUT RODEZ INFO2 2015-2016 groupe 2
 */
package vue;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import evenement.ClicSouris;
import evenement.ToucheClavier;

/**
 * Fenêtre permettant de modifier les commandes de jeu Pour le mode relatif et
 * absolu avec possibilité de remettre les commandes par défaut
 * 
 * @author RainbowRobot
 * @version 1.0
 */
public class F_commandes extends JDialog implements ChangementLangue {

    /** Généré automatiquement par Eclipse */
    private static final long serialVersionUID = 1L;

    /**
     * Tableau temporaire contenant les touches au moment de l'ouverture de la
     * fenetre des commandes pour le mode absolu
     */
    private static int[] tempAbs = new int[ToucheClavier.NB_TOUCHES];

    /**
     * Tableau temporaire contenant les touches au moment de l'ouverture de la
     * fenetre des commandes pour le mode relatif
     */
    private static int[] tempRel = new int[ToucheClavier.NB_TOUCHES];

    /** Label changement de type */
    private JLabel la_type;

    /** Groupe contenant les boutons radio */
    private ButtonGroup rbGroup;

    /** Bouton radio pour mode relatif */
    private JRadioButton br_relatif;

    /** Bouton radio pour mode absolu */
    private JRadioButton br_absolu;

    /** Référence des traductions effectuées dans ChoixLangue.java */
    private ChoixLangue traducteur = ChoixLangue.getChoixLangue();

    /** Bouton qui permet de modifier la commande pour avancer */
    private JButton bt_avancer;

    /** Bouton qui permet de modifier la commande pour reculer */
    private JButton bt_reculer;

    /** Bouton qui permet de modifier la commande pour tourner à droite */
    private JButton bt_droite;

    /** Bouton qui permet de modifier la commande pour tourner à gauche */
    private JButton bt_gauche;

    /**
     * Bouton qui permet de modifier la commande pour attraper/lacher une caisse
     */
    private JButton bt_attraper;

    /** Bouton qui permet de modifier la commande pour fusionner 2 caisses */
    private JButton bt_fusion;

    /** Bouton qui permet de réinitialiser aux commandes par défaut */
    private JButton bt_reset;

    /**
     * Bouton qui permet d'annuler les modifications en cours (retour fenêtre
     * précédente)
     */
    private JButton bt_annuler;

    /** Bouton qui permet de sauvegarder les commandes "modifiées" */
    private JButton bt_save;

    /** Label avancer */
    private JLabel la_avancer;

    /** Label reculer */
    private JLabel la_reculer;

    /** Label droite */
    private JLabel la_droite;

    /** Label gauche */
    private JLabel la_gauche;

    /** Label attraper/lâcher */
    private JLabel la_attraper;

    /** Label fusion */
    private JLabel la_fusion;

    /**
     * Constructeur de la fenêtre de modification des commandes. Modale, non
     * redimensionnable.
     * 
     * @param gestion
     *            le contrôleur qui va controler cette vue = cible
     *            evenementielle
     * @param owner
     * 
     */
    public F_commandes(ClicSouris gestion, JFrame owner) {
        super(owner);

        super.setSize(UtilitaireFenetre.DIM_FENETRE_COMMANDES);
        // On rend la fenêtre non redimenssionable
        super.setResizable(false);
        super.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        // On centre à l'écran
        UtilitaireFenetre.centrerFenetre(this);
        // panneau principal
        Container contentCommandes = new JPanel();
        contentCommandes
                .setPreferredSize(UtilitaireFenetre.DIM_FENETRE_COMMANDES);
        contentCommandes.setLayout(new BorderLayout());

        /*
         * PANNEAU DU HAUT
         */
        Container contentHaut = new JPanel();
        contentHaut.setLayout(new GridBagLayout());
        GridBagConstraints gbcHaut = new GridBagConstraints();
        // ajout à un groupe pour séléction d'un seul bouton radio
        rbGroup = new ButtonGroup();

        rbGroup.add(getBr_relatif());
        rbGroup.add(getBr_absolu());
        // axe y ne change pas (1 seule ligne)
        gbcHaut.gridy = 0;
        gbcHaut.gridx = 0;
        // 50% de l'espace supplémentaire pour le label
        gbcHaut.weightx = 0.5;
        contentHaut.add(getLa_type(), gbcHaut);
        gbcHaut.gridx = 1;
        // 25% pour les boutons radio
        gbcHaut.weightx = 0.25;
        contentHaut.add(getBr_relatif(), gbcHaut);
        gbcHaut.gridx = 2;
        gbcHaut.weightx = 0.25;
        contentHaut.add(getBr_absolu(), gbcHaut);

        /*
         * PANNEAU DU MILIEU contenant les actions associées aux boutons de
         * modification
         */
        Container contentActions = new JPanel();
        // 6 lignes pour chacune des actions et 2 colonnes (label + bouton)
        contentActions.setLayout(new GridBagLayout());
        GridBagConstraints gbcCentre = new GridBagConstraints();

        // label non collé au bouton
        gbcCentre.weightx = 1;
        // espaces entre chaque composants
        gbcCentre.insets = new Insets(5, 5, 5, 5);

        // ajout des composants dans la grille
        gbcCentre.gridx = 0;
        gbcCentre.gridy = 0;
        contentActions.add(getLa_avancer(), gbcCentre);
        gbcCentre.gridx = 1;
        gbcCentre.gridy = 0;
        contentActions.add(getBt_avancer(), gbcCentre);
        gbcCentre.gridx = 0;
        gbcCentre.gridy = 1;
        contentActions.add(getLa_reculer(), gbcCentre);
        gbcCentre.gridx = 1;
        gbcCentre.gridy = 1;
        contentActions.add(getBt_reculer(), gbcCentre);
        gbcCentre.gridx = 0;
        gbcCentre.gridy = 2;
        contentActions.add(getLa_droite(), gbcCentre);
        gbcCentre.gridx = 1;
        gbcCentre.gridy = 2;
        contentActions.add(getBt_droite(), gbcCentre);
        gbcCentre.gridx = 0;
        gbcCentre.gridy = 3;
        contentActions.add(getLa_gauche(), gbcCentre);
        gbcCentre.gridx = 1;
        gbcCentre.gridy = 3;
        contentActions.add(getBt_gauche(), gbcCentre);
        gbcCentre.gridx = 0;
        gbcCentre.gridy = 4;
        contentActions.add(getLa_attraper(), gbcCentre);
        gbcCentre.gridx = 1;
        gbcCentre.gridy = 4;
        contentActions.add(getBt_attraper(), gbcCentre);
        gbcCentre.gridx = 0;
        gbcCentre.gridy = 5;
        contentActions.add(getLa_fusion(), gbcCentre);
        gbcCentre.gridx = 1;
        gbcCentre.gridy = 5;
        contentActions.add(getBt_fusion(), gbcCentre);

        /*
         * PANNEAU DU BAS contenant boutons Reset, annuler, sauvegarder
         */
        Container contentNavigation = new JPanel();
        contentNavigation.setLayout(new GridBagLayout());
        GridBagConstraints gbcBas = new GridBagConstraints();

        // panneau à 10px du bas et du haut de la fenêtre
        gbcBas.insets = new Insets(10, 0, 10, 0);
        // l'axe y ne bouge pas (1 seule ligne)
        gbcBas.gridy = 0;
        gbcBas.gridx = 0;
        // 50% de l'espace supplémentaire pour le bouton reset
        gbcBas.weightx = 0.5;
        contentNavigation.add(getBt_reset(), gbcBas);
        gbcBas.gridx = 1;
        // 25% pour le bouton annuler
        gbcBas.weightx = 0.25;
        contentNavigation.add(getBt_annuler(), gbcBas);
        gbcBas.gridx = 2;
        // 25% pour le bouton sauvegarder
        gbcBas.weightx = 0.25;
        contentNavigation.add(getBt_save(), gbcBas);

        // On ajoute le nom des composants en fonction de la langue choisie
        setLangue();

        // ajout des panneaux au panneau principal
        contentCommandes.add(contentHaut, BorderLayout.NORTH);
        contentCommandes.add(contentActions, BorderLayout.CENTER);
        contentCommandes.add(contentNavigation, BorderLayout.SOUTH);

        // ajout au panneau de la fenêtre
        super.add(contentCommandes);

        // on sauvegarde les touches au moment de l'ouverture pour pouvoir les
        // restituer en cas d'annulation
        for (int i = 0; i < ToucheClavier.NB_TOUCHES; i++) {
            tempAbs[i] = ToucheClavier.TOUCHES_ABSOLU[i];
            tempRel[i] = ToucheClavier.TOUCHES_RELATIF[i];
        }

        // on modifie le texte des boutons
        setTextBt();

        // On ajoute les évènements sur les boutons
        getBt_avancer().addMouseListener(gestion);
        getBt_reculer().addMouseListener(gestion);
        getBt_droite().addMouseListener(gestion);
        getBt_gauche().addMouseListener(gestion);
        getBt_attraper().addMouseListener(gestion);
        getBt_fusion().addMouseListener(gestion);
        getBt_save().addMouseListener(gestion);
        getBt_annuler().addMouseListener(gestion);
        getBt_reset().addMouseListener(gestion);

    }

    /**
     * Convertit un entier correspondant à une touche pressée en chaîne de
     * caractères
     * 
     * @param key
     *            l'entier
     * @return valeur la valeur correspondante à l'entier
     */
    public static String keyToString(int key) {
        String valeur;

        // caractères particuliers
        switch (key) {
        case KeyEvent.VK_UP: // Flêche du haut
            valeur = "↑";
            break;
        case KeyEvent.VK_LEFT: // Flêche de gauche
            valeur = "←";
            break;
        case KeyEvent.VK_RIGHT: // Flêche de droite
            valeur = "→";
            break;
        case KeyEvent.VK_DOWN: // Flêche du bas
            valeur = "↓";
            break;
        case KeyEvent.VK_CONTROL: // Touche Contrôle
            valeur = "Ctrl";
            break;
        case KeyEvent.VK_SPACE: // Touche Espace
            valeur = "Space";
            break;
        case KeyEvent.VK_ENTER: // touche entrée
            valeur = "Enter";
            break;
        case KeyEvent.VK_BACK_SPACE: // touche backspace
            valeur = "BkSpc";
            break;
        case KeyEvent.VK_SHIFT:
            valeur = "Maj";
            break;
        case KeyEvent.VK_ALT:
            valeur = "Alt";
            break;
        default:
            valeur = Character.toString((char) (key));
            break;
        }
        return valeur;
    }

    /**
     * Modifie le texte des boutons par la valeur correspondante
     */
    public void setTextBt() {
        // on modifie le texte des boutons (partie graphique) en fonction du
        // mode

        if (ToucheClavier.isModeAbsolu) {
            for (int i = 0; i < ToucheClavier.NB_TOUCHES; i++) {
                switch (i) {
                case 0:
                    getBt_avancer().setText(
                            keyToString(ToucheClavier.TOUCHES_ABSOLU[i]));
                    break;
                case 1:
                    getBt_gauche().setText(
                            keyToString(ToucheClavier.TOUCHES_ABSOLU[i]));
                    break;
                case 2:
                    getBt_droite().setText(
                            keyToString(ToucheClavier.TOUCHES_ABSOLU[i]));
                    break;
                case 3:
                    getBt_reculer().setText(
                            keyToString(ToucheClavier.TOUCHES_ABSOLU[i]));
                    break;
                case 4:
                    getBt_fusion().setText(
                            keyToString(ToucheClavier.TOUCHES_ABSOLU[i]));
                    break;
                case 5:
                    getBt_attraper().setText(
                            keyToString(ToucheClavier.TOUCHES_ABSOLU[i]));
                    break;
                }
            }
        } else { // mode relatif
            for (int i = 0; i < ToucheClavier.NB_TOUCHES; i++) {
                switch (i) {
                case 0:
                    getBt_avancer().setText(
                            keyToString(ToucheClavier.TOUCHES_RELATIF[i]));
                    break;
                case 1:
                    getBt_gauche().setText(
                            keyToString(ToucheClavier.TOUCHES_RELATIF[i]));
                    break;
                case 2:
                    getBt_droite().setText(
                            keyToString(ToucheClavier.TOUCHES_RELATIF[i]));
                    break;
                case 3:
                    getBt_reculer().setText(
                            keyToString(ToucheClavier.TOUCHES_RELATIF[i]));
                    break;
                case 4:
                    getBt_fusion().setText(
                            keyToString(ToucheClavier.TOUCHES_RELATIF[i]));
                    break;
                case 5:
                    getBt_attraper().setText(
                            keyToString(ToucheClavier.TOUCHES_RELATIF[i]));
                    break;
                }
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see vue.ChangementLangue#setLangue()
     */
    @Override
    public void setLangue() {
        String[] traductionCommandes = traducteur.getCommandes();
        this.setTitle(traductionCommandes[0]);
        getLa_type().setText(traductionCommandes[1]);
        getBr_absolu().setText(traductionCommandes[2]);
        getBr_relatif().setText(traductionCommandes[3]);

        getLa_avancer().setText(traductionCommandes[4]);
        getLa_reculer().setText(traductionCommandes[5]);
        getLa_droite().setText(traductionCommandes[6]);
        getLa_gauche().setText(traductionCommandes[7]);
        getLa_attraper().setText(traductionCommandes[8]);
        getLa_fusion().setText(traductionCommandes[9]);

        getBt_reset().setText(traductionCommandes[10]);
        getBt_annuler().setText(traductionCommandes[11]);
        getBt_save().setText(traductionCommandes[12]);

    }

    /**
     * @return the la_type
     */
    public JLabel getLa_type() {
        if (la_type == null) {
            la_type = new JLabel();
            la_type.setHorizontalAlignment(SwingConstants.LEFT);
            la_type.setPreferredSize(UtilitaireFenetre.DIM_COMPOSANT_PRINCIPAL);
        }
        return la_type;
    }

    /**
     * @return the br_relatif
     */
    public JRadioButton getBr_relatif() {
        if (br_relatif == null) {
            br_relatif = new JRadioButton();
            br_relatif.setSelected(!ToucheClavier.isModeAbsolu);

        }
        // on modifie le texte des touches par rapport au mode
        // relatif
        br_relatif.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent g) {
                ToucheClavier.setModeAbsolu(false);
                setTextBt();
            }
        });
        return br_relatif;
    }

    /**
     * @return the br_absolu
     */
    public JRadioButton getBr_absolu() {
        if (br_absolu == null) {
            br_absolu = new JRadioButton();
            br_absolu.setSelected(ToucheClavier.isModeAbsolu);
        }
        // on modifie le texte des touches par rapport au mode
        // absolu
        br_absolu.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                ToucheClavier.setModeAbsolu(true);
                setTextBt();
            }
        });
        return br_absolu;
    }

    /**
     * @return the bt_avancer
     */
    public JButton getBt_avancer() {
        if (bt_avancer == null) {
            bt_avancer = new JButton();
            // On définit une taille pour le bouton
            UtilitaireFenetre.setAllSize(bt_avancer,
                    UtilitaireFenetre.DIM_BOUTON_COMMANDE);
        }
        return bt_avancer;
    }

    /**
     * @return the bt_reculer
     */
    public JButton getBt_reculer() {
        if (bt_reculer == null) {
            bt_reculer = new JButton();
            // On définit une taille pour le bouton
            UtilitaireFenetre.setAllSize(bt_reculer,
                    UtilitaireFenetre.DIM_BOUTON_COMMANDE);
        }
        return bt_reculer;
    }

    /**
     * @return the bt_droite
     */
    public JButton getBt_droite() {
        if (bt_droite == null) {
            bt_droite = new JButton();
            // On définit une taille pour le bouton
            UtilitaireFenetre.setAllSize(bt_droite,
                    UtilitaireFenetre.DIM_BOUTON_COMMANDE);
        }
        return bt_droite;
    }

    /**
     * @return the bt_gauche
     */
    public JButton getBt_gauche() {
        if (bt_gauche == null) {
            bt_gauche = new JButton();
            // On définit une taille pour le bouton
            UtilitaireFenetre.setAllSize(bt_gauche,
                    UtilitaireFenetre.DIM_BOUTON_COMMANDE);
        }
        return bt_gauche;
    }

    /**
     * @return the bt_attraper
     */
    public JButton getBt_attraper() {
        if (bt_attraper == null) {
            bt_attraper = new JButton();
            // On définit une taille pour le bouton
            UtilitaireFenetre.setAllSize(bt_attraper,
                    UtilitaireFenetre.DIM_BOUTON_COMMANDE);
        }
        return bt_attraper;
    }

    /**
     * @return the bt_fusion
     */
    public JButton getBt_fusion() {
        if (bt_fusion == null) {
            bt_fusion = new JButton();
            // On définit une taille pour le bouton
            UtilitaireFenetre.setAllSize(bt_fusion,
                    UtilitaireFenetre.DIM_BOUTON_COMMANDE);
        }
        return bt_fusion;
    }

    /**
     * @return the bt_reset
     */
    public JButton getBt_reset() {
        if (bt_reset == null) {
            bt_reset = new JButton();
            // On définit une taille pour le bouton
            UtilitaireFenetre.setAllSize(bt_reset,
                    UtilitaireFenetre.DIM_BOUTON_BAS_COMMANDE);
        }
        return bt_reset;
    }

    /**
     * @return the bt_annuler
     */
    public JButton getBt_annuler() {
        if (bt_annuler == null) {
            bt_annuler = new JButton();
            // On définit une taille pour le bouton
            UtilitaireFenetre.setAllSize(bt_annuler,
                    UtilitaireFenetre.DIM_BOUTON_BAS_COMMANDE);
        }
        return bt_annuler;
    }

    /**
     * @return the bt_save
     */
    public JButton getBt_save() {
        if (bt_save == null) {
            bt_save = new JButton();
            // On définit une taille pour le bouton
            UtilitaireFenetre.setAllSize(bt_save,
                    UtilitaireFenetre.DIM_BOUTON_BAS_COMMANDE);
        }
        return bt_save;
    }

    /**
     * @return the la_avancer
     */
    public JLabel getLa_avancer() {
        if (la_avancer == null) {
            la_avancer = new JLabel();
            la_avancer.setHorizontalAlignment(SwingConstants.RIGHT);
            la_avancer
                    .setPreferredSize(UtilitaireFenetre.DIM_COMPOSANT_COMMANDE);
        }
        return la_avancer;
    }

    /**
     * @return the la_reculer
     */
    public JLabel getLa_reculer() {
        if (la_reculer == null) {
            la_reculer = new JLabel();
            la_reculer.setHorizontalAlignment(SwingConstants.RIGHT);
            la_reculer
                    .setPreferredSize(UtilitaireFenetre.DIM_COMPOSANT_COMMANDE);
        }
        return la_reculer;
    }

    /**
     * @return the la_droite
     */
    public JLabel getLa_droite() {
        if (la_droite == null) {
            la_droite = new JLabel();
            la_droite.setHorizontalAlignment(SwingConstants.RIGHT);
            la_droite
                    .setPreferredSize(UtilitaireFenetre.DIM_COMPOSANT_COMMANDE);
        }
        return la_droite;
    }

    /**
     * @return the la_gauche
     */
    public JLabel getLa_gauche() {
        if (la_gauche == null) {
            la_gauche = new JLabel();
            la_gauche.setHorizontalAlignment(SwingConstants.RIGHT);
            la_gauche
                    .setPreferredSize(UtilitaireFenetre.DIM_COMPOSANT_COMMANDE);
        }
        return la_gauche;
    }

    /**
     * @return the la_attraper
     */
    public JLabel getLa_attraper() {
        if (la_attraper == null) {
            la_attraper = new JLabel();
            la_attraper.setHorizontalAlignment(SwingConstants.RIGHT);
            la_attraper
                    .setPreferredSize(UtilitaireFenetre.DIM_COMPOSANT_COMMANDE);
        }
        return la_attraper;
    }

    /**
     * @return the la_fusion
     */
    public JLabel getLa_fusion() {
        if (la_fusion == null) {
            la_fusion = new JLabel();
            la_fusion.setHorizontalAlignment(SwingConstants.RIGHT);
            la_fusion
                    .setPreferredSize(UtilitaireFenetre.DIM_COMPOSANT_COMMANDE);
        }
        return la_fusion;
    }

    /**
     * Fenêtre dialog permettant de récupérer une touche entrée par
     * l'utilisateur
     * 
     * @param indice
     *            indice correspondant à la touche à modifier dans le tableau
     * @param leBt
     *            bouton correspondant pour modifier le texte
     */
    public static void afficherPressKey(int indice, JButton leBt) {
        JDialog dialog = new JDialog();
        dialog.setModal(true);
        JLabel label = new JLabel("Press a key");
        UtilitaireFenetre.setAllSize(label,
                UtilitaireFenetre.DIM_COMPOSANT_SECONDAIRE);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent f) {
                SwingUtilities.getWindowAncestor(f.getComponent()).dispose();
                if (f != null && !isPresent(f.getKeyCode())) {
                    leBt.setText(keyToString(f.getKeyCode()));
                    // on modifie dans le tableau des commandes correspondant
                    if (ToucheClavier.isModeAbsolu) {
                        ToucheClavier.TOUCHES_ABSOLU[indice] = f.getKeyCode();
                    } else {
                        ToucheClavier.TOUCHES_RELATIF[indice] = f.getKeyCode();
                    }
                }

            }
        });

        label.setFocusable(true);
        dialog.add(label);
        dialog.pack();
        // On centre l'écran
        UtilitaireFenetre.centrerFenetre(dialog);
        dialog.setVisible(true);
    }

    /**
     * @return the temp le tableau temporaire
     */
    public static int[] getTemp() {
        return (ToucheClavier.isModeAbsolu ? tempAbs : tempRel);
    }

    /**
     * Verifie si une valeur est déjà présente dans le tableau des touches
     * 
     * @param aVerifier
     * @return true si la valeur est présente false si non présente
     */
    public static boolean isPresent(int aVerifier) {
        boolean present = false;
        if (ToucheClavier.isModeAbsolu) {
            for (int i = 0; i < ToucheClavier.NB_TOUCHES; i++) {
                if (ToucheClavier.TOUCHES_ABSOLU[i] == aVerifier) {
                    present = true;
                }
            }
        } else {
            for (int i = 0; i < ToucheClavier.NB_TOUCHES; i++) {
                if (ToucheClavier.TOUCHES_RELATIF[i] == aVerifier) {
                    present = true;
                }
            }
        }
        return (present);
    }
}
