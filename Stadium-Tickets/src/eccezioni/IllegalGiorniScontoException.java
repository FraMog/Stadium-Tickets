package eccezioni;

/**
 * Questa exception si verifica quando i giorni di uno sconto per giorni sono invalidi.
 *  
 *
 */
public class IllegalGiorniScontoException extends Exception{

	/**
	 * Crea una nuova exception.
	 */
	public IllegalGiorniScontoException() {
		super();
		
	}
	
	/**
	 * Crea una nuova exception con un messaggio di errore.
	 */
	public IllegalGiorniScontoException(String message) {
		super(message);
		
	}
	
}
