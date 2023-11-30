package model.entities;

import java.util.UUID;

public final class UUIDs {
    private UUIDs() {}

    public static UUID xorUUIDs(UUID uuid1, UUID uuid2) {
        long msb1 = uuid1.getMostSignificantBits();
        long lsb1 = uuid1.getLeastSignificantBits();
    
        long msb2 = uuid2.getMostSignificantBits();
        long lsb2 = uuid2.getLeastSignificantBits();
    
        long xorMsb = msb1 ^ msb2;
        long xorLsb = lsb1 ^ lsb2;
    
        return new UUID(xorMsb, xorLsb);
    }
}
