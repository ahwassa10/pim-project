package model.metadata;

import java.util.UUID;

import model.metadata.Metadatas.Association;
import model.metadata.Metadatas.SingleTable;


public class Test {
    public static void main(String[] args) {
        UUID entity = UUID.randomUUID();
        
        Association exis = Metadatas.markedMetadata();
        
        UUID exisTrait = exis.associate(entity);
        
        System.out.println(exisTrait);
        System.out.println(exis.computeID(entity));
        
        SingleTable<String> name = Metadatas.singleMetadata();
        
        name.attach(exisTrait, "First Name");
        
       Trait<String> entityName = name.asTrait(exisTrait);
       System.out.println(entityName.get().any());
       System.out.println(entityName.getTraitID());
       
       System.out.println(entity.equals(exis.computeID(exisTrait)));
       System.out.println(entity);
       System.out.println(exis.computeID(exisTrait));
       System.out.println(entity.variant());
       System.out.println(exis.computeID(exisTrait).variant());
    }
}