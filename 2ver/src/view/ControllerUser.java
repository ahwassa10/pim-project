package view;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

/**
 * FXML controller for user view which sends events to progContext
 * 
 * @see controller.programfsm.ProgContext
 */
public class ControllerUser extends Controller {
    @FXML
    public FlowPane groupFlowPane;

    @FXML
    public GridPane selectedGroupFunctionality;

    @FXML
    public Button openGroupButton;

    @FXML
    public TextField newGroupNameTextField;

    @FXML
    public Button editGroupNameButton;

    @FXML
    public Button deleteGroupButton;

    @FXML
    public TextField newGroupTextField;

    @FXML
    public Button createNewGroupButton;
	
	/**
	 * Event handler for actions in user state.
	 * @param event javafx Event to be handled
	 */
    @FXML
    public void handleEvent(Event event) {
        progContext.processEvent(event);
    }
}
