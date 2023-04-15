package entity;

import java.util.UUID;

public final class Identifiers {
    private Identifiers() {}
    
    public static Identifier combine(String qualifier, Identifier i) {
        String identiferKey = i.asKey();
        String combinedKey = Keys.combine(qualifier, identiferKey);
        
        return new StringIdentifier(combinedKey);
    }
    
    public static Identifier newIdentifier() {
        String uuid = UUID.randomUUID().toString();
        
        return new StringIdentifier(uuid);
    }
    
    private static class StringIdentifier implements Identifier {
        private final String identifier;
        
        StringIdentifier(String identifier) {
            this.identifier = identifier;
        }
        
        public String asKey() {
            return identifier;
        }
        
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof StringIdentifier)) {
                return false;
            }
            StringIdentifier o = (StringIdentifier) other;
            return identifier.equals(o.identifier);
        }
        
        public int hashCode() {
            return identifier.hashCode();
        }
        
        public String toString() {
            return String.format("StringIdentifier: %s", identifier);
        }
    }
}
