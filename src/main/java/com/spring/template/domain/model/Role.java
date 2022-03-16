package com.spring.template.domain.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN(Code.ADMIN),
    USER(Code.USER),
    MANAGER(Code.MANAGER);

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public class Code {
        public static final String USER = "USER";
        public static final String MANAGER = "MANAGER";
        public static final String ADMIN = "ADMIN";
    }
}
