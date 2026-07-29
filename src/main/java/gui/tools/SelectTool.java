package gui.tools;

import components.Component;
import gui.CircuitCanvas;
import javafx.scene.input.MouseEvent;
import util.Vector2;

/**
 * Default editor tool.
 *
 * Handles selecting circuit components.
 */
public class SelectTool extends Tool {

    private static final double SELECTION_RADIUS = 30;

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
            canvas.getSelectionManager()
                    .select(selected);
        }
        else{
            canvas.getSelectionManager()
                    .clearSelection();
        }

        canvas.redraw();
    }


    /**
     * Finds the closest component to a world position.
     */
    private Component findComponent(
            Vector2 position,
            CircuitCanvas canvas){

        Component closest = null;
        double closestDistance = Double.MAX_VALUE;


        for(Component component : canvas.getCircuit().getComponents()){

            double distance =
                    component.getPosition()
                            .distance(position);


            if(distance < SELECTION_RADIUS
                    && distance < closestDistance){

                closest = component;
                closestDistance = distance;
            }
        }

        return closest;
    }
}