package Libreria.Modello;

public class DemoCommand {
    public static void main (String[] args){
        Libreria libreria= Libreria.getInstance();
        CommandManager manager= new CommandManager();
        Libro libro = new Libro.Builder()
                .setAutore("Italo Calvino")
                .setTitolo("Il barone rampante")
                .setISBN("1111111111")
                .setGenere(Genere.ROMANZO_DI_AVVENTURA)
                .setTipo(TipiDiOggetto.LIBRO)
                .setStatoDiLettura(StatoDiLettura.DA_LEGGERE)
                .build();
        Command aggiungiCmd =new AggiungiLibroCommand(libreria,libro);
        System.out.println("prima aggiunta: "+ libreria.getTuttiLibri());
        manager.eseguiComando(aggiungiCmd);
        System.out.println("Dopo l'aggiunta "+ libreria.getTuttiLibri());
        Command rimuoviCmd= new RimuoviLibroCommand(libreria,"1111111111");
        manager.eseguiComando(rimuoviCmd);
        System.out.println("Dopo la rimozione "+ libreria.getTuttiLibri());
        manager.undo();
        System.out.println("Annullo l'ultima operazione ovvero remove: "+ libreria.getTuttiLibri()
        );
        manager.undo();
        System.out.println("Annullo un altra operazione ovvero l'aggiunta "+ libreria.getTuttiLibri());
    }
}
