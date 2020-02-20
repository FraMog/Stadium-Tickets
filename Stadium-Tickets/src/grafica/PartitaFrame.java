package grafica;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import eccezioni.*;
import partite.*;
import partite.Biglietto.StatiPossibili;
import partite.Partita.SettoriPossibili;
import utenti.*;

/**
 * Questa classe definisce il disegno grafico del campo di una partita scelta.
 * @author Mogavero
 *
 */
public class PartitaFrame extends JFrame {

	/**
	 * Crea un nuovo Frame partita definendone le dimensioni, i vari pannelli, i vari settori dello stadio con i vari posti.
	 * @param partitaInput La partita per la quale creare la rappresentazione grafica.
	 * @param cliente Il cliente che sta attualmente accedendo alla rappresentazione grafica per prenotare o acquistare biglietti.
	 */
	public PartitaFrame(Partita partitaInput, Cliente cliente) {
		partita=partitaInput;
		this.cliente= cliente;
		bigliettiSettoreNord= partita.getBigliettiSettoreNord();
		bigliettiSettoreSud=partita.getBigliettiSettoreSud();
		bigliettiSettoreEst=partita.getBigliettiSettoreEst();
		bigliettiSettoreWest=partita.getBigliettiSettoreWest();
		stadio=partita.getStadio();
		migliorPrezzoDisponibile=partita.getMigliorPrezzoDisponibile();
		postiGrafici= new ArrayList<MyBottone>();

		
		setLayout(null);
		add(creaPannelloNord());
		creaPannelloStadio();
		riempiTextArea();
		setTitle(partita.toString());
		
		
		setSize(1366,730);
		setLocationRelativeTo(null);
		setResizable(false);
				
	}
	

	
	/**
	 * Crea il pannello Nord.
	 * @return Il Pannello Nord.
	 */
	private JPanel creaPannelloNord() {
		JPanel pannelloNord= new JPanel();
		pannelloNord.setBounds(0, 0, 1362, 136);
		pannelloNord.setLayout(new BorderLayout());
		pannelloNord.add(creaPannelloLegenda(), BorderLayout.NORTH);
		pannelloNord.add(creaScrollPrenotazioniAcquisti (), BorderLayout.CENTER);
		return pannelloNord;
	}


	/**
	 * Crea il Pannello legenda, dove viene mostrata la legenda dei posti.
	 * @return Il Pannello Legenda.
	 */
	private JPanel creaPannelloLegenda() {
		JPanel pannelloLegenda= new JPanel();
		
		JLabel legendaLabel = new JLabel("Legenda:");
		legendaLabel.setFont(new Font("Serif", Font.BOLD, 14));
		pannelloLegenda.add(legendaLabel);
		
		
		JLabel postoLiberoLabel = new JLabel("   Posto Libero:");
		pannelloLegenda.add(postoLiberoLabel);
		JButton postoLiberoButton = new JButton();
		postoLiberoButton.setBackground(Color.GREEN);
		postoLiberoButton.setEnabled(false);
		postoLiberoButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		pannelloLegenda.add(postoLiberoButton);
		
		JLabel vendutoLabel = new JLabel("  Posto Venduto:");
		pannelloLegenda.add(vendutoLabel);
		JButton postoVendutoButton = new JButton();
		postoVendutoButton.setBackground(Color.RED);
		postoVendutoButton.setEnabled(false);
		postoVendutoButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		pannelloLegenda.add(postoVendutoButton);
		
		JLabel prenotatoDaTeLabel = new JLabel("  Posto prenotato da te:");
		pannelloLegenda.add(prenotatoDaTeLabel);
		JButton prenotatoDaTeButton = new JButton();
		prenotatoDaTeButton.setBackground(Color.YELLOW);
		prenotatoDaTeButton.setEnabled(false);
		prenotatoDaTeButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		pannelloLegenda.add(prenotatoDaTeButton);
		
		JLabel prenotatoDaAltroClienteLabel = new JLabel("  Posto prenotato da altro Cliente: ");
		pannelloLegenda.add(prenotatoDaAltroClienteLabel);
		
		JButton prenotatoDaAltroClienteButton = new JButton();
		prenotatoDaAltroClienteButton.setBackground(Color.BLUE);
		prenotatoDaAltroClienteButton.setEnabled(false);
		prenotatoDaAltroClienteButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		pannelloLegenda.add(prenotatoDaAltroClienteButton);
		
		
		return pannelloLegenda;
	}
	
	
	/**
	 * Crea l'area testo e la scrollPane dove vengono mostrate prenotazioni e acquisti del cliente per questa partita.
	 * @return La scrollPane prenotazioni-Acquisti.
	 */
	private JScrollPane creaScrollPrenotazioniAcquisti() {
			areaTesto= new JTextArea(5,20);
			areaTesto.setEditable(false);
			scrollPane=new JScrollPane(areaTesto);
			return scrollPane;
		
	}
	
	
	

