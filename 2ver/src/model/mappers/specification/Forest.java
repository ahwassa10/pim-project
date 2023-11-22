package model.mappers.specification;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public interface Forest<T> {
    default boolean isNode(T node) {
        return true;
    }
    
    default boolean isRootNode(T node) {
        return !hasParent(node);
    }
    
    default boolean isLeafNode(T node) {
        return !hasChildren(node);
    }
    
    default boolean isParticipatingNode(T node) {
        return hasParent(node) || hasChildren(node);
    }
    
    default boolean isSingleNode(T node) {
        return !hasParent(node) && !hasChildren(node);
    }
    
    boolean hasParent(T node);
    
    T getParent(T node);
    
    default Iterator<T> iterateParents(T node) {
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
    
    boolean hasChildren(T node);
    
    Set<T> getChildren(T node);
    
    default Iterator<T> iterateBFS(T node) {
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
                    getChildren(node).forEach(child -> queue.addLast(child));
                }
                
                return node;
            }
        };
    }
    
    default Iterator<T> iterateDFS(T node) {
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
    
    TreeNode<T> atNode(T node);
    
    Tree<T> atTree(T node);
    
    void attachRoot(T root, T parent);
    
    void attachSingle(T single, T parent);
    
    T detach(T child);
}
