package sconto;
import java.io.Serializable;
import partite.*;
import partite.Partita.FasciaGiornaliera;

/**
 * Questa classe modella il concetto di sconto per fasca giornaliera:
 * Uno sconto per fascia giornaliera oltre alle caratteristice di uno Sconto
 * presenta anche una fascia oraria delle partite al quale lo sconto si riferisce.
 * @author Mogavero
 *
 */
public class ScontoPerFasciaGiornaliera extends Sconto implements Serializable{

	private static final long serialVersionUID = 2758343188281714985L;


	/**
	 * Crea un nuovo ScontoPerFasciaGiornaliera.
	 * @param percentualeSconto La percentuale dello sconto.
	 * @param fasciaPartitaSconto La fascia oraria delle partite al quale lo sconto si rivolge.
	 */
	public ScontoPerFasciaGiornaliera(int percentualeSconto, FasciaGiornaliera fasciaPartitaSconto)
			{
		super(percentualeSconto);
		this.fasciaPartitaSconto = fasciaPartitaSconto;
	}

	
	
	/**
	 * Aggiunge lo sconto alla partita passata in input se e solo se la fascia Oraria della partita coincide con quella dello sconto.
	 */
	@Override
	public void aggiungiScontoAPartita(Partita partita) { 
		if (partita.getFasciaOrarioPartita()==fasciaPartitaSconto) partita.addScontoDisponibile(this);
		
	}
	
	/**
	 * Confronta il parametro implicito con un altro oggetto passato come parametro.
	 * @return TRUE se i due oggetti sono uguali, FALSE se sono diversi.
	 */
	public boolean equals (Object obj){
		if (!super.equals(obj))return false;
		ScontoPerFasciaGiornaliera altroScontoPerFasciaGiornaliera = (ScontoPerFasciaGiornaliera) obj;
		return fasciaPartitaSconto== altroScontoPerFasciaGiornaliera.fasciaPartitaSconto;
	}


	private Partita.FasciaGiornaliera fasciaPartitaSconto;



	
}
