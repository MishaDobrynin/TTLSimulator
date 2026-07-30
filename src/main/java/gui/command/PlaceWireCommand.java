package gui.command;

import circuit.Circuit;
import circuit.Net;
import circuit.Pin;
import circuit.Wire;

import java.util.ArrayList;
import java.util.List;

public class PlaceWireCommand implements Command {

    private final Circuit circuit;
    private final Wire wire;

    private Net createdNet;

    private Net mergedNet;
    private Net mergedIntoNet;
    private List<Pin> originalPins;
    private List<Wire> originalWires;

    public PlaceWireCommand(Circuit circuit, Wire wire){
        this.circuit = circuit;
        this.wire = wire;
    }

    @Override
    public void execute(){
        circuit.addWire(wire);

        Pin start = wire.getStart();
        Pin end = wire.getEnd();

        Net startNet = circuit.getNet(start);
        Net endNet = circuit.getNet(end);

        if(startNet == null && endNet == null){
            createdNet = new Net();

            createdNet.addPin(start);
            createdNet.addPin(end);
            createdNet.addWire(wire);

            circuit.addNet(createdNet);
        }
        else if(startNet != null && endNet == null){
            startNet.addPin(end);
            startNet.addWire(wire);
        }
        else if(startNet == null){
            endNet.addPin(start);
            endNet.addWire(wire);
        }
        else if(startNet != endNet){
            mergedIntoNet = startNet;
            mergedNet = endNet;

            originalPins = new ArrayList<>(startNet.getPins());
            originalWires = new ArrayList<>(startNet.getWires());

            startNet.merge(endNet);
            startNet.addWire(wire);

            circuit.removeNet(endNet);
        }
        else{
            startNet.addWire(wire);
        }
    }

    @Override
    public void undo(){
        circuit.getWires().remove(wire);

        if(createdNet != null){
            circuit.removeNet(createdNet);
            return;
        }

        if(mergedNet != null){
            mergedIntoNet.getPins().clear();
            mergedIntoNet.getWires().clear();

            mergedIntoNet.getPins().addAll(originalPins);
            mergedIntoNet.getWires().addAll(originalWires);

            circuit.addNet(mergedNet);
            return;
        }

        Net net = circuit.getNet(wire.getStart());

        if(net != null){
            net.removeWire(wire);

            if(net.containsPin(wire.getStart())){
                net.removePin(wire.getStart());
            }

            if(net.containsPin(wire.getEnd())){
                net.removePin(wire.getEnd());
            }
        }
    }
}