package com.cd.quizwhiz.UserStuff;
// Import necessary libraries
import java.security.Key;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class PasswordEncryption {
    // Define the encryption algorithm (AES) and encryption key
    private static final String ALGORITHM = "AES";
    private static final String KEY = "1Hbfh667adfDEJ78";

    // Method to encrypt a password
    public static String encrypt(String password) throws Exception {
        // Generate a secret key from the provided key string
        Key key = generateKey();
        // Create a cipher object for encryption
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // Initialize the cipher for encryption using the generated key
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // Encrypt the password and encode it in Base64
        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Method to decrypt an encrypted password
    public static String decrypt(String encryptedPassword) throws Exception {
        // Generate a secret key from the provided key string
        Key key = generateKey();
        // Create a cipher object for decryption
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // Initialize the cipher for decryption using the generated key
        cipher.init(Cipher.DECRYPT_MODE, key);
        // Decode the Base64 encoded encrypted password and decrypt it
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
        return new String(decryptedBytes);
    }

    // Method to generate a secret key from the provided key string
    private static Key generateKey() throws Exception {
        // Convert the key string to bytes
        byte[] keyValue = KEY.getBytes();
        // Create a secret key object using the bytes and the encryption algorithm
        return new SecretKeySpec(keyValue, ALGORITHM);
    }
}