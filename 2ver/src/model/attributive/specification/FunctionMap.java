package model.attributive.specification;

import java.util.Iterator;
import java.util.Set;

public interface FunctionMap<T, U> {
    boolean hasProperties(T attributer);
    
    U getProperty(T attributer);
    
    boolean hasAttributes(U holder);
    
    Set<T> getAttributes(U holder);
    
    Iterator<T> iterateAttributes(U holder);
    
    void apply(T attributer, U holder);
    
    void remove(T attributer, U holder);
    
    void remove(T attributer);
    
    void forget(U holder);
}
