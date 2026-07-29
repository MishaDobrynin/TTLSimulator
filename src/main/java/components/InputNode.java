package components;

import circuit.Pin;
import util.Vector2;

public class InputNode extends Component {

    private final Pin output;
    private boolean state;

    public InputNode(Vector2 position){
        super(position);

        output = new Pin(
                this,
                Vector2.ZERO
        );

        addPin(output);

        state = false;
    }

    public Pin getOutput(){
        return output;
    }

    public boolean getState(){
        return state;
    }

    public void setState(boolean state){
        this.state = state;
    }
}