package model.mappers.implementation;

import model.mappers.specification.Forest;

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
    
    public static <T> T requireParticipatingNode(Forest<T> forest, T node) {
        if (!forest.isParticipatingNode(node)) {
            String msg = String.format("%s is not a participating node in this forest", node);
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
    
    public static <T> T requireParent(Forest<T> forest, T node) {
        if (!forest.hasParent(node)) {
            String msg = String.format("%s does not have a parent node", node);
            throw new IllegalArgumentException(msg);
        }
        return node;
    }
    
    public static <T> T requireChildren(Forest<T> forest, T node) {
        if (!forest.hasChildren(node)) {
            String msg = String.format("%s does not have any child nodes", node);
            throw new IllegalArgumentException(msg);
        }
        return node;
    }
}