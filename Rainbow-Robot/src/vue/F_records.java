/*
 * Reccords.java							é8 nov é015
 * IUT Info2 2015-é016
 */
package vue;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import metier.LigneUsernameScore;
import metier.OperationsFichier;
import evenement.ClicSouris;

/**
 * Fenetre contenant meilleurs scores (temps) des joueurs en fonction du niveau
 * effectué. Chaque onglet correspond à un niveau
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class F_records extends JFrame implements ChangementLangue {

	/**
	 * Généré automatiquement par Eclipse
	 */
	private static final long serialVersionUID = 7296802114481829665L;

	/** nombre de scores à afficher */
	private static final int NB_MAX_SCORES = 10;
	
	/** Nombre maximal de caractères que doit faire le pseudo du joueur */
	public static final int NB_MAX_CARACT_PSEUDO = 16;

	/** Titre de la fenêtre */
	private JLabel la_titre;

	/** Bouton pour retourner à l'accueil */
	private JButton bt_Retour;

	/** Onglets des différents niveaux */
	private JTabbedPane lesOnglets;

	/** tableau de JPanel pour chaque niveau */
	private JPanel[] lesPanneaux;

	/** label contenant le titre de la colonne joueur */
	private JLabel la_titreJoueur;

	/** label contenant le titre de la colonne temps */
	private JLabel la_titreTemps;

	/** label contenant le tableau a afficher (données) */
	private JLabel la_contenu;

	/** label du niveau affiché dans les onglets */
	private JLabel la_niveau;

	/** chemin d'accès au fichier record */
	public static final String FIC_RECORD = "./Ressource/highscore";
	
	/** extension obligatoire du fichier */
	public static final String FIC_EXTENSION = ".txt";

	/**
	 * Constructeur de la fenêtre Reccords
	 * 
	 * @param gestion
	 *            le contrôleur qui va controler cette vue = cible
	 *            evenementielle
	 */
	public F_records(ClicSouris gestion) {
		super();
		super.setSize(UtilitaireFenetre.DIM_FENETRE);
		// On rend la fenêtre non redimenssionable
		super.setResizable(false);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// panneau de la fenêtre
		Container contentPane = super.getContentPane();
		contentPane.setLayout(null);

		this.lesOnglets = new JTabbedPane(JTabbedPane.TOP); // onglets en hauts
		this.lesOnglets.setBounds(45, 45, 900, 550); // position et taille
		lesOnglets.setBackground(Color.CYAN);
		lesOnglets.setBorder(BorderFactory.createLineBorder(Color.black));
		this.lesPanneaux = new JPanel[NB_MAX_SCORES];

		// On ajoute le nom des composants en fonction de la langue choisie
		setLangue();
		String texte;
		// ajout du contenu du panneau pour chaque niveau
		for (int i = 0; i < lesPanneaux.length; i++) {
			texte = getLa_niveau().getText() + " " + (i + 1);
			// ajout des onglets et de leur panneaux
			lesOnglets.addTab(texte, ajoutContenu(lesPanneaux[i], i + 1));

		}

		// ajout évènement sur bouton retour
		getBt_Retour().addMouseListener(gestion);

		// On centre l'écran
		UtilitaireFenetre.centrerFenetre(this);

		contentPane.add(getLesOnglets()); // ajout des onglets
		contentPane.add(getBt_Retour());
	}

	/**
	 * Crée le panneau du niveau correspondant et lui ajoute le contenu
	 * 
	 * @param aAjouter
	 *            nouveau panneau contenant des scores
	 * @param indice niveau du mode story
	 * @return aAjouter le panneau avec le contenu
	 */
	public JPanel ajoutContenu(JPanel aAjouter, int indice) {

		aAjouter = new JPanel();
		aAjouter.setLayout(null);

		la_contenu = new JLabel();
		la_contenu.setBounds(10, 10, 850, 500);
		la_contenu.setFont(new Font("Georgia", Font.PLAIN, 30));
		la_contenu.setHorizontalAlignment(SwingConstants.CENTER);

		// récupération des données
		ArrayList<LigneUsernameScore> ligneUsernameScore = OperationsFichier
				.lectureFichierReccord(new File(FIC_RECORD
						+ Integer.toString(indice) + FIC_EXTENSION));

		String scores;
		scores = "<html>";
		// titre des colonnes du tableau
		scores += "<table>"
				+ "<tr><th style=\"text-align:center; \">No. |</th><th style=\"text-align:center; margin-left: 10px; margin-right: 10px;\">"
				+ getLa_titreJoueur().getText()
				+ "</th><th style=\"text-align:center;\">| "
				+ getLa_titreTemps().getText() + "</th></tr><hr/>";
		// contenu de chaque ligne
		for (int i = 0; i < ligneUsernameScore.size(); i++) {
			scores += "<tr><td style=\"text-align:center;\">"
					+ (i + 1)
					+ ".</td><td style=\"text-align:center; margin-left: 10px; margin-right: 10px;\">"
					+ ligneUsernameScore.get(i).getUsername()
					+ "</td><td style=\"text-align:center;\">"
					+ ligneUsernameScore.get(i).getScore() + "</td></tr><br>";
		}
		scores += "</table></html>";

		la_contenu.setText(scores);

		aAjouter.add(la_contenu);
		return aAjouter;
	}

	/**
	 * Méthode permettant de savoir si un score est dans le top 10 du niveau et
	 * à quelle place
	 * 
	 * @param score
	 *            Score à comparer
	 * @param niveau niveau du mode story
	 * @return la place dans le classement -1 si ce n'est pas un record
	 */
	public static int estRecord(String score, int niveau) {
		String ligne;
		String carMinute, carSeconde;
		BufferedReader fichier;
		int[][] records = new int[10][2];
		int minute, seconde, compteur = 0;
		StringTokenizer tokenName;
		StringTokenizer tokenScore;
		try {
			fichier = new BufferedReader(new FileReader(FIC_RECORD
					+ Integer.toString(niveau) + FIC_EXTENSION));
			// Lecture ligne par ligne
			try {
				while ((ligne = fichier.readLine()) != null) {
					// Pour séparer les pseudos et les temps
					tokenName = new StringTokenizer(ligne, "#");
					// On "ignore" le pseudo
					tokenName.nextToken();
					// Séparation des minutes et secondes
					tokenScore = new StringTokenizer(tokenName.nextToken(), ":");
					// Sauvegarde des minutes
					records[compteur][0] = Integer.parseInt(tokenScore
							.nextToken().trim());
					// Sauvegarde des secondes
					records[compteur][1] = Integer.parseInt(tokenScore
							.nextToken().trim());
					compteur++;
				}
			} catch (IOException erreur) {
				System.out
						.println("Problème avec la lecture du fichier des records");
			} finally {
				try {
					fichier.close(); // fermeture du fichier
				} catch (IOException erreur) {
					System.out
							.println("Problème avec la fermeture du fichier record");
				}
			}
		} catch (FileNotFoundException erreur) {
			System.out.println("Fichier records non trouvé");
		}

		// On découpe pour récupérer séparemment les minutes et les secondes
		// su score que l'on souhaite analyser
		String[] tabScore = score.split(" : ");
		carMinute = tabScore[0];
		carSeconde = tabScore[1];

		// On parse les string en int pour pouvoir les comparer
		minute = Integer.parseInt(carMinute);
		seconde = Integer.parseInt(carSeconde);

		// Parcours du tableau des records
		for (int i = 0; i <= records.length; i++) {
			// Si le temps le score du joueur courant est inférieur on retourne
			// son classement
			if (minute * 60 + seconde <= records[i][0] * 60 + records[i][1]
					|| records[i][0] * 60 + records[i][1] == 0) {
				// Retourne le classement si le score fait parti du top 10
				return i + 1;
			}
		}
		// Cas où le score ne fait pas parti du top 10
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see vue.ChangementLangue#setLangue()
	 */
	@Override
	public void setLangue() {
		// On actualise la langue
		String[] traductionReccords = ChoixLangue.getChoixLangue()
				.getRecords();
		this.setTitle(traductionReccords[0]);
		getLa_titreJoueur().setText(traductionReccords[1]);
		getLa_titreTemps().setText(traductionReccords[2]);
		getLa_niveau().setText(traductionReccords[3]);
		getBt_Retour().setText(traductionReccords[4]);
	}

	/**
	 * @return le JLabel la_titre titre de la fenêtre
	 */
	public JLabel getLa_titre() {
		if (la_titre == null) {
			la_titre = new JLabel();
			la_titre.setPreferredSize(UtilitaireFenetre.DIM_COMPOSANT_PRINCIPAL);
		}
		return la_titre;
	}

	/**
	 * @return le JButton bt_Retour qui lance la fenêtre F_Accueil
	 */
	public JButton getBt_Retour() {
		if (bt_Retour == null) {
			bt_Retour = new JButton();
			// On définit une taille et on place le composant
			bt_Retour.setBounds(10, UtilitaireFenetre.DIM_FENETRE.height
					- (UtilitaireFenetre.DIM_COMPOSANT_SECONDAIRE.height + 35),
					UtilitaireFenetre.DIM_COMPOSANT_SECONDAIRE.width,
					UtilitaireFenetre.DIM_COMPOSANT_SECONDAIRE.height);
		}
		return bt_Retour;
	}

	/**
	 * @return le JLabel la_titreJoueur le nom du joueur
	 */
	public JLabel getLa_titreJoueur() {
		if (la_titreJoueur == null) {
			la_titreJoueur = new JLabel();
			la_titreJoueur.setFont(new Font("Georgia", Font.PLAIN, 50));
			la_titreJoueur.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return la_titreJoueur;
	}

	/**
	 * @return le JLabel la_titreTemps contenant le temps que le joueur a mis
	 *         pour terminer le niveau.
	 */
	public JLabel getLa_titreTemps() {
		if (la_titreTemps == null) {
			la_titreTemps = new JLabel();
			la_titreJoueur.setFont(new Font("Georgia", Font.PLAIN, 50));
			la_titreJoueur.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return la_titreTemps;
	}

	/**
	 * @return JTabbledPane lesOnglets contenant la navigation entre les onglets
	 */
	public JTabbedPane getLesOnglets() {
		return lesOnglets;
	}

	/**
	 * @return le JLabel la_niveau contenant le numéro du niveau
	 */
	public JLabel getLa_niveau() {
		if (la_niveau == null) {
			la_niveau = new JLabel();
		}
		return la_niveau;
	}

}