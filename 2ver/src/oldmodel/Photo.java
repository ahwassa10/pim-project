package oldmodel;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import java.time.*;

/**
 * The logical representation of a photo.
 * A photo is identified by its backing, a path to a photo file on the file
 * system. A photo object also stores the modify-time of the file, a caption,
 * and a collection of tags in the tag store.s
 */
public class Photo {
    /**
     * The path to the photo file on the system.
     */
    private  File     backing;

    /**
     * The modify-time of the file, as reported by the file system.
     */
    private  Instant  modifyTime;

    /**
     * A caption for the photo. Captions are not used to identify the photo.
     */
    private  String   caption;

    /**
     * Represents a collection of tags applied to the photo.
     */
    private  TagStore tagStore;

    /**
     * A collection of valid photo extensions that the program can read.
     */
    private static final List<String> validExtensions =
            Arrays.asList("bmp", "gif", "jpg", "jpeg", "png");

    /**
     * Returns a collection of valid photo extensions that the program can read.
     *
     * @return A list of file extensions.
     */
    public static List<String> getValidExtensions() {
        return validExtensions;
    }

    /**
     * Creates a photo object using the photo file specified by the path.
     * This constructor also automatically updates the modify-time, generates
     * an empty tag store, and sets the caption to an empty string.
     * @param filepath The path to the file on disk
     */
    public Photo(File filepath) {
        if (!Photo.isValidPhotoFile(filepath)) {
            throw new IllegalArgumentException("Filepath is not a valid file");
        }
        backing = filepath;
        caption = "";
        tagStore = new TagStore();
        refreshModifyTime();
    }

    /**
     * A predicate to test if a file system path represents a valid photo file.
     * This method checks that the extension is valid as well.
     *
     * @param filepath The path to the file.
     * @return True is the file is valid, false otherwise.
     */
    public static boolean isValidPhotoFile(File filepath) {
        if (filepath == null)    {return false;}
        if (!filepath.isFile())  {return false;}
        if (!filepath.canRead()) {return false;}

        String[] fileParts = filepath.getName().split("\\.");
        String extension = fileParts[fileParts.length - 1].toLowerCase();

        return validExtensions.contains(extension);
    }

    /**
     * Returns the caption of the photo.
     *
     * @return The caption string. Never null.
     */
    public String getCaption() {
        return caption;
    }

    /**
     * A predicate to check if a string represents a valid caption.
     * Any string can be a caption, as long as the string is not null.
     *
     * @param caption The string to check.
     * @return true is not null, false otherwise.
     */
    public boolean isValidCaption(String caption) {
        return caption != null;
    }

    /**
     * Sets the caption of the photo to the specifies string.
     * Internally, this method calls isValidCaption and then sets
     * the caption to the passed in string.
     *
     * @param newCaption A new caption for the photo.
     */
    public void setCaption(String newCaption) {
        if (!isValidCaption(newCaption)) {
            throw new IllegalArgumentException("Cannot set the caption to null");
        } else {
            this.caption = newCaption;
        }
    }

    /**
     * Updates the modify-time of the photo object by consulting the file system
     * though the java.io file system interface.
     *
     */
    public void refreshModifyTime() {
        modifyTime = Instant.ofEpochMilli(backing.lastModified());
    }

    /**
     * Returns the modify-time in the local date format.
     * The program displays dates, but internally, it uses
     * milliseconds since the epoch to store time.
     *
     * @return A LocalDate object.
     */
    public LocalDate getModifyDate() {
        return modifyTime.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Returns the number of milliseconds since the epoch.
     * This method has package level access since it should only be
     * used by group to update its start and end times.
     *
     * @return The number of milliseconds since the epoch.
     */
    Instant getModifyInstant() {return modifyTime;}

    /**
     * Returns the collections of tags applied to the photo.
     *
     * @return An object that stores the tags associated with the photo.
     */
    public TagStore getTagStore() {
        return tagStore;
    }

    /**
     * Returns the path to the photo file on disk.
     * File paths are used to identify a photo object.
     *
     * @return The file path of the photo.
     */
    public File getBacking() {return backing;}

    /**
     * Checks two photos for equality by comparing their file system paths.
     *
     * @param o The other photo object
     * @return true if the file system paths match, false otherwise.
     */
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Photo)) {
            return false;
        }
        Photo other = (Photo) o;
        // Checks if two file paths point to the same file.
        return other.backing.equals(this.backing);
    }

    /**
     * Returns the string representation of the photo: a caption and
     * a modify-time.
     *
     * @return caption + " " + modifyTime
     */
    public String toString() {
        return caption + " " + modifyTime;
    }
}
