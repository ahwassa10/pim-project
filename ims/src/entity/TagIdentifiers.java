package entity;

import java.util.Objects;
import java.util.regex.Pattern;

public final class TagIdentifiers {
    private static final Pattern TAG_PATTERN =
            Pattern.compile("^[a-z0-9\\-]{1,127}$");
    
    private TagIdentifiers() {}
    
    public static boolean isValidTagName(String test_tag) {
        return test_tag != null &&
               TAG_PATTERN.matcher(test_tag).matches();
    }
    
    public static String requireValidTagName(String test_tag) {
        Objects.requireNonNull(test_tag, "Tag name cannot be null");
        
        if (TagIdentifiers.isValidTagName(test_tag)) {
            return test_tag;
        } else {
            String msg = String.format("%s is not a valid tag name", test_tag);
            throw new IllegalArgumentException(msg);
        }
    }
}
