package sconto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import eccezioni.*;
import partite.*;
/**
 * Questa classe modella il concetto di sconto per giorni settimanali:
 * Uno sconto per giorni settimanali oltre alle caratteristice di uno Sconto
 * presenta anche una elenco di giorni della settimana nei quali lo sconto è attivo;
 * Ogni partita il cui giorno della settimana di disputa dovesse corrispondere ad
 * un giorno della settimana per cui questo sconto è attivo avrà questo sconto
 * tra gli sconti disponibili.
 * @author Mogavero
 *
 */

public class ScontoPerGiorniSettimanali extends Sconto implements Serializable{


	private static final long serialVersionUID = -5084024159490876798L;

	/**
	 * Crea un nuovo ScontoPerGiorniSettimanali.
	 * @param percentualeSconto La percentuale dello sconto.
	 * @param elencoGiorniSconto  L'elenco dei giorni settimanali nei quali lo sconto è attivo.
	 * @throws IllegalGiorniScontoException Se l'elenco dei giorni per il quale lo sconto si riferisce è vuoto.
	 */
	public ScontoPerGiorniSettimanali(int percentualeSconto, ArrayList<Integer> elencoGiorniSconto) throws IllegalGiorniScontoException
			{
		super(percentualeSconto);
		if (elencoGiorniSconto.size()==0) throw new IllegalGiorniScontoException ("Non puoi aggiungere uno sconto per nessun giorno settimanale!");
		this.elencoGiorniSconto = elencoGiorniSconto;
	}
	
	/**
	 * Aggiunge lo sconto ad una partita passata in input se e solo se il giorno settimanale di disputa della partita è uguale ad uno dei giorni in cui lo sconto è attivo.
	 */
	@Override
	public void aggiungiScontoAPartita(Partita partita) {
		int i=0;
		while (i<elencoGiorniSconto.size() && !(elencoGiorniSconto.get(i)==partita.getData().get(GregorianCalendar.DAY_OF_WEEK)))
			i++;
		if (i<elencoGiorniSconto.size()) partita.addScontoDisponibile(this); //se i<elencoGiorniSconto.size vuol dire che il giorno della partita è tra i giorni in cui lo sconto è attivo		
	}
	
	/**
	 * Confronta il parametro implicito con un altro oggetto passato come parametro.
	 * @return TRUE se i due oggetti sono uguali, FALSE se sono diversi.
	 */
	public boolean equals (Object obj){
		if (!super.equals(obj)) return false;
		ScontoPerGiorniSettimanali altroScontoPerGiorniSettimanali= (ScontoPerGiorniSettimanali) obj;
		return elencoGiorniSconto.equals(altroScontoPerGiorniSettimanali.elencoGiorniSconto);
	}
	

	private ArrayList <Integer> elencoGiorniSconto;

	
}
