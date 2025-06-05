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

// questo è un costruttore privato che può essere usato solo dal builder con builder.build
    private Libro(Builder b) {
        this.Autore = b.Autore;
        this.titolo = b.titolo;
        this.ISBN = b.ISBN;
        this.genere = b.genere;
        this.tipo = b.tipo;
        this.statoDiLettura = b.statoDiLettura;
        this.Valutazione = b.Valutazione;
        this.Recensione = b.Recensione;
    }
    public String getAutore() {
        return Autore;
    }



    public String getTitolo() {
        return titolo;
    }



    public String getISBN() {
        return ISBN;
    }


    public Genere getGenere() {
        return genere;
    }


    public TipiDiOggetto getTipo() {
        return tipo;
    }


    public int getValutazione() {
        return Valutazione;
    }


    public StatoDiLettura getStatoDiLettura() {
        return statoDiLettura;
    }


    public String getRecensione() {
        return Recensione;
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
    public static class Builder{
        private String Autore;
        private String titolo;
        private String ISBN;
        private Genere genere;
        private TipiDiOggetto tipo;
        private StatoDiLettura statoDiLettura;
        private int Valutazione = 0;       // default 0
        private String Recensione = null; //null di default

        //imposta l'autore:
        public Builder setAutore(String autore){
            this.Autore=autore;
            return this;
        }
        public Builder setTitolo(String titolo){
            this.titolo=titolo;
            return this;
        }
        public Builder setISBN(String ISBN){
            this.ISBN=ISBN;
            return this;
        }
        public Builder setGenere(Genere genere){
            this.genere=genere;
            return this;
        }
        public Builder setTipo(TipiDiOggetto tipo) {
            this.tipo = tipo;
            return this;

        }
        public Builder setStatoDiLettura(StatoDiLettura stato){
            this.statoDiLettura=stato;
            return this;
        }
        public Builder setValutazione(int v){
            this.Valutazione=v;
            return this;
        }
        public Builder setRecensione(String r){
            this.Recensione=r;
            return this;

        }
        public Libro build(){
            if(Autore==null||Autore.trim().isEmpty()){
                throw new IllegalArgumentException("Il campo Autore non può essere vuoto");
            }
            if (titolo == null || titolo.trim().isEmpty()) {
                throw new IllegalArgumentException("Il campo Titolo non può essere vuoto.");
            }
            if (ISBN == null || ISBN.trim().isEmpty()) {
                throw new IllegalArgumentException("Il campo ISBN non può essere vuoto.");
            }
            if (genere == null) {
                throw new IllegalArgumentException("Il campo 'Genere' è obbligatorio.");
            }
            if (tipo == null) {
                throw new IllegalArgumentException("Il tipo è obbligatorio");
            }
            if (statoDiLettura == null) {
                throw new IllegalArgumentException("Il campo stato di lettura non può essere vuoto");
            }
            if (statoDiLettura != StatoDiLettura.LETTO) {
                if(Valutazione!=0){
                    throw new IllegalArgumentException("non puoi assegnare una valutazione se non hai finito di leggere");
                }
                if (Recensione != null && !Recensione.trim().isEmpty()) {
                    throw new IllegalArgumentException("Non puoi inserire una recensione se non hai finito di leggere");
                }
                this.Valutazione=0;
                this.Recensione=null;


            }
            if(statoDiLettura==StatoDiLettura.LETTO){
                if(Valutazione<1 ||Valutazione>5){
                    throw new IllegalArgumentException("La valutazione deve essere compresa tra 1 e 5");
                }
                if(Recensione==null || Recensione.trim().isEmpty()){
                    throw  new IllegalArgumentException("La recensione deve essere scritta se hai letto il libro");
                }
            }
            if (statoDiLettura == StatoDiLettura.LETTO) {
                if (Valutazione < 1 || Valutazione > 5) {
                    throw new IllegalArgumentException(
                            "La valutazione deve essere compresa tra uno e 5."
                    );
                }
                if (Recensione == null || Recensione.trim().isEmpty()) {
                    throw new IllegalArgumentException(
                            "Il libro va recensito una volta letto ."
                    );
                }
            }
            this.Autore=Autore.trim();
            this.titolo=titolo.trim();
            return new Libro(this);

        }
    }
}
