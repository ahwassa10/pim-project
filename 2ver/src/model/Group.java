package model;

import java.io.Serializable;
import java.util.*;

/**
 * The logical representation of an group.
 * The group class stores the group name and a collection of photo objects.
 *
 * @see Photo
 */
public class Group implements Iterable<Photo>, Serializable {
    /**
     * A field used to serialize the group object
     */
	private static final long serialVersionUID = 1L;

    /**
     * The name of the group.
     */
    private String           name       = "";

    /**
     * A collection of photo objects. The group class maintains this collection.
     */
    private ArrayList<Photo> photos     = new ArrayList<>();

    /**
     * Creates a new group object with a name.
     * Uses the <code>isValidGroupName</code> method to check if the passed in
     * name argument is a valid group name.
     *
     * @param name The name of the group.
     */
    public Group(String name) {
        if (!Group.isValidGroupName(name)) {
            throw new IllegalArgumentException("Invalid group name");
        }
        this.name = name;
    }

    /**
     * Creates a new group object using a source of photos.
     * All photos from the iterator are added to the group.
     * This constructor is used to create a new group from
     * the results of a search query.
     *
     * @param name The name of the group
     * @param source An iterator of photo objects to add to the group.
     */
    public Group(String name, Iterator<Photo> source) {
        this(name);

        if (source == null) {
            throw new IllegalArgumentException("Cannot build an group from null source");
        }

        while (source.hasNext()) {
            this.add(source.next());
        }
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
     * A predicate used to check if a String represents a valid group name.
     *
     * @param name the string to check.
     * @return true if the name is valid, false otherwise.
     */
    public static boolean isValidGroupName(String name) {
        return name != null && !name.equals("");
    }

    /**
     * Checks that the new name is valid, and then sets the group name to the
     * new name.
     * This method has package level access since changing the name could
     * potentially allow for duplicate groups in the User class. Code outside
     * this package should use an instance of User to change the group name.
     *
     * @param name The new name of the group.A
     */
    void setName(String name) {
        if (Group.isValidGroupName(name)) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Invalid new group name");
        }
    }

    /**
     * A predicate used to check if a photo (represented by a path to a file)
     * is already in the group.
     *
     * @param filepath A string representing the path to the photo file.
     * @return True if the group contains the photo, false otherwise.
     */
    public boolean photoIsPresent(String filepath) {
        for (Photo photo: photos) {
            if (photo.getBacking().toString().equals(filepath)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the photo object that corresponds to the passed in
     * file path.
     * Note that <code>photoIsPresent</code> should be called first
     * to check if the group actually contains the requested photo.
     * If the photo is not found, this method will throw an exception.
     *
     * @param filepath The path to the photo file.
     * @return The Photo object corresponding to the file path.
     */
    public Photo getPhotoByFilePath(String filepath) {
        for (Photo photo: photos) {
            if (photo.getBacking().toString().equals(filepath)) {
                return photo;
            }
        }
        throw new IllegalArgumentException("Filepath not found");
    }

    /**
     * A predicate to test if a Photo object is present in the group.
     * Calls the underlying collection's contains method.
     *
     * @param photo The photo object.
     * @return True if the photo is present, false otherwise.
     */
    public boolean contains(Photo photo) {
        return photos.contains(photo);
    }

    /**
     * Adds a photo file to the group, updating the start and end times if
     * necessary.
     * Before calling this method, one should use either <code>contains</code>
     * or <code>photoIsPresent</code> to check for duplicates. This method
     * will through an exception if a duplicate is detected.
     *
     * @param photo The photo object to insert.
     */
    public void add(Photo photo) {
        if (photo == null) {
            throw new IllegalArgumentException("Cannot add null photo");
        }

        if (photos.contains(photo)) {
            throw new IllegalArgumentException("Group already contains this photo");
        }
        photos.add(photo);
    }

    /**
     * Removes a photo file from the group, updating the start and end times
     * if necessary.
     * The method will not do anything if the group does not contain the photo.
     *
     * @param photo The photo to remove.
     */
    public void remove(Photo photo) {
        photos.remove(photo);
    }

    /**
     * Returns an iterator of photo objects that match the search criteria.
     * The iterator can be used to view the matching photo objects and to
     * create a new group.
     *
     * @param photoFilter Represents a search query.
     * @return An iterator of matching photo objects
     */
    public Iterator<Photo> searchPhotos(PhotoFilter photoFilter) {
        if (photoFilter == null) {
            throw new IllegalArgumentException("PhotoFilter can't be null");
        }

        ArrayList<Photo> matchingPhotos = new ArrayList<>();
        for (Photo photo: photos) {
            if (photoFilter.match(photo)) {
                matchingPhotos.add(photo);
            }
        }

        return new Iterator<Photo> () {
            private Iterator<Photo> itr = matchingPhotos.iterator();

            public boolean hasNext() {
                return itr.hasNext();
            }

            public Photo next() {
                return itr.next();
            }
        };
    }

    /**
     * Creates a circular iterator backed by the group.
     * Circular iterators are used to implement the slideshow.
     *
     * @return An iterator where hasNext() is always true.
     */
    public CircularIterator<Photo> circularIterator() {
        return new CircularIterator<Photo> () {
            private int cursor = -1;

            public boolean hasNext() {
                return photos.size() != 0;
            }

            public boolean hasPrevious() {
                return photos.size() != 0;
            }

            public Photo next() {
                cursor++;
                if (cursor == photos.size()) {
                    cursor = 0;
                }
                return photos.get(cursor);
            }

            public Photo previous() {
                cursor--;
                if (cursor < 0) {
                    cursor = photos.size() - 1;
                }
                return photos.get(cursor);
            }
        };
    }

    /**
     * Returns a regular iterator backed by the group object.
     * Used to view the photos inside an group.
     *
     * @return A iterator of the photos contained by the group.
     */
    public Iterator<Photo> iterator() {
        return new Iterator<Photo> () {
            private Iterator<Photo> itr = photos.iterator();

            // Don't want to expose the remove() method from ArrayList's iterator.
            public boolean hasNext() {
                return itr.hasNext();
            }

            public Photo next() {
                return itr.next();
            }
        };
    }

    /**
     * Returns the number of photos in the group.
     * @return The number of photos that the group contains.
     */
    public int size() {
        return photos.size();
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
