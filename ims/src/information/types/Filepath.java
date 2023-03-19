package information.types;

import java.nio.file.Files;
import java.nio.file.Path;

import information.AbstractSingleValueInfo;
import information.SimpleInfoType;
import sys_d.InfoType;

public final class Filepath extends AbstractSingleValueInfo<Path> {
	public static final InfoType DATA_TYPE =
			new SimpleInfoType("Filepath");
	
	private Filepath(Path filepath) {
		super(filepath);
	}
	
	public static Filepath from(Path filepath) {
		if (!isValidFilepath(filepath)) {
			throw new IllegalArgumentException("Not a valid path to a file");
		} else {
			return new Filepath(filepath);
		}
	}
	
	public static boolean isValidFilepath(Path test_path) {
		return (test_path != null) &&
			   (Files.exists(test_path)) &&
			   (Files.isRegularFile(test_path));
	}
	
	public InfoType getDataType() {
		return DATA_TYPE;
	}
}
