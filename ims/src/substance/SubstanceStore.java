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
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import util.Hashing;

public final class SubstanceStore {
    private static final EnumSet<StandardOpenOption> WRITE_OPTIONS = EnumSet
            .of(StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
    
    private final Path damaged_folder;
    private final Path substance_folder;

    SubstanceStore(Path damaged_folder, Path substance_folder) {
        this.damaged_folder = damaged_folder;
        this.substance_folder = substance_folder;

        System.out.println("Successfully created a substance store");
    }

    public String capture(Path substanceSource) {
        Objects.requireNonNull(substanceSource, "Substance source cannot be null");
        
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
        Objects.requireNonNull(hash, "Hash cannot be null");
        return get(hash) != null;
    }
    
    private boolean damagedHandler(Path damagedSubstance) {
        // We are prepending the filename (hash) with a random 8 character string.
        // This prevents problems with duplicate damaged substances in the damaged folder.
        String randomPrefix = UUID.randomUUID().toString().substring(0, 8);
        String damagedFilename = randomPrefix + "_" + damagedSubstance.getFileName().toString();
        
        Path damagedPath = damaged_folder.resolve(damagedFilename);
        try {
            Files.move(damagedSubstance, damagedPath);
        } catch (IOException e) {
            System.out.println("Failed to move " + damagedSubstance);
            e.printStackTrace();
            try {
                Files.delete(damagedSubstance);
            } catch (IOException e2) {
                System.out.println("Failed to delete " + damagedSubstance);
                e2.printStackTrace();
                return false;
            }
        }
        return true;
    }
    
    public boolean delete(String hash) {
        Objects.requireNonNull(hash, "Hash cannot be null");
        
        Path substancePath = substance_folder.resolve(hash);
        try {
            return Files.deleteIfExists(substancePath);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Map<String, String> findDamages() {
        Map<String, String> mismatchedHashes = new HashMap<>();
        
        try (DirectoryStream<Path> substanceStream = Files.newDirectoryStream(substance_folder)) {
            for (Path substance : substanceStream) {
                String diskHash = substance.getFileName().toString();
                String actualHash = Hashing.asString(Hashing.calculateSHA256(substance));
                
                if (!diskHash.equals(actualHash)) {
                    mismatchedHashes.put(diskHash, actualHash);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return mismatchedHashes;
    }
    
    public Path get(String hash) {
        Objects.requireNonNull(hash, "Hash cannot be null");

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
    
    public boolean isCoherent() {
        try {
            return Files.list(substance_folder)
                        .map(path -> path.getFileName().toString())
                        .allMatch(this::isCoherent);
            
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isCoherent(String hash) {
        Objects.requireNonNull(hash, "Hash cannot be null");
        return hash.equals(rehash(hash));
    }
    
    public String rehash(String hash) {
        Objects.requireNonNull(hash, "Hash cannot be null");
        
        Path substancePath = substance_folder.resolve(hash);
        return Files.exists(substancePath) 
                ? Hashing.asString(Hashing.calculateSHA256(substancePath)) 
                : null; // Return null if the substance does not exist
    }
    
    public boolean repair() {
        boolean allRepairsSuccessful = true;
        try (DirectoryStream<Path> substanceStream = Files.newDirectoryStream(substance_folder)) {
            for (Path substance : substanceStream) {
                String diskHash = substance.getFileName().toString();
                String actualHash = Hashing.asString(Hashing.calculateSHA256(substance));
                
                if (!diskHash.equals(actualHash)) {
                    boolean repairSuccessful = damagedHandler(substance);
                    allRepairsSuccessful &= repairSuccessful;
                }       
            }
        } catch (IOException e) {
            System.out.println("Encountered problem during the repair process");
            e.printStackTrace();
            return false;
        }
        return allRepairsSuccessful;
    }
    
    public Set<String> substanceSet() {
        try {
            return Files.list(substance_folder)
                        .map(path -> path.getFileName().toString())
                        .collect(Collectors.toSet());
            
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptySet();
        }
    }
    
    public String toString() {
        return String.format("Substance Store<Damaged Folder<%s>, Substance Folder<%s>",
                damaged_folder, substance_folder);
    }
}
