package Libreria.Modello;

import java.util.Stack;

public class CommandManager {
    private final Stack<Command> storia=new Stack<>();

    public void eseguiComando(Command cmd){
        cmd.esegui();
        storia.push(cmd);
    }

    public void undo(){
        if(storia.isEmpty()){
            throw new IllegalStateException("Nessun comando da annullare");
        }
        Command ultimo=storia.pop();
        ultimo.undo();
    }
}
