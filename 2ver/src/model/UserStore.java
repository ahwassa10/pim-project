package model;

import java.io.File;
import java.util.*;

/**
 * Represents a logical collection of users.
 * The user store is the top-level data structure that holds all the information
 * that the program works with. The user store holds users, the users own groups,
 * the groups contain photos, the photos contain tag stores, the tag stores
 * contain tags.
 */
public class UserStore {
    /**
     * The internal data structure used to store users is a tree set. Users are
     * sorted alphabetically by username.
     */
    private Set<User> users = new TreeSet<>(Comparator.comparing(User::getUsername));

    /**
     * This constructor does not do anything.
     */
    public UserStore() { }

    /**
     * A predicate used to test if a username is already used by a user in
     * the user store. Duplicate usernames are not allowed since the username
     * uniquely identifies a user.
     *
     * @param username The username to test.
     * @return true if the username is already taken, false otherwise.
     */
    public boolean usernameInUse(String username) {
        for (User user: users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * A predicate to test if a User object is present in the user store.
     *
     * @param user The user object to test.
     * @return true if the user store contains this user object, false otherwise.
     */
    public boolean contains(User user) {
        return users.contains(user);
    }

    /**
     * Adds a user object to the user store, checking to make sure that the
     * user is not a duplicate.
     *
     * @param user
     */
    public void add(User user) {
        if (user == null ||
            users.contains(user) ||
            usernameInUse(user.getUsername())) {

            throw new IllegalArgumentException("Can't have duplicate username");
        } else {
            users.add(user);
        }
    }

    /**
     * Given a string representing a username, this method returns the User object
     * that corresponds to this username.
     * The usernameInUse() method should be called first to check that the user store
     * actually contains the user; this method will throw an exception if the user
     * is not found.
     *
     * @param username The username to search.
     * @return The User object that corresponds to this username.
     */
    public User getByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        throw new IllegalArgumentException("Username not found");
    }

    /**
     * Removes a user from the data store, ensuring that the user data on disk is also deleted.
     *
     * @param user The user to remove.
     */
    public void remove(User user) {
        users.remove(user);
    }

    /**
     * Returns an unmodifiable collection of User objects.
     *
     * @return Collections.unmodifiableCollection(users)
     */
    public Collection<User> view() {
        return Collections.unmodifiableCollection(users);
    }

    /**
     * Returns the number of users in the user store.
     *
     * @return the number of users in the user store.
     */
    public int size() {
        return users.size();
    }

}
