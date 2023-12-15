package model.presence;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Stream;

public interface None<T> extends Maybe<T> {
    static <T> Maybe<T> of() {
        return new Maybe<T>() {
            public boolean has() {
                return false;
            }
            
            public int count() {
                return 0;
            }
            
            public T any() {
                throw new NoSuchElementException();
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
