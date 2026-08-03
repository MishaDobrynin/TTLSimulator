package components.gates;

import components.Component;
import circuit.Circuit;
import simulation.Voltage;
import util.Vector2;

public abstract class LogicGate extends Component {
    protected LogicGate(Vector2 position){
        super(position);
    }

    public abstract Voltage solve(Circuit circuit);
}