package model.trees;

import java.util.Set;

public interface IncorrigibleTree<T> extends Tree<T> {
    T grow(T child, T parent);
    
    T trim(T child);
    
    Set<T> cut(T child);
}
