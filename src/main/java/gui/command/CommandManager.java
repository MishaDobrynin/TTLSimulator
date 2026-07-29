package gui.command;

import java.util.Stack;

public class CommandManager {

    private final Stack<Command> undoStack;
    private final Stack<Command> redoStack;


    public CommandManager(){

        undoStack = new Stack<>();
        redoStack = new Stack<>();

    }


    public void execute(Command command){

        command.execute();

        undoStack.push(command);

        redoStack.clear();

    }


    public void undo(){

        if(undoStack.isEmpty()){
            return;
        }

        Command command = undoStack.pop();

        command.undo();

        redoStack.push(command);

    }


    public void redo(){

        if(redoStack.isEmpty()){
            return;
        }

        Command command = redoStack.pop();

        command.execute();

        undoStack.push(command);

    }
}