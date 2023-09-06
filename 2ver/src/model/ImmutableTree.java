package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class ImmutableTree<T> {
    private final Map<T, Set<T>> children = new HashMap<>();
    private final Map<T, T> parents = new HashMap<>();
    
    private final T root;
    
    public ImmutableTree(T root) {
        Objects.requireNonNull(root, "Root node object cannot be null");
        
        // Root is a child of root
        this.children.put(root, new HashSet<>());
        this.children.get(root).add(root);
        
        // Root is a parent of root
        this.parents.put(root, root);
        
        this.root = root;
    }
    
    public Set<T> cut(T node) {
        this.requireNonRootNode(node);
        this.requireOwnership(node);
        
        Set<T> removed = new HashSet<>();
        
        List<T> stack = new ArrayList<>();
        stack.add(node);
        
        while (stack.size() != 0) {
            T aNode = stack.get(stack.size() - 1);
            
            if (children.containsKey(aNode)) {
                for (T childNode : children.get(aNode)) {
                    stack.add(childNode);
                }
            } else {
                removed.add(this.removeLeafNode(aNode));
                stack.remove(stack.size() - 1);
            }
        }
        
        return removed;
    }
    
    public Set<T> getChildren(T parentNode) {
        this.requireOwnership(parentNode);
        return Collections.unmodifiableSet(children.get(parentNode));
    }
    
    public T getParent(T childNode) {
        this.requireOwnership(childNode);
        return parents.get(childNode);
    }
    
    public T getRoot() {
        return root;
    }
    
    public T grow(T node, T parentNode) {
        this.requireOwnership(parentNode);
        this.requireNoOwnership(node);
        
        children.computeIfAbsent(parentNode, p -> new HashSet<>()).add(node);
        parents.put(node, parentNode);
        
        return node;
    }
    
    public boolean owns(T object) {
        Objects.requireNonNull(object, "Node cannot be null");
        
        // If the object has a parent, then it must belong in the tree
        return parents.containsKey(object);
    }
    
    private T removeLeafNode(T leafNode) {
        T parentNode = parents.get(leafNode);
        Set<T> childNodes = children.get(parentNode);
        
        // Remove the parent -> child mapping
        childNodes.remove(leafNode);
        if (childNodes.size() == 0) {
            children.remove(parentNode);
        }
        
        // Remove the child -> parent mapping
        parents.remove(leafNode);
        
        return leafNode;
    }
    
    public T requireNonRootNode(T object) {
        if (this.root.equals(object)) {
            String msg = String.format("Cannot cut the RootNode<%s> of this tree", object);
            throw new IllegalArgumentException(msg);
        }
        return object;
    }
    
    public T requireNoOwnership(T object) {
        if (this.owns(object)) {
            String msg = String.format("Node<%s> already belongs to this tree", object);
            throw new IllegalArgumentException(msg);
        }
        return object;
    }
    
    public T requireOwnership(T object) {
        if (!this.owns(object)) {
            String msg = String.format("Node<%s> does not belong to this tree", object);
            throw new IllegalArgumentException(msg);
        }
        return object;
    }
    
    public T trim(T leafNode) {
        this.requireOwnership(leafNode);
        
        if (children.containsKey(leafNode)) {
            String msg = String.format("Node<%s> has child nodes in the tree", leafNode);
            throw new IllegalArgumentException(msg);
        } else {
            return this.removeLeafNode(leafNode);
        }
    }
}
