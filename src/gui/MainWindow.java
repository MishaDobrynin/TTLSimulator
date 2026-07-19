package gui;
import gui.camera.Camera;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
public class MainWindow {
    //window dimensioning
    private static final String WINDOW_TITLE = "Circuit Simulator";
    private static final double DEFAULT_WIDTH = 1400;
    private static final double DEFAULT_HEIGHT = 900;

    //fx objects
    private final Stage stage;
    private final BorderPane root;
    private final Scene scene;
    private final Camera camera;
    private final CircuitCanvas circuitCanvas;
    
    public MainWindow(Stage stage){
        this.stage = stage;

        root = new BorderPane();
        scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        camera = new Camera();

        this.circuitCanvas = new CircuitCanvas(camera);
        
        stage.setTitle(WINDOW_TITLE);
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(600);

        initializeLayout();
    }
    private void initializeLayout(){
        root.setCenter(this.circuitCanvas);
    }
    public void show(){
        stage.show();
    }
}
