package components;

import circuit.Pin;
import util.Vector2;

public class GroundNode extends Component {
    private final Pin output;

    public GroundNode(Vector2 position){
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