/*
 * ModeJeu.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package vue;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.border.Border;

/**
 * TODO Expliquer le fonctionnement de la classe
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public abstract class ModeJeu {

    private final String DESC_COMMANDES = "";
    private String REGLES_STORY;
    private String REGLES_ARCADE;
    private String REGLES_CUSTOM;
    private JLabel titre;
    private Border bordure;
    private JButton bt_Retour;
    private JButton bt_Jouer;
    private JLabel lb_difficulte;
    private JRadioButton rb_Facile;
    private JRadioButton rb_Moyen;
    private JRadioButton rb_Difficile;
    private JTextArea regles;
    private JTextArea Commandes;

    public void retourChoixMode() {
        // TODO - Création automaitque par VisualParadigm
    }

    public void jouer() {
        // TODO - Création automaitque par VisualParadigm
    }

    /**
     * 
     * @param nouveau
     */
    public void setTitre(String nouveau) {
        // TODO - Création automaitque par VisualParadigm
    }

    /**
     * 
     * @param aModifier
     */
    public void setCheminPhoto(String aModifier) {
        // TODO - Création automaitque par VisualParadigm
    }

    /**
     * 
     * @param texte
     */
    public void setRegles(String texte) {
        // TODO - Création automaitque par VisualParadigm
    }

    /**
     * 
     * @param texte
     */
    public void setCommandes(String texte) {
        // TODO - Création automaitque par VisualParadigm
    }

}