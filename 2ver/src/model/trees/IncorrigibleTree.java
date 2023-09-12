package model.trees;

import java.util.Set;

public interface IncorrigibleTree<T> extends Tree<T> {
    T grow(T child, T parent);
    
    T graft(T leaf, T parent);
    
    Set<T> trim(T leaf);
}
