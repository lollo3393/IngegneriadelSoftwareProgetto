package Libreria.Modello.Command.SalvaEcarica;

import Libreria.Modello.Command.Command;
import Libreria.Modello.Libreria;
import Libreria.Modello.Libro;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CaricaDaJson implements Command {
    private final Libreria libreria;
    private final DefaultListModel<Libro> listModel;
    private final File file;
    private final List<Libro> precedenti;
    public CaricaDaJson(Libreria libreria,
                        DefaultListModel<Libro> listModel,
                        File file) {
        this.libreria  = libreria;
        this.listModel = listModel;
        this.file      = file;
        this.precedenti  = new ArrayList<Libro>(libreria.getTuttiLibri());
    }


    @Override
    public void esegui() {
        try {
            // carico i nuovi libri
            List<Libro> caricati = JsonManager.carica(file);


            // svuoto libreria e listModel
            for (Libro l : new ArrayList<Libro>(libreria.getTuttiLibri())) {
                libreria.rimuoviLibro(l.getISBN());
            }
            listModel.clear();

            // aggiungo i caricati
            for (Libro l : caricati) {
                libreria.aggiungiLibro(l);
                listModel.addElement(l);
            }
        } catch (IOException | IllegalArgumentException ex) {
            throw new RuntimeException("Errore in load JSON: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void undo() {
        for (Libro l : new ArrayList<Libro>(libreria.getTuttiLibri())) {
            libreria.rimuoviLibro(l.getISBN());
        }
        listModel.clear();
        // ricostruisco
        for (Libro l : precedenti) {
            libreria.aggiungiLibro(l);
            listModel.addElement(l);
        }
    }
    }

