package model.attributive.specification;

import java.util.Set;

public interface FunctionMap<T, U> extends BiMap<T, U> {
    U getAttribution(T attributer);
    
    Set<T> getAttributes(U object);
}
