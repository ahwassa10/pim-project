package sys_i;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import information.Info;
import information.types.Filepath;
import information.types.Identity;

public final class IMS {
	private final Path output_folder;
	
	IMS(Path of_folder) {
		this.output_folder = of_folder;
		System.out.println("Sucessfully created the IMS");
	}
	
	private Path moveToOutputFolder(Path input_file) {
		Path output_file = output_folder.resolve(input_file.getFileName());
		try {
			Files.move(input_file, output_file);
		} catch (IOException e) {
			e.printStackTrace();
			return input_file;
		}
		return output_file;
	}
	
	public SystemEntity importInfo(List<Info> infolist) {
		Objects.requireNonNull(infolist, "Infolist cannot be null");
		
		Identity identity = Identity.newIdentifier();
		infolist.add(identity);
		
		infolist.stream()
				.filter(i -> i.getDataType().equals(Filepath.DATA_TYPE))
				.findFirst()
				.ifPresent(i -> {
					moveToOutputFolder(((Filepath) i).get());
					infolist.remove(i);
				});
		
		return new SystemEntity() {
			private List<Info> qualities = infolist;
			
			public Identity getIdentity() {
				return identity;
			}
			
			public List<Info> getQualities() {
				return qualities;
			}
		};
	}
	
	public String toString() {
		return String.format("IMS<Output Folder<%s>", output_folder);
	}
}
