package gui.render;

import gui.camera.Camera;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import util.Vector2;

public class WireRenderer {
    private final Camera camera;

    public WireRenderer(Camera camera){
        this.camera = camera;
    }

    /**
     * Draws a wire between two world positions.
     */
    public void drawWire(
            GraphicsContext gc,
            Vector2 start,
            Vector2 end,
            double viewportWidth,
            double viewportHeight){

        Vector2 screenStart = camera.worldToScreen(
                start,
                viewportWidth,
                viewportHeight
        );

        Vector2 screenEnd = camera.worldToScreen(
                end,
                viewportWidth,
                viewportHeight
        );

        gc.setStroke(Color.BLACK);

        gc.strokeLine(
                screenStart.getX(),
                screenStart.getY(),
                screenEnd.getX(),
                screenEnd.getY()
        );
    }
}