	/**
	 * Scrive nell'area testo tutte le prenotazioni ancora attive e gli acquisti effettuati dal cliente per questa partita.
	 */
	private void riempiTextArea() {
		areaTesto.setText(null);
		ArrayList <Biglietto> bigliettiAcquistatiDalCliente= cliente.getBigliettiAcquistati();
		ArrayList <Biglietto> bigliettiPrenotatiAttiviDelCliente= cliente.getPrenotazioniAttive();
		//Creo ora due array che riguardano i bigliettiacquistati dal cliente (e prenotazioni attive) pero solo su questa partita
		ArrayList <Biglietto> bigliettiAcquistatiDalClienteDiQuestaPartita= new ArrayList <Biglietto>();
		ArrayList <Biglietto> bigliettiPrenotatiAttiviDelClienteDiQuestaPartita=  new ArrayList <Biglietto>();
		for (Biglietto biglietto: bigliettiAcquistatiDalCliente){
			if (partita.equals(biglietto.getPartita()))
					bigliettiAcquistatiDalClienteDiQuestaPartita.add(biglietto);
					
		}
		for (Biglietto biglietto: bigliettiPrenotatiAttiviDelCliente){
			if (partita.equals(biglietto.getPartita()))
				bigliettiPrenotatiAttiviDelClienteDiQuestaPartita.add(biglietto);
		}
		if (bigliettiAcquistatiDalClienteDiQuestaPartita.size()==0 && bigliettiPrenotatiAttiviDelClienteDiQuestaPartita.size()==0)
			areaTesto.append("Non hai nessuna prenotazione attiva, e non hai acquistato alcun biglietto per questa partita al momento");
		else {//Il cliente ha almeno un biglietto acquistato o una prenotazione attiva
			if (bigliettiAcquistatiDalClienteDiQuestaPartita.size()>0){
				areaTesto.append("I biglietti che hai acquistato per questa partita sono:\n");
				for (Biglietto biglietto : bigliettiAcquistatiDalClienteDiQuestaPartita)
					areaTesto.append(biglietto.mostraDettagli() + "\n");
			}
			if (bigliettiPrenotatiAttiviDelClienteDiQuestaPartita.size()>0){
				areaTesto.append("I biglietti che hai prenotato per questa partita, e le cui prenotazioni sono ancora attive sono:\n");
				for (Biglietto biglietto : bigliettiPrenotatiAttiviDelClienteDiQuestaPartita)
					areaTesto.append(biglietto.mostraDettagli() + "\n");
			}
		}
		
	}
	
	
	
	/**
	 * Crea il pannello che mostra la figura dello stadio: mostra il campo da calcio al centro e ai 4 punti cardinali i 4 settori.
	 */
	private void creaPannelloStadio() {

		
		
		 String path = "campodicalcio3mod.jpg";
	        File file = new File(path);
	        BufferedImage image;
			try {
				image = ImageIO.read(file);
				JLabel label = new JLabel(new ImageIcon(image));
				label.setBorder(new EtchedBorder());
				label.setBounds(243, 296, 881, 244);
				
				
				add(creaPannelloSettoreNord());
				
				
				add(creaPannelloSettoreWest());
				add(label);
				add(creaPannelloSettoreEst());
				
				
				add(creaPannelloSettoreSud());
				
			} 
			catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Non ho letto l'immagine stadio", "Errore", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
	
	}
	
	

