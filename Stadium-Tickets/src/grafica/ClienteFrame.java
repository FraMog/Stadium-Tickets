package grafica;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import com.toedter.calendar.JDateChooser;
import eccezioni.*;
import misuratore.*;
import partite.*;
import stream.Stream;
import utenti.*;

/**
 * Questa classe definisce il frame Home Cliente
 * @author Mogavero
 *
 */
public class ClienteFrame extends JFrame {

	/**
	 * Crea un nuovo Frame Cliente, definendone dimensioni, titolo, Jmenubar, pannello nord, center e sud.
	 * @param clienteInput Il cliente che attualmente sta usando il programma.
	 * @param elencoPartite L'elenco di tutte le partite programmate.
	 * @param elencoStadi L'elenco di tutti gli stadi presenti in archivio.
	 */
	public ClienteFrame(Cliente clienteInput, ArrayList<Partita> elencoPartite, ArrayList <Stadio> elencoStadi) {
		cliente=clienteInput;
		this.elencoPartite=elencoPartite;
		this.elencoStadi= elencoStadi;
		misuratore=null;
		
		
		
		creaJMenuBar();
		riassegnaPartitaAPrenotazioniAttive();
		JPanel pannelloNord= new JPanel();
		add (pannelloNord,BorderLayout.NORTH);
		JLabel benvenuto = new JLabel("Benvenuto/a " + cliente.getNome() + " " + cliente.getCognome());
		benvenuto.setFont(new Font("Serif", Font.BOLD, 22));
		pannelloNord.add(benvenuto);
		
		
		
		add (creaScrollCentrale(),BorderLayout.CENTER);
		add (creaPannelloSud(), BorderLayout.SOUTH);
		
		setTitle("Home Cliente");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(0, 0, getToolkit().getScreenSize().width,getToolkit().getScreenSize().height);
		
	}

	





