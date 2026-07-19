package simulator;

import gui.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;
public class Main extends Application{
    public static void main(String[] args){
        launch(args);
    }
    public void start(Stage primaryStage){
        MainWindow window = new MainWindow(primaryStage);
        window.show();
    }
}