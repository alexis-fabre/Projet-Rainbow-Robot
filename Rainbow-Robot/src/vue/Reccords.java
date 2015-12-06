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
 * Fenetre contenant meilleurs scores (temps) des joueurs en fonction du niveau
 * effectué
 * Chaque onglet correspond à un niveau
 * @author Rainbow Robot
 * @version 1.0
 */
public class Reccords extends JFrame implements ChangementLangue {

    /** nombre de scores à afficher */
    private final int NB_MAX_SCORES = 10;
    
    /** Titre de la fenêtre */
    private JLabel la_titre;

    /** Bouton pour retourner à l'accueil*/
    private JButton bt_Retour;

    /** Référence des traductions effectuées dans ChoixLangue.java */
    private ChoixLangue traducteur = ChoixLangue.getChoixLangue();

    /** Onglets des différents niveaux */
    private JTabbedPane lesOnglets;
    
    /** tableau de JPanel pour chaque niveau*/
    private JPanel[] lesPanneaux;
    
    /** tableau contenant les couples (nomJoueur, temps effectué) 
     * chaque colonne représente un niveau dans le jeu 
     */
    private HashMap<String, String>[] lesNiveaux;

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
        
        // panneau de la fenêtre
        Container contentPane =  super.getContentPane(); 
        contentPane.setLayout(null);
        
        this.lesOnglets = new JTabbedPane(JTabbedPane.TOP); // onglets en hauts
        this.lesOnglets.setBounds(45, 45, 900, 550);       // position et taille
        lesOnglets.setBackground(Color.YELLOW);
        this.lesPanneaux = new JPanel[NB_MAX_SCORES];
        
        
        // On ajoute le nom des composants en fonction de la langue choisie
        setLangue();

        // ajout du contenu du panneau pour chaque niveau
        for(int i = 0; i < lesPanneaux.length ; i++) {
            // ajout des onglets et de leur panneaux
            lesOnglets.addTab(Integer.toString(i) , ajoutContenu(lesPanneaux[i], i));
        }
  

        // ajout évènement sur bouton retour
        getBt_Retour().addMouseListener(gestion);
        
        // On centre l'écran
        UtilitaireFenetre.centrerFenetre(this);
        
        contentPane.add(getLesOnglets()); // ajout des onglets
        contentPane.add(getBt_Retour());
    }


    /**
     * crée le panneau du niveau correspondant et lui ajoute le contenu
     * @param aAjouter
     * @param indice
     * @return aAjouter le panneau avec le contenu
     */
    public JPanel ajoutContenu(JPanel aAjouter, int indice) {
        aAjouter = new JPanel(new GridLayout(NB_MAX_SCORES, 3));
        // ajout du numro de classement en haut à gauche
        aAjouter.add(new JLabel("No."));
        // ajout du titre joueur en haut au centre
        aAjouter.add(new JLabel("cc"));
        // ajout du titre temps en haut au centre
        aAjouter.add(getLa_titreTemps());
        
        System.out.println(getLa_titreJoueur().getText());
        return aAjouter;
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
                    UtilitaireFenetre.DIM_COMPOSANT_SECONDAIRE);
        }
        return bt_Retour;
    }


    /**
     * TODO Expliquer le fonctionnement de la méthode
     * 
     * @param nomFichier
     */
    public void recupNomScore(String nomFichier) {
        
    }

    
    /* (non-Javadoc)
     * @see vue.ChangementLangue#setLangue()
     */
    @Override
    public void setLangue() {
        // On actualise la langue
        String[] traductionReccords = traducteur.getReccords();
        this.setTitle(traductionReccords[0]);
        getLa_titreJoueur().setText(traductionReccords[1]);
        getLa_titreTemps().setText(traductionReccords[2]);

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
     * @return the lesOnglets
     */
    public JTabbedPane getLesOnglets() {
        return lesOnglets;
    }


    
    

}