package program;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App4 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        
        ImageView imw = new ImageView(new Image("file:data/icon.png", 0, 0, true, true));
        imw.setPreserveRatio(true);
        
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(imw);
        
        Pane pane = new Pane();
        pane.getChildren().add(stackPane);
        
        imw.fitHeightProperty().bind(stackPane.heightProperty());
        imw.fitWidthProperty().bind(stackPane.widthProperty());
        
        gridPane.add(stackPane, 0, 0);
        GridPane.setConstraints(stackPane, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        
        Scene scene = new Scene(gridPane, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        stackPane.prefHeightProperty().addListener(d -> System.out.println(d));
        stackPane.heightProperty().addListener(d -> System.out.println(d));
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}