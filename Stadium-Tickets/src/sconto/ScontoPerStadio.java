package sconto;

import java.io.Serializable;
import eccezioni.*;
import partite.*;

/**
 * Questa classe modella il concetto di sconto per Stadio:
 * Uno sconto per stadio oltre alle caratteristice di uno Sconto
 * presenta anche uno stadio che indica per quale stadio lo sconto
 * è attivo; ogni partita che si svolge in quello stadio avrà questo
 * sconto tra gli sconti disponibili.
 * @author Mogavero
 *
 */
public class ScontoPerStadio extends Sconto implements Serializable{
	
	
	private static final long serialVersionUID = -2566227272811221138L;

	/**
	 * Crea un nuovo ScontoPerStadio.
	 * @param percentualeSconto La percentuale dello sconto.
	 * @param stadio Lo stadio per il quale lo sconto è attivo.
	 * @throws IllegalStadioException Se lo stadio passato in input è null.
	 */
	public ScontoPerStadio(int percentualeSconto, Stadio stadio) throws IllegalStadioException{
		super(percentualeSconto);
		if (stadio==null) throw new IllegalStadioException ("Lo stadio dello sconto non può essere vuoto!");
		this.stadio = stadio;
	}

	/**
	 * Aggiunge lo sconto ad una partita passata in input se e solo se lo stadio in cui si svolge la partita coincide con quello in cui questo sconto è attivo.
	 */
	@Override
	public void aggiungiScontoAPartita(Partita partita) {
		if (partita.getStadio().equals(stadio)) partita.addScontoDisponibile(this);
		
	}
	
	/**
	 * Confronta il parametro implicito con un altro oggetto passato come parametro.
	 * @return TRUE se i due oggetti sono uguali, FALSE se sono diversi.
	 */
	public boolean equals (Object obj){
		if (!super.equals(obj)) return false;
		ScontoPerStadio altroScontoPerStadio= (ScontoPerStadio) obj;
		return stadio.equals(altroScontoPerStadio.stadio);
	}
	
	private Stadio stadio;
}
