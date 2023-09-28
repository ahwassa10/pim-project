package model.trees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    
    default List<T> getAncestors(T node) {
        List<T> ancestors = new ArrayList<>();
        
        T root = getRoot();
        T parent = getParent(node);
        while (!parent.equals(root)) {
            ancestors.add(parent);
            parent = getParent(parent);
        }
        
        Collections.reverse(ancestors);
        
        return ancestors;
    }
    
}
