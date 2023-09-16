package model.trees;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

public class HashTree<T> extends AbstractHashTree<T> implements MutableTree<T> {
    private final T root;
    
    public HashTree(T root) {
        this.root = root;
    }
    
    public T getRoot() {
        return root;
    }
    
    private void addNode(T node, T parent) {
        children.computeIfAbsent(parent, p -> new HashSet<>()).add(node);
        parents.put(node, parent);
    }
    
    private T removeNode(T node) {
        T parentNode = parents.get(node);
        Set<T> childNodes = children.get(parentNode);
        
        // Remove the parent -> child mapping
        childNodes.remove(node);
        if (childNodes.size() == 0) {
            // Delete the empty set too.
            // Most traits aren't going to have children, so there's no point
            // in keeping this objects around.
            children.remove(parentNode);
        }
        
        // Remove the child -> parent mapping
        parents.remove(node);
        
        return parentNode;
    }
    
    public void grow(T node, T parent) {
        this.requireAbsence(node);
        this.requirePresence(parent);
        
        addNode(node, parent);
    }
    
    public T graft(T node, T newParent) {
        this.requireChildNode(node);
        this.requirePresence(newParent);
        
        T parentNode = removeNode(node);
        addNode(node, newParent);
        return parentNode;
    }
    
    public Set<T> trim(T node) {
        this.requireChildNode(node);
        
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
                removeNode(aNode);
                stack.remove(stack.size() - 1);
                removed.add(aNode);
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
}
