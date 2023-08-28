package model;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Entities live in the Garden.
 * 
 */
public class Garden {
    /**
     * An implementation of comparator used to sort the tag names.
     * Tags are sorted alphabetically by their lowercase names.
     */
    private class TagComparator implements Comparator<String> {
        /**
         * Compares two tag alphabetically by their lowercase names.
         *
         * @param o1 The first tag name
         * @param o2 The second tag name
         * @return The result of the comparison.
         */
		public int compare(String o1, String o2) {
			return o1.toLowerCase().compareTo(o2.toLowerCase());
		}
	}
    
    private String name = "";
    
    private Group all = new Group("All");
    
    private Map<Group, Set<Photo>> groups = new HashMap<>();

    private Set<String> tags = new TreeSet<>(new TagComparator());
    
    public Garden(String name) {
        if (!isValidName(name)) {
            throw new IllegalArgumentException("Invalid Garden Name");
        }
        this.name = name;
        groups.put(all, new HashSet<>());
    }
    
    public void changeGroupName(Group group, String newName) {
        group.setName(newName);
    }
    
    public void copy(Group src, Group dest, Photo photo) {
        Objects.requireNonNull(src, "Source group cannot be null");
        Objects.requireNonNull(dest, "Destination group cannot be null");
        Objects.requireNonNull(photo, "Photo cannot be null");
        
        if (!groups.containsKey(src)) {
            throw new IllegalArgumentException("Source group does not exist");
        } else if (!groups.containsKey(dest)) {
            throw new IllegalArgumentException("Destination group does not exist");
        } else if (!groups.get(src).contains(photo)) {
            throw new IllegalArgumentException("Source group does not contain this photo");
        } else {
            groups.get(dest).add(photo);
        }
    }
    
    public void createGroup(Group group) {
        Objects.requireNonNull(group, "Group object cannot be null");
        groups.putIfAbsent(group, new HashSet<>());
    }
    
    public void createPhoto(Photo photo) {
        Objects.requireNonNull(photo, "Photo object cannot be null");
        groups.get(all).add(photo);
    }
    
    public void deleteGroup(Group group) {
        Objects.requireNonNull(group, "Group object cannot be null");
        groups.remove(group);
    }
    
    public void deletePhoto(Group group, Photo photo) {
        Objects.requireNonNull(group, "Group object cannot be null");
        Objects.requireNonNull(photo, "Photo object cannot be null");
        
        if (!groups.containsKey(group)) {
            throw new IllegalArgumentException("This group does not exist");
        } else if (!groups.get(group).contains(photo)) {
            throw new IllegalArgumentException("This group does not contain this photo");
        }
        
        if (group == all) {
            groups.forEach((g, photoSet) -> photoSet.remove(photo));
        } else {
            groups.get(group).remove(photo);
        }
    }
    
    public Set<Group> getGroups() {
        return Collections.unmodifiableSet(groups.keySet());
    }
    
    public String getName() {
        return name;
    }
    
    public Set<Photo> getPhotos(Group group) {
        Objects.requireNonNull(group, "Group cannot be null");
        
        if (!groups.containsKey(group)) {
            throw new IllegalArgumentException("This group does not exist");
        } else {
            return Collections.unmodifiableSet(groups.get(group));
        }
    }
    
    public Collection<String> getTags() {
        return Collections.unmodifiableCollection(tags);
    }
    
    public boolean groupNameInUse(String groupName) {
        for (Group group : groups.keySet()) {
            if (group.getName().equals(groupName)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isValidName(String name) {
        return name != null && !name.equals("");
    }
    
    public void move(Group src, Group dest, Photo photo) {
        copy(src, dest, photo);
        deletePhoto(src, photo);
    }

    /**
     * The implementation of tagging a photo.
     * Specifically, this method applies a tag to a photo inside an group, checking
     * that the user owns the group, that the photo is inside this group, and that
     * the tag is not a duplicate.
     *
     * @param srcGroup The source group
     * @param photo The photo inside the source group
     * @param tag The tag to apply to the photo
     */
    public void tagPhoto(Group srcGroup, Photo photo, Tag tag) {
        if (srcGroup == null ||
            photo == null ||
            tag == null ||
            !srcGroup.contains(photo) ||
            photo.getTagStore().contains(tag)) {

            throw new IllegalArgumentException("Cannot tag this photo");
        }

        photo.getTagStore().add(tag);
        tags.add(tag.getName());
    }

    /**
     * Returns a string representation of the user.
     *
     * @return The username.
     */
    public String toString() {
        return name;
    }
}
