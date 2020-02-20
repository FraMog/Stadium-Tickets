package misuratore;
import partite.*;

/**
 * Questa classe definisce il criterio di confronto per la misura secondo l'ordine cronologico di due partite.
 * @author Mogavero
 *
 */
public class MisuratoreCronologico implements Misuratore {

	/**
	 * Questo metodo restituisce -1 se la prima partita si svolge prima della seconda, 1 se la prima partita si svolge dopo la seconda, 0 se si svolgono contemporaneamente.
	 */
	@Override
	public int misura(Object obj1, Object obj2) {
		Partita partita1= (Partita) obj1;
		Partita partita2= (Partita) obj2;
		
		if (partita1.getData().before(partita2.getData())) return -1;
		else if (partita1.getData().after(partita2.getData())) return 1;
		else return 0; //le due date sono uguali
		
	}

	

}
