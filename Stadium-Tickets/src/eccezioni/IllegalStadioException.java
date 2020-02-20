package eccezioni;

/**
 * Questa exception si verifica quando lo stadio selezionato è invalido.
 *  
 *
 */

public class IllegalStadioException extends Exception{

	/**
	 * Crea una nuova exception.
	 */
	public IllegalStadioException() {
		super();
	}

	/**
	 * Crea una nuova exception con un messaggio di errore.
	 */
	public IllegalStadioException(String message) {
		super(message);
	}
	
}
