package program;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class App2 extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button leftButton = new Button("Left Button");
        Button rightButton = new Button("Right Button");

        HBox hbox = new HBox(leftButton, rightButton);

        // Set HBox to fill the available width
        HBox.setHgrow(leftButton, javafx.scene.layout.Priority.ALWAYS);
        HBox.setHgrow(rightButton, javafx.scene.layout.Priority.ALWAYS);

        Scene scene = new Scene(hbox, 300, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}