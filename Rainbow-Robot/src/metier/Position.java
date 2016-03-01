/*
 * Position.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package metier;

import java.io.Serializable;

/**
 * Classe gérant la position sur un plateau d'une Partie. Elle va pouvoir gérer
 * la position d'un robot, des caisses et du vortex.
 * 
 * @author Rainbow Robot
 */
public class Position implements Serializable {

	/**
	 * Générer automatiquement par Eclispe
	 */
	private static final long serialVersionUID = 6603202690509989524L;

	/** Coordonnées horizontales */
	private int x;

	/** Coordonnées verticales */
	private int y;

	/**
	 * Constructeur avec arguments pour créer une position
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
	 * @return l'abscisse de la position
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x
	 *            pour changer l'abscisse
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return l'ordonnée de la position
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 *            pour changer l'ordonnée
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @param pos
	 *            position centrale
	 * @return les positions adjacentes (gauche, haut, droite, bas dans cet
	 *         ordre) à la position centrale
	 */
	public static Position[] getPositionsAdjacentes(Position pos) {
		return new Position[] { new Position(pos.getX() - 1, pos.getY()),
				new Position(pos.getX(), pos.getY() - 1),
				new Position(pos.getX() + 1, pos.getY()),
				new Position(pos.getX(), pos.getY() + 1), };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
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
		return "(" + getX() + " ; " + getY() + ")";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Position(this.getX(), this.getY());
	}
}
