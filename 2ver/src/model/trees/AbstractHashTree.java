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
    
    public boolean isRootNode(T node) {
        return !parents.containsKey(node);
    }
    
    public boolean isInteriorNode(T node) {
        return isParentNode(node) && isChildNode(node);
    }
    
    public boolean isLeafNode(T node) {
        return !children.containsKey(node);
    }
    
    public boolean isParentNode(T node) {
        return children.containsKey(node);
    }
    
    public boolean isExteriorNode(T node) {
        return isRootNode(node) || isLeafNode(node);
    }
    
    public boolean isChildNode(T node) {
        return parents.containsKey(node);
    }
    
    public T getParent(T node) {
        Trees.requireChildNode(this, node);
        return parents.get(node);
    }
    
    public Set<T> getChildren(T node) {
        Trees.requireParentNode(this, node);
        return Collections.unmodifiableSet(children.get(node));
    }
}