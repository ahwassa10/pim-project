package model.attributive.specification;

import java.util.Iterator;
import java.util.Set;

public interface DenseMap<T, U> {
    boolean hasProperties(T attributer);
    
    Set<U> getProperties(T attributer);
    
    Iterator<U> iterateProperties(T attributer);
    
    boolean hasAttributes(U holder);
    
    Set<T> getAttributes(U holder);
    
    Iterator<T> iterateAttributes(U holder);
    
    void apply(T attributer, U holder);
    
    void remove(T attributer, U holder);
    
    void remove(T attributer);
    
    void forget(U holder);
}
