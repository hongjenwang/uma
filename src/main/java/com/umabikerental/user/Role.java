package com.umabikerental.user;

import lombok.Getter;

@Getter
public enum Role {
    NORMAL_USER("Normal User"),
    ADMIN("Admin");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }
}

