package eccezioni;

/**
 * Questa exception si verifica quando lo stato di un biglietto è invalido.
 *  
 *
 */
public class IllegalStatoBigliettoException extends Exception {

	/**
	 * Crea una nuova exception.
	 */
	public IllegalStatoBigliettoException() {
		super();
		
	}

	/**
	 * Crea una nuova exception con un messaggio di errore.
	 */
	public IllegalStatoBigliettoException(String message) {
		super(message);
	}

}
