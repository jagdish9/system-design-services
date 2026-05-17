package com.sysdesign.pastebinservice.entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Paste {

    private String id;
    private String content;
    private long createdAt;
    private long expiryTime;

    public Paste(String id, String content, long expiryTime) {
        this.id = id;
        this.content = content;
        this.expiryTime = expiryTime;
        this.createdAt = System.currentTimeMillis();
    }

    public boolean isExpired() {
        return expiryTime != 0 && System.currentTimeMillis() > expiryTime;
    }
}
