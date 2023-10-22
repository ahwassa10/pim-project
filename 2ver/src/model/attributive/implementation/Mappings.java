package model.attributive.implementation;

import model.attributive.specification.Mapping;

public final class Mappings {
    private Mappings() {}
    
    public static <T> T requireAttributions(Mapping<T, ?> mapping, T attributer) {
        if (!mapping.attributions().hasMappings(attributer)) {
            String msg = String.format("%s has not made any attributions in this mapping", attributer);
            throw new IllegalArgumentException(msg);
        }
        return attributer;
    }
    
    public static <U> U requirePropertizations(Mapping<?, U> mapping, U propertizer) {
        if (!mapping.propertizations().hasMappings(propertizer)) {
            String msg = String.format("%s has not made any propertizations in this mapping", propertizer);
            throw new IllegalArgumentException(msg);
        }
        return propertizer;
    }
    
    public static <T, U> void requireMapping(Mapping<T, U> mapping, T attributer, U propertizer) {
        if (!mapping.attributions().getMappings(attributer).contains(propertizer)) {
            String msg = String.format("%s is not mapped to %s in this mapping", attributer, propertizer);
            throw new IllegalArgumentException(msg);
        }
    }
    
    public static <T, U> void requireNoMapping(Mapping<T, U> mapping, T attributer, U propertizer) {
        if (mapping.attributions().getMappings(attributer).contains(propertizer)) {
            String msg = String.format("%s is already mapping to %s in this mapping", attributer, propertizer);
            throw new IllegalArgumentException(msg);
        }
    }
    
}
