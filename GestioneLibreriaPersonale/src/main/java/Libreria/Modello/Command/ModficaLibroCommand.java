package Libreria.Modello.Command;

import Libreria.Modello.Libreria;
import Libreria.Modello.Libro;

public class ModficaLibroCommand implements Command{
    private final Libreria libreria;
    private final Libro nuovoLibro;
    private Libro vecchioLibro;

    public ModficaLibroCommand(Libreria libreria, Libro nuovoLibro){
        this.libreria=libreria;
        this.nuovoLibro=nuovoLibro;
    }


    @Override
    public void esegui() {
        String isbn= nuovoLibro.getISBN();
        vecchioLibro= libreria.trovaPerIsbn(isbn);
        libreria.aggiornaLibro(nuovoLibro);
    }

    @Override
    public void undo() {
        if(vecchioLibro==null){
            throw new IllegalStateException("Impossibile annullare l'operazione, nessun libro modificato prima d'ora");
        }
        libreria.aggiornaLibro(vecchioLibro);

    }
}
