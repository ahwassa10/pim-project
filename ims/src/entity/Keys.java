package entity;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

public final class Keys {
    private static final String KEY_SEPARATOR = ".";
    private static final int MAX_KEY_LENGTH = 127; //Inclusive
    private static final Pattern TIP_PATTERN =
            Pattern.compile("^[a-z0-9_\\-]{1,127}$");
    
    private Keys() {}
    
    public static boolean isValidTip(String test_tip) {
        return test_tip != null &&
               TIP_PATTERN.matcher(test_tip).matches();
    }
    
    public static Key createKey() {
        String uuidString = UUID.randomUUID().toString();
        return new TipKey(uuidString);
    }
    
    public static Key newKey(String tip) {
        requireValidTip(tip);
        return new TipKey(tip);
    }
    
    public static Key combine(Key stem, Key tip) {
        Objects.requireNonNull(stem, "Stem key cannot be null");
        Objects.requireNonNull(tip, "Tip key cannot be null");
        
        if (stem.length() + tip.length() + 1 > MAX_KEY_LENGTH) {
            String msg = String.format("Combining %s and %s would exceed max key length",
                    stem, tip);
            throw new IllegalArgumentException(msg);
        }
        
        return new CombinedKey(stem, tip);
    }
    
    public static Key combine(Key stem, String tip) {
        Objects.requireNonNull(stem, "Stem key cannot be null");
        requireValidTip(tip);
        
        if (stem.length() + tip.length() + 1 > MAX_KEY_LENGTH) {
            String msg = String.format("Combining %s and %s would exceed max key length",
                    stem, tip);
            throw new IllegalArgumentException(msg);
        }
        
        return new CombinedKey(stem, new TipKey(tip));
    }
    
    public static String requireValidTip(String test_tip) {
        Objects.requireNonNull(test_tip, "Key tip cannot be null");
        
        if (TagIdentifiers.isValidTagName(test_tip)) {
            return test_tip;
        } else {
            String msg = String.format("%s is not a valid key tip", test_tip);
            throw new IllegalArgumentException(msg);
        }
    }
    
    private static class CombinedKey implements Key {
        private final Key stem;
        private final Key tip;
        
        CombinedKey(Key stem, Key tip) {
            this.stem = stem;
            this.tip = tip;
        }
        
        public String asString() {
            return stem.asString() + KEY_SEPARATOR + tip.asString();
        }
        
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof CombinedKey)) {
                return false;
            }
            CombinedKey other = (CombinedKey) o;
            return stem.equals(other.stem) &&
                   tip.equals(other.tip);
        }
        
        public Key getStem() {
            return stem;
        }
        
        public Key getTip() {
            return tip;
        }
        
        public int length() {
            return stem.length() + 1 + tip.length();
        }
        
        public int hashCode() {
            return Objects.hash(stem, tip);
        }
        
        public String toString() {
            return String.format("CombinedKey<Stem: %s, Tip: %s>",
                    stem, tip);
        }
    }
    
    private static class TipKey implements Key {
        private final String tip;
        
        TipKey(String tip) {
            this.tip = tip;
        }
        
        public String asString() {
            return tip;
        }
        
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof TipKey)) {
                return false;
            }
            TipKey other = (TipKey) o;
            return tip.equals(other.tip);
        }
        
        public Key getStem() {
            return null;
        }
        
        public Key getTip() {
            return this;
        }
        
        public int length() {
            return tip.length();
        }
        
        public int hashCode() {
            return tip.hashCode();
        }
        
        public String toString() {
            return String.format("TipKey<%s>", tip);
        }
    } 
}
