package model.metadata;

import java.util.UUID;

public interface MarkedMetadata {
    UUID mark(UUID entityID);
    UUID unmark(UUID entityID);
}
