/*
 * PartieDessinable.java							6 déc. 2015
 * IUT Info2 2015-2016
 */
package vue;

import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import evenement.ToucheClavier;

/**
 * Panneau qui dessine et contrôle une partie de jeu Rainbow Robot. On dessine
 * les composants d'une partie et on détecte les actions faites par
 * l'utilisateur sur le clavier.
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class PartieDessinable extends JPanel implements Observer {

	/**
	 * TODO Expliquer le fonctionnement du constructeur
	 * @param gestion
	 */
	public PartieDessinable(ToucheClavier gestion) {
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object aRedessiner) {
		// TODO Auto-generated method stub
		
	}
	
	
}
