package quality;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Keys {
    private static final Pattern KEY_PATTERN = Pattern.compile("^[a-z0-9_.\\-]{1,255}$");
    
    private Keys() {}
    
    public static boolean isValid(String test_string) {
        return KEY_PATTERN.matcher(test_string).matches();
    }
    
    public static String requireValidKey(String test_string) {
        Objects.requireNonNull(test_string, "Key cannot be null");
        
        if (Keys.isValid(test_string)) {
            return test_string;
        } else {
            String msg = String.format("%s is not a valid key", test_string);
            throw new IllegalArgumentException(msg);
        }
    }
}
