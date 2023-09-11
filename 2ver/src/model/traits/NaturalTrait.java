package model.traits;

import java.util.List;

public interface NaturalTrait extends Trait {
    Trait getParent();
    
    List<Trait> getAncestors();
    
    NaturalTrait getProgenitor();
    
    List<NaturalTrait> getLineage();
}
