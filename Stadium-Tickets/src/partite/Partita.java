package partite;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import eccezioni.*;
import partite.Biglietto.StatiPossibili;
import sconto.*;
import utenti.Cliente;

/**
 * Questa classe modella il concetto di partita:
 * Una partita ha una squadra casa, una squadra trasferta, una data,uno stadio di svolgimento  (con le varie dimensioni), 
 * un prezzo per i biglietti un elenco di sconti disponibili per i prezzo dei biglietti, una fascia oraria di appartenza.
 * @author Mogavero
 *
 */
public class Partita implements Serializable{

	private static final long serialVersionUID = 7229021718331460124L;
	/**
	 * Crea una nuova partita.
	 * @param squadraCasa La squadra che gioca in casa.
	 * @param squadraTrasferta La squadra che gioca in trasferta.
	 * @param data La data di svolgimento della partita.
	 * @param stadio Lo stadio in cui la partita ha luogo.
	 * @throws IllegalNomeSquadraException Se il nome della squadra inserito ha lunghezza 0 o è scorretto.
	 * @throws IllegalDataException Se la data dell'incontro inserita è antecedente alla data attuale.
	 */
	public Partita(String squadraCasa, String squadraTrasferta, GregorianCalendar data, Stadio stadio) throws IllegalNomeSquadraException, IllegalDataException{
		if (squadraCasa.length()==0) throw new IllegalNomeSquadraException ("La squadra casa non può essere vuota!");
		if (squadraTrasferta.length()==0) throw new IllegalNomeSquadraException ("La squadra trasferta non può essere vuota!");
		
		if (new GregorianCalendar().after(data)) throw new IllegalDataException ("La data della partita non può essere precedente alla data corrente!");
	
		boolean lettera=false;
		for (int i=0; i<squadraCasa.length();i++)
			if (Character.isLetter(squadraCasa.charAt(i))) lettera=true;	
		
			
		if (!lettera) throw new IllegalNomeSquadraException ("La squadraCasa deve avere almeno carattere alfabetico");
		
		
		lettera=false;
		for (int i=0; i<squadraTrasferta.length();i++)
			if (Character.isLetter(squadraTrasferta.charAt(i))) lettera=true;
		
			
		if (!lettera) throw new IllegalNomeSquadraException ("La squadraTrasferta deve avere almeno carattere alfabetico");
			
		
		
		this.squadraCasa = squadraCasa;
		this.squadraTrasferta = squadraTrasferta;
		this.data = data;
		prezzoBiglietti = stadio.getPrezzoPartite();
		this.stadio = stadio;
		
		tribuneCentraliLength= stadio.getTribuneCentraliLength();
		tribuneCentraliHeight= stadio.getTribuneCentraliHeight();
		tribuneLateraliLength=stadio.getTribuneLateraliLength();
		tribuneLateraliHeight=stadio.getTribuneLateraliHeight();
		capienza= stadio.getCapienza();
		scontiDisponibili= new ArrayList <Sconto>();
		
		bigliettiSettoreNord= new Biglietto [tribuneCentraliHeight][tribuneCentraliLength];
		bigliettiSettoreSud= new Biglietto [tribuneCentraliHeight][tribuneCentraliLength];
		bigliettiSettoreWest= new Biglietto [tribuneLateraliHeight][tribuneLateraliLength];
		bigliettiSettoreEst= new Biglietto [tribuneLateraliHeight][tribuneLateraliLength];
		
		
		
		int oraPartita= data.get(GregorianCalendar.HOUR_OF_DAY);
		if (oraPartita>=ORA_INIZIO_NOTTE && oraPartita<ORA_INIZIO_MATTINA) 
			fasciaOrarioPartita=FasciaGiornaliera.NOTTE;
		else if (oraPartita < ORA_INIZIO_POMERIGGIO)
			fasciaOrarioPartita=FasciaGiornaliera.MATTINA;
		else if (oraPartita < ORA_INIZIO_SERA)
			fasciaOrarioPartita=FasciaGiornaliera.POMERIGGIO;
		else 	
			fasciaOrarioPartita=FasciaGiornaliera.SERA;
		}
	
	
	/**
	 * Restituisce la fascia giornaliera in cui appartiene la partita.
	 * @return La fascia giornaliera in cui appartiene la partita.
	 */
	public FasciaGiornaliera getFasciaOrarioPartita(){
		return fasciaOrarioPartita;
	}
	
