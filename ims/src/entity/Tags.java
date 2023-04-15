package entity;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Tags {
    private static final Pattern TAG_PATTERN =
            Pattern.compile("^[a-z0-9\\-]{1,127}$");
    
    private Tags() {}
    
    public static boolean isValid(String test_tag) {
        return test_tag != null &&
               TAG_PATTERN.matcher(test_tag).matches();
    }
    
    public static String requireValidTag(String test_tag) {
        Objects.requireNonNull(test_tag, "Tag cannot be null");
        
        if (Tags.isValid(test_tag)) {
            return test_tag;
        } else {
            String msg = String.format("%s is not a valid tag", test_tag);
            throw new IllegalArgumentException(msg);
        }
    }
}
