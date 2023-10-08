package model.attributive;

import java.util.Set;

public interface Forest<T> {
    boolean isNode(T node);
    
    boolean isRootNode(T node);
    
    boolean isLeafNode(T node);
    
    boolean isSingleNode(T node);
    
    void attachRoot(T root, T parent);
    
    void attachSingle(T single, T parent);
    
    boolean hasParent(T node);
    
    boolean hasChildren(T node);
    
    T getParent(T node);
    
    Set<T> getChildren(T node);
    
    T detach(T child);
    
    Tree<T> treeAt(T node);
}
