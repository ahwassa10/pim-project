package model.trees;

import java.util.Set;

public interface MutableTree<T> extends Tree<T> {
    void grow(T child, T parent);
    
    T graft(T leaf, T parent);
    
    Set<T> trim(T leaf);
}