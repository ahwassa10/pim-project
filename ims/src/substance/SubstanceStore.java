package substance;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import util.Hashing;

public final class SubstanceStore {
    private static final EnumSet<StandardOpenOption> WRITE_OPTIONS = EnumSet
            .of(StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);

    private final Path substance_folder;

    SubstanceStore(Path substance_folder) {
        this.substance_folder = substance_folder;

        System.out.println("Successfully created a substance store");
    }

    public String capture(Path substanceSource) {
        if (substanceSource == null) {
            throw new IllegalArgumentException("Substance source cannot be null");
        }
        
        // A temporary file to write the substance to.
        Path tempFile = substance_folder.resolve("temp");
        
        try {
            // Hash the substance and copy it to the temporary file.
            String hash = Hashing.asString(hashAndCopy(substanceSource, tempFile));
    
            // The filename of the substance is the hash itself.
            Path substanceFile = substance_folder.resolve(hash);
    
            if (Files.exists(substanceFile)) {
                // The case where the substance is a duplicate
                Files.delete(tempFile);
            } else {
                // Rename the tempFile from "temp" to the string representation
                // of the hash.
                Files.move(tempFile, substanceFile);
            }
            // Whether the substance is a duplicate or not, we always return
            // a hash of the substance.
            return hash;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean contains(String hash) {
        if (hash == null) {
            throw new IllegalArgumentException("Hash cannot be null");
        }
        return get(hash) != null;
    }
    
    public boolean delete(String hash) {
        if (hash == null) {
            throw new IllegalArgumentException("Hash cannot be null");
        }
        Path substancePath = substance_folder.resolve(hash);
        try {
            return Files.deleteIfExists(substancePath);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Path get(String hash) {
        if (hash == null) {
            throw new IllegalArgumentException("Hash cannot be null");
        }

        Path substancePath = substance_folder.resolve(hash);
        return Files.exists(substancePath) ? substancePath : null;
    }
    
    private static byte[] hashAndCopy(Path src, Path dest) throws IOException {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        ByteBuffer buffer = ByteBuffer.allocate(8192);
        try (ByteChannel srcChannel = Files.newByteChannel(src);
                ByteChannel destChannel = Files.newByteChannel(dest, WRITE_OPTIONS)) {

            // Calculate hash and copy to destination buffer without having to read
            // the input file twice.
            while (srcChannel.read(buffer) != -1) {
                buffer.flip();
                digest.update(buffer);
                buffer.flip();
                destChannel.write(buffer);
                buffer.clear();
            }
        }
        return digest.digest();
    }
    
    public Set<String> substanceSet() {
        Set<String> substances = new HashSet<>();
        
        try (DirectoryStream<Path> substanceStream = Files.newDirectoryStream(substance_folder)) {
            for (Path substance : substanceStream) {
                substances.add(substance.getFileName().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return substances;
    }
    
    public String toString() {
        return String.format("Substance Store<Substance Folder<%s>", substance_folder);
    }
}
