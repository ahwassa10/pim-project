package information;

import java.nio.file.Files;
import java.nio.file.Path;

public final class Filepath extends AbstractSingleValueInfo<Path> {
	private static final DataType DATA_TYPE =
			new SimpleDataType("Filepath");
	
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
	
	public DataType getDataType() {
		return DATA_TYPE;
	}
}
