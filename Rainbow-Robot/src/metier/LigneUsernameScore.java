/* LigneUsernameScore.java                8 déc. 2015
 * IUT Info2 2015-2016
 */
package metier;

/**
 * Classe permettant d'associer un utilisateur à un score. Cette classe permet
 * de gérer les différents reccords.
 * 
 * @author Rainbow Robot
 * @version 0.1
 */
public class LigneUsernameScore {

	/** pseudo de l'utilisateur */
	private String username;

	/** temps de l'utilisateur */
	private String score;

	/**
	 * Constructeur de la ligne
	 * 
	 * @param username
	 *            nom (ou pseudo) de l'utilisateur
	 * @param score
	 *            score que l'utilisateur a fait
	 */
	public LigneUsernameScore(String username, String score) {
		this.username = username;
		this.score = score;
	}

	/**
	 * @return username le nom de l'utilisateur
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return score le score de l'utilisateur
	 */
	public String getScore() {
		return score;
	}

	/**
	 * @param username le nom de l'utilisateur a modifié
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param score le score de l'utilisateur a modifié
	 */
	public void setScore(String score) {
		this.score = score;
	}
}
