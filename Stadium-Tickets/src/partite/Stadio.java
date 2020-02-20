package partite;

import java.io.Serializable;
import java.util.ArrayList;

import eccezioni.*;
/**
 * Questa classe modella il concetto di Stadio:
 * Uno stadio ha un identificativo numerico, un nome, delle dimensioni per le sue tribune, una capienza,
 * un prezzo per le partite che si svolgono al suo interno, un incasso totale ottenuto dai biglietti 
 * venduti per le partite che si svolgono al suo interno ed un elenco di partite che si svolgono nello stadio..
 * @author Mogavero
 *
 */
public class Stadio implements Serializable{
	
	private static final long serialVersionUID = -8761503023925881318L;
	
	/**
	 * Crea un nuovo stadio.
	 * @param identificativo L'identificativo numerico dello stadio.
	 * @param nome Il nome dello stadio.
	 * @param tribuneCentraliLength Il numero di colonne delle tribune centrali.
	 * @param tribuneCentraliHeight Il numero di righe delle tribune centrali.
	 * @param tribuneLateraliLength Il numero di colonne delle tribune laterali.
	 * @param tribuneLateraliHeight Il numero di righe delle tribune laterali.
	 * @param prezzo Il prezzo per le partite che si svolgono al suo interno.
	 * @throws IllegalNomeStadioException Se il nome dello stadio è vuoto o è scorretto.
	 */
	public Stadio(int identificativo,String nome, int tribuneCentraliLength, int tribuneCentraliHeight, int tribuneLateraliLength,
			int tribuneLateraliHeight,double prezzo) throws IllegalNomeStadioException {
		if (nome.length()==0) throw new IllegalNomeStadioException ("Il nome dello Stadio non può essere vuoto");
		
		
		boolean lettera=false;
		for (int i=0; i<nome.length();i++)
			if (Character.isLetter(nome.charAt(i))) lettera=true;
		
		if (!lettera ) throw new IllegalNomeStadioException ("Il nome dello stadio deve avere almeno un carattere alfabetico");
		
		this.identificativo=identificativo;
		this.nome = nome;
		this.tribuneCentraliLength = tribuneCentraliLength;
		this.tribuneCentraliHeight = tribuneCentraliHeight;
		this.tribuneLateraliLength = tribuneLateraliLength;
		this.tribuneLateraliHeight = tribuneLateraliHeight;
		this.prezzoPartite=prezzo;
		capienza= tribuneCentraliLength*tribuneCentraliHeight*2 + tribuneLateraliHeight*tribuneLateraliLength*2;
		incassoTotaleStadio=0;
		elencoPartiteStadio= new ArrayList <Partita>();
	}
	
	
	/**
	 * Aggiorna le dimensioni dello stadio.
	 * @param tribuneCentraliLength Il nuovo numero di colonne delle tribune centrali.
	 * @param tribuneCentraliHeight Il nuovo numero di righe delle tribune centrali.
	 * @param tribuneLateraliLength Il nuovo numero di colonne delle tribune laterali.
	 * @param tribuneLateraliHeight Il nuovo numero di righe delle tribune laterali.
	 */
	public void aggiornaDimensioniStadio(int tribuneCentraliLength,int tribuneCentraliHeight,int tribuneLateraliLength, int tribuneLateraliHeight){
		this.tribuneCentraliLength=tribuneCentraliLength;
		this.tribuneCentraliHeight=tribuneCentraliHeight;
		this.tribuneLateraliLength=tribuneLateraliLength;
		this.tribuneLateraliHeight=tribuneLateraliHeight;
		capienza= tribuneCentraliLength*tribuneCentraliHeight*2 + tribuneLateraliHeight*tribuneLateraliLength*2;
	}
	
	
	/**
	 * Restituisce l'identificativo dello stadio.
	 * @return L'identificativo dello stadio.
	 */
	public int getIdentificativo() {
		return identificativo;
	}

