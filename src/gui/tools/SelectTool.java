package gui.tools;

import gui.CircuitCanvas;
import javafx.scene.input.MouseEvent;
import util.Vector2;

/**
 * Default editor tool.
 *
 * Handles selecting and moving circuit objects.
 */
public class SelectTool extends Tool {

    @Override
    public void mousePressed(MouseEvent event, CircuitCanvas canvas) {

        Vector2 worldPosition = canvas.getCamera().screenToWorld(
                new Vector2(event.getX(), event.getY()),
                canvas.getWidth(),
                canvas.getHeight()
        );

        System.out.println("Clicked at world position: " + worldPosition);
    }
}
