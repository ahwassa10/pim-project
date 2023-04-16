package statement;

import java.util.Objects;

final class FileValues {
    private static final int MAX_LENGTH = 256; //Exclusive
    
    private FileValues() {}
    
    static boolean isValid(String test_value) {
        return test_value != null && test_value.length() < MAX_LENGTH;
    }
    
    static String requireValidValue(String test_value) {
        Objects.requireNonNull(test_value, "File-based value cannot be null");
        
        if (FileValues.isValid(test_value)) {
            return test_value;
        } else {
            String msg = String.format("%s is not a valid file-based value", test_value);
            throw new IllegalArgumentException(msg);
        }
    }
}
