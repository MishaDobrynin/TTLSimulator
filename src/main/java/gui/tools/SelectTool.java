package gui.tools;

import circuit.Wire;
import components.Component;
import components.InputNode;
import gui.CircuitCanvas;
import gui.command.MoveComponentCommand;
import javafx.scene.input.MouseEvent;
import util.Vector2;

import static gui.render.GridRenderer.GRID_SPACING;

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
                ),
                canvas.getWidth(),
                canvas.getHeight()
        );

        Component selectedComponent =
                findComponent(worldPosition, canvas);

        if(selectedComponent instanceof InputNode inputNode){
            canvas.getSelectionManager().select(inputNode);

            dragging = false;
            dragStartPosition = null;

            inputNode.toggle();
            canvas.simulate();
            canvas.redraw();
            return;
        }

        if(selectedComponent != null){

            canvas.getSelectionManager().select(selectedComponent);

            dragStartPosition = selectedComponent.getPosition();
            dragging = true;

        }
        else {

            Wire selectedWire =
                    findWire(worldPosition, canvas);

            if(selectedWire != null){
                canvas.getSelectionManager().select(selectedWire);

                dragging = false;
                dragStartPosition = null;
            }
            else{
                canvas.getSelectionManager().clearSelection();

                dragStartPosition = null;
                dragging = false;
            }
        }

        canvas.redraw();
    }


    @Override
    public void mouseDragged(MouseEvent event, CircuitCanvas canvas){

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

        selected.setPosition(Vector2.snap(worldPosition, GRID_SPACING));

        canvas.redraw();
    }


    @Override
    public void mouseReleased(MouseEvent event, CircuitCanvas canvas){
        if(dragging){
            Component selected = canvas.getSelectionManager().getSelectedComponent();
            if(selected != null && dragStartPosition != null){
                Vector2 endPosition = selected.getPosition();
                if(!dragStartPosition.equals(endPosition)){
                    canvas.getCommandManager().execute(
                            new MoveComponentCommand(selected, dragStartPosition, endPosition)
                    );
                    canvas.simulate();
                    canvas.redraw();
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


    private Wire findWire(
            Vector2 position,
            CircuitCanvas canvas){

        for(Wire wire :
                canvas.getCircuit().getWires()){

            double distance =
                    distanceToLine(
                            position,
                            wire.getStartPosition(),
                            wire.getEndPosition()
                    );

            if(distance <= SELECTION_DISTANCE){
                return wire;
            }
        }

        return null;
    }


    private double distanceToLine(
            Vector2 point,
            Vector2 start,
            Vector2 end){

        double x = point.getX();
        double y = point.getY();

        double x1 = start.getX();
        double y1 = start.getY();

        double x2 = end.getX();
        double y2 = end.getY();

        double lengthSquared =
                Math.pow(x2 - x1, 2)
                        + Math.pow(y2 - y1, 2);

        if(lengthSquared == 0){
            return point.distance(start);
        }

        double t =
                ((x - x1) * (x2 - x1)
                        + (y - y1) * (y2 - y1))
                        / lengthSquared;

        t = Math.max(0, Math.min(1, t));

        double closestX =
                x1 + t * (x2 - x1);

        double closestY =
                y1 + t * (y2 - y1);

        return point.distance(
                new Vector2(
                        closestX,
                        closestY
                )
        );
    }
}