package gui.command;

import circuit.Circuit;
import components.Component;


public class DeleteComponentCommand implements Command {

    private final Circuit circuit;
    private final Component component;


    public DeleteComponentCommand(
            Circuit circuit,
            Component component){

        this.circuit = circuit;
        this.component = component;

    }


    @Override
    public void execute(){

        circuit.getComponents()
                .remove(component);

    }


    @Override
    public void undo(){

        circuit.addComponent(component);

    }
}