package Libreria.Modello.Frame;

import Libreria.Modello.*;
import Libreria.Modello.Command.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private static final Genere[] GENERI_LIBRO = {
            Genere.SAGGIO,
            Genere.ROMANZO_DI_AVVENTURA,
            Genere.ROMANZO_FANTASY,
            Genere.FANTASCIENTIFICO,
            Genere.HORROR
    };
    private static final Genere[] GENERI_MANGA = {
            Genere.SHONEN,
            Genere.SEINEN,
            Genere.KODOMO,
            Genere.JOSEI,
            Genere.SHOJO
    };
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
        PannelloOrdinamento po= new PannelloOrdinamento(libreria,listModel);
        add(po,BorderLayout.WEST);

        PannelloRicerca pr= new PannelloRicerca(libreria,listModel);
       add(pr,BorderLayout.EAST);

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
        TipiDiOggetto sel = (TipiDiOggetto) tipi.getSelectedItem();
        Genere[] init;
        if (sel == TipiDiOggetto.LIBRO) {
            init = GENERI_LIBRO;
        } else {
            init = GENERI_MANGA;
        }
        generi.setModel(new DefaultComboBoxModel<Genere>(init));


// listener per filtrare i generi quando cambia il tipo
    tipi.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            TipiDiOggetto selected = (TipiDiOggetto) tipi.getSelectedItem();
            Genere[] toShow;
            if (selected == TipiDiOggetto.LIBRO) {
                toShow = GENERI_LIBRO;
            } else {
                toShow = GENERI_MANGA;
            }
            generi.setModel(new DefaultComboBoxModel<Genere>(toShow));
        }
    });


    }
    private Libro mostaPannelloModifica(Libro originale){
        JDialog dialog = new JDialog(this, "Modifica Libro", true);
        dialog.setLayout(new BorderLayout());
       LibroDaPannello pannelloModifica= new LibroDaPannello();
       pannelloModifica.popolaDaLibro(originale);
       dialog.add(pannelloModifica,BorderLayout.CENTER);
       JButton salva= new JButton("SALVA");
       JButton annulla= new JButton("ANNULLA");
       JPanel panelPulsanti= new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelPulsanti.add(salva);
        panelPulsanti.add(annulla);
        dialog.add(panelPulsanti,BorderLayout.SOUTH);
        final Libro[] risultato= {null};
        salva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Libro updated = pannelloModifica.getLibroDaCampi();
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
        annulla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                risultato[0]=null;
                dialog.dispose();
            }
        });
        pannelloModifica.setPreferredSize(new Dimension(500,500));
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        return risultato[0];
    }
    public static void main(String ... args){
        SwingUtilities.invokeLater(()->new MainFrame());
    }

}
