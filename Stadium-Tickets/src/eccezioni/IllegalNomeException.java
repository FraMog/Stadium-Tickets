package eccezioni;

/**
 * Questa exception si verifica quando viene inserito un nome invalido.
 *  
 *
 */
public class IllegalNomeException extends Exception {

	/**
	 * Crea una nuova exception.
	 */
	public IllegalNomeException() {
		super();
	}

	/**
	 * Crea una nuova exception con un messaggio di errore.
	 */
	public IllegalNomeException(String message) {
		super(message);
	}

}
