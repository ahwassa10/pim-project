package controller.photofsm;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Group;
import model.Photo;
import model.Tag;
import model.Garden;
import view.ControllerPhoto;

import java.io.File;
import java.io.IOException;

/**
 * ProgContext controls the finite state machine of the photo viewing sub-system system.
 * The program is implemented using the state design pattern. Therefore,
 * we have the PhotContext class, which stores the current context of the
 * system, an abstract PhotState class, and a number of concrete classes
 * that extend PhotState to implement the various states of the system.
 */
public class PhotContext {
	/**
     * The javafx stage that corresponds to the photo viewer.
     */
	Stage stage;

	/**
     * The scene that corresponds to when a user clicks to view an image.
     */
    Scene photoScene;

    /**
     * The controller for the photo-viewing scene.
     */
    ControllerPhoto photoController;

    /**
     * The state of the program when the user views a photo.
     */
    PhotStatePhoto photoState;

    /**
     * The state of the program when a user selects a tag.
     */
    PhotStateTagSelected tagSelectedState;

    /**
     * The state of the program when a the photo-viewer must exit its current view.
     */
    PhotStateExit exitState;

    /**
     * The most recent event triggered by the javafx framework.
     */
    Event lastEvent;

    /**
     * The currently selected tag.
     */
    Tag tagSelected;

    /**
     * The state that the photo-viewer is currently in.
     */
    private PhotState currentState;

    /**
     * A reference to the garden.
     */
    private Garden garden;

    /**
     * The state that the program is currently in.
     */
    private Group group;

    /**
     * The currently viewed photo.
     */
    private Photo photo;

    /**
     * Creates a new PhotContext object, and initializes the viewer.
     * The photo-viewing scene, controller, and states are created.
     *
     * @param user The user currently using the photo-viewer
     * @param group The group of the currently viewed photo
     * @param photo The photo currently being viewed 
     */
    public PhotContext(Garden user, Group group, Photo photo) {
        if (user == null ||
            group == null ||
            photo == null ||
            !user.contains(group) ||
            !group.contains(photo)
        ) {
            throw new IllegalArgumentException("Cannot create photo context");
        }

        this.garden = user;
        this.group = group;
        this.photo = photo;

        this.stage = new Stage();
        stage.setTitle("Viewing Photo");
        stage.getIcons().add(new Image(new File("data/icon2.png").toURI().toString()));

        try {
            FXMLLoader photoLoader = new FXMLLoader();
            photoLoader.setLocation(getClass().getResource("/view/fxml/layout_photo.fxml"));
            GridPane photoRoot = photoLoader.load();
            
            photoController = photoLoader.getController();
            photoController.setContext(this);
            
            photoController.photoImagePane.heightProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue,
                        Number newValue) {
                    double newHeight = newValue.doubleValue() - 10;
                    photoController.photoImageView.setFitHeight(newHeight);
                }
            });
            
            photoController.photoImagePane.widthProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue,
                        Number newValue) {
                    double newWidth = newValue.doubleValue() - 10;
                    photoController.photoImageView.setFitWidth(newWidth);
                }
            });
            
            photoScene = new Scene(photoRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }

        photoState = new PhotStatePhoto(this);
        tagSelectedState = new PhotStateTagSelected(this);
        exitState = new PhotStateExit(this);

        // Prevent unnecessary reloading of image and date. This fsm only has one scene.
        stage.setScene(photoScene);
        photoController.photoImageView.setImage(new Image(photo.getBacking().toURI().toString()));
        photoController.photoDateLabel.setText("Date: " + photo.getModifyDate().toString());
    }
    
    /**
     * Represents an abstract transition of states by exiting out of the
     * current state and entering the new state.
     * Calls the exit() and enter() methods inside the concrete implementations
     * of the PhotState class. Note that we still call these methods even if
     * we are transition to the same state.
     *
     * @param newState The state to transition to.
     */
    void transitionTo(PhotState newState) {
        if (currentState != null) {
            currentState.exit();
        }
        currentState = newState;
        newState.enter();
    }
    
    /**
     * When an Event occurs (created by the javafx runtime), the event handling
     * is delegated to the current state. The current state returns a state
     * to transition to (this can be the same state).
     *
     * @param e The event to process.
     */
    public void processEvent(Event e) {
        lastEvent = e;
        PhotState newState = currentState.processEvent();
        transitionTo(newState);
    }
    
    /**
     * Represents the entry point into the photo-viewer.
     * Initially, the viewer is in the Photo state.
     */
    public void start() {
        transitionTo(photoState);
    }
    
    Garden getGarden() {
        return garden;
    }
    
    /**
     * Returns the group of the currently viewed photo.
     * @return The currently viewed photo.
     */
    Group getGroup() {
        return group;
    }

    /**
     * Returns the Photo object of the currently viewed photo.
     * @return The Photo currently being viewed.
     */
    Photo getPhoto() {return photo; }
}
