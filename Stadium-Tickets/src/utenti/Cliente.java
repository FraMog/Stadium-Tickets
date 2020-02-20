package utenti;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import eccezioni.*;
import partite.*;
import partite.Biglietto.StatiPossibili;

/**
 * Questa classe modella il concetto di cliente: un cliente oltre a possedere le stesse caratteristiche di un utente
 * ha anche una fascia di appartenza calcolata in base alla data di nascita e degli elenchi di:
 * biglietti acquistati: biglietti che ha acquistato (sia con precedente prenotazione che senza).
 * prenotazioni attive: biglietti che ha prenotato le cui prenotazioni sono ancora attive,
 * prenotazioni scadute: biglietti che ha prenotato le cui prenotazioni sono scadute.
 * prenotazioni cancellate: biglietti che ha prenotato e poi cancellato la prenotazione.
 * prenotazioni acquistate: biglietti che ha prenotato e successivamente ha acquistato la prenotazione.
 * @author Mogavero
 *
 */
public class Cliente extends Utente implements Serializable {
	
	private static final long serialVersionUID = 2934580367548088267L;
	
	public Cliente(String nome, String cognome, String username, String password, GregorianCalendar dataNascita) throws IllegalDataException, IllegalNomeException, IllegalCognomeException, IllegalPasswordException, IllegalUsernameException{
		super(nome, cognome, username, password, dataNascita);
		prenotazioniAttive=new ArrayList <Biglietto>();
		bigliettiAcquistati= new ArrayList <Biglietto>();
		prenotazioniScadute=new ArrayList <Biglietto>();
		prenotazioniCancellate= new ArrayList<Biglietto>();
		prenotazioniAcquistate= new ArrayList <Biglietto>();
		
		int anno = dataNascita.get(GregorianCalendar.YEAR);
		if(anno>=FINE_ETA_BAMBINI) categoriaCliente=Categoria.Bambini;
		else if (anno>=FINE_ETA_STUDENTI)categoriaCliente=Categoria.Studenti;
		else if (anno>=FINE_ETA_ADULTI)categoriaCliente=Categoria.Adulti;
		else categoriaCliente=Categoria.Pensionati;
		
	}

	
	/**
	 * Restituisce la categoria di appartenenza del cliente.
	 * @return La categoria di appartenenza del cliente.
	 */
	public Categoria getCategoriaCliente(){
		return categoriaCliente;
	}
	
	
	/**
	 * Restituisce l'elenco dei biglietti acquistati dal cliente.
	 * @return L'elenco dei biglietti acquistati dal cliente.
	 */
	public ArrayList<Biglietto> getBigliettiAcquistati() {
		return bigliettiAcquistati;
	}

	/**
	 * Restituisce l'elenco delle prenotazioni ancora attive del cliente.
	 * @return L'elenco delle prenotazioni ancora attive del cliente.
	 */
	public ArrayList<Biglietto> getPrenotazioniAttive() {
		return prenotazioniAttive;
	}
	
	
	/**
	 * Restituisce l'elenco delle prenotazioni poi cancellate dal cliente.
	 * @return L'elenco delle prenotazioni poi cancellate dal cliente.
	 */
	public ArrayList<Biglietto> getPrenotazioniCancellate() {
		return prenotazioniCancellate;
	}


	/**
	 * Restituisce l'elenco delle prenotazioni scadute del cliente.
	 * @return L'elenco delle prenotazioni scadute del cliente.
	 */
	public ArrayList<Biglietto> getPrenotazioniScadute() {
		return prenotazioniScadute;
	}

	
	/**
	 * Restituisce l'elenco dei biglietti prenotati e poi acquistati dal cliente.
	 * @return L'elenco dei biglietti prenotati e poi acquistati dal cliente.
	 */
	public ArrayList<Biglietto> getPrenotazioniAcquistate() {
		return prenotazioniAcquistate;
	}


	/**
	 * Rimuove il biglietto passato in input dalle prenotazioni attive del cliente.
	 * @param prenotazioneBiglietto Il biglietto da eliminare.
	 * @throws PrenotazioneInesistenteException Se la prenotazione che si sta tentando di eliminare non è presente fra le prenotazioni attive del cliente.
	 */
	public void rimuoviPrenotazioneAttiva (Biglietto prenotazioneBiglietto) throws PrenotazioneInesistenteException{
		int i=0;
		while (i<prenotazioniAttive.size() && !prenotazioneBiglietto.equals(prenotazioniAttive.get(i)))
			i++;
		if (i==prenotazioniAttive.size()) throw new PrenotazioneInesistenteException("La prenotazione che si sta tentando di cancellare per il cliente non esiste");
		prenotazioniAttive.remove(i);
	}
	
	
	/**
	 * Aggiunge il biglietto passato come parametro all'elenco dei biglietti acquistati dal cliente.
	 * @param biglietto Il biglietto da aggiungere.
	 */
	public void addBigliettoAcquistato(Biglietto biglietto){
		bigliettiAcquistati.add(biglietto);
		
	}
	