	/**
	 * Restituisce l'array di biglietti del settore Nord della partita.
	 * @return L'array di biglietti del settore Nord della partita.
	 */
	public Biglietto[][] getBigliettiSettoreNord() {
		return bigliettiSettoreNord;
	}

	/**
	 * Restituisce l'array di biglietti del settore Sud della partita.
	 * @return L'array di biglietti del settore Sud della partita.
	 */
	public Biglietto[][] getBigliettiSettoreSud() {
		return bigliettiSettoreSud;
	}

	/**
	 * Restituisce l'array di biglietti del settore West della partita.
	 * @return L'array di biglietti del settore West della partita.
	 */
	public Biglietto[][] getBigliettiSettoreWest() {
		return bigliettiSettoreWest;
	}

	/**
	 * Restituisce l'array di biglietti del settore Est della partita.
	 * @return L'array di biglietti del settore Est della partita.
	 */
	public Biglietto[][] getBigliettiSettoreEst() {
		return bigliettiSettoreEst;
	}

	/**
	 * Restituisce la capienza dello stadio della partita.
	 * @return La capienza dello stadio della partita.
	 */
	public int getCapienza(){
		return capienza;
	}
	
	/**
	 * Restituisce la squadra che gioca in casa la partita.
	 * @return La squadra che gioca in casa la partita.
	 */
	public String getSquadraCasa() {
		return squadraCasa;
	}

	/**
	 * Restituisce la squadra che gioca in trasferta la partita.
	 * @return La squadra che gioca in trasferta la partita.
	 */
	public String getSquadraTrasferta() {
		return squadraTrasferta;
	}

	/**
	 * Restituisce la data della partita.
	 * @return La data della partita.
	 */
	public GregorianCalendar getData() {
		return data;
	}

	/**
	 * Restituisce la lunghezza delle tribune centrali della partita.
	 * @return La lunghezza delle tribune centrali della partita.
	 */
	public int getTribuneCentraliLength() {
		return tribuneCentraliLength;
	}

	/**
	 * Restituisce l'altezza delle tribune centrali della partita.
	 * @return L'altezza delle tribune centrali della partita.
	 */
	public int getTribuneCentraliHeight() {
		return tribuneCentraliHeight;
	}

	/**
	 * Restituisce la lunghezza delle tribune laterali della partita.
	 * @return La lunghezza delle tribune laterali della partita.
	 */
	public int getTribuneLateraliLength() {
		return tribuneLateraliLength;
	}

	/**
	 * Restituisce l'altezza delle tribune laterali della partita.
	 * @return L'altezza delle tribune laterali della partita.
	 */
	public int getTribuneLateraliHeight() {
		return tribuneLateraliHeight;
	}

	
	/**
	 * Restituisce lo stadio della partita.
	 * @return Lo stadio della partita.
	 */
	public Stadio getStadio() {
		return stadio;
	}
	
	/**
	 * Setta lo stadio della partita.
	 * @param stadio Lo stadio della partita.
	 */
	public void setStadio(Stadio stadio){
		this.stadio=stadio;
	}
	/**
	 * Controlla se una partita è iniziata o meno.
	 * @return TRUE se è gia iniziata, FALSE altrimenti.
	 */
	public boolean controllaSeNonIniziata(){
		return data.after(new GregorianCalendar());
	}
	
	/**
	 * Aggiunge un nuovo sconto all'elenco degli sconti disponibili per i biglietti della partita.
	 * @param sconto Lo sconto da aggiungere.
	 */
	public void addScontoDisponibile(Sconto sconto){
		scontiDisponibili.add(sconto);
	}

