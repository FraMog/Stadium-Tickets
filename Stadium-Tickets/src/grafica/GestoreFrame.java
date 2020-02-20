package grafica;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import partite.*;
import partite.Partita.FasciaGiornaliera;
import misuratore.*;
import sconto.*;
import sconto.ScontoPerStadio;
import stream.Stream;
import utenti.*;
import com.toedter.calendar.*;
import eccezioni.*;

/**
 * Questa classe definisce il frame Home Gestore
 * @author Mogavero
 *
 */
public class GestoreFrame extends JFrame {

	/**
	 * Crea un nuovo Frame Gestore definendone dimensioni, titolo, JmenuBar, pannello Nord center e sud.
	 * @param elencoPartite L'elenco di tutte le partite programmate.
	 * @param elencoStadi L'elenco di tutti gli stadi presenti in archivio.
	 * @param elencoSconti L'elenco di tutti gli sconti presenti in archivio.
	 * @param gestoreInput Il gestore che sta attualmente usando il programma.
	 */
	public GestoreFrame(ArrayList<Partita> elencoPartite, ArrayList<Stadio> elencoStadi, ArrayList<Sconto> elencoSconti, Gestore gestoreInput) {
		this.elencoPartite=elencoPartite;
		this.elencoStadi= elencoStadi;
		this.elencoSconti=elencoSconti;
		gestore=gestoreInput;
		misuratore=null;
		
		//Le comboBoxStadi servono in più occasioni, per questo le inizializzo subito
		stadiComboBox1 = new JComboBox<Stadio>();
		stadiComboBox2 = new JComboBox<Stadio>();
		stadiComboBox3= new JComboBox <Stadio>();
		
		for (Stadio s: elencoStadi) creaItemComboStadio(s);
		creaJMenuBar();
		
		
		JPanel pannelloNord= new JPanel();
		
		add (pannelloNord,BorderLayout.NORTH);
		
		JLabel benvenuto = new JLabel("Benvenuto/a " + gestore.getNome() + " " + gestore.getCognome());
		benvenuto.setFont(new Font("Serif", Font.BOLD, 22));
		pannelloNord.add(benvenuto);
		add (creaScrollCentrale(),BorderLayout.CENTER);
		add (creaPannelloSud(), BorderLayout.SOUTH);
		setTitle("Home Gestore");
		//con i successivi due metodi imposto le dimensioni della finestra fullScreen.
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(0, 0, getToolkit().getScreenSize().width,getToolkit().getScreenSize().height);
		
		
		
	}



