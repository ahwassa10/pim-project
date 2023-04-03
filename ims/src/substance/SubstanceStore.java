package substance;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.EnumSet;

import util.Hashing;

public final class SubstanceStore {
    private static final EnumSet<StandardOpenOption> WRITE_OPTIONS = EnumSet
            .of(StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);

    private final Path substance_folder;

    SubstanceStore(Path substance_folder) {
        this.substance_folder = substance_folder;

        System.out.println("Successfully created a substance store");
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

    public String capture(Path substanceSource) throws IOException {
        if (substanceSource == null) {
            throw new IllegalArgumentException("Substance source cannot be null");
        }

        // A temporary file to write the substance to.
        Path tempFile = substance_folder.resolve("temp");

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
    }

    public Path get(String hash) {
        if (hash == null) {
            throw new IllegalArgumentException("Hash cannot be null");
        }

        Path substancePath = substance_folder.resolve(hash);
        // Return the path to the substance if it exists, otherwise
        // return null.
        return Files.exists(substancePath) ? substancePath : null;
    }

    public boolean has(String hash) {
        if (hash == null) {
            throw new IllegalArgumentException("Hash cannot be null");
        }
        Path testPath = substance_folder.resolve(hash);
        return Files.exists(testPath);
    }

    public String toString() {
        return String.format("Substance Store<Substance Folder<%s>", substance_folder);
    }
}