	/**
	 * Restituisce il nome dello stadio.
	 * @return Il nome dello stadio.
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * Restituisce la capienza dello stadio.
	 * @return La capienza dello stadio.
	 */
	public int getCapienza(){
		return capienza;
	}
	
	/**
	 * Restituisce la lunghezza delle tribune centrali dello stadio.
	 * @return La lunghezza delle tribune centrali dello stadio.
	 */
	public int getTribuneCentraliLength() {
		return tribuneCentraliLength;
	}
	
	/**
	 * Restituisce l'altezza delle tribune centrali dello stadio.
	 * @return L'altezza delle tribune centrali dello stadio.
	 */
	public int getTribuneCentraliHeight() {
		return tribuneCentraliHeight;
	}
	
	/**
	 * Restituisce la lunghezza delle tribune laterali dello stadio.
	 * @return La lunghezza delle tribune laterali dello stadio.
	 */
	public int getTribuneLateraliLength() {
		return tribuneLateraliLength;
	}

	/**
	 * Restituisce l'altezza delle tribune laterali dello stadio.
	 * @return L'altezza delle tribune laterali dello stadio.
	 */
	public int getTribuneLateraliHeight() {
		return tribuneLateraliHeight;
	}
	
	/**
	 * Restituisce l'incasso ottenuto fin qui per lo stadio.
	 * @return L'incasso ottenuto fin qui per lo stadio.
	 */
	public double getIncassoTotaleStadio(){
		return incassoTotaleStadio;
	}
	
	/**
	 * Aggiunge all'incasso totale dello stadio l'importo dovuto alla vendita di un altro biglietto.
	 * @param amount L'importo da aggiungere.
	 */
	public void addIncasso(double amount){
		incassoTotaleStadio+=amount;
	}
	
	/**
	 * Restituisce il prezzo delle partite dello stadio.
	 * @return Il prezzo delle partite dello stadio.
	 */
	public double getPrezzoPartite() {
		return prezzoPartite;
	}
	
	/**
	 * Modifica il prezzo delle partite dello stadio.
	 * @param prezzo Il nuovo prezzo.
	 */
	public void setPrezzo(double prezzo){
		this.prezzoPartite=prezzo;
	}
	
	
	/**
	 * Confronta il parametro implicito con un altro oggetto passato come parametro.
	 * @return TRUE se i due oggetti sono uguali, FALSE se sono diversi.
	 */
	public boolean equals (Object obj){
		if (obj==null) return false;
		else if (getClass()!=obj.getClass()) return false;
		Stadio altroStadio= (Stadio)obj;
		return nome.equals(altroStadio.nome) && tribuneCentraliLength==altroStadio.tribuneCentraliLength  &&
				tribuneCentraliHeight==altroStadio.tribuneCentraliHeight && tribuneLateraliLength== altroStadio.tribuneLateraliLength && tribuneLateraliHeight== altroStadio.tribuneLateraliHeight;
	}
	
	
	/**
	 * Restituisce lo stadio in formato stringa.
	 */
	public String toString(){
		return identificativo + "." + nome;
	}
	
	/**
	 * Aggiunge una nuova partita all'elenco delle partite che si disputano nello stadio.
	 * @param p La partita da aggiungere.
	 */
	public void addPartita(Partita p){
		elencoPartiteStadio.add(p);
	}
	
	/**
	 * Restituisce l'elenco di tutte le partite che si svolgono nello stadio.
	 * @return L'elenco di tutte le partite che si svolgono nello stadio.
	 */
	public ArrayList <Partita> getElencoPartiteStadio(){
		return elencoPartiteStadio;
	}
	
	private int identificativo;
	private int capienza;
	private String nome;
	private int tribuneCentraliLength, tribuneCentraliHeight;
	private int tribuneLateraliLength,tribuneLateraliHeight;
	private double incassoTotaleStadio,prezzoPartite;
	private ArrayList<Partita> elencoPartiteStadio;
	
}
