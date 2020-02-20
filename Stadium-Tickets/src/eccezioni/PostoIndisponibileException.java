package eccezioni;


/**
 * Questa exception si verifica quando si seleziona un posto non disponibile.
 *  
 *
 */
public class PostoIndisponibileException extends Exception {

	/**
	 * Crea una nuova exception.
	 */
	public PostoIndisponibileException() {
		super();
	}

	/**
	 * Crea una nuova exception con un messaggio di errore.
	 */
	public PostoIndisponibileException(String message) {
		super(message);
	}

	
}
