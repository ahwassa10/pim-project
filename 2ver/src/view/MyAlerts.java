package view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;
import model.Group;
import model.Garden;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

/**
 * Stores a number of commonly used Alerts and Dialogs.
 */
public class MyAlerts {
    /**
     * The constructor is private since this class only contains static methods.
     */
    private MyAlerts() {}

    /**
     * Generates an error Alert with the header and content text fields filled in.
     * The user can only acknowledge the error. This alert is usually used when
     * the user input is incorrect.
     *
     * @param mainStage The stage that owns the alert.
     * @param errorMessage The string displayed in the header text.
     * @param content The string displayed in the content text.
     */
    public static void basicErrorMessage(Stage mainStage, String errorMessage, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(mainStage);
        alert.setTitle("Error");
        alert.setHeaderText(errorMessage);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Generates an error Alert with only a header.
     * The user can only acknowledge the error. This alert is usually used
     * when a generic error has occurred (but the program has not crashed).
     *
     * @param mainStage The stage that owns the alert
     * @param errorMessage The string displayed in the header text.
     */
    public static void basicError(Stage mainStage, String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(mainStage);
        alert.setTitle("Error");
        alert.setHeaderText(errorMessage);
        alert.showAndWait();
    }

    /**
     * Generates a confirmation alert that asks the user to confirm the
     * deletion of an object. This alert is used whenever an object
     * is deleted (deleting a user, group, photo, or tag).
     *
     * @param mainStage The stage that owns the alert
     * @param deleteHeader The string displayed in the header text.
     * @param deleteContent The string displayed in the content text.
     * @return The user's response to the confirmation dialog.
     */
    public static Optional<ButtonType> confirmDelete(Stage mainStage,
                                                     String deleteHeader,
                                                     String deleteContent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(mainStage);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(deleteHeader);
        alert.setContentText(deleteContent);
        return alert.showAndWait();
    }

    /**
     * Generates a choice dialog asking the user to select an group.
     * This dialog is used when moving/copying a photo from one group to
     * another. The user has the option to not select anything as well.
     *
     * @param mainStage The stage that owns the alert
     * @param garden The user that will be selecting an group.
     * @param sourceGroup The source group (we don't want to display the source as an option).
     * @param message The message to display along with the choices.
     * @return The Group object that was selected.
     */
    public static Optional<Group> selectGroup(Stage mainStage,
                                              Garden garden,
                                              Group sourceGroup,
                                              String message) {

        Iterator<Group> itr = garden.getGroups().iterator();
        ArrayList<Group> choices = new ArrayList<>();
        while (itr.hasNext()){
            Group atGroup = itr.next();
            choices.add(atGroup);
        }

        ChoiceDialog<Group> dialog = new ChoiceDialog<>(sourceGroup, choices);
        dialog.initOwner(mainStage);
        dialog.setTitle("Choose an group");
        dialog.setHeaderText(message);
        return dialog.showAndWait();
    }

}
