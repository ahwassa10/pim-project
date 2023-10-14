package model.attributive.implementation;

import java.util.Iterator;

import model.attributive.specification.Forest;
import model.attributive.specification.TreeNode;

final class BasicTreeNode<T> implements TreeNode<T>{
    private final Forest<T> forest;
    
    private final T object;
    
    BasicTreeNode(Forest<T> forest, T object) {
        this.forest = forest;
        this.object = object;
    }
    
    public T getObject() {
        return object;
    }
    
    public boolean hasParent() {
        return forest.hasParent(object);
    }
    
    public TreeNode<T> getParent() {
        return new BasicTreeNode<T>(forest, forest.getParent(object));
    }
    
    public Iterator<? extends TreeNode<T>> iterateParents() {
        Iterator<T> i = forest.iterateParents(object);
        
        return new Iterator<BasicTreeNode<T>>() {
            public boolean hasNext() {
                return i.hasNext();
            }
            
            public BasicTreeNode<T> next() {
                return new BasicTreeNode<T>(forest, i.next());
            }
        };
    }
    
    public TreeNode<T> getRoot() {
        T root = object;
        
        while (forest.hasParent(root)) {
            root = forest.getParent(root);
        }
        
        return new BasicTreeNode<T>(forest, root);
    }
    
    public String toString() {
        return String.format("BasicTreeNode(%s)", object);
    }
}
