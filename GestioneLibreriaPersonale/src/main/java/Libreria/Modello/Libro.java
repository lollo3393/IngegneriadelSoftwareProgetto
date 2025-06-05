package Libreria.Modello;
//interfaccia libro iniziale potrebbe ancora variare
public interface Libro {
    String getTitolo();
    void setTitolo(String autore);

    String getISBN();
    void setISBN(String ISBN);
    String getAutore();
    void setAutore(String autore);
    String getGenere();
    void setGenere(String genere);
    int getValutazione();
    void setValutazione(int valutazione);

    String getStatoDiLettura();
    void setStatoDiLettura(String statoDiLettura);
    String getRecensione();
    void setRecensione(String recensione);



}
