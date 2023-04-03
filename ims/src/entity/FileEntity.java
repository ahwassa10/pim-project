package entity;

public interface FileEntity extends SystemEntity {
    String getFilename();

    long getFilesize();
}
