package model.attributive.implementation;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import model.attributive.specification.Forest;
import model.attributive.specification.Tree;
import model.attributive.specification.TreeNode;

final class NodeTree<T> implements TreeNode<T>, Tree<T>{
    private final Forest<T> forest;
    
    private final T object;
    
    NodeTree(Forest<T> forest, T object) {
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
        return new NodeTree<T>(forest, forest.getParent(object));
    }
    
    public Iterator<TreeNode<T>> iterateParents() {
        Iterator<T> i = forest.iterateParents(object);
        
        return new Iterator<TreeNode<T>>() {
            public boolean hasNext() {
                return i.hasNext();
            }
            
            public NodeTree<T> next() {
                return new NodeTree<T>(forest, i.next());
            }
        };
    }
    
    public TreeNode<T> getRoot() {
        T root = object;
        
        while (forest.hasParent(root)) {
            root = forest.getParent(root);
        }
        
        return new NodeTree<T>(forest, root);
    }
    
    public boolean hasChildren() {
        return forest.hasChildren(object);
    }
    
    public Set<Tree<T>> getChildren() {
        return forest.getChildren(object).stream()
                .map(child -> new NodeTree<T>(forest, object))
                .collect(Collectors.toSet());
    }
    
    public Iterator<Tree<T>> iterateBFS() {
        Iterator<T> i = forest.iterateBFS(object);
        
        return new Iterator<Tree<T>>() {
            public boolean hasNext() {
                return i.hasNext();
            }
            
            public NodeTree<T> next() {
                return new NodeTree<T>(forest, i.next());
            }
        };
    }
    
    public Iterator<Tree<T>> iterateDFS() {
        Iterator<T> i = forest.iterateDFS(object);
        
        return new Iterator<Tree<T>>() {
            public boolean hasNext() {
                return i.hasNext();
            }
            
            public NodeTree<T> next() {
                return new NodeTree<T>(forest, i.next());
            }
        };
    }
    
    public TreeNode<T> asNode() {
        return this;
    }
    
    public String toString() {
        return object.toString();
    }
}
