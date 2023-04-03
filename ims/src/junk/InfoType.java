package junk;

import java.util.regex.Pattern;

public interface InfoType {
    // Matches a string that starts with an uppercase letter followed by
    // zero or more occurrences of any letter (both uppercase and
    // lowercase) or the hyphen -.
    static final Pattern TYPE_PATTERN = Pattern.compile("[A-Z][a-zA-Z[-]]*");
    static final int MAX_INFO_TYPE_LENGTH = 128;
    static final int MIN_INFO_TYPE_LENGTH = 1;

    public static boolean isValidTypeName(String test_string) {
        if ((test_string == null) || (test_string.length() < MIN_INFO_TYPE_LENGTH)
                || (test_string.length() > MAX_INFO_TYPE_LENGTH)) {
            return false;
        }
        return TYPE_PATTERN.matcher(test_string).matches();
    }

    String getTypeName();

    boolean qualifies(String data);
}