	/**
	 * Mostra i dettagli della partita.
	 * @return I dettagli della partita.
	 */
	public String mostraDettagli(){
		int giorno= data.get(GregorianCalendar.DAY_OF_MONTH);
		int mese= data.get(GregorianCalendar.MONTH)+1;
		int anno= data.get(GregorianCalendar.YEAR);
		int ora= data.get(GregorianCalendar.HOUR_OF_DAY);
		int minuti= data.get(GregorianCalendar.MINUTE);
		String stringaOra, stringaMinuti;
		if (ora<10) stringaOra= "0" + ora;
		else stringaOra= "" + ora;
		if (minuti<10) stringaMinuti= "0" + minuti;
		else stringaMinuti= "" + minuti;
		
		return squadraCasa + " - " + squadraTrasferta + " del " + giorno + "/" + mese + "/" + anno + " alle " + stringaOra + ":" + stringaMinuti + " Stadio " + stadio.getNome() + " Capienza " + capienza + " posti" + " Prezzo Biglietto Non Scontato" + prezzoBiglietti  ;
	}
	
	
	

	/**
	 * Confronta il parametro implicito con un altro oggetto passato come parametro.
	 * @return TRUE se i due oggetti sono uguali, FALSE se sono diversi.
	 */
	public boolean equals(Object obj){
		if (obj==null)return false;
		if (getClass()!=obj.getClass()) return false;
		Partita altraPartita = (Partita) obj;
		return squadraCasa.equals(altraPartita.squadraCasa) && squadraTrasferta.equals(altraPartita.squadraTrasferta) && data.equals(altraPartita.data) && stadio.getIdentificativo()== altraPartita.getStadio().getIdentificativo() && stadio.getNome().equals(altraPartita.getStadio().getNome()) && fasciaOrarioPartita.equals(altraPartita.fasciaOrarioPartita)&&
				tribuneCentraliLength== altraPartita.tribuneCentraliLength && 	tribuneCentraliHeight==altraPartita.tribuneCentraliHeight && tribuneLateraliLength==altraPartita.tribuneLateraliLength && tribuneLateraliHeight==altraPartita.tribuneLateraliHeight;
	}
	
	/**
	 * Aggiunge un nuovo biglietto nel settore, riga, colonna specificati.
	 * @param settore Il settore in cui aggiungere il biglietto.
	 * @param riga La riga del settore in cui aggiungere il biglietto.
	 * @param colonna La colonna del settore in cui aggiungere il biglietto.
	 * @param bigliettoDaAggiungere Il biglietto da aggiungere.
	 * @throws IllegalInserimentoBigliettoException Se il settore indicato non rientra fra i settori possibili della partita.
	 * @throws PostoIndisponibileException Se il posto è gia occupato.
	 */
	public void aggiungiBiglietto(SettoriPossibili settore, int riga, int colonna, Biglietto bigliettoDaAggiungere) throws IllegalInserimentoBigliettoException, PostoIndisponibileException{
		switch (settore){
		case Nord: {
			if (bigliettiSettoreNord[riga][colonna]!=null) throw new PostoIndisponibileException ("Posto non disponibile!");
			else{
				bigliettiSettoreNord[riga][colonna]= bigliettoDaAggiungere;
				;
				break;
			}
		}
		case Sud: {
			if (bigliettiSettoreSud[riga][colonna]!=null) throw new PostoIndisponibileException ("Posto non disponibile!");
			else {
				bigliettiSettoreSud[riga][colonna]= bigliettoDaAggiungere;
				break;
			}
		}
		case Ovest: {
			if (bigliettiSettoreWest[riga][colonna]!=null) throw new PostoIndisponibileException ("Posto non disponibile!");
			else {
				bigliettiSettoreWest[riga][colonna]= bigliettoDaAggiungere;
				break;
			}
		}
		case Est: {
			if (bigliettiSettoreEst[riga][colonna]!=null) throw new PostoIndisponibileException ("Posto non disponibile!");
			else {
				bigliettiSettoreEst[riga][colonna]= bigliettoDaAggiungere;
				break;
			}
		}
		default: throw new IllegalInserimentoBigliettoException ("Non è stato possibile inserire il biglietto: settore= " + settore);
		}
	}
	
