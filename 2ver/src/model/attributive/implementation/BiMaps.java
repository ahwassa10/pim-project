package model.attributive.implementation;

import model.attributive.specification.BiMap;

public final class BiMaps {
    private BiMaps() {}
    
    public static <T> T requireAttributions(BiMap<T, ?> biMap, T attributer) {
        if (!biMap.hasAttributions(attributer)) {
            String msg = String.format("%s does not make any attributions in this map", attributer);
            throw new IllegalArgumentException(msg);
        }
        
        return attributer;
    }
    
    public static <T> T requireNoAttributions(BiMap<T, ?> biMap, T attributer) {
        if (biMap.hasAttributions(attributer)) {
            String msg = String.format("%s already makes an attribution in this map", attributer);
            throw new IllegalArgumentException(msg);
        }
        
        return attributer;
    }
    
    public static <U> U requireAttributes(BiMap<?, U> biMap, U object) {
        if (!biMap.hasAttributes(object)) {
            String msg = String.format("%s does not gain any attributes from this map", object);
            throw new IllegalArgumentException(msg);
        }
        
        return object;
    }
}
