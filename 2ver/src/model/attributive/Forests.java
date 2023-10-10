package model.attributive;

public final class Forests {
    private Forests() {}
    
    public static <T> T requireNode(Forest<T> forest, T node) {
        return node;
    }
    
    public static <T> T requireRootNode(Forest<T> forest, T node) {
        if (!forest.isRootNode(node)) {
            String msg = String.format("%s is not a root node in this forest", node);
            throw new IllegalArgumentException(msg);
        }
        return node;
    }
    
    public static <T> T requireLeafNode(Forest<T> forest, T node) {
        if (!forest.isLeafNode(node)) {
            String msg = String.format("%s is not a leaf node in this forest", node);
            throw new IllegalArgumentException(msg);
        }
        return node;
    }
    
    public static <T> T requireSingleNode(Forest<T> forest, T node) {
        if (!forest.isSingleNode(node)) {
            String msg = String.format("%s is not a single node in this forest", node);
            throw new IllegalArgumentException(msg);
        }
        return node;
    }
    
    public static <T> T requireParentNode(Forest<T> forest, T node) {
        if (!forest.isParentNode(node)) {
            String msg = String.format("%s is not a parent node", node);
            throw new IllegalArgumentException(msg);
        }
        return node;
    }
    
    public static <T> T requireChildNode(Forest<T> forest, T node) {
        if (!forest.isChildNode(node)) {
            String msg = String.format("%s is not a child node", node);
            throw new IllegalArgumentException(msg);
        }
        return node;
    }
}