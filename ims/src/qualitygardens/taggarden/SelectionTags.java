package qualitygardens.taggarden;

import java.util.Objects;
import java.util.regex.Pattern;

public final class SelectionTags {
    private static final Pattern SELECTION_TAG_PATTERN =
            Pattern.compile("^[a-z0-9\\-]{1,63}$");
    
    private static final Pattern SELECTION_TAG_VALUE_PATTERN =
            Pattern.compile("^[a-z0-9\\-]{1,63}$");
    
    private SelectionTags() {}
    
    public static boolean isValidSelectionTag(String test_selectiontag) {
        return test_selectiontag != null &&
               SELECTION_TAG_PATTERN.matcher(test_selectiontag).matches();
    }
    
    public static String requireValidSelectionTag(String test_selectiontag) {
        Objects.requireNonNull(test_selectiontag,
                "Selection-tag cannot be null");
        
        if (isValidSelectionTag(test_selectiontag)) {
            return test_selectiontag;
        } else {
            String msg = String.format("%s is not a valid selection-tag",
                    test_selectiontag);
            throw new IllegalArgumentException(msg);
        }
    }
    
    public static boolean isValidSelectionTagValue(String test_selectiontagvalue) {
        return test_selectiontagvalue != null &&
                SELECTION_TAG_VALUE_PATTERN.matcher(test_selectiontagvalue).matches();
    }
    
    public static String requireValidSelectionTagValue(String test_selectiontagvalue) {
        Objects.requireNonNull(test_selectiontagvalue,
                "Selection-tag value cannot be null");
        
        if (isValidSelectionTagValue(test_selectiontagvalue)) {
            return test_selectiontagvalue;
        } else {
            String msg = String.format("%s is not a valid selection-tag value",
                    test_selectiontagvalue);
            throw new IllegalArgumentException(msg);
        }
    }
}
