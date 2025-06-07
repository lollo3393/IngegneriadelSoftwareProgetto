package Libreria.Modello.Command;

import Libreria.Modello.Libreria;
import Libreria.Modello.Libro;

public class AggiungiLibroCommand implements Command{
    private final Libreria libreria;
    private final Libro libro;
    public AggiungiLibroCommand(Libreria libreria, Libro libro){
        this.libreria=libreria;
        this.libro=libro;
    }
    @Override
    public void esegui() {
        libreria.aggiungiLibro(libro);
    }

    @Override
    public void undo() {
            libreria.rimuoviLibro(libro.getISBN());
    }
}
