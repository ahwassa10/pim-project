package program;

import java.util.ArrayList;
import java.util.List;

import substance.SubstanceStore;
import substance.SubstanceStoreBuilder;

public class Testing4 {
    public static void main(String[] args) {
        SubstanceStore ss = SubstanceStoreBuilder.test_substore();
        
        List<Long> times = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            long startTime = System.nanoTime();
            ss.findProblems();
            long endTime = System.nanoTime();
            times.add(endTime - startTime);
        }
        double averageTime = times.stream().mapToLong(Long::longValue).average().orElse(Double.NaN);
        System.out.println(averageTime * 1e-9);
    }
}
