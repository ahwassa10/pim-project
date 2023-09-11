package model.trees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;

public class HashTree<T> implements IncorrigibleTree<T> {
    private final Map<T, Set<T>> children = new HashMap<>();
    
    private final Map<T, T> parents = new HashMap<>();
    
    private final T root;
    
    public HashTree(T root) {
        this.root = root;
    }
    
    public T getRoot() {
        return root;
    }
    
    public boolean contains(T object) {
        // Using Objects.equals() ensures that null rooted trees work.
        return Objects.equals(root, object) || parents.containsKey(object);
    }
    
    public boolean hasParent(T object) {
        return parents.containsKey(object);
    }
    
    public T getParent(T object) {
        this.requirePresence(object);
        this.requireNonRoot(object);
        
        return parents.get(object);
    }
    
    public boolean hasChildren(T object) {
        return children.containsKey(object);
    }
    
    public Set<T> getChildren(T object) {
        this.requirePresence(object);
        
        if (children.containsKey(object)) {
            return Collections.unmodifiableSet(children.get(object));
        } else {
            return Set.of();
        }
    }
    
    public T grow(T node, T parent) {
        this.requireAbsence(node);
        this.requirePresence(parent);
        
        children.computeIfAbsent(parent, p -> new HashSet<>()).add(node);
        parents.put(node, parent);
        
        return node;
    }
    
    private T removeLeafNode(T leafObject) {
        T parentNode = parents.get(leafObject);
        Set<T> childNodes = children.get(parentNode);
        
        // Remove the parent -> child mapping
        childNodes.remove(leafObject);
        if (childNodes.size() == 0) {
            // Delete the empty set too.
            // Most traits aren't going to have children, so there's no point
            // in keeping this objects around.
            children.remove(parentNode);
        }
        
        // Remove the child -> parent mapping
        parents.remove(leafObject);
        
        return leafObject;
    }
    
    public T trim(T leafNode) {
        this.requirePresence(leafNode);
        this.requireNonRoot(leafNode);
        
        if (children.containsKey(leafNode)) {
            String msg = String.format("%s has child nodes in the tree", leafNode);
            throw new IllegalArgumentException(msg);
        
        } else {
            return this.removeLeafNode(leafNode);
        }
    }
    
    public Set<T> cut(T node) {
        this.requirePresence(node);
        this.requireNonRoot(node);
        
        Set<T> removed = new HashSet<>();
        
        List<T> stack = new ArrayList<>();
        stack.add(node);
        
        while (stack.size() != 0) {
            T aNode = stack.get(stack.size() - 1);
            
            if (children.containsKey(aNode)) {
                // A node can only be removed if it has no children.
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
    
    public void print() {
        System.out.println(children);
        System.out.println(parents);
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        for (Entry<T, Set<T>> entry : children.entrySet()) {
            sb.append(entry.getKey());
            sb.append("<-->");
            sb.append(entry.getValue());
            sb.append("\n");
        }
        
        return sb.toString();
    }
    
    private T requireAbsence(T object) {
        if (this.contains(object)) {
            String msg = String.format("This tree already contains %s", object);
            throw new IllegalArgumentException(msg);
        } 
        return object;
    }
    
    private T requirePresence(T object) {
        if (!this.contains(object)) {
            String msg = String.format("This tree does not contain %s", object);
            throw new IllegalArgumentException(msg);
        }
        return object;
    }
    
    private T requireNonRoot(T object) {
        if (getRoot().equals(object)) {
            String msg = String.format("%s is the root of this tree", object);
            throw new IllegalArgumentException(msg);
        }
        return object;
    }
}
