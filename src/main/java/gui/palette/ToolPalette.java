package gui.palette;

import gui.CircuitCanvas;
import gui.tools.ToolFactory;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;

public class ToolPalette {
    private static final double SIZE = 50;

    private final Canvas canvas;
    private final CircuitCanvas circuitCanvas;

    private double centerX;
    private double centerY;

    public ToolPalette(CircuitCanvas circuitCanvas){
        this.circuitCanvas = circuitCanvas;
        
        canvas = new Canvas(150, 150);
        canvas.setOnMousePressed(this::handleClick);
        canvas.setVisible(false);

        draw();
    }

    public Canvas getCanvas(){
        return canvas;
    }

    public void open(double x, double y){
        centerX = x;
        centerY = y;
        canvas.setLayoutX(centerX - 75);
        canvas.setLayoutY(centerY - 75);
        canvas.setVisible(true);
        draw();
    }

    public void close(){
        canvas.setVisible(false);
    }

    private void handleClick(MouseEvent event){
        int column = (int)(event.getX() / SIZE);
        int row = (int)(event.getY() / SIZE);
        int index = getToolIndex(row, column);

        if(index != -1){
            circuitCanvas.getToolManager().setCurrentTool(ToolFactory.getTool(index),circuitCanvas);
        }
        close();

        event.consume();
    }

    private int getToolIndex(int row, int column){
        if(row == 1 && column == 1){
            return -1;
        }

        return switch(row * 3 + column){
            case 0 -> 1;
            case 1 -> 2;
            case 2 -> 3;
            case 3 -> 4;
            case 5 -> 8;
            case 6 -> 5;
            case 7 -> 7;
            case 8 -> 6;
            default -> -1;
        };
    }

    private void draw(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.BLACK);

        for(int i = 0; i <= 3; i++){
            gc.strokeLine(i * SIZE, 0, i * SIZE, 150);
            gc.strokeLine(0, i * SIZE, 150, i * SIZE);
        }

        gc.strokeText("SEL", 10, 30); //select tool
        gc.strokeText("NMOS", 55, 30); //n type metal-oxide semiconductor
        gc.strokeText("PMOS", 105, 30); //p type metal-oxide semiconductor

        gc.strokeText("IN", 15, 80); //input pins, controllable
        gc.strokeText("OUT", 105, 80); //the pins to be read

        gc.strokeText("PWR", 10, 130); //power (vdd)
        gc.strokeText("WIRE", 55, 130); //a wire
        gc.strokeText("GND", 105, 130); //gnd

        gc.strokeText("+", 72, 80);
    }
}