	/**
	 * Rimuove il biglietto dall'elenco dei biglietti della partita nel settore, riga e colonna specificati.
	 * @param settore Il settore dove si trova il biglietto da eliminare.
	 * @param riga La riga del settore dove si trova il biglietto da eliminare.
	 * @param colonna La colonna del settore dove si trova il biglietto da eliminare.
	 * @throws IllegalCancellazioneBigliettoException Se il settore indicato non rientra fra i settori possibili della partita.
	 */
	public void rimuoviBiglietto(SettoriPossibili settore, int riga, int colonna)throws  IllegalCancellazioneBigliettoException{
		switch (settore){
		case Nord: {
			bigliettiSettoreNord[riga][colonna]= null;
			break;
		}
		case Sud: {
			bigliettiSettoreSud[riga][colonna]= null;
			break;
		}
		case Ovest: {
			bigliettiSettoreWest[riga][colonna]= null;
			break;
		}
		case Est: {
			bigliettiSettoreEst[riga][colonna]= null;
			break;
		}
		default: throw new IllegalCancellazioneBigliettoException ("Non è stato possibile cancellare il biglietto");
		}
	}
	
	/**
	 * Restituisce il miglior prezzo disponibile attualmente per i biglietti della partita cercando fra tutte le politiche di sconto attive per la partita.
	 * @return Il miglior prezzo disponibile per i biglietti.
	 */
	public double getMigliorPrezzoDisponibile(){
		double migliorPrezzoFinora=prezzoBiglietti;
		for (int i=0; i<scontiDisponibili.size();i++)
			if (scontiDisponibili.get(i).getPrezzoScontato(prezzoBiglietti)<migliorPrezzoFinora)
				migliorPrezzoFinora=scontiDisponibili.get(i).getPrezzoScontato(prezzoBiglietti);
		return migliorPrezzoFinora;
	}
	
	/**
	 * Restituisce la partita in formato stringa.
	 */
	public String toString(){
		int giorno= data.get(GregorianCalendar.DAY_OF_MONTH);
		int mese= data.get(GregorianCalendar.MONTH)+1;
		int anno= data.get(GregorianCalendar.YEAR);
		int ora= data.get(GregorianCalendar.HOUR_OF_DAY);
		int minuti= data.get(GregorianCalendar.MINUTE);
		String stringaOra, stringaMinuti;
		if (ora<10) stringaOra= "0" + ora;
		else stringaOra= "" + ora;
		if (minuti<10) stringaMinuti= "0" + minuti;
		else stringaMinuti= "" + minuti;
		return squadraCasa + " - " + squadraTrasferta  +" Stadio " + stadio.toString() + " del " + giorno + "/" + mese + "/" + anno + " alle " +  stringaOra + ":" + stringaMinuti;
	}
	
