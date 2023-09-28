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
    
    default T requireRootNode(T node) {
        if (!contains(node) || !isRootNode(node)) {
            String msg = String.format("%s is not the root node of this tree", node);
            throw new IllegalArgumentException(msg);
        }
        return node;
    }
    
    default T requireInteriorNode(T node) {
        if (!contains(node) || !isInteriorNode(node)) {
            String msg = String.format("%s is not an interior node in this tree", node);
            throw new IllegalArgumentException(msg);
        }
        return node;
    }
    
    default T requireLeafNode(T node) {
        if (!contains(node) || !isLeafNode(node)) {
            String msg = String.format("%s is not a leaf node in this tree", node);
            throw new IllegalArgumentException(msg);
        }
        return node;
    }
    
    default T requireParentNode(T node) {
        if (!contains(node) || !isParentNode(node)) {
            String msg = String.format("%s is not a parent node in this tree", node);
            throw new IllegalArgumentException(msg);
        }
        return node;
    }
    
    default T requireExteriorNode(T node) {
        if (!contains(node) || !isExteriorNode(node)) {
            String msg = String.format("%s is not an exterior node in this tree", node);
            throw new IllegalArgumentException(msg);
        }
        return node;
    }
    
    default T requireChildNode(T node) {
        if (!contains(node) || !isChildNode(node)) {
            String msg = String.format("%s is not a child node in this tree", node);
            throw new IllegalArgumentException(msg);
        }
        return node;
    }
    
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