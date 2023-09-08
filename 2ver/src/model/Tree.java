package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Tree<I, R extends I, N extends I> {
    private final Map<I, Set<N>> children = new HashMap<>();
    private final Map<N, I> parents = new HashMap<>();
    
    private final R root;
    
    public Tree(R root) {
        Objects.requireNonNull(root, "Root cannot be null");
        this.root = root;
    }
    
    public boolean contains(I object) {
        Objects.requireNonNull(object, "Node cannot be null");
        
        return object.equals(root) || parents.containsKey(object);
    }
    
    public Set<N> cut(N node) {
        this.requirePresence(node);
        
        Set<N> removed = new HashSet<>();
        
        List<N> stack = new ArrayList<>();
        stack.add(node);
        
        while (stack.size() != 0) {
            N aNode = stack.get(stack.size() - 1);
            
            if (children.containsKey(aNode)) {
                // A node can only be removed if it has no children.
                for (N childNode : children.get(aNode)) {
                    stack.add(childNode);
                }
            } else {
                removed.add(this.removeLeafNode(aNode));
                stack.remove(stack.size() - 1);
            }
        }
        
        return removed;
    }
    
    public Set<N> getChildren(I parentNode) {
        this.requirePresence(parentNode);
        
        return Collections.unmodifiableSet(children.get(parentNode));
    }
    
    public I getParent(N childNode) {
        this.requirePresence(childNode);
        
        return parents.get(childNode);
    }
    
    public R getRoot() {
        return this.root;
    }
    
    public N grow(N node, I parent) {
        this.requirePresence(parent);
        this.requireAbsence(node);
        
        children.computeIfAbsent(parent, p -> new HashSet<>()).add(node);
        parents.put(node, parent);
        
        return node;
    }
    
    private N removeLeafNode(N leafNode) {
        I parentNode = parents.get(leafNode);
        Set<N> childNodes = children.get(parentNode);
        
        // Remove the parent -> child mapping
        childNodes.remove(leafNode);
        if (childNodes.size() == 0) {
            children.remove(parentNode);
        }
        
        // Remove the child -> parent mapping
        parents.remove(leafNode);
        
        return leafNode;
    }
    
    public I requireAbsence(I object) {
        if (this.contains(object)) {
            String msg = String.format("This tree already contains Node<%s>", object);
            throw new IllegalArgumentException(msg);
        }
        return object;
    }
    
    public I requirePresence(I object) {
        if (!this.contains(object)) {
            String msg = String.format("This tree does not contain Node<%s>", object);
            throw new IllegalArgumentException(msg);
        }
        return object;
    }
    
    public N trim(N leafNode) {
        this.requirePresence(leafNode);
        
        if (children.containsKey(leafNode)) {
            String msg = String.format("Node<%s> has child nodes in the tree", leafNode);
            throw new IllegalArgumentException(msg);
        } else {
            return this.removeLeafNode(leafNode);
        }
    }
}
