package model.entity;

import java.util.UUID;

public interface ContentEntity {
    UUID getContentID();
    String getName();
    String getDescription();
}
