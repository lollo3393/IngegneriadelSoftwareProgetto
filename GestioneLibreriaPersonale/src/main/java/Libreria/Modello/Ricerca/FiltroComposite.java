package Libreria.Modello.Ricerca;

import Libreria.Modello.Libro;

import java.util.List;

public class FiltroComposite implements LibroFiltro{
    private final List<LibroFiltro> filtri;
    public FiltroComposite(List<LibroFiltro> filtri){
        this.filtri=filtri;
    }

    @Override
    public boolean Uguale(Libro l) {
        for(LibroFiltro f: filtri){
            if(!f.Uguale(l))
                return false;

        }
        return true;
    }
}
