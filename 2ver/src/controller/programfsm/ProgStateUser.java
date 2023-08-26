package controller.programfsm;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.Group;
import view.ControllerGroupTile;
import view.Messages;
import view.MyAlerts;

import java.io.IOException;
import java.util.Iterator;

/**
 * Represents the state of the program when a user has initially logged in.
 * While in this state, the user's groups are dynamically loaded into the
 * flowpane and the user has the ability to create a new group. For other
 * group interactions (deleting an group, opening an group, etc), the user
 * must select an group from the flowpane.
 */
public class ProgStateUser extends ProgState {

	/**
	 * A series of steps to perform when the program enters into the user state:
	 * 1) Set the scene to the userScene
	 * 2) Disable the group selected functionality (we haven't selected an group yet).
	 * 3) Initializes the new group text field
	 * 4) Dynamically loads the group tiles into the flowpane.
	 */
	void enter() {
		progContext.primaryStage.setScene(progContext.userScene);
		progContext.userController.selectedGroupFunctionality.setVisible(false);
		progContext.userController.newGroupTextField.setText("");
		
		loadGroupTiles();
		progContext.primaryStage.show();
	}

	/**
	 * This method dynamically loads a user's groups into the flow pane by
	 * generating a tile for each group.
	 */
	void loadGroupTiles() {
		progContext.userController.groupFlowPane.getChildren().clear();
		Iterator<Group> groups = user.iterator();

		while (groups.hasNext()) {
			Group group = groups.next();

			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/fxml/layout_groupTile.fxml"));
				GridPane root = loader.load();
				ControllerGroupTile controller = loader.getController();
				root.setId(group.getName());
				controller.groupNameLabel.setText(group.getName());
				if (group.size() == 1) {
					controller.numPhotosLabel.setText("1 Photo");
				} else {
					controller.numPhotosLabel.setText(group.size() + " Photos");
				}

				root.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						progContext.processEvent(e);
					}
				});

				progContext.userController.groupFlowPane.getChildren().add(root);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Implements the functionality to create a new group given an group name.
	 * This method performs input validation and ensures that the user class
	 * invariants are not violated (checks that the group name isn't a
	 * duplicate). An appropriate error message is displayed depending
	 * on what the user has entered.
	 *
	 * @return always returns a ProgStateUser object.
	 */
	ProgState createNewGroup() {
		String newGroupName = progContext.userController.newGroupTextField.getText();

		if (!Group.isValidGroupName(newGroupName)) {
			MyAlerts.basicError(progContext.primaryStage, Messages.noInputError);
			return this;
		}

		if (user.groupNameInUse(newGroupName)) {
			MyAlerts.basicErrorMessage(progContext.primaryStage,
					                   Messages.groupNameInUseHeader,
					                   String.format(Messages.groupNameInUseContent, newGroupName));
		} else {
			Group newGroup = new Group(newGroupName);
			user.add(newGroup);
		}

		progContext.userController.newGroupTextField.setText("");
		return this;
	}

	/**
	 * The event handler for the user state.
	 * When the user clicks on an group tile, the event handler will return
	 * an group selected state.
	 *
	 * @return the next state to transition to.
	 */
	ProgState processEvent() {
		Object source = progContext.lastEvent.getSource();

		if (source == progContext.userController.createNewGroupButton) {
			return createNewGroup();
		} else if (source instanceof GridPane) {
			GridPane gp = (GridPane) source;
			String groupNameSelected = gp.getId();
			if (!user.groupNameInUse(groupNameSelected)) {
				MyAlerts.basicError(progContext.primaryStage,
						            Messages.unexpectedError);
			} else {
				groupSelected = user.getByGroupName(gp.getId());
				return progContext.groupSelectedState;
			}
		}

		return this;
	}
}
