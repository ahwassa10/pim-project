package model.old;

/**
 * The logical representation of a tag.
 * Tags are aggregated and managed using a tag store.
 * Note that tagging a photo is implemented in the User class. This is because
 * the User needs to know the names of previously applied tags.
 */
public class Tag {
    /**
     * The name of the tag. Corresponds to name in (name+value) pair.
     */
    private String  name;

    /**
     * The value of the tag. Corresponds to value in (name+value) pair.
     */
    private String  value;

    /**
     * Creates a new tag object using a (name+value) pair and a tag type.
     *
     * @param name Corresponds to name in (name+value) pair.
     * @param value Corresponds to value in (name+value) pair.
     */
    public Tag(String name, String value) {
        if (!Tag.isValidTagParameters(name, value)) {
            throw new IllegalArgumentException("Invalid tag name or value");
        } else {
            this.name = name.strip();
            this.value = value.strip();
        }
    }

    /**
     * Applies the regular expression "\\s*\\w+\\s*" to check if the name
     * and value of the tag are valid.
     *
     * @param name Corresponds to name in (name+value) pair.
     * @param value Corresponds to value in (name+value) pair.
     * @return true if the (name+value) pair is valid, false otherwise.
     */
    public static boolean isValidTagParameters(String name, String value) {
        return name != null &&
               value != null &&
               name.matches("\\s*\\w+\\s*") &&
               value.matches("\\s*\\w+\\s*");
    }

    /**
     * Returns the name from the (name+value) pair.
     *
     * @return The name of the tag.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the value from the (name+value) pair.
     *
     * @return The value of the tag.
     */
    public String getValue() {
        return value;
    }

    /**
     * Checks if two tags are equal by seeing if the (name+value) pairs match.
     *
     * @param o the other tag.
     * @return true if the (name+value) pairs match, false otherwise.
     */
    public boolean equals(Object o) {
        if (!(o instanceof Tag)) {
            return false;
        }

        Tag other = (Tag) o;
        return name.equals(other.name) && value.equals(other.value);
    }

    /**
     * Returns a string representation of the tag.
     *
     * @return name + ": " + value;
     */
    public String toString() {
        return name + ": " + value;
    }
}
