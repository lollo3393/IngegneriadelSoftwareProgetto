package Libreria.Modello;

import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private final Libreria libreria;
    private final CommandManager manager;
    private final JTextField TestoAutore= new JTextField(15);
    private final JTextField TitoloTesto= new JTextField(15);
    private final JTextField ISBNTesto= new JTextField(15);
    private final JComboBox<Genere> generi=new JComboBox<>(Genere.values());
    private final JComboBox<TipiDiOggetto> tipi= new JComboBox<>(TipiDiOggetto.values());
    private final JComboBox<StatoDiLettura> stati= new JComboBox<>(StatoDiLettura.values());
    private final JLabel valutazioneLabel = new JLabel("Valutazione:");
    private final JComboBox<Integer> valutazioni = new JComboBox<>(new Integer[]{1,2,3,4,5});

    private final JLabel recensioneLabel = new JLabel("Recensione:");
    private final JTextArea areaRecensione = new JTextArea(3, 15);
    private final JScrollPane scrollRecensione = new JScrollPane(areaRecensione,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    private final JButton Aggiungi= new JButton("AGGIUNGI");
    private final JButton Rimuovi= new JButton("RIMUOVI");
    private final JButton undo= new JButton("UNDO");
    private final JButton modifica = new JButton("MODIFICA");
    private final DefaultListModel<Libro> listModel= new DefaultListModel<>();
    private final JList<Libro> ListaLibri=new JList<>(listModel);
    private final JLabel label= new JLabel(" ");

    public MainFrame(){
        super("Gestione libreria");
        this.libreria=Libreria.getInstance();
        this.manager= new CommandManager();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel pannelloInput=new JPanel(new GridBagLayout());
        GridBagConstraints gb= new GridBagConstraints();
        gb.insets= new Insets(4,4,4,4);
        gb.fill= GridBagConstraints.HORIZONTAL;
        gb.gridx=0;
        gb.gridy=0;
        pannelloInput.add(new Label("Autore"),gb);
        gb.gridx=0;
        gb.gridy=1;
        pannelloInput.add(TestoAutore,gb);

        gb.gridx = 0; gb.gridy = 1;
        pannelloInput.add(new JLabel("Titolo:"), gb);
        gb.gridx = 1; gb.gridy = 1;
        pannelloInput.add(TitoloTesto, gb);


        gb.gridx = 0; gb.gridy = 2;
        pannelloInput.add(new JLabel("ISBN:"), gb);
        gb.gridx = 1; gb.gridy = 2;
        pannelloInput.add(ISBNTesto, gb);

        gb.gridx = 0; gb.gridy = 3;
        pannelloInput.add(new JLabel("genere:"), gb);
        gb.gridx = 1; gb.gridy = 3;
        pannelloInput.add(generi, gb);

        gb.gridx = 0; gb.gridy = 4;
        pannelloInput.add(new JLabel("tipologia:"), gb);
        gb.gridx = 1; gb.gridy = 4;
        pannelloInput.add(tipi, gb);

        gb.gridx = 0; gb.gridy = 5;
        pannelloInput.add(new JLabel("stato"), gb);
        gb.gridx = 1; gb.gridy = 5;
        pannelloInput.add(stati, gb);
        gb.gridx = 0; gb.gridy = 6;
        pannelloInput.add(valutazioneLabel, gb);
        gb.gridx = 1;
        pannelloInput.add(valutazioni, gb);
        valutazioneLabel.setVisible(false);
        valutazioni.setVisible(false);

        gb.gridx = 0; gb.gridy = 7;
        pannelloInput.add(recensioneLabel, gb);
        gb.gridx = 1;
        pannelloInput.add(scrollRecensione, gb);
        recensioneLabel.setVisible(false);
        scrollRecensione.setVisible(false);

        JPanel pulsantiPanel= new JPanel(new FlowLayout(FlowLayout.LEFT));
        pulsantiPanel.add(Aggiungi);
        pulsantiPanel.add(Rimuovi);
        pulsantiPanel.add(undo);
        pulsantiPanel.add(modifica);

        gb.gridx=0;
        gb.gridy=8;
        gb.gridwidth=2;
        pannelloInput.add(pulsantiPanel,gb);
        add(pannelloInput,BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(ListaLibri);
        add(scrollPane, BorderLayout.CENTER);

        label.setForeground(Color.red);
        add(label,BorderLayout.SOUTH);
        inizializzaListners();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);


    }
    private void inizializzaListners(){
        Aggiungi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText("");//pulisce quello che c'era scritto prima
                try{
                    String autore= TestoAutore.getText().trim();
                    String titolo=TitoloTesto.getText().trim();
                    String isbn=ISBNTesto.getText().trim();
                    Genere genere= (Genere) generi.getSelectedItem();
                    TipiDiOggetto tipo= (TipiDiOggetto) tipi.getSelectedItem();
                    StatoDiLettura stato= (StatoDiLettura) stati.getSelectedItem();
                    int valutazione= 0;
                    String recensione = null;
                    if(stato== StatoDiLettura.LETTO){
                        valutazione= (Integer) valutazioni.getSelectedItem();
                        recensione= areaRecensione.getText().trim();
                    }
                    Libro.Builder builder= new Libro.Builder().setAutore(autore).setTitolo(titolo).setISBN(isbn).setGenere(genere).setTipo(tipo).setStatoDiLettura(stato);
                    if(stato==StatoDiLettura.LETTO){
                        builder.setValutazione(valutazione).setRecensione(recensione);
                    }
                    Libro nuovoLibro = builder.build();
                    Command cmd= new AggiungiLibroCommand(libreria,nuovoLibro);
                    manager.eseguiComando(cmd);

                    listModel.addElement(nuovoLibro);
                    label.setText("Libro aggiunto correttamente");
                    label.setForeground(Color.green);

                }catch (IllegalArgumentException er){
                    label.setForeground(Color.red);
                    label.setText(er.getMessage());
                }



            }
        });
        Rimuovi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText("");
                Libro selezionato= ListaLibri.getSelectedValue();
                if(selezionato==null){
                    label.setForeground(Color.red);
                    label.setText("Inserisci testo da rimuovere");
                    return;
                }
                String isbRimuovere=selezionato.getISBN();
                try{
                    Command cmd= new RimuoviLibroCommand(libreria,isbRimuovere);
                    manager.eseguiComando(cmd);
                    listModel.removeElement(selezionato);
                    label.setForeground(Color.green);
                    label.setText("Libro rimosso correttamente");

                }catch (IllegalArgumentException er){
                    label.setForeground(Color.red);
                    label.setText(er.getMessage());
                }
            }
        });
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText("");
                if(!manager.canUndo()){
                    label.setForeground(Color.red);
                    label.setText("Nessun comando da annullare");
                    return;
                }
                manager.undo();
                listModel.clear();
                for(Libro l: libreria.getTuttiLibri()){
                    listModel.addElement(l);
                }
                label.setForeground(Color.green);
                label.setText("Operazione annullata");


            }
        });
        stati.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StatoDiLettura statoSelezionato = (StatoDiLettura) stati.getSelectedItem();
                boolean isLetto = (statoSelezionato == StatoDiLettura.LETTO);

                // Se “LETTO”, mostro i campi; altrimenti li nascondo
                valutazioneLabel.setVisible(isLetto);
                valutazioni.setVisible(isLetto);
                recensioneLabel.setVisible(isLetto);
                scrollRecensione.setVisible(isLetto);

                // Quando cambio visibilità, è buona norma ridisegnare il pannello
                valutazioneLabel.getParent().revalidate();
                valutazioneLabel.getParent().repaint();
            }
        });
        modifica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText("");
                Libro selezionato=ListaLibri.getSelectedValue();
                if(selezionato==null){
                    label.setForeground(Color.red);
                    label.setText("Seleziona prima un libro da modificare");
                    return;
                }
                Libro modificato= mostaPannelloModifica(selezionato);
                if(modificato==null){
                    return;
                }
                if(!modificato.getISBN().equals(selezionato.getISBN())){
                    label.setForeground(Color.red);
                    label.setText("non puoi modificare l'isbn");
                    return;
                }
                try{
                    Command cmd= new ModficaLibroCommand(libreria,modificato);
                    manager.eseguiComando(cmd);
                    listModel.clear();
                    for(Libro l: libreria.getTuttiLibri()){
                        listModel.addElement(l);
                    }
                    label.setForeground(Color.green);
                    label.setText("Modificato correttamente");

                }catch (IllegalArgumentException ex){
                    label.setForeground(Color.red);
                    label.setText(ex.getMessage());
                }
            }
        });


    }
    private Libro mostaPannelloModifica(Libro originale){
        JDialog dialog = new JDialog(this, "Modifica Libro", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4, 4, 4, 4);
        gc.fill = GridBagConstraints.HORIZONTAL;
        JTextField txtAutoreMod = new JTextField(originale.getAutore(), 15);
        JTextField txtTitoloMod = new JTextField(originale.getTitolo(), 15);
        JTextField txtISBNMod = new JTextField(originale.getISBN(), 15);
        txtISBNMod.setEditable(false);
        JComboBox<Genere> comboGenereMod = new JComboBox<>(Genere.values());
        comboGenereMod.setSelectedItem(originale.getGenere());

        JComboBox<TipiDiOggetto> comboTipoMod = new JComboBox<>(TipiDiOggetto.values());
        comboTipoMod.setSelectedItem(originale.getTipo());

        JComboBox<StatoDiLettura> comboStatoMod = new JComboBox<>(StatoDiLettura.values());
        comboStatoMod.setSelectedItem(originale.getStatoDiLettura());
        JLabel lblValMod = new JLabel("Valutazione:");
        JComboBox<Integer> comboValMod = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        JLabel lblRecMod = new JLabel("Recensione:");
        JTextArea areaRecMod = new JTextArea(3, 15);
        JScrollPane scrollRecMod = new JScrollPane(areaRecMod,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        if (originale.getStatoDiLettura() == StatoDiLettura.LETTO) {
            comboValMod.setSelectedItem(originale.getValutazione());
            areaRecMod.setText(originale.getRecensione());
            lblValMod.setVisible(true);
            comboValMod.setVisible(true);
            lblRecMod.setVisible(true);
            scrollRecMod.setVisible(true);
        } else {
            lblValMod.setVisible(false);
            comboValMod.setVisible(false);
            lblRecMod.setVisible(false);
            scrollRecMod.setVisible(false);
        }
        // RIGA 0: Autore
        gc.gridx = 0; gc.gridy = 0;
        dialog.add(new JLabel("Autore:"), gc);
        gc.gridx = 1;
        dialog.add(txtAutoreMod, gc);

        // RIGA 1: Titolo
        gc.gridx = 0; gc.gridy = 1;
        dialog.add(new JLabel("Titolo:"), gc);
        gc.gridx = 1;
        dialog.add(txtTitoloMod, gc);

        // RIGA 2: ISBN (non editabile)
        gc.gridx = 0; gc.gridy = 2;
        dialog.add(new JLabel("ISBN:"), gc);
        gc.gridx = 1;
        dialog.add(txtISBNMod, gc);

        // RIGA 3: Genere
        gc.gridx = 0; gc.gridy = 3;
        dialog.add(new JLabel("Genere:"), gc);
        gc.gridx = 1;
        dialog.add(comboGenereMod, gc);

        // RIGA 4: Tipologia
        gc.gridx = 0; gc.gridy = 4;
        dialog.add(new JLabel("Tipologia:"), gc);
        gc.gridx = 1;
        dialog.add(comboTipoMod, gc);

        // RIGA 5: Stato di lettura
        gc.gridx = 0; gc.gridy = 5;
        dialog.add(new JLabel("Stato:"), gc);
        gc.gridx = 1;
        dialog.add(comboStatoMod, gc);

        // RIGA 6: Valutazione
        gc.gridx = 0; gc.gridy = 6;
        dialog.add(lblValMod, gc);
        gc.gridx = 1;
        dialog.add(comboValMod, gc);

        // RIGA 7: Recensione
        gc.gridx = 0; gc.gridy = 7;
        dialog.add(lblRecMod, gc);
        gc.gridx = 1;
        dialog.add(scrollRecMod, gc);

        // RIGA 8: Pulsanti SALVA e ANNULLA
        JButton btnSalva = new JButton("SALVA");
        JButton btnAnnulla = new JButton("ANNULLA");
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtn.add(btnSalva);
        panelBtn.add(btnAnnulla);

        gc.gridx = 0; gc.gridy = 8;
        gc.gridwidth = 2;
        dialog.add(panelBtn, gc);

        // 4) Listener su comboStatoMod per mostrare/nascondere valutazione+recensione
        comboStatoMod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StatoDiLettura s = (StatoDiLettura) comboStatoMod.getSelectedItem();
                boolean isLetto = (s == StatoDiLettura.LETTO);
                lblValMod.setVisible(isLetto);
                comboValMod.setVisible(isLetto);
                lblRecMod.setVisible(isLetto);
                scrollRecMod.setVisible(isLetto);
                dialog.revalidate();
                dialog.repaint();
            }
        });

        // 5) Variabile per “ritornare” il risultato
        final Libro[] risultato = { null };

        // 6) Listener per “SALVA”
        btnSalva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String autoreMod = txtAutoreMod.getText().trim();
                    String titoloMod = txtTitoloMod.getText().trim();
                    String isbnMod = txtISBNMod.getText().trim();
                    Genere genereMod = (Genere) comboGenereMod.getSelectedItem();
                    TipiDiOggetto tipoMod = (TipiDiOggetto) comboTipoMod.getSelectedItem();
                    StatoDiLettura statoMod = (StatoDiLettura) comboStatoMod.getSelectedItem();

                    int valMod = 0;
                    String recMod = null;
                    if (statoMod == StatoDiLettura.LETTO) {
                        valMod = (Integer) comboValMod.getSelectedItem();
                        recMod = areaRecMod.getText().trim();
                    }

                    // Creo il nuovo Libro con lo stesso ISBN
                    Libro.Builder builder = new Libro.Builder()
                            .setAutore(autoreMod)
                            .setTitolo(titoloMod)
                            .setISBN(isbnMod)
                            .setGenere(genereMod)
                            .setTipo(tipoMod)
                            .setStatoDiLettura(statoMod);
                    if (statoMod == StatoDiLettura.LETTO) {
                        builder.setValutazione(valMod).setRecensione(recMod);
                    }
                    Libro updated = builder.build();
                    risultato[0] = updated;
                    dialog.dispose();
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(dialog,
                            ex.getMessage(),
                            "Errore di validazione",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 7) Listener per “ANNULLA”
        btnAnnulla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                risultato[0] = null;
                dialog.dispose();
            }
        });

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        return risultato[0];
    }
    public static void main(String ... args){
        SwingUtilities.invokeLater(()->new MainFrame());
    }

}
