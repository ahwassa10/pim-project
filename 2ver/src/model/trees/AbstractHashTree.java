package model.trees;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractHashTree<T> implements Tree<T> {
    protected final Map<T, Set<T>> children = new HashMap<>();
    
    protected final Map<T, T> parents = new HashMap<>();
    
    public boolean contains(T node) {
        return Objects.equals(getRoot(), node) || parents.containsKey(node);
    }
    
    public T requireAbsence(T node) {
        if (contains(node)) {
            String msg = String.format("%s is present in this tree", node);
            throw new IllegalArgumentException(msg);
        }
        return node;
    }
    
    public T requirePresence(T node) {
        if (!contains(node)) {
            String msg = String.format("%s is not present in this tree", node);
            throw new IllegalArgumentException(msg);
        }
        return node;
    }
    
    public boolean isRootNode(T node) {
        return !parents.containsKey(node);
    }
    
    public T requireRootNode(T node) {
        if (!contains(node) || !isRootNode(node)) {
            String msg = String.format("%s is not the root node in this tree", node);
            throw new IllegalArgumentException(msg);
        }
        return node;
    }
    
    public boolean isInteriorNode(T node) {
        return isParentNode(node) && isChildNode(node);
    }
    
    public T requireInteriorNode(T node) {
        if (!contains(node) || !isInteriorNode(node)) {
            String msg = String.format("%s is not an interior node in this tree", node);
            throw new IllegalArgumentException(msg);
        }
        return node;
    }
    
    public boolean isLeafNode(T node) {
        return !children.containsKey(node);
    }
    
    public T requireLeafNode(T node) {
        if (!contains(node) || !isLeafNode(node)) {
            String msg = String.format("%s is not a leaf node in this tree", node);
            throw new IllegalArgumentException(msg);
        }
        return node;
    }
    
    public boolean isParentNode(T node) {
        return children.containsKey(node);
    }
    
    public T requireParentNode(T node) {
        if (!contains(node) || !isParentNode(node)) {
            String msg = String.format("%s is not a parent node in this tree", node);
            throw new IllegalArgumentException(msg);
        }
        return node;
    }
    
    public boolean isExteriorNode(T node) {
        return isRootNode(node) || isLeafNode(node);
    }
    
    public T requireExteriorNode(T node) {
        if (!contains(node) || !isExteriorNode(node)) {
            String msg = String.format("%s is not an exterior node in this tree", node);
            throw new IllegalArgumentException(msg);
        }
        return node;
    }
    
    public boolean isChildNode(T node) {
        return parents.containsKey(node);
    }
    
    public T requireChildNode(T node) {
        if (!contains(node) || !isChildNode(node)) {
            String msg = String.format("%s is not a child node in this tree", node);
            throw new IllegalArgumentException(msg);
        }
        return node;
    }
    
    public T getParent(T node) {
        requireChildNode(node);
        return parents.get(node);
    }
    
    public Set<T> getChildren(T node) {
        requireParentNode(node);
        return Collections.unmodifiableSet(children.get(node));
    }
}
