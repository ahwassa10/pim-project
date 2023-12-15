package model.presence;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Stream;

public interface MaybeSome<T> {
    boolean has();
    int count();
    
    T any();
    Iterator<T> iterate();
    Stream<T> stream();
    List<T> asList();
    Set<T> asSet();
    
    static <T> MaybeSome<T> of(Collection<T> values) {
        return new MaybeSome<T>() {
            private final Set<T> vals = Set.copyOf(values);
            
            public boolean has() {
                return vals.size() > 0;
            }
            
            public int count() {
                return vals.size();
            }
            
            public T any() {
                if (vals.size() == 0) {
                    throw new NoSuchElementException();
                } else {
                    return vals.iterator().next();
                }
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
