package model.attributive;

import java.util.Iterator;
import java.util.Set;

public interface TreeNode<T> {
    T getObject();
    
    default boolean isNode() {
        return true;
    }
    
    boolean isRootNode();
    
    boolean isLeafNode();
    
    boolean isParticipatingNode();
    
    boolean isSingleNode();
    
    boolean hasParent();
    
    boolean hasChildren();
    
    TreeNode<T> getParent();
    
    Set<? extends TreeNode<T>> getChildren();
    
    TreeNode<T> getRoot();
    
    TreeNode<T> asParentlessNode();
    
    TreeNode<T> asChildlessNode();
    
    Iterator<? extends TreeNode<T>> parentIterator();
    
    Iterator<? extends TreeNode<T>> BFSIterator();
    
    Iterator<? extends TreeNode<T>> DFSIterator();
}