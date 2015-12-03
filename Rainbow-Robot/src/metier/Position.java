/*
 * Position.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package metier;

/**
 *
 * @author Rainbow Robot
 */
public class Position {

	/** Coordonnées */
	private int x;
	private int y;

	/**
	 * Constructeur avec arguemnts pour créer une position
	 * 
	 * @param x
	 *            coordonnées
	 * @param y
	 *            coordonnées
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Pour récupérer la coordonée x
	 * 
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 * Pour changer une coordonnée
	 * 
	 * @param x
	 *            coordonnée
	 */
	public void setX(int x) {
		// X = -5..-4 Y = 3..4 OU X = -5..-4 Y = -4..-3  OU  X = 4..5 Y = 3..4  OU X = 4..5 Y = -4..-3
		this.x = x;
	}

	/**
	 * Pour récupérer la coordonée y
	 * 
	 * @return
	 */
	public int getY() {
		return y;
	}

	/**
	 * Pour changer une coordonnée
	 * 
	 * @param y
	 *            coordonnée
	 */
	public void setY(int y) {
		this.y = y;
	}

	public String toString() {
		return " X : " + getX() + " et Y : " + getY();
	}
}
