package Libreria.Modello.Ricerca;

import Libreria.Modello.Libro;

public class AutoreFiltro implements LibroFiltro{
    private final String Inserimento;
    public AutoreFiltro(String inserimento){
        this.Inserimento=inserimento.toLowerCase();
    }
    @Override
    public boolean Uguale(Libro l) {
        return l.getAutore().toLowerCase().contains(Inserimento);
    }
}
