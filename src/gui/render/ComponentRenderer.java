package gui.render;

import components.Component;
import components.GroundNode;
import components.InputNode;
import components.NMOS;
import components.PMOS;
import components.PowerNode;
import gui.camera.Camera;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import util.Vector2;

public class ComponentRenderer {
    private final Camera camera;

    public ComponentRenderer(Camera camera) {
        this.camera = camera;
    }

    /**
     * Draws a single component.
     */
    public void render(
            GraphicsContext gc,
            Component component,
            double viewportWidth,
            double viewportHeight
    ) {

        Vector2 screen = camera.worldToScreen(
                component.getPosition(),
                viewportWidth,
                viewportHeight
        );

        double x = screen.getX();
        double y = screen.getY();

        gc.setStroke(Color.BLACK);
        gc.setFill(Color.WHITE);

        if (component instanceof NMOS) {
            drawTransistor(gc, x, y, "N");
        }

        else if (component instanceof PMOS) {
            drawTransistor(gc, x, y, "P");
        }

        else if (component instanceof InputNode) {
            drawInput(gc, x, y);
        }

        else if (component instanceof PowerNode) {
            drawPower(gc, x, y);
        }

        else if (component instanceof GroundNode) {
            drawGround(gc, x, y);
        }

        else {
            gc.strokeRect(x - 10, y - 10, 20, 20);
        }
    }

    /**
     * Draws a simple transistor placeholder.
     */
    private void drawTransistor(
            GraphicsContext gc,
            double x,
            double y,
            String label
    ) {

        gc.strokeRect(
                x - 12,
                y - 20,
                24,
                40
        );

        gc.strokeLine(x - 20, y, x - 12, y);

        gc.strokeLine(x, y - 30, x, y - 20);

        gc.strokeLine(x, y + 20, x, y + 30);

        gc.strokeText(label, x - 4, y + 4);
    }

    /**
     * Draws an input node.
     */
    private void drawInput(GraphicsContext gc, double x, double y) {

        gc.strokeOval(
                x - 8,
                y - 8,
                16,
                16
        );

        gc.strokeText("IN", x - 8, y - 12);
    }

    /**
     * Draws a power node.
     */
    private void drawPower(GraphicsContext gc, double x, double y) {

        gc.strokeLine(x, y + 12, x, y - 12);
        gc.strokeLine(x - 8, y - 12, x + 8, y - 12);

        gc.strokeText("VDD", x - 12, y - 18);
    }

    /**
     * Draws a ground node.
     */
    private void drawGround(GraphicsContext gc, double x, double y) {

        gc.strokeLine(x, y - 10, x, y);

        gc.strokeLine(x - 8, y, x + 8, y);

        gc.strokeLine(x - 5, y + 4, x + 5, y + 4);

        gc.strokeLine(x - 2, y + 8, x + 2, y + 8);
    }

}
