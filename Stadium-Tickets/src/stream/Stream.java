package stream;

import java.io.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import partite.*;
import sconto.Sconto;
import utenti.*;
/**
 * Questa classe modella il concetto di stream.
 * Un oggetto stream è un oggetto che permette di leggere e scrivere su file passati in input gli utenti, gli stadi, e gli sconti.
 * @author Mogavero
 *
 */
public class Stream {
	
	/**
	 * Crea un nuovo oggetto stream.
	 * @param nomeFileUtenti Il nome del file da cui leggere e su cui scrivere gli utenti.
	 * @param nomeFileStadi Il nome del file da cui leggere e su cui scrivere gli stadi.
	 * @param nomeFileSconti Il nome del file da cui leggere e su cui scrivere gli sconti.
	 * @param elencoUtenti L'elenco di tutti gli utenti del sistema.
	 * @param elencoPartite L'elenco di tutte le partite del sistema.
	 * @param elencoStadi L'elenco di tutti gli stadi del sistema.
	 * @param elencoSconti L'elenco di tutti gli sconti del sistema.
	 */
	public Stream(String nomeFileUtenti, String nomeFileStadi, String nomeFileSconti, ArrayList<Utente> elencoUtenti, ArrayList<Partita> elencoPartite, ArrayList<Stadio> elencoStadi, ArrayList<Sconto> elencoSconti) {
		this.nomeFileUtenti = nomeFileUtenti;
		this.nomeFileStadi=nomeFileStadi;
		this.nomeFileSconti= nomeFileSconti;
		this.elencoUtenti = elencoUtenti;
		this.elencoPartite = elencoPartite;
		this.elencoStadi=elencoStadi;
		this.elencoSconti=elencoSconti;
	}
	
	
	/**
	 * Legge tutti gli utenti dal file inserendoli nell'elenco utenti.
	 * @throws FileNotFoundException Se il file non è stato trovato.
	 * @throws EOFException Se il file è finito.
	 * @throws ClassNotFoundException Se c'è mismatch tra classe del programma e classe degli oggetti letti.
	 * @throws IOException Se c'è un errore generico nella trasmissione dei dati.
	 */
	public void leggiUtenti() throws  FileNotFoundException, EOFException, IOException, ClassNotFoundException{
		ObjectInputStream inputUtenti = new ObjectInputStream (new FileInputStream (nomeFileUtenti));
		try{
			for (;;){
				elencoUtenti.add ((Utente) inputUtenti.readObject());
			}
		}
		finally{
			inputUtenti.close();
		}
		
	}
	

	/**
	 * Legge uno stadio alla volta tutti gli stadi presenti nel file; per ogni stadio letto esamina tutte le partite che vi 
	 * si svolgono all'interno aggiungendo ogni partita all'elenco partite e settando lo stadio letto come stadio della partita,
	 * infine per ogni partita esamina tutti i biglietti settando da capo il cliente per ogni biglietto della partita (per evitare inconsistenze).
	 * @throws FileNotFoundException Se il file non è stato trovato.
	 * @throws EOFException Se il file è finito.
	 * @throws ClassNotFoundException Se c'è mismatch tra classe del programma e classe degli oggetti letti.
	 * @throws IOException Se c'è un errore generico nella trasmissione dei dati.
	 */
	public void leggiStadi() throws  FileNotFoundException, EOFException, IOException, ClassNotFoundException{
		ObjectInputStream inputStadi = new ObjectInputStream (new FileInputStream (nomeFileStadi));
		try{
			for (;;){
				Stadio stadioLetto= (Stadio)inputStadi.readObject();
				ArrayList <Partita> partiteDelloStadioLetto= stadioLetto.getElencoPartiteStadio();
				for (Partita p: partiteDelloStadioLetto){
					p.setStadio(stadioLetto);
					associaBigliettiAClienti(p);
					elencoPartite.add(p);
				}
				elencoStadi.add (stadioLetto);
			}
		}
		finally{
			inputStadi.close();
		}
		
	}
	

