package model.attributive;

import model.attributive.implementation.MemMappers;
import model.attributive.specification.Mapper;

public class Testing {
    public static void main(String[] args) {
        Mapper<Integer, String> m = MemMappers.denseMapper();
        
        m.map(1, "one");
        m.map(1, "ONE");
        m.map(2, "two");
        m.map(22, "two");
        
        System.out.println(m.getValues(1));
        
        m.unmap(2, "two");
        
        System.out.println(m.inverse().getValues("two"));
        
    }
}
