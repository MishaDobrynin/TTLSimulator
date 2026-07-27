package gui;

import circuit.Circuit;
import components.Component;
import gui.render.ComponentRenderer;
import gui.render.GridRenderer;
import gui.camera.Camera;
import gui.tools.ToolManager;
import gui.tools.SelectTool;
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
    private final ComponentRenderer componentRenderer;
    private final ToolManager toolManager;
    private final SelectionManager selectionManager;

    private final Circuit circuit;

    public CircuitCanvas(Camera camera, Circuit circuit){
        this.camera = camera;
        this.circuit = circuit;
        gridRenderer = new GridRenderer(camera);
        componentRenderer = new ComponentRenderer(camera);
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

    public Circuit getCircuit(){return circuit;}
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

        for(Component component : circuit.getComponents()){
            componentRenderer.drawComponent(
                    gc,
                    component,
                    width,
                    height
            );

        }
    }
}
