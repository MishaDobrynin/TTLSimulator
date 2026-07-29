package circuit;
import java.util.ArrayList;
import java.util.List;

public class Net {
    private final List<Pin> pins;
    private final List<Wire> wires;

    public Net(){
        pins = new ArrayList<>();
        wires = new ArrayList<>();
    }

    public void addPin(Pin pin){
        if(!pins.contains(pin)){
            pins.add(pin);
        }
    }

    public void addWire(Wire wire){
        if(!wires.contains(wire)){
            wires.add(wire);
        }
    }

    public List<Pin> getPins(){
        return this.pins;
    }

    public List<Wire> getWires(){
        return wires;
    }
}