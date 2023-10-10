package model.attributive;

import java.util.Set;

public interface TreeNode<T> {
    T getObject();
    
    boolean hasParent();
    
    TreeNode<T> getParent();
    
    boolean hasChildren();
    
    Set<TreeNode<T>> getChildren();
}