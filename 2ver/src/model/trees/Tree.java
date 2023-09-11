package model.trees;

import java.util.Set;

public interface Tree<T> {
    T getRoot();
    
    boolean contains(T object);
    
    boolean hasParent(T object);
    
    T getParent(T object);
    
    boolean hasChildren(T object);
    
    Set<T> getChildren(T object);
}
