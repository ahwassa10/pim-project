package model;

/**
 * The logical representation of an group.
 *
 * @see Photo
 */
public class Group {
    /**
     * The name of the group.
     */
    private String name = "";
    
    /**
     * Creates a new group object with a name.
     * Uses the <code>isValidGroupName</code> method to check if the passed in
     * name argument is a valid group name.
     *
     * @param name The name of the group.
     */
    Group(String name) {
        if (!Group.isValidGroupName(name)) {
            throw new IllegalArgumentException("Invalid group name");
        }
        this.name = name;
    }
    
    /**
     * A predicate used to check if a String represents a valid group name.
     * 
     * A valid name is a non-null, non-zero-length string.
     * 
     * @param name the string to check.
     * @return true if the name is valid, false otherwise.
     */
    public static boolean isValidGroupName(String name) {
        return name != null && !name.equals("");
    }
    
    /**
     * Returns the name of the group.
     *
     * @return the name of the group.
     */
    public String getName() {
        return name;
    }

    /**
     * Changes the name of a group.
     * 
     * Checks that the new name is valid, and then sets the group name to the
     * new name.
     * 
     * @param name The new name of the group.A
     */
    public void setName(String name) {
        if (Group.isValidGroupName(name)) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Invalid new group name");
        }
    }

    /**
     * Returns a string representation of the group.
     * Used when groups need to populate a ListView.
     *
     * @return The name of the group.
     */
    public String toString() {
        return name;
    }
}
