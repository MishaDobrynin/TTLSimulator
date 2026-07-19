package gui;

import gui.render.GridRenderer;
import gui.camera.Camera;
import javafx.beans.value.ChangeListener;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class CircuitCanvas extends Pane{
    private final Canvas backgroundCanvas;
    private final Camera camera;
    private final GridRenderer gridRenderer;

    public CircuitCanvas(Camera camera){
        this.camera = camera;
        gridRenderer = new GridRenderer(camera);
        backgroundCanvas = new Canvas();

        backgroundCanvas.widthProperty().bind(widthProperty());
        backgroundCanvas.heightProperty().bind(heightProperty());

        getChildren().add(backgroundCanvas);

        ChangeListener<Number> resizeListener = (obs, oldVal, newVal) -> redraw();

        widthProperty().addListener(resizeListener);
        heightProperty().addListener(resizeListener);

        redraw();
    }

    private void redraw(){
        GraphicsContext gc = backgroundCanvas.getGraphicsContext2D();

        double width = backgroundCanvas.getWidth();
        double height = backgroundCanvas.getHeight();

        gc.setFill(Color.WHITE); //clears previous frame
        gc.fillRect(0,0,width,height);
        
        gridRenderer.render( //grid draw
                gc,
                width,
                height
        );
    }
}
