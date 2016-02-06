/*
 * Vortex.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package metier;

import java.io.Serializable;
import java.util.List;
import java.util.Observable;

/**
 * Classe gérant le vortex qui est uniquement défini par une position initiale
 * (il ne peut pas se déplacer).
 *
 * @author Rainbow Robot
 */
public class Vortex extends Observable implements Serializable {

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

	/**
	 * Vérifie si le vortex peut absorber la caisse (si la caisse à la même
	 * couleur que celle demandée). Si c'est le cas, on fait disparaître la
	 * caisse et on actualise la nouvelle caisse a récupéré.
	 * 
	 * @param caissePlateau
	 *            référence des caisses situées sur le plateau de jeu
	 * @param caisseARecuperee
	 *            référence des caisses à récupérer
	 * @param robot
	 *            référence du robot
	 * @return true si la partie est finie ou false sinon.
	 */
	public boolean vortexAspire(Caisse[] caissePlateau,
			List<Caisse> caisseARecuperee, Robot robot) {
		// On parcours les caisses sur le plateau de jeu et on vérifie si l'une
		// d'elle est positionné sur le vortex et si elle est de la même couleur
		// que celle demandée dans la liste
		for (int i = 0; i < caissePlateau.length; i++) {
			if (caissePlateau[i] != null
					&& caissePlateau[i].getPosCaisse().equals(pos_vortex)
					&& caissePlateau[i].getCouleur().equals(
							caisseARecuperee.get(0).getCouleur())) {
				// On fait disparaître la caisse
				caissePlateau[i] = null;
				// On réactualise les caisses a récupéré
				caisseARecuperee.remove(0);
				// On supprime la caisse du robot
				robot.charger();
				// On vérifie s'il reste des caisses à récupérées
				if (caisseARecuperee.isEmpty()) { // On a fini la partie
					return true;
				} else { // On a absorber une caisse
					setChanged();
					notifyObservers(this);
				}
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Vortex((Position) pos_vortex.clone());
	}
}
