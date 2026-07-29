package gui.render;

import gui.camera.Camera;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import util.Vector2;
import components.Component;
import components.GroundNode;
import components.InputNode;
import components.NMOS;
import components.PMOS;
import components.PowerNode;

public class ComponentRenderer {
    private final Camera camera;

    public ComponentRenderer(Camera camera){
        this.camera = camera;
    }

    /**
     * Draws any supported component.
     */
    public void drawComponent(
            GraphicsContext gc,
            Component component,
            double viewportWidth,
            double viewportHeight){

        if(component instanceof NMOS){

            drawNMOS(
                    gc,
                    component.getPosition(),
                    viewportWidth,
                    viewportHeight
            );

        }
        else if(component instanceof PMOS){

            drawPMOS(
                    gc,
                    component.getPosition(),
                    viewportWidth,
                    viewportHeight
            );

        }
        else if(component instanceof InputNode){

            drawInput(
                    gc,
                    component.getPosition(),
                    viewportWidth,
                    viewportHeight
            );

        }
        else if(component instanceof PowerNode){

            drawPower(
                    gc,
                    component.getPosition(),
                    viewportWidth,
                    viewportHeight
            );

        }
        else if(component instanceof GroundNode){

            drawGround(
                    gc,
                    component.getPosition(),
                    viewportWidth,
                    viewportHeight
            );

        }
        else{
            throw new IllegalArgumentException(
                    "Unsupported component type: "
                            + component.getClass().getSimpleName()
            );
        }
    }

    /**
     * Draws a selection highlight around a component.
     */
    public void drawSelection(
            GraphicsContext gc,
            Component component,
            double viewportWidth,
            double viewportHeight){

        Vector2 screen = camera.worldToScreen(
                component.getPosition(),
                viewportWidth,
                viewportHeight
        );

        gc.setStroke(Color.BLUE);

        gc.strokeOval(
                screen.getX() - 20,
                screen.getY() - 20,
                40,
                40
        );
    }

    /**
     * Draws an NMOS transistor.
     */
    public void drawNMOS(
            GraphicsContext gc,
            Vector2 worldPosition,
            double viewportWidth,
            double viewportHeight){

        Vector2 screen = camera.worldToScreen(
                worldPosition,
                viewportWidth,
                viewportHeight
        );

        drawTransistor(
                gc,
                screen,
                "N"
        );
    }

    /**
     * Draws a PMOS transistor.
     */
    public void drawPMOS(
            GraphicsContext gc,
            Vector2 worldPosition,
            double viewportWidth,
            double viewportHeight){

        Vector2 screen = camera.worldToScreen(
                worldPosition,
                viewportWidth,
                viewportHeight
        );

        drawTransistor(
                gc,
                screen,
                "P"
        );
    }

    /**
     * Draws an input node.
     */
    public void drawInput(
            GraphicsContext gc,
            Vector2 worldPosition,
            double viewportWidth,
            double viewportHeight){

        Vector2 screen = camera.worldToScreen(
                worldPosition,
                viewportWidth,
                viewportHeight
        );

        gc.setStroke(Color.BLACK);

        gc.strokeOval(
                screen.getX()-8,
                screen.getY()-8,
                16,
                16
        );

        gc.strokeText(
                "IN",
                screen.getX()-8,
                screen.getY()-12
        );
    }

    /**
     * Draws a power node.
     */
    public void drawPower(
            GraphicsContext gc,
            Vector2 worldPosition,
            double viewportWidth,
            double viewportHeight){

        Vector2 screen = camera.worldToScreen(
                worldPosition,
                viewportWidth,
                viewportHeight
        );

        double x = screen.getX();
        double y = screen.getY();

        gc.setStroke(Color.BLACK);

        gc.strokeLine(x,y+12,x,y-12);
        gc.strokeLine(x-8,y-12,x+8,y-12);

        gc.strokeText(
                "VDD",
                x-12,
                y-18
        );
    }

    /**
     * Draws a ground node.
     */
    public void drawGround(
            GraphicsContext gc,
            Vector2 worldPosition,
            double viewportWidth,
            double viewportHeight){

        Vector2 screen = camera.worldToScreen(
                worldPosition,
                viewportWidth,
                viewportHeight
        );

        double x = screen.getX();
        double y = screen.getY();

        gc.setStroke(Color.BLACK);

        gc.strokeLine(x,y-10,x,y);

        gc.strokeLine(x-8,y,x+8,y);

        gc.strokeLine(x-5,y+4,x+5,y+4);

        gc.strokeLine(x-2,y+8,x+2,y+8);
    }

    /**
     * Shared transistor drawing routine.
     */
    private void drawTransistor(
            GraphicsContext gc,
            Vector2 screen,
            String label){

        double x = screen.getX();
        double y = screen.getY();

        gc.setStroke(Color.BLACK);

        gc.strokeRect(
                x-12,
                y-20,
                24,
                40
        );

        gc.strokeLine(
                x-20,
                y,
                x-12,
                y
        );

        gc.strokeLine(
                x,
                y-30,
                x,
                y-20
        );

        gc.strokeLine(
                x,
                y+20,
                x,
                y+30
        );

        gc.strokeText(
                label,
                x-4,
                y+4
        );
    }
}
