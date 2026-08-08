package gui;

import circuit.Circuit;
import circuit.Net;
import circuit.Wire;
import components.Component;
import components.GroundNode;
import components.InputNode;
import components.NMOS;
import components.OutputNode;
import components.PMOS;
import components.PowerNode;
import gui.camera.Camera;
import gui.command.CommandManager;
import gui.command.DeleteComponentCommand;
import gui.command.DeleteWireCommand;
import gui.command.RotateComponentCommand;
import gui.palette.ToolPalette;
import gui.palette.PartPalette;
import gui.render.ComponentRenderer;
import gui.render.GridRenderer;
import gui.render.VoltageRenderer;
import gui.render.WireRenderer;
import gui.selection.SelectionManager;
import gui.tools.SelectTool;
import gui.tools.ToolManager;
import javafx.beans.value.ChangeListener;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import simulation.SimulationEngine;
import util.Vector2;

public class CircuitCanvas extends Pane {

    private final Canvas backgroundCanvas;

    private final Camera camera;
    private final GridRenderer gridRenderer;
    private final ComponentRenderer componentRenderer;
    private final WireRenderer wireRenderer;
    private final VoltageRenderer voltageRenderer;

    private final ToolManager toolManager;
    private final SelectionManager selectionManager;

    private final Circuit circuit;

    private final CommandManager commandManager;
    private final SimulationEngine simulationEngine;

    private final ToolPalette toolPalette;
    private final PartPalette partPalette;

    private double mouseX;
    private double mouseY;

    public CircuitCanvas(Camera camera, Circuit circuit){
        this.camera = camera;
        this.circuit = circuit;

        gridRenderer = new GridRenderer(camera);
        componentRenderer = new ComponentRenderer(camera);
        wireRenderer = new WireRenderer(camera);
        voltageRenderer = new VoltageRenderer(camera);

        toolPalette = new ToolPalette(this);
        partPalette = new PartPalette(this);

        simulationEngine = new SimulationEngine();

        backgroundCanvas = new Canvas();

        selectionManager = new SelectionManager();
        toolManager = new ToolManager();
        commandManager = new CommandManager();

        toolManager.setCurrentTool(new SelectTool(), this);

        setupKeyboardControls();

        backgroundCanvas.widthProperty().bind(widthProperty());
        backgroundCanvas.heightProperty().bind(heightProperty());

        getChildren().add(backgroundCanvas);
        getChildren().add(toolPalette.getCanvas());
        getChildren().add(partPallete.getCanvas());

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
                toolManager.mousePressed(event, this)
        );

        setOnMouseReleased(event ->
                toolManager.mouseReleased(event, this)
        );

        setOnMouseDragged(event -> {
            mouseX = event.getX();
            mouseY = event.getY();

            toolManager.mouseDragged(event, this);
        });

        setOnMouseMoved(event -> {
            mouseX = event.getX();
            mouseY = event.getY();

            toolManager.mouseMoved(event, this);
        });

        setOnScroll(event -> {
            mouseX = event.getX();
            mouseY = event.getY();

            if(event.getDeltaY() == 0){
                return;
            }

            double zoomFactor;

            if(event.getDeltaY() > 0){
                zoomFactor = 1.1;
            }
            else{
                zoomFactor = 1.0 / 1.1;
            }

            camera.zoomAt(
                    zoomFactor,
                    new Vector2(event.getX(), event.getY()),
                    getWidth(),
                    getHeight()
            );

            redraw();

            event.consume();
        });

        simulate();
        redraw();
    }

    private void setupKeyboardControls(){
        setOnKeyPressed(event -> {

            if((event.isControlDown() || event.isMetaDown())
                    && event.getCode() == KeyCode.Z){

                commandManager.undo();

                simulate();
                redraw();

                return;
            }

            if((event.isControlDown() || event.isMetaDown())
                    && event.getCode() == KeyCode.Y){

                commandManager.redo();

                simulate();
                redraw();

                return;
            }

            if(event.getCode() == KeyCode.DELETE
                    || event.getCode() == KeyCode.BACK_SPACE){

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

                    simulate();
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

                    simulate();
                    redraw();

                    return;
                }
            }

            if(event.getCode() == KeyCode.C){
                toolPalette.open(mouseX, mouseY);
                return;
            }

            if(event.getCode() == KeyCode.P){
                partPalette.open(mouseX, mouseY);
                return;
            }

            if(event.getCode() == KeyCode.R){

                Component selected =
                        selectionManager.getSelectedComponent();

                if(selected != null){

                    commandManager.execute(
                            new RotateComponentCommand(
                                    selected,
                                    selected.getRotation(),
                                    selected.getRotation() + 90
                            )
                    );

                    simulate();
                    redraw();
                }

                return;
            }
        });
    }

    public void simulate(){
        simulationEngine.simulate(circuit);
    }

    public SimulationEngine getSimulationEngine(){
        return simulationEngine;
    }

    public void stepSimulation(long timeStep){
        simulationEngine.step(timeStep, circuit);
        redraw();
    }

    public void resetSimulation(){
        simulationEngine.reset();
        simulate();
        redraw();
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

    public ToolManager getToolManager(){
        return toolManager;
    }

    public void redraw(){

        GraphicsContext gc =
                backgroundCanvas.getGraphicsContext2D();

        double width = backgroundCanvas.getWidth();
        double height = backgroundCanvas.getHeight();

        gc.setFill(Color.LIGHTGRAY);
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

            Net net =
                    circuit.getNet(wire.getStart());

            if(net != null){

                voltageRenderer.drawVoltage(
                        gc,
                        wire.getStartPosition(),
                        wire.getEndPosition(),
                        net.getVoltage(),
                        width,
                        height
                );
            }

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
                    circuit,
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
