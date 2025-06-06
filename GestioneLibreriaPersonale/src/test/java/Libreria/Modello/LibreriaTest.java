package Libreria.Modello;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class LibreriaTest {
    private Libreria libreria;

    @BeforeEach
    void setUp() {
        // Ottengo l’istanza singleton
        libreria = Libreria.getInstance();

        // Creo una copia della lista interna, così posso rimuovere
        // dalla libreria senza incorrere in ConcurrentModificationException
        List<Libro> copia = new ArrayList<>(libreria.getTuttiLibri());
        for (Libro l : copia) {
            libreria.rimuoviLibro(l.getISBN());
        }
    }

    @Test
    void aggiungiLibro() {
        Libro libro = new Libro.Builder()
                .setAutore("Italo Calvino")
                .setTitolo("Il barone rampante")
                .setISBN("1111111111")
                .setGenere(Genere.ROMANZO_DI_AVVENTURA)
                .setTipo(TipiDiOggetto.LIBRO)
                .setStatoDiLettura(StatoDiLettura.DA_LEGGERE)
                .build();

        assertTrue(libreria.getTuttiLibri().isEmpty(),
                "La libreria dovrebbe essere vuota all'inizio");
        libreria.aggiungiLibro(libro);
        assertEquals(1, libreria.getTuttiLibri().size(),
                "Dopo aver aggiunto un libro, la dimensione deve essere 1");
    }

    @Test
    void AggiungoLibroDueVolte() {
        Libro libro = new Libro.Builder()
                .setAutore("Italo Calvino")
                .setTitolo("Il barone rampante")
                .setISBN("1111111111")
                .setGenere(Genere.ROMANZO_DI_AVVENTURA)
                .setTipo(TipiDiOggetto.LIBRO)
                .setStatoDiLettura(StatoDiLettura.DA_LEGGERE)
                .build();

        assertTrue(libreria.getTuttiLibri().isEmpty(),
                "La libreria deve essere vuota all'inizio");
        libreria.aggiungiLibro(libro);
        assertEquals(1, libreria.getTuttiLibri().size());

        try {
            libreria.aggiungiLibro(libro);
            fail("Mi aspettavo IllegalArgumentException perché l’ISBN è duplicato");
        } catch (IllegalArgumentException e) {

        }
    }
    @Test
    void RimuovoLibroInesistente(){
        try{
            libreria.rimuoviLibro("ciao");
            fail("Mi aspetto un illegalAgrument exception perchè non esiste questo isbn");
        }catch (IllegalArgumentException e){}
    }


}
