package model.attributive;

import model.attributive.implementation.MemoryMapper;
import model.attributive.specification.Mapper;

public class Testing {
    public static void main(String[] args) {
        Mapper<Integer, String> m = new MemoryMapper<>();
        
        m.map(1, "one");
        m.map(2, "two");
        m.map(3, "three");
        
        m.map(11, "one");
        m.map(22, "two");
        
        m.map(1, "ONE");
        m.map(1, "oNe");
        
        System.out.println(m.getValues(1));
        System.out.println(m.inverse().getValues("one"));
        
        
    }
}
