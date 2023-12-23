package model.presence;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Stream;

public interface None<T> extends Maybe<T> {
    static <T> None<T> of() {
        return new None<T>() {
            public One<T> certainly() {
                throw new NoSuchElementException();
            }
            
            public boolean hasAny() {
                return false;
            }
            
            public boolean has(T value) {
                return false;
            }
            
            public int count() {
                return 0;
            }
            
            public Iterator<T> iterate() {
                return Collections.emptyIterator();
            }
            
            public Stream<T> stream() {
                return Stream.of();
            }
            
            public List<T> asList() {
                return List.of();
            }
            
            public Set<T> asSet() {
                return Set.of();
            }
        };
    }
}
