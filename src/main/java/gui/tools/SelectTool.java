package gui.tools;

import components.Component;
import gui.CircuitCanvas;
import javafx.scene.input.MouseEvent;
import util.Vector2;

/**
 * Default editor tool.
 *
 * Handles selecting components.
 */
public class SelectTool extends Tool {

    private static final double SELECTION_DISTANCE = 25;
    private Component selectedComponent;
    private boolean dragging;

    @Override
    public void mousePressed(
            MouseEvent event,
            CircuitCanvas canvas){

        Vector2 worldPosition =
                canvas.getCamera().screenToWorld(
                        new Vector2(
                                event.getX(),
                                event.getY()
                        ),
                        canvas.getWidth(),
                        canvas.getHeight()
                );


        Component selected = findComponent(
                worldPosition,
                canvas
        );

        if(selected != null){
            canvas.getSelectionManager().select(selected);
            selectedComponent = selected;
            dragging = true;

        }
        else{
            canvas.getSelectionManager().clearSelection();
            selectedComponent = null;
            dragging = false;
        }


        canvas.redraw();
    }

    @Override
    public void mouseDragged(MouseEvent event, CircuitCanvas canvas){
        if(!dragging || selectedComponent == null){
            return;
        }
        Vector2 worldPosition =
                canvas.getCamera().screenToWorld(
                        new Vector2(
                                event.getX(),
                                event.getY()
                        ), canvas.getWidth(), canvas.getHeight());

        selectedComponent.setPosition(worldPosition);

        canvas.redraw();
    }

    @Override
    public void mouseReleased(MouseEvent event, CircuitCanvas canvas){
        dragging = false;
    }

    private Component findComponent(
            Vector2 position,
            CircuitCanvas canvas){

        for(Component component : canvas.getCircuit().getComponents()){

            double distance =
                    component.getPosition()
                            .distance(position);


            if(distance <= SELECTION_DISTANCE){

                return component;

            }
        }


        return null;
    }
}