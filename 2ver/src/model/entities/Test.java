package model.entities;

import java.util.Iterator;
import java.util.UUID;

public class Test {
    public static void main(String[] args) {
        Exis e = new Exis();
        
        UUID ent = UUID.randomUUID();
        
        e.attach(ent, "Hello");
        
        System.out.println(e.view().getValues(UUID.randomUUID()));
        
        Iterator<Object> i = e.view().iterateValues(ent);
        i.forEachRemaining(o -> System.out.println(o));
        
        Iterator<Object> i2 = e.view().iterateValues(UUID.randomUUID());
        i2.forEachRemaining(o -> System.out.println(o));
        
        System.out.println(e.view().countValues(ent));
        System.out.println(e.view().countValues(UUID.randomUUID()));
        System.out.println(e.view().hasMapping(UUID.randomUUID(), e));
    }
    
    
}
