package model.attributive.implementation;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import model.attributive.specification.Forest;
import model.attributive.specification.Tree;
import model.attributive.specification.TreeNode;

class BasicTree<T> implements Tree<T>{
    private final Forest<T> forest;
    
    private final T root;
    
    BasicTree(Forest<T> forest, T root) {
        this.forest = forest;
        this.root = root;
    }
    
    public boolean hasChildren() {
        return forest.hasChildren(root);
    }
    
    public Set<? extends Tree<T>> getChildren() {
        return forest.getChildren(root).stream()
                .map(child -> new BasicTree<T>(forest, root))
                .collect(Collectors.toSet());
    }
    
    public Iterator<? extends Tree<T>> iterateBFS() {
        Iterator<T> i = forest.iterateBFS(root);
        
        return new Iterator<BasicTree<T>>() {
            public boolean hasNext() {
                return i.hasNext();
            }
            
            public BasicTree<T> next() {
                return new BasicTree<T>(forest, i.next());
            }
        };
    }
    
    public Iterator<? extends Tree<T>> iterateDFS() {
        Iterator<T> i = forest.iterateDFS(root);
        
        return new Iterator<BasicTree<T>>() {
            public boolean hasNext() {
                return i.hasNext();
            }
            
            public BasicTree<T> next() {
                return new BasicTree<T>(forest, i.next());
            }
        };
    }
    
    public TreeNode<T> asNode() {
        return forest.atNode(root);
    }
}
