package Libreria.Modello.Ricerca;

import Libreria.Modello.Genere;
import Libreria.Modello.Libro;

public class GenereFiltro implements LibroFiltro{
    private Genere genere;
    public GenereFiltro(Genere genere){
        this.genere=genere;
    }
    @Override
    public boolean Uguale(Libro l) {
        return l.getGenere() == genere;
    }
}
