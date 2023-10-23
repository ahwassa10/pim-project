package model.attributive.implementation;

import model.attributive.specification.Mapper;
import model.attributive.specification.View;

public final class Mappers {
    private Mappers() {}
    
    public static <T> T requireAttributions(Mapper<T, ?> mapper, T attributer) {
        if (!mapper.attributions().hasMappings(attributer)) {
            String msg = String.format("%s has not been attributed to any other object under this mapper", attributer);
            throw new IllegalArgumentException(msg);
        }
        return attributer;
    }
    
    public static <U> U requirePropertizations(Mapper<?, U> mapper, U propertizer) {
        if (!mapper.propertizations().hasMappings(propertizer)) {
            String msg = String.format("%s has not been propertized to any other object under this mapper", propertizer);
            throw new IllegalArgumentException(msg);
        }
        return propertizer;
    }
    
    public static <T, U> void requireMapping(Mapper<T, U> mapper, T attributer, U propertizer) {
        View<T, U> attributions = mapper.attributions();
        
        if (attributions.hasMappings(attributer) && 
                !attributions.getMappings(attributer).contains(propertizer)) {
            String msg = String.format("%s is not mapped to %s under this mapper", attributer, propertizer);
            throw new IllegalArgumentException(msg);
        }
    }
    
    public static <T, U> void requireNoMapping(Mapper<T, U> mapper, T attributer, U propertizer) {
        View<T, U> attributions = mapper.attributions();
        
        if (attributions.hasMappings(attributer) &&
                attributions.getMappings(attributer).contains(propertizer)) {
            String msg = String.format("%s is already mapped to %s under this mapper", attributer, propertizer);
            throw new IllegalArgumentException(msg);
        }
    }
    
}
