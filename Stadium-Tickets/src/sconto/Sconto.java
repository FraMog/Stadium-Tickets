package sconto;
import java.io.Serializable;
import partite.*;
/**
 * Questa classe modella il concetto di Sconto: ogni sconto ha una percentuale di sconto ed un metodo che dato un prezzo
 * mi restituisce il prezzo scontato.
 * @author Mogavero
 *
 */
public abstract class Sconto implements Serializable {

	private static final long serialVersionUID = 7425069396019472142L;

	/**
	 * Definisce un nuovo Sconto con la percentuale sconto indicata.
	 * @param percentualeSconto La percentuale dello sconto.
	 */
	public Sconto(int percentualeSconto){
		
		this.percentualeSconto = percentualeSconto;
	}
	
	
	/**
	 * Questo metodo aggiunge uno sconto ad una partita passata in input se lo sconto e la partita sono compatibili.
	 * @param partita La partita in cui si vuole aggiungere lo sconto.
	 */
	public abstract void aggiungiScontoAPartita(Partita partita);
	
	
	
	/**
	 * Dato un prezzo in input, restuisce il prezzo scontato.
	 * @param prezzo Il prezzo da scontare.
	 * @return Il prezzo scontato.
	 */
	public double getPrezzoScontato(double prezzo){
		return prezzo-= prezzo*percentualeSconto/100;
	}

	
	
	/**
	 * Confronta il parametro implicito con un altro oggetto passato come parametro.
	 * @return TRUE se i due oggetti sono uguali, FALSE se sono diversi.
	 */
	public boolean equals (Object obj){
		if (obj==null) return false;
		else if (getClass()!= obj.getClass()) return false;
		Sconto altroSconto= (Sconto) obj;
		return percentualeSconto== altroSconto.percentualeSconto;
	}
	
	
	private int percentualeSconto;
}
