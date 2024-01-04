package model.newtables;

import java.util.UUID;

public interface Drop {
    UUID getKey();
    UUID getTableID();
    Object getCore();
    
    boolean hasNextDrop();
    Drop nextDrop();
}
