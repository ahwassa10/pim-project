package model.forest;

import java.util.Iterator;
import java.util.Set;

public interface Tree<T> {
    boolean hasChildren();
    
    Set<Tree<T>> getChildren();
    
    Iterator<Tree<T>> iterateBFS();
    
    Iterator<Tree<T>> iterateDFS();
    
    Node<T> asNode();
}
