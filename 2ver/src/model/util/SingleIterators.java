package model.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class SingleIterators {
    public SingleIterators() {}
    
    private static class SingleIterator<T> implements Iterator<T> {
        private final T value;
        private boolean hasNext = true;
        
        public SingleIterator(T value) {
            this.value = value;
        }
        
        public boolean hasNext() {
            return hasNext;
        }
        
        public T next() {
            if (!hasNext) {
                throw new NoSuchElementException();
            }
            hasNext = false;
            return value;   
        }
    }
    
    public static <T> Iterator<T> of(T object) {
        return new SingleIterator<T>(object);
    }
}
