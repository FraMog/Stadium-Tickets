package misuratore;

/**
 * Questa interfaccia definisce un metodo misura che dovranno avere tutte le classi che la estendono.
 * @author Mogavero
 *
 */
public interface Misuratore {
	/**
	 * Il metodo misura dovr� confrontare due oggetti, dar� -1 se il primo � minore del secondo, 1 se il primo � maggiore del secondo, 0 se sono uguali.
	 * @param obj1 Il primo oggetto da confrontare.
	 * @param obj2 Il secondo oggetto da confrontare.
	 * @return Il risultato del confronto.
	 */
	public int misura (Object obj1, Object obj2);
}
