
package eccezioni;

/**
 * Questa exception si verifica quando l'username di un utente � invalido.
 *  
 *
 */
public class IllegalUsernameException extends Exception {

	/**
	 * Crea una nuova exception.
	 */
	public IllegalUsernameException() {
		super();
		
	}

	/**
	 * Crea una nuova exception con un messaggio di errore.
	 */
	public IllegalUsernameException(String message) {
		super(message);
		
	}
	
}
