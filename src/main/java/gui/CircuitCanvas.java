package gui;

import circuit.Circuit;
import components.Component;
import components.GroundNode;
import components.InputNode;
import components.NMOS;
import components.PMOS;
import components.PowerNode;
import gui.camera.Camera;
import gui.render.ComponentRenderer;
import gui.render.GridRenderer;
import gui.selection.SelectionManager;
import gui.tools.PlaceComponentTool;
import gui.tools.SelectTool;
import gui.tools.ToolManager;
import gui.command.CommandManager;
import gui.command.DeleteComponentCommand;
import javafx.beans.value.ChangeListener;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class CircuitCanvas extends Pane {

    private final Canvas backgroundCanvas;

    private final Camera camera;
    private final GridRenderer gridRenderer;
    private final ComponentRenderer componentRenderer;

    private final ToolManager toolManager;
    private final SelectionManager selectionManager;

    private final Circuit circuit;

    private final CommandManager commandManager;


    public CircuitCanvas(Camera camera, Circuit circuit){
        this.camera = camera;
        this.circuit = circuit;

        gridRenderer = new GridRenderer(camera);
        componentRenderer = new ComponentRenderer(camera);

        backgroundCanvas = new Canvas();

        selectionManager = new SelectionManager();
        toolManager = new ToolManager();
        commandManager = new CommandManager();

        toolManager.setCurrentTool(
                new SelectTool(),
                this
        );

        setupKeyboardControls();

        backgroundCanvas.widthProperty()
                .bind(widthProperty());

        backgroundCanvas.heightProperty()
                .bind(heightProperty());


        getChildren().add(backgroundCanvas);


        setFocusTraversable(true);

        sceneProperty().addListener((obs, oldScene, newScene) -> {
            if(newScene != null){
                requestFocus();
            }
        });


        ChangeListener<Number> resizeListener =
                (obs, oldVal, newVal) -> redraw();

        widthProperty().addListener(resizeListener);
        heightProperty().addListener(resizeListener);


        setOnMousePressed(event ->
                toolManager.mousePressed(event, this));

        setOnMouseReleased(event ->
                toolManager.mouseReleased(event, this));

        setOnMouseDragged(event ->
                toolManager.mouseDragged(event, this));

        setOnMouseMoved(event ->
                toolManager.mouseMoved(event, this));

        redraw();
    }


    private void setupKeyboardControls() {
        setOnKeyPressed(event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.Z) {
                commandManager.undo();
                redraw();
                return;
            }
            if (event.isControlDown() && event.getCode() == KeyCode.Y) {
                commandManager.redo();
                redraw();
                return;
            }
            if (event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.BACK_SPACE) {
                Component selected = selectionManager.getSelectedComponent();
                if (selected != null) {
                    commandManager.execute(new DeleteComponentCommand(circuit, selected));
                    selectionManager.clearSelection();
                    redraw();
                }
                return;
            }
            if (event.getCode() == KeyCode.DIGIT1) {
                toolManager.setCurrentTool(new SelectTool(), this);
            } else if (event.getCode() == KeyCode.DIGIT2) {
                toolManager.setCurrentTool(new PlaceComponentTool(pos -> new NMOS(pos)), this);
            } else if (event.getCode() == KeyCode.DIGIT3) {
                toolManager.setCurrentTool(new PlaceComponentTool(pos -> new PMOS(pos)), this);
            } else if (event.getCode() == KeyCode.DIGIT4) {
                toolManager.setCurrentTool(new PlaceComponentTool(pos -> new InputNode(pos)), this);
            } else if (event.getCode() == KeyCode.DIGIT5) {
                toolManager.setCurrentTool(new PlaceComponentTool(pos -> new PowerNode(pos)), this);
            } else if (event.getCode() == KeyCode.DIGIT6) {
                toolManager.setCurrentTool(new PlaceComponentTool(pos -> new GroundNode(pos)), this);
            }
        });
    }



    public Camera getCamera(){
        return camera;
    }


    public Circuit getCircuit(){
        return circuit;
    }

    public SelectionManager getSelectionManager(){
        return selectionManager;
    }

    public CommandManager getCommandManager(){
        return commandManager;
    }

    public void redraw(){

        GraphicsContext gc =
                backgroundCanvas.getGraphicsContext2D();


        double width = backgroundCanvas.getWidth();
        double height = backgroundCanvas.getHeight();


        gc.setFill(Color.WHITE);
        gc.fillRect(
                0,
                0,
                width,
                height
        );


        gridRenderer.render(
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


            if(selectionManager.isSelected(component)){

                componentRenderer.drawSelection(
                        gc,
                        component,
                        width,
                        height
                );

            }
        }
    }
}