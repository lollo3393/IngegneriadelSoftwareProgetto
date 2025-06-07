package Libreria.Modello;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibroDaPannello extends JPanel {
    private static final Genere[] GENERI_LIBRI = {
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
    private final JTextField AutoreTesto = new JTextField(15);
    private final JTextField TitoloTesto = new JTextField(15);
    private final JTextField IsbnTesto = new JTextField(15);
    private final JComboBox<Genere> generi = new JComboBox<>(Genere.values());
    private final JComboBox<StatoDiLettura> stati = new JComboBox<>(StatoDiLettura.values());
    private final JComboBox<TipiDiOggetto> tipi = new JComboBox<>(TipiDiOggetto.values());

    private final JLabel labelValutazione = new JLabel("Valutazione : ");
    private final JComboBox<Integer> Valutazioni = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
    private final JLabel labelRecensione = new JLabel("Recensione : ");
    private final JTextArea areaRecensione = new JTextArea(3, 15);
    private final JScrollPane scrollRecensione = new JScrollPane(areaRecensione, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    public LibroDaPannello() {
        super(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        gb.insets = new Insets(4, 4, 4, 4);
        gb.fill = GridBagConstraints.HORIZONTAL;

        //AUTORE
        gb.gridx = 0;
        gb.gridy = 0;
        add(new JLabel("Autore : "), gb);
        gb.gridx = 1;
        add(AutoreTesto, gb);

        //TITULO (per gli interisti 0 tituli)
        gb.gridx = 0;
        gb.gridy = 1;
        add(new JLabel("Titolo: "), gb);
        gb.gridx = 1;
        add(TitoloTesto, gb);

        //ISBN
        gb.gridx = 0;
        gb.gridy = 2;
        add(new JLabel("ISBN: "), gb);
        gb.gridx = 1;
        add(IsbnTesto, gb);

        //Genere
        gb.gridx = 0;
        gb.gridy = 3;
        add(new JLabel("Genere: "), gb);
        gb.gridx = 1;
        add(generi, gb);

        //Tipologia
        gb.gridx = 0;
        gb.gridy = 4;
        add(new JLabel("Tipologia: "), gb);
        gb.gridx = 1;
        add(tipi, gb);

        //Stato di lettura
        gb.gridx = 0;
        gb.gridy = 5;
        add(new JLabel("Stato di lettura: "), gb);
        gb.gridx = 1;
        add(stati, gb);

        gb.gridx = 0;
        gb.gridy = 6;
        add(labelValutazione, gb);
        gb.gridx = 1;
        add(Valutazioni, gb);
        labelValutazione.setVisible(false);
        Valutazioni.setVisible(false);

        gb.gridx = 0;
        gb.gridy = 7;
        add(labelRecensione, gb);
        gb.gridx = 1;
        add(scrollRecensione, gb);
        labelRecensione.setVisible(false);
        scrollRecensione.setVisible(false);

        stati.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StatoDiLettura s = (StatoDiLettura) stati.getSelectedItem();
                boolean isLetto = (s == StatoDiLettura.LETTO);
                labelValutazione.setVisible(isLetto);
                labelRecensione.setVisible(isLetto);
                Valutazioni.setVisible(isLetto);
                scrollRecensione.setVisible(isLetto);
                revalidate();
                repaint();
            }
        });
        tipi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TipiDiOggetto selected = (TipiDiOggetto) tipi.getSelectedItem();
                Genere[] corretto;
                if (selected == TipiDiOggetto.LIBRO) {
                    corretto = GENERI_LIBRI;
                } else {
                    corretto = GENERI_MANGA;
                }
                generi.setModel(new DefaultComboBoxModel<>(corretto));
            }
        });
        {
            TipiDiOggetto sel = (TipiDiOggetto) tipi.getSelectedItem();
            Genere[] init = sel == TipiDiOggetto.LIBRO ? GENERI_LIBRI : GENERI_MANGA;
            generi.setModel(new DefaultComboBoxModel<>(init));
        }

    }

    public void popolaDaLibro(Libro l) {
        AutoreTesto.setText(l.getAutore());
        TitoloTesto.setText(l.getTitolo());
        IsbnTesto.setText(l.getISBN());
        IsbnTesto.setEditable(false);
        generi.setSelectedItem(l.getGenere());
        tipi.setSelectedItem(l.getTipo());
        stati.setSelectedItem(l.getStatoDiLettura());
        if (l.getStatoDiLettura() == StatoDiLettura.LETTO) {
            Valutazioni.setSelectedItem(l.getValutazione());
            areaRecensione.setText(l.getRecensione());
            labelRecensione.setVisible(true);
            labelValutazione.setVisible(true);
            Valutazioni.setVisible(true);
            scrollRecensione.setVisible(true);
        } else {
            labelRecensione.setVisible(false);
            labelValutazione.setVisible(false);
            Valutazioni.setVisible(false);
            scrollRecensione.setVisible(false);
            Valutazioni.setSelectedIndex(0);
            areaRecensione.setText("");
        }
    }

    public Libro getLibroDaCampi() {
        String autore = AutoreTesto.getText().trim();
        String Isbn = IsbnTesto.getText().trim();
        String titolot = TitoloTesto.getText().trim();
        Genere genere = (Genere) generi.getSelectedItem();
        StatoDiLettura stato = (StatoDiLettura) stati.getSelectedItem();
        TipiDiOggetto tipo = (TipiDiOggetto) tipi.getSelectedItem();
        int valutazione = 0;
        String recensione = null;
        if (stato == StatoDiLettura.LETTO) {
            valutazione = (Integer) Valutazioni.getSelectedItem();
            recensione = areaRecensione.getText().trim();
        }
        Libro.Builder builder = new Libro.Builder().setAutore(autore).setTitolo(titolot).setISBN(Isbn).setGenere(genere).setTipo(tipo).setStatoDiLettura(stato);
        if (stato == StatoDiLettura.LETTO) {
            builder.setValutazione(valutazione).setRecensione(recensione);
        }
      return builder.build();
    }


}
