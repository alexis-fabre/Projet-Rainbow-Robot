/*
 * ChangementLangue.java							30 nov. 2015
 * IUT Info2 2015-2016
 */
package vue;

/**
 * Interface qui permet de savoir si une classe dispose d'une fonction
 * permettant de changer la langue de tous ses composants.
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public interface ChangementLangue {

	/**
	 * <p>
	 * Permet de raffraichir les objets en cas de changement de langue.<br />
	 * public abstract void setLangue().
	 * </p>
	 */
	abstract void setLangue();
}
