package model.entities;

import java.util.Iterator;
import java.util.UUID;

public class Test {
    public static void main(String[] args) {
        Exis e = new Exis();
        
        UUID ent = UUID.randomUUID();
        
        e.attach(ent, "Hello");
        
        System.out.println(e.getMetadata(UUID.randomUUID()));
        
        Iterator<Object> i = e.iterateMetadata(ent);
        i.forEachRemaining(o -> System.out.println(o));
        
        Iterator<Object> i2 = e.iterateMetadata(UUID.randomUUID());
        i2.forEachRemaining(o -> System.out.println(o));
        
        System.out.println(e.countMetadata(ent));
        System.out.println(e.countMetadata(UUID.randomUUID()));
    }
    
    
}
