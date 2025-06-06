package Libreria.Modello;

import java.util.ArrayList;
import java.util.List;

public class Libreria {
    private final List<Libro> libri;
    private static Libreria instance;
    private Libreria(){
        this.libri=new ArrayList<>();
    }
    public static Libreria getInstance(){
        if(instance==null){
            instance=new Libreria();
        }
        return instance;
    }
    public List<Libro> getTuttiLibri(){
            return libri;
    }
    public void aggiungiLibro(Libro nuovoLibro){
        if(!controlloAggiunta(nuovoLibro)){
            throw new IllegalArgumentException("Non puoi aggiungere un libro con ISBN =" +nuovoLibro.getISBN());
        }
        libri.add(nuovoLibro);
    }
    private boolean controlloAggiunta(Libro l){
        for(Libro li: libri){
            if( li.getISBN().equals(l.getISBN()))
                return false;
        }
        return true;
    }
    public void rimuoviLibro(String isbn) {
        boolean trovato = false;
        for (Libro l : libri) {
            if (l.getISBN().equals(isbn)) {
                libri.remove(l);
                trovato = true;
                break;
            }
        }
        if (!trovato) {
            throw new IllegalArgumentException("Il tuo ISBN è inesistente nella libreria");
        }
    }
    public Libro trovaPerIsbn(String isbn) {
        for (Libro l : libri) {
            if (l.getISBN().equals(isbn)) {
                return l;
            }
        }
        throw new IllegalArgumentException("Nessun libro trovato con ISBN = " + isbn);
    }
    public void aggiornaLibro(Libro libroAggiornato) {
        String isbn = libroAggiornato.getISBN();
        boolean trovato = false;

        for (int i = 0; i < libri.size(); i++) {
            if (libri.get(i).getISBN().equals(isbn)) {
                // Sostituisco la vecchia versione con la nuova
                libri.set(i, libroAggiornato);
                trovato = true;
                break;
            }
        }

        if (!trovato) {
            throw new IllegalArgumentException("Nessun libro con ISBN = " + isbn + " da aggiornare.");
        }
    }
}
