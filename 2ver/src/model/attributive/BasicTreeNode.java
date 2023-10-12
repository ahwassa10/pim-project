package model.attributive;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

class BasicTreeNode<T> implements TreeNode<T>{
    private Forest<T> forest;
    
    private T object;
    
    BasicTreeNode(Forest<T> forest, T object) {
        this.forest = forest;
        this.object = object;
    }
    
    public T getObject() {
        return object;
    }
    
    Forest<T> getForest() {
        return forest;
    }
    
    public boolean isRootNode() {
        return forest.isRootNode(object);
    }
    
    public boolean isLeafNode() {
        return forest.isLeafNode(object);
    }
    
    public boolean isParticipatingNode() {
        return forest.isParticipatingNode(object);
    }
    
    public boolean isSingleNode() {
        return forest.isSingleNode(object);
    }
    
    public boolean hasParent() {
        return forest.hasParent(object);
    }
    
    public boolean hasChildren() {
        return forest.hasChildren(object);
    }
    
    public TreeNode<T> getParent() {
        return new BasicTreeNode<T>(forest, forest.getParent(object));
    }
    
    public Set<TreeNode<T>> getChildren() {
        return forest.getChildren(object).stream()
                .map(child -> new BasicTreeNode<T>(forest, child))
                .collect(Collectors.toSet());
    }
    
    public TreeNode<T> getRoot() {
        return new BasicTreeNode<T>(forest, forest.getRoot(object));
    }
    
    public TreeNode<T> asParentlessNode() {
        return new ParentlessTreeNode<T>(forest, object);
    }
    
    public TreeNode<T> asChildlessNode() {
        return new ChildlessTreeNode<T>(forest, object);
    }
    
    public Iterator<? extends TreeNode<T>> parentIterator() {
        Iterator<T> i = forest.parentIterator(object);
        
        return new Iterator<BasicTreeNode<T>>() {
            public boolean hasNext() {
                return i.hasNext();
            }
            
            public BasicTreeNode<T> next() {
                return new BasicTreeNode<T>(forest, i.next());
            }
        };
    }
    
    public Iterator<? extends TreeNode<T>> BFSIterator() {
        Iterator<T> i = forest.BFSIterator(object);
        
        return new Iterator<BasicTreeNode<T>>() {
            public boolean hasNext() {
                return i.hasNext();
            }
            
            public BasicTreeNode<T> next() {
                return new BasicTreeNode<T>(forest, i.next());
            }
        };
    }
    
    public Iterator<? extends TreeNode<T>> DFSIterator() {
        Iterator<T> i = forest.DFSIterator(object);
        
        return new Iterator<BasicTreeNode<T>>() {
            public boolean hasNext() {
                return i.hasNext();
            }
            
            public BasicTreeNode<T> next() {
                return new BasicTreeNode<T>(forest, i.next());
            }
        };
    }
    
    public String toString() {
        return String.format("TreeNode(%s)", object);
    }
}
