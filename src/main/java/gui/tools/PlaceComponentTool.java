package gui.tools;

import gui.CircuitCanvas;
import components.Component;
import gui.command.PlaceComponentCommand;
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

        Component component =
                componentFactory.create(worldPosition);

        canvas.getCommandManager()
                .execute(
                        new PlaceComponentCommand(
                                canvas.getCircuit(),
                                component
                        )
                );

        canvas.redraw();
    }
}