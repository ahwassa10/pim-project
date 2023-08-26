package program;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Button button = new Button( "Button at PreferredSize This is a very long button");
        button.setMaxWidth(Double.MAX_VALUE);
        
        Button b2 = new Button("Small");
        b2.setMaxWidth(Double.MAX_VALUE);
        
        Button b3 = new Button("Other button that is very very very very long");
        b3.setMaxWidth(Double.MAX_VALUE);
        
        Button b4 = new Button("test button");
        b4.setMaxWidth(Double.MAX_VALUE);
        
        GridPane.setFillWidth(b2, true);
        GridPane.setFillWidth(b3, true);
        GridPane.setFillWidth(b4, true);
        
        GridPane root = new GridPane();
        root.add(button, 0, 0, 3, 1);
        root.add(b2, 0, 1);
        root.add(b3, 1, 1);
        root.add(b4, 2, 1);
        root.setHgap(5);
        root.setPadding(new Insets(5, 5, 5, 5));
        root.setGridLinesVisible(true);
        //root.setPrefWidth(Control.USE_PREF_SIZE);
        root.setMaxWidth(1400);
        
        ColumnConstraints cols = new ColumnConstraints();
        cols.setPercentWidth(30);
        root.getColumnConstraints().add(cols);
        root.getColumnConstraints().add(cols);
        root.getColumnConstraints().add(cols);
        
        VBox outer = new VBox(root);
        VBox.setVgrow(root, Priority.NEVER);
        
        Scene scene = new Scene(outer);
        primaryStage.setTitle("Java FX");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        System.out.println(root.getMaxWidth());
        System.out.println(root.getWidth());
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
