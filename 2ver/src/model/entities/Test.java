package model.entities;

import java.util.UUID;

import model.entities.metadata.MemMetadata;

public class Test {
    public static void main(String[] args) {
        UUID entity = UUID.randomUUID();
        
        MemMetadata<String> name = new MemMetadata<>();
        MemMetadata<String> description = new MemMetadata<>();
        
        name.attach(entity, "Test Name");
        description.attach(entity, "Basic Description");
        
        System.out.println(name.view().anyValue(entity));
        System.out.println(description.view().getValues(entity));
    }
}