	/**
	 * Crea la JMenuBar per la classe ClienteFrame.
	 */
	private void creaJMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		JMenuItem esciItem = new JMenuItem("Esci");
		esciItem.addActionListener(new ActionListener() {
			/**
			 * Esce dal Frame.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
		fileMenu.add(esciItem);
		
		JMenu visualizzaMenu = new JMenu("Visualizza");
		menuBar.add(visualizzaMenu);
		partiteStadioMenu = new JMenu("Lista Partite di Uno Stadio");
		visualizzaMenu.add(partiteStadioMenu);
		for (Stadio s: elencoStadi)
			creaItemMenuStadio(s);
		
		JMenuItem acquistiEffettutatiMenuItem = new JMenuItem("Acquisti Effettutati");
		visualizzaMenu.add(acquistiEffettutatiMenuItem);
		acquistiEffettutatiMenuItem.addActionListener(new ActionListener() {
			
			/**
			 * Mostra tutti i biglietti acquistati dal cliente.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				areaTesto.setText(null);
				ArrayList <Biglietto> bigliettiAcquistatiDalCliente= cliente.getBigliettiAcquistati();
				if (bigliettiAcquistatiDalCliente.size()==0) areaTesto.append("Non hai ancora acquistato alcun biglietto!\n");
				else {
					for (Biglietto biglietto: bigliettiAcquistatiDalCliente) areaTesto.append(biglietto.mostraDettagli() + "\n");
				}
				
			}
		});
		
		JMenuItem prenotazioniEffettuateMenuItem = new JMenuItem("Prenotazioni Effettuate");
		visualizzaMenu.add(prenotazioniEffettuateMenuItem);
		
		
		prenotazioniEffettuateMenuItem.addActionListener(new ActionListener() {
			
		
			/**
			 * Mostra tutte le prenotazioni effettuate dal cliente.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				areaTesto.setText(null);
				
				controllaScadenzaPrenotazioniAttive(); //Inizialmente controllo che le eventuali prenotazioni attive del cliente siano ancora valide
				ArrayList <Biglietto> prenotazioniDelClienteAttive = cliente.getPrenotazioniAttive();
				ArrayList <Biglietto> prenotazioniDelClienteScadute = cliente.getPrenotazioniScadute();
				ArrayList <Biglietto> prenotazioniDelClienteAcquistate = cliente.getPrenotazioniAcquistate();
				ArrayList <Biglietto> prenotazioniDelClienteCancellate= cliente.getPrenotazioniCancellate();
				if (prenotazioniDelClienteAttive.size()==0 && prenotazioniDelClienteScadute.size()==0 && prenotazioniDelClienteAcquistate.size()==0 && prenotazioniDelClienteCancellate.size()==0) 
					areaTesto.append("Non hai ancora prenotato alcun biglietto!\n");
				else{ //se ho almeno una prenotazione attiva o scaduta
					if (prenotazioniDelClienteAttive.size()==0) areaTesto.append("Non hai nessuna prenotazione attiva al momento\n");
					else {//ho almeno una prenotazione attiva
						areaTesto.append("Le prenotazioni che hai effettuato e sono ancora attive sono:\n");
						scriviElencoPrenotazioni(prenotazioniDelClienteAttive);
					}
					if (prenotazioniDelClienteAcquistate.size()==0) areaTesto.append("Non hai nessuna prenotazione che poi hai acquistato al momento\n");
					else {//ho almeno una prenotazione attiva
						areaTesto.append("Le prenotazioni che hai effettuato e poi acquistato sono:\n");
						scriviElencoPrenotazioni(prenotazioniDelClienteAcquistate);
					}
					if (prenotazioniDelClienteScadute.size()==0) areaTesto.append("Non hai nessuna prenotazione scaduta al momento\n");
					else {//ho almeno una prenotazione attiva
						areaTesto.append("Le prenotazioni che hai effettuato e sono scadute sono:\n");
						scriviElencoPrenotazioni(prenotazioniDelClienteScadute);
					}
					if (prenotazioniDelClienteCancellate.size()==0) areaTesto.append("Non hai nessuna prenotazione che hai cancellato al momento\n");
					else {//ho almeno una prenotazione attiva
						areaTesto.append("Le prenotazioni che hai effettuato e poi cancellato sono:\n");
						scriviElencoPrenotazioni(prenotazioniDelClienteCancellate);
					}
				}
				
			}
		});
		
	}
	
	/**
	 * Questo metodo mi risetta le partite di tutte le prenotazioni attive del cliente per evitare inconsistenze.
	 */
	private void riassegnaPartitaAPrenotazioniAttive() {
		for (Biglietto biglietto: cliente.getPrenotazioniAttive()){
			int i=0;
			while (i<elencoPartite.size() && !biglietto.getPartita().equals(elencoPartite.get(i))) i++;
			if (i== elencoPartite.size()) {
				JOptionPane.showMessageDialog(null, "La partita del biglietto non esiste!", "Errore", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
			else {
				biglietto.setPartita(elencoPartite.get(i));
			}
		}
		
	}
	
	/**
	 * Questo metodo invoca il controllo di tutte le prenotazioni attive del cliente.
	 */
	private void controllaScadenzaPrenotazioniAttive(){
		try {
			cliente.controllaValiditaPrenotazioniAttive();
		} catch (IllegalStatoBigliettoException | IllegalPrenotazioneException
				| IllegalCancellazioneBigliettoException e) {
			JOptionPane.showMessageDialog(null,e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		} 
	}
	
	
	
	/**
	 * Crea l'item della JMenuBar per lo stadio di riferimento.
	 * @param stadio Lo stadio di riferimento.
	 */
	private void creaItemMenuStadio(final Stadio stadio){
		JMenuItem itemStadioMenu= new JMenuItem(stadio.toString());
		itemStadioMenu.addActionListener(new ActionListener() {
			
			/**
			 * Questo metodo invoca la scrittura di tutte le partite disputate in uno stadio
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				scriviPartiteDiUnoStadio(stadio);
			}
		});
		
		partiteStadioMenu.add(itemStadioMenu);
	}
	
	
	
	
	/**
	 * Scrive nell'area di testo tutte le partite di un determinato stadio di riferimento.
	 * @param stadio Lo stadio di riferimento.
	 */
	private void scriviPartiteDiUnoStadio(Stadio stadio){
		areaTesto.setText(null);
		ArrayList <Partita> partitedelloStadio=stadio.getElencoPartiteStadio();
		if (partitedelloStadio.size()==0)
			areaTesto.append("Non ci sono al momento in archivio partite che si svolgono nello stadio " + stadio.getNome());
		else{
			for (Partita partita: partitedelloStadio){
				areaTesto.append(partita.mostraDettagli() + "\n");
			}
		}
	}
	
	

	/**
	 * Scrive l'elenco delle prenotazioni del cliente scelto in area testo.
	 * @param prenotazioniDaScrivere L'elenco delle prenotazioni da scrivere scelto.
	 */
	private void scriviElencoPrenotazioni(ArrayList <Biglietto> prenotazioniDaScrivere){
		for (Biglietto biglietto: prenotazioniDaScrivere) areaTesto.append(biglietto.mostraDettagli() + "\n");
	}
	
	/**
	 * Crea l'area testo e la scrollPane centrale.
	 * @return La scrollPane centrale.
	 */
	private JScrollPane creaScrollCentrale(){
		areaTesto= new JTextArea();
		areaTesto.setEditable(false);
		scrollPane=new JScrollPane(areaTesto);
		return scrollPane;
	}
	
	/**
	 * Definisce il pannello Sud.
	 * @return Il pannello Sud.
	 */
	private JPanel creaPannelloSud(){
		JPanel pannelloSud = new JPanel();
		pannelloSud.setLayout(new FlowLayout());
		
		pannelloSud.add(creaPannelloPartite());
		pannelloSud.add(creaPannelloPartiteSettimanali());
		return pannelloSud;
	}
	
	
	/**
	 * Crea il pannello partite.
	 * @return Il pannello Partite.
	 */
	private JPanel creaPannelloPartite() {
		JPanel pannelloPartite = new JPanel();
		pannelloPartite.setLayout(new BorderLayout());
		pannelloPartite.setBorder(new TitledBorder(new EtchedBorder(), "Acquista/Prenota Biglietti"));
		
		
		JPanel pannelloPartiteNord = new JPanel();
		pannelloPartite.add(pannelloPartiteNord, BorderLayout.NORTH);
		pannelloPartiteNord.setLayout(new BorderLayout());

		
		JPanel pannelloRiga1 = new JPanel();
		pannelloRiga1.setBorder(new TitledBorder(new EtchedBorder(), "Come vuoi ordinare l'elenco delle partite?"));
		pannelloPartiteNord.add(pannelloRiga1, BorderLayout.NORTH);
		
		stadioRadioButton = new JRadioButton("Per Stadio");
		stadioRadioButton.addActionListener(new ActionListener() {
			/**
			 * Crea un nuovo misuratore per identificativo stadio e invoca l'ordinamento nella combo box Partite.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				misuratore= new MisuratoreIdentificativoStadio();
				ordinaComboBox();
			}
		});
		stadioRadioButton.setSelected(true);
		pannelloRiga1.add(stadioRadioButton);
		
		ordinamentoCrologicoRadioButton = new JRadioButton("Cronologicamente");
		ordinamentoCrologicoRadioButton.addActionListener(new ActionListener() {
			
			/**
			 * Crea un nuovo misuratore cronologico e invoca l'ordinamento nella combo box Partite.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				misuratore= new MisuratoreCronologico();
				ordinaComboBox();
				
			}
		});
		pannelloRiga1.add(ordinamentoCrologicoRadioButton);
		
		squadreRadioButton = new JRadioButton("Per ordine di Squadre");
		squadreRadioButton.addActionListener(new ActionListener() {
			
			/**
			 * Crea un nuovo misuratore per ordine lessicografico delle squadre e invoca l'ordinamento nella combo box Partite.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				misuratore= new MisuratoreLessicograficoSquadre();
				ordinaComboBox();
				
			}
		});
		pannelloRiga1.add(squadreRadioButton);
		
		ButtonGroup gruppoOrdinamentoPartite= new ButtonGroup();
		gruppoOrdinamentoPartite.add(stadioRadioButton);
		gruppoOrdinamentoPartite.add(ordinamentoCrologicoRadioButton);
		gruppoOrdinamentoPartite.add(squadreRadioButton);
		
		
		JPanel pannelloRiga2 = new JPanel();
		pannelloPartiteNord.add(pannelloRiga2,BorderLayout.CENTER);
		JLabel elencoPartite= new JLabel ("Elenco partite non iniziate: ");
		partiteNonIniziate= new JComboBox <Partita>();
		partiteNonIniziate.setPreferredSize(new Dimension(300,30));
		pannelloRiga2.add(elencoPartite);
		pannelloRiga2.add(partiteNonIniziate);
		misuratore= new MisuratoreIdentificativoStadio();
		ordinaComboBox();//inizialmente ordino per identificativo stadio, che è quello selezionato per default
		
		
		JPanel pannelloRiga3 = new JPanel();
		pannelloPartiteNord.add(pannelloRiga3,BorderLayout.SOUTH);
		pannelloRiga3.setBorder(new TitledBorder(new EtchedBorder(), "Cosa vuoi fare?"));
		
		acquistaPrenotaRadioButton = new JRadioButton("Acquista/Prenota Biglietto");
		acquistaPrenotaRadioButton.setSelected(true);
		pannelloRiga3.add(acquistaPrenotaRadioButton);
		
		mostraDettagliRadioButton = new JRadioButton("Mostra Dettagli Partita");
		pannelloRiga3.add(mostraDettagliRadioButton);
		
		
		
		ButtonGroup gruppoSceltaOperazione= new ButtonGroup();
		gruppoSceltaOperazione.add(acquistaPrenotaRadioButton);
		gruppoSceltaOperazione.add(mostraDettagliRadioButton);
		
		JPanel pannelloPartiteBottoneEsegui = new JPanel();
		pannelloPartite.add(pannelloPartiteBottoneEsegui,BorderLayout.SOUTH);
		
		eseguiButton = new JButton("Esegui!");
		if (partiteNonIniziate.getSelectedItem()==null)
			eseguiButton.setEnabled(false);
		eseguiButton.addActionListener(new ActionListener() {
			
			/**
			 * Esegue l'operazione selezionata dal cliente (acquistare/prenotare un biglietto o visualizzare i dettagli partita).
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				Partita partitaSelezionata= (Partita)partiteNonIniziate.getSelectedItem();
				if (!partitaSelezionata.controllaSeNonIniziata()){
					JOptionPane.showMessageDialog(null, "Ci dispiace ma la partita che hai selezionato non è più disponibile");
					partiteNonIniziate.removeItem(partitaSelezionata);
					if (partiteNonIniziate.getSelectedItem()==null) eseguiButton.setEnabled(false);
				}
				else{//se la partita è ancora disponibile
					if (acquistaPrenotaRadioButton.isSelected()){
						setVisible(false);
						try {
							partitaSelezionata.checkValiditaBigliettiPrenotati();
						} catch ( PrenotazioneInesistenteException| IllegalStatoBigliettoException | 
								IllegalPrenotazioneException e1) {
						
							e1.printStackTrace();
						}
						PartitaFrame partitaFrame= new PartitaFrame (partitaSelezionata,cliente);
						partitaFrame.setVisible(true);
						partitaFrame.addWindowListener(new ChiudiFinestreInterne());
					}
					else{ //mostraDettagliRadioButton è selezionato
						areaTesto.setText(null);
						areaTesto.append(partitaSelezionata.mostraDettagli());
					}
					
				}
				
			}
		});
		pannelloPartiteBottoneEsegui.add(eseguiButton);
		return pannelloPartite;
	}


	/**
	 * Ordina la comboBox partite secondo il criterio di misurazione attuale.
	 */
	private void ordinaComboBox() {
		partiteNonIniziate.removeAllItems();
		if (elencoPartite.size()>0){
			ArrayList <Partita> partiteNonIniziateOrdinate= new ArrayList <Partita>();	
		//prima mi creo un array ordinato secondo il criterio scelto
			for (Partita partita: elencoPartite){//prendo tutte le partite dell'elenco partite (una alla volta e se non sono gia iniziate le confronto con quelle che ho gia inserito nell'array ordinato)
				if (partita.controllaSeNonIniziata()){
					int i=0;
					while (i<partiteNonIniziateOrdinate.size() && misuratore.misura(partiteNonIniziateOrdinate.get(i), partita)<0)
						i++;
					partiteNonIniziateOrdinate.add(i, partita);
				}
			}
		//ora scrivo l'elenco delle partite ordinate secondo l'ordine scelta
			for (Partita partita: partiteNonIniziateOrdinate)
				partiteNonIniziate.addItem(partita);
		}
		
	}


	/**
	 * Crea il pannello Delle partite Settimanali
	 * @return
	 */
	private JPanel creaPannelloPartiteSettimanali() {
		JPanel pannelloPartiteSettimana= new JPanel();
		pannelloPartiteSettimana.setPreferredSize(new Dimension(300, 215));
		pannelloPartiteSettimana.setLayout(new BorderLayout());
		pannelloPartiteSettimana.setBorder(new TitledBorder(new EtchedBorder(), "Mostra Partite di una settimana"));
		
		JPanel pannelloSceltaGiorni= new JPanel();
		pannelloPartiteSettimana.add(pannelloSceltaGiorni,BorderLayout.CENTER);
		pannelloSceltaGiorni.setLayout(new GridLayout(2,1));
		
		
		JPanel pannelloSceltaGiorniRiga1= new JPanel();
		pannelloSceltaGiorni.add(pannelloSceltaGiorniRiga1);
		pannelloSceltaGiorniRiga1.setBorder(new TitledBorder(new EtchedBorder(), "Seleziona un Lunedi"));
		pannelloSceltaGiorniRiga1.setLayout(new GridLayout(1,2));
		JLabel label1= new JLabel ("dal ");
		pannelloSceltaGiorniRiga1.add(label1);
		sceltaData1= new JDateChooser();
		pannelloSceltaGiorniRiga1.add(sceltaData1);
		sceltaData1.addPropertyChangeListener(new PropertyChangeListener() {
			
			/**
			 * Quando viene selezionato un nuovo giorno di inizio settimana questo metodo inserisce tutte le informazioni relative alla settimana scelta nei vari campi.
			 */
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
			GregorianCalendar giornoSelezionato= (GregorianCalendar) sceltaData1.getCalendar(); //l'orario conta
				if (giornoSelezionato!=null){
					if (giornoSelezionato.get(GregorianCalendar.DAY_OF_WEEK)!= GregorianCalendar.MONDAY){
						sceltaData1.setCalendar(null);
						sceltaData2.setCalendar(null);
						JOptionPane.showMessageDialog(null, "Seleziona un Lunedi!", "Errore", JOptionPane.ERROR_MESSAGE);
						inizioSettimana=null;
						fineSettimana=null;
					}
					else{
						//Imposto il giorno selezionato per l'inizio della settimana come Lunedi alle ore 00:00 (anche se dovrebbe farlo di default)
						giornoSelezionato= new GregorianCalendar(giornoSelezionato.get(GregorianCalendar.YEAR), giornoSelezionato.get(GregorianCalendar.MONTH), giornoSelezionato.get(GregorianCalendar.DAY_OF_MONTH));//cosi lo imposto alle 00:00:00
						//Imposto il giorno di fine settimana inizialmente come lo stesso Lunedi ma alle ore 23:59:59
						GregorianCalendar giornoDiFineSettimana= new GregorianCalendar(giornoSelezionato.get(GregorianCalendar.YEAR), giornoSelezionato.get(GregorianCalendar.MONTH), giornoSelezionato.get(GregorianCalendar.DAY_OF_MONTH),23,59,59);
					
						//Scorro il giorno di fine settimana in avanti finche non giungo ad una domenica
						while (giornoDiFineSettimana.get(GregorianCalendar.DAY_OF_WEEK)!=GregorianCalendar.SUNDAY){
							giornoDiFineSettimana.add(GregorianCalendar.DAY_OF_MONTH, 1);
						}
						
						inizioSettimana= giornoSelezionato;
						fineSettimana= giornoDiFineSettimana;
						sceltaData2.setCalendar(giornoDiFineSettimana);
					}
				}
			}
		});
		
		JPanel pannelloSceltaGiorniRiga2= new JPanel();
		pannelloSceltaGiorniRiga2.setLayout(new GridLayout(1,2));
		pannelloSceltaGiorniRiga2.setBorder(new EtchedBorder());
		pannelloSceltaGiorni.add(pannelloSceltaGiorniRiga2);
		JLabel label2= new JLabel ("al ");
		pannelloSceltaGiorniRiga2.add(label2);
		sceltaData2= new JDateChooser();
		pannelloSceltaGiorniRiga2.add(sceltaData2);
		
		sceltaData2.setEnabled(false);
		
		
		JPanel pannelloBottoneMostra= new JPanel();
		pannelloPartiteSettimana.add(pannelloBottoneMostra, BorderLayout.SOUTH);
		JButton mostraButton= new JButton("Mostra!");
		mostraButton.addActionListener(new ActionListener() {
			
			/**
			 * Mostra le partite della settimana scelta in area testo.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if (inizioSettimana==null || fineSettimana==null) 
					JOptionPane.showMessageDialog(null, "Inserisci una data valida", "Errore", JOptionPane.ERROR_MESSAGE);
				else {
					areaTesto.setText(null);
					ArrayList <Partita> partiteSettimanali= new ArrayList <Partita>();
					for (Partita partita: elencoPartite){
						if (partita.compresaFra(inizioSettimana,fineSettimana))
							partiteSettimanali.add(partita);
					}
					if (partiteSettimanali.size()==0) areaTesto.append("Non ci sono partite programmate nella settimana selezionata");
					else{
						String stringInizioSettimana= "" +  inizioSettimana.get(GregorianCalendar.DAY_OF_MONTH) + "/" + inizioSettimana.get(GregorianCalendar.MONTH) + "/" + inizioSettimana.get(GregorianCalendar.YEAR);
						String stringFineSettimana= "" +  fineSettimana.get(GregorianCalendar.DAY_OF_MONTH) + "/" + fineSettimana.get(GregorianCalendar.MONTH) + "/" + fineSettimana.get(GregorianCalendar.YEAR);
						areaTesto.append("L'elenco delle partite comprese fra il " + stringInizioSettimana + " e il " + stringFineSettimana + " è:\n");
						for (Partita partita: partiteSettimanali)
							areaTesto.append(partita.toString() + "\n");
					}
				} 
			}
		});
		pannelloBottoneMostra.add(mostraButton);
		
		return pannelloPartiteSettimana;
	}

	
	
	//per le finestre interne che verranno aperte da questa
	/**
	 * Questa classe serve a definire le operazioni che devono essere svolte dalle finestre che verranno aperte da questa in caso esse vengano chiuse.
	 * @author Mogavero
	 *
	 */
	class ChiudiFinestreInterne implements WindowListener{

		@Override
		public void windowActivated(WindowEvent arg0) {}

		/**
		 * Quando una finestra aperta da questa viene chiusa bisogna di nuovo rendere visibile questa finestra.
		 */
		@Override
		public void windowClosed(WindowEvent arg0) {
			setVisible(true);
			misuratore= new MisuratoreIdentificativoStadio();
			ordinaComboBox();
			areaTesto.setText(null);
		}

		/**
		 * Quando una finestra aperta da questa viene chiusa bisogna di nuovo rendere visibile questa finestra.
		 */
		@Override
		public void windowClosing(WindowEvent arg0) {
			setVisible (true);
			misuratore= new MisuratoreIdentificativoStadio();
			ordinaComboBox();
			areaTesto.setText(null);
			
		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {}

		@Override
		public void windowIconified(WindowEvent arg0) {}

		@Override
		public void windowOpened(WindowEvent arg0) {}
		
	}
	
	private ArrayList<Partita> elencoPartite;
	private ArrayList <Stadio> elencoStadi;
	private Cliente cliente;
	private Misuratore misuratore;
	private JTextArea areaTesto;
	private JScrollPane scrollPane;
	private JComboBox <Partita> partiteNonIniziate;
	private JMenu partiteStadioMenu;
	private JRadioButton stadioRadioButton,ordinamentoCrologicoRadioButton,squadreRadioButton,acquistaPrenotaRadioButton,mostraDettagliRadioButton;
	private JButton eseguiButton;
	private JDateChooser sceltaData1, sceltaData2;
	private GregorianCalendar inizioSettimana, fineSettimana;
}
