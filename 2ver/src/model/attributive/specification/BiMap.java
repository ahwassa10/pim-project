package model.attributive.specification;

import java.util.Iterator;

public interface BiMap<T, U> {
    boolean hasAttributions(T attributer);
    
    Iterator<U> iterateAttributions(T attributer);
    
    boolean hasAttributes(U object);
    
    Iterator<T> iterateAttributes(U object);
    
    void apply(T attributer, U object);
    
    void remove(T attributer, U object);
    
    void remove(T attributer);
    
    void forget(U object);
}
