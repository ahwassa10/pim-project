package model.attributive.specification;

import java.util.Iterator;
import java.util.Set;

public interface DenseMap<T, U> {
    boolean hasAttributions(T attributer);
    
    Set<U> getAttributions(T attributer);
    
    Iterator<U> iterateAttributions(T attributer);
    
    boolean hasProperties(U holder);
    
    Set<T> getProperties(U holder);
    
    Iterator<T> iterateProperties(U holder);
    
    void attribute(T attributer, U holder);
    
    void remove(T attributer, U holder);
    
    void remove(T attributer);
    
    void forget(U holder);
}
