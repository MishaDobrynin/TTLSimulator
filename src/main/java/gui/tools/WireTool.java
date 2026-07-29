package gui.tools;

import circuit.Pin;
import circuit.Wire;
import gui.CircuitCanvas;
import gui.command.PlaceWireCommand;
import gui.command.PlaceWireCommand;
import javafx.scene.input.MouseEvent;
import util.Vector2;

public class WireTool extends Tool {

    private static final double PIN_SELECTION_DISTANCE = 15;

    private Pin startPin;

    @Override
    public void mousePressed(MouseEvent event, CircuitCanvas canvas){

        Vector2 worldPosition = canvas.getCamera().screenToWorld(
                new Vector2(event.getX(), event.getY()),
                canvas.getWidth(),
                canvas.getHeight()
        );

        Pin clickedPin = findPin(worldPosition, canvas);

        if(clickedPin == null){
            startPin = null;
            return;
        }

        if(startPin == null){
            startPin = clickedPin;
            return;
        }

        if(startPin != clickedPin){
            Wire wire = new Wire(startPin, clickedPin);

            canvas.getCommandManager().execute(
                    new PlaceWireCommand(canvas.getCircuit(), wire)
            );
        }

        startPin = null;
        canvas.redraw();
    }

    private Pin findPin(Vector2 position, CircuitCanvas canvas){

        for(var component : canvas.getCircuit().getComponents()){
            for(Pin pin : component.getPins()){

                if(pin.getWorldPosition().distance(position) <= PIN_SELECTION_DISTANCE){
                    return pin;
                }

            }
        }

        return null;
    }
}