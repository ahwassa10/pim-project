package model.attributive.specification;

import java.util.Set;

public interface DenseMap<T, U> extends BiMap<T, U> {
    Set<U> getAttributions(T attributer);
    
    Set<T> getAttributes(U object);
}
