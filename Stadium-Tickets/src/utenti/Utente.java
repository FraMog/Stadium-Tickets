package utenti;
import java.io.Serializable;
import java.util.GregorianCalendar;
import eccezioni.*;
/**
 * Questa classe modella il concetto di utente: un utente ha un nome, un cognome, un username, una password, ed una data di Nascita.
 * @author Mogavero
 *
 */
public abstract class Utente implements Serializable{
	
	private static final long serialVersionUID = 7196455566595062395L;
	/**
	 * Definisce un utente.
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
	public Utente(String nome, String cognome, String username, String password, GregorianCalendar dataNascita) throws  IllegalDataException, IllegalNomeException, IllegalCognomeException, IllegalPasswordException, IllegalUsernameException{
		if (nome.length()==0) throw new IllegalNomeException("Il nome non può essere vuoto!");
		if (cognome.length()==0) throw new IllegalCognomeException("Il cognome non può essere vuoto!");
		if (username.length()==0) throw new IllegalUsernameException("L'username non può essere vuoto!");
		if (password.length()==0) throw new IllegalPasswordException("La password non può essere vuota!");
		if (dataNascita==null) throw new IllegalDataException ("La data non può essere vuota!");
		
		
		boolean lettera=false;
		int i=0;
		while (i<nome.length() && ((Character.isLetter(nome.charAt(i))||nome.charAt(i)==' '|| nome.charAt(i)=='\''))){
			if (Character.isLetter(nome.charAt(i))) lettera=true;
			i++;
		}
			if (i<nome.length()|| !lettera) throw new IllegalNomeException ("Il nome deve contenere solo caratteri alfabetici o spazi!");
		
			
		i=0;
		lettera=false;
		
		while (i<cognome.length() && ((Character.isLetter(cognome.charAt(i))||cognome.charAt(i)==' '||cognome.charAt(i)=='\''))) {
			if (Character.isLetter(cognome.charAt(i))) lettera=true;
			i++;
		}
		
		if (i<cognome.length()|| !lettera) throw new IllegalCognomeException ("Il cognome deve contenere solo caratteri alfabetici o spazi!");
		
		
		
		lettera= false;
		boolean numero= false;
		i=0;
		while (i< password.length()){
			if (Character.isLetter(password.charAt(i))) lettera=true;
			else if (Character.isDigit(password.charAt(i))) numero=true;
			i++;
		}
		if (!lettera || !numero) throw new IllegalPasswordException ("La password deve contenenere numeri e lettere");
		
		this.dataNascita=dataNascita;
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.password = password;
	}
	

	
	/**
	 * Restituisce il nome dell'utente.
	 * @return Il nome dell'utente.
	 */
	public String getNome() {
		return nome;
	}


	
	/**
	 * Restituisce il cognome dell'utente.
	 * @return Il cognome dell'utente.
	 */
	public String getCognome() {
		return cognome;
	}


	/**
	 * Restituisce l'username dell'utente.
	 * @return L'username dell'utente.
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * Controlla se le credenziali di accesso passate in input corrispondono con quelle dell'utente.
	 * @param username L'username da verificare.
	 * @param password La password da verificare.
	 * @return TRUE se le credenziali corrispondono FALSE altrimenti.
	 */
	public boolean controllaCredenzialiAccesso(String username, String password){
		if (password.equals(this.password) && username.equals(this.username)) return true;
		else return false;
		
	}
	
	
	/**
	 * Confronta il parametro implicito con un altro oggetto passato come parametro.
	 * @return TRUE se i due oggetti sono uguali, FALSE se sono diversi.
	 */
	public boolean equals (Object obj){
		if (obj==null) return false;
		else if (getClass()!= obj.getClass()) return false;
		Utente altroUtente= (Utente) obj;
		return nome.equals(altroUtente.nome) && cognome.equals(altroUtente.cognome) && username.equals(altroUtente.username) && password.equals(altroUtente.password) && dataNascita.equals(altroUtente.dataNascita);
	}

	private String nome, cognome, username, password;
	private GregorianCalendar dataNascita;
}
