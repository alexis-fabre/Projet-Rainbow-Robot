/*
 * CaisseARecuperer.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package metier;
import java.awt.Color;
import java.util.ArrayList;


/**
 *
 * @author Rainbow Robot
 */
public class CaisseARecuperer {
	
	
	private Color couleur;
	
	/** Liste des caisses à récuperer par le joueur */
	ArrayList<Caisse> caisseAAttraper = new ArrayList<Caisse>();
	
	public CaisseARecuperer(){
		caisseAAttraper.add(new Caisse(couleur));
	}
}
