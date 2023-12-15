package model.presence;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import model.util.SingleIterators;

public interface One<T> extends Maybe<T>, Some<T> {
    static <T> One<T> of(T value) {
        return new One<T>() {
            private final T val = value;
            
            public One<T> certainly() {
                return this;
            }
            
            public int count() {
                return 1;
            }
            
            public T any() {
                return val;
            }
            
            public Iterator<T> iterate() {
                return SingleIterators.of(val);
            }
            
            public Stream<T> stream() {
                return Stream.of(val);
            }
            
            public List<T> asList() {
                return List.of(val);
            }
            
            public Set<T> asSet() {
                return Set.of(val);
            }
        };
    }
}
