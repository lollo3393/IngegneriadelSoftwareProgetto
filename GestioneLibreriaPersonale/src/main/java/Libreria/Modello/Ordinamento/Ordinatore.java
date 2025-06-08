package Libreria.Modello.Ordinamento;

import Libreria.Modello.Libro;
import Libreria.Modello.TipiDiOggetto;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ordinatore {
    public static void sort(List<Libro> libri,CriteriDiOrdinamento criterio, Ordine ordine){
        Comparator<Libro> comparatore= null;
        if(criterio==CriteriDiOrdinamento.TIPO){
            comparatore= new Comparator<Libro>() {
                @Override
                public int compare(Libro o1, Libro o2) {
                    TipiDiOggetto t1=o1.getTipo();
                    TipiDiOggetto t2=o2.getTipo();
                    return t1.ordinal()-t2.ordinal();
                }
            };
            
        } else if (criterio==CriteriDiOrdinamento.VALUTAZIONE) {
            comparatore= new Comparator<Libro>() {
                @Override
                public int compare(Libro o1, Libro o2) {
                    return o1.getValutazione()-o2.getValutazione();
                }
            };


            
        }
        else if(criterio==CriteriDiOrdinamento.STATO){
            comparatore= new Comparator<Libro>() {
                @Override
                public int compare(Libro o1, Libro o2) {
                    return o1.getStatoDiLettura().ordinal()-o2.getStatoDiLettura().ordinal();

                }
            };
        }
        else {
            throw new IllegalArgumentException("Criterio non riconosciuto "+ criterio);
        }
        Collections.sort(libri,comparatore);
        if(ordine==Ordine.DECRESCENTE){
            Collections.reverse(libri);
        }
    }
}
