package controller.slideshowfsm;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Group;
import model.CircularIterator;
import model.Garden;
import model.Photo;
import view.ControllerSlideshow;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Implements the slideshow functionality of the program.
 * Note that a slideshow opens in a new window; each slideshow is essentially
 * a separate program, allowing multiple slideshows to exist at the same time.
 * Internally, a slideshow uses a state design pattern, but since there is only
 * one state, the context, abstract state, and concrete state are all bundled
 * together into one class.
 */
public class SlideshowContext {
    /**
     * A slideshow uses a circular iterator to loop back to the start/end of an group.
     */
    private CircularIterator<Photo> groupItr;

    /**
     * The current photo the slideshow is showing.
     */
    private Photo photo;

    /**
     * The stage to display the slideshow in.
     */
    private Stage stage;

    /**
     * The visual representation of the slideshow.
     */
    private Scene slideshowScene;

    /**
     * The controller for the slideshow scene.
     */
    private ControllerSlideshow slideshowController;

    /**
     * Represents the entry into the slideshow finite state machine.
     * Calling start will open a new window and display the first photo in the slideshow.
     */
    public void start() {
        stage.setScene(slideshowScene);
        slideshowController.showingImageView.setImage(new Image(photo.getBacking().toURI().toString()));
        stage.show();
    }

    /**
     * The event handler for the slideshow.
     * The user can either click the next photo button, the previous photo button,
     * or exit out of the slideshow. Note that the slideshow is effectively
     * infinite: the user can keep clicking the next photo button and the slideshow
     * will keep looping back to the start of the group if needed.
     *
     * @param event The event to process.
     */
    public void processEvent(Event event) {
        Object source = event.getSource();

        if (source == slideshowController.nextPhotoButton) {
            photo = groupItr.next();
        } else if (source == slideshowController.previousPhotoButton) {
            photo = groupItr.previous();
        } else if (source == slideshowController.exitSlideshowButton) {
            stage.close();
            return;
        }

        start();
    }

    /**
     * Creates a new slideshow object, initializing the stage, scene, controller,
     * and the circular iterator.
     * If the group does not have any photos, the constructor will throw an exception.
     *
     * @param group The group to create a slideshow from.
     */
    public SlideshowContext(Garden garden, Group group) {
        this.groupItr = garden.getCircularIterator(group);

        if (!groupItr.hasNext()) {
            throw new IllegalArgumentException("Group is empty");
        }

        photo = groupItr.next();

        this.stage = new Stage();
        stage.setTitle("Gallery");
        stage.getIcons().add(new Image(new File("data/icon2.png").toURI().toString()));

        try {
            FXMLLoader loader = new FXMLLoader(Path.of("src/view/fxml/layout_slideshow.fxml").toUri().toURL());
            VBox root = loader.load();
            slideshowController = loader.getController();
            slideshowController.setContext(this);
            
            slideshowController.photoImagePane.heightProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue,
                        Number newValue) {
                    double newHeight = newValue.doubleValue() - 10;
                    slideshowController.showingImageView.setFitHeight(newHeight);
                }
            });
            
            slideshowController.photoImagePane.widthProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue,
                        Number newValue) {
                    double newWidth = newValue.doubleValue() - 10;
                    slideshowController.showingImageView.setFitWidth(newWidth);
                }
            });
            
            slideshowScene = new Scene(root);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