	/**
	 * Legge tutti gli sconti dal file inserendoli nell'elenco sconti.
	 * @throws FileNotFoundException Se il file non è stato trovato.
	 * @throws EOFException Se il file è finito.
	 * @throws ClassNotFoundException Se c'è mismatch tra classe del programma e classe degli oggetti letti.
	 * @throws IOException Se c'è un errore generico nella trasmissione dei dati.
	 */
	public void leggiSconti() throws  FileNotFoundException, EOFException, IOException, ClassNotFoundException{
		ObjectInputStream inputSconti = new ObjectInputStream (new FileInputStream (nomeFileSconti));
		try{
			for (;;){
				elencoSconti.add ((Sconto)inputSconti.readObject());
			}
		}
		finally{
			inputSconti.close();
		}
		
	}
	
	
	/**
	 * Scrive tutti gli utenti su file.
	 * @throws IOException Se c'è un errore nella scrittura su file.
	 */
	public void scriviUtenti() throws IOException{
		ObjectOutputStream outputUtenti = new ObjectOutputStream (new FileOutputStream(nomeFileUtenti));
		for (Utente u: elencoUtenti){
			outputUtenti.writeObject(u);
		}
		outputUtenti.close();
	}
	

	/**
	 * Scrive tutti gli stadi su file.
	 * @throws IOException Se c'è un errore nella scrittura su file.
	 */
	public void scriviStadi() throws IOException{
		ObjectOutputStream outputStadi = new ObjectOutputStream (new FileOutputStream(nomeFileStadi));
		for (Stadio s: elencoStadi){
			outputStadi.writeObject(s);
		}
		outputStadi.close();
	}
	
	
	/**
	 * Scrive tutti gli sconti su file.
	 * @throws IOException Se c'è un errore nella scrittura su file.
	 */
	public void scriviSconti() throws IOException{
		ObjectOutputStream outputSconti = new ObjectOutputStream (new FileOutputStream(nomeFileSconti));
		for (Sconto s: elencoSconti){
			outputSconti.writeObject(s);
		}
		outputSconti.close();
	}
	
	
	/**
	 * Per ogni biglietto della partita passata in input invoca il metodo per associarvi un cliente nella lista utenti.
	 * @param partitaDaAssociare La partita esaminata.
	 */
	private void associaBigliettiAClienti(Partita partitaDaAssociare) {
		Biglietto [][] bigliettiSettoreNord=partitaDaAssociare.getBigliettiSettoreNord();
		Biglietto [][] bigliettiSettoreSud = partitaDaAssociare.getBigliettiSettoreSud();
		Biglietto [][]bigliettiSettoreWest= partitaDaAssociare.getBigliettiSettoreWest();
		Biglietto [][] bigliettiSettoreEst= partitaDaAssociare.getBigliettiSettoreEst();
		
		for (int i=0; i<partitaDaAssociare.getTribuneCentraliHeight();i++)
			for (int j=0; j<partitaDaAssociare.getTribuneCentraliLength();j++)
				if (bigliettiSettoreNord[i][j]!=null) associaClienteDelBiglietto (bigliettiSettoreNord[i][j]);
		
		for (int i=0; i<partitaDaAssociare.getTribuneCentraliHeight();i++)
			for (int j=0; j<partitaDaAssociare.getTribuneCentraliLength();j++)
				if (bigliettiSettoreSud[i][j]!=null) associaClienteDelBiglietto (bigliettiSettoreSud[i][j]);
		
		for (int i=0; i<partitaDaAssociare.getTribuneLateraliHeight();i++)
			for (int j=0; j<partitaDaAssociare.getTribuneLateraliLength();j++)
				if (bigliettiSettoreWest[i][j]!=null) associaClienteDelBiglietto (bigliettiSettoreWest[i][j]);
		
		for (int i=0; i<partitaDaAssociare.getTribuneLateraliHeight();i++)
			for (int j=0; j<partitaDaAssociare.getTribuneLateraliLength();j++)
				if (bigliettiSettoreEst[i][j]!=null) associaClienteDelBiglietto (bigliettiSettoreEst[i][j]);
		
	}
	
	/**
	 * Associa al biglietto passato in input un cliente dalla lista utenti.
	 * @param bigliettoDaAssociare Il Biglietto da associare.
	 */
	private void associaClienteDelBiglietto(Biglietto bigliettoDaAssociare) {
		int i=0;
		while (i<elencoUtenti.size() && !bigliettoDaAssociare.getCliente().equals(elencoUtenti.get(i))) i++;
		if (i== elencoUtenti.size()) {
			JOptionPane.showMessageDialog(null, "L'utente del biglietto non esiste!", "Errore", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		else {
			bigliettoDaAssociare.setCliente((Cliente) elencoUtenti.get(i));
		}
	}
	
	




	private String nomeFileUtenti, nomeFileStadi,nomeFileSconti;
	private ArrayList <Utente> elencoUtenti;
	private ArrayList <Partita> elencoPartite;
	private ArrayList<Stadio> elencoStadi;
	private ArrayList <Sconto> elencoSconti;
}
