package gui;

import javafx.beans.value.ChangeListener;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class CircuitCanvas extends Pane{
    private final Canvas backgroundCanvas;
    private static final double GRID_SPACING = 20; //spacing... between the grids
    public CircuitCanvas(){
        backgroundCanvas = new Canvas();
        setStyle("-fx-background-color: white;");
        backgroundCanvas.widthProperty().bind(widthProperty());
        backgroundCanvas.heightProperty().bind(heightProperty());

        getChildren().add(backgroundCanvas);
        ChangeListener<Number> resizeListener = (obs, oldVal, newVal) -> drawBackground();
        widthProperty().addListener(resizeListener);
        heightProperty().addListener(resizeListener);

        drawBackground();
    }
    private void drawBackground() {

        GraphicsContext gc = backgroundCanvas.getGraphicsContext2D();

        double width = backgroundCanvas.getWidth();
        double height = backgroundCanvas.getHeight();

        // Clear
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);

        // Draw grid dots
        gc.setFill(Color.LIGHTGRAY);

        for (double x = 0; x < width; x += GRID_SPACING) {
            for (double y = 0; y < height; y += GRID_SPACING) {

                gc.fillOval(x - 1, y - 1, 2, 2);

            }
        }

    }

}