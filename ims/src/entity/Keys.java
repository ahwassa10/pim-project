package entity;

public final class Keys {
    private static final String KEY_SEPARATOR = ".";
    
    public static String combine(String key1, String key2) {
        return key1 + KEY_SEPARATOR + key2;
    }
}
