package eccezioni;

/**
 * Questa exception si verifica quando viene inserito una password invalida.
 *  
 *
 */

public class IllegalPasswordException extends Exception {

	/**
	 * Crea una nuova exception.
	 */
	public IllegalPasswordException() {
		super();
	}

	/**
	 * Crea una nuova exception con un messaggio di errore.
	 */
	public IllegalPasswordException(String message) {
		super(message);
	}
	
}
