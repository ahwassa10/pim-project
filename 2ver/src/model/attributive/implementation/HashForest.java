package model.attributive.implementation;

import java.util.Iterator;
import java.util.Set;

import model.attributive.implementation.MemMappers.FunctionMapper;
import model.attributive.specification.Forest;
import model.attributive.specification.Tree;
import model.attributive.specification.TreeNode;

public final class HashForest<T> implements Forest<T> {
    // The parent of the key is the value.
    private final FunctionMapper<T, T> parents = MemMappers.functionMapper();
    
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
            
            parents.map(object, parent);
        }
    }
    
    public boolean hasParent(T node) {
        return parents.hasValues(node);
    }
    
    public T getParent(T node) {
        Forests.requireParent(this, node);
        
        return parents.anyValue(node);
    }
    
    public boolean hasChildren(T node) {
        return parents.inverse().hasValues(node);
    }
    
    public Set<T> getChildren(T node) {
        Forests.requireChildren(this, node);
        
        return parents.inverse().getValues(node);
    }
    
    public TreeNode<T> atNode(T node) {
        return new NodeTree<T>(this, node);
    }
    
    public Tree<T> atTree(T node) {
        return new NodeTree<T>(this, node);
    }
    
    public void attachRoot(T root, T parent) {
        Forests.requireRootNode(this, root);
        Forests.requireRootNode(this, parent);
        
        parents.map(root, parent);
    }
    
    public void attachSingle(T single, T parent) {
        Forests.requireSingleNode(this, single);
        
        parents.map(single, parent);
    }
    
    public T detach(T node) {
        Forests.requireParent(this, node);
        
        T parent = parents.anyValue(node);
        parents.unmap(node, parent);
        
        return parent;
    }
}
