package model.attributive.specification;

public interface Mapping<T, U> {
    View<T, U> attributions();
    
    View<U, T> propertizations();
    
    void apply(T attributer, U propertizer);
    
    void remove(T attributer, U propertizer);
    
    void delete(T attributer);
    
    void forget(U propertizer);
}
