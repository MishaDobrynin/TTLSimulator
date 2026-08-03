package components.gates;

import circuit.Net;
import components.Component;
import circuit.Circuit;
import simulation.Voltage;
import util.Vector2;

public abstract class LogicGate extends Component {
    protected LogicGate(Vector2 position){
        super(position);
    }

    public abstract Voltage solve(Circuit circuit);
    protected Voltage getInputVoltage(Circuit circuit, int index){
        Net net = circuit.getNet(getPins().get(index));

        if(net == null){
            return Voltage.FLOATING;
        }

        return net.getVoltage();
    }
}