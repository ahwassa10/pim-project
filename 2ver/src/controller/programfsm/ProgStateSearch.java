package controller.programfsm;

import controller.photofsm.PhotContext;
import javafx.scene.layout.GridPane;
import model.Group;
import model.Photo;
import view.Messages;
import view.MyAlerts;

/**
 * Represents the state when the user has made a valid search query and the
 * program is now showing the results of that query.
 * This state extends ProgStateGroupOpened since they are essentially the same
 * state (we're just initializing the tiles using a different iterator, and
 * adding functionality to create a new group from the search results).
 */
public class ProgStateSearch extends ProgStateGroupOpened {

    /**
     * A series of steps to perform when the program enters into the search state:
     * 1) Set the scene to the group scene (the same scene as the group opened state)
     * 2) Show the name of the group that we are currently in.
     * 3) Disable the photo interaction (slideshow, add photo) and search functionality.
     * 4) Enable the search results functionality (exit out of the search, or create a new group from the results).
     * 5) Display the query that resulted in these search results.
     * 6) Initialize the new group name text field
     * 7) Load the photos from the search results into the flowpane.
     */
    void enter() {
        progContext.primaryStage.setScene(progContext.groupScene);
        progContext.groupController.groupNameLabel.setText("Group name: " + groupSelected.getName());
        progContext.groupController.photoInteractionFunctionality.setVisible(false);
        progContext.groupController.searchPhotoFunctionality.setVisible(false);
        progContext.groupController.searchResultsFunctionality.setVisible(true);
        progContext.groupController.searchQueryLabel.setText(searchQuery);
        progContext.groupController.newGroupTextField.setText("");
        loadTiles(groupSelected.searchPhotos(searchResults));
    }

    /**
     * Implements the functionality where the user can create a new group from
     * the search results.
     * Performs input validation and maintains the user class invariants by making
     * sure that the user doesn't already contain an group with this name. Displays
     * appropriate error messages depending on what the user entered into the
     * text field.
     *
     * @return The next state to transition to.
     */
    ProgState createGroupFromSearch() {
        String groupName = progContext.groupController.newGroupTextField.getText();
        if (groupName == null || groupName.equals("")) {
            MyAlerts.basicError(progContext.primaryStage, Messages.enterNewGroupName);
            return this;
        }

        if (!Group.isValidGroupName(groupName)) {
            MyAlerts.basicError(progContext.primaryStage, Messages.invalidGroupName);
            return this;
        }

        if (user.groupNameInUse(groupName)) {
            MyAlerts.basicErrorMessage(progContext.primaryStage,
                                       Messages.groupNameInUseHeader,
                                       String.format(Messages.groupNameInUseContent, groupName));
            return this;
        }
        Group newGroup = new Group(groupName, groupSelected.searchPhotos(searchResults));
        user.add(newGroup);
        return progContext.groupOpenedState;
    }

    /**
     * The event handler for the search state.
     * This state has limited functionality. The user can either exit out of the
     * search or create a new group from the search results.
     *
     * @return the next event to transition to.
     */
    ProgState processEvent() {
        Object source = progContext.lastEvent.getSource();

        if (source == progContext.groupController.exitSearchButton) {
            return progContext.groupOpenedState;
        } else if (source == progContext.groupController.groupFromSearchButton) {
            return createGroupFromSearch();
        } else if (source == progContext.groupController.goBackToGroupsButton) {
            return progContext.userState;
        } else if (source instanceof GridPane) {
            GridPane tile = (GridPane) source;
            String photoId = tile.getId();
            if (!groupSelected.photoIsPresent(photoId)) {
                MyAlerts.basicError(progContext.primaryStage,
                        Messages.unexpectedError);
            } else {
                Photo selectedPhoto = groupSelected.getPhotoByFilePath(photoId);
                PhotContext newWindow = new PhotContext(user, groupSelected, selectedPhoto);
                newWindow.start();
                return this;
            }
        }
        return this;
    }

}
