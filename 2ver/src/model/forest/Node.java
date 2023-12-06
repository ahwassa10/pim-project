package model.forest;

import java.util.Iterator;

public interface Node<T> {
    T getObject();
    
    boolean hasParent();
    
    Node<T> getParent();
    
    Iterator<Node<T>> iterateParents();
    
    Node<T> getRoot();
}