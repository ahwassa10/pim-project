package view;

import controller.programfsm.ProgContext;

/**
 * Specifies the common progContext field and setContext(ProgContext context) method for all FXML Controller classes
 * 
 * @see ProgContext
 */
public abstract class Controller {
    /**
     * The program context shared by all controllers that belong to the program fsm.
     */
    static ProgContext progContext;

    /**
     * Sets the program context to the passed in ProgContext. Used to initialize all
     * the program controllers.
     *
     * @param context the shared context.
     */
    public static void setContext(ProgContext context) {
        progContext = context;
    }
}
