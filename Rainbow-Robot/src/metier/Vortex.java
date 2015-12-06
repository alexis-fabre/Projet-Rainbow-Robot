/*
 * Vortex.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package metier;

import java.awt.Graphics2D;

/**
 *
 * @author Rainbow Robot
 */
public class Vortex implements Dessinable {
	
	private Position pos_vortex;

	@Override
	public void dessiner(Graphics2D g) {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools|Templates.
	}

	// LE VORTEX SERA EN 0,0

	public Vortex(Position pos){
		pos_vortex = pos;
	}

	/**
	 * Retourne la position du Vortex
	 * @return pos_courante la position courante
	 */
	public Position getPosVortex(){
		return pos_vortex;
	}
	
}
