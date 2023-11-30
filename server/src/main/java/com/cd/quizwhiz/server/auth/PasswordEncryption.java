package com.cd.quizwhiz.server.auth;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordEncryption {
    private static final String SALT = "1Hbfh667adfDEJ78";

    public static String hashPassword(String password) {
        return DigestUtils.sha256Hex(SALT + password);
    }
}
