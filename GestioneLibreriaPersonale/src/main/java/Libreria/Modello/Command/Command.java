package Libreria.Modello.Command;

public interface Command{
    //definisce l'azione che può essere eseguita (se rispetta le condizioni)
    void esegui();

    //annulla l'operazione appena fatta
    void undo();
}