	/**
	 * Crea il pannello Nord dello stadio con i suoi biglietti.
	 * @return Il pannello Nord.
	 */
	private JScrollPane creaPannelloSettoreNord(){
		JPanel pannelloSettoreNord = new JPanel();
		int righe, colonne;
		righe= partita.getTribuneCentraliHeight();
		colonne= partita.getTribuneCentraliLength();
		aggiungiBottoniToPannello(pannelloSettoreNord,SettoriPossibili.Nord,righe, colonne, bigliettiSettoreNord);
		JScrollPane panelPane= new JScrollPane (pannelloSettoreNord);
		panelPane.setBorder(new TitledBorder(new EtchedBorder(), "Settore N"));
		panelPane.setBounds(242, 132, 881, 166);
		
		return panelPane;
	}
	
	/**
	 * Crea il pannello Sud dello stadio con i suoi biglietti.
	 * @return Il pannello Sud.
	 */
	private JScrollPane creaPannelloSettoreSud(){
		JPanel pannelloSettoreSud = new JPanel();
		int righe, colonne;
		righe= partita.getTribuneCentraliHeight();
		colonne= partita.getTribuneCentraliLength();
		aggiungiBottoniToPannello(pannelloSettoreSud,SettoriPossibili.Sud,righe, colonne,bigliettiSettoreSud);
		JScrollPane panelPane= new JScrollPane (pannelloSettoreSud);
		panelPane.setBorder(new TitledBorder(new EtchedBorder(), "Settore S"));
		panelPane.setBounds(242, 537, 881, 166);

		return panelPane;
	}
	

	/**
	 * Crea il pannello West dello stadio con i suoi biglietti.
	 * @return Il pannello West.
	 */
	private JScrollPane creaPannelloSettoreWest(){
		JPanel pannelloSettoreWest = new JPanel();
		int righe, colonne;
		righe= partita.getTribuneLateraliHeight();
		colonne= partita.getTribuneLateraliLength();
		aggiungiBottoniToPannello(pannelloSettoreWest,SettoriPossibili.Ovest,righe, colonne,bigliettiSettoreWest);
		JScrollPane panelPane= new JScrollPane (pannelloSettoreWest);
		panelPane.setBorder(new TitledBorder(new EtchedBorder(), "Settore W"));
		panelPane.setBounds(0, 290, 247, 253);
	
		return panelPane;
		
		
	}

	/**
	 * Crea il pannello Est dello stadio con i suoi biglietti.
	 * @return Il pannello Est.
	 */
	private JScrollPane creaPannelloSettoreEst(){
		JPanel pannelloSettoreEst = new JPanel();
		int righe, colonne;
		righe= partita.getTribuneLateraliHeight();
		colonne= partita.getTribuneLateraliLength();
		aggiungiBottoniToPannello(pannelloSettoreEst,SettoriPossibili.Est,righe, colonne,bigliettiSettoreEst);
		JScrollPane panelPane= new JScrollPane (pannelloSettoreEst);
		panelPane.setBorder(new TitledBorder(new EtchedBorder(), "Settore E"));
		panelPane.setBounds(1122, 290, 247, 253);

		return panelPane;
		
		
	}


	
	
	
	/**
	 * Ogni pannello passato in input viene riempito di tutt i suoi posti secondo una griglia. 
	 * @param pannello Il pannello da riempire.
	 * @param settore Il settore del campo a cui si riferisce il pannello.
	 * @param righe Il numero di righe per i posti del settore.
	 * @param colonne Il numero di colonne per i posti del settore.
	 * @param bigliettiSettoreScelto L'array di biglietti riferiti al settore scelto.
	 */
	private void aggiungiBottoniToPannello(JPanel pannello, Partita.SettoriPossibili settore,int righe, int colonne, Biglietto[][] bigliettiSettoreScelto){
		pannello.setLayout(new GridBagLayout());
		GridBagConstraints gbc= new GridBagConstraints();
		gbc.gridy=0;
		JPanel pannelloRiga1= new JPanel();
		pannello.add(pannelloRiga1,gbc);
	
		
		ImageIcon icon = new ImageIcon ("rc.png");
		JLabel labelRigaColonna= new JLabel (icon);
		labelRigaColonna.setBorder(new EtchedBorder());
		labelRigaColonna.setVerticalAlignment(JLabel.BOTTOM);
		labelRigaColonna.setHorizontalAlignment(JLabel.CENTER);
		
		labelRigaColonna.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		pannelloRiga1.add(labelRigaColonna);
		for (int j=0; j<colonne;j++){ //primariga
			int simbolo= j+1;
			JLabel labelColonna= new JLabel ("" + simbolo);
			Font font= labelColonna.getFont();
			font= font.deriveFont(font.getStyle(),10);
			labelColonna.setFont(font);
			labelColonna.setBorder(new EtchedBorder());
			labelColonna.setVerticalAlignment(JLabel.BOTTOM);
			labelColonna.setHorizontalAlignment(JLabel.CENTER);
			labelColonna.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
			pannelloRiga1.add(labelColonna);
		}
		
		for (int i=0; i<righe;i++){
			JPanel pannelloRiga= new JPanel();
			gbc.gridy=i+1;
			pannello.add(pannelloRiga,gbc);
			int simbolo= i+1;
			JLabel labelRiga= new JLabel ("" + simbolo);
			Font font= labelRiga.getFont();
			font= font.deriveFont(font.getStyle(),10);
			labelRiga.setFont(font);
			labelRiga.setBorder(new EtchedBorder());
			labelRiga.setHorizontalAlignment(JLabel.CENTER);
			labelRiga.setVerticalAlignment(JLabel.CENTER);
			labelRiga.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
			pannelloRiga.add(labelRiga);
				for (int j=0; j<colonne;j++){
					pannelloRiga.add(creaButton(settore,i,j,bigliettiSettoreScelto));
			}
		}
	}
	
	

