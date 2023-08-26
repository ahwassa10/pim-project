package program;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

public class Controller {
    
    @FXML
    private ListView<String> list;
    
    @FXML
    private TilePane pane;
    
    private ObservableList<String> tags = FXCollections.observableArrayList("all", "tech", "car", "ai", "school");
    
    public void fillWithImages(Path imageDir) {
        try (DirectoryStream<Path> images = Files.newDirectoryStream(imageDir)) {
            for (Path imageFile : images) {
                Image image = new Image(Files.newInputStream(imageFile), 100, 100, true, true);
                ImageView view = new ImageView(image);
                
                pane.getChildren().add(view);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void fillWithTags() {
        list.setItems(tags);
        list.getSelectionModel().selectFirst();
    }
}
