package model.attributive.specification;

public interface Mapper<T, U> {
    View<T, U> attributions();
    
    View<U, T> propertizations();
    
    void map(T attributer, U propertizer);
    
    void unmap(T attributer, U propertizer);
    
    void delete(T attributer);
    
    void forget(U propertizer);
}
