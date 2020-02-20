package main;

import grafica.LoginFrame;

/**
 * Questa classe lancia il programma.
 */
public class Main {

	/**
	 * Il metodo main crea e rende visibile il frame login.
	 * @param args Parametri in ingresso.
	 */
	public static void main(String[] args) {
		LoginFrame loginFrame= new LoginFrame();
		loginFrame.setVisible(true);

	}
}