package gui;
import gui.render.GridRenderer;
import gui.camera.Camera;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

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
    }
}