/*
 * Caisse.java							28 nov 2015
 * IUT Info2 2015-2016
 */
package vue;

import metier.Position;

public class Caisse {

    private static final int TAILLE_CAISSE = 1;
    private String couleur;
    private Position position;

    public Caisse() {
        // TODO - Création automaitque par VisualParadigm
    }

    public String getCouleur() {
        return this.couleur;
    }

    /**
     * 
     * @param nouvelle
     */
    public void setCouleur(String nouvelle) {
        this.couleur = nouvelle;
    }

}