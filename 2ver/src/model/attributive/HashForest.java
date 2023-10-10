package model.attributive;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class HashForest<T> implements Forest<T> {
    private Map<T, T> parents = new HashMap<>();
    // The parent of the key is the value.
    
    private Map<T, Set<T>> children = new HashMap<>();
    // The children of the key are in the elements of the value set.
    
    public HashForest() {}
   
    public boolean isNode(T node) {
        return true;
    }
    
    public boolean isRootNode(T node) {
        return !parents.containsKey(node);
    }
    
    public boolean isLeafNode(T node) {
        return !children.containsKey(node);
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
    
    public boolean isParentNode(T node) {
        return children.containsKey(node);
    }
    
    public boolean isChildNode(T node) {
        return parents.containsKey(node);
    }
    
    public T detach(T node) {
        Forests.requireChildNode(this, node);
        
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
    
    private class Node implements TreeNode<T> {
        private T object;
        
        private Node(T object) {
            this.object = object;
        }
        
        public T getObject() {
            return object;
        }
        
        public boolean hasParent() {
            return parents.containsKey(object);
        }
        
        public TreeNode<T> getParent() {
            Forests.requireChildNode(HashForest.this, object);
            
            return new Node(parents.get(object));
        }
        
        public boolean hasChildren() {
            return children.containsKey(object);
        }
        
        public Set<TreeNode<T>> getChildren() {
            Forests.requireParentNode(HashForest.this, object);
            
            return children.get(object).stream()
                    .map(child -> new Node(child))
                    .collect(Collectors.toSet());
        }
        
        public String toString() {
            return Objects.toString(object);
        }
    }
    
    public TreeNode<T> atNode(T node) {
        return new Node(node);
    }
    
}
