package model.presence;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public interface Some<T> extends MaybeSome<T> {
    default boolean has() {
        return true;
    }
    
    static <T> Some<T> of(Collection<T> values) {
        if (values.size() == 0) {
            throw new IllegalArgumentException("Collection must contain one or more values");
        }
        
        return new Some<T>() {
            private final Set<T> vals = Set.copyOf(values);
            
            public int count() {
                return vals.size();
            }
            
            public T any() {
                return vals.iterator().next();
            }
            
            public Iterator<T> iterate() {
                return vals.iterator();
            }
            
            public Stream<T> stream() {
                return vals.stream();
            }
            
            public List<T> asList() {
                return List.copyOf(vals);
            }
            
            public Set<T> asSet() {
                return vals;
            }
        };
    }
}
