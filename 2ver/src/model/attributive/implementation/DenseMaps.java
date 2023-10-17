package model.attributive.implementation;

import model.attributive.specification.DenseMap;

public final class DenseMaps {
    private DenseMaps() {}
    
    public static <T> T requireAttributions(DenseMap<T, ?> denseMap, T attributer) {
        if (!denseMap.hasProperties(attributer)) {
            String msg = String.format("%s does not gain any attributions from this DenseMap", attributer);
            throw new IllegalArgumentException(msg);
        }
        
        return attributer;
    }
    
    public static <U> U requireProperties(DenseMap<?, U> denseMap, U holder) {
        if (!denseMap.hasAttributes(holder)) {
            String msg = String.format("%s does not gain any properties from this DenseMap", holder);
            throw new IllegalArgumentException(msg);
        }
        
        return holder;
    }
}
