package controller.programfsm;

import java.util.Optional;

import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import model.Group;
import view.Messages;
import view.MyAlerts;

/**
 * Represents the state of the program when a user selects an group.
 * After an group is selected, its name can be edited, or the group itself
 * can be deleted. This state extends ProgStateUse since both states share a
 * large amount of functionality.
 * Once an group is selected, we can open it to show the photos inside (ProgStateGroupOpened).
 */
public class ProgStateGroupSelected extends ProgStateUser {

    /**
     * A series of steps to perform when the program enters into the group selected state:
     * 1) Set the scene to the userScene (the same scene as the user state)
     * 2) Turn on the selected group functionality (shows the edit name and delete buttons).
     * 3) Initialized the edit name field.
     * 4) Sets the group name label to the current name of the group.
     * 5) Loads all the groups into the flowpane.
     */
    void enter() {
        progContext.primaryStage.setScene(progContext.userScene);
        progContext.userController.selectedGroupFunctionality.setVisible(true);
        progContext.userController.newGroupTextField.setText("");
        progContext.userController.newGroupNameTextField.setText(groupSelected.getName());
        loadGroupTiles();
        progContext.primaryStage.show();
    }

    /**
     * Functionality to edit an group name.
     * This method performs input validation, maintains the class invariants of the User
     * object (duplicate group names are not allowed), and displays error messages if
     * necessary.
     *
     * @return always returns a ProgStateGroupSelected object.
     */
    ProgState editGroupName() {
        String newGroupName = progContext.userController.newGroupNameTextField.getText();

        if (!Group.isValidGroupName(newGroupName)) {
            MyAlerts.basicError(progContext.primaryStage,
                                Messages.invalidGroupName);

        } else if (newGroupName.equals(groupSelected.getName())) {
            MyAlerts.basicError(progContext.primaryStage,
                                Messages.enterNewGroupName);

        } else if (user.groupNameInUse(newGroupName)) {
            MyAlerts.basicErrorMessage(progContext.primaryStage,
                                       Messages.groupNameInUseHeader,
                                       Messages.groupNameInUseContent);

        } else {
            user.changeGroupName(groupSelected, newGroupName);
        }

        return this;
    }

    /**
     * Functionality to delete an group.
     * The user will have to confirm the deletion before the group is removed
     * from the user class. If deleted, we transition back to the user state.
     * Otherwise, we remain in this state.
     *
     * @return the next state to transition to.
     */
    ProgState deleteGroup() {
        if (groupSelected == null || !user.contains(groupSelected)) {
            MyAlerts.basicError(progContext.primaryStage, Messages.unexpectedError);
            return this;
        }

        Optional<ButtonType> response =
            MyAlerts.confirmDelete(progContext.primaryStage,
                                   Messages.deleteGroupHeader,
                                   String.format(Messages.deleteGroupContent, groupSelected));

        if (response.isPresent() && response.get() == ButtonType.OK) {
            user.remove(groupSelected);
            return progContext.userState;
        }

        return this;
    }

    /**
     * Implements the event handler for the group selected state.
     * This event handler looks very similar to the one for ProgStateUser,
     * except the ability to change the group name, delete the group, and
     * open the group are implemented. Note that we can select another group
     * while in this state (we transition to the same state, but the group
     * selected is different).
     *
     * @return the next state to transition to.
     */
    ProgState processEvent() {
        Object source = progContext.lastEvent.getSource();

        if (source == progContext.userController.createNewGroupButton) {
            return createNewGroup();

        } else if (source == progContext.userController.openGroupButton) {
            return progContext.groupOpenedState;

        } else if (source == progContext.userController.editGroupNameButton) {
            return editGroupName();

        } else if (source == progContext.userController.deleteGroupButton) {
            return deleteGroup();

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
