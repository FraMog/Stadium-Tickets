package misuratore;

import partite.*;

/**
 * Questa classe definisce il criterio di confronto per la misura secondo l'identificativo dello stadio di due partite.
 * @author Mogavero
 *
 */
public class MisuratoreIdentificativoStadio implements Misuratore{

	/**
	 * Questo metodo restituisce -1 se l'identificativo dello stadio della prima partita è minore di quello della seconda, 1 se l'identificativo dello stadio della prima partita è maggiore di quello della seconda, 0 se sono uguali.
	 */
	@Override
	public int misura(Object obj1, Object obj2) {
		Partita partita1= (Partita) obj1;
		Partita partita2= (Partita) obj2;
		
		if (partita1.getStadio().getIdentificativo()<partita2.getStadio().getIdentificativo()) return -1;
		else if (partita1.getStadio().getIdentificativo()>partita2.getStadio().getIdentificativo()) return 1;
		else return 0; //sono uguali
	}

}
