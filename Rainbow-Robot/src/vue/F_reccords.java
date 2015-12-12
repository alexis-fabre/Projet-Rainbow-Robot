/*
 * Reccords.java							é8 nov é015
 * IUT Info2 2015-é016
 */
package vue;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import metier.LigneUsernameScore;
import metier.OperationsFichier;
import evenement.ClicSouris;

/**
 * Fenetre contenant meilleurs scores (temps) des joueurs en fonction du niveau
 * effectué
 * Chaque onglet correspond à un niveau
 * @author Rainbow Robot
 * @version 1.0
 */
public class F_reccords extends JFrame implements ChangementLangue {

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

    /** label contenant le titre de la colonne joueur */ 
    private JLabel la_titreJoueur;

    /** label contenant le titre de la colonne temps */ 
    private JLabel la_titreTemps;

    /**
     * label contenant le tableau a afficher (données)
     */
    private JLabel la_contenu;

    /**
     * label du niveau affiché dans les onglets
     */
    private JLabel la_niveau;

    /**
     * Constructeur de la fenêtre Reccords 
     * @param gestion
     */
    public F_reccords(ClicSouris gestion) {
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
        lesOnglets.setBackground(Color.CYAN);
        lesOnglets.setBorder(BorderFactory.createLineBorder(Color.black));
        this.lesPanneaux = new JPanel[NB_MAX_SCORES];
        
        // On ajoute le nom des composants en fonction de la langue choisie
        setLangue();
        String texte;
        // ajout du contenu du panneau pour chaque niveau
        for(int i = 0; i < lesPanneaux.length ; i++) {
            texte = getLa_niveau().getText() + " " + (i+1); 
            // ajout des onglets et de leur panneaux
            lesOnglets.addTab(texte , ajoutContenu(lesPanneaux[i], i));
            
        }
  
        
        // ajout évènement sur bouton retour
        getBt_Retour().addMouseListener(gestion);
        
        // On centre l'écran
        UtilitaireFenetre.centrerFenetre(this);
        
        contentPane.add(getLesOnglets()); // ajout des onglets
        contentPane.add(getBt_Retour());
    }


    /**
     * Crée le panneau du niveau correspondant et lui ajoute le contenu
     * @param aAjouter
     * @param indice
     * @return aAjouter le panneau avec le contenu
     */
    public JPanel ajoutContenu(JPanel aAjouter, int indice) {
        
        aAjouter = new JPanel();
        aAjouter.setLayout(null);
        
        la_contenu = new JLabel();
        la_contenu.setBounds(10, 10, 850, 500);
        la_contenu.setFont(new Font("Georgia", Font.PLAIN, 30));
        la_contenu.setHorizontalAlignment(SwingConstants.CENTER);
        // récupération des données
        ArrayList<LigneUsernameScore> ligneUsernameScore = 
                OperationsFichier.lectureFichierReccord(new File("highscore1.txt"));
        
        
        String scores;
        scores = "<html>";
        // titre des colonnes du tableau
        scores += "<table>" + "<tr><th style=\"text-align:center; \">No. |</th><th style=\"text-align:center; margin-left: 10px; margin-right: 10px;\">" 
                + getLa_titreJoueur().getText() + "</th><th style=\"text-align:center;\">| " + getLa_titreTemps().getText() 
                + "</th></tr><hr/>";
        // contenu de chaque ligne
        for(int i = 0; i < ligneUsernameScore.size(); i++) {
                scores += "<tr><td style=\"text-align:center;\">" + (i+1) + ".</td><td style=\"text-align:center; margin-left: 10px; margin-right: 10px;\">" + ligneUsernameScore.get(i).getUsername() 
                        + "</td><td style=\"text-align:center;\">" + ligneUsernameScore.get(i).getScore() + "</td></tr><br>";
        }
        scores += "</table></html>";
        
        la_contenu.setText(scores);
        
        aAjouter.add(la_contenu);
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
        getLa_niveau().setText(traductionReccords[3]);

    }


    /**
     * @return the la_titreJoueur
     */
    public JLabel getLa_titreJoueur() {
        if (la_titreJoueur == null) {
            la_titreJoueur = new JLabel();
            la_titreJoueur.setFont(new Font("Georgia", Font.PLAIN, 50));
            la_titreJoueur.setHorizontalAlignment(SwingConstants.CENTER);
        }
        return la_titreJoueur;
    }


    /**
     * @return the la_titreTemps
     */
    public JLabel getLa_titreTemps() {
        if (la_titreTemps == null) {
            la_titreTemps = new JLabel();
            la_titreJoueur.setFont(new Font("Georgia", Font.PLAIN, 50));
            la_titreJoueur.setHorizontalAlignment(SwingConstants.CENTER);
        }
        return la_titreTemps;
    }


    /**
     * @return the lesOnglets
     */
    public JTabbedPane getLesOnglets() {
        return lesOnglets;
    }


    /**
     * @return the la_niveau
     */
    public JLabel getLa_niveau() {
        if (la_niveau == null) {
            la_niveau = new JLabel();
        }
        return la_niveau;
    }


    
    

}