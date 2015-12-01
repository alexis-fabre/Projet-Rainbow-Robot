/*
 * ChoixMode.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package vue;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import evenement.ClicSouris;

/**
 * TODO Expliquer le fonctionnement de la classe
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class ChoixMode extends JFrame implements ChangementLangue {

    /**
     * Titre de la fenêtre
     */
    private JLabel la_titre;

    /**
     * Référence des traductions effectuées dans ChoixLangue.java
     */
    private ChoixLangue traducteur = ChoixLangue.getChoixLangue();
    
    /**
     * Bouton qui lance la fenêtre expliquant le mode "Story"
     */
    private JButton bt_Story;

    /**
     * Bouton qui lance la fenêtre expliquant le mode "Arcade"
     */
    private JButton bt_Arcade;

    /**
     * Bouton qui lance la fenêtre expliquant le mode "Custom"
     */
    private JButton bt_Custom;

    /**
     * Bouton qui permet de retourner au menu précédent
     */
    private JButton bt_Retour;

    /**
     * Brêve description du mode Story
     */
    private static final JLabel DESC_STORY = new JLabel();

    /**
     * Brêve description du mode Arcade
     */
    private static final JLabel DESC_ARCADE = new JLabel();

    /**
     * Brêve description du mode Custom
     */
    private static final JLabel DESC_CUSTOM = new JLabel();

    /**
     * Constructeur de la fenêtre ChoixMode
     * Initialise les composants et les disposent sur un contexte graphique 2D.
     * @param gestion
     */
    public ChoixMode(ClicSouris gestion) {
        super();

        super.setSize(UtilitaireFenetre.DIM_FENETRE);
        // On rend la fenêtre non redimenssionable
        super.setResizable(false);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // On définit le layoutManager
        Container contentPane = super.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        // On ajoute les composants dans la fenêtre
        UtilitaireFenetre.addAComposantWithBoxLayout(getLa_titre(),
                        contentPane, 0, 30);
        UtilitaireFenetre.addAComposantWithBoxLayout(getBt_Story(),
                        contentPane, 0, 7);
        UtilitaireFenetre.addAComposantWithBoxLayout(getDescStory(),
                        contentPane, 0, 25);
        UtilitaireFenetre.addAComposantWithBoxLayout(getBt_Arcade(),
                        contentPane, 0, 7);
        UtilitaireFenetre.addAComposantWithBoxLayout(getDescArcade(),
                        contentPane, 0, 25);
        UtilitaireFenetre.addAComposantWithBoxLayout(getBt_Custom(),
                        contentPane, 0, 7);
        UtilitaireFenetre.addAComposantWithBoxLayout(getDescCustom(),
                        contentPane, 0, 25);
        // On aligne le composant horizontalement par rapport à la fenêtre
        // getBt_Retour().setAlignmentX(Component.BOTTOM_ALIGNMENT);        
        //contentPane.add(Box.createRigidArea(new Dimension(10,0)));
        
        // LayoutManager pour placer les deux boutons
        SpringLayout layout_boutons = new SpringLayout();
        // Panneau contenant les deux boutons Retour et Jouer
        Container contentBoutons = new JPanel(layout_boutons);

        // On laisse une petite marge sur l'axe des y
        contentBoutons.setPreferredSize(new Dimension(
          UtilitaireFenetre.DIM_FENETRE.width,
          UtilitaireFenetre.DIM_BOUTON_SECONDAIRE.height + 20));

        contentBoutons.add(getBt_Retour());

        // On place les deux boutons
        // Bouton Retour
        layout_boutons.putConstraint(SpringLayout.WEST, getBt_Retour(), 10,
          SpringLayout.WEST, contentBoutons);
        layout_boutons.putConstraint(SpringLayout.NORTH, getBt_Retour(), 10,
          SpringLayout.NORTH, contentBoutons);
        getBt_Retour().setLocation(10, 400);
        contentPane.add(getBt_Retour());

        // On ajoute le nom des composants en fonction de la langue choisie
        setLangue();

        // On centre l'écran
        UtilitaireFenetre.centrerFenetre(this);

        // On ajoute les évènements sur les boutons
        getBt_Story().addMouseListener(gestion);
        getBt_Arcade().addMouseListener(gestion);
        getBt_Custom().addMouseListener(gestion);
        getBt_Retour().addMouseListener(gestion);
        
    }

    /* (non-Javadoc)
     * @see vue.ChangementLangue#setLangue()
     */
    @Override
    public void setLangue() {
        String[] traducionChoixMode = traducteur.getChoixMode();
        this.setTitle(traducionChoixMode[0]);
        getLa_titre().setText(traducionChoixMode[0]);
        getDescStory().setText(traducionChoixMode[1]);
        getDescArcade().setText(traducionChoixMode[2]);
        getDescCustom().setText(traducionChoixMode[3]);
        getBt_Retour().setText(traducionChoixMode[4]);

    }

    /**
     * @return the la_titre
     */
    public JLabel getLa_titre() {
        if (la_titre == null) {
            la_titre = new JLabel();
            la_titre.setPreferredSize(UtilitaireFenetre.DIM_BOUTON_PRINCIPAL);
        }
        return la_titre;
    }

    /**
     * @return the bt_Story
     */
    public JButton getBt_Story() {
        if (bt_Story == null) {
            bt_Story = new JButton();
            // On définit une taille pour le bouton
            bt_Story.setMaximumSize(UtilitaireFenetre.DIM_BOUTON_PRINCIPAL);
            bt_Story.setMinimumSize(UtilitaireFenetre.DIM_BOUTON_PRINCIPAL);
            bt_Story.setPreferredSize(UtilitaireFenetre.DIM_BOUTON_PRINCIPAL);
            bt_Story.setText("Story");
        }
        return bt_Story;
    }

    /**
     * @return the bt_Arcade
     */
    public JButton getBt_Arcade() {
        if (bt_Arcade == null) {
            bt_Arcade = new JButton();
            // On définit une taille pour le bouton
            bt_Arcade.setMaximumSize(UtilitaireFenetre.DIM_BOUTON_PRINCIPAL);
            bt_Arcade.setMinimumSize(UtilitaireFenetre.DIM_BOUTON_PRINCIPAL);
            bt_Arcade.setPreferredSize(UtilitaireFenetre.DIM_BOUTON_PRINCIPAL);
            bt_Arcade.setText("Arcade");
        }
        return bt_Arcade;
    }

    /**
     * @return the bt_Custom
     */
    public JButton getBt_Custom() {
        if (bt_Custom == null) {
            bt_Custom = new JButton();
            // On définit une taille pour le bouton
            bt_Custom.setMaximumSize(UtilitaireFenetre.DIM_BOUTON_PRINCIPAL);
            bt_Custom.setMinimumSize(UtilitaireFenetre.DIM_BOUTON_PRINCIPAL);
            bt_Custom.setPreferredSize(UtilitaireFenetre.DIM_BOUTON_PRINCIPAL);
            bt_Custom.setText("Custom");
        }
        return bt_Custom;
    }

    /**
     * @return the bt_Retour
     */
    public JButton getBt_Retour() {
        if (bt_Retour == null) {
            bt_Retour = new JButton();
            // On définit une taille pour le bouton
            bt_Retour.setMaximumSize(UtilitaireFenetre.DIM_BOUTON_SECONDAIRE);
            bt_Retour.setMinimumSize(UtilitaireFenetre.DIM_BOUTON_SECONDAIRE);
            bt_Retour.setPreferredSize(UtilitaireFenetre.DIM_BOUTON_SECONDAIRE);
        }
        return bt_Retour;
    }

    /**
     * @return the descStory
     */
    public static JLabel getDescStory() {
        return DESC_STORY;
    }

    /**
     * @return the descArcade
     */
    public static JLabel getDescArcade() {
        return DESC_ARCADE;
    }

    /**
     * @return the descCustom
     */
    public static JLabel getDescCustom() {
        return DESC_CUSTOM;
    }



}