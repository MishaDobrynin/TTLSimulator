package gui.palette;

import gui.CircuitCanvas;
import gui.tools.PartFactory;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class PartPalette {
    private static final double SIZE = 50;

    private final Canvas canvas;
    private final CircuitCanvas circuitCanvas;

    private double centerX;
    private double centerY;

    public PartPalette(CircuitCanvas circuitCanvas){
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

        int index = getPartIndex(row, column);

        if(index != -1){
            circuitCanvas.getToolManager().setCurrentTool(
                    PartFactory.getPart(index),
                    circuitCanvas
            );
        }

        close();

        event.consume();
    }

    private int getPartIndex(int row, int column){
        if(row == 1 && column == 1){
            return -1;
        }

        return switch(row * 3 + column){
            case 0 -> 1; // NAND
            case 1 -> 2; // NOT
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

        gc.strokeText("NAND", 5, 30);
        gc.strokeText("NOT", 60, 30);

        gc.strokeText("+", 72, 80);
    }
}
