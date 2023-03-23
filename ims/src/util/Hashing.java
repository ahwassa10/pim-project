package util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.EnumSet;
import java.util.HexFormat;

public final class Hashing {
	private static final HexFormat HEX_FORMATTER =
			HexFormat.of().withUpperCase();
	
	private static final EnumSet<StandardOpenOption> DEST_OPTIONS =
			EnumSet.of(StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
	
	private Hashing() {}
	
	public static byte[] calculateHash(Path file) throws IOException {
		if (file == null) {
			throw new IllegalArgumentException("Inputs cannot be null");
		}
		
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
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
	
	public static String calculateHashString(Path file) throws IOException {
		byte[] hash = calculateHash(file);
		return HEX_FORMATTER.formatHex(hash);
	}
	
	public static byte[] hashAndCopy(Path sourceFile, Path destFile) throws IOException {
		if (sourceFile == null || destFile == null) {
			throw new IllegalArgumentException("Inputs cannot be null");
		}
		
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		ByteBuffer buffer = ByteBuffer.allocate(8192);
		try (ByteChannel sourceChannel = Files.newByteChannel(sourceFile);
			 ByteChannel destChannel = Files.newByteChannel(destFile, DEST_OPTIONS)) {
			
			while (sourceChannel.read(buffer) != -1) {
				buffer.flip();
				digest.update(buffer);
				buffer.flip();
				destChannel.write(buffer);
				buffer.clear();
			}
		}
		
		return digest.digest();
	}
	
	public static String hashStringAndCopy(Path sourceFile, Path destFile) throws IOException {
		byte[] hash = hashAndCopy(sourceFile, destFile);
		return HEX_FORMATTER.formatHex(hash);
	}
}
