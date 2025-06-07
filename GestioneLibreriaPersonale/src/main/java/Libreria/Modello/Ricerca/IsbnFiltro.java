package Libreria.Modello.Ricerca;

import Libreria.Modello.Libro;

public class IsbnFiltro implements LibroFiltro{
    private final String isbn;

    public IsbnFiltro(String isbn){
        this.isbn=isbn.trim();
    }
    @Override
    public boolean Uguale(Libro l) {
        return l.getISBN().equals(isbn);
    }
}
