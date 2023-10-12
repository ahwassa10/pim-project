package model.attributive;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public interface Forest<T> {
    default boolean isNode(T node) {
        return true;
    }
    
    boolean isRootNode(T node);
    
    boolean isLeafNode(T node);
    
    boolean isParticipatingNode(T node);
    
    boolean isSingleNode(T node);
    
    boolean hasParent(T node);
    
    boolean hasChildren(T node);
    
    T getParent(T node);
    
    Set<T> getChildren(T node);
    
    default T getRoot(T node) {
        T root = node;
        
        while (hasParent(root)) {
            root = getParent(root);
        }
        
        return root;
    }
    
    void attachRoot(T root, T parent);
    
    void attachSingle(T single, T parent);
    
    T detach(T child);
    
    TreeNode<T> asNode(T node);
    
    default Iterator<T> parentIterator(T node) {
        return new Iterator<T>() {
            private T atNode = node;
            
            public boolean hasNext() {
                return hasParent(atNode);
            }
            
            public T next() {
                if (!hasParent(atNode)) {
                    throw new NoSuchElementException();
                } else {
                    atNode = getParent(atNode);
                    return atNode;
                }
            }
        };
    }
    
    default Iterator<T> BFSIterator(T node) {
        Deque<T> queue = new ArrayDeque<>();
        queue.addLast(node);
        
        return new Iterator<T>() {
            public boolean hasNext() {
                return queue.size() > 0;
            }
            
            public T next() {
                // Automatically throws a NoSuchElementException.
                T node = queue.removeFirst();
                if (hasChildren(node)) {
                    getChildren(node).forEach(child -> queue.addLast(node));
                }
                
                return node;
            }
        };
    }
    
    default Iterator<T> DFSIterator(T node) {
        Deque<T> stack = new ArrayDeque<>();
        stack.addLast(node);
        
        return new Iterator<T>() {
            public boolean hasNext() {
                return stack.size() > 0;
            }
            
            public T next() {
                // Automatically throws a NoSuchElementException.
                T node = stack.removeLast();
                if (hasChildren(node)) {
                    getChildren(node).forEach(child -> stack.addLast(child));
                }
                
                return node;
            }
        };
    }
}
