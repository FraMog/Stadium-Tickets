
package eccezioni;

/**
 * Questa exception si verifica quando viene inserita una data invalida.
 *  
 *
 */

public class IllegalDataException extends Exception {

	/**
	 * Crea una nuova exception.
	 */
	public IllegalDataException() {
		super();
	}

	/**
	 * Crea una nuova exception con un messaggio di errore.
	 */
	public IllegalDataException(String message) {
		super(message);
		
	}

	
}
