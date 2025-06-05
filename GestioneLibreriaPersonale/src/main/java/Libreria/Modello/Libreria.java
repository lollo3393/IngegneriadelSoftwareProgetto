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
}
