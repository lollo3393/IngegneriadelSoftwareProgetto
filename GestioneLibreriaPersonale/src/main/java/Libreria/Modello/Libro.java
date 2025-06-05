package Libreria.Modello;

public class Libro {
    private String Autore;
    private String titolo;
    private String ISBN;
    private Genere genere;
    private TipiDiOggetto tipo;
    private int Valutazione; //da 1 a 5 solo se letto
    private StatoDiLettura statoDiLettura;
    private String Recensione; // funziona solo se il libro è LETTO

    public Libro(String autore, String titolo, String ISBN, Genere genere, TipiDiOggetto tipo, int valutazione, StatoDiLettura statoDiLettura, String recensione) {
        Autore = autore;
        this.titolo = titolo;
        this.ISBN = ISBN;
        this.genere = genere;
        this.tipo = tipo;
        this.statoDiLettura = statoDiLettura;
        if(statoDiLettura!= StatoDiLettura.LETTO){
            this.Valutazione=0;
            this.Recensione= null;
        }
    }

    public String getAutore() {
        return Autore;
    }

    public void setAutore(String autore) {
        Autore = autore;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public Genere getGenere() {
        return genere;
    }

    public void setGenere(Genere genere) {
        this.genere = genere;
    }

    public TipiDiOggetto getTipo() {
        return tipo;
    }

    public void setTipo(TipiDiOggetto tipo) {
        this.tipo = tipo;
    }

    public int getValutazione() {
        return Valutazione;
    }

    public void setValutazione(int valutazione) {
        if(this.statoDiLettura!=StatoDiLettura.LETTO){
            throw new IllegalStateException("Non si può impostare una valutazione se non si è finito di leggere");
        }
        if(valutazione>5 ||valutazione<1){
            throw new IllegalArgumentException("La valutazione deve essere compresa tra 1 e 5");
        }
        this.Valutazione=valutazione;
    }

    public StatoDiLettura getStatoDiLettura() {
        return statoDiLettura;
    }

    public void setStatoDiLettura(StatoDiLettura nuovoStato) {
        this.statoDiLettura = nuovoStato;
        if(nuovoStato!=StatoDiLettura.LETTO){
            this.Valutazione=0;
            this.Recensione=null;
        }
    }

    public String getRecensione() {
        return Recensione;
    }

    public void setRecensione(String recensione) {
        if(this.statoDiLettura!=StatoDiLettura.LETTO){
            throw new IllegalStateException("Non puoi recensire un libro che non hai letto");
        }
        this.Recensione=recensione;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Libro{");
        sb.append("Autore='").append(Autore).append('\'');
        sb.append(", titolo='").append(titolo).append('\'');
        sb.append(", ISBN='").append(ISBN).append('\'');
        sb.append(", genere=").append(genere);
        sb.append(", tipo=").append(tipo);
        sb.append(", statoDiLettura=").append(statoDiLettura);
        if(statoDiLettura==StatoDiLettura.LETTO){
            sb.append(", Valutazione=").append(Valutazione).append(" Stelle");
            sb.append(", Recensione='").append(Recensione).append('\'');
            sb.append('}');
        }
        sb.append("");

        return sb.toString();
    }
}