	/**
	 * Crea la JMenuBar per la classe GestoreFrame.
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
		
		JMenu partiteVisualizzaMenu = new JMenu("Lista Partite");
		visualizzaMenu.add(partiteVisualizzaMenu);
		
		JMenuItem capienzaStadiItem = new JMenuItem("Per Capienza Stadi");
		partiteVisualizzaMenu.add(capienzaStadiItem);
		capienzaStadiItem.addActionListener(new ActionListener() {
			
			/**
			 * Crea un nuovo misuratore per capienza stadi e invoca il metodo di ordinamento delle partite nell'area testo.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				misuratore=new MisuratoreCapienzaStadi();
				scriviPartiteOrdinate();
				
			}
		});
		
		
		JMenuItem ordineCronologicoItem = new JMenuItem("Per Ordine Cronologico");
		ordineCronologicoItem.addActionListener(new ActionListener() {
			
			/**
			 * Crea un nuovo misuratore cronologico e invoca il metodo di ordinamento delle partite nell'area testo.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				misuratore=new MisuratoreCronologico();
				scriviPartiteOrdinate();
				
			}
		});
		partiteVisualizzaMenu.add(ordineCronologicoItem);
		
		JMenu incassoMenu = new JMenu("Incasso");
		visualizzaMenu.add(incassoMenu);
		
		JMenuItem incassoTotaleItem = new JMenuItem("Totale");
		incassoTotaleItem.addActionListener(new ActionListener() {
			
			/**
			 * Mostra l'incasso totale fin ora ottenuto.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				areaTesto.setText(null);
				double incassoTotale=0;
				for (Stadio s: elencoStadi){
					incassoTotale+=s.getIncassoTotaleStadio();
				}
				areaTesto.append("L'incasso totale ottenuto finora è  " + incassoTotale);
				
			}
		});
		incassoMenu.add(incassoTotaleItem);
		
		incassoPerStadioMenu = new JMenu("Per Stadio");
		incassoMenu.add(incassoPerStadioMenu);
		for (Stadio s: elencoStadi)
			creaItemMenuStadio(s);
	}
	
	
	/**
	 * Crea un item nella Jmenubar per lo stadio di riferimento.
	 * @param stadio Lo stadio di riferimento.
	 */
	private void creaItemMenuStadio(final Stadio stadio){
		JMenuItem itemStadioMenu= new JMenuItem(stadio.toString());
		itemStadioMenu.addActionListener(new ActionListener() {
			
			/**
			 * Scrive in area testo l'incasso totale fin qui ottenuto.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				areaTesto.setText(null);
				areaTesto.append("L'incasso totale per lo stadio " + stadio.getNome() + " è " + stadio.getIncassoTotaleStadio());
			}
		});
		
		incassoPerStadioMenu.add(itemStadioMenu);
	}
	
	
	/**
	 * Questo metodo inserisce un item nelle combo box stadio per lo stadio selezionato.
	 * @param stadio Lo stadio selezionato.
	 */
	private void creaItemComboStadio(Stadio stadio){
			stadiComboBox1.addItem(stadio);
			stadiComboBox2.addItem(stadio);
			stadiComboBox3.addItem(stadio);
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
	 * Scrive in area testo tutte le partite ordinate secondo il criterio di ordinamento attuale.
	 */
	private void scriviPartiteOrdinate(){
		areaTesto.setText(null);
		if (elencoPartite.size()==0) areaTesto.append("Non vi sono presenti partite in archivio \n");
		else{
		//prima mi creo un array ordinato secondo il criterio scelto
			ArrayList <Partita> partiteOrdinate= new ArrayList <Partita>();
			for (Partita partita: elencoPartite){ //prendo tutte le partite dell'elenco partite (una alla volta e le confronto con quelle che ho gia inserito nell'array ordinato)
				int i=0;
				while (i<partiteOrdinate.size() && misuratore.misura(partiteOrdinate.get(i), partita)<0)
					i++;
				partiteOrdinate.add(i, partita);
			}
		//ora scrivo l'elenco delle partite ordinate secondo l'ordine scelto
			areaTesto.append("L'elenco delle partite ordinate è: \n");
			for (Partita partita: partiteOrdinate)
				areaTesto.append(partita.mostraDettagli()+ "\n");
		}
	}
	
	
	/**
	 * Definisce il pannello Sud.
	 * @return Il Pannello Sud.
	 */
	private JPanel creaPannelloSud(){
		JPanel pannelloSud= new JPanel();
		pannelloSud.setLayout(new GridLayout(1,4));
		pannelloSud.add(creaPannelloInserisciPartita());
		pannelloSud.add(creaPannelloInserisciStadio());
		pannelloSud.add(creaPannelloInserisciSconto());
		pannelloSud.add(creaPannelloUpdateStadio());
		return pannelloSud;
	}
	

	/**
	 * Crea il pannello Inserisci partita.
	 * @return Il pannello Inserisci Partita.
	 */
	private JPanel creaPannelloInserisciPartita(){
		JPanel pannelloInserisciPartita= new JPanel ();
		pannelloInserisciPartita.setBorder(new TitledBorder(new EtchedBorder(), "Inserisci una nuova Partita"));
		pannelloInserisciPartita.setLayout(new BorderLayout());
		
		
		JPanel pannelloCampiPartita = new JPanel();
		pannelloCampiPartita.setLayout(new GridLayout(5,1));
		
		pannelloInserisciPartita.add(pannelloCampiPartita,BorderLayout.CENTER);
		
		JPanel pannelloRiga1 = new JPanel();
		pannelloRiga1.setLayout(new GridLayout(1,2));
		pannelloCampiPartita.add(pannelloRiga1);
		
		JLabel squadraCasa = new JLabel("Squadra Casa:");
		pannelloRiga1.add(squadraCasa);
		
		squadraCasaField = new JTextField();
		pannelloRiga1.add(squadraCasaField);
		
		JPanel pannelloRiga2 = new JPanel();
		pannelloRiga2.setLayout(new GridLayout(1,2));
		pannelloCampiPartita.add(pannelloRiga2);
		
		JLabel squadraTrasferta = new JLabel("Squadra Trasferta:");
		pannelloRiga2.add(squadraTrasferta);
		
		squadraTrasfertaField = new JTextField();
		pannelloRiga2.add(squadraTrasfertaField);
		
		JPanel pannelloRiga3 = new JPanel();
		pannelloRiga3.setLayout(new GridLayout(1,2));
		pannelloCampiPartita.add(pannelloRiga3);
		
		JLabel stadio = new JLabel("Stadio");
		pannelloRiga3.add(stadio);
		
		
		pannelloRiga3.add(stadiComboBox1);
		
		JPanel pannelloRiga4 = new JPanel();
		pannelloRiga4.setLayout(new GridLayout(1,2));
		pannelloCampiPartita.add(pannelloRiga4);
		
		JLabel data = new JLabel("Data:");
		pannelloRiga4.add(data);
		
		sceltaData = new JDateChooser();
		sceltaData.setMinSelectableDate(new Date());
		pannelloRiga4.add(sceltaData);
		
		JPanel pannelloRiga5 = new JPanel();
		pannelloRiga5.setLayout(new GridLayout(1,4));
		pannelloCampiPartita.add(pannelloRiga5);
		
		JLabel ore = new JLabel("Alle Ore:");
		pannelloRiga5.add(ore);
		
		SpinnerNumberModel modelloOre= new SpinnerNumberModel(0, 0, 23, 1);
		oreSpinner = new JSpinner(modelloOre);
		pannelloRiga5.add(oreSpinner);
		
		JLabel minuti = new JLabel("Minuti:");
		pannelloRiga5.add(minuti);
		
		
		SpinnerNumberModel modelloMinuti= new SpinnerNumberModel(0, 0, 59, 1);
		minutiSpinner = new JSpinner(modelloMinuti);
		pannelloRiga5.add(minutiSpinner);
	
		
		JPanel pannelloBottoneRegistra = new JPanel();
		pannelloInserisciPartita.add(pannelloBottoneRegistra, BorderLayout.SOUTH);
		
		inserisciPartitaButton = new JButton("Inserisci Partita!");
		if (elencoStadi.size()==0) inserisciPartitaButton.setEnabled(false);
		inserisciPartitaButton.addActionListener(new ActionListener() {
			
			/**
			 * Al click dell'utente sul bottone inserisce una nuova partita nell'elenco delle partite programmate.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				areaTesto.setText(null);
				GregorianCalendar dataInserita= (GregorianCalendar) sceltaData.getCalendar();
				if (dataInserita==null) JOptionPane.showMessageDialog(null, "Inserire una data!","Errore",JOptionPane.ERROR_MESSAGE);
				else {
					Stadio stadioSelezionato= (Stadio)stadiComboBox1.getSelectedItem();
					try {
						Partita partitaDaInserire= new Partita(squadraCasaField.getText(), squadraTrasfertaField.getText(), new GregorianCalendar(dataInserita.get(GregorianCalendar.YEAR), dataInserita.get(GregorianCalendar.MONTH), dataInserita.get(GregorianCalendar.DAY_OF_MONTH), (int) oreSpinner.getValue(), (int) minutiSpinner.getValue()),stadioSelezionato);
						int i=0;
						while (i<elencoPartite.size() && !partitaDaInserire.equals(elencoPartite.get(i))) i++;
						if (i==elencoPartite.size()) {
							elencoPartite.add(partitaDaInserire);
							for (Sconto sconto: elencoSconti) sconto.aggiungiScontoAPartita(partitaDaInserire);
							stadioSelezionato.addPartita(partitaDaInserire);
							JOptionPane.showMessageDialog(null, "Partita inserita con successo!");
						}
						else JOptionPane.showMessageDialog(null, "La partita che si sta cercando di inserire è gia presente in archivio","Errore",JOptionPane.ERROR_MESSAGE);	
						
					} 
						
					catch (IllegalNomeSquadraException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(),"Errore",JOptionPane.ERROR_MESSAGE);
					} 
					catch (IllegalDataException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(),"Errore",JOptionPane.ERROR_MESSAGE);
					}	
					
			}
		}			
			
		});
		pannelloBottoneRegistra.add(inserisciPartitaButton);
		
		
		return pannelloInserisciPartita;
	}
	
	
	
	/**
	 * Crea il Pannello Inserisci Stadio.
	 * @return Il pannello Inserisci Stadio.
	 */
	private JPanel creaPannelloInserisciStadio() {
		JPanel pannelloInserisciStadio= new JPanel();
		pannelloInserisciStadio.setBorder(new TitledBorder(new EtchedBorder(),"Inserisci un nuovo Stadio"));
		pannelloInserisciStadio.setLayout(new BorderLayout());
		
		JPanel pannelloCampiStadio = new JPanel();
		pannelloInserisciStadio.add(pannelloCampiStadio, BorderLayout.CENTER);
		pannelloCampiStadio.setLayout(new GridLayout(6,1));
		
		JPanel pannelloRiga1 = new JPanel();
		pannelloCampiStadio.add(pannelloRiga1);
		pannelloRiga1.setLayout(new GridLayout(1,2));
		JLabel nomeStadio = new JLabel("Nome Stadio:");
		pannelloRiga1.add(nomeStadio);
		nomeStadioField = new JTextField();
		pannelloRiga1.add(nomeStadioField);
		
		
		JPanel pannelloRiga2 = new JPanel();
		pannelloCampiStadio.add(pannelloRiga2);
		pannelloRiga2.setLayout(new GridLayout(1,2));
		JLabel costoBiglietti = new JLabel("Costo Biglietti:");
		pannelloRiga2.add(costoBiglietti);
		SpinnerNumberModel modelloCosto= new SpinnerNumberModel(0.01, 0.01, Double.MAX_VALUE, 0.01);
		costoStadioSpinner= new JSpinner(modelloCosto);
		pannelloRiga2.add(costoStadioSpinner);
		
		
		JPanel pannelloRiga3 = new JPanel();
		pannelloCampiStadio.add(pannelloRiga3);
		pannelloRiga3.setLayout(new GridLayout(1,2));
		JLabel lunghezzaTribuneCentrali = new JLabel("Lunghezza Tribune Centrali:");
		pannelloRiga3.add(lunghezzaTribuneCentrali);
		SpinnerNumberModel modelloTribuneCentraliLunghezza= new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1); //Vale per tutti gli Spinner creati sulle dimensioni stadio
		lunghezzaTribuneCentraliSpinner= new JSpinner(modelloTribuneCentraliLunghezza);
		pannelloRiga3.add(lunghezzaTribuneCentraliSpinner);
		
		
		
		JPanel pannelloRiga4 = new JPanel();
		pannelloCampiStadio.add(pannelloRiga4);
		pannelloRiga4.setLayout(new GridLayout(1,2));
		JLabel altezzaTribuneCentrali = new JLabel("Altezza Tribune Centrali:");
		pannelloRiga4.add(altezzaTribuneCentrali);
		SpinnerNumberModel modelloTribuneCentraliAltezza= new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
		altezzaTribuneCentraliSpinner= new JSpinner(modelloTribuneCentraliAltezza);
		pannelloRiga4.add(altezzaTribuneCentraliSpinner);
		
		JPanel pannelloRiga5 = new JPanel();
		pannelloCampiStadio.add(pannelloRiga5);
		pannelloRiga5.setLayout(new GridLayout(1,2));
		JLabel lunghezzaTribuneLaterali = new JLabel("Lunghezza Tribune Laterali:");
		pannelloRiga5.add(lunghezzaTribuneLaterali);
		SpinnerNumberModel modelloTribuneLateraliLunghezza= new SpinnerNumberModel(1, 1,  Integer.MAX_VALUE, 1);
		lunghezzaTribuneLateraliSpinner= new JSpinner(modelloTribuneLateraliLunghezza);
		pannelloRiga5.add(lunghezzaTribuneLateraliSpinner);
		
		
		
		JPanel pannelloRiga6 = new JPanel();
		pannelloCampiStadio.add(pannelloRiga6);
		pannelloRiga6.setLayout(new GridLayout(1,2));
		JLabel altezzaTribuneLaterali = new JLabel("Altezza Tribune Laterali:");
		pannelloRiga6.add(altezzaTribuneLaterali);
		SpinnerNumberModel modelloTribuneLateraliAltezza= new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
		altezzaTribuneLateraliSpinner= new JSpinner(modelloTribuneLateraliAltezza);
		pannelloRiga6.add(altezzaTribuneLateraliSpinner);
		
		
		JPanel pannelloBottoneInserisciStadio = new JPanel();
		pannelloInserisciStadio.add(pannelloBottoneInserisciStadio, BorderLayout.SOUTH);
		bottoneInserisciStadio = new JButton("Inserisci Stadio!");
		pannelloBottoneInserisciStadio.add(bottoneInserisciStadio);
		
		bottoneInserisciStadio.addActionListener(new ActionListener() {
			
			/**
			 * Al click dell'utente inserisce un nuovo stadio nell'elenco degli stadi.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				areaTesto.setText(null);
				try {
					Stadio stadioInserito = new Stadio(elencoStadi.size()+1, nomeStadioField.getText(), (int)lunghezzaTribuneCentraliSpinner.getValue(), (int)altezzaTribuneCentraliSpinner.getValue(), (int)lunghezzaTribuneLateraliSpinner.getValue(), (int) altezzaTribuneLateraliSpinner.getValue(), (double) costoStadioSpinner.getValue());
					int i=0;
					while (i<elencoStadi.size() && !stadioInserito.equals(elencoStadi.get(i))) i++;
					if (i<elencoStadi.size()) JOptionPane.showMessageDialog(null, "Lo stadio che stai tentando di inserire è gia presente in archivio","Errore",JOptionPane.ERROR_MESSAGE);
					else {
						elencoStadi.add(stadioInserito);
						creaItemMenuStadio(stadioInserito);
						creaItemComboStadio(stadioInserito);
						inserisciPartitaButton.setEnabled(true);
						updateStadioButton.setEnabled(true);
						JOptionPane.showMessageDialog(null, "Stadio inserito con successo!");
					}
				} 
				catch (IllegalNomeStadioException e1) {
					JOptionPane.showMessageDialog(null, "Errore: " + e1.getMessage(),"Errore",JOptionPane.ERROR_MESSAGE );
				}
				
			}
		});
		
		return pannelloInserisciStadio;
	}
	
	
	/**
	 * Crea il pannello Inserisci Sconto.
	 * @return Il pannello Inseerisci Sconto.
	 */
	private JPanel creaPannelloInserisciSconto() {
		JPanel pannelloInserisciSconto= new JPanel();
		pannelloInserisciSconto.setBorder(new TitledBorder(new EtchedBorder(), "Inserisci uno Sconto alle Partite"));
		pannelloInserisciSconto.setLayout(new BorderLayout());
		
		JPanel pannelloNord= new JPanel();
		pannelloInserisciSconto.add(pannelloNord, BorderLayout.NORTH);
		pannelloNord.setLayout(new GridLayout (1,2));
		pannelloNord.setBorder(new TitledBorder(new EtchedBorder(),"Scegli percentuale Sconto"));
		pannelloNord.add(new JLabel("Percentuale Sconto: "));
		
		SpinnerNumberModel modelloSconto= new SpinnerNumberModel(1, 1, 99, 1);
		scontoSpinner = new JSpinner(modelloSconto);
		pannelloNord.add(scontoSpinner);
		
		
		
		JPanel pannelloSceltaSconto = new JPanel();
		pannelloSceltaSconto.setLayout(new BorderLayout());
		
		pannelloInserisciSconto.add(pannelloSceltaSconto, BorderLayout.CENTER);
		
		//creo il pannello dello sconto per stadio
		JPanel pannelloScontoPerStadio = new JPanel();
		pannelloSceltaSconto.add(pannelloScontoPerStadio,BorderLayout.NORTH);
		pannelloScontoPerStadio.setBorder(new EtchedBorder());
		pannelloScontoPerStadio.setLayout(new GridLayout(1,2));
		scontoPerStadioRadioButton = new JRadioButton("Per Stadio");
		scontoPerStadioRadioButton.setSelected(true);
		scontoPerStadioRadioButton.addActionListener(new ActionListener() {
			
			/**
			 * Al click dell'utente abilita la comboBox per lo sconto per stadio disabilitando tutti gli altri components destinati agli altri tipi di sconto.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				stadiComboBox2.setEnabled(true);
				
				lunediCheckBox.setEnabled(false);
				martediCheckBox.setEnabled(false);
				mercolediCheckBox.setEnabled(false);
				giovediCheckBox.setEnabled(false);
				venerdiCheckBox.setEnabled(false);
				sabatoCheckBox.setEnabled(false);
				domenicaCheckBox.setEnabled(false);
				
				fasciaOrariaComboBox.setEnabled(false);
			}
		});
		pannelloScontoPerStadio.add(scontoPerStadioRadioButton);
		pannelloScontoPerStadio.add(stadiComboBox2);
		
		
		// creo il pannello dello sconto per giorni della settimana
		JPanel pannelloScontoPerGiorni = new JPanel();
		pannelloScontoPerGiorni.setBorder(new EtchedBorder());
		pannelloSceltaSconto.add(pannelloScontoPerGiorni,BorderLayout.CENTER);
		pannelloScontoPerGiorni.setLayout(new GridLayout(2,1));
		
		JPanel pannelloScontoPerGiorniAlto= new JPanel();
		pannelloScontoPerGiorniAlto.setLayout(new GridLayout(1,2));
		pannelloScontoPerGiorni.add(pannelloScontoPerGiorniAlto);
		scontoPerGiorniRadioButton = new JRadioButton("Per Giorni");
		scontoPerGiorniRadioButton.addActionListener(new ActionListener() {
			
			/**
			 * Al click dell'utente abilita i Check Box per lo sconto per giorni Settimanali disabilitando tutti gli altri components destinati agli altri tipi di sconto.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				stadiComboBox2.setEnabled(false);
				
				lunediCheckBox.setEnabled(true);
				martediCheckBox.setEnabled(true);
				mercolediCheckBox.setEnabled(true);
				giovediCheckBox.setEnabled(true);
				venerdiCheckBox.setEnabled(true);
				sabatoCheckBox.setEnabled(true);
				domenicaCheckBox.setEnabled(true);
				
				fasciaOrariaComboBox.setEnabled(false);
			}
		});
		pannelloScontoPerGiorniAlto.add(scontoPerGiorniRadioButton);
		JPanel pannelloScontoPerGiorniAltoaDx= new JPanel();
		pannelloScontoPerGiorniAltoaDx.setLayout(new GridLayout(1,2));
		pannelloScontoPerGiorniAlto.add(pannelloScontoPerGiorniAltoaDx);
		lunediCheckBox = new JCheckBox ("Lun");
		pannelloScontoPerGiorniAltoaDx.add(lunediCheckBox);
		martediCheckBox = new JCheckBox("Mar");
		pannelloScontoPerGiorniAltoaDx.add(martediCheckBox);
	
	
		
		JPanel pannelloScontoPerGiorniBasso= new JPanel();
		pannelloScontoPerGiorniBasso.setLayout(new GridLayout(1,5));
		pannelloScontoPerGiorni.add(pannelloScontoPerGiorniBasso);
		mercolediCheckBox = new JCheckBox("Mer");
		pannelloScontoPerGiorniBasso.add(mercolediCheckBox);
		giovediCheckBox = new JCheckBox("Gio");
		pannelloScontoPerGiorniBasso.add(giovediCheckBox);
		venerdiCheckBox = new JCheckBox("Ven");
		pannelloScontoPerGiorniBasso.add(venerdiCheckBox);
		sabatoCheckBox = new JCheckBox("Sab");
		pannelloScontoPerGiorniBasso.add(sabatoCheckBox);
		domenicaCheckBox = new JCheckBox("Dom");
		pannelloScontoPerGiorniBasso.add(domenicaCheckBox);
		
		//Inizialmente non sono enabled
		lunediCheckBox.setEnabled(false);
		martediCheckBox.setEnabled(false);
		mercolediCheckBox.setEnabled(false);
		giovediCheckBox.setEnabled(false);
		venerdiCheckBox.setEnabled(false);
		sabatoCheckBox.setEnabled(false);
		domenicaCheckBox.setEnabled(false);
		
		
		// creo il pannello dello sconto per fascia oraria
		JPanel pannelloScontoPerFasciaOraria = new JPanel();
		pannelloScontoPerFasciaOraria.setLayout(new GridLayout(1,2));
		pannelloScontoPerFasciaOraria.setBorder(new EtchedBorder());
		pannelloSceltaSconto.add(pannelloScontoPerFasciaOraria,BorderLayout.SOUTH);
		fasciaOrariaComboBox= new JComboBox <Partita.FasciaGiornaliera>();
		scontoPerFasciaOrariaRadioButton = new JRadioButton("Per Fascia Oraria");
		scontoPerFasciaOrariaRadioButton.addActionListener(new ActionListener() {
			
			/**
			 * Al click dell'utente abilita la comboBox per lo sconto per fascia Oraria disabilitando tutti gli altri components destinati agli altri tipi di sconto.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				stadiComboBox2.setEnabled(false);
				
				lunediCheckBox.setEnabled(false);
				martediCheckBox.setEnabled(false);
				mercolediCheckBox.setEnabled(false);
				giovediCheckBox.setEnabled(false);
				venerdiCheckBox.setEnabled(false);
				sabatoCheckBox.setEnabled(false);
				domenicaCheckBox.setEnabled(false);
				
				fasciaOrariaComboBox.setEnabled(true);
			}
		});
		pannelloScontoPerFasciaOraria.add(scontoPerFasciaOrariaRadioButton);
		pannelloScontoPerFasciaOraria.add(fasciaOrariaComboBox);
		fasciaOrariaComboBox.addItem(FasciaGiornaliera.MATTINA);
		fasciaOrariaComboBox.addItem(FasciaGiornaliera.POMERIGGIO);
		fasciaOrariaComboBox.addItem(FasciaGiornaliera.SERA);
		fasciaOrariaComboBox.addItem(FasciaGiornaliera.NOTTE);
		fasciaOrariaComboBox.setEnabled(false);
		
		
	
		
		JPanel pannelloBottoneInserisciSconto = new JPanel();
		pannelloInserisciSconto.add(pannelloBottoneInserisciSconto, BorderLayout.SOUTH);
		bottoneSconto = new JButton("Inserisci Sconto!");
		bottoneSconto.addActionListener(new ActionListener() {
			
			/**
			 * Al click dell'utente inserisce lo sconto nell'elenco sconti e lo associa a tutte le partite gia esistenti che sono compatibili con le caratteristiche dello sconto.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				areaTesto.setText(null);
				int percentualeSconto=(int)scontoSpinner.getValue();
				Sconto scontoInserito;
				//vedo quale dei tre radioButton è selezionato
				if (scontoPerStadioRadioButton.isSelected()){
					try {
						scontoInserito= new ScontoPerStadio(percentualeSconto,(Stadio) stadiComboBox2.getSelectedItem());
						int i =0;
						while (i<elencoSconti.size() && !scontoInserito.equals(elencoSconti.get(i))) i++;
						if (i<elencoSconti.size()) JOptionPane.showMessageDialog(null, "Lo sconto che stai tentando di inserire è gia presente in archivio","Errore",JOptionPane.ERROR_MESSAGE);
						else {
							for (Partita partita: elencoPartite){
								scontoInserito.aggiungiScontoAPartita(partita);
							}
							elencoSconti.add(scontoInserito);
							JOptionPane.showMessageDialog(null, "Sconto inserito con successo!");
						} 
					}
					catch (IllegalStadioException e1) {
						JOptionPane.showMessageDialog(null, "Errore" +  e1.getMessage(),"Errore",JOptionPane.ERROR_MESSAGE);
					}
					
				}
				else if (scontoPerFasciaOrariaRadioButton.isSelected()){
					scontoInserito= new ScontoPerFasciaGiornaliera(percentualeSconto, (Partita.FasciaGiornaliera) fasciaOrariaComboBox.getSelectedItem());
					int i =0;
					while (i<elencoSconti.size() && !scontoInserito.equals(elencoSconti.get(i))) i++;
					if (i<elencoSconti.size()) JOptionPane.showMessageDialog(null, "Lo sconto che stai tentando di inserire è gia presente in archivio","Errore",JOptionPane.ERROR_MESSAGE);
					else {
						for (Partita partita: elencoPartite){
							scontoInserito.aggiungiScontoAPartita(partita);
						}
						elencoSconti.add(scontoInserito);
						JOptionPane.showMessageDialog(null, "Sconto inserito con successo!");
					}
				}
				else { //è selezionato scontoPerGiorniRadioButton
					ArrayList <Integer> giorniSconto=new ArrayList <Integer>();
					if (lunediCheckBox.isSelected()) giorniSconto.add(GregorianCalendar.MONDAY);
					if (martediCheckBox.isSelected()) giorniSconto.add(GregorianCalendar.TUESDAY);
					if (mercolediCheckBox.isSelected()) giorniSconto.add(GregorianCalendar.WEDNESDAY);
					if (giovediCheckBox.isSelected()) giorniSconto.add(GregorianCalendar.THURSDAY);
					if (venerdiCheckBox.isSelected()) giorniSconto.add(GregorianCalendar.FRIDAY);
					if (sabatoCheckBox.isSelected()) giorniSconto.add(GregorianCalendar.SATURDAY);
					if (domenicaCheckBox.isSelected()) giorniSconto.add(GregorianCalendar.SUNDAY);
					try {
						scontoInserito= new ScontoPerGiorniSettimanali(percentualeSconto, giorniSconto);
						int i =0;
						while (i<elencoSconti.size() && !scontoInserito.equals(elencoSconti.get(i))) i++;
						if (i<elencoSconti.size()) JOptionPane.showMessageDialog(null, "Lo sconto che stai tentando di inserire è gia presente in archivio","Errore",JOptionPane.ERROR_MESSAGE);
						else {
							for (Partita partita: elencoPartite){
								scontoInserito.aggiungiScontoAPartita(partita);
							}
							elencoSconti.add(scontoInserito);
							JOptionPane.showMessageDialog(null, "Sconto inserito con successo!");
						}
					} 
					catch (IllegalGiorniScontoException e1) {
						JOptionPane.showMessageDialog(null, "Errore " + e1.getMessage(),"Errore",JOptionPane.ERROR_MESSAGE );
					}
				}
				
				
			}
		});
		pannelloBottoneInserisciSconto.add(bottoneSconto);
		
		
		ButtonGroup gruppoSceltaSconto= new ButtonGroup();
		gruppoSceltaSconto.add(scontoPerStadioRadioButton);
		gruppoSceltaSconto.add(scontoPerFasciaOrariaRadioButton);
		gruppoSceltaSconto.add(scontoPerGiorniRadioButton);
		
		return pannelloInserisciSconto;
	}

	
	/**
	 * Crea il pannello Update Stadio.
	 * @return Il pannello Update Stadio.
	 */
	private JPanel creaPannelloUpdateStadio() {
		JPanel pannelloUpdateStadio= new JPanel();
		pannelloUpdateStadio.setLayout(new BorderLayout());
		pannelloUpdateStadio.setBorder(new TitledBorder(new EtchedBorder(),"Update Stadio"));
		
		JPanel pannelloNord = new JPanel();
		pannelloNord.setLayout(new GridLayout(2,1));
		pannelloNord.setBorder(new EtchedBorder());
		pannelloUpdateStadio.add(pannelloNord, BorderLayout.NORTH);
		
		modificaPrezzoStadioRadioButton = new JRadioButton("Modifica Prezzo Partite");
		modificaPrezzoStadioRadioButton.setSelected(true);
		modificaPrezzoStadioRadioButton.addActionListener(new ActionListener() {
			
			/**
			 * Al click dell'utente rende abilita lo spinner per modificare il costo dello stadio e disabilita gli spinner per modificare la capienza.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				costoStadioSpinner2.setEnabled(true);
				
				lunghezzaTribuneCentraliSpinner2.setEnabled(false);
				altezzaTribuneCentraliSpinner2.setEnabled(false);
				lunghezzaTribuneLateraliSpinner2.setEnabled(false);
				altezzaTribuneLateraliSpinner2.setEnabled(false);
			}
		});
		pannelloNord.add(modificaPrezzoStadioRadioButton);
		
		JPanel pannelloScegliPrezzo = new JPanel();
		pannelloScegliPrezzo.setLayout(new GridLayout(1,2));
		pannelloScegliPrezzo.add(new JLabel("Nuovo Prezzo:"));
		SpinnerNumberModel modelloCosto;
		if (stadiComboBox3.getSelectedItem()==null)
			modelloCosto= new SpinnerNumberModel(0.01, 0.01, Double.MAX_VALUE, 0.01);
		else  modelloCosto= new SpinnerNumberModel(((Stadio)stadiComboBox3.getSelectedItem()).getPrezzoPartite(), 0.01, Double.MAX_VALUE, 0.01);
		costoStadioSpinner2= new JSpinner(modelloCosto);
		pannelloScegliPrezzo.add(costoStadioSpinner2);
		pannelloNord.add(pannelloScegliPrezzo);
		
		JPanel pannelloCenter = new JPanel();
		pannelloCenter.setLayout(new BorderLayout());
		pannelloUpdateStadio.add(pannelloCenter, BorderLayout.CENTER);
		
		JPanel pannelloCenterCampiModificaStadio= new JPanel();
		pannelloCenter.add(pannelloCenterCampiModificaStadio, BorderLayout.CENTER);
		pannelloCenterCampiModificaStadio.setBorder(new EtchedBorder());
		pannelloCenterCampiModificaStadio.setLayout(new GridLayout(5,1));
		
		
		modificaCapienzaStadioRadioButton= new JRadioButton("Modifica Capienza Stadio");
		modificaCapienzaStadioRadioButton.addActionListener(new ActionListener() {
			
			/**
			 * Al click dell'utente abilita gli spinner per la modifica della capienza dello stadio e disabilita quello per la modifica del prezzo.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				costoStadioSpinner2.setEnabled(false);
				
				lunghezzaTribuneCentraliSpinner2.setEnabled(true);
				altezzaTribuneCentraliSpinner2.setEnabled(true);
				lunghezzaTribuneLateraliSpinner2.setEnabled(true);
				altezzaTribuneLateraliSpinner2.setEnabled(true);
			}
		});
		pannelloCenterCampiModificaStadio.add(modificaCapienzaStadioRadioButton);//in riga1
		
		JPanel pannelloRiga2 = new JPanel();
		pannelloCenterCampiModificaStadio.add(pannelloRiga2);
		pannelloRiga2.setLayout(new GridLayout(1,2));
		JLabel lunghezzaTribuneCentrali = new JLabel("Lunghezza Tribune Centrali:");
		pannelloRiga2.add(lunghezzaTribuneCentrali);
		SpinnerNumberModel modelloTribuneCentraliLunghezza;
		if (stadiComboBox3.getSelectedItem()==null)
			modelloTribuneCentraliLunghezza= new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1); 
		else modelloTribuneCentraliLunghezza= new SpinnerNumberModel(((Stadio)stadiComboBox3.getSelectedItem()).getTribuneCentraliLength(), 1, Integer.MAX_VALUE, 1); 
		lunghezzaTribuneCentraliSpinner2= new JSpinner(modelloTribuneCentraliLunghezza);
		pannelloRiga2.add(lunghezzaTribuneCentraliSpinner2);
		
		
		
		JPanel pannelloRiga3 = new JPanel();
		pannelloCenterCampiModificaStadio.add(pannelloRiga3);
		pannelloRiga3.setLayout(new GridLayout(1,2));
		JLabel altezzaTribuneCentrali = new JLabel("Altezza Tribune Centrali:");
		pannelloRiga3.add(altezzaTribuneCentrali);
		SpinnerNumberModel modelloTribuneCentraliAltezza;
		if (stadiComboBox3.getSelectedItem()==null)
			modelloTribuneCentraliAltezza= new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
		else modelloTribuneCentraliAltezza= new SpinnerNumberModel(((Stadio)stadiComboBox3.getSelectedItem()).getTribuneCentraliHeight(), 1, Integer.MAX_VALUE, 1); 
		altezzaTribuneCentraliSpinner2= new JSpinner(modelloTribuneCentraliAltezza);
		pannelloRiga3.add(altezzaTribuneCentraliSpinner2);
		
		JPanel pannelloRiga4 = new JPanel();
		pannelloCenterCampiModificaStadio.add(pannelloRiga4);
		pannelloRiga4.setLayout(new GridLayout(1,2));
		JLabel lunghezzaTribuneLaterali = new JLabel("Lunghezza Tribune Laterali:");
		pannelloRiga4.add(lunghezzaTribuneLaterali);
		SpinnerNumberModel modelloTribuneLateraliLunghezza;
		if (stadiComboBox3.getSelectedItem()==null)
			modelloTribuneLateraliLunghezza= new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
		else modelloTribuneLateraliLunghezza= new SpinnerNumberModel(((Stadio)stadiComboBox3.getSelectedItem()).getTribuneLateraliLength(), 1, Integer.MAX_VALUE, 1); 
		lunghezzaTribuneLateraliSpinner2= new JSpinner(modelloTribuneLateraliLunghezza);
		pannelloRiga4.add(lunghezzaTribuneLateraliSpinner2);
		
		
		
		JPanel pannelloRiga5 = new JPanel();
		pannelloCenterCampiModificaStadio.add(pannelloRiga5);
		pannelloRiga5.setLayout(new GridLayout(1,2));
		JLabel altezzaTribuneLaterali = new JLabel("Altezza Tribune Laterali:");
		pannelloRiga5.add(altezzaTribuneLaterali);
		SpinnerNumberModel modelloTribuneLateraliAltezza;
		if (stadiComboBox3.getSelectedItem()==null)
			modelloTribuneLateraliAltezza= new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
		else modelloTribuneLateraliAltezza= new SpinnerNumberModel(((Stadio)stadiComboBox3.getSelectedItem()).getTribuneLateraliHeight(), 1, Integer.MAX_VALUE, 1); 
		altezzaTribuneLateraliSpinner2= new JSpinner(modelloTribuneLateraliAltezza);
		pannelloRiga5.add(altezzaTribuneLateraliSpinner2);
		
		//Inizialmente non sono abilitate
		lunghezzaTribuneCentraliSpinner2.setEnabled(false);
		altezzaTribuneCentraliSpinner2.setEnabled(false);
		lunghezzaTribuneLateraliSpinner2.setEnabled(false);
		altezzaTribuneLateraliSpinner2.setEnabled(false);
		
		JPanel pannelloCenterSud=new JPanel();
		pannelloCenter.add(pannelloCenterSud,BorderLayout.SOUTH);
		pannelloCenterSud.setBorder(new EtchedBorder());
		pannelloCenterSud.setLayout(new GridLayout(1,2));
		JLabel scegliStadio = new JLabel("Stadio da Modificare:");
		pannelloCenterSud.add(scegliStadio);
		pannelloCenterSud.add(stadiComboBox3);
		stadiComboBox3.addActionListener(new ActionListener() {

			/**
			 * Al momento della scelta dell'utente di un diverso stadio da modificare (tramite comboBox Stadi) modifica le impostazioni dei vari spinner aggiornandole a quelle dello stadio selezionato.
			 */
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Stadio stadioSelezionato= (Stadio) stadiComboBox3.getSelectedItem();
				if (stadioSelezionato!=null){
					costoStadioSpinner2.setValue(stadioSelezionato.getPrezzoPartite());
					lunghezzaTribuneCentraliSpinner2.setValue(stadioSelezionato.getTribuneCentraliLength());
					altezzaTribuneCentraliSpinner2.setValue(stadioSelezionato.getTribuneCentraliHeight());
					lunghezzaTribuneLateraliSpinner2.setValue(stadioSelezionato.getTribuneLateraliLength());
					altezzaTribuneLateraliSpinner2.setValue(stadioSelezionato.getTribuneLateraliHeight());
				}
				
			}
			
		});
		
		JPanel pannelloSud = new JPanel();
		pannelloUpdateStadio.add(pannelloSud, BorderLayout.SOUTH);
		
		updateStadioButton = new JButton("Esegui!");
		if (elencoStadi.size()==0) updateStadioButton.setEnabled(false);
		updateStadioButton.addActionListener(new ActionListener() {
			
			/**
			 * Al click dell'utente modifica lo stadio secondo le modifiche richieste.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				areaTesto.setText(null);
				Stadio stadioSelezionato=(Stadio)stadiComboBox3.getSelectedItem();
				//vedo quale dei due radioButton è selezionato
				if (modificaPrezzoStadioRadioButton.isSelected()){
					stadioSelezionato.setPrezzo((double)costoStadioSpinner2.getValue());
					JOptionPane.showMessageDialog(null, "Modifica Prezzo Biglietti Completata!");
				}
				else { //modificaCapienzaStadioRadioButton è selezionato
					stadioSelezionato.aggiornaDimensioniStadio((int)lunghezzaTribuneCentraliSpinner2.getValue(), (int) altezzaTribuneCentraliSpinner2.getValue(), (int) lunghezzaTribuneLateraliSpinner2.getValue(),(int) altezzaTribuneLateraliSpinner2.getValue());
					JOptionPane.showMessageDialog(null, "Modifica Capienza Stadio Completata!");
					}
				
			}
		});
		pannelloSud.add(updateStadioButton);
		
		ButtonGroup gruppoBottoniUpdateStadio= new ButtonGroup();
		gruppoBottoniUpdateStadio.add(modificaPrezzoStadioRadioButton);
		gruppoBottoniUpdateStadio.add(modificaCapienzaStadioRadioButton);
		return pannelloUpdateStadio;
	}
	
	
	private ArrayList<Partita> elencoPartite;
	private ArrayList<Stadio> elencoStadi;
	private ArrayList<Sconto> elencoSconti;
	private Misuratore misuratore;
	private JTextArea areaTesto;
	private JScrollPane scrollPane;
	private JTextField squadraCasaField, squadraTrasfertaField,nomeStadioField;
	private Gestore gestore;
	private JComboBox <Stadio> stadiComboBox1,stadiComboBox2,stadiComboBox3;
	private JComboBox <Partita.FasciaGiornaliera>fasciaOrariaComboBox;
	private JSpinner oreSpinner,minutiSpinner,costoStadioSpinner,costoStadioSpinner2,lunghezzaTribuneCentraliSpinner,altezzaTribuneLateraliSpinner,lunghezzaTribuneLateraliSpinner,altezzaTribuneCentraliSpinner,scontoSpinner,lunghezzaTribuneCentraliSpinner2,altezzaTribuneCentraliSpinner2,lunghezzaTribuneLateraliSpinner2,altezzaTribuneLateraliSpinner2;
	private JDateChooser sceltaData;
	private JMenu incassoPerStadioMenu;
	private JRadioButton scontoPerGiorniRadioButton,scontoPerFasciaOrariaRadioButton, scontoPerStadioRadioButton,modificaPrezzoStadioRadioButton, modificaCapienzaStadioRadioButton;
	private JCheckBox lunediCheckBox, martediCheckBox, mercolediCheckBox,giovediCheckBox,venerdiCheckBox,sabatoCheckBox,domenicaCheckBox;
	private JButton bottoneSconto,inserisciPartitaButton,updateStadioButton,bottoneInserisciStadio;

}
