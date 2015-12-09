/** LigneUsernameScore.java                8 déc. 2015
 * IUT RODEZ INFO2 2015-2016 groupe 2
 */
package metier;

/**
 * Classe permettant d'associer un utilisateur à un score
 * @author INFO2
 * @version 0.1
 */
public class LigneUsernameScore {

    /** psseudo de l'utilisateur */
    private String username;
    
    /** temps de l'utilisateur  */
    private String score;

    /**
     * Constructeur de la ligne 
     * @param username
     * @param score
     */
    public LigneUsernameScore(String username, String score) {
        this.username = username;
        this.score = score;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return score
     */
    public String getScore() {
        return score;
    }

    /**
     * TODO commenter la méthode
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * TODO commenter la méthode
     * @param score
     */
    public void setScore(String score) {
        this.score = score;
    }





}
