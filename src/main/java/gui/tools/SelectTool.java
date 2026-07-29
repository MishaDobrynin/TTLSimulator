package gui.tools;

import components.Component;
import gui.CircuitCanvas;
import gui.command.MoveComponentCommand;
import javafx.scene.input.MouseEvent;
import util.Vector2;

/**
 * Default editor tool.
 *
 * Handles selecting and moving components.
 */
public class SelectTool extends Tool {
    private static final double SELECTION_DISTANCE = 25;
    private boolean dragging;
    private Vector2 dragStartPosition;

    @Override
    public void mousePressed(MouseEvent event, CircuitCanvas canvas){
        Vector2 worldPosition = canvas.getCamera().screenToWorld(
                        new Vector2(
                                event.getX(),
                                event.getY()
                        ), canvas.getWidth(), canvas.getHeight());

        Component selected = findComponent(worldPosition, canvas);

        if(selected != null){
            canvas.getSelectionManager().select(selected);

            dragStartPosition = selected.getPosition();
            dragging = true;

        }
        else{
            canvas.getSelectionManager().clearSelection();
            dragStartPosition = null;
            dragging = false;
        }

        canvas.redraw();
    }


    @Override
    public void mouseDragged(
            MouseEvent event,
            CircuitCanvas canvas){

        if(!dragging){
            return;
        }


        Component selected =
                canvas.getSelectionManager()
                        .getSelectedComponent();


        if(selected == null){
            return;
        }


        Vector2 worldPosition =
                canvas.getCamera().screenToWorld(
                        new Vector2(
                                event.getX(),
                                event.getY()
                        ),
                        canvas.getWidth(),
                        canvas.getHeight()
                );


        selected.setPosition(worldPosition);


        canvas.redraw();
    }


    @Override
    public void mouseReleased(
            MouseEvent event,
            CircuitCanvas canvas){

        if(dragging){
            Component selected = canvas.getSelectionManager().getSelectedComponent();

            if(selected != null && dragStartPosition != null){
                Vector2 endPosition = selected.getPosition();

                if(!dragStartPosition.equals(endPosition)){
                    canvas.getCommandManager().execute(
                            new MoveComponentCommand(
                                    selected,
                                    dragStartPosition,
                                    endPosition
                            )
                    );
                }
            }
        }

        dragging = false;
        dragStartPosition = null;
    }


    private Component findComponent(
            Vector2 position,
            CircuitCanvas canvas){

        for(Component component :
                canvas.getCircuit().getComponents()){


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