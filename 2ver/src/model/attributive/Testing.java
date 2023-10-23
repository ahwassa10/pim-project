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
        
        System.out.println(m.attributions().getMappings(2));
        System.out.println(m.propertizations().getMappings("hi"));
        System.out.println(m.propertizations().getMappings("hello"));
    }
}
