package model.metadata;

import java.util.UUID;

import model.util.UUIDs;

public class Test {
    public static void main(String[] args) {
        UUID entity = UUID.randomUUID();
        
        MarkedMetadata exis = MemMetadata.markingMetadata();
        
        UUID exisTrait = exis.mark(entity);
        
        System.out.println(exisTrait);
        System.out.println(exis.asTrait(entity).getTraitID());
        
        ValueMetadata<String> name = MemMetadata.singleMetadata();
        
        name.attach(exisTrait, "First Name");
        
       ValueTrait<String> entityName = name.asValueTrait(exisTrait);
       System.out.println(entityName.anyValue());
       System.out.println(entityName.getTraitID());
        
    }
}
