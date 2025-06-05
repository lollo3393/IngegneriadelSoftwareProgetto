package Libreria.Modello;
//creo un enum per tenere traccia degli stati di lettura in quanto così posso differenziare lo stato di lettura di un libro.

public enum StatoDiLettura {

    DA_LEGGERE, //Non ancora iniziato
    IN_LETTURA, //inziato ma non finito
    LETTO //finito
}
