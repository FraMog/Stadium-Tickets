package eccezioni;


/**
 * Questa exception si verifica quando viene inserito un cognome invalido.
 *  
 *
 */
public class IllegalCognomeException extends Exception{

	/**
	 * Crea una nuova exception.
	 */
	public IllegalCognomeException() {
		super();
	}

	/**
	 * Crea una nuova exception con un messaggio di errore.
	 */
	public IllegalCognomeException(String message) {
		super(message);
	}

}
