package program;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App3 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        
        ImageView imw = new ImageView(new Image("file:data/icon.png", 0, 0, true, true));
        imw.setPreserveRatio(true);
        
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(imw);
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(stackPane);
        
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        
        scrollPane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue,
                    Number newValue) {
                double newHeight = newValue.doubleValue() - 10;
                imw.setFitHeight(newHeight);
            }
        });
        
        scrollPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue,
                    Number newValue) {
                double newWidth = newValue.doubleValue() - 10;
                imw.setFitWidth(newWidth);
            }
        });
        
        //imw.fitHeightProperty().bind(scrollPane.heightProperty());
        //imw.fitWidthProperty().bind(scrollPane.widthProperty());
        
        gridPane.add(scrollPane, 0, 0);
        GridPane.setConstraints(scrollPane, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        
        Scene scene = new Scene(gridPane, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        scrollPane.prefHeightProperty().addListener(d -> System.out.println(d));
        scrollPane.heightProperty().addListener(d -> System.out.println(d));
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
