package view;

import controller.photofsm.PhotContext;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.old.Tag;

/**
 * FXML controller for user view which sends events to photContext
 * 
 * @see controller.photofsm.PhotContext
 */
public class ControllerPhoto {
    private PhotContext photContext;
    
    @FXML
    public ScrollPane photoImagePane;
    
    @FXML
    public ImageView photoImageView;

    @FXML
    public TextField captionNameTextField;

    @FXML
    public Button editCaptionButton;

    @FXML
    public ListView<Tag> tagListView;

    @FXML
    public Label photoDateLabel;

    @FXML
    public GridPane createTagFunctionality;

    @FXML
    public ComboBox<String> tagSelectComboBox;

    @FXML
    public TextField newTagValueTextField;

    @FXML
    public Button addTagButton;

    @FXML
    public Button copyToGroupButton;

    @FXML
    public Button moveToGroupButton;

    @FXML
    public Button deleteFromGroupButton;

    @FXML
    public GridPane deleteTagFunctionality;

    @FXML
    public Label selectedTagTextField;

    @FXML
    public Button cancelDeleteButton;

    @FXML
    public Button deleteTagButton;

    /**
	 * Event handler for actions in photo-viewing susbsystem.
	 * @param event javafx Event to be handled
	 */
    @FXML
    private void handleEvent(Event event) {
        photContext.processEvent(event);
    }

    /**
     * Set the current photContext for controller.
     * @param photContext The PhotContext to switch to.
     */
    public void setContext(PhotContext photContext) {
        this.photContext = photContext;
    }
}
