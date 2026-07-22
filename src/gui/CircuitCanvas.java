package gui;

import gui.render.GridRenderer;
import gui.camera.Camera;
import gui.selection.SelectionManager;
import javafx.beans.value.ChangeListener;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class CircuitCanvas extends Pane{
    private final Canvas backgroundCanvas;
    private final Camera camera;
    private final GridRenderer gridRenderer;
    private final ToolManager toolManager;
    private final SelectionManager selectionManager;

    public CircuitCanvas(Camera camera){
        this.camera = camera;
        gridRenderer = new GridRenderer(camera);
        backgroundCanvas = new Canvas();
        selectionManager = new SelectionManager();
        toolManager = new ToolManager();
        toolManager.setCurrentTool(new SelectTool(), this);

        backgroundCanvas.widthProperty().bind(widthProperty());
        backgroundCanvas.heightProperty().bind(heightProperty());

        getChildren().add(backgroundCanvas);

        ChangeListener<Number> resizeListener = (obs, oldVal, newVal) -> redraw();

        widthProperty().addListener(resizeListener);
        heightProperty().addListener(resizeListener);

        redraw();

        setOnMousePressed(event ->
            toolManager.mousePressed(event, this));
        setOnMouseReleased(event ->
            toolManager.mouseReleased(event, this));
        setOnMouseDragged(event ->
            toolManager.mouseDragged(event, this));
        setOnMouseMoved(event ->
            toolManager.mouseMoved(event, this));
    }

    public Camera getCamera() {
        return camera;
    }

    public SelectionManager getSelectionManager() {
        return selectionManager;
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
