package model.attributive.implementation;

import model.attributive.specification.DenseMap;
import model.attributive.specification.FunctionMap;

public final class Maps {
    private Maps() {}
    
    public static <T> T requireAttributions(DenseMap<T, ?> denseMap, T attributer) {
        if (!denseMap.hasAttributions(attributer)) {
            String msg = String.format("%s does not make any attributions in this DenseMap", attributer);
            throw new IllegalArgumentException(msg);
        }
        
        return attributer;
    }
    
    public static <T> T requireAttributions(FunctionMap<T, ?> denseMap, T attributer) {
        if (!denseMap.hasAttributions(attributer)) {
            String msg = String.format("%s does not make an attribution in this FunctionMap", attributer);
            throw new IllegalArgumentException(msg);
        }
        
        return attributer;
    }
    
    public static <T> T requireNoAttributions(FunctionMap<T, ?> denseMap, T attributer) {
        if (denseMap.hasAttributions(attributer)) {
            String msg = String.format("%s already makes an attribution in this FunctionMap", attributer);
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
