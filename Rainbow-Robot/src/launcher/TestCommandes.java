/** TestCommandes.java                21 févr. 2016
 * IUT RODEZ INFO2 2015-2016 groupe 2
 */
package launcher;

import metier.JeuRainbow;
import evenement.ClicSouris;
import vue.F_commandes;

/**
 * TODO commenter l'objectif du programme
 * @author Florian
 * @version 0.1
 */
public class TestCommandes {

    /**
     * TODO commenter la méthode
     * @param args
     */
    public static void main(String[] args) {
        JeuRainbow jeu = JeuRainbow.lectureFichier();
        ClicSouris gestion = new ClicSouris(jeu);

        F_commandes testCmd = new F_commandes(gestion);
        testCmd.setVisible(true);
    }

}
