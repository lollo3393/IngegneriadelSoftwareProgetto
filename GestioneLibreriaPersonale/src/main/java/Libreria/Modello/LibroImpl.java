package Libreria.Modello;

public class LibroImpl implements Libro{
    private String Titolo;
    private String Autore;
    private String ISBN;
    private String Genere;
    private int Valutazione;
    private String StatoDiLettura;
    private String Recensione;


    public LibroImpl
    @Override
    public String getTitolo() {
        return Titolo;
    }

    @Override
    public void setTitolo(String titolo) {
        Titolo = titolo;
    }

    @Override
    public String getAutore() {
        return Autore;
    }

    @Override
    public void setAutore(String autore) {
        Autore = autore;
    }

    @Override
    public String getISBN() {
        return ISBN;
    }

    @Override
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    @Override
    public String getGenere() {
        return Genere;
    }

    @Override
    public void setGenere(String genere) {
        Genere = genere;
    }

    @Override
    public int getValutazione() {
        return Valutazione;
    }

    @Override
    public void setValutazione(int valutazione) {
        Valutazione = valutazione;
    }

    @Override
    public String getStatoDiLettura() {
        return StatoDiLettura;
    }

    @Override
    public void setStatoDiLettura(String statoDiLettura) {
        StatoDiLettura = statoDiLettura;
    }

    @Override
    public String getRecensione() {
        return Recensione;
    }

    @Override
    public void setRecensione(String recensione) {
        Recensione = recensione;
    }

}
