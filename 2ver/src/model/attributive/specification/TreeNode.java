package model.attributive.specification;

import java.util.Iterator;

public interface TreeNode<T> {
    T getObject();
    
    default boolean isNode() {
        return true;
    }
    
    default boolean isRootNode() {
        return !hasParent();
    }
    
    boolean hasParent();
    
    TreeNode<T> getParent();
    
    Iterator<? extends TreeNode<T>> iterateParents();
    
    TreeNode<T> getRoot();
}