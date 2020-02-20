package misuratore;

import partite.*;
/**
 * Questa classe definisce il criterio di confronto per la misura secondo l'ordine lessicografico delle squadre delle due partite.
 * @author Mogavero
 *
 */
public class MisuratoreLessicograficoSquadre implements Misuratore {

	/**
	 * Se la squadra Casa della prima partita è minore kessicograficamente rispetto alla squadra casa della seconda partita restituisce -1,
	 * se invece risulta essere maggiore lessicograficamente restituisce 1, altrimenti se le due squadre casa sono uguali lessicograficamente
	 * passa ad esaminare con lo stesso criterio le squadre Trasferta: in caso vi sia nuovamente uguaglianza restituisce 0.
	 */
	@Override
	public int misura(Object obj1, Object obj2) {
		Partita partita1= (Partita) obj1;
		Partita partita2= (Partita) obj2;
		if (partita1.getSquadraCasa().compareTo(partita2.getSquadraCasa())<0)return -1;
		else if (partita1.getSquadraCasa().compareTo(partita2.getSquadraCasa())>0) return 1;
		else { //se le due squadre di casa sono uguali in entrambe le partite//
			if (partita1.getSquadraTrasferta().compareTo(partita2.getSquadraTrasferta())<0) return -1;
			else if (partita1.getSquadraTrasferta().compareTo(partita2.getSquadraTrasferta())>0) return 1;
			else return 0; //sia la squadra di casa che di trasferta sono uguali per entrambe le partite
		}
		
	}

}
