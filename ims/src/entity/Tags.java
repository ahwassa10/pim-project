package entity;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Tags {
    private static final Pattern TAG_PATTERN =
            Pattern.compile("^[a-z0-9\\-]{1,127}$");
    
    private Tags() {}
    
    public static boolean isValidName(String test_tag) {
        return test_tag != null &&
               TAG_PATTERN.matcher(test_tag).matches();
    }
    
    public static String requireValidTagName(String test_tag) {
        Objects.requireNonNull(test_tag, "Tag cannot be null");
        
        if (Tags.isValidName(test_tag)) {
            return test_tag;
        } else {
            String msg = String.format("%s is not a valid tag", test_tag);
            throw new IllegalArgumentException(msg);
        }
    }
    
    public static Tag from(String tagSystemName, String tagName) {
        Objects.requireNonNull(tagSystemName, "Tag system cannot be null");
        Tags.requireValidTagName(tagName);
        
        return new StringTag(tagSystemName, tagName);
    }
    
    private static class StringTag implements Tag {
        private final String tagNameKey;
        private final String tagSystemKey;
        
        StringTag(String tagNameKey, String tagSystemKey) {
            this.tagNameKey = tagNameKey;
            this.tagSystemKey = tagSystemKey;
        }
        
        public String asKey() {
            return Keys.combine(tagSystemKey, tagNameKey);
        }
        
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof StringTag)) {
                return false;
            }
            StringTag o = (StringTag) other;
            return tagNameKey.equals(o.tagNameKey) &&
                    tagSystemKey.equals(o.tagSystemKey);
        }
        
        public String getNameKey() {
            return tagNameKey;
        }
        
        public String getTagSystemNameKey() {
            return tagSystemKey;
        }
        
        public int hashCode() {
            return Objects.hash(tagNameKey, tagSystemKey);
        }
        
        public String toString() {
            return String.format("StringTag: %s.%s", tagSystemKey, tagNameKey);
        }
    }
}
