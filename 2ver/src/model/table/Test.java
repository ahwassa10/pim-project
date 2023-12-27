package model.table;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Integer> l = List.of(1, 2, 3, 4, 5);
        List<Integer> l2 = List.of(10, 11, 12);
        
        System.out.println(l.stream().flatMap(i -> l2.stream()).toList());
        
        
    }
}
