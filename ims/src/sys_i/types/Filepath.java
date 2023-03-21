package sys_i.types;

import java.nio.file.Files;
import java.nio.file.Path;

public final class Filepath {
	private Filepath() {}
	
	public static boolean isValidFilepath(String test_string) {
		Path test_path = Path.of(test_string);
		return (test_path != null) &&
			   (Files.exists(test_path)) &&
			   (Files.isRegularFile(test_path));
	}
}
