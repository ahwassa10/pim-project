package model.attributive.implementation;

import model.attributive.specification.DenseMap;
import model.attributive.specification.FunctionMap;

public final class DenseMaps {
    private DenseMaps() {}
    
    public static <T> T requireProperties(DenseMap<T, ?> denseMap, T attributer) {
        if (!denseMap.hasProperties(attributer)) {
            String msg = String.format("%s does not gain any properties from this DenseMap", attributer);
            throw new IllegalArgumentException(msg);
        }
        
        return attributer;
    }
    
    public static <T> T requireProperties(FunctionMap<T, ?> denseMap, T attributer) {
        if (!denseMap.hasProperties(attributer)) {
            String msg = String.format("%s does not gain any properties from this FunctionMap", attributer);
            throw new IllegalArgumentException(msg);
        }
        
        return attributer;
    }
    
    public static <U> U requireAttributes(DenseMap<?, U> denseMap, U holder) {
        if (!denseMap.hasAttributes(holder)) {
            String msg = String.format("%s does not gain any attributes from this DenseMap", holder);
            throw new IllegalArgumentException(msg);
        }
        
        return holder;
    }
    
    public static <U> U requireAttributes(FunctionMap<?, U> denseMap, U holder) {
        if (!denseMap.hasAttributes(holder)) {
            String msg = String.format("%s does not gain any attributes from this FunctionMap", holder);
            throw new IllegalArgumentException(msg);
        }
        
        return holder;
    }
}
