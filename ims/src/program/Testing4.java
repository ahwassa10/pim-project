package program;

import java.io.IOException;

import quality.QualityStore;
import quality.QualityStoreBuilder;

public class Testing4 {
    public static void main(String[] args) throws IOException {
        QualityStore qs = QualityStoreBuilder.test_qualitystore();
        qs.printIndex();
    }
}
