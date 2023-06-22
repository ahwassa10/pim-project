package program;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;

public class Controller {
    
    @FXML
    private TilePane pane;
    
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
}
