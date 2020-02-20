package eccezioni;

/**
 * Questa exception si verifica quando si cerca di cancellare una prenotazione inesistente.
 *  
 *
 */
public class PrenotazioneInesistenteException extends Exception {

	/**
	 * Crea una nuova exception.
	 */
	public PrenotazioneInesistenteException() {
		super();
	}

	/**
	 * Crea una nuova exception con un messaggio di errore.
	 */
	public PrenotazioneInesistenteException(String message) {
		super(message);
	}
	

	
}
