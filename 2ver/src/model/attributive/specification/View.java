package model.attributive.specification;

import java.util.Iterator;
import java.util.Set;

public interface View<X, Y> {
    boolean hasMappings(X object);
    
    Set<Y> getMappings(X object);
    
    Iterator<Y> iterateMappings(X object);
}
