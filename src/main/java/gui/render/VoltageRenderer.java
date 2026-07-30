package gui.render;

import gui.camera.Camera;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import simulation.Voltage;
import util.Vector2;

public class VoltageRenderer {

    private final Camera camera;

    public VoltageRenderer(Camera camera){
        this.camera = camera;
    }

    public void drawVoltage(
            GraphicsContext gc,
            Vector2 start,
            Vector2 end,
            Voltage voltage,
            double viewportWidth,
            double viewportHeight){

        if(voltage == Voltage.FLOATING){
            return;
        }

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

        double dx = screenEnd.getX() - screenStart.getX();
        double dy = screenEnd.getY() - screenStart.getY();

        double length = Math.sqrt(
                dx * dx + dy * dy
        );

        if(length == 0){
            return;
        }

        double offset = 2;

        double offsetX = -dy / length * offset;
        double offsetY = dx / length * offset;

        if(voltage == Voltage.HIGH){
            gc.setStroke(Color.RED);
        }
        else if(voltage == Voltage.LOW){
            gc.setStroke(Color.BLUE);
        }
        else if(voltage == Voltage.CONFLICT){
            gc.setStroke(Color.ORANGE);
        }

        gc.strokeLine(
                screenStart.getX() + offsetX,
                screenStart.getY() + offsetY,
                screenEnd.getX() + offsetX,
                screenEnd.getY() + offsetY
        );

        gc.strokeLine(
                screenStart.getX() - offsetX,
                screenStart.getY() - offsetY,
                screenEnd.getX() - offsetX,
                screenEnd.getY() - offsetY
        );
    }
}