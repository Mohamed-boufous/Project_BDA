package com.bd.gestion_bancaire.Utils;

public class RibGeneratorUtils {
    public static String generateUniqueRib() {
        long timestamp = System.currentTimeMillis();
        int random = (int)(Math.random() * 9000) + 1000;
        return timestamp +String.valueOf(random);
    }
}
