/*
 * Vortex.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package metier;

import java.io.Serializable;

/**
 *
 * @author Rainbow Robot
 */
public class Vortex implements Serializable {

	/**
	 * Générer automatiquement par Eclipse
	 */
	private static final long serialVersionUID = 6297297964624363269L;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return new Vortex((Position) pos_vortex.clone());
	}

}
