package components.gates;

import circuit.Circuit;
import circuit.Net;
import circuit.Pin;
import simulation.Voltage;
import util.Vector2;

public class NOTGate extends LogicGate {
    public NOTGate(Vector2 position){
        super(position);

        addPin(new Pin(this, new Vector2(-10,0))); // input
        addPin(new Pin(this, new Vector2(10,0))); // output
    }

    @Override
    public String getID(){
        return "NOT";
    }

    @Override
    public Voltage solve(Circuit circuit){
        Net inputNet = circuit.getNet(getPins().get(0));

        if(inputNet == null){
            return Voltage.FLOATING;
        }

        return switch(inputNet.getVoltage()){
            case HIGH -> Voltage.LOW;
            case LOW -> Voltage.HIGH;
            case FLOATING -> Voltage.FLOATING;
            case CONFLICT -> Voltage.CONFLICT;
        };
    }
}