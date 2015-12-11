/*
 * PartieEvent.java							11 déc. 2015
 * IUT Info2 2015-2016
 */
package evenement;

import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import metier.JeuRainbow;
import vue.ChoixLangue;
import vue.F_accueil;
import vue.FenetreJeu;

/**
 * TODO Expliquer le fonctionnement de la classe
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class PartieEvent implements PartieListener {

	/**
	 * Permet de revenir à la page d'accueil tout en gardant la référence de la
	 * classe ClicSouris
	 */
	ClicSouris evenement;

	/**
	 * Référence de la partie métier
	 */
	JeuRainbow metier;

	/**
	 * Référence de la partie vue
	 */
	JFrame vue;

	/**
	 * TODO Expliquer le fonctionnement du constructeur
	 * 
	 * @param evenement
	 * @param metier
	 * @param jeu
	 */
	public PartieEvent(ClicSouris evenement, JeuRainbow metier) {
		super();
		this.evenement = evenement;
		this.metier = metier;
	}

	public void setVue(FenetreJeu nouvelleVue) {
		vue = nouvelleVue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see evenement.PartieListener#partieFinie()
	 */
	@Override
	public void partieFinie() {
		if (metier.getPartieCourante().isFinished()
				&& vue instanceof FenetreJeu) {
			FenetreJeu fenetre = (FenetreJeu) vue;
			String[] traductionFinPartie = ChoixLangue.getChoixLangue()
					.getFinPartie();
			String[] traductionBouton = Arrays.copyOfRange(traductionFinPartie,
					2, traductionFinPartie.length);
			int retour = JOptionPane.showOptionDialog(null,
					traductionFinPartie[0], traductionFinPartie[1],
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, traductionBouton, traductionBouton[0]);
			switch (retour) {
			case 0: // Recommencer
				fenetre.getPartieDessinable().setJeuRainbowRobot(
						metier.getPartieCourante());
				break;
			case 1: // Partie suivante
				metier.setNiveauSuivant();
				fenetre.getPartieDessinable().setJeuRainbowRobot(
						metier.getPartieCourante());
				break;
			case 2: // Quitter
				// On revient à l'accueil
				F_accueil fenetreAccueil = new F_accueil(evenement);
				vue.setVisible(false);
				fenetreAccueil.setVisible(true);
				break;
			}
		}
	}
}
