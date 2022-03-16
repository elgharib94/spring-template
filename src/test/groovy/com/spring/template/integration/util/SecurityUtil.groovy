package com.spring.template.integration.util

import com.spring.template.domain.model.Role
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.test.context.TestSecurityContextHolder

class SecurityUtil {

//    private static final String ROLE_DEFAULT_PREFIX = "ROLE_";
    private static final String ROLE_DEFAULT_PREFIX = "";

    static void loginAsAdmin(String username) {
        logInWithRole("test_admin", ROLE_DEFAULT_PREFIX + Role.Code.ADMIN);
    }

    static void loginAsManager(String username) {
        logInWithRole("test_manager", ROLE_DEFAULT_PREFIX + Role.Code.MANAGER);
    }

    static void loginAsUser(String username) {
        logInWithRole("test_user", ROLE_DEFAULT_PREFIX + Role.Code.USER);
    }

    static void loginAsAnonymous(String username) {
        logInWithRole("", "anonymous");
    }

    private static void logInWithRole(String username, String role) {
        TestSecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(username, "pass1234", List.of(new SimpleGrantedAuthority(role)))
        );
    }

    static void logout() {
        TestSecurityContextHolder.getContext().setAuthentication(null);
    }
}
