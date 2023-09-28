package model.trees;

public final class Trees {
    private Trees() {}
    
    public static <T> T requireAbsence(Tree<T> tree, T node) {
        if (tree.contains(node)) {
            String msg = String.format("%s is present in this tree", node);
            throw new IllegalArgumentException(msg);
        }
        return node;
    }
    
    public static <T> T requirePresence(Tree<T> tree, T node) {
        if (!tree.contains(node)) {
            String msg = String.format("%s is not present in this tree", node);
            throw new IllegalArgumentException(msg);
        }
        return node;
    }
    
    public static <T> T requireRootNode(Tree<T> tree, T node) {
        if (!tree.contains(node) || !tree.isRootNode(node)) {
            String msg = String.format("%s is not the root node of this tree", node);
            throw new IllegalArgumentException(msg);
        }
        return node;
    }
    
    public static <T> T requireInteriorNode(Tree<T> tree, T node) {
        if (!tree.contains(node) || !tree.isInteriorNode(node)) {
            String msg = String.format("%s is not an interior node in this tree", node);
            throw new IllegalArgumentException(msg);
        }
        return node;
    }
    
    public static <T> T requireLeafNode(Tree<T> tree, T node) {
        if (!tree.contains(node) || !tree.isLeafNode(node)) {
            String msg = String.format("%s is not a leaf node in this tree", node);
            throw new IllegalArgumentException(msg);
        }
        return node;
    }
    
    public static <T> T requireParentNode(Tree<T> tree, T node) {
        if (!tree.contains(node) || !tree.isParentNode(node)) {
            String msg = String.format("%s is not a parent node in this tree", node);
            throw new IllegalArgumentException(msg);
        }
        return node;
    }
    
    public static <T> T requireExteriorNode(Tree<T> tree, T node) {
        if (!tree.contains(node) || !tree.isExteriorNode(node)) {
            String msg = String.format("%s is not an exterior node in this tree", node);
            throw new IllegalArgumentException(msg);
        }
        return node;
    }
    
    public static <T> T requireChildNode(Tree<T> tree, T node) {
        if (!tree.contains(node) || !tree.isChildNode(node)) {
            String msg = String.format("%s is not a child node in this tree", node);
            throw new IllegalArgumentException(msg);
        }
        return node;
    }
}