	/**
	 * Controlla se la data della partita è compresa fra la data di inizio e la data di fine passate in input.
	 * @param dataInizio La data di inizio.
	 * @param dataFine La data di fine.
	 * @return TRUE se la data della partita è compresa fra le due date, FALSE altrimenti.
	 */
	public boolean compresaFra(GregorianCalendar dataInizio, GregorianCalendar dataFine) {
		return !data.before(dataInizio) && !data.after(dataFine);
	}
	
	
	/**
	 * Controlla la validita di tutti i biglietti che sono stati PRENOTATI per questa partita: in caso la loro prenotazione non sia più valida li rimuove.
	 * @throws PrenotazioneInesistenteException Se la prenotazione che si sta tentando di cancellare per il cliente non esiste.
	 * @throws IllegalStatoBigliettoException Se lo stato del biglietto non appartiene agli stati possibili.
	 * @throws IllegalPrenotazioneException  Se la prenotazione è scorretta.
	 */
	public void checkValiditaBigliettiPrenotati() throws PrenotazioneInesistenteException, IllegalStatoBigliettoException, IllegalPrenotazioneException {
		for (int i=0; i<tribuneCentraliHeight;i++)
			for (int j=0; j<tribuneCentraliLength;j++){
				Biglietto biglietto= bigliettiSettoreNord[i][j];
				if (biglietto!= null && biglietto.getStato()==StatiPossibili.PRENOTATO && !biglietto.checkValiditaPrenotazione()){
					Cliente cliente= biglietto.getCliente();
					cliente.rimuoviPrenotazioneAttiva(biglietto);
					cliente.addPrenotazioneScaduta(biglietto);
					biglietto.setStato(StatiPossibili.SCADUTO, new GregorianCalendar());
					bigliettiSettoreNord[i][j]=null;
				}
			}
		for (int i=0; i<tribuneCentraliHeight;i++)
			for (int j=0; j<tribuneCentraliLength;j++){
				Biglietto biglietto= bigliettiSettoreSud[i][j];
				if (biglietto!= null && biglietto.getStato()==StatiPossibili.PRENOTATO && !biglietto.checkValiditaPrenotazione()){
					Cliente cliente= biglietto.getCliente();
					cliente.rimuoviPrenotazioneAttiva(biglietto);
					cliente.addPrenotazioneScaduta(biglietto);
					biglietto.setStato(StatiPossibili.SCADUTO, new GregorianCalendar());
					bigliettiSettoreSud[i][j]=null;
				}
			}
		for (int i=0; i<tribuneLateraliHeight;i++)
			for (int j=0; j<tribuneLateraliLength;j++){
				Biglietto biglietto= bigliettiSettoreWest[i][j];
				if (biglietto!= null && biglietto.getStato()==StatiPossibili.PRENOTATO && !biglietto.checkValiditaPrenotazione()){
					Cliente cliente= biglietto.getCliente();
					cliente.rimuoviPrenotazioneAttiva(biglietto);
					cliente.addPrenotazioneScaduta(biglietto);
					biglietto.setStato(StatiPossibili.SCADUTO, new GregorianCalendar());
					bigliettiSettoreWest[i][j]=null;
				}
			}
		for (int i=0; i<tribuneLateraliHeight;i++)
			for (int j=0; j<tribuneLateraliLength;j++){
				Biglietto biglietto= bigliettiSettoreEst[i][j];
				if (biglietto!= null && biglietto.getStato()==StatiPossibili.PRENOTATO && !biglietto.checkValiditaPrenotazione()){
					Cliente cliente= biglietto.getCliente();
					cliente.rimuoviPrenotazioneAttiva(biglietto);
					cliente.addPrenotazioneScaduta(biglietto);
					biglietto.setStato(StatiPossibili.SCADUTO, new GregorianCalendar());
					bigliettiSettoreEst[i][j]=null;
				}
			}
	}
	
	
	
	public static final int ORA_INIZIO_NOTTE=0, ORA_INIZIO_MATTINA=7, ORA_INIZIO_POMERIGGIO=13, ORA_INIZIO_SERA=20; 
	public static enum FasciaGiornaliera {MATTINA, POMERIGGIO, SERA, NOTTE}
	public static enum SettoriPossibili {Nord,Sud,Ovest,Est}
	
	
	private Biglietto [][] bigliettiSettoreNord, bigliettiSettoreSud, bigliettiSettoreWest, bigliettiSettoreEst;
	private FasciaGiornaliera fasciaOrarioPartita;
	private ArrayList <Sconto> scontiDisponibili;
	private String squadraCasa, squadraTrasferta;
	private GregorianCalendar data;
	private int tribuneCentraliLength, tribuneCentraliHeight;
	private int tribuneLateraliLength, tribuneLateraliHeight;
	private int capienza;
	private double prezzoBiglietti;
	private Stadio stadio;

	
	
}
