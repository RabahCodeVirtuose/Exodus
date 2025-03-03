package model;

import java.util.Stack;

public class CommandManager {
    private Stack<Command> history = new Stack<>();

    public void executeCommand(Command command) {
        command.execute();
        history.push(command);
    }

    public void undoLastCommand() {
        if (!history.isEmpty()) {
            history.pop(); // Pas de "undo" ici, mais on peut l'implémenter si nécessaire.
        }
    }
}
