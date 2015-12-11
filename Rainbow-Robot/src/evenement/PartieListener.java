/*
 * PartieListener.java							11 déc. 2015
 * IUT Info2 2015-2016
 */
package evenement;


/**
 * Interface permettant d'écouter lorsqu'une partie est finie et effectué des
 * actions par la suite.
 * 
 * @author Rainbow Robot
 * @version 1.0
 */
public interface PartieListener {

	/**
	 * Permet de détecter la fin d'une partie.
	 */
	public void partieFinie();
}
