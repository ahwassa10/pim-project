package model.attributive;

public interface Forest<T> {
    boolean isNode(T node);
    
    boolean isRootNode(T node);
    
    boolean isLeafNode(T node);
    
    boolean isSingleNode(T node);
    
    void attachRoot(T root, T parent);
    
    void attachSingle(T single, T parent);
    
    boolean isParentNode(T node);
    
    boolean isChildNode(T node);
    
    T detach(T child);
    
    TreeNode<T> atNode(T node);
}
