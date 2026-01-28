package com.buildtechwithJack.holmes.utilities;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SecurityUtils {

    public static String Sha3_256Hex(String input) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
            final byte[] hashbytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashbytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static String bytesToHex(byte[] hash) {
        return IntStream.range(0, hash.length)
                .mapToObj(i -> String.format("%02x", hash[i]))
                .collect(Collectors.joining());
    }

}
