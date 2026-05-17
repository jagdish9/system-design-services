package com.sysdesign.urlshortenerservice.util;

public class Base62Encoder {

    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String encode(long num) {
        StringBuilder sb = new StringBuilder();

        while (num > 0) {
            long modValue = num % 62;
            sb.append(BASE62.charAt((int) modValue));
            num = num / 62;
        }

        return sb.reverse().toString();
    }
}
