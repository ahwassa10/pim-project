package statement;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Keys {
    private static final Pattern KEY_PATTERN = Pattern.compile("^[a-z0-9_.\\-]{1,127}$");
    
    private Keys() {}
    
    public static boolean isValid(String test_key) {
        return test_key != null &&
               KEY_PATTERN.matcher(test_key).matches();
    }
    
    public static String requireValidKey(String test_key) {
        Objects.requireNonNull(test_key, "Key cannot be null");
        
        if (Keys.isValid(test_key)) {
            return test_key;
        } else {
            String msg = String.format("%s is not a valid key", test_key);
            throw new IllegalArgumentException(msg);
        }
    }
}
