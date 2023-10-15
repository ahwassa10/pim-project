package model.attributive.implementation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import model.attributive.specification.Forest;
import model.attributive.specification.Tree;
import model.attributive.specification.TreeNode;

public final class HashForest<T> implements Forest<T> {
    private Map<T, T> parents = new HashMap<>();
    // The parent of the key is the value.
    
    private Map<T, Set<T>> children = new HashMap<>();
    // The children of the key are in the elements of the value set.
    
    public HashForest() {}
    
    public HashForest(Tree<T> tree) {
        Iterator<Tree<T>> i = tree.iterateDFS();
        
        // Iterate though the root node.
        i.next();
        
        // Iterate though all the other nodes in the tree.
        while (i.hasNext()) {
            TreeNode<T> atNode = i.next().asNode();
            
            T object = atNode.getObject();
            T parent = atNode.getParent().getObject();
            
            parents.put(object, parent);
            children.computeIfAbsent(parent, p -> new HashSet<>()).add(object);
        }
    }
    
    public boolean hasParent(T node) {
        return parents.containsKey(node);
    }
    
    public T getParent(T node) {
        Forests.requireParent(this, node);
        
        return parents.get(node);
    }
    
    public TreeNode<T> atNode(T node) {
        return new NodeTree<T>(this, node);
    }
    
    public boolean hasChildren(T node) {
        return children.containsKey(node);
    }
    
    public Set<T> getChildren(T node) {
        Forests.requireChildren(this, node);
        
        return Collections.unmodifiableSet(children.get(node));
    }
    
    public Tree<T> atTree(T node) {
        return new NodeTree<T>(this, node);
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
}
