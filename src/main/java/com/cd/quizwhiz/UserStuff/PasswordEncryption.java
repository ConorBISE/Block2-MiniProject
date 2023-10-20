package com.cd.quizwhiz.UserStuff;
//imports
import java.security.Key;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class PasswordEncryption {
    private static final String ALGORITHM = "AES";
    private static final String KEY = "1Hbfh667adfDEJ78";

    public static String encrypt(String password) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedPassword) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
        return new String(decryptedBytes);
    }

    private static Key generateKey() throws Exception {
        byte[] keyValue = KEY.getBytes();
        return new SecretKeySpec(keyValue, ALGORITHM);
    }
}
