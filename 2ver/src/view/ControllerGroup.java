package view;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

/**
 * FXML controller for Group view which sends events to progContext
 * 
 * @see controller.programfsm.ProgContext
 */
public class ControllerGroup extends Controller{
	@FXML
	public FlowPane photoFlowPane;

	@FXML
	public Button goBackToGroupsButton;

	@FXML
	public Label groupNameLabel;

	@FXML
	public GridPane searchPhotoFunctionality;

	@FXML
	public TextField tagQueryTextField;

	@FXML
	public Button searchButton;

	@FXML
	public DatePicker fromDate;

	@FXML
	public DatePicker toDate;

	@FXML
	public GridPane photoInteractionFunctionality;

	@FXML
	public Button openSlideshowButton;

	@FXML
	public Button addPhotoButton;

	@FXML
	public GridPane searchResultsFunctionality;

	@FXML
	public Label searchQueryLabel;

	@FXML
	public Button exitSearchButton;

	@FXML
	public TextField newGroupTextField;

	@FXML
	public Button groupFromSearchButton;

	@FXML
	private void handleEvent(Event event)
	{
		progContext.processEvent(event);
	}
}
