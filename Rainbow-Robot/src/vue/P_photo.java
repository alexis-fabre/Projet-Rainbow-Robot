/*
 * Photo.java							2 déc. 2015
 * IUT Info2 2015-2016
 */
package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * <p>
 * Représentation d'un panneau avec une image en fond.<br />
 * La photo sera redimensionné par la taille du panneau.
 * </p>
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public class P_photo extends JPanel {

	/**
	 * Image de fond du panneau
	 */
	private BufferedImage photo;

	/**
	 * Dimension du panneau
	 */
	private static final Dimension DIM_PHOTO = new Dimension(300, 250);

	/**
	 * On créé un panneau avec une image en fond et une bordure qui cadre
	 * l'image.
	 * 
	 * @param cheminPhoto
	 *            chemin absolu ou relatif de la photo
	 */
	public P_photo(String cheminPhoto) {
		super();
		try {
			photo = ImageIO.read(new File(cheminPhoto));
		} catch (IOException e) {
			System.out.println("Chemin Inexistant");
		}
		UtilitaireFenetre.setAllSize(this, DIM_PHOTO);
		super.setBorder(BorderFactory.createLineBorder(Color.black, 3));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D contexte = (Graphics2D) g;
		contexte.drawImage(photo, 0, 0, this.getWidth(), this.getHeight(), this);
	}

}
