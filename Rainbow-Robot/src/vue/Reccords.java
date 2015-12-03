/*
 * Reccords.java							é8 nov é015
 * IUT Info2 2015-é016
 */
package vue;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import evenement.ClicSouris;

/**
 * TODO Expliquer le fonctionnement de la classe
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class Reccords extends JFrame implements ChangementLangue{

    /** Titre de la fenêtre */
    private JLabel la_titre;

    /** Bouton pour retourner à l'accueil*/
    private JButton bt_Retour;

    /** Référence des traductions effectuées dans ChoixLangue.java */
    private ChoixLangue traducteur = ChoixLangue.getChoixLangue();

    /** Onglets des différents niveaux */
    private JTabbedPane lesOnglets;

    /** panneau CardLayout pour le niveau 1 */
    private JPanel cl_niv1;

    /** panneau pour afficher le contenu sur 2 colonnes pour le niveau 1 */
    private JPanel niv1;

    /** panneau pour le niveau 2 */
    private JPanel cl_niv2;

    /** panneau pour afficher le contenu sur 2 colonnes pour le niveau 2 */
    private JPanel niv2;

    /** panneau pour le niveau 3 */
    private JPanel cl_niv3;

    /** panneau pour afficher le contenu sur 2 colonnes pour le niveau 3 */
    private JPanel niv3;

//    /** tableau contenant les couples (nomJoueur, temps effectué) 
//     * chaque colonne représente un niveau dans le jeu 
//     */
//    private HashMap<String, String>[] lesNiveaux;

    /** label contenant le titre de la colonne joueur */ 
    private JLabel la_titreJoueur;

    /** label contenant le titre de la colonne temps */ 
    private JLabel la_titreTemps;

//    /**
//     * label du temps affiché sur la fenêtre
//     */
//    private JLabel[] la_temps;
//
//    /**
//     * label du nom du joueur affiché sur la fenêtre
//     */
//    private JLabel[] la_joueur;

    /**
     * Constructeur de la fenêtre Reccords 
     * @param gestion
     */
    public Reccords(ClicSouris gestion) {
        super();
        super.setSize(UtilitaireFenetre.DIM_FENETRE);
        // On rend la fenêtre non redimenssionable
        super.setResizable(false);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.lesOnglets = new JTabbedPane(JTabbedPane.TOP); // onglets en hauts
        this.lesOnglets.setBounds(20, 10, 450, 580);       // position et taille
        // panneau de la fenêtre
        Container contentPane =  super.getContentPane();
        
        contentPane.setLayout(null);


        
        
        // cardLayout pour les modifications en temps réel
        JPanel cl_niv1 = new JPanel(new CardLayout());
        JPanel cl_niv2 = new JPanel(new CardLayout());
        JPanel cl_niv3 = new JPanel(new CardLayout());

        // panneaux de gridLayout pour pouvoir séparer 
        // on ajoute 1 pour le titre
//        JPanel niv1 = new JPanel(new GridLayout(lesNiveaux[0].size()+1, 2));
//        JPanel niv2 = new JPanel(new GridLayout(lesNiveaux[1].size()+1, 2) );
//        JPanel niv3 = new JPanel(new CardLayout(lesNiveaux[2].size()+1, 2) );
        
        JPanel niv1 = new JPanel(new GridLayout(10, 3));
        JPanel niv2 = new JPanel(new GridLayout(10, 3) );
        JPanel niv3 = new JPanel(new GridLayout(10, 3) );

        // ajout des onglets et de leur panneaux
        lesOnglets.addTab("1", cl_niv1);
        cl_niv1.add(niv1);

        lesOnglets.addTab("2", cl_niv2);
        cl_niv1.add(niv2);

        lesOnglets.addTab("3", cl_niv3);
        cl_niv1.add(niv3);

        // ajout des titres
        niv1.add(getLa_titreJoueur(), 0);
        niv1.add(getLa_titreJoueur(), 0);
        
        

        // On ajoute le nom des composants en fonction de la langue choisie
        setLangue();


        // ajout évènement sur bouton retour
        getBt_Retour().addMouseListener(gestion);
        
        contentPane.add(lesOnglets); // ajout des onglets
        contentPane.add(getBt_Retour());
    }


    /**
     * @return le JLabel la_titre titre de la fenêtre
     */
    public JLabel getLa_titre() {
        if (la_titre == null) {
            la_titre = new JLabel();
            la_titre.setPreferredSize(UtilitaireFenetre.DIM_COMPOSANT_PRINCIPAL);
        }
        return la_titre;
    }

    /**
     * @return le JButton bt_Retour qui lance la fenêtre F_Accueil
     */
    public JButton getBt_Retour() {
        if (bt_Retour == null) {
            bt_Retour = new JButton();
            // On définit une taille pour le bouton
            UtilitaireFenetre.setAllSize(bt_Retour,
                    UtilitaireFenetre.DIM_COMPOSANT_PRINCIPAL);
        }
        return bt_Retour;
    }


    /**
     * TODO Expliquer le fonctionnement de la méthode
     * 
     * @param nomFichier
     */
    public void recupNomScore(String nomFichier) {
        // TODO - Création automaitque par VisualParadigm
    }

    /* (non-Javadoc)
     * @see vue.ChangementLangue#setLangue()
     */
    @Override
    public void setLangue() {
        // On actualise la langue
        String[] traductionAccueil = traducteur.getReccords();
        this.setTitle(traductionAccueil[0]);
        getLa_titre().setText(traductionAccueil[0]);
        getLa_titreJoueur().setText(traductionAccueil[1]);
        getLa_titreTemps().setText(traductionAccueil[2]);

    }


    /**
     * @return the la_titreJoueur
     */
    public JLabel getLa_titreJoueur() {
        if (la_titreJoueur == null) {
            la_titreJoueur = new JLabel();
        }
        return la_titreJoueur;
    }


    /**
     * @return the la_titreTemps
     */
    public JLabel getLa_titreTemps() {
        if (la_titreTemps == null) {
            la_titreTemps = new JLabel();
        }
        return la_titreTemps;
    }


    /**
     * @return the cl_niv1
     */
    public JPanel getCl_niv1() {
        return cl_niv1;
    }


    /**
     * @return the niv1
     */
    public JPanel getNiv1() {
        return niv1;
    }


    /**
     * @return the cl_niv2
     */
    public JPanel getCl_niv2() {
        return cl_niv2;
    }


    /**
     * @return the niv2
     */
    public JPanel getNiv2() {
        return niv2;
    }


    /**
     * @return the cl_niv3
     */
    public JPanel getCl_niv3() {
        return cl_niv3;
    }


    /**
     * @return the niv3
     */
    public JPanel getNiv3() {
        return niv3;
    }
    
    

}