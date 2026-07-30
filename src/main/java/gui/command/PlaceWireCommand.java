package gui.command;

import circuit.Circuit;
import circuit.Net;
import circuit.Pin;
import circuit.Wire;

public class PlaceWireCommand implements Command {

    private final Circuit circuit;
    private final Wire wire;
    private Net createdNet;
    private Net mergedNet;

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
            startNet.merge(endNet);
            startNet.addWire(wire);

            circuit.getNets().remove(endNet);
            mergedNet = endNet;
        }
        else{
            startNet.addWire(wire);
        }
    }

    @Override
    public void undo(){
        circuit.getWires().remove(wire);

        if(createdNet != null){
            circuit.getNets().remove(createdNet);
            return;
        }

        Net net = circuit.getNet(wire.getStart());

        if(net != null){
            net.removeWire(wire);
            net.removePin(wire.getStart());
            net.removePin(wire.getEnd());
        }

        if(mergedNet != null){
            circuit.addNet(mergedNet);
        }
    }
}