package program;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public final class PIMLibrary extends Application {
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/program/layout.fxml"));
        GridPane userRoot = loader.load();
        userRoot.setGridLinesVisible(true);
        Controller controller = loader.getController();
        Scene scene = new Scene(userRoot);
        
        
        primaryStage.setTitle("PIM 2ver");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void stop() {}
    
    public static void main(String[] args) {
        launch(args);
    }
}
