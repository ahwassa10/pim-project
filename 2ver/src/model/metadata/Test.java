package model.metadata;

import java.util.UUID;

import model.util.UUIDs;

public class Test {
    public static void main(String[] args) {
        UUID entity = UUID.randomUUID();
        
        ValueMetadata<String> name = MemMetadata.multiMetadata();
        
        UUID namedEntity = name.attach(entity, "Hello");
        
        System.out.println(entity);
        System.out.println(namedEntity);
        System.out.println(name.getMetadataID());
        
        System.out.println(UUIDs.xorUUIDs(namedEntity, name.getMetadataID()));
        
        ValueTrait<String> nameTrait = name.asValueTrait(entity);
        System.out.println(nameTrait.anyValue());
    }
}
