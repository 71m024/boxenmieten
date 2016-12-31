package ch.drshit.domain.services;

import org.apache.shiro.codec.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;


public class PasswordHash {

    // The following constants may be changed without breaking existing hashes.
    public static final int SALT_BYTES = 24;
    public static final int SHA256_ITERATIONS = 1024;

    /**
     * Returns a salted SHA256 hash of the password.
     *
     * @param   password    the password to hash
     * @return              a salted SHA256 hash of the password
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     */
    public static String createHash(String password, String salt)
        throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        // Hash the password
        String hash = sha256(password, salt);
        // format iterations:salt:hash
        return hash;
    }

    public static String createSalt() {
        // Generate a random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTES];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Validates a password using a hash.
     *
     * @param   password    the password to check
     * @param   goodHash    the hash of the valid password
     * @return              true if the password is correct, false if not
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     */
    public static boolean validatePassword(String password, String goodHash, String saltString)
        throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        // Compute the hash of the provided password, using the same salt,
        String testHash = sha256(password, saltString);
        // Compare the hashes in constant time. The password is correct if
        // both hashes match.
        return goodHash.equals(testHash);
    }

    private static String sha256(String password, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        if (salt != null) {
            digest.reset();
            digest.update(salt.getBytes());
        }
        byte[] hashed = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        int iterations = SHA256_ITERATIONS - 1; //already hashed once above
        //iterate remaining number:
        for (int i = 0; i < iterations; i++) {
            digest.reset();
            hashed = digest.digest(hashed);
        }
        return bytesToHex(hashed);
    }

    private static String bytesToHex(byte[] hash) {
        return Hex.encodeToString(hash);
    }

}