	/**
	 * Aggiunge il biglietto passato come parametro all'elenco delle prenotazioni attive del cliente.
	 * @param biglietto Il biglietto da aggiungere.
	 */
	public void addPrenotazioneAttiva(Biglietto prenotazioneBiglietto){
		prenotazioniAttive.add(prenotazioneBiglietto);
		
	}
	
	
	/**
	 * Aggiunge il biglietto passato come parametro all'elenco delle prenotazioni scadute del cliente.
	 * @param biglietto Il biglietto da aggiungere.
	 */
	public void addPrenotazioneScaduta(Biglietto prenotazioneBiglietto){
		prenotazioniScadute.add(prenotazioneBiglietto);
		
	}
	
	/**
	 * Aggiunge il biglietto passato come parametro all'elenco delle prenotazioni cancellate del cliente.
	 * @param biglietto Il biglietto da aggiungere.
	 */
	public void addPrenotazioneCancellata(Biglietto prenotazioneBiglietto){
		prenotazioniCancellate.add(prenotazioneBiglietto);
	}

	
	/**
	 * Aggiunge il biglietto passato come parametro all'elenco delle prenotazioni acquistate del cliente.
	 * @param biglietto Il biglietto da aggiungere.
	 */
	public void addPrenotazioneAcquistata(Biglietto prenotazioneBiglietto){
		prenotazioniAcquistate.add(prenotazioneBiglietto);
		
	}
	
	
	/** 
	 * Per ogni biglietto appartenente all'elenco delle prenotazioni attive del cliente controlla se la prenotazione sia ancora attiva,
	 * se lo è aggiunge tale biglietto nel nuovo elenco di prenotazioni attive altrimenti aggiunge il biglietto tra le prenotazioni
	 * scadute del cliente e rimuove tale biglietto dai biglietti della partita al quale si riferiva.
	 * @throws IllegalStatoBigliettoException Se lo stato del biglietto non rientra tra gli stati possibili.
	 * @throws IllegalPrenotazioneException Se lo stato del biglietto viene impostato su prenotato ma la prenotazione non può avvenire.
	 * @throws IllegalCancellazioneBigliettoException Se il settore indicato non rientra fra i settori possibili della partita.
	 */
	public void controllaValiditaPrenotazioniAttive() throws IllegalStatoBigliettoException,IllegalPrenotazioneException,IllegalCancellazioneBigliettoException{
		ArrayList <Biglietto> nuovePrenotazioniAttive= new ArrayList<Biglietto>();
		for (Biglietto biglietto: prenotazioniAttive){
		if (!biglietto.checkValiditaPrenotazione()){ //smisto le prenotazioni tra quelle ancora attive e quelle scadute
				addPrenotazioneScaduta(biglietto);
				biglietto.setStato(StatiPossibili.SCADUTO, new GregorianCalendar());
				biglietto.getPartita().rimuoviBiglietto(biglietto.getSettore(), biglietto.getRiga(), biglietto.getColonna());
			}
			else {
				nuovePrenotazioniAttive.add(biglietto);
			}
		}
		prenotazioniAttive= nuovePrenotazioniAttive;
	}
	
	

	
	
	/**
	 * Confronta il parametro implicito con un altro oggetto passato come parametro.
	 * @return TRUE se i due oggetti sono uguali, FALSE se sono diversi.
	 */
	public boolean equals(Object obj){
		if (!super.equals(obj)) return false;
		Cliente altroCliente= (Cliente) obj;
		return categoriaCliente==altroCliente.categoriaCliente;
		
	}


	public static final int FINE_ETA_BAMBINI=2005, FINE_ETA_STUDENTI= 1990, FINE_ETA_ADULTI=1950;
	public static enum Categoria  {Bambini,Studenti, Adulti, Pensionati}
	
	
	private ArrayList <Biglietto> bigliettiAcquistati,prenotazioniAttive, prenotazioniScadute,prenotazioniCancellate,prenotazioniAcquistate;
	private Categoria categoriaCliente;
	
}
