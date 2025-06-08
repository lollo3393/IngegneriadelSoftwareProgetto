package Libreria.Modello.Frame;

import Libreria.Modello.Libreria;
import Libreria.Modello.Libro;
import Libreria.Modello.Ordinamento.CriteriDiOrdinamento;
import Libreria.Modello.Ordinamento.Ordinatore;
import Libreria.Modello.Ordinamento.Ordine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PannelloOrdinamento extends JPanel {
    private final JComboBox<CriteriDiOrdinamento> criteri = new JComboBox<CriteriDiOrdinamento>(CriteriDiOrdinamento.values());
    private final JComboBox<Ordine> ordini = new JComboBox<Ordine>(Ordine.values());
    private final JButton ordina= new JButton("ORDINA");

    public PannelloOrdinamento(final Libreria libreria, final DefaultListModel<Libro> listModel){
        super(new FlowLayout(FlowLayout.LEFT));
        add(new JLabel("Criterio:"));
        add(criteri);
        add(new JLabel("Ordine:"));
        add(ordini);
        add(ordina);
        ordina.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CriteriDiOrdinamento crit =
                        (CriteriDiOrdinamento) criteri.getSelectedItem();
                Ordine ord =
                        (Ordine) ordini.getSelectedItem();

                // Preparo la lista da ordinare
                List<Libro> tutti = libreria.getTuttiLibri();
                // Eseguo l’ordinamento in place
                Ordinatore.sort(tutti, crit, ord);

                // Ripopolazione della listModel
                listModel.clear();
                for (Libro l : tutti) {
                    listModel.addElement(l);
                }
            }
        });
    }
}
