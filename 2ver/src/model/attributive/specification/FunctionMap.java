package model.attributive.specification;

import java.util.Iterator;
import java.util.Set;

public interface FunctionMap<T, U> {
    boolean hasAttributions(T attributer);
    
    U getAttribution(T attributer);
    
    boolean hasAttributes(U object);
    
    Set<T> getAttributes(U object);
    
    Iterator<T> iterateAttributes(U object);
    
    void apply(T attributer, U object);
    
    void remove(T attributer, U object);
    
    void remove(T attributer);
    
    void forget(U object);
}