package com.zespolowka.entity.user;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Pitek on 2015-11-29.
 */
public enum Role implements GrantedAuthority {
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

    @Override
    public String getAuthority() {
        return name();
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> addf63146eadb4865c3e88fc9502c025b3871c1e
