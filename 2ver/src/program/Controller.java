package program;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.entity.Content;
import model.entity.Tag;

/**
 * FXML controller for user view which sends events to progContext
 * 
 * @see controller.programfsm.ProgContext
 */
public class Controller {
    private final Program program;
    
    private Content selectedContent;
    private Label selectedLabel;
    
    private void selectContent(Content selectedContent, Label selectedLabel) {
        Objects.requireNonNull(selectedContent);
        Objects.requireNonNull(selectedLabel);
        this.selectedContent = selectedContent;
        
        if (this.selectedLabel != null) {
            this.selectedLabel.setBorder(null);
        }
        
        this.selectedLabel = selectedLabel;
        
        selectedLabel.setBorder(new Border(new BorderStroke(Color.BLACK, 
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        
        this.deleteButton.setDisable(false);
    }
    
    private void unselectAll() {
        selectedContent = null;
        if (selectedLabel != null) {
            selectedLabel.setBorder(null);
        }
        this.deleteButton.setDisable(true);
    }
    
    private void removeSelection() {
        if (selectedContent == null) {
            throw new IllegalStateException("Remove selection called when selectedContent=null");
        }
        program.removeContent(selectedContent);
        selectedContent = null;
        entityPane.getChildren().remove(selectedLabel);
        this.deleteButton.setDisable(true);
    }
   
    @FXML
    public Button homeButton;
    
    @FXML
    public VBox savedLocations;
    
    @FXML
    public Button contentButton;
    
    @FXML
    public Button tagsButton;
    
    @FXML
    public GridPane topBar;
    
    @FXML
    public Button createContent;
    
    @FXML
    public Button createTag;
    
    @FXML
    public Button deleteButton;
    
    @FXML
    public TextField textField;
    
    @FXML
    public FlowPane entityPane;
    
    @FXML
    public void handleCreateContent(ActionEvent event) {
        program.createContent();
    }
    
    @FXML
    public void handleContent(ActionEvent event) {
        entityPane.getChildren().clear();
        unselectAll();
        
        Random r = new Random();
        
        List<Content> content = program.getContent();
        for (Content contentInstance : content) {
            Label label = new Label(contentInstance.getName() + " " + contentInstance.getContentKey().toString().substring(9, 13));
            label.setUserData(contentInstance);
            label.setMinHeight(100);
            label.setMaxHeight(100);
            label.setMinWidth(100);
            label.setMaxWidth(100);
            
            Color randomColor = Color.rgb(r.nextInt(100, 256), r.nextInt(100, 256), r.nextInt(100, 256));
            
            label.setBackground(new Background(new BackgroundFill(randomColor, CornerRadii.EMPTY, Insets.EMPTY)));
            
            label.setOnMouseClicked((MouseEvent me) -> {
                Label clickedLabel = (Label) me.getSource();
                Content clickedContent = (Content) clickedLabel.getUserData();
                
                System.out.println(clickedContent.getContentKey());
                this.selectContent(clickedContent, clickedLabel);
            });
            
            entityPane.getChildren().add(label);
        }
    }
    
    @FXML
    public void handleCreateTag(ActionEvent event) {
        program.createTag();
    }
    
    public void handleTags(ActionEvent event) {
        entityPane.getChildren().clear();
        unselectAll();
        
        Random r = new Random();
        
        List<Tag> tags = program.getTags();
        for (Tag tag : tags) {
            Label label = new Label(tag.getName());
            label.setMinHeight(100);
            label.setMaxHeight(100);
            label.setMinWidth(100);
            label.setMaxWidth(100);
            
            Color randomColor = Color.rgb(r.nextInt(100, 256), r.nextInt(100, 256), r.nextInt(100, 256));
            label.setBackground(new Background(new BackgroundFill(randomColor, CornerRadii.EMPTY, Insets.EMPTY)));
            
            entityPane.getChildren().add(label);
        }
    }
    
    @FXML
    public void handleDelete(ActionEvent event) {
        removeSelection();
    }
    
    public Controller(Program program) {
        Objects.requireNonNull(program);
        this.program = program;
    }
}