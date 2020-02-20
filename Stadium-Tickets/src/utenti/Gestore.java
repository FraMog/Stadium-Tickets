package utenti;
import java.io.Serializable;
import java.util.GregorianCalendar;
import eccezioni.*;

/**
 * Questa classe modella il concetto di gestore.
 * @author Mogavero
 *
 */
public class Gestore extends Utente implements Serializable {
	

	private static final long serialVersionUID = -8038733527772424743L;
	/**
	 * Crea un nuovo gestore.
	 * @param nome Il nome dell'utente.
	 * @param cognome Il cognome dell'utente.
	 * @param username L'username dell'utente.
	 * @param password La password dell'utente.
	 * @param dataNascita La data di nascita dell'utente.
	 * @throws IllegalDataException Se la data è nulla.
	 * @throws IllegalNomeException Se il nome è vuoto o è scorretto.
	 * @throws IllegalCognomeException Se il nome è vuoto o è scorretto. 
	 * @throws IllegalPasswordException Se la password non contiene numeri e lettere.
	 * @throws IllegalUsernameException Se l'username è vuoto.
	 */
	public Gestore(String nome, String cognome, String username, String password, GregorianCalendar dataNascita) throws  IllegalDataException, IllegalNomeException, IllegalCognomeException, IllegalPasswordException, IllegalUsernameException{
		super(nome, cognome, username, password, dataNascita);
	}

}
