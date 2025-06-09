package Libreria.Modello.Command.SalvaEcarica;

import Libreria.Modello.Command.Command;
import Libreria.Modello.Libro;

import java.io.File;
import java.io.IOException;
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
        try{
            JsonManager.salva(libriAttuali,file);

        }catch (IOException e){
            throw new RuntimeException("ERRORE IN SALVATAGGIO "+ e.getMessage());
        }


    }

    @Override
    public void undo() {
        if(file.exists()){
            boolean ok= file.delete();
            if(!ok){
                System.out.println("errore nessun file da cancellare");
            }
        }

    }
}
