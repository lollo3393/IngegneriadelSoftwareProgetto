package Libreria.Modello.Command;

import Libreria.Modello.Libreria;
import Libreria.Modello.Libro;

public class RimuoviLibroCommand implements Command{
    private final Libreria libreria;
    private final String ISBN;
    private Libro libroRimosso;

    public RimuoviLibroCommand(Libreria libreria, String isbn){
        this.libreria=libreria;
        this.ISBN=isbn;
    }

    @Override
    public void esegui() {
            libroRimosso= libreria.trovaPerIsbn(ISBN);
           libreria.rimuoviLibro(ISBN);
    }

    @Override
    public void undo() {
        if(libroRimosso==null){
            throw new IllegalStateException("Prima di fare la UNDO devi eseguire un comando");
        }
        libreria.aggiungiLibro(libroRimosso);
    }
}
