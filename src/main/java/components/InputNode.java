package components;

import circuit.Pin;
import util.Vector2;
import simulation.Voltage;

public class InputNode extends Component {

    private final Pin output;

    private Voltage voltage;

    public InputNode(Vector2 position){
        super(position);

        voltage = Voltage.LOW;

        output = new Pin(
                this,
                Vector2.ZERO
        );

        addPin(output);
    }

    public Pin getOutput(){
        return output;
    }

    public Voltage getVoltage(){
        return voltage;
    }

    public void toggle(){
        if(voltage == Voltage.LOW){
            voltage = Voltage.HIGH;
        }
        else{
            voltage = Voltage.LOW;
        }
    }
}