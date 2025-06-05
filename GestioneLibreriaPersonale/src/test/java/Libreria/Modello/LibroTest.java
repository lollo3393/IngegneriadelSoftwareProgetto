package Libreria.Modello;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LibroTest {

    @Test
    public void creaLibroDaLeggereValido() {
        // Costruisco un libro con stato DA_LEGGERE e senza valutazione/recensione
        Libro l = new Libro.Builder()
                .setAutore("Italo Calvino")
                .setTitolo("Il barone rampante")
                .setISBN("1234567890")
                .setGenere(Genere.ROMANZO_DI_AVVENTURA)
                .setTipo(TipiDiOggetto.LIBRO)
                .setStatoDiLettura(StatoDiLettura.DA_LEGGERE)
                // non chiamo né setValutazione() né setRecensione()
                .build();

        assertEquals("Italo Calvino", l.getAutore());
        assertEquals("Il barone rampante", l.getTitolo());
        assertEquals("1234567890", l.getISBN());
        assertEquals(Genere.ROMANZO_DI_AVVENTURA, l.getGenere());
        assertEquals(TipiDiOggetto.LIBRO, l.getTipo());
        assertEquals(StatoDiLettura.DA_LEGGERE, l.getStatoDiLettura());
        assertEquals(0, l.getValutazione(), "Valutazione di default deve essere 0");
        assertNull(l.getRecensione(), "Recensione di default deve essere null");
    }


    @Test
    public void erroreSeValutazioneNonPermessaQuandoNonLetto() {
        // Provo a costruire un libro con stato DA_LEGGERE ma con valutazione per cui deve lanciare IllegalArgumentException
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new Libro.Builder()
                        .setAutore("Tite Kubo")
                        .setTitolo("Bleach Vol. 1")
                        .setISBN("6677889900")
                        .setGenere(Genere.SHONEN)
                        .setTipo(TipiDiOggetto.MANGA)
                        .setStatoDiLettura(StatoDiLettura.DA_LEGGERE)
                        .setValutazione(3)
                        .build()
        );
        assertTrue(ex.getMessage().toLowerCase().contains("valutazione"),
                "Il messaggio d'errore dovrebbe segnalare che non è permesso valutare se non è LETTO");
    }
    @Test
    public void nonPermetteValutazioneFuoriRangeSeLetto() {
        // un valore <1 o >5, quando stato == LETTO, deve fallire
        for (int voto : new int[]{0, 6, 100}) {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> new Libro.Builder()
                            .setAutore("Autore")
                            .setTitolo("Titolo")
                            .setISBN("1234567890")
                            .setGenere(Genere.ROMANZO_DI_AVVENTURA)
                            .setTipo(TipiDiOggetto.LIBRO)
                            .setStatoDiLettura(StatoDiLettura.LETTO)
                            .setValutazione(voto)
                            .setRecensione("Recensione valida")
                            .build()
            );
            assertTrue(ex.getMessage().toLowerCase().contains("valutazione"),
                    "Mi aspetto un errore che menzioni la valutazione");
        }
    }
    @Test
    public void nonPermetteRecensioneVuotaSeLetto() {
        // se stato == LETTO, recensione non può essere null o stringa vuota
        for (String rec : new String[]{null, "", "   "}) {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> new Libro.Builder()
                            .setAutore("Autore")
                            .setTitolo("Titolo")
                            .setISBN("1234567890")
                            .setGenere(Genere.ROMANZO_DI_AVVENTURA)
                            .setTipo(TipiDiOggetto.LIBRO)
                            .setStatoDiLettura(StatoDiLettura.LETTO)
                            .setValutazione(4)
                            .setRecensione(rec)
                            .build()
            );
            assertTrue(ex.getMessage().toLowerCase().contains("recensione"),
                    "Mi aspetto un errore che menzioni la recensione obbligatoria");
        }

    }
    @Test
    public void costruzioneValidaQuandoLettoConValutazioneERecensione() {
        // Testo “positivo”: se fornisco valutazione =3 e recensione valida, il build non lancia eccezione
        Libro l = new Libro.Builder()
                .setAutore("Italo Calvino")
                .setTitolo("Il barone rampante")
                .setISBN("0987654321")
                .setGenere(Genere.ROMANZO_DI_AVVENTURA)
                .setTipo(TipiDiOggetto.LIBRO)
                .setStatoDiLettura(StatoDiLettura.LETTO)
                .setValutazione(3)
                .setRecensione("Considerazioni genetali")
                .build();
        // Qui controllo che, una volta costruito, valga la regola:
        int val = l.getValutazione();
        assertTrue(val >= 1 && val <= 5, "La valutazione deve essere compresa tra 1 e 5");
        assertNotNull(l.getRecensione());
    }

    @Test
    public void costruzioneValidaQuandoNonLettoSenzaValutazioneRecensione() {
        // Se lo stato è non letto o da leggere, provo a creare un libro senza valutazion e recensione
        Libro l1 = new Libro.Builder()
                .setAutore("Italo Calvino")
                .setTitolo("Le città invisibili")
                .setISBN("1234567890")
                .setGenere(Genere.ROMANZO_DI_AVVENTURA)
                .setTipo(TipiDiOggetto.LIBRO)
                .setStatoDiLettura(StatoDiLettura.DA_LEGGERE)
                .build();

        assertEquals(0, l1.getValutazione());
        assertNull(l1.getRecensione());

        Libro l2 = new Libro.Builder()
                .setAutore("Italo Calvino")
                .setTitolo("Le città invisibili")
                .setISBN("1234567890")
                .setGenere(Genere.ROMANZO_DI_AVVENTURA)
                .setTipo(TipiDiOggetto.LIBRO)
                .setStatoDiLettura(StatoDiLettura.IN_LETTURA)
                .build();

        assertEquals(0, l2.getValutazione());
        assertNull(l2.getRecensione());
    }
}


