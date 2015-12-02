package com.zespolowka.Entity;

/**
 * Created by Pitek on 2015-11-29.
 */
public enum Role {
    USER("user"),

    ADMIN("admin"),

    SUPERADMIN("superadmin");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
