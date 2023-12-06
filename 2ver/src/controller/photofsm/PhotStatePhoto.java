package controller.photofsm;

import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import model.*;
import model.old.Group;
import model.old.Photo;
import model.old.Tag;
import model.old.TagStore;
import view.Messages;
import view.MyAlerts;

import java.util.Optional;

/**
 * Represents the state of the program when a user selects a photo.
 * After a photo is selected, its caption can be edited, and tags can be added or removed from the photo. 
 * In this state, the photo-viewer can also delete the current photo from its group, 
 * copy it to another group, or move it to another group.
 */
public class PhotStatePhoto extends PhotState{
    
    /**
     * A series of steps to perform when the program enters into the group selected state:
     * 1) Sets the current photo for the viewer.
     * 2) Initializes the photo's caption field.
     * 3) Populates the tag list view and combo-boxes.
     * 4) Sets the create tag functionality to true and the delete tag functionality to false
     */
	void enter() {
        Photo photo = photContext.getPhoto();
        photContext.photoController.captionNameTextField.setText(photo.getCaption());
        photContext.photoController.tagListView.getItems().setAll(photo.getTagStore().view());
        photContext.photoController.tagSelectComboBox.getItems().setAll(photContext.getGarden().getTags());
        photContext.photoController.tagSelectComboBox.valueProperty().set(null);
        photContext.photoController.createTagFunctionality.setVisible(true);
        photContext.photoController.deleteTagFunctionality.setVisible(false);
        
        photContext.stage.show();
    }

	/**
     * Functionality to edit a photo caption.
     * This method performs input validation, maintains the class invariants of the Group
     * object (duplicate photo captions are not allowed), and displays error messages if
     * necessary.
     *
     * @return always returns a PhotStatePhoto object.
     */
    PhotState setCaption () {
        Photo photo = photContext.getPhoto();
        String newCaption = photContext.photoController.captionNameTextField.getText();

        if (newCaption == null) {
            MyAlerts.basicError(photContext.stage,
                                Messages.unexpectedError);

        } else if (newCaption.equals(photo.getCaption())) {
            MyAlerts.basicErrorMessage(photContext.stage,
                                       Messages.sameCaptionHeader,
                                       String.format(Messages.sameCaptionContent, newCaption));
        } else {
            photo.setCaption(newCaption);
        }

        return this;
    }

    /**
     * Functionality to delete photo from group.
     * The user will have to confirm the deletion before the photo is removed
     * from the Group class. If deleted, we exit the photo-view sub-system.
     * Otherwise, we remain in this state.
     *
     * @return the next state to transition to.
     */
    PhotState deleteFromGroup() {
        Photo photo = photContext.getPhoto();

        Optional<ButtonType> response =
                MyAlerts.confirmDelete(photContext.stage,
                                       Messages.deletePhotoHeader,
                                       Messages.deletePhotoContent);

        if (response.isPresent() && response.get() == ButtonType.OK) {
            photContext.getGarden().deletePhoto(photContext.getGroup(), photo);
            return photContext.exitState;
        }
        return this;
    }

    /**
     * Gets input group from user used to find where to copy or move a photo. 
     * @param dialog The message to show the user.
     * @return The group with which to perform some action.
     */
    Group getDestinationGroup(String dialog) {
        Photo photo = photContext.getPhoto();

        Optional<Group> response =
            MyAlerts.selectGroup(photContext.stage,
                                 photContext.getGarden(),
                                 photContext.getGroup(),
                                 dialog);

        if (!response.isPresent()) {
            return null;
        }

        Group destinationGroup = response.get();
        if (!photContext.getGarden().getGroups().contains(destinationGroup)) {
            // Should theoretically never happen.
            MyAlerts.basicError(photContext.stage, Messages.moveCopyError);
            return null;
        }
        
        // Case where group is duplicate.
        if (photContext.getGarden().getPhotos(destinationGroup).contains(photo)) {
            MyAlerts.basicErrorMessage(photContext.stage,
                    Messages.moveCopyDuplicateHeader,
                    String.format(Messages.moveCopyDuplicateContent,
                                  destinationGroup));
            return null;
        }
        return destinationGroup;
    }

    /**
     * Copies current photo to retrieved destination group
     *
     * @return the next state to transition to.
     */
    PhotState copyToGroup() {
        Group destinationGroup = getDestinationGroup(Messages.copyMessage);
        if (destinationGroup != null) {
            photContext.getGarden().copy(photContext.getGroup(),
                                         destinationGroup,
                                         photContext.getPhoto());
        }
        return this;
    }

    /**
     * Moves current photo to retrieved destination group
     *
     * @return the next state to transition to.
     */
    PhotState moveToGroup() {
        Group destinationGroup = getDestinationGroup(Messages.moveMessage);
        if (destinationGroup != null) {
            photContext.getGarden().move(photContext.getGroup(),
                                         destinationGroup,
                                         photContext.getPhoto());
            return photContext.exitState;
        }
        return this;
    }

    /**
     * Adds tag specified by the user to the current photo.
     * @return The next state to transition to.
     */
    PhotState addTag() {
        String tagName = photContext.photoController.tagSelectComboBox.getValue();
        String tagValue = photContext.photoController.newTagValueTextField.getText();
        
        if (tagName == null || tagValue == null || !Tag.isValidTagParameters(tagName, tagValue)) {
            MyAlerts.basicErrorMessage(photContext.stage,
                                       Messages.invalidTagHeader,
                                       String.format(Messages.invalidTagContent, tagName, tagValue));
            return this;
        }

        Tag tag = new Tag(tagName, tagValue);
        TagStore tagStore = photContext.getPhoto().getTagStore();

        if (tagStore.contains(tag)) {
            MyAlerts.basicErrorMessage(photContext.stage,
                    Messages.duplicateTagHeader,
                    Messages.duplicateTagContent);
            return this;
        }

        photContext.getGarden().tagPhoto(photContext.getGroup(),
                                       photContext.getPhoto(),
                                       tag);
        return this;
    }

    /**
     * Implements the event handler for the photo-view state.
     * Allows for the implmentation of caption editing, deletion, copying,
     * and moving from the group, and adding tags, or moves to tag selected state.
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
        } else if (source == photContext.photoController.addTagButton) {
            return addTag();
        } else if (source == photContext.photoController.tagListView) {
            @SuppressWarnings("unchecked")
            ListView<Tag> lv = (ListView<Tag>) source;
            photContext.tagSelected = lv.getSelectionModel().getSelectedItem();
            return (photContext.tagSelected == null ? this : photContext.tagSelectedState);
        }

        return this;

    }
    
    /**
	 * Creates a new PhotStatePhoto object and initializes photContext.
	 * @param photContext The context of the photo viewer.
	 */
    PhotStatePhoto(PhotContext photContext) {
        this.photContext = photContext;
    }
}