	/**
	 * Per ogni posto del settore crea il corrispondente bottone.
	 * @param settore Il settore del posto.
	 * @param riga La riga del posto nell'array di bigliettiSettoreScelto. 
	 * @param colonna  La colonna del posto nell'array di bigliettiSettoreScelto. 
	 * @param bigliettiSettoreScelto L'array di biglietti riferiti al settore scelto.
	 * @return Il bottone creato per il posto indicato.
	 */
	private JButton creaButton(Partita.SettoriPossibili settore,int riga, int colonna, Biglietto[][] bigliettiSettoreScelto){
		Biglietto bigliettoDelBottone=bigliettiSettoreScelto[riga][colonna];
		MyBottone button = null;
		try {
			button = new MyBottone(settore,riga,colonna, bigliettoDelBottone,cliente);
			button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
			button.addActionListener (new ClickBottone(button));
			postiGrafici.add(button);
		} 
		catch (IllegalStatoBigliettoException e) {
			e.printStackTrace();
		}

		return button;
	}
	
	
	
	/**
	 * Questo metodo cancella tutte le prenotazioni esistenti per questa partita
	 * @throws IllegalStatoBigliettoException Errore nella definizione dello stato del biglietto
	 * @throws IllegalPrenotazioneException Errore nella prenotazione del biglietto.
	 * @throws IllegalCancellazioneBigliettoException Errore nella cancellazione del biglietto.
	 * @throws PrenotazioneInesistenteException Errore nella cancellazione della prenotazione del biglietto: prenotazione inesistente.
	 */
	private void cancellaTutteLePrenotazioni() throws IllegalStatoBigliettoException, IllegalPrenotazioneException, IllegalCancellazioneBigliettoException, PrenotazioneInesistenteException{
		for (MyBottone bottone: postiGrafici){
			Biglietto bigliettoDelBottone=bottone.getBiglietto();
			if (bigliettoDelBottone!= null && bigliettoDelBottone.getStato()==StatiPossibili.PRENOTATO){
					Cliente clienteDelBiglietto= bigliettoDelBottone.getCliente();
					clienteDelBiglietto.rimuoviPrenotazioneAttiva(bigliettoDelBottone);
					clienteDelBiglietto.addPrenotazioneScaduta(bigliettoDelBottone);
					partita.rimuoviBiglietto(bigliettoDelBottone.getSettore(), bigliettoDelBottone.getRiga(), bigliettoDelBottone.getColonna());
					bigliettoDelBottone.setStato(StatiPossibili.SCADUTO, new GregorianCalendar());
					bottone.setBiglietto(null);
					bottone.setBackground(Color.GREEN);
					bottone.repaint();
				
			}
		}
	}
	
	
	
	
	/**
	 * Questa classe definisce tutto cio che deve accadere quando un cliente clicca su un bottone riferito ad un posto al quale è associato un oggetto di questa classe.
	 * @author Mogavero
	 *
	 */
	class ClickBottone implements ActionListener{

