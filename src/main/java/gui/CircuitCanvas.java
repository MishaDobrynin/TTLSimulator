package gui;

import circuit.Circuit;
import circuit.Wire;
import components.Component;
import components.GroundNode;
import components.InputNode;
import components.NMOS;
import components.PMOS;
import components.PowerNode;
import gui.camera.Camera;
import gui.command.CommandManager;
import gui.command.DeleteComponentCommand;
import gui.command.RotateComponentCommand;
import gui.render.ComponentRenderer;
import gui.render.GridRenderer;
import gui.render.WireRenderer;
import gui.selection.SelectionManager;
import gui.tools.PlaceComponentTool;
import gui.tools.SelectTool;
import gui.tools.ToolManager;
import gui.tools.WireTool;
import gui.command.DeleteWireCommand;
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
    private final WireRenderer wireRenderer;

    private final ToolManager toolManager;
    private final SelectionManager selectionManager;

    private final Circuit circuit;

    private final CommandManager commandManager;

    public CircuitCanvas(Camera camera, Circuit circuit){
        this.camera = camera;
        this.circuit = circuit;

        gridRenderer = new GridRenderer(camera);
        componentRenderer = new ComponentRenderer(camera);
        wireRenderer = new WireRenderer(camera);

        backgroundCanvas = new Canvas();

        selectionManager = new SelectionManager();
        toolManager = new ToolManager();
        commandManager = new CommandManager();

        toolManager.setCurrentTool(new SelectTool(), this);

        setupKeyboardControls();

        backgroundCanvas.widthProperty().bind(widthProperty());
        backgroundCanvas.heightProperty().bind(heightProperty());

        getChildren().add(backgroundCanvas);

        setFocusTraversable(true);

        sceneProperty().addListener((obs, oldScene, newScene) -> {
            if(newScene != null){
                requestFocus();
            }
        });

        ChangeListener<Number> resizeListener = (obs, oldVal, newVal) -> redraw();

        widthProperty().addListener(resizeListener);
        heightProperty().addListener(resizeListener);

        setOnMousePressed(event -> toolManager.mousePressed(event, this));
        setOnMouseReleased(event -> toolManager.mouseReleased(event, this));
        setOnMouseDragged(event -> toolManager.mouseDragged(event, this));
        setOnMouseMoved(event -> toolManager.mouseMoved(event, this));

        redraw();
    }

    private void setupKeyboardControls() {
        setOnKeyPressed(event -> {
            if((event.isControlDown() || event.isMetaDown()) && event.getCode() == KeyCode.Z){
                commandManager.undo();
                redraw();
                return;
            }

            if((event.isMetaDown() || event.isControlDown()) && event.isShiftDown() && event.getCode() == KeyCode.Z){
                commandManager.redo();
                redraw();
                return;
            }

            if((event.isControlDown() || event.isMetaDown()) && event.getCode() == KeyCode.Y){
                commandManager.redo();
                redraw();
                return;
            }

            if(event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.BACK_SPACE){

                Component selectedComponent =
                        selectionManager.getSelectedComponent();

                if(selectedComponent != null){

                    commandManager.execute(
                            new DeleteComponentCommand(
                                    circuit,
                                    selectedComponent
                            )
                    );

                    selectionManager.clearSelection();
                    redraw();
                    return;
                }

                Wire selectedWire =
                        selectionManager.getSelectedWire();

                if(selectedWire != null){

                    commandManager.execute(
                            new DeleteWireCommand(
                                    circuit,
                                    selectedWire
                            )
                    );

                    selectionManager.clearSelection();
                    redraw();
                    return;
                }
            }

            if(event.getCode() == KeyCode.DIGIT1){
                toolManager.setCurrentTool(new SelectTool(), this);
            }
            else if(event.getCode() == KeyCode.DIGIT2){
                toolManager.setCurrentTool(new PlaceComponentTool(pos -> new NMOS(pos)), this);
            }
            else if(event.getCode() == KeyCode.DIGIT3){
                toolManager.setCurrentTool(new PlaceComponentTool(pos -> new PMOS(pos)), this);
            }
            else if(event.getCode() == KeyCode.DIGIT4){
                toolManager.setCurrentTool(new PlaceComponentTool(pos -> new InputNode(pos)), this);
            }
            else if(event.getCode() == KeyCode.DIGIT5){
                toolManager.setCurrentTool(new PlaceComponentTool(pos -> new PowerNode(pos)), this);
            }
            else if(event.getCode() == KeyCode.DIGIT6){
                toolManager.setCurrentTool(new PlaceComponentTool(pos -> new GroundNode(pos)), this);
            }
            else if(event.getCode() == KeyCode.DIGIT7){
                toolManager.setCurrentTool(new WireTool(), this);
            }

            if(event.getCode() == KeyCode.R){
                Component selected = selectionManager.getSelectedComponent();

                if(selected != null){
                    commandManager.execute(
                            new RotateComponentCommand(
                                    selected,
                                    selected.getRotation(),
                                    selected.getRotation() + 90
                            )
                    );

                    redraw();
                }

                return;
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
        GraphicsContext gc = backgroundCanvas.getGraphicsContext2D();

        double width = backgroundCanvas.getWidth();
        double height = backgroundCanvas.getHeight();

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);

        gridRenderer.render(gc, width, height);

        for(Wire wire : circuit.getWires()){

            wireRenderer.drawWire(
                    gc,
                    wire.getStartPosition(),
                    wire.getEndPosition(),
                    width,
                    height
            );

            if(selectionManager.isSelected(wire)){
                wireRenderer.drawSelection(
                        gc,
                        wire.getStartPosition(),
                        wire.getEndPosition(),
                        width,
                        height
                );
            }
        }

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