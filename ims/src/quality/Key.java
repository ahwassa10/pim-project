package quality;

import java.util.regex.Pattern;

public interface Key {
    Pattern KEY_PATTERN = Pattern.compile("^[a-z0-9_.\\-]{1,255}$");
    
    static boolean isValid(String test_string) {
        return KEY_PATTERN.matcher(test_string).matches();
    }
}
