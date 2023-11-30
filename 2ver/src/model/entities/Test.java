package model.entities;

import java.util.UUID;

import model.entities.metadata.MemMetadata;
import model.entities.metadata.Metadata;

public class Test {
    public static void main(String[] args) {
        UUID entity = UUID.randomUUID();
        
        Metadata<String> name = MemMetadata.multiMetadata();
        
        UUID namedEntity = name.attach(entity, "Hello");
        
        System.out.println(entity);
        System.out.println(namedEntity);
        System.out.println(name.getMetadataID());
        
        System.out.println(UUIDs.xorUUIDs(namedEntity, name.getMetadataID()));
    }
}
