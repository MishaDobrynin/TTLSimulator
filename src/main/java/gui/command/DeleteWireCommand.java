package gui.command;

import circuit.Circuit;
import circuit.Net;
import circuit.Pin;
import circuit.Wire;

import java.util.ArrayList;
import java.util.List;

public class DeleteWireCommand implements Command {

    private final Circuit circuit;
    private final Wire wire;

    private Net originalNet;
    private List<Pin> originalPins;
    private List<Wire> originalWires;

    private final List<Net> createdNets;

    public DeleteWireCommand(Circuit circuit, Wire wire){
        this.circuit = circuit;
        this.wire = wire;
        this.createdNets = new ArrayList<>();
    }

    @Override
    public void execute(){
        originalNet = findNet();

        if(originalNet == null){
            return;
        }

        originalPins = new ArrayList<>(originalNet.getPins());
        originalWires = new ArrayList<>(originalNet.getWires());

        circuit.getWires().remove(wire);

        originalNet.removeWire(wire);

        circuit.removeNet(originalNet);

        createdNets.clear();

        for(List<Pin> group : originalNet.getConnectedGroups()){

            Net newNet = new Net();

            for(Pin pin : group){
                newNet.addPin(pin);
            }

            for(Wire currentWire : originalNet.getWires()){
                if(group.contains(currentWire.getStart())
                        && group.contains(currentWire.getEnd())){

                    newNet.addWire(currentWire);
                }
            }

            circuit.addNet(newNet);
            createdNets.add(newNet);
        }
    }

    @Override
    public void undo(){
        circuit.getWires().add(wire);

        for(Net net : createdNets){
            circuit.removeNet(net);
        }

        originalNet.getPins().clear();
        originalNet.getPins().addAll(originalPins);

        originalNet.getWires().clear();
        originalNet.getWires().addAll(originalWires);

        circuit.addNet(originalNet);
    }

    private Net findNet(){
        for(Net net : circuit.getNets()){
            if(net.containsWire(wire)){
                return net;
            }
        }

        return null;
    }
}