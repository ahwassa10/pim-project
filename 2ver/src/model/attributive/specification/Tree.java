package model.attributive.specification;

import java.util.Iterator;
import java.util.Set;

public interface Tree<T> {
    default boolean isNode() {
        return true;
    }
    
    default boolean isLeafNode() {
        return hasChildren();
    }
    
    boolean hasChildren();
    
    Set<? extends Tree<T>> getChildren();
    
    Iterator<? extends Tree<T>> iterateBFS();
    
    Iterator<? extends Tree<T>> iterateDFS();
    
    TreeNode<T> asNode();
}
