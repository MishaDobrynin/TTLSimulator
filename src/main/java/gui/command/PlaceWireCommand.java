package gui.command;

import circuit.Circuit;
import circuit.Wire;

public class PlaceWireCommand implements Command {

    private final Circuit circuit;
    private final Wire wire;

    public PlaceWireCommand(Circuit circuit, Wire wire){
        this.circuit = circuit;
        this.wire = wire;
    }

    @Override
    public void execute(){
        circuit.addWire(wire);
    }

    @Override
    public void undo(){
        circuit.getWires().remove(wire);
    }
}