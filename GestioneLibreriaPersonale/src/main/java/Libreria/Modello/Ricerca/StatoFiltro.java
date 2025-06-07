package Libreria.Modello.Ricerca;

import Libreria.Modello.Libro;
import Libreria.Modello.StatoDiLettura;

public class StatoFiltro implements LibroFiltro{
    private final StatoDiLettura stato;
    public StatoFiltro(StatoDiLettura stato){
        this.stato=stato;
    }
    @Override
    public boolean Uguale(Libro l) {
        return l.getStatoDiLettura()==stato;
    }
}
