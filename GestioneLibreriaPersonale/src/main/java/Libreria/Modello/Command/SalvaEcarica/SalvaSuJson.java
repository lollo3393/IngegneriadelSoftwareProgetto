package Libreria.Modello.Command.SalvaEcarica;

import Libreria.Modello.Command.Command;
import Libreria.Modello.Libro;

import java.io.File;
import java.util.List;

public class SalvaSuJson implements Command {

    private final List<Libro> libriAttuali;
    private final File file;

    public SalvaSuJson(List<Libro> libriAttuali,File file){
        this.libriAttuali=libriAttuali;
        this.file=file;
    }


    @Override
    public void esegui() {


    }

    @Override
    public void undo() {

    }
}
