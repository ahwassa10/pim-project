package quality;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Keys {
    private static final Pattern KEY_PATTERN = Pattern.compile("^[a-z0-9_.\\-]{1,255}$");
    private static final String KEY_SEPARATOR = "$";
    private static final Pattern KEY_SPLIT_PATTERN = Pattern.compile("\\$");
    
    private Keys() {}
    
    public static String getKeySeparator() {
        return KEY_SEPARATOR;
    }
    
    public static String combine(String key1, String key2) {
        return key1 + KEY_SEPARATOR + key2;
    }
    
    public static String combine(String key1, String key2, String key3) {
        return key1 + KEY_SEPARATOR + key2 + KEY_SEPARATOR + key3;
    }
    
    public static boolean isValid(String test_string) {
        return test_string != null &&
               KEY_PATTERN.matcher(test_string).matches();
    }
    
    public static String requireValidKey(String test_string) {
        Objects.requireNonNull(test_string, "Input cannot be null");
        
        if (Keys.isValid(test_string)) {
            return test_string;
        } else {
            String msg = String.format("%s is not a valid key", test_string);
            throw new IllegalArgumentException(msg);
        }
    }
    
    public static String[] split(String multiKey) {
        return KEY_SPLIT_PATTERN.split(multiKey);
    }
}
