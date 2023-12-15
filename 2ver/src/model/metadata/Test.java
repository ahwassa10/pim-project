package model.metadata;

import java.util.UUID;

import model.metadata.Metadatas.MarkedMetadata;
import model.metadata.Metadatas.SingleMetadata;


public class Test {
    public static void main(String[] args) {
        UUID entity = UUID.randomUUID();
        
        MarkedMetadata exis = Metadatas.markedMetadata();
        
        UUID exisTrait = exis.mark(entity);
        
        System.out.println(exisTrait);
        System.out.println(exis.asTrait(entity).getTraitID());
        
        SingleMetadata<String> name = Metadatas.singleMetadata();
        
        name.attach(exisTrait, "First Name");
        
       ValueTrait<String> entityName = name.asValueTrait(exisTrait);
       System.out.println(entityName.get().any());
       System.out.println(entityName.getTraitID());
       
       System.out.println(entity.equals(exis.computeID(exisTrait)));
       System.out.println(entity);
       System.out.println(exis.computeID(exisTrait));
       System.out.println(entity.variant());
       System.out.println(exis.computeID(exisTrait).variant());
    }
}
