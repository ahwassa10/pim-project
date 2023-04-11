package quality;

import java.util.Objects;

public final class Values {
    private static final int MAX_LENGTH = 256;
    
    public static boolean isValid(String test_value) {
        return test_value != null && test_value.length() <= MAX_LENGTH;
    }
    
    public static String requireValidValue(String test_value) {
        Objects.requireNonNull(test_value, "Value cannot be null");
        
        if (Values.isValid(test_value)) {
            return test_value;
        } else {
            String msg = String.format("%s is not a valid value", test_value);
            throw new IllegalArgumentException(msg);
        }
    }
}
