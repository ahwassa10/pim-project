package controller.photofsm;

/**
 * The abstract representation of a state in the photo-viewing subsystem.
 * Contains fields and methods useful to all concrete states in the photo-viewer.
 */
public abstract class PhotState {
	/**
     * A reference to the photo-viewer context.
     */
	PhotContext photContext;

	/**
     * Defines a series of actions to take when the viewer enters into a state.
     */
    abstract void enter();

    /**
     * Method to process an event triggered by the javafx framework.
     * Concrete state classes are not required to extend the processEvent() method
     * and may simply return themselves.
     *
     * @return The state to transition to after the event has been processed.
     */
    PhotState processEvent() {return this;}

    /**
     * Defines a series of actions to take when the program exits out of a state.
     * exit() is part of the state design pattern, but no concrete state ended up
     * needing it.A default implementation was created here.
     */
    void exit() {}
}
