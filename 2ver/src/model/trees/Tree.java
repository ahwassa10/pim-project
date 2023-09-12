package model.trees;

import java.util.Set;

public interface Tree<T> {
    boolean contains(T node);
    
    boolean isRootNode(T node);
    
    boolean isInteriorNode(T node);
    
    boolean isLeafNode(T node);
    
    boolean isParentNode(T node);
    
    boolean isExteriorNode(T node);
    
    boolean isChildNode(T node);
    
    T getRoot();
    
    T getParent(T node);
    
    Set<T> getChildren(T node);
}
