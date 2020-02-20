package partite;

import java.io.Serializable;
import java.util.GregorianCalendar;
import eccezioni.*;
import utenti.*;
/**
 * Questa classe modella il concetto di biglietto:
 * Un biglietto ha un costo, una partita a cui si riferisce, un cliente che lo prenota o lo acquista,
 * all'interno dello stadio esso è caratterizzato da un posto: il posto si identifica con un settore
 * dello stadio, un numero di riga e di colonna. Un biglietto ha inoltre uno stato che puo essere:
 * ACQUISTATO se il biglietto è stato acquistato dal cliente, PRENOTATO se il biglietto è stato prenotato
 * dal cliente, SCADUTO se la prenotazione del biglietto è scaduta, CANCELLATO se il cliente ha cancellato
 * la prenotazione che aveva precedentemente effettuato.
 * @author Mogavero
 *
 */
public class Biglietto implements Serializable{


	private static final long serialVersionUID = -5395633463102412534L;
	
	
	/**
	 * Crea un nuovo biglietto.
	 * @param costo Il costo del biglietto.
	 * @param partita La partita alla quale il biglietto è riferito.
	 * @param cliente Il cliente che ha prenotato/acquistato il biglietto.
	 * @param settore Il settore all'interno dello stadio di riferimento.
	 * @param riga La riga del biglietto.
	 * @param colonna La colonna del biglietto.
	 */
	public Biglietto(double costo, Partita partita, Cliente cliente, Partita.SettoriPossibili settore, int riga, int colonna) {
		this.costo = costo;
		this.partita = partita;
		this.cliente= cliente;
		this.settore= settore;
		this.riga= riga;
		this.colonna= colonna;
		
	}
	
	
	/**
	 * Nel caso lo stato del biglietto sia prenotato questo metodo controlla se sia possibile o meno prenotare questo biglietto.
	 * @throws IllegalPrenotazioneException Nel caso in cui la prenotazione non sia possibile.
	 */
	private void controllaPossibilitaPrenotazione() throws IllegalPrenotazioneException{
		GregorianCalendar dataCorrente, dataPartita;
		dataCorrente= new GregorianCalendar();
		dataPartita= partita.getData();
		
		long millisecondiCorrenti= dataCorrente.getTimeInMillis();
		long millisecondiPartita= dataPartita.getTimeInMillis();
		
		if (millisecondiCorrenti+MILLISECONDI_IN_12_ORE>millisecondiPartita)
			throw new IllegalPrenotazioneException ("Impossibile prenotare partite a meno di 12 ore dal loro inizio");
	}
	
	/**
	 * Restituisce il costo del biglietto.
	 * @return Il costo del biglietto.
	 */
	public double getCosto() {
		return costo;
	}
	
	/**
	 * Restituisce la partita del biglietto.
	 * @return La partita del biglietto.
	 */
	public Partita getPartita(){
		return partita;
	}

	/**
	 * Restituisce il cliente del biglietto.
	 * @return Il cliente del biglietto.
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * Restituisce il settore del biglietto.
	 * @return Il settore del biglietto.
	 */
	public Partita.SettoriPossibili getSettore() {
		return settore;
	}

	/**
	 * Restituisce la riga del biglietto.
	 * @return La riga del biglietto.
	 */
	public int getRiga() {
		return riga;
	}
	
	/**
	 * Restituisce la colonna del biglietto.
	 * @return La colonna del biglietto.
	 */
	public int getColonna() {
		
		return colonna;
	}

	/**
	 * Restituisce lo stato del biglietto.
	 * @return Lo stato del biglietto.
	 */
	public StatiPossibili getStato() {
		return stato;
	}
	
	/**
	 * Modifica il cliente del biglietto.
	 * @param cliente Il  cliente del biglietto.
	 */
	public void setCliente (Cliente cliente){
		this.cliente=cliente;
	}
	
	/**
	 * Modifica la partita del biglietto.
	 * @param cliente La partita del biglietto.
	 */
	public void setPartita(Partita partita) {
		this.partita=partita;
		
	}
	
	
	/**
	 * Setta lo stato del biglietto.
	 * @param stato Lo stato del biglietto.
	 * @param dataMovimento La data in cui è avvenuto il cambiamento/definizione dello stato del biglietto.
	 * @throws IllegalStatoBigliettoException Se lo stato del biglietto non rientra fra gli stati possibili.
	 * @throws IllegalPrenotazioneException Se lo stato che si tenta di settare è prenotato ma non si può più prenotare il biglietto.
	 */
	public void setStato (StatiPossibili stato, GregorianCalendar dataMovimento) throws IllegalStatoBigliettoException, IllegalPrenotazioneException{
		if (stato==StatiPossibili.PRENOTATO)controllaPossibilitaPrenotazione();
		this.stato=stato;
		switch (stato){
			case ACQUISTATO: dataAcquisto= dataMovimento; break;
			case CANCELLATO: dataCancellazione= dataMovimento; break;//significa che è stato cancellato
			case PRENOTATO: dataPrenotazione= dataMovimento; break;
			case SCADUTO: break; 
			default: throw new IllegalStatoBigliettoException ("Errore nell'inserimento dello stato");
		}
	}


