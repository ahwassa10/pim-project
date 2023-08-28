package controller.programfsm;

import model.Group;
import model.PhotoFilter;
import model.Garden;

/**
 * The abstract representation of a state in the program.
 * Note that the Program content, user, user selected, group,
 * photo filer, and search query are stored as static field of
 * the state. All states will have direct access to these fields
 * and can modify them as the user interacts with the program.
 */
public abstract class ProgState {
    /**
     * A reference to the program context.
     */
    static ProgContext progContext;
        
    /**
     * That garden that is opened.
     */
    static Garden garden;

    /**
     * The group selected by the user.
     */
    static Group groupSelected;

    /**
     * The filter that, when applied to an group, generates the search results.
     */
    static PhotoFilter searchResults;

    /**
     * A string representing a search query. This string is only for visual purposes.
     */
    static String searchQuery;

    /**
     * Defines a series of actions to take when the program enters into a state.
     */
    abstract void enter();

    /**
     * Concrete state classes must extend the processEvent() method to handle
     * various event such as button presses and clicks on tiles.
     *
     * @return The state to transition to after the event has been processed.
     */
    abstract ProgState processEvent();

    /**
     * Defines a series of actions to take when the program exits out of a state.
     * exit() is part of the state design pattern, but no concrete state ended up
     * needing it. I created a default implementation here.
     */
    void exit() {}
}
