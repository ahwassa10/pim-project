package util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public final class Hashing {
    private static final HexFormat HEX_FORMATTER = HexFormat.of().withUpperCase();

    private Hashing() {
    }

    public static String asString(byte[] hash) {
        return HEX_FORMATTER.formatHex(hash);
    }

    public static byte[] calculateHash(Path file, String algorithm)
            throws IOException, NoSuchAlgorithmException {
        if (file == null || algorithm == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        }

        MessageDigest digest = MessageDigest.getInstance(algorithm);

        try (ByteChannel byteChannel = Files.newByteChannel(file)) {
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            while (byteChannel.read(buffer) != -1) {
                buffer.flip();
                digest.update(buffer);
                buffer.clear();
            }
        }
        return digest.digest();
    }

    public static byte[] calculateSHA256(Path file) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        try {
            return calculateHash(file, "SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
