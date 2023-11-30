package model.entities;

import java.util.UUID;

import model.entities.metadata.MemMetadata;

public class Test {
    public static void main(String[] args) {
        UUID entity = UUID.randomUUID();
        
        MemMetadata<String> name = new MemMetadata<>();
        
        UUID namedEntity = name.attach(entity, "Hello");
        
        System.out.println(entity);
        System.out.println(namedEntity);
        System.out.println(name.metadataID());
        
        System.out.println(MemMetadata.xorUUIDs(namedEntity, name.metadataID()));
    }
}
