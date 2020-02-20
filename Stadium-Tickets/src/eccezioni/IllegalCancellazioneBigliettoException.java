package eccezioni;

/**
 * Questa exception si verifica quando la cancellazione di un biglietto non va a buon fine.
 *  
 *
 */
public class IllegalCancellazioneBigliettoException extends Exception {

	/**
	 * Crea una nuova exception.
	 */
	public IllegalCancellazioneBigliettoException() {
		super();	
	}

	/**
	 * Crea una nuova exception con un messaggio di errore.
	 */
	public IllegalCancellazioneBigliettoException(String message) {
		super(message);
	}
		
}
