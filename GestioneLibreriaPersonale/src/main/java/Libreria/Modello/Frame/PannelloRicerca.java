package Libreria.Modello.Frame;

import Libreria.Modello.Genere;
import Libreria.Modello.Libreria;
import Libreria.Modello.Libro;
import Libreria.Modello.Ricerca.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PannelloRicerca extends JPanel {
    private final JTextField CampoAutore= new JTextField(10);
    private final JTextField CampoTitolo= new JTextField(10);
    private final JTextField CampoIsbn = new JTextField(10);
    private final JButton reset= new JButton("Ripristina");

    private final JComboBox<Genere> generi= new JComboBox<>(Genere.values());

    private final JButton cerca= new JButton("CERCA");

    public PannelloRicerca(final Libreria libreria, final DefaultListModel<Libro> listModel){
        super(new GridBagLayout());
        GridBagConstraints gb= new GridBagConstraints();
        gb.insets = new Insets(2,2,2,2);
        gb.fill= GridBagConstraints.HORIZONTAL;

        //prima riga
        gb.gridx=0;
        gb.gridy=0;
        add(new Label("Autore: "),gb);
        gb.gridx=1;
        add(CampoAutore,gb);
        gb.gridx=2;
        add(new JLabel("Titolo: "),gb);
        gb.gridx=3;
        add(CampoTitolo,gb);

        //seconda riga
        gb.gridx=0;
        gb.gridy=1;
        add(new Label("ISBN: "),gb);
        gb.gridx=1;
        add(CampoIsbn,gb);
        gb.gridx=2;
        add(new JLabel("Genere: "),gb);
        gb.gridx=3;
        add(generi,gb);

        //riga 3
     
        gb.gridy=2;


        gb.gridx=2;
        add(cerca,gb);

        gb.gridx=3;
        add(reset,gb);


        cerca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String aut= CampoAutore.getText().trim();
                String tit= CampoTitolo.getText().trim();
                String isbn= CampoIsbn.getText().trim();
                Genere gen = (Genere) generi.getSelectedItem();


                ArrayList<LibroFiltro> filtri= new ArrayList<>();
                if(!aut.isEmpty())
                    filtri.add(new AutoreFiltro(aut));
                if(!tit.isEmpty())
                    filtri.add(new TitoloFiltro(tit));
                if(!isbn.isEmpty())
                    filtri.add(new IsbnFiltro(isbn));
                if(gen!= null)
                    filtri.add(new GenereFiltro(gen));
                FiltroComposite comp= new FiltroComposite(filtri);

                listModel.clear();
                for(Libro l : libreria.getTuttiLibri()){
                    if(comp.Uguale(l)){
                        listModel.addElement(l);
                    }
                }

            }
        });
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CampoAutore.setText("");
                CampoIsbn.setText("");
                CampoIsbn.setText("");
                generi.getSelectedIndex();
                listModel.clear();
                for(Libro l:libreria.getTuttiLibri()){
                    listModel.addElement(l);
                }

            }
        });




    }
}
