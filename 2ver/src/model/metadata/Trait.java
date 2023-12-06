package model.metadata;

import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public interface Trait<T> {
    int countValues();
    Iterator<T> iterateValues();
    Set<T> getValues();
    T anyValue();
    
    UUID getTraitID();
}
