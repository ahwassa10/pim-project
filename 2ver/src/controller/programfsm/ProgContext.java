package controller.programfsm;

import java.io.File;
import java.io.IOException;
import controller.Stock;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Garden;
import view.*;

/**
 * ProgContext controls the finite state machine of the application.
 * The program is implemented using the state design pattern. Therefore,
 * we have the ProgContext class, which stores the current context of the
 * program, an abstract ProgState class, and a number of concrete classes
 * that extend ProgState to implement the various states of the program.
 */
public class ProgContext {
    /**
     * The javafx stage that corresponds to the main program.
     */
    Stage primaryStage;

    /**
     * The scene that corresponds to when the user first logs in, showing all the
     * groups that the user has. Also handles when a user selects an group.
     */
    Scene userScene;

    /**
     * The controller for the userScene.
     */
    ControllerUser userController;

    /**
     * The scene that corresponds to when a user opens an group, showing all the photos
     * inside it.
     */
    Scene groupScene;

    /**
     * The controller for the groupScene.
     */
    ControllerGroup groupController;

    /**
     * The state of the program when the user opens an group, showing all the photos inside it.
     */
    ProgStateGroupOpened groupOpenedState;

    /**
     * The state of the program when the user selects an group.
     */
    ProgStateGroupSelected groupSelectedState;

    /**
     * The state of the program when the user searches by date/tag.
     */
    ProgStateSearch searchState;

    /**
     * The state of the program when the user initially logs in.
     */
    ProgStateUser userState;

    /**
     * The most recent event triggered by the javafx framework.
     */
    Event lastEvent;

    /**
     * The state that the program is currently in.
     */
    private ProgState currentState;
    
    private final Garden user;

    /**
     * Creates a new ProgContext object, and initializes the program.
     * All the scenes, controllers, and states are created. The user store
     * is also loaded from disk, and if the stock account is missing, it is
     * automatically created.
     *
     * @param primaryStage the stage that will be used for the program.
     */
    public ProgContext(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("2ver PIM");
        primaryStage.getIcons().add(new Image(new File("data/icon2.png").toURI().toString()));
        
        // Reloads the state every time the window is selected.
        // Used to update a window after a change in another window, such as deleting a photo.
        primaryStage.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean onHidden, Boolean onShown) {
                if (onShown) {
                    currentState.enter();
                }
            }
        });
        
        // Create all the scenes and controllers.
        // An FXMLLoader can only be used once without problems.
        try {
            FXMLLoader userLoader = new FXMLLoader();
            userLoader.setLocation(getClass().getResource("/view/fxml/layout_user.fxml"));
            HBox userRoot = userLoader.load();
            userController = userLoader.getController();
            userScene = new Scene(userRoot);
            
            FXMLLoader groupLoader = new FXMLLoader();
            groupLoader.setLocation(getClass().getResource("/view/fxml/layout_group.fxml"));
            HBox groupRoot = groupLoader.load();
            groupController = groupLoader.getController();
            groupScene = new Scene(groupRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // All states have access to the context.
        ProgState.progContext = this;
        Controller.setContext(this);

        groupOpenedState   = new ProgStateGroupOpened();
        groupSelectedState = new ProgStateGroupSelected();
        searchState        = new ProgStateSearch();
        userState          = new ProgStateUser();
        
        user = Stock.createStockGarden();
        
        ProgState.user = user;
    }

    /**
     * Represents an abstract transition of states by exiting out of the
     * current state and entering the new state.
     * Calls the exit() and enter() methods inside the concrete implementations
     * of the ProgState class. Note that we still call these methods even if
     * we are transition to the same state.
     *
     * @param newState The state to transition to.
     */
    void transitionTo(ProgState newState) {
        if (currentState != null) {
            currentState.exit();
        }
        currentState = newState;
        newState.enter();
    }

    /**
     * Represents the entry point into the program.
     * Transitions into the first state.
     */
    public void start() {
        transitionTo(userState);
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
        ProgState newState = currentState.processEvent();
        transitionTo(newState);
    }
}
