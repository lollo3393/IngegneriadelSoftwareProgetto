package Libreria.Modello.Ricerca;

import Libreria.Modello.Libro;

public class TitoloFiltro implements LibroFiltro{
    private final String inserimento;

    public TitoloFiltro(String inserimento) {
        this.inserimento = inserimento.toLowerCase();
    }

    @Override
    public boolean Uguale(Libro l) {
        return l.getTitolo().toLowerCase().contains(inserimento);
    }
}
