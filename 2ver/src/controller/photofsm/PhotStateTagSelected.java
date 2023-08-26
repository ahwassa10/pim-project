package controller.photofsm;

import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import model.Photo;
import model.Tag;
import view.Messages;
import view.MyAlerts;

import java.util.Optional;

/**
 * Represents the state of the program when a user selects a tag from the list view.
 * After a tag is selected, it can be deleted from the photo's tags. 
 */
public class PhotStateTagSelected extends PhotStatePhoto {

	/**
     * A series of steps to perform when the program enters into the group selected state:
     * 1) Sets the current photo and selected tag for the viewer.
     * 2) Initializes the photo's caption field.
     * 3) Populates the tag list view and combo-boxes.
     * 4) Sets the create tag functionality to false and the delete tag functionality to true
     * 5) Displays currently selected tag
     */
    void enter() {
        Photo photo = photContext.getPhoto();
        Tag tagSelected = photContext.tagSelected;

        photContext.photoController.captionNameTextField.setText(photo.getCaption());
        photContext.photoController.tagListView.getItems().setAll(photo.getTagStore().view());
        photContext.photoController.tagListView.getSelectionModel().select(tagSelected);
        photContext.photoController.tagSelectComboBox.getItems().setAll(photContext.getUser().viewUserTags());
        photContext.photoController.tagSelectComboBox.valueProperty().set(null);
        photContext.photoController.deleteTagFunctionality.setVisible(true);
        photContext.photoController.createTagFunctionality.setVisible(false);
        photContext.photoController.selectedTagTextField.setText("Selected: " + tagSelected.toString());
        photContext.stage.show();
    }

    /**
     * Functionality to delete currently selected tag from the photo's tags.
     * The user will have to confirm the deletion before the tag is removed
     * from the Photo class. If deleted, we return to the default photo-viewing state.
     * Otherwise, we remain in this state.
     *
     * @return the next state to transition to.
     */
    PhotState deleteTag() {
        Photo photo = photContext.getPhoto();
        Tag tagSelected = photContext.tagSelected;

        if (tagSelected == null || !photo.getTagStore().contains(tagSelected)) {
            MyAlerts.basicError(photContext.stage, Messages.unexpectedError);
            return this;
        }

        Optional<ButtonType> response =
                MyAlerts.confirmDelete(photContext.stage,
                        Messages.deleteTagHeader,
                        String.format(Messages.deleteTagContent,
                                      tagSelected.getName(),
                                      tagSelected.getValue()));

        if (response.isPresent() && response.get() == ButtonType.OK) {
            photo.getTagStore().remove(tagSelected);
            return photContext.photoState;
        }

        return this;
    }

    /**
     * Implements the event handler for the tag-selected state.
     * Allows for the implmentation of caption editing, deletion, copying,
     * and moving from the group, and deleting tags
     *
     * @return the next state to transition to.
     */
    PhotState processEvent() {
        Object source = photContext.lastEvent.getSource();

        if (source == photContext.photoController.editCaptionButton) {
            return setCaption();
        } else if (source == photContext.photoController.deleteFromGroupButton) {
            return deleteFromGroup();
        } else if (source == photContext.photoController.copyToGroupButton) {
            return copyToGroup();
        } else if (source == photContext.photoController.moveToGroupButton) {
            return moveToGroup();
        } else if (source == photContext.photoController.cancelDeleteButton) {
            return photContext.photoState;
        } else if (source == photContext.photoController.deleteTagButton) {
            return deleteTag();
        } else if (source == photContext.photoController.tagListView) {
            @SuppressWarnings("unchecked")
            ListView<Tag> lv = (ListView<Tag>) source;
            photContext.tagSelected = lv.getSelectionModel().getSelectedItem();
            return (photContext.tagSelected == null ? this : photContext.tagSelectedState);
        }
        return this;
    }

    /**
	 * Creates a new PhotStateTagSelected object and initializes photContext.
	 * @param photContext The context of the photo viewer.
	 */
    PhotStateTagSelected(PhotContext photContext) {
        super(photContext);
    }
}
