package model.attributive;

import java.util.Iterator;
import java.util.Set;

final class ChildlessTreeNode<T> extends BasicTreeNode<T> {
    ChildlessTreeNode(Forest<T> forest, T object) {
        super(forest, object);
    }
    
    public boolean isLeafNode() {
        return true;
    }
    
    public boolean hasChildren() {
        return false;
    }
    
    public Set<TreeNode<T>> getChildren() {
        String msg = String.format("%s does not have any child nodes", this);
        throw new IllegalArgumentException(msg);
    }
    
    public Iterator<ChildlessTreeNode<T>> parentIterator() {
        Iterator<T> i = getForest().parentIterator(getObject());
        
        return new Iterator<ChildlessTreeNode<T>>() {
            public boolean hasNext() {
                return i.hasNext();
            }
            
            public ChildlessTreeNode<T> next() {
                return new ChildlessTreeNode<T>(getForest(), i.next());
            }
        };
    }
    
    public Iterator<ChildlessTreeNode<T>> BFSIterator() {
        Iterator<T> i = getForest().BFSIterator(getObject());
        
        return new Iterator<ChildlessTreeNode<T>>() {
            public boolean hasNext() {
                return i.hasNext();
            }
            
            public ChildlessTreeNode<T> next() {
                return new ChildlessTreeNode<T>(getForest(), i.next());
            }
        };
    }
    
    public Iterator<? extends TreeNode<T>> DFSIterator() {
        Iterator<T> i = getForest().DFSIterator(getObject());
        
        return new Iterator<ChildlessTreeNode<T>>() {
            public boolean hasNext() {
                return i.hasNext();
            }
            
            public ChildlessTreeNode<T> next() {
                return new ChildlessTreeNode<T>(getForest(), i.next());
            }
        };
    }
}
