package entity.types;

public final class Name {
    private static final int MAX_NAME_LENGTH = 128;
    private static final int MIN_NAME_LENGTH = 0;

    private Name() {
    }

    public static boolean isValidName(String test_string) {
        if ((test_string == null) || (test_string.length() < MIN_NAME_LENGTH)
                || (test_string.length() > MAX_NAME_LENGTH)) {
            return false;
        }
        for (char c : test_string.toCharArray()) {
            // Checks if c is a printable ascii character.
            if (c < 32 || c >= 127) {
                return false;
            }
        }
        return true;
    }
}
