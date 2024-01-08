package oldmodel;

import java.util.Iterator;

/**
 * A CircularIterator loops around to the start or the end of the collection
 * after the last or first item has been reached, respectively. hasNext()
 * and hasPrevious() will always return true, as long as there is at least
 * one object in the collection.
 *
 * @param <E> The type of object to iterate over.
 */
public interface CircularIterator<E> extends Iterator<E> {
    /**
     * Should always return true, since the iterator will loop back to the last
     * object after the first object.
     *
     * @return true.
     */
    boolean hasPrevious();

    /**
     * Returns the previous object in the collection.
     * Note, .previous().next().previous() will always return the same object.
     *
     * @return an object in the collection.
     */
    E previous();
}
