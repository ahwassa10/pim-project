package model.attributive;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class HashForest<T> implements Forest<T> {
    private Map<T, T> parents = new HashMap<>();
    // The parent of the key is the value.
    
    private Map<T, Set<T>> children = new HashMap<>();
    // The children of the key are in the elements of the value set.
    
    public HashForest() {}
    
    public boolean isRootNode(T node) {
        return !parents.containsKey(node);
    }
    
    public boolean isLeafNode(T node) {
        return !children.containsKey(node);
    }
    
    public boolean isParticipatingNode(T node) {
        return parents.containsKey(node) || children.containsKey(node);
    }
    
    public boolean isSingleNode(T node) {
        return !parents.containsKey(node) && !children.containsKey(node);
    }
    
    public void attachRoot(T root, T parent) {
        Forests.requireRootNode(this, root);
        Forests.requireRootNode(this, parent);
        
        parents.put(root, parent);
        children.computeIfAbsent(parent, p -> new HashSet<>()).add(root);
    }
    
    public void attachSingle(T single, T parent) {
        Forests.requireSingleNode(this, single);
        
        parents.put(single, parent);
        children.computeIfAbsent(parent, p -> new HashSet<>()).add(single);
    }
    
    public boolean hasParent(T node) {
        return parents.containsKey(node);
    }
    
    public boolean hasChildren(T node) {
        return children.containsKey(node);
    }
    
    public T getParent(T node) {
        Forests.requireParent(this, node);
        
        return parents.get(node);
    }
    
    public Set<T> getChildren(T node) {
        Forests.requireChildren(this, node);
        
        return Collections.unmodifiableSet(children.get(node));
    }
    
    public T detach(T node) {
        Forests.requireParent(this, node);
        
        // Remove the child -> parent mapping
        T parentNode = parents.remove(node);
        
        // Remove the parent -> child mapping
        Set<T> childNodes = children.get(parentNode);
        childNodes.remove(node);
        
        // If the parent node doesn't have any more children, delete the set.
        if (childNodes.size() == 0) {
            children.remove(parentNode);
        }
        
        return parentNode;
    }
    
    public TreeNode<T> asNode(T node) {
        return new BasicTreeNode<T>(this, node);
    }
}
