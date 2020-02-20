
package eccezioni;

/**
 * Questa exception si verifica quando la prenotazione di un biglietto non va a buon fine o causa inconsistenze.
 *  
 *
 */

public class IllegalPrenotazioneException extends Exception {

	/**
	 * Crea una nuova exception.
	 */
	public IllegalPrenotazioneException() {
		super();
	}
	
	/**
	 * Crea una nuova exception con un messaggio di errore.
	 */
	
	public IllegalPrenotazioneException(String message) {
		super(message);
	}
	
}
