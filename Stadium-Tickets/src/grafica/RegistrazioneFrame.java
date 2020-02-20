package grafica;

import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import utenti.*;
import java.awt.*;
import java.awt.event.*;
import com.toedter.calendar.JDateChooser;
import eccezioni.*;

/**
 * Questa classe definisce il Frame per la registrazione di un nuovo utente.
 * @author Mogavero
 *
 */
public class RegistrazioneFrame extends JFrame {

	/**
	 * Crea un nuovo Frame Registrazione definendone dimensioni e pannelli.
	 * @param elencoUtenti Tutti gli utenti finora registrati al sistema.
	 */
	public RegistrazioneFrame(ArrayList<Utente> elencoUtenti) {
		this.elencoUtenti= elencoUtenti;
		
		
		JPanel pannelloInAlto= new JPanel();
		
		JLabel introduzione = new JLabel("Inserisci tutti i campi per completare la registrazione:");
		introduzione.setFont(new Font("Serif", Font.BOLD, 14));
		pannelloInAlto.add(introduzione);
		add(pannelloInAlto,BorderLayout.NORTH);
		
		
		
		JPanel panelloCentrale = new JPanel();
		add(panelloCentrale, BorderLayout.CENTER);
		panelloCentrale.setLayout(new GridLayout(6,2));
		
		JLabel nome = new JLabel("Nome:");
		nome.setFont(new Font("Serif", Font.BOLD, 14));
		panelloCentrale.add(nome);
		nomeField = new JTextField();
		panelloCentrale.add(nomeField);
		
		
		
		
		
		JLabel cognome = new JLabel("Cognome:");
		cognome.setFont(new Font("Serif", Font.BOLD, 14));
		panelloCentrale.add(cognome);
		cognomeField = new JTextField();
		panelloCentrale.add(cognomeField);
		
		
		
		
		
		JLabel username = new JLabel("Username:");
		username.setFont(new Font("Serif", Font.BOLD, 14));
		panelloCentrale.add(username);
		usernameField = new JTextField();
		panelloCentrale.add(usernameField);
		
		
		
		
		JLabel password = new JLabel("Password:");
		password.setFont(new Font("Serif", Font.BOLD, 14));
		panelloCentrale.add(password);
		passwordField = new JPasswordField();
		panelloCentrale.add(passwordField);
		
		
		
		
		JLabel ripetiPassword = new JLabel("Ripeti Password:");
		ripetiPassword.setFont(new Font("Serif", Font.BOLD, 14));
		panelloCentrale.add(ripetiPassword);
		ripetiPasswordField = new JPasswordField();
		panelloCentrale.add(ripetiPasswordField);
		
		
		
		
		JLabel dataNascita = new JLabel("Data di Nascita:");
		dataNascita.setFont(new Font("Serif", Font.BOLD, 14));
		panelloCentrale.add(dataNascita);
		sceltaData = new JDateChooser();
		sceltaData.setMaxSelectableDate(new Date());
		panelloCentrale.add(sceltaData);
		
		
		JPanel pannelloSud = creaPannelloSud();
		add (pannelloSud, BorderLayout.SOUTH);
		
		setTitle ("Registrazione");
		setResizable(false);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLocationRelativeTo(null);
	}
	
	
	
	

	
	/**
	 * Crea il pannello Sud per la scelta del tipo di cliente e il bottone per la registrazione.
	 * @return Il pannello Sud.
	 */
	private JPanel creaPannelloSud() {
		JPanel pannelloSud = new JPanel();
		pannelloSud.setLayout(new BorderLayout ());
		pannelloSud.add(creaPannelloRadioButtons(),BorderLayout.CENTER);
		pannelloSud.add(creaPannelloConfermaRegistrazione(),BorderLayout.SOUTH);
		
		return pannelloSud;
	}






