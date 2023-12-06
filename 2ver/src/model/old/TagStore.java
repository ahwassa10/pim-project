package model.old;

import java.util.*;

/**
 * Represents a logical collection of tags applied to a photo.
 */
public class TagStore implements Iterable<Tag> {
    /**
     * An implementation of comparator used to sort the tags in the tag store.
     * Tags are sorted alphabetically by lowercase name first, and if names
     * match, tags are sorted alphabetically by lowercase value.
     *
     */
    private class TagComparator implements Comparator<Tag> {
        /**
         * The implementation of compare method inside the Comparator interface.
         * Tags are sorted alphabetically by lowercase name first, and if names
         * match, tags are sorted alphabetically by lowercase value.
         *
         * @param t1 The first tag
         * @param t2 The second tag
         * @return an integer representing the order of the order of t1 and t2.
         */
		public int compare(Tag t1, Tag t2) {
			return (t1.getName().equalsIgnoreCase(t2.getName()) ?
                    t1.getValue().compareToIgnoreCase(t2.getValue()) :
                    t1.getName().compareToIgnoreCase(t2.getName()));
		}
	}

    /**
     * The collections used to actually store the tag objects.
     * TreeSets don't contain duplicates and maintain an ordered property,
     * making them the ideal data structure to implement the tag store.
     */
    private Set<Tag> tags = new TreeSet<>(new TagComparator());

    /**
     * A predicate to test if a tag is already present in the tag store.
     *
     * @param tag The tag to test.
     * @return true if the tag is present, false otherwise.
     */
    public boolean contains(Tag tag) {
        if (tag == null) {
            return false;
        }
        for (Tag aTag : tags) {
            if (aTag.equals(tag)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Inserts a tag into the tag store, checking to make sure that it is not already
     * present.
     *
     * @param tag the tag to add.
     */
    void add(Tag tag) {
        if (tag == null) {
            throw new IllegalArgumentException("Can't add null tag");
        }

        if (this.contains(tag)) {
            throw new IllegalArgumentException("Can't contain duplicate tags");
        } else {
            tags.add(tag);
        }
    }

    /**
     * A predicate to test if the tag store already contains a tag with the
     * passed in name.
     *
     * @param name The name to test.
     * @return True if a tag already has this name, false otherwise.
     */
    public boolean containsTagByName(String name) {
        for (Tag tag: tags) {
            if (tag.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * A predicate to test if a name+value pair is present in the tagstore
     *
     * @param name The name to test
     * @param value The value to test
     * @return true if present, false otherwise.
     */
    public boolean containsNameValue(String name, String value) {
        for (Tag tag: tags) {
            if (tag.getName().equals(name) && tag.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a tag from the tag store. Does nothing if the tag is not present
     * in the tag store.
     *
     * @param tag The tag to remove.
     */
    public void remove(Tag tag) {
        tags.remove(tag);
    }

    /**
     * Returns an iterator of tags applied to the photo.
     *
     * @return An iterator of tags in the tag store.
     */
    public Iterator<Tag> iterator() {
        return new Iterator<Tag> () {
            // TagStore's iterator delegates to the iterator from TreeSet.
            // We do this to prevent the remove() method from being called
            // on TreeSet's iterator.
            private Iterator<Tag> itr = tags.iterator();

            public boolean hasNext() {
                return itr.hasNext();
            }

            public Tag next() {
                return itr.next();
            }
        };
    }

    /**
     * Returns an unmodifiable view of the treeset used to implement the tag store.
     *
     * @return An unmodifiable collection of tags.
     */
    public Collection<Tag> view() {
        return Collections.unmodifiableCollection(tags);
    }
}
