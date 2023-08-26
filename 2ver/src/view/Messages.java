package view;

/**
 * Holds the various string messages that can be displayed to the user as they
 * navigate the program. Some messages have string format parameter, allowing
 * the message to be formatted into a more specific error.
 */
public final class Messages {
    /**
     * The constructor is private since this class only contains static fields.
     */
    private Messages() {}

    /**
     * "An error has occurred"
     */
    public static final String generic =
            "An error has occurred";

    /**
     * "An unexpected error has occurred"
     */
    public static final String unexpectedError =
            "An unexpected error has occurred";

    /**
     * "Please enter a new name into the text box"
     */
    public static final String noInputError =
            "Please enter a new name into the text box";

    /**
     * "This username does not exist"
     */
    public static final String invalidUsernameHeader =
            "This username does not exist";

    /**
     * "%s is not a registered user"
     */
    public static final String invalidUsernameContent =
            "%s is not a registered user";

    /**
     * "This Group already exists"
     */
    public static final String groupNameInUseHeader =
            "This group already exists";

    /**
     * "You already have an group named %s. Duplicates are not allowed"
     */
    public static final String groupNameInUseContent =
            "You already have a group named %s. Duplicates are not allowed";

    /**
     * "Are you sure you want to deleted this group?"
     */
    public static final String deleteGroupHeader =
            "Are you sure you want to deleted this group?";

    /**
     * "Deleting a group will not remove the photos from your computer"
     */
    public static final String deleteGroupContent =
            "Deleting a group will not remove the photos from your computer";

    /**
     * "Please enter a new group name"
     */
    public static final String enterNewGroupName =
            "Please enter a new group name";

    /**
     * "This group name is invalid"
     */
    public static final String invalidGroupName =
            "This group name is invalid";

    /**
     * "Please enter a new caption"
     */
    public static final String sameCaptionHeader =
            "Please enter a new caption";

    /**
     * "%s is already the caption for this photo"
     */
    public static final String sameCaptionContent =
            "%s is already the caption for this photo";

    /**
     * "Are you sure you want to delete this photo?"
     */
    public static final String deletePhotoHeader =
            "Are you sure you want to delete this photo?";

    /**
     * "The photo file will not be removed from your computer"
     */
    public static final String deletePhotoContent =
            "The photo file will not be removed from your computer";

    /**
     * "Choose a group to move the photo to"
     */
    public static final String moveMessage =
            "Choose a group to move the photo to";

    /**
     * "Chose a group to copy the photo to"
     */
    public static final String copyMessage =
            "Chose a group to copy the photo to";

    /**
     * "Duplicates are not allowed"
     */
    public static final String moveCopyDuplicateHeader =
            "Duplicates are not allowed";

    /**
     * "%s already contains this photo"
     */
    public static final String moveCopyDuplicateContent =
            "%s already contains this photo";

    /**
     * "Cannot move this photo to %s."
     */
    public static final String moveCopyError =
            "Cannot move this photo to %s.";

    /**
     * "This name+value pair is not valid"
     */
    public static final String invalidTagHeader =
            "This name+value pair is not valid";

    /**
     * "%s or %s likely contain spaces or other weird characters"
     */
    public static final String invalidTagContent =
            "%s or %s likely contain spaces or other weird characters";
    
    /**
     * "Duplicate tags are not allowed"
     */
    public static final String duplicateTagHeader = 
            "Duplicate tags are not allowed";
    
    /**
     * "Two tags cannot have the same name+value"
     */
    public static final String duplicateTagContent =
            "Two tags cannot have the same name+value";

    /**
     * "Are you sure you want to delete this tag?"
     */
    public static final String deleteTagHeader =
            "Are you sure you want to delete this tag?";

    /**
     * "The (%s, %s) tag will be permanently lost"
     */
    public static final String deleteTagContent =
            "The (%s, %s) tag will be permanently lost";

    /**
     * "Cannot make a slideshow with an empty group"
     */
    public static final String emptyGroupSlideshow =
            "Cannot make a slideshow with an empty group";

    /**
     * "Please select a valid photo file"
     */
    public static final String invalidPhotoHeader =
            "Please select a valid photo file";

    /**
     * "%s is not a valid photo file"
     */
    public static final String invalidPhotoContent =
            "%s is not a valid photo file";

    /**
     * "Duplicate photos in a group are not allowed"
     */
    public static final String duplicatePhotoHeader =
            "Duplicate photos in a group are not allowed";

    /**
     * "%s is already in this group"
     */
    public static final String duplicatePhotoContent =
            "%s is already in this group";

    /**
     * "This date range is not valid"
     */
    public static final String invalidDate =
            "This date range is not valid";

    /**
     * "Incorrect query format"
     */
    public static final String invalidQueryHeader =
            "Incorrect query format";

    /**
     * "%s is not a single tag-value pair, a conjunctive query, or a disjunctive query"
     */
    public static final String invalidQueryContent =
            "%s is not a single tag-value pair, a conjunctive query, or a disjunctive query";

}
