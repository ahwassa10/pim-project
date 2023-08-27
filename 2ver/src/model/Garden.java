package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * The logical representation of a user.
 * A user is a collection of groups and tag names. Whenever a tag is added to any of
 * a user's photos, that tag name is stored in the user so that it can be reused
 * later. Many other operations, such as moving or copying a photo to a different
 * group go through the user class.
 */
public class Garden {
    /**
     * An implementation of comparator used to sort the tag names inside the user.
     * Tags are sorted alphabetically by their lowercase names.
     */
    private class TagComparator implements Comparator<String> {
        /**
         * Compares two tag alphabetically by their lowercase names.
         *
         * @param o1 The first tag name
         * @param o2 The second tag name
         * @return The result of the comparison.
         */
		public int compare(String o1, String o2) {
			return o1.toLowerCase().compareTo(o2.toLowerCase());
		}
	}

    /**
     * The username of the user. This field is used to identify a user.
     */
    private String           username = "";

    /**
     * A collection of groups owned by the user.
     */
    private ArrayList<Group> groups   = new ArrayList<>();

    /**
     * A collection of tag names that the user has applied to photos.
     */
    private Set<String>      userTags = new TreeSet<>(new TagComparator());

    /**
     * Creates a new user object after checking that the username is valid.
     *
     * @param username The string to use as the username.
     */
    public Garden(String username) {
        if (!isValidUsername(username)) {
            throw new IllegalArgumentException("Invalid username");
        }
        this.username = username;
        userTags.add("Person");
        userTags.add("Location");
        userTags.add("Event");
    }

    /**
     * A predicate used to test if a string represents a valid username.
     *
     * @param name The string to test.
     * @return true if the username is valid, false otherwise.
     */
    public static boolean isValidUsername(String name) {
        return name != null && !name.equals("");
    }

    /**
     * Returns the username of the user.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * A predicate to test if the user owns the passed in group object.
     *
     * @param group The group to test
     * @return true is the user owns the group, false otherwise.
     */
    public boolean contains(Group group) {
        return groups.contains(group);
    }

    /**
     * A predicate to test if the user already owns an group with this name.
     *
     * @param name the name to test.
     * @return true if the user owns an group with this name, false otherwise.
     */
    public boolean groupNameInUse(String name) {
        for (Group group: groups) {
            if (group.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Given a string representing an group name, this method returns the group object
     * corresponding to that string.
     * Before calling this method, groupNameInUse() should be called first to check that
     * the user already contains an group with this name. getByGroupName will throw an
     * exception if the group is not found.
     *
     * @param groupName The group name to search.
     * @return The Group object corresponding to this group name.
     */
    public Group getByGroupName(String groupName) {
        for (Group group : groups) {
            if (group.getName().equals(groupName)) {
                return group;
            }
        }
        throw new IllegalArgumentException("Username not found");
    }

    /**
     * Adds an Group object to the User, checking that the group is not a duplicate.
     * Internally, this method calls groupNameInUse() on the name of the new group
     * since groups are identified by their names. Duplicate group names are not
     * allowed.
     *
     * @param group The Group object to add.
     */
    public void add(Group group) {
        if (group == null ||
            this.groupNameInUse(group.getName())) {

            throw new IllegalArgumentException("Can't add null group");
        }

        groups.add(group);
    }

    /**
     * Removes a Group object from the user, doing nothing if the group is not present.
     *
     * @param group The group to remove
     */
    public void remove(Group group) {
        groups.remove(group);
    }

    /**
     * Changes the name of an group, making sure that the name change does not result
     * in a duplicate group name.
     *
     * @param group The Group object to apply the change to.
     * @param newName The new name of the group.
     */
    public void changeGroupName(Group group, String newName) {
        if (group == null ||
            newName == null ||
            !groups.contains(group) || // User must own the group.
            this.groupNameInUse(newName)) { // Can't contain duplicate names.

            throw new IllegalArgumentException("Invalid arguments to change group name");
        }

        group.setName(newName);
    }

    /**
     * Creates an iterator of the User's groups.
     * Note that this iterator is read only. There is no way to add/remove an group
     * using the iterator.
     *
     * @return An iterator for the Group objects.
     */
    public Iterator<Group> iterator() {
        // Don't want to expose the remove() method from ArrayList's iterator.
        return new Iterator<Group>() {
            private Iterator<Group> itr = groups.iterator();

            public boolean hasNext() {
                return itr.hasNext();
            }

            public Group next() {
                return itr.next();
            }
        };
    }

    /**
     * Returns an unmodifiable collection of tag names.
     *
     * @return Collections.unmodifiableCollection(userTags)
     */
    public Collection<String> viewUserTags() {
        return Collections.unmodifiableCollection(userTags);
    }

    /**
     * The implementation of copying a photo from one group to another.
     * This method comprehensively checks that the user owns both the source and
     * destination groups, that the source contains the photo, and that the
     * destination does not contain the photo.
     *
     * @param srcGroup The source group
     * @param destGroup The destination group
     * @param photo The photo to copy from the source to the destination groups.
     */
    public void copyPhoto(Group srcGroup, Group destGroup, Photo photo) {
        if (srcGroup == null ||
            destGroup == null ||
            photo == null ||
            !groups.contains(srcGroup) ||
            !groups.contains(destGroup) ||
            !srcGroup.contains(photo) ||
            destGroup.contains(photo)) {

            throw new IllegalArgumentException("Invalid Arguments to Copy Photo");
        }

        destGroup.add(photo);
    }

    /**
     * The implementation of moving a photo from one group to another.
     * This method comprehensively checks that the user owns both the source and
     * destination groups, that the source contains the photo, and that the
     * destination does not contain the photo.
     *
     * @param srcGroup The source group.
     * @param destGroup The destination group.
     * @param photo The photo to move from the source to the destination groups.
     */
    public void movePhoto(Group srcGroup, Group destGroup, Photo photo) {
        if (srcGroup == null ||
                destGroup == null ||
            photo == null ||
            !groups.contains(srcGroup) ||
            !groups.contains(destGroup) ||
            !srcGroup.contains(photo) ||
            destGroup.contains(photo)) {

            throw new IllegalArgumentException("Invalid Arguments to Move Photo");
        }

        srcGroup.remove(photo);
        destGroup.add(photo);
    }

    /**
     * The implementation of tagging a photo.
     * Specifically, this method applies a tag to a photo inside an group, checking
     * that the user owns the group, that the photo is inside this group, and that
     * the tag is not a duplicate.
     *
     * @param srcGroup The source group
     * @param photo The photo inside the source group
     * @param tag The tag to apply to the photo
     */
    public void tagPhoto(Group srcGroup, Photo photo, Tag tag) {
        if (srcGroup == null ||
            photo == null ||
            tag == null ||
            !groups.contains(srcGroup) ||
            !srcGroup.contains(photo) ||
            photo.getTagStore().contains(tag)) {

            throw new IllegalArgumentException("Cannot tag this photo");
        }

        photo.getTagStore().add(tag);
        userTags.add(tag.getName());
    }

    /**
     * Two users are equal if they have the same username.k
     *
     * @param o The other object to test.
     * @return true if both users have the same username, false otherwise.
     */
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Garden)) {
            return false;
        }
        Garden other = (Garden) o;
        return this.username.equals(other.username);
    }

    /**
     * Returns a string representation of the user.
     *
     * @return The username.
     */
    public String toString() {
        return username;
    }
}
