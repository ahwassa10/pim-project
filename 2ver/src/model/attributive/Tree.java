package model.attributive;

import java.util.Set;

public interface Tree<T> {
    T getRoot();
    
    boolean hasParent(T node);
    
    boolean hasChildren(T node);
    
    T getParent(T node);
    
    Set<T> getChildren(T node);
}
