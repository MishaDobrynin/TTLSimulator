package components.gates;

import circuit.Circuit;
import circuit.Net;
import circuit.Pin;
import simulation.Voltage;
import util.Vector2;

public class NANDGate extends LogicGate {
    public NANDGate(Vector2 position){
        super(position);

        addPin(new Pin(this, new Vector2(-10,-5))); // input A
        addPin(new Pin(this, new Vector2(-10,5)));  // input B
        addPin(new Pin(this, new Vector2(10,0)));   // output
    }
    @Override
    public String getID(){
        return "NAND";
    }
    @Override
    public Voltage solve(Circuit circuit){
        Voltage a = getInputVoltage(circuit, 0);
        Voltage b = getInputVoltage(circuit, 1);
        if(a == Voltage.CONFLICT || b == Voltage.CONFLICT){
            return Voltage.CONFLICT;
        }
        if(a == Voltage.LOW || b == Voltage.LOW){
            return Voltage.HIGH;
        }
        if(a == Voltage.HIGH && b == Voltage.HIGH){
            return Voltage.LOW;
        }
        return Voltage.FLOATING;
    }
}