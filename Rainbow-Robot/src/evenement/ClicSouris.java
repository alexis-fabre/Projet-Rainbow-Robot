/*
 * ClicSouris.java							28 nov 2015
 * IUT Info2 2015-2016
 */

package evenement;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import vue.APropos;
import vue.F_accueil;
import vue.ChoixLangue;
import vue.ChoixMode;
import vue.Reccords;

/**
 * Controleur lors d'un clic de la souris. Utilis� surtout pour naviguer entre
 * les pages.
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class ClicSouris implements MouseListener {

	/**
	 * On initialise le constructeur par d�faut.
	 */
	public ClicSouris() {
	}

	/**
	 * D�tection des clics sur une fen�tre. On peut traiter qu'une seule fen�tre
	 * car il n'y aura jamais deux fen�tres affich�es en m�me temps.
	 */
	private JFrame fenetre;

	/*
	 * Quand on presse la souris sur un composant (mais sans le relacher la
	 * pression) (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * Quand la souris quitte un composant. (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * Quand la souris sort d'un composant (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * Quand la souris presse et relache le clic sur un m�me composant un
	 * composant (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
		// On v�rifie si la fen�tre que l'on contr�le est bien la fen�tre
		// d'accueil
		if (fenetre instanceof F_accueil) {
			F_accueil fenetreAccueil = (F_accueil) fenetre;

			// On v�rifie quel bouton a �t� utilis�
			// Bouton Jouer
			if (e.getSource() == fenetreAccueil.getBt_Jouer()) {
				// On lance la fen�tre Jouer ChoixMode.java
				ChoixMode nouvelleFenetre = new ChoixMode(this);
				fenetre.setVisible(false);
				nouvelleFenetre.setVisible(true);
				setFenetre(nouvelleFenetre);
			}

			// Bouton Reccords
			if (e.getSource() == fenetreAccueil.getBt_Reccords()) {
				// On lance la page des reccords Reccords.java
				Reccords nouvelleFenetre = new Reccords(this);
				fenetre.setVisible(false);
				nouvelleFenetre.setVisible(true);
				setFenetre(nouvelleFenetre);
			}

			// Bouton Langue
			if (e.getSource() == fenetreAccueil.getBt_Langue()) {
				// On lance la page des langues ChoixLangue.java
				ChoixLangue nouvelleFenetre = new ChoixLangue(this);
				fenetre.setVisible(false);
				nouvelleFenetre.setVisible(true);
				setFenetre(nouvelleFenetre);
			}

			// Bouton A Propos
			if (e.getSource() == fenetreAccueil.getBt_Apropos()) {
				// On lance la page d'a propos A Propos.java
				APropos nouvelleFenetre = new APropos(this);
				fenetre.setVisible(false);
				nouvelleFenetre.setVisible(true);
				setFenetre(nouvelleFenetre);
			}

			// Bouton Quitter
			if (e.getSource() == fenetreAccueil.getBt_Quitter()) {
				// On quitte l'application
				// On demande si l'utilisateur veux vraiment quitter ou sil a
				// commit une erreur
				int resultat = JOptionPane.showConfirmDialog(fenetreAccueil,
						"Voulez-vous quitter l'application ?",
						"Quitter l'application", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (resultat == JOptionPane.OK_OPTION) {
					// On quitte l'application
					fenetre.setVisible(false);
					// On arr�te le d�roulement logique de l'application
					System.exit(0);
				}

			}

		}

	}

	/*
	 * Quand la souris est relach� sur un composant (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param nouvelleFenetre
	 *            la nouvelle fen�tre � controller
	 */
	public void setFenetre(JFrame nouvelleFenetre) {
		this.fenetre = nouvelleFenetre;
	}

}
