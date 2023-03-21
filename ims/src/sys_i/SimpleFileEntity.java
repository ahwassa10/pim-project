package sys_i;

import java.util.UUID;

final class SimpleFileEntity implements FileEntity {
	private final UUID identity;
	private final String filename;
	private final long filesize;
	
	SimpleFileEntity(UUID identity, String filename, long filesize) {
		this.identity = identity;
		this.filename = filename;
		this.filesize = filesize;
	}
	
	public UUID getIdentity() {
		return identity;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public long getFilesize() {
		return filesize;
	}
	
	public String toString() {
		return String.format("FileEntity<Identity:%s, Filename:%s, Filesize:%d>",
				identity.toString(), filename, filesize);
	}
}
