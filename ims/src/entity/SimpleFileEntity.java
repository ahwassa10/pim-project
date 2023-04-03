package entity;

import java.util.UUID;

final class SimpleFileEntity implements FileEntity {
    private final SystemEntity systemEntity;
    private final String filename;
    private final long filesize;

    SimpleFileEntity(SystemEntity systemEntity, String filename, long filesize) {
        this.systemEntity = systemEntity;
        this.filename = filename;
        this.filesize = filesize;
    }

    public UUID getIdentity() {
        return systemEntity.getIdentity();
    }

    public String getFilename() {
        return filename;
    }

    public long getFilesize() {
        return filesize;
    }

    public String toString() {
        return String.format("FileEntity<%s, Filename<%s>, Filesize<%d>>",
                systemEntity, filename, filesize);
    }
}
