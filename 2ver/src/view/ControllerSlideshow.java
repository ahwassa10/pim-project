package view;

import controller.slideshowfsm.SlideshowContext;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;

/**
 * FXML controller for user view which sends events to  slideshowContext
 * 
 * @see controller.slideshowfsm.SlideshowContext
 */
public class ControllerSlideshow {
    
	private SlideshowContext slideshowContext;
	
	@FXML
	public ScrollPane photoImagePane;

    @FXML
    public ImageView showingImageView;

    @FXML
    public Button nextPhotoButton;

    @FXML
    public Button previousPhotoButton;

    @FXML
    public Button exitSlideshowButton;

    /**
	 * Event handler for when actions in slideshow mode.
	 * @param event javafx Event to be handled
	 */
    @FXML
    private void handleEvent(Event event) {
        slideshowContext.processEvent(event);
    }

    /**
     * Sets slideshow context for controller to given SlideshowContext.
     * 
     * @param slideshowContext The SlideshowContext to set as the controller's new context.
     */
    public void setContext(SlideshowContext slideshowContext) {
        this.slideshowContext = slideshowContext;
    }
}
