package gui;

import circuit.Circuit;
import gui.camera.Camera;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainWindow {

    // Window dimensioning
    private static final String WINDOW_TITLE = "Circuit Simulator";
    private static final double DEFAULT_WIDTH = 1400;
    private static final double DEFAULT_HEIGHT = 900;

    // Clock configuration
    private static final double CLOCK_INTERVAL_MILLISECONDS = 100;

    // FX objects
    private final Stage stage;
    private final BorderPane root;
    private final Scene scene;
    private final Camera camera;
    private final CircuitCanvas circuitCanvas;

    private final Circuit circuit;

    private final Label clockLabel;
    private final TextField stepField;

    private final Button startStopButton;
    private final Button stepButton;
    private final Button resetButton;

    private final Timeline clock;

    private boolean clockRunning;

    public MainWindow(Stage stage){
        this.stage = stage;

        root = new BorderPane();
        scene = new Scene(
                root,
                DEFAULT_WIDTH,
                DEFAULT_HEIGHT
        );

        camera = new Camera();
        circuit = new Circuit();

        circuitCanvas = new CircuitCanvas(
                camera,
                circuit
        );

        clockLabel = new Label("Time: 0");

        stepField = new TextField("10");
        stepField.setPrefWidth(70);

        startStopButton = new Button("Start");
        stepButton = new Button("Step");
        resetButton = new Button("Reset");

        clockRunning = false;

        clock = new Timeline(
                new KeyFrame(
                        Duration.millis(
                                CLOCK_INTERVAL_MILLISECONDS
                        ),
                        event -> advanceClock()
                )
        );

        clock.setCycleCount(Timeline.INDEFINITE);

        startStopButton.setOnAction(event ->
                toggleClock()
        );

        stepButton.setOnAction(event ->
                stepClock()
        );

        resetButton.setOnAction(event ->
                resetClock()
        );

        stage.setTitle(WINDOW_TITLE);
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(600);

        initializeLayout();

        updateClockLabel();
    }

    private void initializeLayout(){

        HBox clockControls = new HBox(
                10,
                startStopButton,
                stepButton,
                resetButton,
                clockLabel,
                new Label("Step:"),
                stepField
        );

        clockControls.setPadding(
                new Insets(8)
        );

        root.setTop(clockControls);
        root.setCenter(circuitCanvas);
    }

    private void toggleClock(){

        if(clockRunning){
            stopClock();
        }
        else{
            startClock();
        }
    }

    private void startClock(){

        if(clockRunning){
            return;
        }

        clockRunning = true;
        startStopButton.setText("Stop");

        clock.play();
    }

    private void stopClock(){

        if(!clockRunning){
            return;
        }

        clockRunning = false;
        startStopButton.setText("Start");

        clock.stop();
    }

    private void advanceClock(){

        long step = getStepSize();

        circuitCanvas.stepSimulation(step);

        updateClockLabel();
    }

    private void stepClock(){

        long step = getStepSize();

        circuitCanvas.stepSimulation(step);

        updateClockLabel();
    }

    private void resetClock(){

        stopClock();

        circuitCanvas.resetSimulation();

        updateClockLabel();
    }

    private long getStepSize(){

        try{
            long value = Long.parseLong(
                    stepField.getText().trim()
            );

            if(value <= 0){
                return 1;
            }

            return value;
        }
        catch(NumberFormatException exception){
            return 1;
        }
    }

    private void updateClockLabel(){

        clockLabel.setText(
                "Time: "
                        + circuitCanvas
                        .getSimulationEngine()
                        .getCurrentTime()
        );
    }

    public void show(){
        stage.show();
    }
}
