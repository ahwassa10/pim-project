package controller.programfsm;

import controller.photofsm.PhotContext;
import controller.slideshowfsm.SlideshowContext;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import model.Photo;
import model.PhotoFilter;
import view.ControllerPhotoTile;
import view.Messages;
import view.MyAlerts;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents the state of the program when the user opens an group (displaying
 * all the photos inside the group).
 */
public class ProgStateGroupOpened extends ProgState {
    /**
     * Performs a series of steps when the program transitions to the group opened state:
     * 1) Sets the scene to the groupScene
     * 2) Changes the label to display the opened group's name.
     * 3) Enables the search photo and photo interaction (slideshow and adding photo) functionality.
     * 4) Disables the search results functionality since we are not in that state.
     * 5) Initializes the date pickers and the query box.
     * 6) Loads the photos into the flowpane.
     */
    void enter() {
        progContext.primaryStage.setScene(progContext.groupScene);
        progContext.groupController.groupNameLabel.setText("Group name: " + groupSelected.getName());
        progContext.groupController.searchPhotoFunctionality.setVisible(true);
        progContext.groupController.photoInteractionFunctionality.setVisible(true);
        progContext.groupController.searchResultsFunctionality.setVisible(false);
        progContext.groupController.fromDate.setValue(null);
        progContext.groupController.toDate.setValue(null);
        progContext.groupController.tagQueryTextField.setText("");
        loadTiles(groupSelected.iterator());
        progContext.primaryStage.show();
    }

    /**
     * Creates an ExtentionFilter used by the Javafx file selection dialog.
     * The dialog will ask the user to pick a file from the file system that
     * matches these extensions.
     *
     * @return an ExtensionFilter object containing photo file extensions.
     */
    public static FileChooser.ExtensionFilter getValidExtensionFilter()
    {
        List<String> extensions = new ArrayList<String>();
        for(String ext : Photo.getValidExtensions())
        {
            extensions.add("*." + ext);
        }
        return new FileChooser.ExtensionFilter("Image Files", extensions);
    }

    /**
     * Given a photo iterator, this method will load thumbnails and captions
     * of the photos into the flowpane (displaying the thumbnails to the user).
     *
     * @param photos The source of photos to use.
     */
    void loadTiles(Iterator<Photo> photos) {
        progContext.groupController.photoFlowPane.getChildren().clear();

        while (photos.hasNext()) {
            Photo photo = photos.next();

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/fxml/layout_photoTile.fxml"));
                GridPane root = loader.load();
                ControllerPhotoTile controller = loader.getController();
                root.setId(photo.getBacking().toString());
                controller.tileImageview.setImage(new Image(photo.getBacking().toURI().toString(),
                        120, 120, true, true));
                controller.tileLabel.setText(photo.getCaption());

                root.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        progContext.processEvent(e);
                    }
                });

                progContext.groupController.photoFlowPane.getChildren().add(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The functionality to add a photo to the group using the system provided file picker.
     * This method ensures that the photo file is valid and maintains the class invariants
     * of the Group (duplicates are not allowed). It also handles any errors that may occur.
     *
     * @return always returns a ProgStateGroupOpened object.
     */
    ProgState addPhoto() {
        //TODO: Create file dialog
        //Create Photo object from file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Photo");
        fileChooser.getExtensionFilters().add(getValidExtensionFilter());

        File photoFile = fileChooser.showOpenDialog(progContext.primaryStage);

        if (photoFile == null || !Photo.isValidPhotoFile(photoFile)) {
            MyAlerts.basicError(progContext.primaryStage,
                                       Messages.invalidPhotoHeader);

            return this;
        }

        if (groupSelected.photoIsPresent(photoFile.toString())) {
            MyAlerts.basicErrorMessage(progContext.primaryStage,
                                       Messages.duplicatePhotoHeader,
                                       String.format(Messages.duplicatePhotoContent, photoFile.toString()));
        } else {
            groupSelected.add(new Photo(photoFile));
        }
        return this;
    }

    /**
     * Implements the functionality to search for a photo in the group.
     * This method attempts to generate a PhotoFilter object using the inputs
     * into the date pickers and text field. If the search criteria are invalid,
     * the method will display an appropriate error. If the search criteria are
     * valid, a PhotoFilter object will be created and the PhotStateSearch object
     * will be returned (transitioning the program into a state that displays the
     * search results).
     *
     * @return Either the same state (if the search criteria are invalid), or a PhotStateSearch object.
     */
    ProgState search() {
        LocalDate startDate = progContext.groupController.fromDate.getValue();
        LocalDate endDate = progContext.groupController.toDate.getValue();
        String query = progContext.groupController.tagQueryTextField.getText();

        if ((startDate == null && endDate != null) ||
            (startDate != null && endDate == null) ||
            (startDate != null && endDate != null && startDate.isAfter(endDate))) {

            MyAlerts.basicError(progContext.primaryStage,
                                Messages.invalidDate);

            return this;
        }

        if (query != null &&
            !query.equals("") &&
            !PhotoFilter.isValid(query)) {

            MyAlerts.basicErrorMessage(progContext.primaryStage,
                                       Messages.invalidQueryHeader,
                                       String.format(Messages.invalidQueryContent, query));

            return this;
        }

        searchResults = new PhotoFilter(startDate, endDate, query);
        StringBuilder sb = new StringBuilder();
        sb.append("Start Date: ");
        sb.append(startDate == null ? "NA" : startDate.toString());
        sb.append("\nEnd Date: ");
        sb.append(endDate == null ? "NA" : endDate.toString());
        sb.append("\nQuery: ");
        sb.append(query == null || query.equals("") ? "NA" : query);
        searchQuery = sb.toString();

        return progContext.searchState;
    }

    /**
     * Implements the event handler for the group opened state.
     * This state can transition to a number of different states depending
     * on how the user interacts with it.
     * If the user clicks on a photo, a PhotContext will be created. The PhotContext
     * is an entirely new finite state machine displayed in a separate window, representing
     * a branch in the program.
     *
     * @return The state to transition to.
     */
    ProgState processEvent() {
        Object source = progContext.lastEvent.getSource();

        if (source == progContext.groupController.goBackToGroupsButton) {
            return progContext.userState;

        } else if (source == progContext.groupController.addPhotoButton) {
            return addPhoto();

        } else if (source == progContext.groupController.searchButton) {
            return search();

        } else if (source == progContext.groupController.openSlideshowButton) {
            if (groupSelected.size() == 0) {
                MyAlerts.basicError(progContext.primaryStage,
                                    Messages.emptyGroupSlideshow);
                return this;
            }
            SlideshowContext slideshow = new SlideshowContext(groupSelected);
            slideshow.start();

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
