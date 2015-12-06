/*
 * Vortex.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package metier;

/**
 *
 * @author Rainbow Robot
 */
public class Vortex {

	private Position pos_vortex;

	// LE VORTEX SERA EN 0,0

	public Vortex(Position pos) {
		pos_vortex = pos;
	}

	/**
	 * Retourne la position du Vortex
	 * 
	 * @return pos_courante la position courante
	 */
	public Position getPosVortex() {
		return pos_vortex;
	}

}
