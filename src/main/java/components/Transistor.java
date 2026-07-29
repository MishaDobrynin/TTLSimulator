package components;

import circuit.Pin;
import util.Vector2;

public abstract class Transistor extends Component {
    private final Pin gate;
    private final Pin drain;
    private final Pin source;

    protected Transistor(Vector2 position){
        super(position);

        gate = new Pin(this, new Vector2(-20,0));

        drain = new Pin(this, new Vector2(0,-30));

        source = new Pin(this, new Vector2(0,30));

        addPin(gate);
        addPin(drain);
        addPin(source);
    }

    public Pin getGate(){
        return gate;
    }

    public Pin getDrain(){
        return drain;
    }

    public Pin getSource(){
        return source;
    }
}