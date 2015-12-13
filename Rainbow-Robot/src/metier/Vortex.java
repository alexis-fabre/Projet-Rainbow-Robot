/*
 * Vortex.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package metier;

import java.io.Serializable;

/**
 * Classe gérant le vortex qui est uniquement défini par une position initiale
 * (il ne peut pas se déplacer).
 *
 * @author Rainbow Robot
 */
public class Vortex implements Serializable {

	/**
	 * Générer automatiquement par Eclipse
	 */
	private static final long serialVersionUID = 6297297964624363269L;

	/**
	 * Position initiale du vortex
	 */
	private Position pos_vortex;

	/**
	 * Construit un vortex avec sa position initiale
	 * 
	 * @param pos
	 *            position initiale du vortex
	 */
	public Vortex(Position pos) {
		pos_vortex = pos;
	}

	/**
	 * @return pos_courante la position courante du vortex
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
