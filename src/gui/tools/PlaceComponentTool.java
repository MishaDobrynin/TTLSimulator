package gui.tools;

import gui.CircuitCanvas;
import javafx.scene.input.MouseEvent;
import util.Vector2;

public class PlaceComponentTool extends Tool {

    private final ComponentFactory componentFactory;

    public PlaceComponentTool(ComponentFactory componentFactory){
        this.componentFactory = componentFactory;
    }

    @Override
    public void mousePressed(
            MouseEvent event,
            CircuitCanvas canvas){

        Vector2 screenPosition = new Vector2(
                event.getX(),
                event.getY()
        );

        Vector2 worldPosition =
                canvas.getCamera().screenToWorld(
                        screenPosition,
                        canvas.getWidth(),
                        canvas.getHeight()
                );

        canvas.getCircuit().addComponent(
                componentFactory.create(worldPosition)
        );

        canvas.redraw();
    }
}