package model.presence;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public interface MaybeSome<T> {
    Some<T> certainly();
    
    boolean has();
    int count();
    
    Iterator<T> iterate();
    Stream<T> stream();
    List<T> asList();
    Set<T> asSet();
}