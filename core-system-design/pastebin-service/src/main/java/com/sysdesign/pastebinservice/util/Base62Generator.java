package com.sysdesign.pastebinservice.util;

import java.util.concurrent.atomic.AtomicLong;

public class Base62Generator {

    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final AtomicLong counter = new AtomicLong(1);

    public static String generateId() {
        long value = counter.getAndIncrement();
        return encode(value);
    }

    private static String encode(long value) {
        StringBuilder sb = new StringBuilder();
        while (value > 0) {
            long mod = value % 62;
            sb.append((int) mod);
            value = value / 62;
        }
        return sb.reverse().toString();
    }
}
