package eccezioni;
/**
 * Questa exception si verifica quando viene inserito un nome stadio illegale.
 *  
 *
 */
public class IllegalNomeStadioException extends Exception {

	/**
	 * Crea una nuova exception.
	 */
	public IllegalNomeStadioException() {
		super();
	
	}

	/**
	 * Crea una nuova exception con un messaggio di errore.
	 */
	public IllegalNomeStadioException(String message) {
		super(message);
		
	}

	
}
