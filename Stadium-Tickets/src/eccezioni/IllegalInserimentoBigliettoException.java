package eccezioni;

/**
 * Questa exception si verifica quando l'inserimento di un biglietto non va a buon fine.
 *  
 *
 */
public class IllegalInserimentoBigliettoException extends Exception{

	/**
	 * Crea una nuova exception.
	 */
	public IllegalInserimentoBigliettoException() {
		super();
	}

	/**
	 * Crea una nuova exception con un messaggio di errore.
	 */
	public IllegalInserimentoBigliettoException(String message) {
		super(message);
	}

	
}
