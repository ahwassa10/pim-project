package model.attributive.specification;

import java.util.Iterator;

public interface TreeNode<T> {
    T getObject();
    
    boolean hasParent();
    
    TreeNode<T> getParent();
    
    Iterator<TreeNode<T>> iterateParents();
    
    TreeNode<T> getRoot();
}