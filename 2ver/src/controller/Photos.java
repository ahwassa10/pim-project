package controller;

import controller.programfsm.ProgContext;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The entry-way into the 2ver program.
 * 2ver is implemented using the state design pattern. States control
 * the visual representation of the logical model. The logical model does not
 * know javafx, and is completely separated from the visual representation of the
 * program. As the user interacts with the program, it is possible that new
 * finite state machines are created. This occurs when the user opens a photo
 * in a separate display area or opens a slideshow for an group (both of these
 * features open a new window, which maintains its own state). This allows us
 * to open multiple photos and slideshows at the same time, without interfering
 * with the main program.
 */
public class Photos extends Application {

	/**
	 * The program always has exactly one ProgContext.
	 */
	private ProgContext progContext;

	/**
	 * Called by the javafx runtime to start the program. Creating a new progContext
	 * will completely initialize the program and load the user store from disk. Calling
	 * start() will transition the program to the user state.
	 *
	 * @param primaryStage the window that the program will run in.
	 * @throws Exception an error occurred in the javafx runtime.
	 */
	public void start(Stage primaryStage) throws Exception{
		progContext = new ProgContext(primaryStage);
		progContext.start();
	}

	/**
	 * Called by the javafx runtime when the user presses "X" on the window.
	 * This method ensures that the user state is saved to the disk before the
	 * program exits.
	 */
	public void stop() {}

	/**
	 * The main method of the program; calls launch() to start the javafx framework.
	 *
	 * @param args command line arguments.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
