package gui;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
public class MainWindow {
    //window dimensioning
    private static final String WINDOW_TITLE = "Circuit Simulator";
    private static final double DEFAULT_WIDTH = 1400; //such good conventional naming!
    private static final double DEFAULT_HEIGHT = 900;

    //fx objects
    private final Stage stage;
    private final BorderPane root;
    private final Scene scene;
    public MainWindow(Stage stage){
        this.stage = stage;
        root = new BorderPane();
        scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);

        stage.setTitle(WINDOW_TITLE);
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(600);

        initializeLayout();
    }
    private void initializeLayout(){
        CircuitCanvas canvas = new CircuitCanvas();
        root.setCenter(canvas);
    }
    public void show(){
        stage.show();
    }
}