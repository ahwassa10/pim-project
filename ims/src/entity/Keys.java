package entity;

import java.util.UUID;
import java.util.regex.Pattern;

public final class Keys {
    private static final Pattern KEY_PATTERN =
            Pattern.compile("^[a-z0-9_\\-]+(\\.[a-z0-9_\\-]+)*$");
    private static final char KEY_SEPARATOR = '.';
    private static final int MAX_KEY_LENGTH = 127; //Inclusive
    private static final int MIN_KEY_LENGTH = 1; //Inclusive
    
    private Keys() {}
    
    public static String createKey() {
        String uuidString = UUID.randomUUID().toString();
        return uuidString;
    }
    
    public static String combine(String key1, String key2) {
        if (key1 == null && key2 == null) {
            return null;
        } else if (key1 == null) {
            return key2;
        } else if (key2 == null) {
            return key1;
        }
        
        requiresValidKey(key1);
        requiresValidKey(key2);
        
        if (key1.length() + key2.length() + 1 > MAX_KEY_LENGTH) {
            String msg = String.format("Combining %s and %s would exceed max key length",
                    key1, key2);
            throw new IllegalArgumentException(msg);
        }
        return key1 + KEY_SEPARATOR + key2;
    }
    
    public static String getStem(String key) {
        requiresValidKey(key);
        
        int endIndex = key.length() - 1;
        while (endIndex > 0 && key.charAt(endIndex) != KEY_SEPARATOR) {
            endIndex--;
        }
        
        String stem = key.substring(0, endIndex);
        return stem.length() > 0 ? stem : null;
    }
    
    public static String getTip(String key) {
        requiresValidKey(key);
        
        int beginIndex = key.length() - 1;
        while (beginIndex >= 0 && key.charAt(beginIndex) != KEY_SEPARATOR) {
            beginIndex--;
        }
        beginIndex += 1;
        
        return key.substring(beginIndex);
    }
    
    public static boolean isValid(String key) {
        return (key != null) &&
               (key.length() >= MIN_KEY_LENGTH) &&
               (key.length() <= MAX_KEY_LENGTH) &&
               (KEY_PATTERN.matcher(key).matches());   
    }
    
    public static String requiresValidKey(String key) {
        if (isValid(key)) {
            return key;
        } else {
            String msg = String.format("%s is not a valid key", key);
            throw new IllegalArgumentException(msg);
        }
    }
}
