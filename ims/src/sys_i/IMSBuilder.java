package sys_i;

import java.nio.file.Files;
import java.nio.file.Path;

public class IMSBuilder {
	private Path output_folder;
	
	public IMSBuilder() {}
	
	public static IMS test_ims() {
		return new IMSBuilder()
				.setOutputFolder("C:\\Users\\Primary\\Desktop\\output")
				.build();
	}
	
	public IMSBuilder setOutputFolder(String pathname) {
		if (pathname == null) {
			throw new IllegalArgumentException("Output folder path cannot be null");
		}
		Path test_path = Path.of(pathname);
		if (!Files.exists(test_path)) {
			throw new IllegalArgumentException("Output folder path does not exist");
		} else if (!Files.isDirectory(test_path)) {
			throw new IllegalArgumentException("Output folder path is not a directory");
		}
		output_folder = test_path;
		return this;	
	}
	
	public IMS build() {
		if (output_folder == null) {
			throw new IllegalStateException("Output folder path not specified");
		}
		return new IMS(output_folder);
	}
}
