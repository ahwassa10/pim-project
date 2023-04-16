package statement;

import java.util.Objects;
import java.util.regex.Pattern;

final class FileDescriptors {
    private static final Pattern FILE_DESCRIPTOR_PATTERN =
            Pattern.compile("^[a-z0-9_.\\-]{1,127}\\$[a-z0-9_.\\-]{1,127}$");
    private static char SEPARATOR = '$';
    
    private FileDescriptors() {}
    
    static String from(String qualifierKey, String holderKey) {
        Objects.requireNonNull(qualifierKey, "Qualifier key cannot be null");
        Objects.requireNonNull(holderKey, "Holder key cannot be null");
        
        return qualifierKey + SEPARATOR + holderKey;
    }
    
    static String getHolder(String fileDescriptor) {
        int beginIndex = fileDescriptor.length() - 1;
        
        // The loop find the index of the separator character
        // in the string.
        while (fileDescriptor.charAt(beginIndex) != SEPARATOR) {
            beginIndex--;
        }
        // the holder begins at the first index after the separator.
        beginIndex += 1; 
        
        return fileDescriptor.substring(beginIndex);
    }
    
    static String getQualifier(String fileDescriptor) {
        int endIndex = 0;
        
        while (fileDescriptor.charAt(endIndex) != SEPARATOR) {
            endIndex++;
        }
        
        // The qualifier is the part of the string before
        // the separator.
        return fileDescriptor.substring(0, endIndex);
    }
    
    char getSeparator() {
        return SEPARATOR;
    }
    
    static boolean isValid(String test_descriptor) {
        return test_descriptor != null && 
               FILE_DESCRIPTOR_PATTERN.matcher(test_descriptor).matches();
    }
    
    String requireValidDescriptor(String test_descriptor) {
        Objects.requireNonNull(test_descriptor, "File-based descriptor cannot be null");
        
        if (isValid(test_descriptor)) {
            return test_descriptor;
        } else {
            String msg = String.format("%s is not a valid file-based descriptor",
                    test_descriptor);
            throw new IllegalArgumentException(msg);
        }
    }
}
