package model.attributive;

import model.attributive.implementation.MemoryMapper;
import model.attributive.specification.Mapper;

public class Testing {
    public static void main(String[] args) {
        Mapper<Integer, String> m = new MemoryMapper<>();
        
        m.map(1, "hi");
        m.map(2, "hi");
        m.map(2, "hello");
        m.map(3, "there");
        
        System.out.println(m.forward().getMappings(2));
        System.out.println(m.backward().getMappings("hi"));
        System.out.println(m.backward().getMappings("hello"));
        
        m.unmap(3, "there");
        System.out.println(m.forward().hasMappings(3));
        System.out.println(m.backward().hasMappings("there"));
        
        m.delete(2);
        System.out.println(m.forward().hasMappings(2));
        System.out.println(m.backward().getMappings("hi"));
        
        m.forget("hi");
        m.map(4, "hello");
        System.out.println(m.forward().hasMappings(1));
        System.out.println(m.backward().hasMappings("hi"));
        System.out.println(m.backward().hasMappings("hello"));
        
    }
}
