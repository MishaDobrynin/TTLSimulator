package components;

import circuit.Pin;
import util.Vector2;

public abstract class Transistor extends Component {
    private final Pin gate;
    private final Pin drain;
    private final Pin source;

    protected Transistor(Vector2 position){
        super(position);

        gate = new Pin(
                this,
                new Vector2(-20,0)
        );

        drain = new Pin(
                this,
                new Vector2(0,-20)
        );

        source = new Pin(
                this,
                new Vector2(0,20)
        );

        addPin(gate);
        addPin(drain);
        addPin(source);
    }

    public Pin getGate(){
        return this.gate;
    }

    public Pin getDrain(){
        return this.drain;
    }

    public Pin getSource(){
        return this.source;
    }
}