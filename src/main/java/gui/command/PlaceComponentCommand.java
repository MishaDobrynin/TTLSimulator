package gui.command;

import circuit.Circuit;
import components.Component;

public class PlaceComponentCommand implements Command {

    private final Circuit circuit;
    private final Component component;


    public PlaceComponentCommand(
            Circuit circuit,
            Component component){

        this.circuit = circuit;
        this.component = component;
    }


    @Override
    public void execute(){

        circuit.addComponent(component);

    }


    @Override
    public void undo(){

        circuit.getComponents()
                .remove(component);

    }
}