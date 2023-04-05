package program;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import substance.SubstanceStore;
import substance.SubstanceStoreBuilder;

public class Testing4 {
    public static SubstanceStore ss = SubstanceStoreBuilder.test_substore();
    
    public static void benchmark() {
        List<Long> times = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            long startTime = System.nanoTime();
            ss.findDamages();
            long endTime = System.nanoTime();
            times.add(endTime - startTime);
        }
        double averageTime = times.stream().mapToLong(Long::longValue).average().orElse(Double.NaN);
        System.out.println(averageTime * 1e-9);
    }
    
    public static void repairs() {
        System.out.println("Is Coherent: " + ss.isCoherent());
        
        Map<String, String> damages = ss.findDamages();
        System.out.println("Damages: " + damages);
        
        boolean repairs = ss.repair();
        System.out.println("Repairs Successful: " + repairs);
        
        System.out.println("Is Coherent: " + ss.isCoherent());
    }
    
    public static void main(String[] args) {
        repairs();
    }
}
