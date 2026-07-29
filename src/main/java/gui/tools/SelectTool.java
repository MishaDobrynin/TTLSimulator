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