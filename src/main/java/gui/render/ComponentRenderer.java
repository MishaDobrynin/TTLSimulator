package gui.render;

import circuit.Net;
import components.Component;
import components.GroundNode;
import components.InputNode;
import components.NMOS;
import components.PMOS;
import components.PowerNode;
import gui.camera.Camera;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import simulation.Voltage;
import util.Vector2;
import components.OutputNode;
import circuit.Circuit;

public class ComponentRenderer {
    private final Camera camera;

    public ComponentRenderer(Camera camera){
        this.camera = camera;
    }

    public void drawComponent(GraphicsContext gc, Component component, Circuit circuit, double viewportWidth, double viewportHeight){
        Vector2 screen = camera.worldToScreen(
                component.getPosition(),
                viewportWidth,
                viewportHeight
        );

        gc.save();

        gc.translate(screen.getX(), screen.getY());
        gc.rotate(component.getRotation());

        if(component instanceof NMOS){
            drawNMOS(gc);
        }
        else if(component instanceof PMOS){
            drawPMOS(gc);
        }
        else if(component instanceof InputNode){
            drawInput(gc);
        }
        else if(component instanceof PowerNode){
            drawPower(gc);
        }
        else if(component instanceof GroundNode){
            drawGround(gc);
        }
        else if(component instanceof OutputNode outputNode){
            drawOutput(gc, circuit, outputNode);
        }
        else{
            throw new IllegalArgumentException(
                    "Unsupported component type: " + component.getClass().getSimpleName()
            );
        }

        gc.restore();
    }

    public void drawSelection(GraphicsContext gc, Component component, double viewportWidth, double viewportHeight){
        Vector2 screen = camera.worldToScreen(component.getPosition(), viewportWidth, viewportHeight);

        gc.setStroke(Color.BLUE);
        gc.strokeOval(screen.getX() - 20, screen.getY() - 20, 40, 40);
    }

    private void drawNMOS(GraphicsContext gc){
        drawTransistor(gc, "N");
    }

    private void drawPMOS(GraphicsContext gc){
        drawTransistor(gc, "P");
    }

    private void drawInput(GraphicsContext gc){
        gc.setStroke(Color.BLACK);

        gc.strokeOval(-8, -8, 16, 16);
        gc.strokeText("IN", -8, -12);
    }

    private void drawPower(GraphicsContext gc){
        gc.setStroke(Color.BLACK);

        gc.strokeLine(0, 12, 0, -12);
        gc.strokeLine(-8, -12, 8, -12);
        gc.strokeText("VDD", -12, -18);
    }

    private void drawGround(GraphicsContext gc){
        gc.setStroke(Color.BLACK);

        gc.strokeLine(0, -10, 0, 0);
        gc.strokeLine(-8, 0, 8, 0);
        gc.strokeLine(-5, 4, 5, 4);
        gc.strokeLine(-2, 8, 2, 8);
    }

    private void drawTransistor(GraphicsContext gc, String label){
        gc.setStroke(Color.BLACK);

        gc.strokeRect(-12, -20, 24, 40);

        // Gate
        gc.strokeLine(-20, 0, -12, 0);

        // Drain
        gc.strokeLine(0, -30, 0, -20);

        // Source
        gc.strokeLine(0, 20, 0, 30);

        gc.strokeText(label, -4, 4);

        drawTransistorLabels(gc);
    }

    private void drawTransistorLabels(GraphicsContext gc){
        gc.setFill(Color.BLACK);

        gc.fillText("G", -32, 5);
        gc.fillText("D", 5, -25);
        gc.fillText("S", 5, 35);
    }
    private void drawOutput(GraphicsContext gc, Circuit circuit, OutputNode outputNode){
        gc.setStroke(Color.BLACK);

        gc.strokeOval(-8, -8, 16, 16);
        gc.strokeText("OUT", -12, -12);

        Voltage voltage = Voltage.FLOATING;

        Net net = circuit.getNet(outputNode.getInput());

        if(net != null){
            voltage = net.getVoltage();
        }

        switch(voltage){
            case HIGH -> gc.setFill(Color.RED);
            case LOW -> gc.setFill(Color.BLUE);
            case CONFLICT -> gc.setFill(Color.PURPLE);
            case FLOATING -> gc.setFill(Color.YELLOW);
        }

        gc.fillOval(-5, -5, 10, 10);
    }
}