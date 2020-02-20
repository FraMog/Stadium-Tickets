
package eccezioni;

/**
 * Questa exception si verifica quando viene inserito un nome squadra illegale.
 *  
 *
 */

public class IllegalNomeSquadraException extends Exception {

	/**
	 * Crea una nuova exception.
	 */
	public IllegalNomeSquadraException() {
		super();
	}

	/**
	 * Crea una nuova exception con un messaggio di errore.
	 */
	public IllegalNomeSquadraException(String message) {
		super(message);
	}

}
