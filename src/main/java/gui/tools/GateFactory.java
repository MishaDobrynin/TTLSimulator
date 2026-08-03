package gui.tools;

import components.gates.NANDGate;
import components.gates.NOTGate;

public class GateFactory {

    public static Tool getGate(int index){

        return switch(index){
            case 1 -> new PlaceComponentTool(pos -> new NANDGate(pos));
            case 2 -> new PlaceComponentTool(pos -> new NOTGate(pos));
            default -> null;
        };
    }
}