	/**
	 * Restituisce i dettagli del biglietto.
	 * @return I dettagli del biglietto.
	 */
	public String mostraDettagli(){
		
		String ritorno= partita.toString() +  " posto: " + settore + "," + (riga+1) + "," +  (colonna +1) + " Costo Biglietto: " + costo;
		if (stato==StatiPossibili.SCADUTO) ritorno+= " Scaduto, ";
		
		if (dataAcquisto!=null)
			ritorno+= " Acquistato il: " + getStringaData(dataAcquisto);

		if (dataPrenotazione!=null)
			ritorno+= " Prenotato il: " + getStringaData(dataPrenotazione);
		
		if (dataCancellazione!=null)
			ritorno+= " Cancellato il: " + getStringaData(dataCancellazione);
		
		return ritorno;
	}
	
	/**
	 * Restituisce la data che viene passata in input in un formato stringa consono.
	 * @param dataDaScrivere La data da scrivere in formato stringa.
	 * @return La data in formato stringa.
	 */
	private String getStringaData(GregorianCalendar dataDaScrivere){
		int giorno,mese, anno, ore, minuti;
		String stringaOre, stringaMinuti, stringaDiRitorno="";
	
		giorno=dataDaScrivere.get(GregorianCalendar.DAY_OF_MONTH);
		mese= dataDaScrivere.get(GregorianCalendar.MONTH) +1;
		anno= dataDaScrivere.get(GregorianCalendar.YEAR);
		ore=dataDaScrivere.get(GregorianCalendar.HOUR_OF_DAY);
		minuti= dataDaScrivere.get(GregorianCalendar.MINUTE);
		stringaDiRitorno+=  giorno + "/" + mese + "/" + anno + " alle ore: ";
		if (ore<10) stringaOre= "0" + ore;
		else stringaOre= "" + ore;
		if (minuti<10) stringaMinuti= "0" + minuti;
		else stringaMinuti= "" + minuti;
		
		stringaDiRitorno+= stringaOre + ":" + stringaMinuti;
		return stringaDiRitorno;
			
	}
	
	/**
	 * Nel caso lo stato del biglietto sia prenotato controlla se la prenotazione è ancora valida.
	 * @return TRUE se lo stato del biglietto è PRENOTATO e la prenotazione è ancora valida FALSE altrimenti.
	 */
	public boolean checkValiditaPrenotazione(){
		if (stato!=StatiPossibili.PRENOTATO) return false;
		GregorianCalendar dataCorrente, dataPartita;
		dataCorrente= new GregorianCalendar();
		dataPartita= partita.getData();
		
		long millisecondiCorrenti= dataCorrente.getTimeInMillis();
		long millisecondiPartita= dataPartita.getTimeInMillis();
		if (millisecondiCorrenti+MILLISECONDI_IN_12_ORE>millisecondiPartita)
			return false;
		else return true;
	}

	/**
	 * Confronta il parametro implicito con un altro oggetto passato come parametro.
	 * @return TRUE se i due oggetti sono uguali, FALSE se sono diversi.
	 */
	public boolean equals (Object obj){
		if (obj==null) return  false;
		else if (getClass()!=obj.getClass()) return false;
		Biglietto altroBiglietto= (Biglietto) obj;
		boolean uguaglianza= partita.equals(altroBiglietto.partita) && cliente.equals(altroBiglietto.cliente) && settore==altroBiglietto.settore && riga==altroBiglietto.riga && colonna==altroBiglietto.colonna && stato==altroBiglietto.stato;
		if (dataAcquisto!=null) uguaglianza= uguaglianza && dataAcquisto.equals(altroBiglietto.dataAcquisto);
		if (dataPrenotazione!= null) uguaglianza= uguaglianza && dataPrenotazione.equals(altroBiglietto.dataPrenotazione);
		if (dataCancellazione!=null) uguaglianza= uguaglianza && dataCancellazione.equals(altroBiglietto.dataCancellazione);
		return uguaglianza;
	}
	
	
	public static final int MILLISECONDI_IN_12_ORE=43200000;
	public enum StatiPossibili {PRENOTATO, ACQUISTATO, SCADUTO, CANCELLATO}; //Un biglietto diventa scaduto se era stato prenotato e non è più valida la prenotazione, diventa cancellato se era stato prenotato e la prenotazione è stata cancellata
	private StatiPossibili stato;
	private double costo;
	private Partita partita;
	private Cliente cliente;
	private Partita.SettoriPossibili settore;
	private int riga;
	private int colonna;
	private GregorianCalendar dataAcquisto, dataPrenotazione, dataCancellazione; //la data di prenotazione o acquisto di un biglietto


}
