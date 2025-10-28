package com.ebards.urlshortener.utils;

import java.security.SecureRandom;

public class Base62GeneratorUtil {
    private static final int codeLength = 6;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static String generateCode() {
        StringBuilder sb = new StringBuilder();
        String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        for (int i = 0; i < codeLength; i++) {
            sb.append(BASE62.charAt(SECURE_RANDOM.nextInt(BASE62.length())));
        }

        return sb.toString();
    }
}
