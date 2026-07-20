package components;

import circuit.Pin;
import util.Vector2;

public class PowerNode extends Component {
    private final Pin output;

    public PowerNode(Vector2 position){
        super(position);
        output = new Pin(
                this,
                Vector2.ZERO
        );
        addPin(output);
    }
    public Pin getOutput(){
        return this.output;
    }
}