package model.attributive;

import model.attributive.implementation.DirectMapper;
import model.attributive.implementation.DenseMapper;
import model.attributive.specification.Mapper;

public class Testing {
    public static void main(String[] args) {
        Mapper<Integer, String> m = new DirectMapper<>();
        
        m.map(1, "one");
        
        m.map(2, "two");
        
        System.out.println(m.getValues(1));
        
        m.unmap(1, "one");
        
        m.map(1, "ONE");
        
        System.out.println(m.getValues(1));
        
    }
}
