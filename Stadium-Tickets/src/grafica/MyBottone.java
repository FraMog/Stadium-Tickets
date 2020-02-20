package grafica;

import java.awt.Color;
import javax.swing.JButton;
import eccezioni.*;
import partite.*;
import partite.Biglietto.StatiPossibili;
import partite.Partita.SettoriPossibili;
import utenti.*;
/**
 * Questa classe definisce le caratteristiche di un Bottone da visualizzare nel PartitaFrame.
 * @author Mogavero
 *
 */
public class MyBottone extends JButton {

	/**
	 * Crea un nuovo Bottone riferito ad un Posto sul Campo di una determinata Partita, un possibile biglietto riferito al posto (può essere anche null se è il posto è ancora libero),
	 * e il clienteAttuale che sta usando il programma.
	 * @param settore Il settore del Posto del Bottone nel campo.
	 * @param riga La riga del posto di riferimento del bottone nell'array dei posti del settore. 
	 * @param colonna La colonna del posto di riferimento del bottone nell'array dei posti del settore. 
	 * @param biglietto Il biglietto associato al posto in questione.
	 * @param clienteAttuale Il cliente che sta attualmente usando il programma.
	 * @throws IllegalStatoBigliettoException Errore nella definizione dello stato del biglietto.
	 */
	public MyBottone(SettoriPossibili settore, int riga, int colonna, Biglietto biglietto, Cliente clienteAttuale) throws IllegalStatoBigliettoException {
		this.settore=settore;
		this.riga=riga;
		this.colonna=colonna;
		this.biglietto=biglietto;
		this.clienteAttuale=clienteAttuale;
		
		setSfondo();
		
	}
	
	/**
	 * Imposta lo sfondo del bottone a seconda che il posto ad esso associato sia libero, gia stato acquistato, prenotato dallo stesso cliente che sta usando in questo momento 
	 * il programma o da un altro cliente.
	 * @throws IllegalStatoBigliettoException Errore nella definizione dello stato del biglietto.
	 */
	private void setSfondo() throws IllegalStatoBigliettoException{
		if (biglietto==null){// il posto è ancora libero
			setBackground(Color.GREEN);
		}
		else{
			StatiPossibili statoDelBiglietto= biglietto.getStato();		
			switch (statoDelBiglietto){
				case ACQUISTATO: setBackground(Color.RED); break;
				case PRENOTATO:{
					Cliente possessoreDelBiglietto= biglietto.getCliente();
				
					if (possessoreDelBiglietto.equals(clienteAttuale)){
						setBackground(Color.YELLOW);
						break;
					}	
					else {
						setBackground(Color.BLUE);
						break;
					}
					
				}
				default: throw new IllegalStatoBigliettoException("Errore nella definizione dello stato dei biglietti!");
			}	
			
		}
	}
	

	/**
	 * Modifica il biglietto associato a questo posto.
	 */
	public void setBiglietto(Biglietto biglietto) {
		this.biglietto = biglietto;
	}

	

	/**
	 * Restituisce il settore del bottone.
	 * @return Il settore del bottone.
	 */
	public SettoriPossibili getSettore() {
		return settore;
	}

	
	/**
	 * Restituisce la riga del bottone nell'array di posti associati al settore del bottone.
	 * @return La riga del bottone.
	 */
	public int getRiga() {
		return riga;
	}

	
	/**
	 * Restituisce la colonna del bottone nell'array di posti associati al settore del bottone.
	 * @return La colonna del bottone.
	 */
	public int getColonna() {
		return colonna;
	}

	
	/**
	 * Restituisce il biglietto associato al bottone.
	 * @return Il biglietto associato al bottone.
	 */
	public Biglietto getBiglietto() {
		return biglietto;
	}




	private SettoriPossibili settore;
	private int riga;
	private int colonna;
	private Biglietto biglietto;
	private Cliente clienteAttuale;
}
