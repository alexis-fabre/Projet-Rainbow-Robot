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

	/** Coordonn�es */
	private int x;
	private int y;

	/**
	 * Constructeur avec arguemnts pour cr�er une position
	 * 
	 * @param x
	 *            coordonn�es
	 * @param y
	 *            coordonn�es
	 */
	public Position(int x, int y) {
		// TODO �crire le corps
		this.x = x;
		this.y = y;
	}

	/**
	 * Pour r�cup�rer la coordon�e x
	 * 
	 * @return
	 */
	public int getX() {
		// TODO �crire le corps
		return x;
	}

	/**
	 * Pour changer une coordonn�e
	 * 
	 * @param x
	 *            coordonn�e
	 */
	public void setX(int x) {
		// TODO �crire le corps
		this.x = x;
	}

	/**
	 * Pour r�cup�rer la coordon�e y
	 * 
	 * @return
	 */
	public int getY() {
		// TODO �crire le corps
		return y;
	}

	/**
	 * Pour changer une coordonn�e
	 * 
	 * @param y
	 *            coordonn�e
	 */
	public void setY(int y) {
		// TODO �crie le corps
		this.y = y;
	}

	public String toString() {
		return " X : " + getX() + " et Y : " + getY();
	}
}
