package misuratore;

import partite.*;
/**
 * Questa classe definisce il criterio di confronto per la misura secondo la capienza dello stadio di due partite.
 * @author Mogavero
 *
 */
public class MisuratoreCapienzaStadi implements Misuratore {

	/**
	 * Questo metodo restituisce -1 se la capienza della prima partita è inferiore a quella della seconda, 1 se la capienza della prima partita è identica a quella della seconda, 0 se sono uguali.
	 */
	@Override
	public int misura(Object obj1, Object obj2) {
		Partita partita1= (Partita) obj1;
		Partita partita2= (Partita) obj2;
		
		if (partita1.getCapienza()<partita2.getCapienza()) return -1;
		else if (partita1.getCapienza()>partita2.getCapienza()) return 1;
		else return 0; //le capienze degli stadi sono uguali
	}

}
