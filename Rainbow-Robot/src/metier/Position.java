/*
 * Position.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package metier;

/**
 * Classe gérant la position sur un plateau d'une Partie
 * Elle va pouvoir gérer la position d'un robot, des caisses, du vortex
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
		// X = -5..-4 Y = 3..4 OU X = -5..-4 Y = -4..-3 OU X = 4..5 Y = 3..4 OU
		// X = 4..5 Y = -4..-3
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return obj instanceof Position
				&& ((Position) obj).getX() == this.getX()
				&& ((Position) obj).getY() == this.getY();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return " X : " + getX() + " et Y : " + getY();
	}
}
