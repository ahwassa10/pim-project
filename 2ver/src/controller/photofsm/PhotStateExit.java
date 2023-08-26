package controller.photofsm;

/**
 * Represents the state of the program when the user closes the photo-viewer.
 */
public class PhotStateExit extends PhotState {
	/**
     * Closes the current photo-viewing stage when the state is entered.
     */
	void enter() {
        photContext.stage.close();
    }
	
	/**
	 * Creates a new PhotStateExit object and initializes photContext.
	 * @param photContext The context of the photo viewer.
	 */
    PhotStateExit(PhotContext photContext) {
        this.photContext = photContext;
    }
}
