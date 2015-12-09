/** OperationsFichier.java                8 déc. 2015
 * IUT RODEZ INFO2 2015-2016 groupe 2
 */
package metier;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import metier.LigneUsernameScore;

/**
 * Classe permettant de lire et d'écrire dans le fichier highscores.txt
 * @author Florian
 * @version 0.1
 */
public class OperationsFichier {


    /**
     * Lis le fichier des reccords et retourne les données présentes dans une ArrayList
     * @param fichier le fichier
     * @return resultat l'arrayList contenant les données
     */
    public static ArrayList<LigneUsernameScore> lectureFichierReccord(File fichier) {
        String ligne = null;
        BufferedReader tampon = null;
        ArrayList<LigneUsernameScore> resultat = new ArrayList<LigneUsernameScore>();

        // ouverture fichier
        try{ 
            tampon = new BufferedReader(new FileReader(fichier));
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        do {
            // lecture d'une ligne
            try {
                ligne = tampon.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // ajout du contenu de la ligne dans l'ArrayList
            String username;
            String score;
            if (ligne != null) {
                username = ligne.split("#")[0];
                score = ligne.split("#")[1];
                resultat.add(new LigneUsernameScore(username,score));
            }
        } while (ligne != null);

        // fermeture fichier
        try {
            tampon.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultat;
    }

    /**
     * Ecrit les couples username/score dans le fichier
     * @param fichier
     * @param ligneUsernameScore
     */
    public static void ecrireFichierReccord(File fichier, ArrayList<LigneUsernameScore> ligneUsernameScore) {
        Writer tampon = null;
        try {
            tampon = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichier)));
            for(LigneUsernameScore ligne : ligneUsernameScore) {
                tampon.write(ligne.getUsername() + "#" + ligne.getScore() + "\n");
            }
            tampon.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}


