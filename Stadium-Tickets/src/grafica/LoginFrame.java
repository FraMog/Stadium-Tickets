package grafica;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import partite.*;
import sconto.Sconto;
import stream.*;
import utenti.*;

/**
 * Questa classe definisce il frame Login Frame
 * @author Mogavero
 *
 */
public class LoginFrame extends JFrame {

	/**
	 * Crea un nuovo Frame definendone la dimensioni, il pannello nord, center e sud e legge i dati in input da File tramite lo stream.
	 */
	public LoginFrame() {
		
		elencoStadi = new ArrayList <Stadio>();
		elencoPartite = new ArrayList<Partita>();
		elencoUtenti = new ArrayList<Utente>();
		elencoSconti= new ArrayList<Sconto>();
		stream = new Stream("utenti.txt", "stadi.txt","sconti.txt", elencoUtenti, elencoPartite,elencoStadi,elencoSconti);
		leggi();

		JLabel benvenuto = new JLabel("Benvenuto nel Sistema");
		benvenuto.setFont(new Font(Font.SERIF, Font.BOLD, 24));

		JPanel pannelloInAlto = new JPanel();
		pannelloInAlto.add(benvenuto);
		add(pannelloInAlto, BorderLayout.NORTH);
		JPanel pannelloCentrale = new JPanel();
		pannelloCentrale.setBorder(new TitledBorder(new EtchedBorder(), "Login"));
		add(pannelloCentrale, BorderLayout.CENTER);
		//imposto il layout del pannelloCentrale anch'esso come BorderLayout
		pannelloCentrale.setLayout(new BorderLayout());
		
		//Creo il pannello dei campi login e password aggiungendolo come CENTER nel pannello centrale
		JPanel pannelloCentraleCampiLogin= new JPanel();
		pannelloCentraleCampiLogin.setLayout(new GridLayout(2,1));
		pannelloCentrale.add(pannelloCentraleCampiLogin, BorderLayout.CENTER);

		JPanel pannelloCampoDati1 = new JPanel(), pannelloCampoDati2 = new JPanel();
		pannelloCentraleCampiLogin.add(pannelloCampoDati1);
		pannelloCentraleCampiLogin.add(pannelloCampoDati2);
		pannelloCampoDati1.setLayout(new GridLayout(1, 2));
		pannelloCampoDati2.setLayout(new GridLayout(1, 2));

		JLabel username = new JLabel("Username:"), password = new JLabel("Password:");
		username.setFont(new Font(Font.SERIF, Font.BOLD, 14));
		password.setFont(new Font(Font.SERIF, Font.BOLD, 14));
		pannelloCampoDati1.add(username);

		textFieldUsername = new JTextField();
		pannelloCampoDati1.add(textFieldUsername);
		pannelloCampoDati2.add(password);

		textFieldPassword = new JPasswordField();
		pannelloCampoDati2.add(textFieldPassword);

		JPanel pannelloLogin = creaPannelloLogin();
		pannelloCentrale.add(pannelloLogin,BorderLayout.SOUTH);
		
		JPanel pannelloRegistrazione= creaPannelloRegistrazione();
		add(pannelloRegistrazione, BorderLayout.SOUTH);
		
		setTitle("Gestione Stadi");
		setResizable(false);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/**
		 * Definisce il comportamento che la finestra deve avere al momento della chiusura.
		 */
		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {

			}

			@Override
			public void windowIconified(WindowEvent e) {

			}

			@Override
			public void windowDeiconified(WindowEvent e) {

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			/**
			 * Al momento della chiusura della finestra vanno scritti tutti i dati su file tramite lo stream.
			 */
			@Override
			public void windowClosing(WindowEvent e) {
				scrivi();
			}

			/**
			 * Al momento della chiusura della finestra vanno scritti tutti i dati su file tramite lo stream.
			 */
			@Override
			public void windowClosed(WindowEvent e) {
				scrivi();

			}

			@Override
			public void windowActivated(WindowEvent e) {

			}
		});
	}


	/**
	 * Questo metodo legge utenti, stadi e sconti da file tramite lo stream.
	 */
	private void leggi() {

		try {
			stream.leggiUtenti();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Errore nella lettura del file utenti, file inesistente","Errore",JOptionPane.ERROR_MESSAGE);
		} catch (EOFException e) {
			// letto tutto
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Mismatch tra classe del programma e classe del file in input utenti","Errore",JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(),"Errore",JOptionPane.ERROR_MESSAGE);
		}
		
		try {
			stream.leggiStadi();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Errore nella lettura del file stadi, file inesistente","Errore",JOptionPane.ERROR_MESSAGE);
		} catch (EOFException e) {
			// letto tutto
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Mismatch tra classe del programma e classe del file in input stadi","Errore",JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(),"Errore",JOptionPane.ERROR_MESSAGE);
		}
		
		try {
			stream.leggiSconti();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Errore nella lettura del file sconti, file inesistente","Errore",JOptionPane.ERROR_MESSAGE);
		} catch (EOFException e) {
			// letto tutto
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Mismatch tra classe del programma e classe del file in input sconti","Errore",JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(),"Errore",JOptionPane.ERROR_MESSAGE);
		}
		
		
	}

	/**
	 * Questo metodo scrive tutti i dati su file tramite stream.
	 */
	private void scrivi() {
		try {
			stream.scriviUtenti();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e,"Errore",JOptionPane.ERROR_MESSAGE);
		}
		
		try {
			stream.scriviStadi();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e,"Errore",JOptionPane.ERROR_MESSAGE);
		}
		
		try {
			stream.scriviSconti();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e,"Errore",JOptionPane.ERROR_MESSAGE);
		}
		
	}

	
	/**
	 * Crea il Pannello login centrale.
	 * @return Il pannello Login centrale.
	 */
	private JPanel creaPannelloLogin(){
		JPanel pannelloLogin= new JPanel();
		login= new JButton ("Login");
		pannelloLogin.add(login);
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String login= textFieldUsername.getText();
				String password= textFieldPassword.getText();
				int i=0;
				boolean trovato=false;
				while (i<elencoUtenti.size() && !trovato){
					if (elencoUtenti.get(i).controllaCredenzialiAccesso(login, password) && (elencoUtenti.get(i) instanceof Cliente)){
						ClienteFrame clienteFrame= new ClienteFrame((Cliente) elencoUtenti.get(i),elencoPartite, elencoStadi);
						clienteFrame.addWindowListener(new ChiudiFinestreInterne());
						clienteFrame.setVisible(true);
						setVisible(false);
						trovato=true;
						}
					else if (elencoUtenti.get(i).controllaCredenzialiAccesso(login, password) && (elencoUtenti.get(i) instanceof Gestore)){
						GestoreFrame gestoreFrame= new GestoreFrame(elencoPartite, elencoStadi,elencoSconti, (Gestore) elencoUtenti.get(i));	
						gestoreFrame.addWindowListener(new ChiudiFinestreInterne());
						gestoreFrame.setVisible(true);
						setVisible(false);
						trovato=true;
						}
					i++;
				}
				if (i==elencoUtenti.size() && !trovato)
					JOptionPane.showMessageDialog(null, "Credenziali di accesso errate","Errore", JOptionPane.ERROR_MESSAGE);
				
			}
		});
		
		
		return pannelloLogin;
	}
	
	
	/**
	 * Crea il pannello per la registrazione.
	 * @return Il pannello per la registrazione.
	 */
	private JPanel creaPannelloRegistrazione(){
		JPanel panelloRegistrazione = new JPanel();
		panelloRegistrazione.setBorder(new TitledBorder(new EtchedBorder(), "Registrazione"));
		add(panelloRegistrazione, BorderLayout.SOUTH);

		registrazione = new JButton("Registrati");
		panelloRegistrazione.add(registrazione);
		registrazione.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RegistrazioneFrame registrazioneFrame= new RegistrazioneFrame(elencoUtenti);
				registrazioneFrame.setVisible(true);
				registrazioneFrame.addWindowListener(new ChiudiFinestreInterne());
				setVisible(false);
				
			}
		});
		
		
		
		return panelloRegistrazione;
	}
	
	
	
	/**
	 * Questa classe serve a definire le operazioni che devono essere svolte dalle finestre che verranno aperte da questa in caso esse vengano chiuse.
	 * @author Mogavero
	 *
	 */
	class ChiudiFinestreInterne implements WindowListener{

		@Override
		public void windowActivated(WindowEvent arg0) {
		}

		/**
		 * Quando una finestra aperta da questa viene chiusa bisogna di nuovo rendere visibile questa finestra.
		 */
		@Override
		public void windowClosed(WindowEvent arg0) {
			textFieldUsername.setText("");
			textFieldPassword.setText("");
			setVisible(true);
			
		}

		/**
		 * Quando una finestra aperta da questa viene chiusa bisogna di nuovo rendere visibile questa finestra.
		 */
		@Override
		public void windowClosing(WindowEvent arg0) {
			textFieldUsername.setText("");
			textFieldPassword.setText("");
			setVisible (true);
			
		}

	
		/**
		 * Quando una finestra aperta da questa viene deattivata (ad esempio l'esecuzione del programma viene interrotta improvvisamente) bisogna scrivere tutto su file tramite stream.
		 */
		@Override
		public void windowDeactivated(WindowEvent arg0) {
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
			
		}

		@Override
		public void windowIconified(WindowEvent arg0) {

			
		}

		@Override
		public void windowOpened(WindowEvent arg0) {
			
		}
		
	}
	

	
	public static final int FRAME_WIDTH = 300, FRAME_HEIGHT = 300;
	private JTextField textFieldUsername, textFieldPassword;
	private JButton registrazione, login;
	private ArrayList<Stadio> elencoStadi;
	private ArrayList<Partita> elencoPartite;
	private ArrayList <Sconto> elencoSconti;
	private ArrayList<Utente> elencoUtenti;
	private Stream stream;
}
