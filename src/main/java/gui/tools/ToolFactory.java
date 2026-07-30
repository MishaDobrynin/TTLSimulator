package gui.tools;

import components.*;

public class ToolFactory {

    public static Tool getTool(int index){

        return switch(index){
            case 1 -> new SelectTool();
            case 2 -> new PlaceComponentTool(pos -> new NMOS(pos));
            case 3 -> new PlaceComponentTool(pos -> new PMOS(pos));
            case 4 -> new PlaceComponentTool(pos -> new InputNode(pos));
            case 5 -> new PlaceComponentTool(pos -> new PowerNode(pos));
            case 6 -> new PlaceComponentTool(pos -> new GroundNode(pos));
            case 7 -> new WireTool();
            case 8 -> new PlaceComponentTool(pos -> new OutputNode(pos));
            default -> null;
        };
    }
}