package model.metadata;

import java.util.Iterator;
import java.util.Set;

public interface ValueTrait<T> extends Trait {
    int countValues();
    Iterator<T> iterateValues();
    T anyValue();
    Set<T> getValues();
}
