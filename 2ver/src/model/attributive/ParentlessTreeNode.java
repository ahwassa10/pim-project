package model.attributive;

import java.util.Iterator;

final class ParentlessTreeNode<T> extends BasicTreeNode<T> {
    ParentlessTreeNode(Forest<T> forest, T node) {
        super(forest, node);
    }
    
    public boolean isRootNode() {
        return true;
    }
    
    public boolean hasParent() {
        return false;
    }
    
    public ParentlessTreeNode<T> getParent() {
        String msg = String.format("%s does not have a parent node", this);
        throw new IllegalArgumentException(msg);
    }
    
    public Iterator<ParentlessTreeNode<T>> parentIterator() {
        Iterator<T> i = getForest().parentIterator(getObject());
        
        return new Iterator<ParentlessTreeNode<T>>() {
            public boolean hasNext() {
                return i.hasNext();
            }
            
            public ParentlessTreeNode<T> next() {
                return new ParentlessTreeNode<T>(getForest(), i.next());
            }
        };
    }
    
    public Iterator<ParentlessTreeNode<T>> BFSIterator() {
        Iterator<T> i = getForest().BFSIterator(getObject());
        
        return new Iterator<ParentlessTreeNode<T>>() {
            public boolean hasNext() {
                return i.hasNext();
            }
            
            public ParentlessTreeNode<T> next() {
                return new ParentlessTreeNode<T>(getForest(), i.next());
            }
        };
    }
    
    public Iterator<ParentlessTreeNode<T>> DFSIterator() {
        Iterator<T> i = getForest().DFSIterator(getObject());
        
        return new Iterator<ParentlessTreeNode<T>>() {
            public boolean hasNext() {
                return i.hasNext();
            }
            
            public ParentlessTreeNode<T> next() {
                return new ParentlessTreeNode<T>(getForest(), i.next());
            }
        };
    }
    
    public String toString() {
        return String.format("ParentlessTreeNode(%s)", getObject());
    }
}
