package Libreria.Modello;
import Libreria.Modello.Command.AggiungiLibroCommand;
import Libreria.Modello.Command.CommandManager;
import Libreria.Modello.Command.ModficaLibroCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommandLibroTest {
    private Libreria libreria;
    private CommandManager manager;

    @BeforeEach
    void setUp() {
        libreria = Libreria.getInstance();
        manager = new CommandManager();
        // svuoto la libreria
        while (!libreria.getTuttiLibri().isEmpty()) {
            String isbn = libreria.getTuttiLibri().get(0).getISBN();
            libreria.rimuoviLibro(isbn);
        }
    }

    @Test
    void modificaTitolo_EFunziona() {
        // aggiungo un libro di partenza
        Libro originale = new Libro.Builder()
                .setAutore("Dante")
                .setTitolo("Inferno")
                .setISBN("I001")
                .setGenere(Genere.SAGGIO)
                .setTipo(TipiDiOggetto.LIBRO)
                .setStatoDiLettura(StatoDiLettura.DA_LEGGERE)
                .build();
        manager.eseguiComando(new AggiungiLibroCommand(libreria, originale));

        // preparo la versione modificata (solo titolo)
        Libro modificato = new Libro.Builder()
                .setAutore("Dante")
                .setTitolo("Paradiso")
                .setISBN("I001")
                .setGenere(Genere.SAGGIO)
                .setTipo(TipiDiOggetto.LIBRO)
                .setStatoDiLettura(StatoDiLettura.DA_LEGGERE)
                .build();

        ModficaLibroCommand cmd = new ModficaLibroCommand(libreria, modificato);
        manager.eseguiComando(cmd);

        List<Libro> list = libreria.getTuttiLibri();
        assertEquals(1, list.size());
        assertEquals("Paradiso", list.get(0).getTitolo());
    }

    @Test
    void modificaNonEsistente_LanciaIllegalArgument() {
        // senza aggiungere nulla
        Libro fittizio = new Libro.Builder()
                .setAutore("X")
                .setTitolo("Y")
                .setISBN("NOTFOUND")
                .setGenere(Genere.HORROR)
                .setTipo(TipiDiOggetto.LIBRO)
                .setStatoDiLettura(StatoDiLettura.DA_LEGGERE)
                .build();

        try {
            ModficaLibroCommand cmd = new ModficaLibroCommand(libreria, fittizio);
            manager.eseguiComando(cmd);
            fail("Mi aspettavo IllegalArgumentException perché il libro non esiste");
        } catch (IllegalArgumentException e) {
            // tutto ok: è stata sollevata l’eccezione attesa
        }
    }

    @Test
    void modificaStatoDaLettoADaLeggere_AzzeraValutazioneERecensione() {
        // libro già letto
        Libro letto = new Libro.Builder()
                .setAutore("Austen")
                .setTitolo("Orgoglio")
                .setISBN("A001")
                .setGenere(Genere.ROMANZO_DI_AVVENTURA)
                .setTipo(TipiDiOggetto.LIBRO)
                .setStatoDiLettura(StatoDiLettura.LETTO)
                .setValutazione(5)
                .setRecensione("Molto bello")
                .build();
        manager.eseguiComando(new AggiungiLibroCommand(libreria, letto));

        // modifico a DA_LEGGERE
        Libro daLeggere = new Libro.Builder()
                .setAutore("Austen")
                .setTitolo("Orgoglio")
                .setISBN("A001")
                .setGenere(Genere.ROMANZO_DI_AVVENTURA)
                .setTipo(TipiDiOggetto.LIBRO)
                .setStatoDiLettura(StatoDiLettura.DA_LEGGERE)
                .build();
        manager.eseguiComando(new ModficaLibroCommand(libreria, daLeggere));

        Libro risultato = libreria.getTuttiLibri().get(0);
        assertEquals(StatoDiLettura.DA_LEGGERE, risultato.getStatoDiLettura());
        assertEquals(0, risultato.getValutazione());
        assertNull(risultato.getRecensione());
    }

    @Test
    void undoRipristinaVecchiValori() {
        // 1) aggiungo in IN_LETTURA
        Libro primo = new Libro.Builder()
                .setAutore("Wells")
                .setTitolo("Guerra")
                .setISBN("W001")
                .setGenere(Genere.FANTASCIENTIFICO)
                .setTipo(TipiDiOggetto.LIBRO)
                .setStatoDiLettura(StatoDiLettura.IN_LETTURA)
                .build();
        manager.eseguiComando(new AggiungiLibroCommand(libreria, primo));

        // 2) modifico in LETTO con valutazione
        Libro secondo = new Libro.Builder()
                .setAutore("Wells")
                .setTitolo("Guerra")
                .setISBN("W001")
                .setGenere(Genere.FANTASCIENTIFICO)
                .setTipo(TipiDiOggetto.LIBRO)
                .setStatoDiLettura(StatoDiLettura.LETTO)
                .setValutazione(4)
                .setRecensione("Avvincente")
                .build();
        ModficaLibroCommand cmd = new ModficaLibroCommand(libreria, secondo);
        manager.eseguiComando(cmd);

        // verifico stato modificato
        Libro mod = libreria.getTuttiLibri().get(0);
        assertEquals(StatoDiLettura.LETTO, mod.getStatoDiLettura());
        assertEquals(4, mod.getValutazione());

        // 3) chiamo undo, deve tornare a IN_LETTURA e valutazione 0
        manager.undo();
        Libro undo = libreria.getTuttiLibri().get(0);
        assertEquals(StatoDiLettura.IN_LETTURA, undo.getStatoDiLettura());
        assertEquals(0, undo.getValutazione());
        assertNull(undo.getRecensione());
    }
}