		/**
		 * Crea un nuovo oggetto ClickBottone riferito ad un bottone passato in input.
		 * @param button Il bottone di riferimento.
		 */
		public ClickBottone (MyBottone button){
			this.button=button;
			riga=button.getRiga();
			colonna=button.getColonna();
			settore=button.getSettore();
			
			
		}
		
		/**
		 * Definisce il comportamento da avere nei vari stati possibili del biglietto: che sia libero, acquistato, prenotato dallo stesso cliente attuale o da un altro cliente.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			
		bigliettoDelBottone=button.getBiglietto();	
		
		//se il cliente sta cliccando su un biglietto che esso stesso ha prenotato
		if (bigliettoDelBottone!=null && bigliettoDelBottone.getStato()== StatiPossibili.PRENOTATO && cliente.equals(bigliettoDelBottone.getCliente())){
			String [] cosaFare= {"Acquista il Biglietto", "Cancella Prenotazione", "Annulla"};
			int scelta= JOptionPane.showOptionDialog(null, "Cosa vuoi fare?", "Cosa vuoi fare?",JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, cosaFare, 0);
			if (scelta==0){//il cliente ha scelto di acquistare il biglietto
				try {
					cliente.rimuoviPrenotazioneAttiva(bigliettoDelBottone);
					cliente.addBigliettoAcquistato(bigliettoDelBottone);
					cliente.addPrenotazioneAcquistata(bigliettoDelBottone);
					bigliettoDelBottone.setStato(StatiPossibili.ACQUISTATO, new GregorianCalendar());
					double costoDelBigliettoSelezionato= bigliettoDelBottone.getCosto();
					stadio.addIncasso(costoDelBigliettoSelezionato);
					button.setBackground(Color.RED);
					repaint();
					riempiTextArea();
					JOptionPane.showMessageDialog(null, "Acquisto completato con successo!");
				} 
				catch (PrenotazioneInesistenteException e1) {			
					JOptionPane.showMessageDialog(null, "Errore: " + e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				} 
				catch (IllegalStatoBigliettoException e1) {
					JOptionPane.showMessageDialog(null, "Errore: " + e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				} 
				catch (IllegalPrenotazioneException e1) {
					JOptionPane.showMessageDialog(null, "Errore: " + e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}
			}
			if (scelta==1){//Il cliente ha scelto di cancellare la prenotazione
				try {
					cliente.rimuoviPrenotazioneAttiva(bigliettoDelBottone);	
					partita.rimuoviBiglietto(settore, riga, colonna);
					cliente.addPrenotazioneCancellata(bigliettoDelBottone);
					bigliettoDelBottone.setStato(StatiPossibili.CANCELLATO, new GregorianCalendar());
					button.setBiglietto(null);
					button.setBackground(Color.GREEN);
					repaint();
					riempiTextArea();
					JOptionPane.showMessageDialog(null, "Cancellazione completata con successo!");
				} 
				catch (IllegalStatoBigliettoException e1) {
					JOptionPane.showMessageDialog(null, "Errore: " + e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}	
				catch (IllegalCancellazioneBigliettoException e1) {
					JOptionPane.showMessageDialog(null, "Errore: " + e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				} 
				catch (PrenotazioneInesistenteException e1) {
					JOptionPane.showMessageDialog(null, "Errore: " + e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				} 
				catch (IllegalPrenotazioneException e1) {
					JOptionPane.showMessageDialog(null, "Errore: " + e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}
						
			}
				
				
		}
	
		else{
			String [] cosaFare= {"Acquista il Biglietto", "Prenota Il Biglietto", "Annulla"};
			int scelta= JOptionPane.showOptionDialog(null, "Cosa vuoi fare?", "Cosa vuoi fare?",JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, cosaFare, 0);
			if (scelta==0){//Il cliente ha scelto di acquistare il biglietto
				Biglietto bigliettoAcquistato= new Biglietto(migliorPrezzoDisponibile, partita, cliente, settore, riga, colonna);
				try {
					partita.aggiungiBiglietto(settore, riga, colonna, bigliettoAcquistato);
					bigliettoAcquistato.setStato(StatiPossibili.ACQUISTATO, new GregorianCalendar());
					cliente.addBigliettoAcquistato(bigliettoAcquistato);
					button.setBiglietto(bigliettoAcquistato); //il biglietto del bottone ora non è più null ma è il biglietto che ho acquistato
					stadio.addIncasso(migliorPrezzoDisponibile);
					button.setBackground(Color.RED);
					repaint();
					riempiTextArea();
					JOptionPane.showMessageDialog(null, "Acquisto completato con successo!");
				}
				catch (PostoIndisponibileException e1) {
					JOptionPane.showMessageDialog(null, "Errore: " + e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}
				catch (IllegalInserimentoBigliettoException e1) {
					JOptionPane.showMessageDialog(null, "Errore: " + e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				} catch (IllegalStatoBigliettoException e1) {
					JOptionPane.showMessageDialog(null, "Errore: " + e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				} catch (IllegalPrenotazioneException e1) {
					JOptionPane.showMessageDialog(null, "Errore: " + e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				} 
				
			}
			else if (scelta==1){//Il Cliente ha scelto di prenotare il biglietto
				Biglietto bigliettoPrenotato= new Biglietto(migliorPrezzoDisponibile, partita, cliente, settore, riga, colonna);
				try {
					partita.aggiungiBiglietto(settore, riga, colonna, bigliettoPrenotato);
					bigliettoPrenotato.setStato(StatiPossibili.PRENOTATO, new GregorianCalendar());
					cliente.addPrenotazioneAttiva(bigliettoPrenotato);
					button.setBiglietto(bigliettoPrenotato); //il biglietto del bottone ora non è più null ma è il biglietto che ho acquistato
					button.setBackground(Color.YELLOW);
					repaint();
					riempiTextArea();
					JOptionPane.showMessageDialog(null, "Prenotazione completata con successo!\nRicorda questa prenotazione sarà attiva fino a 12 ore prima della partita");
				}
				catch (PostoIndisponibileException e1) {
					JOptionPane.showMessageDialog(null, "Errore: " + e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}
				catch (IllegalInserimentoBigliettoException e1) {
					JOptionPane.showMessageDialog(null, "Errore: " + e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				} catch (IllegalStatoBigliettoException e1) {
					JOptionPane.showMessageDialog(null, "Errore: " + e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
					
				} catch (IllegalPrenotazioneException e1) {//Se il biglietto non può essere prenotato, devo cancellarlo dalla partita, e ne approfitto per cancellare anche tutte le altre prenotazioni (che anch'esse non saranno più valide)
					JOptionPane.showMessageDialog(null, "Errore: " + e1.getMessage() + "\nTutte le prenotazioni per questa partita sono scadute!" , "Errore ", JOptionPane.ERROR_MESSAGE);
					try {
						partita.rimuoviBiglietto(settore, riga, colonna);
						cancellaTutteLePrenotazioni();
						riempiTextArea();
					} 
					catch (IllegalCancellazioneBigliettoException e2) {
						e2.printStackTrace();
						
					} 
					catch (IllegalStatoBigliettoException e2) {
						
						e2.printStackTrace();
					} 
					catch (IllegalPrenotazioneException e2) {
					
						e2.printStackTrace();
					} 
					catch (PrenotazioneInesistenteException e2) {
						
						e2.printStackTrace();
					}
				} 
			}
		}
	}
		
		private MyBottone button;
		private Biglietto bigliettoDelBottone;
		private int riga;
		private int colonna;
		private Partita.SettoriPossibili settore;
		
	}

	



	public static final int BUTTON_WIDTH=30, BUTTON_HEIGHT=30;
	public static final int ALTEZZA_PANEL_VUOTO=100, LUNGHEZZA_PANEL_VUOTO=100;
	private Partita partita;
	private Stadio stadio;
	private double migliorPrezzoDisponibile;
	private Cliente cliente;
	private JTextArea areaTesto;
	private JScrollPane scrollPane;
	private Biglietto [][] bigliettiSettoreNord, bigliettiSettoreSud, bigliettiSettoreWest, bigliettiSettoreEst;
	private ArrayList <MyBottone> postiGrafici;
}