	/** 
	 * Crea il pannello Radio Buttons con i radio Button cliente e gestore.
	 * @return Il Pannello Radio Buttons.
	 */
	private JPanel creaPannelloRadioButtons() {
		JPanel pannelloRadioButtons= new JPanel();
		pannelloRadioButtons.setBorder(new TitledBorder(new EtchedBorder(), "Registrati come"));
		
		clienteRadioButton = new JRadioButton("Cliente");
		pannelloRadioButtons.add(clienteRadioButton);
		gestoreRadioButton = new JRadioButton("Gestore");
		pannelloRadioButtons.add(gestoreRadioButton);
		ButtonGroup gruppo= new ButtonGroup();
		gruppo.add(clienteRadioButton);
		gruppo.add(gestoreRadioButton);
		clienteRadioButton.setSelected(true);
		return pannelloRadioButtons;
	}






	/**
	 * Crea il pannello con il bottone conferma registrazione.
	 * @return Il pannello conferma registrazione.
	 */
	private JPanel creaPannelloConfermaRegistrazione() {
		JPanel pannelloConfermaRegistrazione= new JPanel();
		confermaRegistrazione = new JButton("Conferma Registrazione!");
		pannelloConfermaRegistrazione.add(confermaRegistrazione);
		confermaRegistrazione.addActionListener(new ActionListener() {
			
			/**
			 * Se l'utente ha rispettato tutti i requisiti per potersi registrare, inserisce un nuovo utente nell'elenco utenti.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				String usernameScelto, passwordScelta, ripetiPasswordScelta;
				passwordScelta= passwordField.getText();
				ripetiPasswordScelta= ripetiPasswordField.getText();
				usernameScelto=usernameField.getText();
				boolean usernameGiaPresente=false;
				for (Utente utente: elencoUtenti){
					if (utente.getUsername().equals(usernameScelto)) usernameGiaPresente=true;
				}
				if (!passwordScelta.equals(ripetiPasswordScelta))
					JOptionPane.showMessageDialog(null, "La password scelta e la conferma non coincidono!","Errore",JOptionPane.ERROR_MESSAGE);
				
				else if (usernameGiaPresente) 
					JOptionPane.showMessageDialog(null, "L'username scelto è gia presente in archivio!","Errore",JOptionPane.ERROR_MESSAGE);
					
				else {
					if (clienteRadioButton.isSelected()){
						try {
							elencoUtenti.add(new Cliente(nomeField.getText(), cognomeField.getText(), usernameScelto, passwordScelta, (GregorianCalendar) sceltaData.getCalendar()));
							JOptionPane.showMessageDialog(null, "Registrazione completata con successo!");
							dispose();
						} 
						catch (IllegalDataException | IllegalNomeException | IllegalCognomeException
							| IllegalPasswordException | IllegalUsernameException e1) {
						
							JOptionPane.showMessageDialog(null, e1.getMessage(),"Errore",JOptionPane.ERROR_MESSAGE);
						}
					}	
					else {
						try {
							elencoUtenti.add(new Gestore(nomeField.getText(), cognomeField.getText(), usernameScelto, passwordScelta, (GregorianCalendar) sceltaData.getCalendar()));
							JOptionPane.showMessageDialog(null, "Registrazione completata con successo!");
							dispose();
						} 
						catch (IllegalDataException | IllegalNomeException | IllegalCognomeException
							| IllegalPasswordException | IllegalUsernameException e1) {
						
							JOptionPane.showMessageDialog(null, e1.getMessage(),"Errore",JOptionPane.ERROR_MESSAGE);
						}
						
					}
					
				}
				
			}
		});
		
		return pannelloConfermaRegistrazione;
	}







	public static final int FRAME_WIDTH = 400, FRAME_HEIGHT = 400;
	private ArrayList<Utente> elencoUtenti;
	private JTextField nomeField;
	private JTextField cognomeField;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPasswordField ripetiPasswordField;
	private JButton confermaRegistrazione;
	private JDateChooser sceltaData;
	private JRadioButton clienteRadioButton, gestoreRadioButton;

}
