package model.metadata;

import java.util.UUID;

public interface MarkedMetadata extends Metadata {
    UUID mark(UUID entityID);
    UUID unmark(UUID entityID);
}
