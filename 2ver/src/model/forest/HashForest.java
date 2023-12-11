package model.forest;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import model.bimapper.BiMappers;
import model.bimapper.BiMappers.FunctionMapper;

public final class HashForest<T> implements Forest<T> {
    private final class NodeTree<U> implements Node<T>, Tree<T> {
        private final T object;
        
        NodeTree(T object) {
            this.object = object;
        }
        
        public T getObject() {
            return object;
        }
        
        public boolean hasParent() {
            return HashForest.this.hasParent(object); 
        }
        
        public Node<T> getParent() {
            return new NodeTree<T>(HashForest.this.getParent(object));
        }
        
        public Iterator<Node<T>> iterateParents() {
            Iterator<T> i = HashForest.this.iterateParents(object);
            
            return new Iterator<Node<T>>() {
                public boolean hasNext() {
                    return i.hasNext();
                }
                
                public NodeTree<T> next() {
                    return new NodeTree<T>(i.next());
                }
            };
        }
        
        public Node<T> getRoot() {
            T root = object;
            
            while (HashForest.this.hasParent(root)) {
                root = HashForest.this.getParent(root);
            }
            
            return new NodeTree<T>(root);
        }
        
        public boolean hasChildren() {
            return HashForest.this.hasChildren(object);
        }
        
        public Set<Tree<T>> getChildren() {
            return HashForest.this.getChildren(object).stream()
                    .map(child -> new NodeTree<T>(object))
                    .collect(Collectors.toSet());
        }
        
        public Iterator<Tree<T>> iterateBFS() {
            Iterator<T> i = HashForest.this.iterateBFS(object);
            
            return new Iterator<Tree<T>>() {
                public boolean hasNext() {
                    return i.hasNext();
                }
                
                public NodeTree<T> next() {
                    return new NodeTree<T>(i.next());
                }
            };
        }
        
        public Iterator<Tree<T>> iterateDFS() {
            Iterator<T> i = HashForest.this.iterateDFS(object);
            
            return new Iterator<Tree<T>>() {
                public boolean hasNext() {
                    return i.hasNext();
                }
                
                public NodeTree<T> next() {
                    return new NodeTree<T>(i.next());
                }
            };
        }
        
        public Node<T> asNode() {
            return this;
        }
        
        public String toString() {
            return object.toString();
        }
    }
    
    // The parent of the key is the value.
    private final FunctionMapper<T, T> parents = BiMappers.functionMapper();
    
    public HashForest() {}
    
    public HashForest(Tree<T> tree) {
        Iterator<Tree<T>> i = tree.iterateDFS();
        
        // Iterate though the root node.
        i.next();
        
        // Iterate though all the other nodes in the tree.
        while (i.hasNext()) {
            Node<T> atNode = i.next().asNode();
            
            T object = atNode.getObject();
            T parent = atNode.getParent().getObject();
            
            parents.map(object, parent);
        }
    }
    
    public boolean hasParent(T object) {
        return parents.hasKey(object);
    }
    
    public T getParent(T node) {
        Forests.requireParent(this, node);
        
        return parents.anyValue(node);
    }
    
    public boolean hasChildren(T node) {
        return parents.inverse().hasKey(node);
    }
    
    public Set<T> getChildren(T node) {
        Forests.requireChildren(this, node);
        
        return parents.inverse().getValues(node);
    }
    
    public Node<T> atNode(T node) {
        return new NodeTree<T>(node);
    }
    
    public Tree<T> atTree(T node) {
        return new NodeTree<T>(node);
    }
    
    public void attachRoot(T root, T parent) {
        Forests.requireRootNode(this, root);
        Forests.requireRootNode(this, parent);
        
        parents.map(root, parent);
    }
    
    public void attachSingle(T single, T parent) {
        Forests.requireSingleNode(this, single);
        
        parents.map(single, parent);
    }
    
    public T detach(T node) {
        Forests.requireParent(this, node);
        
        T parent = parents.anyValue(node);
        parents.unmap(node, parent);
        
        return parent;
    }
}