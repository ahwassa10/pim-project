package program;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * FXML controller for user view which sends events to progContext
 * 
 * @see controller.programfsm.ProgContext
 */
public class Controller {
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
    public TextField textField;
    
    @FXML
    public FlowPane entityPane;
    
    /**
     * Event handler for actions in user state.
     * @param event javafx Event to be handled
     */
    @FXML
    public void handleEvent(Event event) {
        System.out.println("Must handle event");
    }
}