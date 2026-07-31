package components;
import circuit.Circuit;
import circuit.Net;
import circuit.Pin;
import simulation.Voltage;
import util.Vector2;
public class OutputNode extends Component {
    private final Pin input;
    public OutputNode(Vector2 position){
        super(position);
        input = new Pin(this, Vector2.ZERO);
        addPin(input);
    }
    public Pin getInput(){
        return input;
    }
    public Voltage getVoltage(Circuit circuit){
        Net net = circuit.getNet(input);
        if(net == null){
            return Voltage.FLOATING;
        }
        return net.getVoltage();
    }
    @Override
    public String getID(){
        return "OutPin";
    }
}